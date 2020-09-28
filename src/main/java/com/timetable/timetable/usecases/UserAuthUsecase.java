package com.timetable.timetable.usecases;

import com.timetable.timetable.entities.UserEntity;
import com.timetable.timetable.exceptions.IncorrectUserPasswordException;
import com.timetable.timetable.exceptions.IncorrectUsernameException;
import com.timetable.timetable.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserAuthUsecase {
    private Logger log = Logger.getLogger(UserAuthUsecase.class.getName());
    private final UserRepository userRepository;

    public UserAuthUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String execute(String incomeUsername, String incomePassword) throws IncorrectUserPasswordException, IncorrectUsernameException {
        log.info("Start execute");
        try {
            UserEntity user = userRepository.findByUsername(incomeUsername);
            if (user.getPassword().equals(incomePassword)) {
                log.info("User has successfully authenticated");
                return "Susses";
            } else {
                log.info("Not successful. User \"" + incomeUsername + "\" write incorrect password");
                throw new IncorrectUserPasswordException("User " + incomeUsername + " write incorrect password");
            }
        } catch (NullPointerException e) {
            log.info("Not successful. User with \"" + incomeUsername + "\" username not exist");
            throw new IncorrectUsernameException("User with username " + incomeUsername + " not exist");
        }
    }
}
