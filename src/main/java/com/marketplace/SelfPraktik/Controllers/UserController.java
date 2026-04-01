package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.DTO.User.User;
import com.marketplace.SelfPraktik.DTO.User.UserUpdate;
import com.marketplace.SelfPraktik.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Called method getAllUsers");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Called method getUserById with id={}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Called method getProfile");
        String email = userDetails.getUsername();
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdate updateDTO
    ) {
        log.info("Called method updateUser with id={}", id);
        return ResponseEntity.ok(userService.updateUser(id, updateDTO));
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUserRole(
            @PathVariable Long id
    ) {
        log.info("Called updateUserRole for user id: {}", id);

        return ResponseEntity.ok(userService.updateUserRole(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Called method deleteUser with id={}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}