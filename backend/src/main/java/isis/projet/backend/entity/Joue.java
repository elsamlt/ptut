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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clé primaire auto-générée
    private Long idJoue;

    private String role;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false) // Relier à Film
    private Film film;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false) // Relier à Participant
    private Participant participant;

    // Getters et setters
}
