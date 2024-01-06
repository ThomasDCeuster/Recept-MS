package fact.it.userservice.controller;

import fact.it.userservice.dto.*;
import fact.it.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public void createUser(@RequestBody UserRequest userRequest) {
//        {
//            userService.createUser(userRequest);
//        }
//    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<UserResponse> getUserById(@RequestParam Long id) {
//        return userService.getUserById(id);
//    }
//
//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public List<UserResponse> getAllUsers() {
//        return userService.getAllUsers();
//    }
}