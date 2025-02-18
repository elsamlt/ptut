package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString

@Entity
@Table(name = "commentaires")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCommentaire;
    private String description;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    // Getters et setters
}
