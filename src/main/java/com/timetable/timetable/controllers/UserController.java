package com.timetable.timetable.controllers;

import com.timetable.timetable.exceptions.IncorrectUserPasswordException;
import com.timetable.timetable.exceptions.IncorrectUsernameException;
import com.timetable.timetable.forms.UserAuthForm;
import com.timetable.timetable.entities.UserEntity;
import com.timetable.timetable.repositories.UserRepository;
import com.timetable.timetable.usecases.UserAuthUsecase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserAuthUsecase userAuthUsecase;


    public UserController(UserRepository userRepository, UserAuthUsecase userAuthUsecase) {
        this.userRepository = userRepository;
        this.userAuthUsecase = userAuthUsecase;
    }

    @GetMapping("/auth")
    public String getAuth(Model model) {
        model.addAttribute("userForm", new UserAuthForm());
        return "auth";
    }

    @PostMapping("/auth")
    public String postAuth(@ModelAttribute("userForm") UserAuthForm userEntity, BindingResult bindingResult) {
        try {
            userAuthUsecase.execute(userEntity.getUsername(), userEntity.getPassword());
            return "table";
        } catch (IncorrectUserPasswordException e) {
            bindingResult.rejectValue("username", "error.username", "Incorrect password. Try another");
            return "auth";
        } catch (IncorrectUsernameException e) {
            bindingResult.rejectValue("password", "error.password", "User with username \"" + userEntity.getUsername() + "\" not exist");
            return "auth";
        }
    }

}
