package uz.nanotechsolutions.lexon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.nanotechsolutions.lexon.payload.request.RegisterRequest;
import uz.nanotechsolutions.lexon.service.AuthService;

import static uz.nanotechsolutions.lexon.entity.enums.Role.*;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class LexonApplication {

    public static void main(String[] args) {
        SpringApplication.run(LexonApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthService authService) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password-admin")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + authService.register(admin).getAccessToken());

            var manager = RegisterRequest.builder()
                    .firstname("Manager")
                    .lastname("Manager")
                    .email("manager@mail.com")
                    .password("password-manager")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + authService.register(manager).getAccessToken());

            var receptionist = RegisterRequest.builder()
                    .firstname("Receptionist")
                    .lastname("Receptionist")
                    .email("receptionist@mail.com")
                    .password("password-Receptionist")
                    .role(RECEPTIONIST)
                    .build();
            System.out.println("Receptionist token: " + authService.register(receptionist).getAccessToken());

            var doctor = RegisterRequest.builder()
                    .firstname("Doctor")
                    .lastname("Doctor")
                    .email("doctor@mail.com")
                    .password("password-doctor")
                    .role(DOCTOR)
                    .build();
            System.out.println("Doctor token: " + authService.register(doctor).getAccessToken());
        };
    }

}
