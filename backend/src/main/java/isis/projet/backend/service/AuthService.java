package isis.projet.backend.service;

import isis.projet.backend.dao.AdminRepository;
import isis.projet.backend.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin authenticate(String email, String password) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        System.out.println("Tentative de connexion pour l'email : " + email);

        if (optionalAdmin.isPresent()) {
            System.out.println("Utilisateur trouvé en base de données");
            Admin admin = optionalAdmin.get();
            // Débogage de la vérification de mot de passe
            boolean passwordMatches = passwordEncoder.matches(password, admin.getPassword());
            System.out.println("Résultat de la vérification du mot de passe : " + passwordMatches);

            if (passwordMatches) {
                return admin;
            }
        } else {
            System.out.println("Aucun utilisateur trouvé avec cet email");
        }

        return null; // Authentification échouée
    }
}