package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "moments")
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMoment;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String nomImg;

    @Column(nullable = false, length = 1000) // Pour permettre une description plus longue
    private String description;

    // Getters et setters sont générés par Lombok
}
