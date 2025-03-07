package isis.projet.backend.dao;
import isis.projet.backend.entity.Actualite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActualiteRepository extends JpaRepository<Actualite, Integer> {
}
