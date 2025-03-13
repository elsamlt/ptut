package isis.projet.backend.service;

import isis.projet.backend.entity.Actualite;
import isis.projet.backend.dao.ActualiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActualiteService {

    private final ActualiteRepository actualiteRepository;
    private static final int MAX_ACTUALITES = 3;

    @Autowired
    public ActualiteService(ActualiteRepository actualiteRepository) {
        this.actualiteRepository = actualiteRepository;
    }

    @Transactional
    public Actualite addActualite(Actualite actualite) {
        // Sauvegarder la nouvelle actualité
        Actualite savedActualite = actualiteRepository.save(actualite);

        // Vérifier le nombre d'actualités
        List<Actualite> allActualites = actualiteRepository.findAll(Sort.by(Sort.Direction.ASC, "idActu"));

        // Si plus de MAX_ACTUALITES, supprimer la plus ancienne
        if (allActualites.size() > MAX_ACTUALITES) {
            // La plus ancienne est la première dans la liste (triée par idActu ASC)
            Actualite oldest = allActualites.get(0);
            actualiteRepository.delete(oldest);
        }

        return savedActualite;
    }

    public List<Actualite> getAllActualites() {
        return actualiteRepository.findAll(Sort.by(Sort.Direction.DESC, "idActu"));
    }
}