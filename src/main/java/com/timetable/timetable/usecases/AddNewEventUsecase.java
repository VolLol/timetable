package com.timetable.timetable.usecases;

import com.timetable.timetable.entities.EventEntity;
import com.timetable.timetable.entities.RoomEntity;
import com.timetable.timetable.entities.UserEntity;
import com.timetable.timetable.exceptions.IncorrectDateException;
import com.timetable.timetable.repositories.EventRepository;
import com.timetable.timetable.repositories.RoomRepository;
import com.timetable.timetable.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;

@Service
public class AddNewEventUsecase {

    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public AddNewEventUsecase(EventRepository eventRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }


    public void execute(String summary, String roomName, String startedAt, String finishedAt, String username) throws IncorrectDateException, DateTimeParseException {
        try {

            LocalDateTime start = LocalDateTime.parse(startedAt);
            LocalDateTime finish = LocalDateTime.parse(finishedAt);

            if (finish.isBefore(start)) {
                throw new IncorrectDateException("Incorrect start date");
            }

            if (finish.minusMinutes(30L).isBefore(start)) {
                throw new IncorrectDateException("Incorrect finish date. Minimum booking interval 30 minutes");
            }

            if (Duration.between(start, finish).toMinutes() > 1440) {
                throw new IncorrectDateException("Incorrect date. Maximum booking interval 24 hours");
            }


            RoomEntity roomEntity = roomRepository.findByName(roomName);

            UserEntity user = userRepository.findByUsername(username);

            Collection<EventEntity> crossingEvents = eventRepository.findAllCrossing(start, finish, roomEntity.getId());
            if (crossingEvents.size() == 0) {
                EventEntity event = EventEntity.builder()
                        .summary(summary)
                        .userId(user.getId())
                        .roomId(roomEntity.getId())
                        .startAt(start)
                        .finishAt(finish)
                        .build();
                eventRepository.save(event);
            } else {
                throw new IncorrectDateException("Incorrect date. The time on this day is already taken ");
            }

        } catch (DateTimeParseException e) {
            throw new IncorrectDateException("Incorrect date ");
        }

    }


}
