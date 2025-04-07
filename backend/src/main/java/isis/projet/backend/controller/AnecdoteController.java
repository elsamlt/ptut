package isis.projet.backend.controller;

import isis.projet.backend.dao.AnecdoteRepository;
import isis.projet.backend.dao.FilmRepository;
import isis.projet.backend.dao.ParticipantRepository;
import isis.projet.backend.dto.AnecdoteDTO;
import isis.projet.backend.entity.Anecdote;
import isis.projet.backend.entity.Film;
import isis.projet.backend.entity.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/anecdotes")
@CrossOrigin("*")
public class AnecdoteController {

    @Autowired
    private AnecdoteRepository anecdoteRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @GetMapping
    public ResponseEntity<List<AnecdoteDTO>> getAllAnecdotes() {
        try {
            // Récupérer toutes les anecdotes depuis le repository
            List<Anecdote> anecdotes = anecdoteRepository.findAll();

            // Transformer chaque Anecdote en AnecdoteDTO
            List<AnecdoteDTO> anecdoteDTOs = anecdotes.stream()
                    .map(anecdote -> {
                        AnecdoteDTO dto = new AnecdoteDTO();
                        dto.setDescription(anecdote.getDescription());
                        dto.setIdAnecdote(anecdote.getIdAnecdote());
                        dto.setId_film(anecdote.getFilm().getIdFilm());  // Récupérer l'ID du film lié à l'anecdote
                        dto.setId_participant(anecdote.getParticipant().getIdParticipant());  // Récupérer l'ID du participant
                        return dto;
                    })
                    .collect(Collectors.toList());

            // Retourner la liste des DTOs avec un code HTTP 200 OK
            return ResponseEntity.ok(anecdoteDTOs);

        } catch (Exception e) {
            // En cas d'erreur, retourner une réponse avec un code HTTP 400 Bad Request
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> addAnecdote(@RequestBody AnecdoteDTO anecdoteDTO) {
        try {
            // Créer une nouvelle anecdote
            Anecdote anecdote = new Anecdote();
            anecdote.setDescription(anecdoteDTO.getDescription());

            // Récupérer le film et le participant par leurs IDs
            Film film = filmRepository.findById(anecdoteDTO.getId_film())
                    .orElseThrow(() -> new RuntimeException("Film non trouvé"));

            Participant participant = participantRepository.findById(anecdoteDTO.getId_participant())
                    .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

            // Assigner les objets récupérés
            anecdote.setFilm(film);
            anecdote.setParticipant(participant);

            // Sauvegarder l'anecdote
            Anecdote savedAnecdote = anecdoteRepository.save(anecdote);


            return ResponseEntity.ok(savedAnecdote);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout de l'anecdote: " + e.getMessage());
        }
    }
}
