package com.ej.msuser.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {

    @NotBlank
    @Size(min = 8, max = 16)
    private String newPassword;
    @NotBlank
    @Size(min = 8, max = 16)
    private String confirmNewPassword;
}
