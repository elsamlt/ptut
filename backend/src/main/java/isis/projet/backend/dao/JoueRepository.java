package isis.projet.backend.dao;

import isis.projet.backend.entity.Joue;
import isis.projet.backend.entity.JoueId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoueRepository extends JpaRepository<Joue, JoueId> {
    @Query("SELECT j FROM Joue j WHERE j.film.idFilm = :filmId")
    List<Joue> findJouesByFilmId(@Param("filmId") int filmId);

    @Query("SELECT j FROM Joue j WHERE j.film.idFilm = :filmId")
    Page<Joue> findJouesByFilmIdPaginated(@Param("filmId") int filmId, Pageable pageable);
}