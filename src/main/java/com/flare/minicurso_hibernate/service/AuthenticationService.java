package com.flare.minicurso_hibernate.service;

import com.flare.minicurso_hibernate.config.security.JwtService;
import com.flare.minicurso_hibernate.infra.dto.auth.LoginRequestDTO;
import com.flare.minicurso_hibernate.infra.dto.auth.LoginResponseDTO;
import com.flare.minicurso_hibernate.infra.dto.auth.RegisterRequestDTO;
import com.flare.minicurso_hibernate.infra.model.Usuario;
import com.flare.minicurso_hibernate.infra.repository.UsuarioRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final Counter loginSuccessCounter;
    private final Counter loginFailureCounter;
    private final Counter registerCounter;

    public AuthenticationService(MeterRegistry meterRegistry) {
        this.loginSuccessCounter = Counter.builder("auth.login.success")
                .description("Numero de logins bem-sucedidos")
                .register(meterRegistry);

        this.loginFailureCounter = Counter.builder("auth.login.failure")
                .description("Numero de logins falhados")
                .register(meterRegistry);

        this.registerCounter = Counter.builder("auth.register")
                .description("Numero de registros")
                .register(meterRegistry);
    }

    public LoginResponseDTO register(RegisterRequestDTO request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Usuario ja existe");
        }

        var usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Usuario.Role.USER)
                .enabled(true)
                .build();

        usuarioRepository.save(usuario);
        registerCounter.increment();

        var jwtToken = jwtService.generateToken(usuario);

        return LoginResponseDTO.builder()
                .token(jwtToken)
                .username(usuario.getUsername())
                .role(usuario.getRole().name())
                .build();
    }

    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            var usuario = usuarioRepository.findByUsername(request.getUsername())
                    .orElseThrow();

            var jwtToken = jwtService.generateToken(usuario);

            loginSuccessCounter.increment();

            return LoginResponseDTO.builder()
                    .token(jwtToken)
                    .username(usuario.getUsername())
                    .role(usuario.getRole().name())
                    .build();
        } catch (Exception e) {
            loginFailureCounter.increment();
            throw e;
        }
    }
}

