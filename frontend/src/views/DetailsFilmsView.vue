<template>
  <div>
    <h1>The Hospital</h1>
    <p class="annee">2023</p>
    <p class="type">Comédie</p>
    <p class="duree">14 min</p>
    <p class="resume">Resumé film</p>

   >
    <div class="Boutton">
      <button @click="regarderFilm">REGARDER</button>
      <button @click="voirBandeAnnonce">BANDE D'ANNONCE</button>
    </div>

    <h2>Acteurs</h2>
    <div class="acteurs">
      <p>Nom/prenom</p>
      <p>Nom/prenom</p>
      <p>Nom/prenom</p>
      <p>Nom/prenom</p>
      <button class="bouton-rond">&#8592;</button>
      <button class="bouton-rond">&#8594;</button>
    </div>

    <h2>Post-Production</h2>
    <div class="post_prod">
      <p>Nom/prenom</p>
      <p>Nom/prenom</p>
      <button class="bouton-rond">&#8592;</button>
      <button class="bouton-rond">&#8594;</button>
    </div>

    <h2>Equipe de tournage</h2>
    <div class="equipe_tournage">
      <p>Nom/prenom</p>
      <button class="bouton-rond">&#8592;</button>
      <button class="bouton-rond">&#8594;</button>
    </div>

    <h2>Anecdotes</h2>
    <p>Voici une anecdote intéressante</p>

    <h2>Images exclusives du tournage</h2>
    <div class="carousel">
      <button name="revenir"></button>
      <button class="bouton-rond">&#8592;</button>
      <button class="bouton-rond">&#8594;</button>
    </div>

    <h2>Commentaires</h2>
    <div class="commentaires">
      <textarea v-model="commentaire" placeholder="Écrivez un commentaire..."></textarea>
      <button class="publier-btn" @click="publierCommentaire">Publier</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      commentaire: '',  // Data pour le commentaire
    };
  },
  methods: {
    regarderFilm() {
      console.log("Regarder le film");
      // Ajouter votre logique ici pour la fonction 'Regarder'
    },
    voirBandeAnnonce() {
      console.log("Voir la bande annonce");
      // Ajouter votre logique ici pour la fonction 'Bande d'annonce'
    },
    publierCommentaire() {
      console.log("Commentaire publié : ", this.commentaire);
      // Logique pour publier le commentaire
    }
  }
};
</script>

<style scoped>
/* Importation de polices Google Fonts */
@import url('https://fonts.googleapis.com/css2?family=Cinzel+Decorative:wght@700&family=Playfair+Display:wght@600&family=Roboto:wght@400;700&display=swap');


html, body {
  margin: 0;
  padding: 0;
  background-color: #000000;
  color: #FFFFFF;
  font-family: 'Roboto', sans-serif;
}


h1 {
  font-family: 'Cinzel Decorative', cursive;
  font-size: 3rem;
  text-align: center;
  margin: 20px 0;
}


h2 {
  font-family: 'Playfair Display', serif; /* Police élégante */
  font-size: 2rem;
  margin: 20px 0 10px 0;
  text-align: center;
}

p {
  font-size: 1rem;
  margin: 5px 0;
  text-align: center;
}


.Boutton {
  position: fixed;
  bottom: 20px; /* Ajustez selon la marge souhaitée */
  left: 20px; /* Ajustez selon la marge souhaitée */
  display: flex;
  gap: 10px;
  background-position: bottom left;
  background-attachment: fixed;
}


.Boutton button,
.bouton-rond,
.publier-btn {
  background-color: #6C63FF; /* Violet */
  color: #FFFFFF; /* Texte blanc */
  border: none;
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
  transition: background-color 0.3s ease;
}


.bouton-rond {
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 1.2rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  margin: 5px;
}


.Boutton button {
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 1rem;
}


.Boutton button:hover,
.bouton-rond:hover,
.publier-btn:hover {
  background-color: #5A52E5;
}


.acteurs, .post_prod, .equipe_tournage {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  gap: 20px;
  margin: 20px 0;
}

.acteurs p, .post_prod p, .equipe_tournage p {
  text-align: center;
  margin: 0;
  font-size: 1rem;
}


.carousel {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0;
}


.commentaires {
  width: 80%;
  max-width: 800px;
  margin: 0 auto 40px auto;
  text-align: center;
}


.commentaires textarea {
  width: 100%;
  height: 100px;
  background-color: #FFFFFF;
  color: #000000;
  border: none;
  border-radius: 5px;
  padding: 10px;
  font-family: 'Roboto', sans-serif;
  font-size: 1rem;
  resize: none;
}


.publier-btn {
  margin-top: 10px;
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 1rem;
}
</style>

<script setup>

</script>

