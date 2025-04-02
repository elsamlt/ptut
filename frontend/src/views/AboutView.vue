<template>
  <div class="about-container">
    <section class="section school">
      <div class="text-content">
        <h2>À propos de l’école ISIS Castres</h2>
        <img src="@/assets/ISIS.jpg" alt="ISIS Castres" class="section-image ISIS" />
        <p>
          Première école en France à former des ingénieurs spécialisés en E-Santé, ISIS Castres vous ouvre les portes de métiers passionnants dans une filière en pleine expansion.
          Notre établissement vous offre une double expertise, alliant compétences numériques et savoir-faire en santé, aboutissant à un diplôme d'ingénieur reconnu par l'État.
        </p>
        <h3>Une école tournée vers l'innovation et la recherche</h3>
        <p>
          À ISIS Castres, nous mettons un point d'honneur à développer des projets innovants qui repoussent les limites de la technologie au service de la santé.
          Nos étudiants et enseignants-chercheurs travaillent sur des initiatives variées, allant de l'intelligence artificielle appliquée au diagnostic médical à la télémédecine, en passant par l'analyse de données de santé et la cybersécurité des systèmes hospitaliers.
        </p>
        <h3>Des projets concrets et interdisciplinaires</h3>
        <p>
          ISIS Castres s'investit dans plusieurs projets ambitieux, favorisant l'apprentissage par l'expérimentation et le travail collaboratif.
          Parmi nos initiatives marquantes, nous développons une plateforme numérique de suivi des patients, des applications mobiles d’aide aux professionnels de santé et des outils de réalité virtuelle pour la formation médicale.
        </p>
        <p>
          Nous avons également un projet original liant cinéma et santé : une étude des représentations de la médecine et de la technologie dans les films et séries.
          Ce projet vise à analyser l'impact des œuvres audiovisuelles sur la perception du public concernant les avancées technologiques en santé.
        </p>
      </div>
    </section>

    <section class="section project">
      <img src="@/assets/film.jpeg" alt="The Hospital" class="section-image left" />
      <div class="text-content">
        <h2>Le projet "The Hospital"</h2>
        <h3>La genèse du projet</h3>
        <p>
          Le projet est né de Monsieur O’Neil. Il souhaitait améliorer le niveau d’anglais de ses élèves de manière ludique et immersive. Depuis 2021, les élèves ont exploré des approches innovantes pour permettre aux participants de progresser en anglais à travers des mises en situation inspirées du monde réel.
        </p>
        <h3>Une approche basée sur des situations concrètes</h3>
        <p>
          Dès la 3ᵉ année, les participants apprennent à s’exprimer en anglais à travers des scénarios pratiques. En s’appuyant sur des films et des sketches humoristiques, ils enrichissent leur vocabulaire et leur fluidité tout en s’amusant. Cette méthode encourage un apprentissage naturel et interactif.
        </p>
        <h3>Les débuts du projet CHL</h3>
        <p>
          Le projet a débuté modestement avec la création d’un premier sketch intitulé "The Hospital" en 2021, développé par une petite équipe de l’ENSAV et d’ISIS. Ce projet pilote a permis de poser les bases et de tester notre concept. Un making-of de cette première édition est disponible pour revivre l’expérience !
        </p>
        <h3>Une évolution continue</h3>
        <p>
          En 2023, le projet a pris de l’ampleur en intégrant l’ensemble des promotions ENSAV et ISIS FIE4. Les étudiants ont formé des groupes pour concevoir de petits sketches inspirés de situations cocasses vécues dans des hôpitaux. Ce processus participatif inclut des recherches, l’écriture de dialogues et un mois de montage collaboratif pour aboutir à des productions uniques et enrichissantes.
        </p>
        <h3>Des perspectives prometteuses</h3>
        <p>
          En 2024, nous avons perfectionné notre approche : les étudiants s’impliquent encore davantage en écrivant eux-mêmes les dialogues et en s’inspirant d’anecdotes véridiques. Cette année encore, les participants continuent de développer leur expression orale, tout en se préparant à la prochaine édition avec des idées de speech déjà en cours pour 2025.
          Notre projet n’est pas seulement un outil pédagogique, c’est aussi une aventure humaine et artistique qui montre que l’apprentissage peut être à la fois efficace et amusant. Nous avons hâte de partager cette expérience avec encore plus d’étudiants dans les années à venir !
        </p>
      </div>
    </section>

    <section class="section film">
      <div class="text-content">
        <h2>Le Film</h2>
        <p>
          La création d’un film suit un processus assez long. Tout commence au second semestre de la 4ᵉ année, où les étudiants conçoivent et écrivent des sketches humoristiques en anglais.
        </p>
        <p>
          Une fois finalisés, ces sketches font l’objet de répétitions encadrées avant d’être présentés aux élèves de l’école ENSAV lors de leur 5ᵉ année. Une journée entière est dédiée au tournage, permettant aux participants de mettre en scène leurs idées.
        </p>
        <p>
          La phase de postproduction, prise en charge par les étudiants de l’ENSAV, inclut le montage, les effets spéciaux, les sous-titres et la finition technique.
        </p>
        <p>
          Enfin, le film est diffusé aux deux écoles à la fin de ce semestre, offrant un clap de fin à leurs études au sein d’ISIS.
        </p>
      </div>
      <img src="@/assets/cinema.jpg" alt="Film" class="section-image right" />
    </section>
  </div>
</template>

<script setup>
</script>

<style scoped>
.about-container {
  display: flex;
  flex-direction: column;
  gap: 40px;
  padding: 40px;
  max-width: 1000px;
  margin: auto;
}

.section {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px;
  border-radius: 10px;
  background: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.text-content {
  flex: 1;
}

.section-image {
  width: 300px;
  height: auto;
  max-width: none;
  border-radius: 10px;
}

.section-image.left {
  order: -1;
}

.section-image.ISIS {
  width: 100%;
}

.section-image.right {
  height: auto;
  width: 400px;
}

h2 {
  color: #2c3e50;
  margin-bottom: 15px;
}

h3 {
  color: #34495e;
  margin-top: 10px;
}
</style>
