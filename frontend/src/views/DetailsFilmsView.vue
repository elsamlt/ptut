<template>
  <div>
    <div class="affiche-film">
      <img :src="film.affiche" alt="Affiche du film" />
    </div>

    <h1>{{ film.titre }}</h1>
    <p class="annee">{{ film.annee }}</p>
    <p class="type">{{ film.genre }}</p>
    <p class="duree">{{ film.duree }}</p>
    <p class="resume">{{ film.synopsis }}</p>

    <div class="Boutton">
      <button @click="regarderFilm">REGARDER</button>
      <button @click="voirBandeAnnonce">BANDE D'ANNONCE</button>
    </div>


    <h2>Acteurs</h2>
    <div class="acteurs">
      <p v-for="(acteur, index) in film.acteurs" :key="index">{{ acteur.nom }} {{ acteur.prenom }}</p>
      <button class="bouton-rond" @click="previousActeurs">&#8592;</button>
      <button class="bouton-rond" @click="nextActeurs">&#8594;</button>
    </div>

    <h2>Post-Production</h2>
    <div class="post_prod">
      <p v-for="(postProd, index) in film.postProduction" :key="index">{{ postProd.nom }} {{ postProd.prenom }}</p>
      <button class="bouton-rond" @click="previousPostProd">&#8592;</button>
      <button class="bouton-rond" @click="nextPostProd">&#8594;</button>
    </div>


    <h2>Equipe de tournage</h2>
    <div class="equipe_tournage">
      <p v-for="(equipe, index) in film.equipeTournage" :key="index">{{ equipe.nom }} {{ equipe.prenom }}</p>
      <button class="bouton-rond" @click="previousEquipe">&#8592;</button>
      <button class="bouton-rond" @click="nextEquipe">&#8594;</button>
    </div>


    <h2>Anecdotes</h2>
    <p v-for="(anecdote, index) in anecdotes" :key="index">{{ anecdote.texte }}</p>



    <h2>Images exclusives du tournage</h2>
    <div class="carousel">
      <button name="revenir"></button>
      <button class="bouton-rond" @click="previousImage">&#8592;</button>
      <button class="bouton-rond" @click="nextImage">&#8594;</button>


      <div class="images-tournage">
        <img v-for="(image, index) in currentImages" :key="index" :src="image.url" alt="Image de tournage" class="image-tournage" />
      </div>
    </div>


    <h2>Commentaires</h2>
    <div class="commentaires">
      <textarea v-model="commentaire" placeholder="Écrivez un commentaire..."></textarea>
      <button class="publier-btn" @click="publierCommentaire">Publier</button>

      <div v-if="commentaires.length > 0">
        <ul>
          <li v-for="(commentaire, index) in commentaires" :key="index">
            {{ commentaire.texte }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      film: {},
      commentaires: [],
      commentaire: '',
      anecdotes: [],
      images: [],
      currentImageIndex: 0,  // Index pour la pagination des images
      filmId: null,
    };
  },
  mounted() {
    this.filmId = this.$route.params.id; // Récupérer l'ID du film depuis l'URL
    this.fetchFilmDetails(this.filmId);
    this.fetchCommentaires(this.filmId);
    this.fetchAnecdotes(this.filmId);
    this.fetchImages(this.filmId);
  },
  computed: {

    currentImages() {
      const imagesPerPage = 5;
      return this.images.slice(this.currentImageIndex, this.currentImageIndex + imagesPerPage);
    }
  },
  methods: {
    // Récupérer les détails du film depuis l'API
    fetchFilmDetails(filmId) {
      axios.get(`http://localhost:8989/api/films/${filmId}`)
        .then(response => {
          this.film = response.data;
        })
        .catch(error => {
          console.error("Erreur lors du chargement du film", error);
        });
    },

    // Récupérer les commentaires du film depuis l'API
    fetchCommentaires(filmId) {
      axios.get(`http://localhost:8989/api/films/${filmId}/commentaires`)
        .then(response => {
          this.commentaires = response.data;
        })
        .catch(error => {
          console.error("Erreur lors du chargement des commentaires", error);
        });
    },

    // Publier un commentaire
    publierCommentaire() {
      const nouveauCommentaire = {
        texte: this.commentaire,
        filmId: this.filmId,
      };

      axios.post(`http://localhost:8989/api/films/${this.filmId}/commentaires`, nouveauCommentaire)
        .then(response => {
          this.commentaires.push(response.data);  // Ajouter le commentaire à la liste
          this.commentaire = '';  // Réinitialiser le champ de saisie
        })
        .catch(error => {
          console.error("Erreur lors de l'ajout du commentaire", error);
        });
    },

    // Récupérer les anecdotes depuis l'API
    fetchAnecdotes(filmId) {
      axios.get(`http://localhost:8989/api/films/${filmId}/anecdotes`)
        .then(response => {
          this.anecdotes = response.data;
        })
        .catch(error => {
          console.error("Erreur lors du chargement des anecdotes", error);
        });
    },

    // Récupérer les images du tournage depuis l'API
    fetchImages(filmId) {
      axios.get(`http://localhost:8989/api/films/${filmId}/images`)
        .then(response => {
          this.images = response.data;
        })
        .catch(error => {
          console.error("Erreur lors du chargement des images", error);
        });
    },

    // Fonction pour "Regarder" le film (ajouter votre logique ici)
    regarderFilm() {
      console.log("Regarder le film");
    },


    voirBandeAnnonce() {
      console.log("Voir la bande annonce");
    },


    nextImage() {
      if (this.currentImageIndex + 5 < this.images.length) {
        this.currentImageIndex += 5;
      }
    },

    previousImage() {
      if (this.currentImageIndex - 5 >= 0) {
        this.currentImageIndex -= 5;
      }
    },


    previousActeurs() {
      console.log("Acteurs précédent");
    },
    nextActeurs() {
      console.log("Acteurs suivant");
    },
    previousPostProd() {
      console.log("Post-production précédent");
    },
    nextPostProd() {
      console.log("Post-production suivant");
    },
    previousEquipe() {
      console.log("Équipe de tournage précédent");
    },
    nextEquipe() {
      console.log("Équipe de tournage suivant");
    },
  },
};
</script>

<style scoped>
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
  font-family: 'Playfair Display', serif;
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
  bottom: 20px;
  left: 20px;
  display: flex;
  gap: 10px;
  background-position: bottom left;
  background-attachment: fixed;
}

.Boutton button,
.bouton-rond,
.publier-btn {
  background-color: #6C63FF;
  color: #FFFFFF;
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

/* Affiche du film fixée en haut */
.affiche-film {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 300px;
  background-color: #333;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
}

.affiche-film img {
  max-height: 100%;
  object-fit: cover;
  width: 100%;
}

/* Décalage du contenu pour laisser de l'espace sous l'affiche fixe */
div {
  padding-top: 350px; /* Marge supérieure pour éviter que l'affiche ne cache le contenu */
}

.carousel {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0;
}

.images-tournage {
  display: flex;
  align-items: center;
  gap: 20px;
  overflow-x: auto;
}

.images-tournage img {
  width: 200px;
  height: 200px;
  object-fit: cover;
  border-radius: 10px;
  transition: transform 0.3s ease;
}

.images-tournage img:hover {
  transform: scale(1.1);
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
