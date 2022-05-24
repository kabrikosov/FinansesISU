package ru.isu.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isu.model.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/getId")
    public Integer getLoggedUserId(@AuthenticationPrincipal User user){
        return user.getId();
    }
}
