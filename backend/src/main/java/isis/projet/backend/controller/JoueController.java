package isis.projet.backend.controller;

import isis.projet.backend.dao.FilmRepository;
import isis.projet.backend.dao.ParticipantRepository;
import isis.projet.backend.dto.JoueDTO;
import isis.projet.backend.entity.Film;
import isis.projet.backend.entity.Joue;
import isis.projet.backend.entity.Participant;
import isis.projet.backend.dao.JoueRepository;
import isis.projet.backend.enums.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/joues")
public class JoueController {

    @Autowired
    private JoueRepository joueRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @GetMapping("/films/participants")
    public Map<String, Object> getJouesByFilm(
            @RequestParam("idFilm") int idFilm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Joue> jouesPage = joueRepository.findJouesByFilmIdPaginated(idFilm, pageable);

        List<JoueDetailDTO> jouesDTO = jouesPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("joues", jouesDTO);
        response.put("page", Map.of(
                "size", jouesPage.getSize(),
                "totalElements", jouesPage.getTotalElements(),
                "totalPages", jouesPage.getTotalPages(),
                "number", jouesPage.getNumber()
        ));

        return response;
    }

    // Endpoint sans pagination
    /*@GetMapping("/api/films/participants/all")
    public List<JoueDetailDTO> getAllJouesByFilm(@RequestParam("idFilm") int idFilm) {
        List<Joue> joues = joueRepository.findJouesByFilmId(idFilm);
        return joues.stream().map(joue -> {
            JoueDetailDTO dto = new JoueDetailDTO();
            dto.setIdJoue(0);
            dto.setRole(joue.getRole());

            Participant p = joue.getParticipant();
            ParticipantDTO pdto = new ParticipantDTO();
            pdto.setIdParticipant(p.getIdParticipant());
            pdto.setNom(p.getNom());
            pdto.setPrenom(p.getPrenom());
            pdto.setPdp(p.getPdp());

            dto.setParticipant(pdto);
            return dto;
        }).collect(Collectors.toList());
    }*/
    @GetMapping("/films/participants/all")
    public List<JoueDetailDTO> getAllJouesByFilm(@RequestParam("idFilm") int idFilm) {
        List<Joue> joues = joueRepository.findJouesByFilmId(idFilm);
        return joues.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /*public static class JoueDetailDTO {
        private int idJoue;
        private String role;
        private ParticipantDTO participant;

        // Getters and setters
        public int getIdJoue() { return idJoue; }
        public void setIdJoue(int idJoue) { this.idJoue = idJoue; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public ParticipantDTO getParticipant() { return participant; }
        public void setParticipant(ParticipantDTO participant) { this.participant = participant; }
    }

    public static class ParticipantDTO {
        private int idParticipant;
        private String nom;
        private String prenom;
        private String pdp;

        // Getters and setters
        public int getIdParticipant() { return idParticipant; }
        public void setIdParticipant(int idParticipant) { this.idParticipant = idParticipant; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
        public String getPdp() { return pdp; }
        public void setPdp(String pdp) { this.pdp = pdp; }
    }

    @GetMapping("/api/groupes/participants/all")
    public List<JoueDetailDTO> getAllJouesByGroupe(@RequestParam("groupe") String groupeStr) {
        // Conversion en enum Groupe
        Groupe groupe = Groupe.valueOf(groupeStr);

        // Récupération des données
        List<Joue> joues = joueRepository.findJouesByGroupe(groupe);

        // Transformation en DTOs
        return joues.stream().map(joue -> {
            JoueDetailDTO dto = new JoueDetailDTO();
            dto.setIdJoue(joue.getIdJoue().intValue());
            dto.setRole(joue.getRole());

            Participant p = joue.getParticipant();
            ParticipantDTO pdto = new ParticipantDTO();
            pdto.setIdParticipant(p.getIdParticipant());
            pdto.setNom(p.getNom());
            pdto.setPrenom(p.getPrenom());
            pdto.setPdp(p.getPdp());

            dto.setParticipant(pdto);
            return dto;
        }).collect(Collectors.toList());
    }*/
    @GetMapping("/groupes/participants/all")
    public List<JoueDetailDTO> getAllJouesByGroupe(@RequestParam("groupe") String groupeStr) {
        Groupe groupe = Groupe.valueOf(groupeStr);  // Convertit String en Enum
        List<Joue> joues = joueRepository.findJouesByGroupe(groupe);
        return joues.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // ✅ Extraction d'une méthode de conversion pour éviter la duplication de code
    private JoueDetailDTO convertToDTO(Joue joue) {
        JoueDetailDTO dto = new JoueDetailDTO();
        dto.setIdJoue(joue.getIdJoue());
        dto.setRole(joue.getRole());
        dto.setGroupe(joue.getGroupe());  // Ajout du groupe si utile pour le frontend
        dto.setIdFilm(joue.getFilm().getIdFilm());  // Ajout de l'ID du film

        Participant p = joue.getParticipant();
        ParticipantDTO pdto = new ParticipantDTO();
        pdto.setIdParticipant(p.getIdParticipant());
        pdto.setNom(p.getNom());
        pdto.setPrenom(p.getPrenom());
        pdto.setPdp(p.getPdp());

        dto.setParticipant(pdto);
        return dto;
    }

    // ✅ Déplacement des DTOs vers des classes distinctes (bonne pratique)
    public static class JoueDetailDTO {
        private Long idJoue;
        private String role;
        private Groupe groupe;
        private int idFilm;
        private ParticipantDTO participant;

        // Getters et Setters
        public Long getIdJoue() { return idJoue; }
        public void setIdJoue(Long idJoue) { this.idJoue = idJoue; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public Groupe getGroupe() { return groupe; }
        public void setGroupe(Groupe groupe) { this.groupe = groupe; }
        public int getIdFilm() { return idFilm; }
        public void setIdFilm(int idFilm) { this.idFilm = idFilm; }
        public ParticipantDTO getParticipant() { return participant; }
        public void setParticipant(ParticipantDTO participant) { this.participant = participant; }
    }

    public static class ParticipantDTO {
        private int idParticipant;
        private String nom;
        private String prenom;
        private String pdp;

        // Getters et Setters
        public int getIdParticipant() { return idParticipant; }
        public void setIdParticipant(int idParticipant) { this.idParticipant = idParticipant; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
        public String getPdp() { return pdp; }
        public void setPdp(String pdp) { this.pdp = pdp; }
    }

    @PostMapping
    public ResponseEntity<?> addJoue(@RequestBody JoueDTO joueDTO) {
        try {
            System.out.println("DTO reçu: " + joueDTO.getFilm_id() + ", " + joueDTO.getParticipant_id());
            // Récupérer le film par son ID
            Film film = filmRepository.findById(joueDTO.getFilm_id())
                    .orElseThrow(() -> new RuntimeException("Film non trouvé"));

            // Récupérer le participant par son ID
            Participant participant = participantRepository.findById(joueDTO.getParticipant_id())
                    .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

            // Créer une nouvelle instance de Joue
            Joue joue = new Joue();
            joue.setRole(joueDTO.getRole());
            joue.setGroupe(joueDTO.getGroupe());
            joue.setFilm(film);
            joue.setParticipant(participant);

            if (joue.getFilm() == null || joue.getParticipant() == null) {
                throw new RuntimeException("Relations non définies correctement");
            }

            // Sauvegarder la relation dans la base de données
            Joue savedJoue = joueRepository.save(joue);

            return ResponseEntity.ok(savedJoue);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout de la relation Joue: " + e.getMessage());
        }
    }
}