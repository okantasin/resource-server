package com.template.resourceserver.controller;


import com.template.resourceserver.dto.UserResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    @GetMapping("/status/check")
    public String statusCheck() {
        return "OK";
    }

    // @Secured("ROLE_user")
    @PreAuthorize("hasAuthority('ROLE_developer') or #userId == #jwt.subject")
    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable String userId, @AuthenticationPrincipal Jwt jwt) {
        return "Deleted user: " + userId + " from " + jwt.getSubject();

    }

    // @Secured("ROLE_user")
    @PostAuthorize("returnObject.userId == #jwt.subject")
    @GetMapping(path = "/{userId}")
    public UserResponse getUser(@PathVariable String userId, @AuthenticationPrincipal Jwt jwt) {
        return new UserResponse("Okan","tasin","994dec25-a13f-490f-97c7-2de8389ddde3");

    }
}
