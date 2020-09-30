package com.timetable.timetable.controllers;

import com.timetable.timetable.exceptions.IncorrectDateException;
import com.timetable.timetable.forms.EventAddForm;
import com.timetable.timetable.usecases.AddNewEventUsecase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventController {

    private final AddNewEventUsecase addNewEventUsecase;

    public EventController(AddNewEventUsecase addNewEventUsecase) {
        this.addNewEventUsecase = addNewEventUsecase;
    }


    @GetMapping("/addEvent")
    public String getAuth(Model model) {
        model.addAttribute("eventForm", new EventAddForm());
        return "addEvent";
    }


    @PostMapping("/addEvent")
    public String postAuth(@ModelAttribute("eventForm") EventAddForm eventAddForm, BindingResult bindingResult) {
        try {
            addNewEventUsecase.execute(
                    eventAddForm.getSummary(),
                    eventAddForm.getRoom(),
                    eventAddForm.getStartedAt(), eventAddForm.getFinishedAt());
            return "table";
        } catch (IncorrectDateException e) {
            bindingResult.rejectValue("startedAt", "error.start", e.getMessage() + " . Choose another");
            return "addEvent";
        }
    }
}
