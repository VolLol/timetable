package com.timetable.timetable.repositories;

import com.timetable.timetable.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    RoomEntity findByName(String name);
}
