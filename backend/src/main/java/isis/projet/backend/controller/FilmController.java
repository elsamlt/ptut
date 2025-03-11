package isis.projet.backend.controller;

import isis.projet.backend.service.JoueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final JoueService joueService;

    public FilmController(JoueService joueService) {
        this.joueService = joueService;
    }

    // 🎭 Route pour récupérer les participants d’un film
    @GetMapping("/{idFilm}/participants")
    public ResponseEntity<List<String>> getParticipantsByFilm(@PathVariable int idFilm) {
        List<String> participants = joueService.getParticipantsByFilm(idFilm);
        return ResponseEntity.ok(participants);
    }
}