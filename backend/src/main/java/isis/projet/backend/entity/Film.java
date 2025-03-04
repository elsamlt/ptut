//package com.example.demo.model;
package isis.projet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFilm;
    private String titre;
    private String synopsis;
    private String genre;
    private int annee;
    private String duree;
    private String affiche;
    private String urlFilm;
    private String urlBA;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Anecdote> anecdotes;

    /*@ManyToMany
    @JoinTable(
            name = "film_participant",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> participants;*/

    // Getters et setters
}
