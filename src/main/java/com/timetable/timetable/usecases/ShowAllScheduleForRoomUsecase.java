package com.timetable.timetable.usecases;

import com.timetable.timetable.entities.EventEntity;
import com.timetable.timetable.entities.RoomEntity;
import com.timetable.timetable.forms.EventScheduleForm;
import com.timetable.timetable.forms.ScheduleForm;
import com.timetable.timetable.repositories.EventRepository;
import com.timetable.timetable.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ShowAllScheduleForRoomUsecase {

    private final RoomRepository roomRepository;
    private final EventRepository eventRepository;

    public ShowAllScheduleForRoomUsecase(RoomRepository roomRepository, EventRepository eventRepository) {
        this.roomRepository = roomRepository;
        this.eventRepository = eventRepository;
    }

    public ScheduleForm execute(String incomeRoomId) {
        Long roomId = Long.parseLong(incomeRoomId);
        Optional<RoomEntity> room = roomRepository.findById(roomId);
        String roomName = room.get().getName();

        List<Collection<EventEntity>> scheduleOnWeek = getAllEventsInWeek(roomId);
        List<Collection<EventScheduleForm>> result = getResultModel(scheduleOnWeek);

        return ScheduleForm.builder()
                .roomName(roomName)
                .scheduleOnWeek(result)
                .build();
    }

    private List<Collection<EventEntity>> getAllEventsInWeek(Long roomId) {
        ArrayList<Collection<EventEntity>> allWeekSchedule = new ArrayList<>();
        LocalDate day = LocalDate.now().with(DayOfWeek.MONDAY);
        for (int i = 1; i < 7; i++) {
            LocalDateTime startAt = LocalDateTime.of(day.getYear(), day.getMonth(),
                    day.getDayOfMonth(), 0, 0, 0);
            LocalDateTime finishAt = LocalDateTime.of(day.getYear(), day.getMonth(),
                    day.getDayOfMonth(), 0, 0, 0).plusDays(1);
            day = day.plusDays(1);
            allWeekSchedule.add(eventRepository.findAllByRoomIdAndStartAtGreaterThanAndFinishAtLessThan(roomId, startAt, finishAt));
            List<EventEntity> events = (List<EventEntity>) allWeekSchedule.get(i - 1);
            Collections.sort(events, Comparator.comparing(EventEntity::getStartAt));
        }
        return allWeekSchedule;
    }

    private List<Collection<EventScheduleForm>> getResultModel(List<Collection<EventEntity>> allWeekSchedule) {
        List<Collection<EventScheduleForm>> resultModel = new ArrayList<>();
        for (Collection<EventEntity> list : allWeekSchedule) {
            Collection<EventEntity> listOfEvents = list;
            Collection<EventScheduleForm> eventShowForms = new ArrayList<>();
            for (EventEntity eventEntity : listOfEvents) {
                EventScheduleForm eventShowForm = EventScheduleForm.builder()
                        .id(eventEntity.getId())
                        .summary(eventEntity.getSummary())
                        .dates(eventEntity.getStartAt().format(DateTimeFormatter.ofPattern("dd/MM")) +
                                "-" +
                                eventEntity.getFinishAt().format(DateTimeFormatter.ofPattern("dd/MM")
                                ))
                        .startAt(eventEntity.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm")))
                        .finishAt(eventEntity.getFinishAt().format(DateTimeFormatter.ofPattern("HH:mm")))
                        .build();
                eventShowForms.add(eventShowForm);
            }
            resultModel.add(eventShowForms);
        }
        return resultModel;
    }


}
