package com.hainguyen.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResetPassword {
    @NotBlank(message="secret_token not blank")
    private String secretToken;

    @NotBlank(message="password not blank")
    private String password;

    @NotBlank(message="confirm-password not blank")
    private String confirmPassword;
}
