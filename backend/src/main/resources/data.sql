INSERT INTO Films (titre, synopsis, genre, annee, duree, affiche, url_film, urlBA)
VALUES
    ('Sketch FIE4 Promo 2022', 'Un sketch réalisé par la promo FIE4 2022', 'Comédie', 2021, '15min08', NULL, '', NULL),
    ('The Hospital 1', 'Premier volet de The Hospital', 'Comédie', 2023, '13min54', NULL, '', NULL),
    ('The Hospital 3', 'Troisième volet de The Hospital', 'Comédie', 2024, '14min28', '/img/thehospital3.jpg', '', NULL),
    ('The Hospital 4', 'Quatrième volet de The Hospital', 'Comédie', 2025, '15min08', '/img/thehospital4.jpg','', NULL);

INSERT INTO Participants (nom, prenom, pdp) VALUES
                                                ('DUPUY', 'Emma', NULL),
                                                ('SALVAN', 'Emma', NULL),
                                                ('DON', 'Marie-Laure', NULL),
                                                ('SADGAL', 'Rania', NULL),
                                                ('BARBOT', 'Mattéo', NULL),
                                                ('CABANES', 'Ulysse', NULL),
                                                ('GOETSCHEL', 'Numa', NULL),
                                                ('VICHET', 'Victoria', NULL),
                                                ('CHANAL', 'Marie', NULL),
                                                ('LEONE', 'Julie', NULL),
                                                ('FAUCHER', 'Ruby', NULL),
                                                ('MARTY', 'Morgan', NULL),
                                                ('CANAVY', 'Marisol', NULL),
                                                ('HENRY', 'Camille', NULL),
                                                ('ROGERS', 'Nelson', NULL),
                                                ('ABITBOL', 'Lucas', NULL),
                                                ('AIT YOUSEF', 'Wassim', NULL),
                                                ('DELESTRE', 'Clémence', NULL),
                                                ('REGHEM', 'Romain', NULL),
                                                ('MAJID', 'Saif', NULL),
                                                ('BOUILLET', 'Benjamin', NULL),
                                                ('JOVIGNOT', 'Noah', NULL),
                                                ('CUISOT', 'Mallory', NULL),
                                                ('GOBIN', 'Rémi', NULL),
                                                ('LARTOT', 'Alexis', NULL),
                                                ('BESSE', 'Vincent', NULL),
                                                ('MERCIER', 'Lara', NULL),
                                                ('BRUNET', 'Enora', NULL),
                                                ('SAHAGUIAN', 'Solan', NULL),
                                                ('DORTE', 'Romain', NULL),
                                                ('VIEILLARD', 'Antoine', NULL),
                                                ('LAHMIDI', 'Soukaïna', NULL),
                                                ('CHAUBET', 'Paul', NULL),
                                                ('MASSIAS', 'Paul', NULL),
                                                ('JOLIVET', 'Anaëlle', NULL),
                                                ('BONNAFOUS', 'Arnaud', NULL),
                                                ('MATTONE', 'Elsa', NULL),
                                                ('DELMAS', 'Léo', NULL),
                                                ('ORTEGA', 'Paul', NULL),
                                                ('AYMARD', 'Marion', NULL),
                                                ('KESKIN', 'Defne', NULL),
                                                ('HAJJAM', 'Ikhlass', NULL),
                                                ('EVRARD', 'Damien', NULL),
                                                ('RAYNAL', 'Mathilde', NULL),
                                                ('VILLEDIEU', 'Baptiste', NULL),
                                                ('LASSERVARIE', 'Gabin', NULL),
                                                ('BOURDARIAS', 'Cléa', NULL),
                                                ('ACHER', 'Solène', NULL),
                                                ('BOLZAN', 'Lucie', NULL),
                                                ('ZIDOUR', 'Chérine', NULL),
                                                ('ELIE', 'Antoine', NULL),
                                                ('GATIEN', 'Grégory', NULL),
                                                ('RENUCCI', 'Chloé', NULL),
                                                ('KUBALE', 'Magdalena', NULL),
                                                ('YAGUIYAN', 'Arnaud', NULL),
                                                ('GATIEN', 'Grégory', NULL),
                                                ('ACHER', 'Solène', NULL),
                                                ('TAISNE', 'Lucas', NULL),
                                                ('SOSSEY ALAOUI', 'Léopold', NULL),
                                                ('LEROY', 'Guillaume', NULL),
                                                ('BOUDRIGA', 'Sarra', NULL),
                                                ('TOUJENI', 'Imen', NULL),
                                                ('MEHL-PALMIE', 'Matis', NULL),
                                                ('DELBOS', 'Lucille', NULL),
                                                ('DUPE', 'Cassandra', NULL),
                                                ('COURREDE', 'Félix', NULL),
                                                ('REZOUKI', 'Rokaya', NULL),
                                                ('O’NEILL', 'Kevin', NULL),
                                                ('LOCK-FAT', 'Amélie', NULL),
                                                ('BAAZAOUI', 'Chaima', NULL),
                                                ('LIPOVAC', 'Noémi', NULL),
                                                ('AUBER', 'Colline', NULL),
                                                ('TOTI', 'Mathis', NULL),
                                                ('BERLABI', 'Ellee', NULL),
                                                ('ORTEGA', 'Zafiro', NULL),
                                                ('BONIN', 'Lenny', NULL),
                                                ('RIGAUD', 'Toinou', NULL),
                                                ('DE MONTEVY', 'Agathe', NULL),
                                                ('CHAUVEL', 'Justine', NULL),
                                                ('LAUDE', 'Camille', NULL),
                                                ('GROSJEAN', 'Alexandre', NULL),
                                                ('LEVENT', 'Romaine', NULL),
                                                ('CHANAL', 'Laura', NULL),
                                                ('SUBIRA', 'Emma', NULL),
                                                ('GRUEL', 'Alexis', NULL),
                                                ('BARTHE', 'Emma', NULL),
                                                ('BONNIN', 'Lenny', NULL),
                                                ('TOTI', 'Mathis', NULL),
                                                ('SZCZEPANSKI', 'Neo', NULL),
                                                ( 'GUENNOC', 'Dénoëla', NULL),
                                                (NULL, 'Léandre', NULL);

