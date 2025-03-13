package isis.projet.backend.controller;

import isis.projet.backend.entity.Commentaire;
import isis.projet.backend.service.CommentaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    /**
     * Récupère tous les commentaires associés à un film donné.
     *
     * @param idFilm Identifiant du film.
     * @return Liste de commentaires pour le film spécifié.
     */
    @GetMapping("/film/{idFilm}")
    public ResponseEntity<List<Commentaire>> getCommentairesByFilmId(@PathVariable Integer idFilm) {
        List<Commentaire> commentaires = commentaireService.getCommentairesByFilmId(idFilm);
        return ResponseEntity.ok(commentaires);
    }
}
