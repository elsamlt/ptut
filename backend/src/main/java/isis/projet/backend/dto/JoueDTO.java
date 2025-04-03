package isis.projet.backend.dto;

import isis.projet.backend.enums.Groupe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoueDTO {
    private int film_id;
    private int participant_id;
    private String role;
    private Groupe groupe;

    // Getters et setters
}
