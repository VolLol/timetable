package com.timetable.timetable.entities;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    @Column(nullable = false)
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false)
    private String username;
    @Getter
    @Setter
    @Column(name = "role_name", nullable = false)
    private String roleName;

}
