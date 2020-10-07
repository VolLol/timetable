package com.timetable.timetable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue
    @Getter
    @Column(nullable = false)
    private Long id;
    @Getter
    @Column(name = "room_id", nullable = false)
    private Long roomId;
    @Getter
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Getter
    @Column(nullable = false)
    private String summary;

    @Getter
    @Column(name = "startAt", nullable = false)
    private LocalDateTime startAt;
    @Getter
    @Column(name = "finishAt", nullable = false)
    private LocalDateTime finishAt;
}
