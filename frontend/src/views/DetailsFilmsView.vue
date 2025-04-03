<template>
  <div class="contenu">
    <div v-if="isLoading" class="loading">Chargement...</div>
    <div v-else style="margin-top: 10px">
      <div class="d-flex flex-row justify-center" style="gap: 80px">
        <div class="affiche d-flex flex-column">
          <img class="affiche" :src="selectedFilm.affiche ? selectedFilm.affiche : defaultImage">

          <!-- Boutons d'action avec Vuetify -->
          <v-btn v-if="selectedFilm.urlFilm" class="btn" :href="selectedFilm.urlFilm" target="_blank">
            REGARDER
          </v-btn>

          <v-btn v-if="selectedFilm.urlBA" class="btn-bd" :href="selectedFilm.urlBA" target="_blank">
            BANDE D'ANNONCE
          </v-btn>
        </div>
        <div class="film-details">
          <!-- Détails du film -->
          <h1 class="film-title">{{ selectedFilm.titre }}</h1>
          <p style="font-size: 12px">
            {{ selectedFilm.annee }} - {{ selectedFilm.genre }} - {{ selectedFilm.duree }}
          </p>
          <br>
          <p class="film-resume">{{ selectedFilm.synopsis }}</p>

          <!-- Acteurs en carrousel -->
          <div v-if="acteurs.length" class="caroussel-container">
            <h2>Acteurs</h2>
            <div class="swiper-wrapper">
              <Swiper
                :modules="[Navigation]"
                :slides-per-view="4"
                :spaceBetween="15"
                navigation
                class="images-swiper"
              >
                <SwiperSlide v-for="item in acteurs" :key="`${item.participant.nom}-${item.participant.prenom}`">
                  <div class="participant-card d-flex flex-column justify-center text-center">
                    <div>
                      <img v-if="item.participant.pdp" :src="item.participant.pdp" alt="Photo de profil du participant"/>
                      <img v-else :src="defaultAvatar" alt="Avatar" style="width: 100px; border-radius: 50px"/>
                    </div>
                    <div>
                      <p class="participant-name">{{ item.participant.nom }} {{ item.participant.prenom }}</p>
                      <p class="role">{{ item.role }}</p>
                    </div>
                  </div>
                </SwiperSlide>
              </Swiper>
            </div>
          </div>

          <!-- Équipe de tournage en carrousel -->
          <div v-if="equipeTournage.length" class="caroussel-container">
            <h2>Équipe de tournage</h2>
            <div class="swiper-wrapper">
              <Swiper
                :modules="[Navigation]"
                :slides-per-view="4"
                :spaceBetween="15"
                navigation
                class="images-swiper"
              >
                <SwiperSlide v-for="item in equipeTournage" :key="`${item.participant.nom}-${item.participant.prenom}`">
                  <div class="participant-card d-flex flex-column justify-center text-center">
                    <div>
                      <img v-if="item.participant.pdp" :src="item.participant.pdp"  alt="Photo de profil du participant" style="width: 100px; border-radius: 50px"/>
                      <img v-else :src="defaultAvatar"  alt="Avatar" style="width: 100px; border-radius: 50px"/>
                    </div>
                    <div>
                      <p class="participant-name">{{ item.participant.nom }} {{ item.participant.prenom }}</p>
                      <p class="role">{{ item.role }}</p>
                    </div>
                  </div>
                </SwiperSlide>
              </Swiper>
            </div>
          </div>

          <!-- PostProduction en carrousel -->
          <div v-if="postProductions.length" class="caroussel-container">
            <h2>Post Production</h2>
            <div class="swiper-wrapper">
              <Swiper
                :modules="[Navigation]"
                :slides-per-view="4"
                :spaceBetween="15"
                navigation
                class="images-swiper"
              >
                <SwiperSlide v-for="item in postProductions" :key="`${item.participant.nom}-${item.participant.prenom}`">
                  <div class="participant-card d-flex flex-column justify-center text-center">
                    <div>
                      <img v-if="item.participant.pdp" :src="item.participant.pdp"  alt="Photo de profil du participant"/>
                      <img v-else :src="defaultAvatar"  alt="Avatar" style="width: 100px; border-radius: 50px"/>
                    </div>
                    <div>
                      <p class="participant-name">{{ item.participant.nom }} {{ item.participant.prenom }}</p>
                      <p class="role">{{ item.role }}</p>
                    </div>
                  </div>
                </SwiperSlide>
              </Swiper>
            </div>
          </div>

          <!-- Anecdotes avec participant en liste -->
          <div v-if="anecdotes.length" style="margin-top: 50px">
            <h2>Anecdotes</h2>
            <v-card v-for="anecdote in anecdotes" :key="anecdote.idAnecdote" class="anecdote-card">
              <v-row align="center">
                <v-col cols="3">
                  <img v-if="anecdote.participant && anecdote.participant.pdp" :src="anecdote.participant.pdp" class="anecdote-image" alt="Photo du participant" style="width: 100px; border-radius: 50px">
                  <img v-else :src="defaultAvatar" alt="Avatar" style="width: 100px; border-radius: 50px"/>
                </v-col>
                <v-col>
                  <h3 class="participant-name">
                    {{ anecdote.participant ? anecdote.participant.nom + ' ' + anecdote.participant.prenom : 'Participant inconnu' }}
                  </h3>
                  <p class="anecdote-description">{{ anecdote.description }}</p>
                </v-col>
              </v-row>
            </v-card>
          </div>

          <div v-if="images.length" style="margin-top: 50px">
            <h2 style="margin-bottom: 10px">Images exclusives</h2>
            <v-row>
              <v-col
                v-for="(image, index) in images"
                :key="index"
                class="d-flex child-flex"
                cols="3"
              >
                <v-img
                  :lazy-src="image.img"
                  :src="image.img"
                  aspect-ratio="1"
                  class="bg-grey-lighten-2"
                  cover
                >
                  <template v-slot:placeholder>
                    <v-row
                      align="center"
                      class="fill-height ma-0"
                      justify="center"
                    >
                      <v-progress-circular
                        color="grey-lighten-5"
                        indeterminate
                      ></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
              </v-col>
            </v-row>
          </div>

          <div style="margin-top: 50px">
            <!-- Commentaires -->
            <h2>Commentaires</h2>
            <v-card v-if="listCommentaires.length" v-for="comment in listCommentaires" :key="comment.id" class="anecdote-card" style="padding-left: 20px; margin-bottom: 20px">
              <p class="anecdote-description">{{ comment.description }}</p>
            </v-card>
            <v-textarea class="text-input"
              v-model="nouveauCommentaire"
              label="Écrivez un commentaire..."
              outlined
              auto-grow
            ></v-textarea>
            <v-btn class="btn" style="margin-bottom: 50px" @click="publierCommentaire">Publier</v-btn>
          </div>

        </div>
      </div>




    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { Swiper, SwiperSlide } from 'swiper/vue'
