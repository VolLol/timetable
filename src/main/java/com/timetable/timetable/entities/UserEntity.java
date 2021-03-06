package com.timetable.timetable.entities;

import lombok.*;

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
    @Column(nullable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    @Getter
    private String username;
    @Getter
    @Setter
    @Column(nullable = false)
    private String password;
    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean enabled;
}
