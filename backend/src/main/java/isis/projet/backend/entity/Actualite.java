package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "actualites")
public class Actualite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idActu;

    @Column(nullable = false)
    private String lien;

    // Getters et setters sont générés par Lombok
}
