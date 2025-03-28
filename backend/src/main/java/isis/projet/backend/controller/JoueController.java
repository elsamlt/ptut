package isis.projet.backend.controller;

import isis.projet.backend.entity.Joue;
import isis.projet.backend.entity.Participant;
import isis.projet.backend.dao.JoueRepository;
import isis.projet.backend.enums.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JoueController {

    @Autowired
    private JoueRepository joueRepository;

    @GetMapping("/api/films/participants")
    public Map<String, Object> getJouesByFilm(
            @RequestParam("idFilm") int idFilm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        // Créer un objet Pageable pour la pagination
        Pageable pageable = PageRequest.of(page, size);

        // Récupérer les données paginées
        Page<Joue> jouesPage = joueRepository.findJouesByFilmIdPaginated(idFilm, pageable);

        // Conversion en DTOs
        List<JoueDetailDTO> jouesDTO = jouesPage.getContent().stream()
                .map(joue -> {
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

        // Création de l'objet de réponse avec structure similaire à l'API participants
        Map<String, Object> response = new HashMap<>();

        // Ajouter le contenu directement (au lieu de l'encapsuler dans "content")
        for (int i = 0; i < jouesDTO.size(); i++) {
            response.put(String.valueOf(i), jouesDTO.get(i));
        }

        // Ajouter les informations de pagination dans un objet "page"
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("size", jouesPage.getSize());
        pageInfo.put("totalElements", jouesPage.getTotalElements());
        pageInfo.put("totalPages", jouesPage.getTotalPages());
        pageInfo.put("number", jouesPage.getNumber());

        response.put("page", pageInfo);

        return response;
    }

    // Endpoint sans pagination
    @GetMapping("/api/films/participants/all")
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
    }

    public static class JoueDetailDTO {
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
    }
}