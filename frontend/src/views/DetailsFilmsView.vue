<template>
  <div>
    <div v-if="isLoading" class="loading">Chargement...</div>

    <div v-else>
      <h1 class="film-title">{{ selectedFilm.titre }}</h1>
      <p class="film-info">
        {{ selectedFilm.annee }} - {{ selectedFilm.genre }} - {{ selectedFilm.duree }}
      </p>
      <p class="film-resume">{{ selectedFilm.synopsis }}</p>

      <div class="Boutton">
        <button name="Regarder" @click="watchFilm">REGARDER</button>
        <button name="Bande annonce" @click="watchTrailer">BANDE D'ANNONCE</button>
      </div>

      <h2 class="section-title">Acteurs</h2>
      <div class="acteurs">
        <button class="bouton-rond">&#8592;</button>
        <p v-for="acteur in acteurs" :key="acteur.id">{{ acteur.nom }} {{ acteur.prenom }}</p>
        <button class="bouton-rond">&#8594;</button>
      </div>

      <h2 class="section-title">Post-Production</h2>
      <div class="post_prod">
        <button class="bouton-rond">&#8592;</button>
        <p v-for="postProd in postProductions" :key="postProd.id">{{ postProd.nom }} {{ postProd.prenom }}</p>
        <button class="bouton-rond">&#8594;</button>
      </div>

      <h2 class="section-title">Equipe de tournage</h2>
      <div class="equipe_tournage">
        <button class="bouton-rond">&#8592;</button>
        <p v-for="equipe in equipeTournage" :key="equipe.id">{{ equipe.nom }} {{ equipe.prenom }}</p>
        <button class="bouton-rond">&#8594;</button>
      </div>

      <h2 class="section-title">Anecdotes</h2>
      <p class="film-info" v-for="anecdote in anecdotes" :key="anecdote.id">{{ anecdote.texte }}</p>

      <h2 class="section-title">Images exclusives du tournage</h2>
      <div class="carousel">
        <button name="revenir"></button>
        <button class="bouton-rond">&#8592;</button>
        <button class="bouton-rond">&#8594;</button>
      </div>

      <h2 class="section-title">Commentaires</h2>
      <div class="commentaires">
        <textarea placeholder="Écrivez un commentaire..."></textarea>
        <button class="publier-btn">Publier</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const API_URL = 'http://localhost:8989/api/films'
const selectedFilm = ref({})
const isLoading = ref(true)
const acteurs = ref([])
const postProductions = ref([])
const equipeTournage = ref([])
const anecdotes = ref([])
const route = useRoute()

async function getData(endpoint) {
  try {
    const response = await fetch(endpoint);
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Erreur lors de la récupération des données depuis ${endpoint} :`, error);
    return null;
  }
}

async function getFilmDetail(filmId) {
  isLoading.value = true;
  selectedFilm.value = await getData(`${API_URL}/${filmId}`) || {};
  acteurs.value = await getData(`${API_URL}/${filmId}/acteurs`) || [];
  postProductions.value = await getData(`${API_URL}/${filmId}/postproduction`) || [];
  equipeTournage.value = await getData(`${API_URL}/${filmId}/equipe`) || [];
  anecdotes.value = await getData(`${API_URL}/${filmId}/anecdotes`) || [];
  isLoading.value = false;
}

function watchFilm() {
  if (selectedFilm.value?.urlFilm?.trim()) {
    window.location.href = selectedFilm.value.urlFilm;
  }
}

function watchTrailer() {
  if (selectedFilm.value?.urlBA?.trim()) {
    window.location.href = selectedFilm.value.urlBA;
  }
}

onMounted(() => {
  const filmId = route.params.filmId;
  getFilmDetail(filmId);
});
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
  background-color: #000000;
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
  background-color: #FFFFFF;
  color: #000000;
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
</style>
