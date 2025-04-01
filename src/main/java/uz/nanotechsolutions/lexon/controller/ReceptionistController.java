package uz.nanotechsolutions.lexon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nanotechsolutions.lexon.payload.request.PatientRequest;
import uz.nanotechsolutions.lexon.payload.response.UserResponse;
import uz.nanotechsolutions.lexon.service.PatientService;

@RestController
@RequestMapping("/api/v1/receptionist")
@RequiredArgsConstructor
@Tag(name = "Receptionist", description = "Receptionist can read, add, update patient")
@PreAuthorize("hasRole('RECEPTIONIST')")
public class ReceptionistController {
    private final PatientService patientService;

    @PreAuthorize("hasAuthority('receptionist:create')")
    @PostMapping("/register-patient")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRequest patientRequest) {
        UserResponse userResponse = patientService.registerPatient(patientRequest);
        HttpStatus status;
        if (userResponse.getErrorCode() == 201) {
            status = HttpStatus.CREATED;
        } else if (userResponse.getErrorCode() == 404) {
            status = HttpStatus.NOT_FOUND;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }

    @PreAuthorize("hasAuthority('receptionist:read')")
    @GetMapping("/patients")
    public ResponseEntity<?> getAllPatients() {
        UserResponse userResponse = patientService.getAllPatients();
        HttpStatus status;
        if (userResponse.getErrorCode() == 200) {
            status = HttpStatus.OK;
        } else if (userResponse.getErrorCode() == 204) {
            status = HttpStatus.NO_CONTENT;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }

    //TODO: need to update and delete patient
}
