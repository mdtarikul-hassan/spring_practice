package com.accesspoint.io;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    @NotBlank(message = "password should not be empty!")
    private String newPassword;

    @NotBlank(message = "email should not be empty!")
    private String email;

    @NotBlank(message = "otp should not be empty!")
    private String otp;
}
