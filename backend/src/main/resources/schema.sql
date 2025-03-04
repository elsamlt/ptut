-- Création de la table Films
CREATE TABLE IF NOT EXISTS Films (
                                     idFilm INT PRIMARY KEY AUTO_INCREMENT,
                                     titre VARCHAR(255) NOT NULL,
                                     synopsis TEXT,
                                     genre VARCHAR(50),
                                     annee INT,
                                     duree VARCHAR(10),
                                     affiche VARCHAR(255),
                                     urlFilm VARCHAR(500),
                                     urlBA VARCHAR(500)
);

-- Création de la table Participants
CREATE TABLE IF NOT EXISTS Participants (
                                            idParticipant INT PRIMARY KEY AUTO_INCREMENT,
                                            nom VARCHAR(100) NOT NULL,
                                            prenom VARCHAR(100) NOT NULL,
                                            ppdp VARCHAR(255)
);

-- Création de la table Joue (relation entre Participants et Films)
CREATE TABLE IF NOT EXISTS Joue (
                                    idParticipant INT,
                                    idFilm INT,
                                    libelle VARCHAR(100),
                                    PRIMARY KEY (idParticipant, idFilm),
                                    FOREIGN KEY (idParticipant) REFERENCES Participants(idParticipant) ON DELETE CASCADE,
                                    FOREIGN KEY (idFilm) REFERENCES Films(idFilm) ON DELETE CASCADE
);

-- Création de la table Commentaires
CREATE TABLE IF NOT EXISTS Commentaires (
                                            idCommentaire INT PRIMARY KEY AUTO_INCREMENT,
                                            description TEXT NOT NULL,
                                            idFilm INT,
                                            FOREIGN KEY (idFilm) REFERENCES Films(idFilm) ON DELETE CASCADE
);

-- Création de la table Images
CREATE TABLE IF NOT EXISTS Images (
                                      idImage INT PRIMARY KEY AUTO_INCREMENT,
                                      img VARCHAR(255) NOT NULL,
                                      idFilm INT,
                                      FOREIGN KEY (idFilm) REFERENCES Films(idFilm) ON DELETE CASCADE
);

-- Création de la table Administrateurs
CREATE TABLE IF NOT EXISTS Administrateurs (
                                               idAdmin INT PRIMARY KEY AUTO_INCREMENT,
                                               email VARCHAR(255) NOT NULL UNIQUE,
                                               password VARCHAR(255) NOT NULL
);

-- Création de la table Actualites
CREATE TABLE IF NOT EXISTS Actualites (
                                          idActu INT PRIMARY KEY AUTO_INCREMENT,
                                          lien VARCHAR(500) NOT NULL
);

-- Création de la table Moments
CREATE TABLE IF NOT EXISTS Moments (
                                       idMoment INT PRIMARY KEY AUTO_INCREMENT,
                                       titre VARCHAR(255) NOT NULL,
                                       nomImage VARCHAR(255),
                                       description TEXT
);

-- Création de la table Anecdotes
CREATE TABLE IF NOT EXISTS Anecdotes (
                                         idAnecdote INT PRIMARY KEY AUTO_INCREMENT,
                                         description TEXT NOT NULL,
                                         idFilm INT,
                                         idParticipant INT,
                                         FOREIGN KEY (idFilm) REFERENCES Films(idFilm) ON DELETE CASCADE,
                                         FOREIGN KEY (idParticipant) REFERENCES Participants(idParticipant) ON DELETE CASCADE
);
