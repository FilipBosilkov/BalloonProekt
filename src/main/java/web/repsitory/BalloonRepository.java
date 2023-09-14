package web.repsitory;

import web.model.Balloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalloonRepository extends JpaRepository<Balloon, Long> {
    List<Balloon> findAllByNameOrDescription(String name, String description);
    Optional<Balloon> findByName(String name);
    void deleteByName(String name);


}
