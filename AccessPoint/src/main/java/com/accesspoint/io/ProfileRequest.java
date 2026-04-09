package com.accesspoint.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {

    @NotBlank(message = "Name should not be empty")
    private String name;
    @Email(message = "Enter valid Email Address")
    @NotBlank(message = "Email should not be empty")
    private String email;
    @Size(min = 6, max = 8, message = "Password must be between 6 to 8 characters")
    private String password;
}
