# Gokarting System

Spring Boot application originally created for WWW Programming course at university.
It was extended/modified a little since then.

### Description

Application was created mainly for learning purposes. It imitates system for managing gokarting tracks.
It uses PostgreSQL database.

It allows logged in users (Basic Auth) to reserve tickets for specific time slots during the day (UTC time):
* slot 0: 15:00 - 16:00
* slot 1: 16:00 - 17:00
* slot 2: 17:00 - 18:00
* slot 3: 18:00 - 19:00
* slot 4: 19:00 - 20:00
* slot 5: 20:00 - 21:00
* slot 6: 21:00 - 22:00
* slot 7: 22:00 - 23:00

Ticket reservation is possible only for the current day.

Expired tickets are deleted from the database by a scheduled method every 60 minutes.
They are send to Kafka with additional info (hardcoded best lap time) in JSON format and then consumed by GO api.

### Endpoints

| Path                                     | HTTP Method | Authorized  |
|------------------------------------------|-------------|------------|
| `/tracks`                                 | GET         | -          |
| `/tracks/{id}`                            | GET         | ADMIN, USER |
| `/tracks`                                  | POST        | ADMIN      |
| `/tracks/{id}`                             | DELETE      | ADMIN      |
| `/tracks/{id}`                             | PATCH       | ADMIN      |
| `/tracks/{id}/gokarts`                     | GET         | ADMIN, USER |
| `/tracks/{id}/gokarts`                     | POST        | ADMIN      |
| `/gokarts/{gokartId}/reserve/{slotNumber}` | POST        | ADMIN, USER |
| `/register`                                | POST        | -          |
| `/login`                                   | GET         | -          |
| `/logout`                                  | POST        | ADMIN, USER |
| `/reservations`                            | GET         | USER, ADMIN |
