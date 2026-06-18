package com.secureauth.onboarding.service;

import com.secureauth.onboarding.dto.RegistrationRequestDTO;
import com.secureauth.onboarding.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public UserResponseDTO register(RegistrationRequestDTO registrationRequestDTO) {
        return new UserResponseDTO(registrationRequestDTO.getUsername(), registrationRequestDTO.getEmail());
    }
}
