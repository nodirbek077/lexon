package uz.nanotechsolutions.lexon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nanotechsolutions.lexon.payload.request.ChangePasswordRequest;
import uz.nanotechsolutions.lexon.payload.request.UserRequest;
import uz.nanotechsolutions.lexon.payload.response.UserResponse;
import uz.nanotechsolutions.lexon.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management part")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin:update')")
    @PatchMapping
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        UserResponse userResponse = userService.changePassword(request, connectedUser);
        HttpStatus status;
        if (userResponse.getErrorCode() == 0) {
            status = HttpStatus.OK;
        } else if (userResponse.getErrorCode() == 409 || userResponse.getErrorCode() == 2) {
            status = HttpStatus.CONFLICT;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.create(userRequest);
        HttpStatus status;
        if (userResponse.getErrorCode() == 0) {
            status = HttpStatus.CREATED;
        } else if (userResponse.getErrorCode() == 409) {
            status = HttpStatus.CONFLICT;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        UserResponse userResponse = userService.getUserById(id);
        HttpStatus status;
        if (userResponse.getErrorCode() == 0) {
            status = HttpStatus.OK;
        } else if (userResponse.getErrorCode() == 404) {
            status = HttpStatus.NOT_FOUND;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping()
    public ResponseEntity<?> getAll() {
        UserResponse userResponse = userService.getAll();
        HttpStatus status;
        if (userResponse.getErrorCode() == 0) {
            status = HttpStatus.OK;
        } else if (userResponse.getErrorCode() == 204) {
            status = HttpStatus.NO_CONTENT;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        UserResponse userResponse = userService.deleteUserById(id);
        HttpStatus status;
        if (userResponse.getErrorCode() == 0) {
            status = HttpStatus.OK;
        }else if (userResponse.getErrorCode() == 204) {
            status = HttpStatus.NO_CONTENT;
        }else if (userResponse.getErrorCode() == 404) {
            status = HttpStatus.NOT_FOUND;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.update(userRequest, id);
        HttpStatus status;
        if (userResponse.getErrorCode() == 0) {
            status = HttpStatus.OK;
        }else if (userResponse.getErrorCode() == 404) {
            status = HttpStatus.NOT_FOUND;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }
}
