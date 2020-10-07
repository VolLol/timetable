package com.timetable.timetable.repositories;

import com.timetable.timetable.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Collection<EventEntity> findAllByRoomIdAndStartAtGreaterThanAndFinishAtLessThan(Long roomId, LocalDateTime startAt, LocalDateTime finishAt);

    @Query("SELECT e FROM EventEntity e where e.roomId = :roomId and (e.startAt  <= :finishAt  and e.finishAt >= :startAt )")
    List<EventEntity> findAllCrossing(@Param("startAt") LocalDateTime startAt,
                                      @Param("finishAt") LocalDateTime finishAt,
                                      @Param("roomId") Long roomId);


}
