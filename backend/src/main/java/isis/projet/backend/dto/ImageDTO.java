package isis.projet.backend.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private String img;   // Le chemin ou l'URL de l'image
    private int id_film;   // ID du film auquel l'image est associée

    // Getters et setters
}

