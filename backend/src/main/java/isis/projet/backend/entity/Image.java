package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idImage;
    private String nom;
    private String img;

    @ManyToOne
    @JoinColumn(name = "idFilm")
    private Film film;

    // Getters et setters
}
