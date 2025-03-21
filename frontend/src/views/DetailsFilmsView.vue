<template>
  <div>
    <div v-if="isLoading" class="loading">Chargement...</div>
    <div v-else>
      <!-- Détails du film -->
      <h1 class="film-title">{{ selectedFilm.titre }}</h1>
      <p class="film-info">
        {{ selectedFilm.annee }} - {{ selectedFilm.genre }} - {{ selectedFilm.duree }}
      </p>
      <p class="film-resume">{{ selectedFilm.synopsis }}</p>
      <div class="Boutton">
        <button name="Regarder" @click="watchFilm">REGARDER</button>
        <button name="Bande annonce" @click="watchTrailer">BANDE D'ANNONCE</button>
      </div>

      <!-- Acteurs -->
      <h2 class="section-title">Acteurs</h2>
      <div class="acteurs">
        <button class="bouton-rond">&#8592;</button>
        <p v-for="acteur in acteurs" :key="acteur.id">
          {{ acteur.nom }} {{ acteur.prenom }}
        </p>
        <button class="bouton-rond">&#8594;</button>
      </div>

      <!-- Post-Production -->
      <h2 class="section-title">Post-Production</h2>
      <div class="post_prod">
        <button class="bouton-rond">&#8592;</button>
        <p v-for="postProd in postProductions" :key="postProd.id">
          {{ postProd.nom }} {{ postProd.prenom }}
        </p>
        <button class="bouton-rond">&#8594;</button>
      </div>

      <!-- Equipe de tournage -->
      <h2 class="section-title">Equipe de tournage</h2>
      <div class="equipe_tournage">
        <button class="bouton-rond">&#8592;</button>
        <p v-for="equipe in equipeTournage" :key="equipe.id">
          {{ equipe.nom }} {{ equipe.prenom }}
        </p>
        <button class="bouton-rond">&#8594;</button>
      </div>

      <!-- Anecdotes -->
      <h2 class="section-title">Anecdotes</h2>
      <div class="anecdotes">
        <p class="film-info" v-for="anecdote in anecdotes" :key="anecdote.idAnecdote">
          {{ anecdote.description }}
        </p>
      </div>

      <!-- Images exclusives du tournage (Swiper avec espace entre les images) -->
      <h2 class="section-title">Images exclusives du tournage</h2>
      <Swiper
        :modules="[Navigation]"
        :slides-per-view="3"
        :spaceBetween="20"
        navigation
        class="images-swiper"
      >
        <SwiperSlide v-for="(image, index) in images" :key="index">
          <img :src="image.img" alt="Image exclusive du tournage" />
        </SwiperSlide>
      </Swiper>

      <!-- Commentaires -->
      <h2 class="section-title">Commentaires</h2>
      <div class="commentaires">
        <textarea
          v-model="nouveauCommentaire"
          placeholder="Écrivez un commentaire..."
        ></textarea>
        <button class="publier-btn" @click="publierCommentaire">Publier</button>
        <div class="liste-commentaires">
          <p v-for="comment in listCommentaires" :key="comment.id">
            {{ comment.texte }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {useRoute} from 'vue-router'
import {Swiper, SwiperSlide} from 'swiper/vue'
import 'swiper/css'
import 'swiper/css/navigation'
import {Navigation} from 'swiper/modules'

const API_URL = 'http://localhost:8989/api/films'

const selectedFilm = ref({})
const isLoading = ref(true)
const acteurs = ref([])
const postProductions = ref([])
const equipeTournage = ref([])
const anecdotes = ref([])  // Pour stocker les anecdotes
const images = ref([])     // Pour stocker les images exclusives

// Pour les commentaires
const nouveauCommentaire = ref("")  // Le texte du nouveau commentaire
const listCommentaires = ref([])      // La liste des commentaires récupérés

const route = useRoute()

// Fonction utilitaire pour effectuer une requête GET
async function getData(endpoint) {
  try {
    const response = await fetch(endpoint)
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`)
    }
    return await response.json()
  } catch (error) {
    console.error(`Erreur lors de la récupération depuis ${endpoint} :`, error)
    return null
  }
}

async function getFilmDetail(filmId) {
  isLoading.value = true

  // Récupération des informations principales du film
  selectedFilm.value = await getData(`${API_URL}/${filmId}`) || {}
  acteurs.value = await getData(`${API_URL}/${filmId}/acteurs`) || []
  postProductions.value = await getData(`${API_URL}/${filmId}/postproduction`) || []
  equipeTournage.value = await getData(`${API_URL}/${filmId}/equipe`) || []

  // Récupération des anecdotes via GET et extraction de _embedded.anecdotes
  const dataAnecdotes = await getData(`${API_URL}/${filmId}/anecdotes`)
  anecdotes.value =
    dataAnecdotes && dataAnecdotes._embedded && dataAnecdotes._embedded.anecdotes
      ? dataAnecdotes._embedded.anecdotes
      : []

  // Récupération des images via GET et extraction de _embedded.images
  const dataImages = await getData(`${API_URL}/${filmId}/images`)
  images.value =
    dataImages && dataImages._embedded && dataImages._embedded.images
      ? dataImages._embedded.images
      : []

  // Récupération des commentaires pour ce film
  await fetchCommentairesByFilm(filmId)

  isLoading.value = false

  console.log("Anecdotes récupérées :", anecdotes.value)
  console.log("Images récupérées :", images.value)
}

async function fetchCommentairesByFilm(filmId) {
  const data = await getData(`${API_URL}/${filmId}/commentaires`)
  listCommentaires.value =
    data && data._embedded && data._embedded.commentaires
      ? data._embedded.commentaires
      : []
}

async function publierCommentaire() {
  // Vérifier que le champ n'est pas vide
  if (!nouveauCommentaire.value.trim()) return

  const filmId = route.params.filmId
  try {
    const response = await fetch(`${API_URL}/${filmId}/commentaires`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      // On envoie le commentaire dans une propriété "texte"
      body: JSON.stringify({texte: nouveauCommentaire.value})
    })
    if (!response.ok) {
      throw new Error('Erreur lors de la publication du commentaire')
    }
    // Réinitialiser le champ du commentaire
    nouveauCommentaire.value = ""
    // Actualiser la liste des commentaires
    await fetchCommentairesByFilm(filmId)
  } catch (error) {
    console.error("Erreur lors de la publication du commentaire :", error)
  }
}

function watchFilm() {
  if (selectedFilm.value?.urlFilm?.trim()) {
    window.location.href = selectedFilm.value.urlFilm
  }
}

function watchTrailer() {
  if (selectedFilm.value?.urlBA?.trim()) {
    window.location.href = selectedFilm.value.urlBA
  }
}

onMounted(() => {
  const filmId = route.params.filmId
  getFilmDetail(filmId)
})
</script>

<style scoped>
.loading {
  text-align: center;
  font-size: 1.5rem;
  margin-top: 50px;
}

html, body {
  margin: 0;
  padding: 0;
  background-color: #000;
  color: var(--vt-c-white);
  font-family: var(--be-vietnam-font);
}

.film-title {
  font-family: var(--bebas-font);
  font-size: 3rem;
  margin: 20px 0;
}

.section-title {
  font-family: var(--bebas-weight);
  font-size: 2rem;
  margin: 20px 0 10px 0;
}

.film-info, .film-resume {
  font-family: var(--be-vietnam-font);
  font-size: 1rem;
  margin: 5px 0;
}

.Boutton {
  position: fixed;
  bottom: 20px;
  left: 20px;
  display: flex;
  gap: 10px;
}

.Boutton button,
.bouton-rond,
.publier-btn {
  background-color: var(--color-primary);
  color: var(--vt-c-white);
  border: none;
  cursor: pointer;
  font-family: var(--be-vietnam-font);
  transition: background-color 0.3s ease;
}

.bouton-rond {
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 1.2rem;
  display: inline-flex;
  justify-content: center;
  align-items: center;
}

.Boutton button {
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 1rem;
}

.Boutton button:hover,
.bouton-rond:hover,
.publier-btn:hover {
  background-color: var(--color-primary);
}

.commentaires {
  width: 80%;
  max-width: 800px;
  margin: 40px 20px;
  text-align: left;
}

.commentaires textarea {
  width: 100%;
  height: 100px;
  background-color: #fff;
  color: #000;
  border: none;
  border-radius: 5px;
  padding: 10px;
  font-family: var(--be-vietnam-font);
  font-size: 1rem;
  resize: none;
}

.publier-btn {
  margin-top: 10px;
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 1rem;
}

.liste-commentaires {
  margin-top: 20px;
  background-color: #222;
  padding: 10px;
  border-radius: 5px;
}

.liste-commentaires p {
  margin: 5px 0;
}

/* Styles pour le Swiper carrousel d'images (identique à celui de NOS FILMS) */
.images-swiper {
  margin: 20px 0;
}

.images-swiper :deep(.swiper-button-prev),
.images-swiper :deep(.swiper-button-next) {
  background-color: var(--color-button);
  color: var(--color-text);
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.images-swiper :deep(.swiper-button-prev)::after,
.images-swiper :deep(.swiper-button-next)::after {
  font-size: 16px;
  font-weight: bold;
  color: var(--color-text);
  transition: transform 0.3s ease;
}

.images-swiper :deep(.swiper-button-prev:hover)::after,
.images-swiper :deep(.swiper-button-next:hover)::after {
  transform: scale(0.8);
}

.images-swiper img {
  width: 100%;
  height: auto;
  border-radius: 8px;
  object-fit: cover;
}

/* Styles supplémentaires pour la page */
.anecdotes {
  margin: 20px 0;
}
</style>
