package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idImage;
    private String nom;
    private String urlImage;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    // Getters et setters
}
