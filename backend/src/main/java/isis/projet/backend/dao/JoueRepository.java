package isis.projet.backend.dao;
import isis.projet.backend.entity.Joue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoueRepository extends JpaRepository<Joue, Integer> {
    List<Joue> findByFilm_IdFilm(int idFilm);
}
