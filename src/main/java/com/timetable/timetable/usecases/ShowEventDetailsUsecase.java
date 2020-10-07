package com.timetable.timetable.usecases;

import com.timetable.timetable.entities.EventEntity;
import com.timetable.timetable.entities.RoomEntity;
import com.timetable.timetable.entities.UserEntity;
import com.timetable.timetable.forms.EventDetailsForm;
import com.timetable.timetable.repositories.EventRepository;
import com.timetable.timetable.repositories.RoomRepository;
import com.timetable.timetable.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ShowEventDetailsUsecase {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ShowEventDetailsUsecase(EventRepository eventRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public EventDetailsForm execute(String incomeEventId) {
        Long eventId = Long.parseLong(incomeEventId);
        Optional<EventEntity> eventEntity = eventRepository.findById(eventId);
        String summary = eventEntity.get().getSummary();
        LocalDateTime startAt = eventEntity.get().getStartAt();
        LocalDateTime finishAt = eventEntity.get().getFinishAt();
        String date = prepareDateField(startAt, finishAt);
        String time = preparingTimeField(startAt, finishAt);


        Long organizerId = eventEntity.get().getUserId();
        Optional<UserEntity> organizer = userRepository.findById(organizerId);
        String organizerName = organizer.get().getUsername();

        Long roomId = eventEntity.get().getRoomId();
        Optional<RoomEntity> room = roomRepository.findById(roomId);
        String roomName = room.get().getName();


        return EventDetailsForm.builder()
                .organizerName(organizerName)
                .roomName(roomName)
                .summary(summary)
                .date(date)
                .time(time)
                .build();
    }

    private String prepareDateField(LocalDateTime startAt, LocalDateTime finishAt) {
        String date = new String();
        if (startAt.getDayOfYear() == finishAt.getDayOfYear() &&
                startAt.getDayOfMonth() == finishAt.getDayOfMonth()) {
            date = startAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } else {
            date = date.concat("since ");
            date = date.concat(startAt.format(DateTimeFormatter.ofPattern("dd-MM")));
            date = date.concat(" for ");
            date = date.concat(finishAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return date;
    }

    private String preparingTimeField(LocalDateTime startAt, LocalDateTime finishAt) {
        String time = new String();
        time = time.concat(startAt.format(DateTimeFormatter.ofPattern("HH:mm")));
        time = time.concat(" - ");
        time = time.concat(finishAt.format(DateTimeFormatter.ofPattern("HH:mm")));
        return time;
    }
}
