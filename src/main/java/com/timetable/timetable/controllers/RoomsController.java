package com.timetable.timetable.controllers;

import com.timetable.timetable.forms.ScheduleForm;
import com.timetable.timetable.repositories.RoomRepository;
import com.timetable.timetable.usecases.ShowAllScheduleForRoomUsecase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomsController {

    private final RoomRepository roomRepository;
    private final ShowAllScheduleForRoomUsecase showAllScheduleForRoomUsecase;

    public RoomsController(RoomRepository roomRepository, ShowAllScheduleForRoomUsecase showAllScheduleForRoomUsecase) {
        this.roomRepository = roomRepository;
        this.showAllScheduleForRoomUsecase = showAllScheduleForRoomUsecase;
    }

    @GetMapping("/rooms")
    public String showAllRooms(Model model) {
        model.addAttribute("roomsList", roomRepository.findAll());
        return "rooms";
    }

    @GetMapping("/rooms/{room_id}/schedule")
    public String showScheduleForMeetingRoom(Model model, @PathVariable("room_id") String incomeRoomId) {
        ScheduleForm schedule = showAllScheduleForRoomUsecase.execute(incomeRoomId);
        model.addAttribute("schedule", schedule);
        return "schedule";
    }
}
