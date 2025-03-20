package isis.projet.backend.dao;
import isis.projet.backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByFilm_IdFilm(int idFilm);
}
