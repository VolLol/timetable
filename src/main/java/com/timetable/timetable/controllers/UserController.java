package com.timetable.timetable.controllers;

import com.timetable.timetable.entities.EventEntity;
import com.timetable.timetable.entities.RoleEntity;
import com.timetable.timetable.entities.RoomEntity;
import com.timetable.timetable.entities.UserEntity;
import com.timetable.timetable.forms.UserAuthForm;
import com.timetable.timetable.repositories.EventRepository;
import com.timetable.timetable.repositories.RoleRepository;
import com.timetable.timetable.repositories.RoomRepository;
import com.timetable.timetable.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@Controller
public class UserController {

    @GetMapping("/auth")
    public String getAuth(Model model) {
        model.addAttribute("userForm", new UserAuthForm());
        return "auth";
    }

}
