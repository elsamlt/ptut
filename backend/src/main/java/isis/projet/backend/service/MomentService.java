package isis.projet.backend.service;

import isis.projet.backend.entity.Moment;
import isis.projet.backend.dao.MomentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MomentService {

    private final MomentRepository momentRepository;

    @Autowired
    public MomentService(MomentRepository momentRepository) {
        this.momentRepository = momentRepository;
    }

    @Transactional
    public Moment addMoment(Moment moment) {
        // Sauvegarder le nouveau moment
        Moment savedMoment = momentRepository.save(moment);

        // Vérifier le nombre de moments
        List<Moment> allMoments = momentRepository.findAll(Sort.by(Sort.Direction.ASC, "idMoment"));

        // S'il y en a plus d'un, supprimer l'ancien
        if (allMoments.size() > 1) {
            Moment oldest = allMoments.get(0); // Le plus ancien (premier de la liste triée)
            momentRepository.delete(oldest);
        }

        return savedMoment;
    }

    public List<Moment> getAllMoments() {
        return momentRepository.findAll(Sort.by(Sort.Direction.DESC, "idMoment"));
    }
}
