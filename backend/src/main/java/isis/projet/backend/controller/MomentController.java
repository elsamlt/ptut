package isis.projet.backend.controller;

import isis.projet.backend.entity.Moment;
import isis.projet.backend.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moments")
public class MomentController {

    private final MomentService momentService;

    @Autowired
    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @PostMapping
    public ResponseEntity<Moment> addMoment(@RequestBody Moment moment) {
        Moment saved = momentService.addMoment(moment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Moment>> getAllMoments() {
        List<Moment> moments = momentService.getAllMoments();
        return new ResponseEntity<>(moments, HttpStatus.OK);
    }
}