import 'swiper/css'
import 'swiper/css/navigation'
import { Navigation } from 'swiper/modules'

const API_FILM_URL = '/api/films'
const API_GROUPES_URL = '/api/joues/groupes/participants/all'
const defaultAvatar = '/images/avatar.jpg';
const defaultImage = '/images/affiche-default.jpg';

const selectedFilm = ref({})
const isLoading = ref(true)
const anecdotes = ref([])
const images = ref([])
const nouveauCommentaire = ref("")
const listCommentaires = ref([])

const acteurs = ref([])
const equipeTournage = ref([])
const postProductions = ref([])

const route = useRoute()

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

  selectedFilm.value = (await getData(`${API_FILM_URL}/${filmId}`)) || {}

  const dataAnecdotes = await getData(`${API_FILM_URL}/${filmId}/anecdotes`)
  let anecdoteList = []
  if (dataAnecdotes && dataAnecdotes._embedded && dataAnecdotes._embedded.anecdotes) {
    anecdoteList = dataAnecdotes._embedded.anecdotes
  }
  await Promise.all(anecdoteList.map(async (anecdote) => {
    if (anecdote._links && anecdote._links.participant && anecdote._links.participant.href) {
      const participantData = await getData(anecdote._links.participant.href)
      anecdote.participant = participantData
    }
  }))
  anecdotes.value = anecdoteList

  const dataImages = await getData(`${API_FILM_URL}/${filmId}/images`)
  images.value = dataImages && dataImages._embedded && dataImages._embedded.images
    ? dataImages._embedded.images
    : []
  await fetchCommentairesByFilm(filmId)

  isLoading.value = false
}

async function fetchCommentairesByFilm(filmId) {
  const data = await getData(`/api/commentaires/search/findByFilm_IdFilm?idFilm=${filmId}`)
  listCommentaires.value = data && data._embedded && data._embedded.commentaires
    ? data._embedded.commentaires
    : []
}

