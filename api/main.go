package main

import (
	"context"
	"encoding/json"
	"errors"
	"flag"
	"fmt"
	"github.com/joho/godotenv"
	"github.com/julienschmidt/httprouter"
	"github.com/migonov/gokarting_center/api/mailer"
	"github.com/segmentio/kafka-go"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"net/http"
	"os"
	"strconv"
)

const (
	dbName        = "gokartingCenter"
	dbCollection  = "results"
	brokerAddress = "localhost:29092"
	kafkaTopic    = "res"
)

type config struct {
	port int
	smtp struct {
		host     string
		port     int
		username string
		password string
		sender   string
	}
}

type application struct {
	config     config
	collection *mongo.Collection
	mailer     mailer.Mailer
}

type result struct {
	Horsepower int
	Name       string
	Time       float64
	Date       []int
	Gokart     string
}

func newApp(c *mongo.Collection, cfg config) *application {
	return &application{
		config:     cfg,
		collection: c,
		mailer:     mailer.New(cfg.smtp.host, cfg.smtp.port, cfg.smtp.username, cfg.smtp.password, cfg.smtp.sender),
	}
}

func (app *application) routes() http.Handler {
	router := httprouter.New()

	router.HandlerFunc(http.MethodGet, "/v1/results", app.handleGetAllResults)
	router.HandlerFunc(http.MethodGet, "/v1/results/:name", app.handleGetUserResults)

	return router
}

func (app *application) serve() error {
	srv := &http.Server{
		Handler: app.routes(),
		Addr:    fmt.Sprintf(":%d", app.config.port),
	}

	return srv.ListenAndServe()
}

func (app *application) handleGetAllResults(w http.ResponseWriter, _ *http.Request) {
	query := bson.M{}
	cursor, err := app.collection.Find(context.TODO(), query)
	if err != nil {
		log.Fatal(err)
	}

	var results []bson.M
	err = cursor.All(context.TODO(), &results)
	if err != nil {
		log.Fatal(err)
	}

	w.WriteHeader(http.StatusOK)
	w.Header().Add("Content-Type", "application/json")
	err = json.NewEncoder(w).Encode(results)
	if err != nil {
		log.Fatal(err)
	}
}

func (app *application) readNameParam(r *http.Request) string {
	params := httprouter.ParamsFromContext(r.Context())

	name := params.ByName("name")
	log.Printf("getting results for user %s", name)

	return name
}

func (app *application) handleGetUserResults(w http.ResponseWriter, r *http.Request) {
	name := app.readNameParam(r)
	var res result

	err := app.collection.FindOne(context.TODO(), bson.M{"name": name}).Decode(&res)
	if err != nil {
		if errors.Is(err, mongo.ErrNoDocuments) {
			w.WriteHeader(http.StatusNotFound)
			return
		}
		log.Fatalf("check %s", err)
	}

	w.WriteHeader(http.StatusOK)
	w.Header().Add("Content-Type", "application/json")
	err = json.NewEncoder(w).Encode(res)
	if err != nil {
		log.Fatal(err)
	}
}

func kafkaConsumer(app *application) {
	log.Printf("starting kafka consumer")
	config := kafka.ReaderConfig{
		Brokers: []string{brokerAddress},
		Topic:   kafkaTopic,
	}

	reader := kafka.NewReader(config)
	for {
		message, err := reader.ReadMessage(context.Background())
		if err != nil {
			log.Fatal(err)
		}
		saveResult(string(message.Value), app.collection)
		sendResultEmail(app, string(message.Value))
	}
}

func sendResultEmail(app *application, res string) {
	var _result result
	err := json.Unmarshal([]byte(res), &_result)
	data := map[string]any{
		"name":       _result.Name,
		"gokart":     _result.Gokart,
		"date":       _result.Date,
		"horsepower": _result.Horsepower,
		"time":       _result.Time,
	}
	err = app.mailer.Send("test@test.com", "result_mail.tmpl", data)
	if err != nil {
		log.Print(err)
	}
}

func saveResult(result string, coll *mongo.Collection) {
	log.Printf("Saving result: %s\n", result)
	var _result bson.M
	err := json.Unmarshal([]byte(result), &_result)
	if err != nil {
		log.Fatal(err)
	}
	_, err = coll.InsertOne(context.TODO(), _result)
	if err != nil {
		log.Fatal(err)
	}
}

func main() {
	err := godotenv.Load(".env")
	if err != nil {
		log.Fatal(err)
	}

	var cfg config
	flag.IntVar(&cfg.port, "port", 4000, "App port")
	flag.StringVar(&cfg.smtp.host, "smtp-host", os.Getenv("SMTP_HOST"), "SMTP host")

	port, err := strconv.Atoi(os.Getenv("SMTP_PORT"))
	if err != nil {
		log.Fatal(err)
	}
	flag.IntVar(&cfg.smtp.port, "smtp-port", port, "SMTP port")

	flag.StringVar(&cfg.smtp.username, "smtp-username", os.Getenv("SMTP_USERNAME"), "SMTP username")
	flag.StringVar(&cfg.smtp.password, "smtp-password", os.Getenv("SMTP_PASSWORD"), "SMTP password")
	flag.StringVar(&cfg.smtp.sender, "smtp-sender", "API <no-reply@gokartingCenter.net>", "SMTP sender")

	client, err := mongo.Connect(context.TODO(), options.Client().ApplyURI("mongodb://localhost:27017"))
	if err != nil {
		log.Fatal(err)
	}
	collection := client.Database(dbName).Collection(dbCollection)

	app := newApp(collection, cfg)

	go kafkaConsumer(app)

	log.Printf("starting server on :%d", cfg.port)

	err = app.serve()
	log.Fatal(err)
}
