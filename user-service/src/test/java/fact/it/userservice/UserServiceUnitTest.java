package fact.it.userservice;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import fact.it.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setId("1000");
        userRequest.setUsername("TestUser");
        userRequest.setEmail("testuser@gmail.com");

        // Act
        userService.createUser(userRequest);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user = new User();
        user.setId("1000");
        user.setUsername("TestUser");
        user.setEmail("testuser@gmail.com");
        List<User> list = Arrays.asList(user);

        when(userRepository.findAll()).thenReturn(list);

        // Act
        List<UserResponse> users = userService.getAllUsers();

        // Assert
        System.out.println(users);
        assertEquals(1, users.size());
        assertEquals("TestUser", users.get(0).getUsername());
        assertEquals("testuser@gmail.com", users.get(0).getEmail());

        verify(userRepository, times(1)).findAll();
    }


    @Test
    void getUserByIdTest() {
        // Arrange
        String userId = "123";
        User user = new User();
        user.setId("1000");
        user.setUsername("TestUser");
        user.setEmail("testuser@gmail.com");

        List<User> mockUsers = Arrays.asList(user);

        // Mock the userRepository behavior
        when(userRepository.findByIdIn(userId)).thenReturn(mockUsers);

        // Act
        List<UserResponse> result = userService.getUserById(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("TestUser", result.get(0).getUsername());

        // Optionally, you can verify that the method of userRepository was called with the correct parameter
        verify(userRepository, times(1)).findByIdIn(userId);
    }

}