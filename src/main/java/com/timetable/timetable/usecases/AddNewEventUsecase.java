package com.timetable.timetable.usecases;

import com.timetable.timetable.entities.EventEntity;
import com.timetable.timetable.exceptions.IncorrectDateException;
import com.timetable.timetable.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.logging.Logger;

@Service
public class AddNewEventUsecase {

    private Logger log = Logger.getLogger(AddNewEventUsecase.class.getName());
    private final EventRepository eventRepository;

    public AddNewEventUsecase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    public void execute(String summary, String room, String startedAt, String finishedAt) throws IncorrectDateException {

        LocalDateTime start = LocalDateTime.parse(startedAt);
        LocalDateTime finish = LocalDateTime.parse(finishedAt);

        if (finish.isBefore(start)) {
            log.info("Incorrect date");
            throw new IncorrectDateException("Incorrect start date");
        }
        if (finish.minusMinutes(30L).isBefore(start)) {
            log.info("Incorrect date");
            throw new IncorrectDateException("Incorrect finish date. Minimum booking interval 30 minutes");
        }

        if (Duration.between(start, finish).toMinutes() > 1440) {
            log.info("Incorrect date");
            throw new IncorrectDateException("Incorrect date. Maximum booking interval 24 hours");
        }

        LocalDate startDate = LocalDate.of(start.getYear(), start.getMonth(), start.getDayOfMonth());
        LocalTime startTime = LocalTime.of(start.getHour(), start.getMinute());
        LocalDate finishDate = LocalDate.of(finish.getYear(), finish.getMonth(), finish.getDayOfMonth());
        LocalTime finishTime = LocalTime.of(finish.getHour(), finish.getMinute());

        Collection<EventEntity> eventsWithSameStartDateAndFinishDate = eventRepository.findAllByStartDateAndFinishDate(startDate, finishDate);
        for (EventEntity event : eventsWithSameStartDateAndFinishDate) {
            System.out.println(event.toString());
            if (event.getStartTime().isBefore(startTime) && event.getFinishTime().isAfter(finishTime)) {
                log.info("Incorrect date");
                throw new IncorrectDateException("Incorrect date. The time on this day is already taken ");
            }
        }

        EventEntity event = EventEntity.builder()
                .summary(summary)
                .room(room)
                .startDate(startDate)
                .finishDate(finishDate)
                .startTime(startTime)
                .finishTime(finishTime)
                .build();
        eventRepository.save(event);
    }

}
