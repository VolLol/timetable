package com.timetable.timetable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue
    @Getter
    private Long id;
    @Getter
    private String summary;
    @Getter
    private String room;
    @Getter
    @Column(name = "start_date")
    private LocalDate startDate;
    @Getter
    @Column(name = "start_time")
    private LocalTime startTime;
    @Getter
    @Column(name = "finish_date")
    private LocalDate finishDate;
    @Getter
    @Column(name = "finish_time")
    private LocalTime finishTime;

}
