package isis.projet.backend.dao;
import isis.projet.backend.entity.Actualite;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

public interface ActualiteRepository extends JpaRepository<Actualite, Integer> {
}
