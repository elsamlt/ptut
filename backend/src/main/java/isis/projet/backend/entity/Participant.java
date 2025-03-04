package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String urlPhoto;

    /*@ManyToMany(mappedBy = "participants")
    private List<Film> films;*/

    // Getters et setters
}
