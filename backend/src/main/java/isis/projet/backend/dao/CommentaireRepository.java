package isis.projet.backend.dao;
import isis.projet.backend.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {

    // Récupérer les commentaires par ID de film
    List<Commentaire> findByFilm_IdFilm(Integer idFilm);
}