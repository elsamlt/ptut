package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString

@Entity
@Table(name = "anecdotes")
public class Anecdote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnecdote;
    private String description;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    // Getters et setters
}
