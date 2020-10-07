package com.timetable.timetable.forms;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailsForm {

    @Getter
    @Setter
    private String summary;
    @Getter
    @Setter
    private String organizerName;
    @Getter
    @Setter
    private String roomName;
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private String time;


}
