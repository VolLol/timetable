package com.timetable.timetable.forms;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventAddForm {

    @Getter
    @Setter
    private String summary;
    @Getter
    @Setter
    private String room;
    @Getter
    @Setter
    private String startedAt;
    @Getter
    @Setter
    private String finishedAt;

}
