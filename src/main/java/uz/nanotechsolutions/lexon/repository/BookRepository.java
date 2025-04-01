package uz.nanotechsolutions.lexon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nanotechsolutions.lexon.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
