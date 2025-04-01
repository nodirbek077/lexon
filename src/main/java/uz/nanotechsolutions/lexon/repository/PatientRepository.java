package uz.nanotechsolutions.lexon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nanotechsolutions.lexon.entity.User;

public interface PatientRepository extends JpaRepository<User,Integer> {

}
