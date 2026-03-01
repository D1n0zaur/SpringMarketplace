package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.DTO.User.Auth.AuthRequest;
import com.marketplace.SelfPraktik.DTO.User.Auth.AuthResponse;
import com.marketplace.SelfPraktik.DTO.User.User;
import com.marketplace.SelfPraktik.DTO.User.UserCreate;
import com.marketplace.SelfPraktik.Security.JwtUtils;
import com.marketplace.SelfPraktik.Services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserCreate userCreate) {
        User registeredUser = authService.register(userCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}