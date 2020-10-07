package com.timetable.timetable.forms;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventScheduleForm {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String summary;
    @Getter
    @Setter
    private String dates;
    @Getter
    @Setter
    private String startAt;
    @Getter
    @Setter
    private String finishAt;

}
