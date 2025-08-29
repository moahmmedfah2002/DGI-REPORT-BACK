package com.example.back.dto;

import com.example.back.entity.Profile;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.security.Timestamp;
public record AuthDto(String username,String password,Profile profile) {

}
