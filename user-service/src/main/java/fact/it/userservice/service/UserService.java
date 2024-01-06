package fact.it.userservice.service;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @PostConstruct
    public void loadData() {
        if(userRepository.count() <= 0){
            User user = User.builder()
                    .id("0")
                    .username("JohnDoe")
                    .email("test@email.com")
                    .build();

            User user2 = User.builder()
                    .id("1")
                    .username("JaneDoe")
                    .email("test@email.com")
                    .build();


            userRepository.save(user);
            userRepository.save(user2);
        }
    }

    public void createUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .build();

        userRepository.save(user);
    }

    public List<UserResponse> getUserById(String id) {
        List<User> users = userRepository.findByIdIn(id);

        return users.stream().map(this::mapToUserResponse).toList();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(this::mapToUserResponse).toList();
    }
}
