package isis.projet.backend.controller;

import isis.projet.backend.entity.Image;
import isis.projet.backend.service.ImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin("*") // Permet d'autoriser les requêtes depuis un frontend (ex: Angular, React)
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/film/{idFilm}")
    public List<Image> getImagesByFilm(@PathVariable int idFilm) {
        return imageService.getImagesByFilmId(idFilm);
    }
}
