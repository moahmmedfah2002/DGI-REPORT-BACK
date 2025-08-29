package com.example.back.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public record AuthToken(String valid) {
}
