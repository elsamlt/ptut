package isis.projet.backend.controller;

import isis.projet.backend.dao.CommentaireRepository;
import isis.projet.backend.dao.FilmRepository;
import isis.projet.backend.dto.CommentaireDTO;
import isis.projet.backend.entity.Commentaire;
import isis.projet.backend.entity.Film;
import isis.projet.backend.service.CommentaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;
    private final CommentaireRepository commentaireRepository;
    private final FilmRepository filmRepository;

    // Injecter tous les repositories nécessaires dans le constructeur
    public CommentaireController(CommentaireService commentaireService,
                                 CommentaireRepository commentaireRepository,
                                 FilmRepository filmRepository) {
        this.commentaireService = commentaireService;
        this.commentaireRepository = commentaireRepository;
        this.filmRepository = filmRepository;
    }

    @GetMapping
    public ResponseEntity<List<CommentaireDTO>> getAllCommentaires() {
        try {
            List<Commentaire> commentaires = commentaireRepository.findAll();
            List<CommentaireDTO> commentaireDTOs = commentaires.stream()
                    .map(commentaire -> {
                        CommentaireDTO dto = new CommentaireDTO();
                        dto.setDescription(commentaire.getDescription());
                        dto.setId_film(commentaire.getFilm().getIdFilm());
                        return dto;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(commentaireDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/film/{idFilm}")
    public ResponseEntity<List<Commentaire>> getCommentairesByFilmId(@PathVariable Integer idFilm) {
        List<Commentaire> commentaires = commentaireService.getCommentairesByFilmId(idFilm);
        return ResponseEntity.ok(commentaires);
    }

    @PostMapping
    public ResponseEntity<?> addCommentaire(@RequestBody CommentaireDTO commentaireDTO) {
        try {
            Film film = filmRepository.findById(commentaireDTO.getId_film())
                    .orElseThrow(() -> new RuntimeException("Film non trouvé"));

            Commentaire commentaire = new Commentaire();
            commentaire.setDescription(commentaireDTO.getDescription());
            commentaire.setFilm(film);

            Commentaire savedCommentaire = commentaireRepository.save(commentaire);
            return ResponseEntity.ok(savedCommentaire);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout du commentaire: " + e.getMessage());
        }
    }
}
