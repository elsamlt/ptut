package isis.projet.backend.controller;

import isis.projet.backend.dao.FilmRepository;
import isis.projet.backend.dao.ImageRepository;  // Importer l'ImageRepository
import isis.projet.backend.dto.ImageDTO;
import isis.projet.backend.entity.Film;
import isis.projet.backend.entity.Image;
import isis.projet.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
@CrossOrigin("*") // Permet d'autoriser les requêtes depuis un frontend (ex: Angular, React)
public class ImageController {

    private final ImageService imageService;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ImageRepository imageRepository;  // Injection de ImageRepository

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        try {
            List<Image> images = imageRepository.findAll();  // Récupérer toutes les images
            List<ImageDTO> imageDTOs = images.stream()  // Transformer chaque Image en ImageDTO
                    .map(image -> {
                        ImageDTO dto = new ImageDTO();
                        dto.setImg(image.getImg());
                        dto.setId_film(image.getFilm().getIdFilm());  // On suppose que Film est déjà associé à l'image
                        return dto;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(imageDTOs);  // Retourner la liste des DTOs
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/film/{idFilm}")
    public List<Image> getImagesByFilm(@PathVariable int idFilm) {
        return imageService.getImagesByFilmId(idFilm);
    }

    @PostMapping
    public ResponseEntity<?> addImage(@RequestBody ImageDTO imageDTO) {
        try {
            // Récupérer le film par son ID
            Film film = filmRepository.findById(imageDTO.getId_film())
                    .orElseThrow(() -> new RuntimeException("Film non trouvé"));

            // Créer une nouvelle image
            Image image = new Image();
            image.setImg(imageDTO.getImg());

            // Assigner le film à l'image
            image.setFilm(film);

            // Sauvegarder l'image
            Image savedImage = imageRepository.save(image);

            return ResponseEntity.ok(savedImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout de l'image: " + e.getMessage());
        }
    }
}
