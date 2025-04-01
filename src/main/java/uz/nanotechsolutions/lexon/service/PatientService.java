package uz.nanotechsolutions.lexon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nanotechsolutions.lexon.entity.User;
import uz.nanotechsolutions.lexon.entity.enums.Role;
import uz.nanotechsolutions.lexon.payload.request.PatientRequest;
import uz.nanotechsolutions.lexon.payload.response.UserResponse;
import uz.nanotechsolutions.lexon.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final UserRepository userRepository;

    public UserResponse registerPatient(PatientRequest patientRequest){
        //1. check patient already registered or not


        Optional<User> existingDoctor = userRepository.findDoctorById(patientRequest.getDoctorId());
        if (existingDoctor.isEmpty()){
            return new UserResponse(404, "Doctor not found");
        }

        User patient = new User();
        patient.setFirstname(patientRequest.getFirstName());
        patient.setLastname(patientRequest.getLastName());
        patient.setPhone(patientRequest.getPhone());
        patient.setAge(patientRequest.getAge());
        patient.setRole(Role.PATIENT);
        patient.setCreatedAt(LocalDate.now());
        patient.setDoctor(existingDoctor.get());

        userRepository.save(patient);
        return new UserResponse(201,"Patient created successfully");
    }

    public UserResponse getAllPatients(){
        List<User> allPatients = userRepository.findAllByRole(String.valueOf(Role.PATIENT));
        if (allPatients.isEmpty()){
            return new UserResponse(204, "No patient found");
        }

        return new UserResponse(200,"Completed successfully", allPatients);
    }

    public UserResponse getMyPatients(Integer doctorId) {
        List<User> patientsByDoctorId = userRepository.findPatientsByDoctorId(doctorId);

        if (patientsByDoctorId.isEmpty()){
            return new UserResponse(204, "Your patient not found");
        }

        return new UserResponse(200,"Completed successfully", patientsByDoctorId);
    }
}
