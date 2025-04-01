package isis.projet.backend.dto;

import lombok.Data;

@Data
public class AnecdoteDTO {
    private String description;
    private int id_film;
    private int id_participant;
}