async function publierCommentaire() {
  const filmId = route.params.filmId

  try {
    const response = await fetch(`/api/commentaires`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        description: nouveauCommentaire.value,
        id_film: filmId,
      })
    })
    if (!response.ok) {
      throw new Error(`Erreur lors de la publication du commentaire: ${response.status}`)
    }
    nouveauCommentaire.value = ""
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

async function getFilmParticipants(filmId) {
  const data = await getData(`${API_FILM_URL}/${filmId}/participants`)
  if (!data) return []
  return data.map(str => {
    const parts = str.split(" - Rôle:")
    return {
      fullName: parts[0].trim(),
      role: parts[1] ? parts[1].trim() : ''
    }
  })
}

async function getParticipantsByGroupe(groupe) {
  const endpoint = `${API_GROUPES_URL}?groupe=${encodeURIComponent(groupe)}`
  const data = await getData(endpoint)
  return data || []
}

async function getParticipants() {
  const filmId = route.params.filmId
  const filmParticipants = await getFilmParticipants(filmId)
  const filmNames = filmParticipants.map(item => item.fullName)

  const allActeurs = await getParticipantsByGroupe('Acteur')
  acteurs.value = allActeurs.filter(item =>
    filmNames.includes(`${item.participant.nom} ${item.participant.prenom}`)
  )

  const allEquipe = await getParticipantsByGroupe('EquipeDeTournage')
  equipeTournage.value = allEquipe.filter(item =>
    filmNames.includes(`${item.participant.nom} ${item.participant.prenom}`)
  )

  const allPostProd = await getParticipantsByGroupe('PostProduction')
  postProductions.value = allPostProd.filter(item =>
    filmNames.includes(`${item.participant.nom} ${item.participant.prenom}`)
  )
}

onMounted(() => {
  const filmId = route.params.filmId
  getFilmDetail(filmId)
  getParticipants()
})
</script>

<style scoped>
.loading {
  text-align: center;
  font-size: 1.5rem;
  margin-top: 50px;
}

.contenu{
  background-color: var(--color-background);
  color: var(--color-text);
}

.affiche {
  position: sticky;
  top: 90px; /* Fixe l'élément à 20px du haut */
  align-self: flex-start; /* Pour éviter que ça ne s'étire en hauteur */
}

.caroussel-container{
  width: 70%;
  margin-top: 50px;
}

.affiche{
  width: 250px;
  height: auto;
}

.icon-placeholder {
  font-size: 48px;
}

.participants-card {
  padding: 8px;
  width: 200px;
}

.anecdote-card {
  margin: 10px 0;
  padding: 10px;
  background-color: var(--color-background);
  color: var(--color-text);
  box-shadow: 0px 1px 4px rgba(102, 100, 100, 0.6);
}

.role {
  font-size: 0.9rem;
  color: #ccc;
}

.commentaires {
  width: 80%;
  max-width: 800px;
  margin: 40px 20px;
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

/* Swiper pour images exclusives */
.images-swiper {
  margin: 20px 0;
}

.images-swiper img {
  width: 100%;
  height: auto;
  border-radius: 8px;
  object-fit: cover;
}

.btn{
  background-color: var(--color-button);
  color: var(--color-text);
  border-radius: 20px;
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-top: 10px;
}

.btn-bd{
  border: 1px solid var(--color-text);
  color: var(--color-text);
  background-color: transparent;
  border-radius: 20px;
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-top: 10px;
}

/* Personnalisation des flèches de navigation */
:deep(.swiper-button-prev),
:deep(.swiper-button-next) {
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

:deep(.swiper-button-prev)::after,
:deep(.swiper-button-next)::after {
  font-size: 16px;
  font-weight: bold;
  color: var(--color-text);
  transition: transform 0.3s ease;
}

:deep(.swiper-button-prev:hover)::after,
:deep(.swiper-button-next:hover)::after {
  transform: scale(0.8);
}

.film-details {
  flex: 1;
  max-width: 1000px; /* Empêche ce conteneur de devenir trop large */
}

.swiper-wrapper {
  width: 1000px;/* Déjà défini dans le style inline, mais vous pouvez le mettre ici */
  margin: 0 auto;
  overflow: hidden; /* Pour s'assurer que rien ne déborde */
}
</style>
