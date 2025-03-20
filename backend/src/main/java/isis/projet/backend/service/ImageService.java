package isis.projet.backend.service;

import isis.projet.backend.entity.Image;
import isis.projet.backend.dao.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getImagesByFilmId(int idFilm) {
        return imageRepository.findByFilm_IdFilm(idFilm);
    }
}
