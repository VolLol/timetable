package com.timetable.timetable.repositories;

import com.timetable.timetable.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Collection<EventEntity> findAllByStartDateAndFinishDate(LocalDate startDate, LocalDate finishDate);

}
