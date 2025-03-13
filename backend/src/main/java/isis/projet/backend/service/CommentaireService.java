package isis.projet.backend.service;

import isis.projet.backend.dao.CommentaireRepository;
import isis.projet.backend.dao.FilmRepository;
import isis.projet.backend.entity.Commentaire;
import isis.projet.backend.entity.Film;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final FilmRepository filmRepository;

    public CommentaireService(CommentaireRepository commentaireRepository, FilmRepository filmRepository) {
        this.commentaireRepository = commentaireRepository;
        this.filmRepository = filmRepository;
    }

    /**
     * Récupère tous les commentaires d'un film donné.
     *
     * @param idFilm L'identifiant du film pour lequel on souhaite récupérer les commentaires.
     * @return Liste de commentaires associés au film.
     */
    public List<Commentaire> getCommentairesByFilmId(Integer idFilm) {
        // Récupérer le film par son id
        Film film = filmRepository.findById(idFilm).orElseThrow(() -> new RuntimeException("Film non trouvé"));

        // Récupérer la liste des commentaires associés au film
        return commentaireRepository.findByFilm_IdFilm(idFilm);
    }
}
