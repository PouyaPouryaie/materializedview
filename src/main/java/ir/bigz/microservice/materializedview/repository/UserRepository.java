package ir.bigz.microservice.materializedview.repository;

import ir.bigz.microservice.materializedview.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
