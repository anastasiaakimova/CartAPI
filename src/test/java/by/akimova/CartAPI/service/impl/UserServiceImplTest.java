package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotFreeUsernameException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testing class for {@link UserService}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user1;
    private User user2;
    private User userToSave;
    private List<User> users;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();

        user1 = new User();
        user1.setUserId(UUID.randomUUID());
        user1.setName("Alex");
        user1.setMail("asd@mail");
        user1.setRole(Role.USER);
        user1.setPassword("user");

        users.add(user1);

        user2 = new User();
        user2.setUserId(UUID.randomUUID());
        user2.setName("Qwerty");
        user2.setMail("zxcvb@mail");
        user2.setRole(Role.ADMIN);
        user2.setPassword("admin");

        users.add(user2);

        userToSave = new User();
        userToSave.setMail("asd@mail");
        userToSave.setPassword(bCryptPasswordEncoder.encode("user"));

    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = null;
        users = null;
    }

    @Test
    void getAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(users);
        List<User> users1 = userServiceImpl.getAllUsers();
        assertEquals(users1, users);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getById_success() throws Exception {
        when(userRepository.findUserByUserId(user1.getUserId())).thenReturn(user1);
        assertThat(userServiceImpl.getById((user1.getUserId()))).isEqualTo(user1);
    }

    @Test
    void getById_NotValidUsernameException() throws Exception {
        User user = new User();
        when(userRepository.findUserByUserId(user.getUserId())).thenReturn(user);
        assertThatThrownBy(() -> userServiceImpl.getById(null))
                .isInstanceOf(NotValidUsernameException.class).hasMessage("userId is null");
    }

    @Test
    void getById_EntityNotFoundException() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findUserByUserId(uuid)).thenReturn(null);
        assertThatThrownBy(() -> userServiceImpl.getById(uuid))
                .isInstanceOf(EntityNotFoundException.class).hasMessage("user not found");
    }

    @Test
    void saveUser_success() throws Exception {
        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        User savedUser = userServiceImpl.saveUser(userToSave);
        assertThat(savedUser.getMail()).isNotNull();
        assertThat(savedUser.getUserId()).isSameAs(userToSave.getUserId());
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void saveUser_NotFreeUsernameException() throws Exception {
        when(userRepository.findByMail(user1.getMail())).thenReturn(Optional.ofNullable(user1));
        assertThatThrownBy(() -> userServiceImpl.saveUser(userToSave).getMail().equals(user1.getMail()))
                .isInstanceOf(NotFreeUsernameException.class).hasMessage("This username is already taken");
    }

    @Test
    void updateUser() throws Exception {
        when(userRepository.findUserByUserId(user1.getUserId())).thenReturn(user1);
        User user = userServiceImpl.getById((user1.getUserId()));
        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        User updatedUser = userServiceImpl.updateUser(user.getUserId(), userToSave);
        assertThat(updatedUser.getMail()).isEqualTo(userToSave.getMail());

    }

    @Test
    void findByMail_success() throws Exception {
        when(userRepository.findByMail(user1.getMail())).thenReturn(java.util.Optional.of(user1));
        assertThat(userServiceImpl.findByMail((user1.getMail()))).isEqualTo(Optional.of(user1));
    }

    @Test
    void findByMail_EntityNotFoundException() throws Exception {
        String mail = "ghjnbv@mail";

        when(userRepository.findByMail(mail)).thenReturn(isNull());

        assertThatThrownBy(() -> userServiceImpl.findByMail(mail))
                .isInstanceOf(EntityNotFoundException.class).hasMessage("User doesn't exists");
    }

    @Test
    void deleteUserById() throws Exception {
        userServiceImpl.deleteUserById(user1.getUserId());
        verify(userRepository, times(1)).deleteUserByUserId(user1.getUserId());
    }
}