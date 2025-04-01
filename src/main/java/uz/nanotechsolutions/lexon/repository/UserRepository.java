package uz.nanotechsolutions.lexon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.nanotechsolutions.lexon.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE role = CAST(:role AS TEXT)", nativeQuery = true)
    List<User> findAllByRole(@Param("role") String role);

    @Query(value = "SELECT * FROM users WHERE role = 'PATIENT' AND doctor_id = :doctorId", nativeQuery = true)
    List<User> findPatientsByDoctorId(@Param("doctorId") Integer doctorId);

    @Query(value = "SELECT * FROM users WHERE role = 'DOCTOR' AND doctor_id = :doctorId", nativeQuery = true)
    Optional<User> findDoctorById(@Param("doctorId") Integer doctorId);
}
