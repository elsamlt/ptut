package isis.projet.backend.dao;
import isis.projet.backend.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
}
