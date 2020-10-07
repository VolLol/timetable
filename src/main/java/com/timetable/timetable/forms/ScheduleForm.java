package com.timetable.timetable.forms;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleForm {

    @Getter
    @Setter
    private String roomName;

    @Getter
    @Setter
    private List<Collection<EventScheduleForm>> scheduleOnWeek = new ArrayList<>();
}
