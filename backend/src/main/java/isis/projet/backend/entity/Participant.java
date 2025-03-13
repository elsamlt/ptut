package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipant;
    private String nom;
    private String prenom;
    private String pdp;

    @OneToMany(mappedBy = "participant")
    private List<Joue> filmsJoues;

    // Getters et setters
}
