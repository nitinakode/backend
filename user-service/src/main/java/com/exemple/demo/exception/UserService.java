package com.exemple.demo.exception;


import com.exemple.demo.config.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationProvider userAuthenticationProvider;
    public UserDto findByLogin(String login) {
        User user = userRepository.findByEmail(login)
                .orElseThrow(() -> new AppException("UNKNOWN USER", HttpStatus.PAYMENT_REQUIRED));


        if (user.getStatus().equals(Status.INACTIVE)) {
            throw new AppException("Subscription plan expired", HttpStatus.PAYMENT_REQUIRED);
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public UserDto login(CredentialsDto credentialsDto) {

        UserDto userDto = loginByCredits(credentialsDto);

        userDto.setToken(userAuthenticationProvider.createToken(userDto.getEmail()));

        return userDto;

    }
    public UserDto loginByCredits(CredentialsDto credentialsDto) {
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("UNKNOWN USER", HttpStatus.NOT_FOUND));



        if (user.getStatus() == Status.INACTIVE) {
            throw new AppException("Your status has been set to Inactive please contact your admin", HttpStatus.PAYMENT_REQUIRED);
        }

        if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setUserName(user.getUserName());
            userDto.setEmail(user.getEmail());
            userDto.setStatus(user.getStatus());
            return userDto;
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }


    public UserDto callSaveDetailsOfUsers(UserDto userDto) {

        User user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setStatus(Status.NEW);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return userDto;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}