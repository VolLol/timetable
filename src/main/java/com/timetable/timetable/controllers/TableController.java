package com.timetable.timetable.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {


    @GetMapping("/table")
    public String getTable() {
        return "table";
    }
}
