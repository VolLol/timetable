package com.timetable.timetable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    @Getter
    private Long id;
    @Column(unique = true)
    @Getter
    private String username;
    @Getter
    private String password;
}
