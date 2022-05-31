package ru.isu.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isu.model.User;
import ru.isu.projection.UsersMoney;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/getId")
    public Integer getLoggedUserId(@AuthenticationPrincipal User user){
        return user.getId();
    }

    @GetMapping("/getUsersMoney")
    public UsersMoney getUsersMoney(@AuthenticationPrincipal User user){
        return new UsersMoney() {
            @Override
            public Integer getMonthlyIncome() {
                return user.getMonthlyIncome();
            }

            @Override
            public Integer getCashAvailable() {
                return user.getCashAvailable();
            }
        };
    }
}
