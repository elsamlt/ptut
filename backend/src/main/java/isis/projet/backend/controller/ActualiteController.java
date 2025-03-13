package isis.projet.backend.controller;

import isis.projet.backend.entity.Actualite;
import isis.projet.backend.service.ActualiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actualites")
public class ActualiteController {

    private final ActualiteService actualiteService;

    @Autowired
    public ActualiteController(ActualiteService actualiteService) {
        this.actualiteService = actualiteService;
    }

    @PostMapping
    public ResponseEntity<Actualite> addActualite(@RequestBody Actualite actualite) {
        Actualite saved = actualiteService.addActualite(actualite);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Actualite>> getAllActualites() {
        List<Actualite> actualites = actualiteService.getAllActualites();
        return new ResponseEntity<>(actualites, HttpStatus.OK);
    }
}