INSERT INTO Commentaires (description, id_film) VALUES
                                                                 ('Un excellent film avec une intrigue captivante.', 1),
                                                                 ('Les acteurs ont fait un travail remarquable.', 2);

INSERT INTO Images (img, id_film) VALUES
                                             ('path/to/image1.jpg', 1),
                                             ('path/to/image2.jpg', 2);

INSERT INTO ADMINISTRATEURS (email, password) VALUES ('admin@example.com', '$2a$10$bI4I1CZSy93e425U9zGVoOY7vbB1gevrHAaqHWG0TYpJ.08dx5.Ey');

INSERT INTO Actualites (lien) VALUES
                                  ('https://www.youtube.com/watch?v=njgNtDXYPzs'),
                                  ('https://www.youtube.com/watch?v=zZRFJyzFgJQ'),
                                  ('https://www.youtube.com/watch?v=2Wu3Q_TyUnw&t=2s');

INSERT INTO Moments (titre, nom_img, description) VALUES
                                                       ('Première du film', 'premiere.jpg', 'La première du film a eu lieu hier soir.');

INSERT INTO joue (FILM_ID, PARTICIPANT_ID, ROLE) VALUES
                                                     (1, 1, 'Acteur'),
                                                     (1, 2, 'Acteur'),
                                                     (1, 3, 'Acteur'),
                                                     (1, 4, 'Acteur'),
                                                     (1, 5, 'Acteur'),
                                                     (1, 6, 'Acteur'),
                                                     (1, 7, 'Acteur'),
                                                     (1, 8, 'Acteur'),
                                                     (1, 9, 'Acteur'),
                                                     (1, 10, 'Acteur'),
                                                     (1, 11, 'Acteur'),
                                                     (1, 12, 'Acteur'),
                                                     (1, 13, 'Acteur'),
                                                     (2, 14, 'Britney'),
                                                     (2, 15, 'Alex'),
                                                     (2, 16, 'Marge'),
                                                     (2, 17, 'Joey'),
                                                     (2, 18, 'Karen'),
                                                     (2, 19, 'Junior Doctor'),
                                                     (2, 20, 'Doctor'),
                                                     (2, 21, 'Director'),
                                                     (2, 22, 'Script'),
                                                     (2, 23, 'Camera Operator'),
                                                     (2, 24, 'Boom Operator'),
                                                     (2, 91, 'Sound'),
                                                     (2, 24, 'Editor'),
                                                     (2, 21, 'Koh Lanta’s part Editor'),
                                                     (2, 22, 'Next’s part Supervisor'),
                                                     (2, 25, 'Next’s part Animations'),
                                                     (2, 90, 'Make up Artist'),
                                                     (3, 26, 'Director'),
                                                     (3, 27, 'Director of Photography'),
                                                     (3, 28, 'Editor'),
                                                     (3, 29, 'Editor'),
                                                     (3, 30, 'Music Composer'),
                                                     (3, 31, 'Music Composer'),
                                                     (3, 32, 'First Assistant Director'),
                                                     (3, 28, 'Second Assistant Director'),
                                                     (3, 33, 'Doctor Mean'),
                                                     (3, 34, 'Doctor Donald'),
                                                     (3, 35, 'Doctor Karen'),
                                                     (3, 36, 'Doctor Grouch'),
                                                     (3, 37, 'Skater Girl'),
                                                     (3, 38, 'IT Luigi'),
                                                     (3, 39, 'IT Mario'),
                                                     (3, 40, 'Interviewer'),
                                                     (3, 41, 'Patient 1'),
                                                     (3, 42, 'Patient 2'),
                                                     (3, 43, 'Patient 3'),
                                                     (3, 44, 'Patient 4'),
                                                     (3, 45, 'Patient 5'),
                                                     (3, 46, 'Patient 6'),
                                                     (3, 47, 'Chemist'),
                                                     (3, 48, 'Sound Operator'),
                                                     (3, 29, 'First Assistant Sound'),
                                                     (3, 49, 'Camera Operator'),
                                                     (3, 50, 'Camera Operator'),
                                                     (3, 51, 'First Assistant Camera'),
                                                     (3, 30, 'First Assistant Camera'),
                                                     (3, 27, 'Chief Lighting Technician'),
                                                     (3, 52, 'Assistant Chief Lighting Technician'),
                                                     (3, 53, 'Assistant Chief Lighting Technician'),
                                                     (3, 54, 'Script'),
                                                     (3, 55, 'Chief Sound Editor'),
                                                     (3, 30, 'Music Compositor'),
                                                     (3, 31, 'Music Compositor'),
                                                     (3, 52, 'Color Calibration'),
                                                     (3, 30, 'Color Calibration'),
                                                     (3, 48, 'Film Poster and Credits'),
                                                     (3, 28, 'Visual Effects Supervisor'),
                                                     (4, 58, 'IT'),
                                                     (4, 59, 'Director'),
                                                     (4, 60, 'IT Geek'),
                                                     (4, 61, 'Carer'),
                                                     (4, 62, 'Mrs Jude'),
                                                     (4, 63, 'Ambulance Driver'),
                                                     (4, 64, 'Mrs Brand'),
                                                     (4, 65, 'Receptionist'),
                                                     (4, 66, 'Mr O’Dea'),
                                                     (4, 64, 'Dr Smart'),
                                                     (4, 68, 'Vasectomy Doctor'),
                                                     (4, 69, 'Nurse'),
                                                     (4, 70, 'Patient'),
                                                     (4, 71, 'Patient’s Friend'),
                                                     (4, 72, 'Dr Bolonski'),
                                                     (4, 73, 'Camera'),                   -- Mathis TOTI
                                                     (4, 74, 'Camera'),                   -- Ellee BERLABI
                                                     (4, 75, 'First Assistant Director'), -- Zafiro ORTEGA
                                                     (4, 76, 'Continuity Supervisor'),    -- Lenny BONIN
                                                     (4, 77, 'Sound'),                    -- Toinou RIGAUD
                                                     (4, 78, 'Sound'),                    -- Agathe DE MONTEVY
                                                     (4, 79, 'Sound'),                    -- Justine CHAUVEL
                                                     (4, 80, 'Gaffers'),                  -- Camille LAUDE
                                                     (4, 81, 'Gaffers'),                  -- Alexandre GROSJEAN
                                                     (4, 82, 'Gaffers'),                  -- Romaine LEVENT
                                                     (4, 83, 'Set Decoration'),           -- Laura CHANAL
                                                     (4, 84, 'Set Decoration'),           -- Emma SUBIRA
                                                     (4, 85, 'Set Decoration'),           -- Alexis GRUEL
                                                     (4, 89, 'Grips'),                    -- Neo SZCZEPANSKI
                                                     (4, 86, 'Grips'),                    -- Emma BARTHE
                                                     (4, 87, 'Editors'),                  -- Lenny BONNIN
                                                     (4, 85, 'Editors'),                  -- Alexis GRUEL
                                                     (4, 88, 'Colorist'),                 -- Mathis TOTI
                                                     (4, 89, 'Colorist'),                 -- Neo SZCZEPANSKI
                                                     (4, 84, 'Poster and Opening by'),    -- Emma SUBIRA
                                                     (4, 83, 'Making of'),                -- Laura CHANAL
                                                     (4, 75, 'Making of');                -- Zafiro ORTEGA

INSERT INTO Anecdotes (description, id_film, id_participant) VALUES
                                                                          ('Une scène a été tournée en une seule prise.', 1, 1),
                                                                          ('Les acteurs ont improvisé certaines répliques.', 2, 2);
