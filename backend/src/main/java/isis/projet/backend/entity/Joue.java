package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "joue")
public class Joue {
    @EmbeddedId
    private JoueId id;
    private String role;

    @ManyToOne
    @MapsId("idFilm")
    @JoinColumn(name = "idFilm")
    private Film film;

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    private Participant participant;

    // Getters et setters
}
