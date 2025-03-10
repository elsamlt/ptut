package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "anecdotes")
public class Anecdote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnecdote;
    private String description;

    @ManyToOne
    @JoinColumn(name = "idFilm")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "idParticipant")
    private Participant participant;

    // Getters et setters
}
