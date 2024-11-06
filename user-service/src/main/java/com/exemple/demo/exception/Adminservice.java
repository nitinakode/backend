package com.exemple.demo.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Adminservice {
    private final UserRepository userRepository;
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
}
