package com.example.gokartingsystem.services;

import com.example.gokartingsystem.dto.KafkaMessage;
import com.example.gokartingsystem.entities.Ticket;
import com.example.gokartingsystem.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class Scheduler {
    private final TicketRepository ticketRepository;
    private final Clock clock;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    @Value("${spring.kafka.topic}")
    private String resultTopic;

    public Scheduler(TicketRepository ticketRepository, Clock clock, KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        this.ticketRepository = ticketRepository;
        this.clock = clock;
        this.kafkaTemplate = kafkaTemplate;
    }

    private final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Scheduled(fixedRate = 6000000)
//    @Scheduled(fixedDelay = 50000)
    public void expiredTicketsRemoval() {
        List<Ticket> ticketList = ticketRepository.findAll();
        LocalDateTime localDateTime = LocalDateTime.now(clock);

        int deletedTickets = 0;

        for (Ticket ticket : ticketList) {
            if (ticket.getReservationDate().isAfter(localDateTime)) {
                sendResultToKafka(ticket);
                ticketRepository.delete(ticket);
                deletedTickets++;
            }
        }

        logger.info("Deleted " + deletedTickets + " tickets");
    }

    private void sendResultToKafka(Ticket ticket) {
        KafkaMessage kafkaMessage = new KafkaMessage(ticket);
        kafkaTemplate.send(resultTopic, kafkaMessage);
    }
}
