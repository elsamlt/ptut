package isis.projet.backend.dao;
import isis.projet.backend.entity.Moment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MomentRepository extends JpaRepository<Moment, Integer> {
}
