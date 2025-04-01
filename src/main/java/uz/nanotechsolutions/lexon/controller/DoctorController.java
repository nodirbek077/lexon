package uz.nanotechsolutions.lexon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nanotechsolutions.lexon.payload.response.UserResponse;
import uz.nanotechsolutions.lexon.service.PatientService;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Doctor", description = "Doctor can see his current patients, write some medicine and update status to the patient")
public class DoctorController {
    private final PatientService patientService;

    @PreAuthorize("hasAuthority('doctor:read')")
    @GetMapping()
    public ResponseEntity<?> getMyPatients(@RequestParam Integer doctorId) {
        UserResponse userResponse = patientService.getMyPatients(doctorId);
        HttpStatus status;
        if (userResponse.getErrorCode() == 200) {
            status = HttpStatus.OK;
        } else if (userResponse.getErrorCode() == 204) {
            status = HttpStatus.NO_CONTENT;
        }else
            status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(userResponse);
    }
}
