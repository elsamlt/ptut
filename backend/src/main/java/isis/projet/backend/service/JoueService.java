package isis.projet.backend.service;

import isis.projet.backend.entity.Joue;
import isis.projet.backend.dao.JoueRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JoueService {
    private final JoueRepository joueRepository;

    public JoueService(JoueRepository joueRepository) {
        this.joueRepository = joueRepository;
    }

    // Récupérer les participants d'un film
    public List<String> getParticipantsByFilm(int idFilm) {
        // Utilise la nouvelle méthode findJouesByFilmId
        List<Joue> relations = joueRepository.findJouesByFilmId(idFilm);
        return relations.stream()
                .map(joue -> joue.getParticipant().getNom() + " " + joue.getParticipant().getPrenom() + " - Rôle: " + joue.getRole())
                .collect(Collectors.toList());
    }
}