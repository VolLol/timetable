package com.timetable.timetable.forms;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthForm {

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;


}
