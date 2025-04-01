package uz.nanotechsolutions.lexon.payload.request;

import lombok.Data;

@Data
public class PatientRequest {
    private String firstName;
    private String lastName;
    private Integer age;
    private String phone;
    private Integer doctorId;
}
