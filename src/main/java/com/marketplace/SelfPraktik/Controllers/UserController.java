package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.DTO.User.User;
import com.marketplace.SelfPraktik.DTO.User.UserUpdate;
import com.marketplace.SelfPraktik.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Called method getAllUsers");

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        log.info("Called method getUserById with id={}", id);

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserUpdate updateDTO
    ) {
        log.info("Called method updateUser with id={}", id);

        return ResponseEntity.ok(userService.updateUser(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.info("Called method deleteUser with id={}", id);

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
