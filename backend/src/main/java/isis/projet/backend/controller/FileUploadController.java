package isis.projet.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    // Dossier où stocker les images (dans "public/img")
    private static final String UPLOAD_DIR = "public/img/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aucun fichier sélectionné.");
        }

        try {
            // Crée le dossier "public/img" s'il n'existe pas
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Génére un nom de fichier unique
            String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Sauvegarde le fichier sur le serveur
            file.transferTo(filePath);

            // Retourne l'URL d'accès au fichier
            String fileUrl = "/img/" + fileName;
            return ResponseEntity.status(HttpStatus.OK).body("{\"fileUrl\":\"" + fileUrl + "\"}");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload du fichier.");
        }
    }
}
