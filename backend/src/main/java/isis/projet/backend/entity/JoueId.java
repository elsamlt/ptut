package isis.projet.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class JoueId implements Serializable {
    private String idFilm;
    private Long participantId;
}