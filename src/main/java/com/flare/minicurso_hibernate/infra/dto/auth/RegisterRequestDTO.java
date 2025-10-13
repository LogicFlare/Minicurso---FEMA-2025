package com.flare.minicurso_hibernate.infra.dto.auth;

import com.flare.minicurso_hibernate.infra.model.Usuario;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;
    private Usuario.Role role;
}

