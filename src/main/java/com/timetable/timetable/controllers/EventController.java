package com.timetable.timetable.controllers;

import com.timetable.timetable.exceptions.IncorrectDateException;
import com.timetable.timetable.forms.EventAddForm;
import com.timetable.timetable.forms.EventDetailsForm;
import com.timetable.timetable.repositories.RoomRepository;
import com.timetable.timetable.usecases.AddNewEventUsecase;
import com.timetable.timetable.usecases.ShowEventDetailsUsecase;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EventController {

    private final AddNewEventUsecase addNewEventUsecase;
    private final RoomRepository roomRepository;
    private final ShowEventDetailsUsecase showEventDetailsUsecase;

    public EventController(AddNewEventUsecase addNewEventUsecase, RoomRepository roomRepository, ShowEventDetailsUsecase showEventDetailsUsecase) {
        this.addNewEventUsecase = addNewEventUsecase;
        this.roomRepository = roomRepository;
        this.showEventDetailsUsecase = showEventDetailsUsecase;
    }


    @GetMapping("/addEvent")
    public String getAuth(Model model) {
        model.addAttribute("eventForm", new EventAddForm());
        model.addAttribute("roomList", roomRepository.findAll());
        return "addEvent";
    }


    @PostMapping("/addEvent")
    public String addEvent(@ModelAttribute("eventForm") EventAddForm eventAddForm, BindingResult bindingResult, Model model, Authentication authentication) {
        try {
            addNewEventUsecase.execute(
                    eventAddForm.getSummary(),
                    eventAddForm.getRoom(),
                    eventAddForm.getStartedAt(),
                    eventAddForm.getFinishedAt(),
                    authentication.getName());
            return "redirect:/rooms";
        } catch (IncorrectDateException e) {
            bindingResult.rejectValue("startedAt", "error.start", e.getMessage() + " . Choose another");
            model.addAttribute("roomList", roomRepository.findAll());
            return "addEvent";
        }
    }

    @GetMapping("/{event_id}/details")
    public String showDetails(Model model, @PathVariable("event_id") String incomeEventId) {
        EventDetailsForm eventDetailsForm = showEventDetailsUsecase.execute(incomeEventId);
        model.addAttribute("eventDetails", eventDetailsForm);
        return "details";
    }
}
