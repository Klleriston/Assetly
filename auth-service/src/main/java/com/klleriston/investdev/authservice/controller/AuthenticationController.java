package com.klleriston.investdev.authservice.controller;

import com.klleriston.investdev.authservice.config.security.TokenService;
import com.klleriston.investdev.authservice.dto.ResponseLogin;
import com.klleriston.investdev.authservice.dto.ResponseRegister;
import com.klleriston.investdev.authservice.dto.UserDTO;
import com.klleriston.investdev.authservice.model.User;
import com.klleriston.investdev.authservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated UserDTO userDTO) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new ResponseLogin(token));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated UserDTO userDTO) {
        if (this.userRepository.findByEmail(userDTO.email()).isPresent()) return ResponseEntity.badRequest().build();

        String encryptPass = new BCryptPasswordEncoder().encode(userDTO.password());
        User newUser = new User(userDTO.email(), encryptPass);

        this.userRepository.save(newUser);
        return ResponseEntity.ok(new ResponseRegister("Usuário registrado com sucesso!"));
    }
}
