package acc.spring.springtdd.repository;

import acc.spring.springtdd.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDogRepository extends JpaRepository<Dog,Long> {
    Optional<Dog> findById(Long dogId);
}
