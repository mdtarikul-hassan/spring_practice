package com.accesspoint.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private String token;
}
