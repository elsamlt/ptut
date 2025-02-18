package isis.projet.backend.dao;
import isis.projet.backend.entity.Anecdote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnecdoteRepository extends JpaRepository<Anecdote, Integer> {
}
