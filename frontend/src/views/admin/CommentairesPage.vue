<template>
  <v-row class="d-flex align-center mt-1 gap-x-4">
    <v-row class="d-flex align-center gap-x-4">
      <p>Afficher les commentaires de :</p>
      <v-col cols="4">
        <v-select class="select" v-model="selectedFilm" :items="AllOption" item-value="idFilm" item-title="titre" density="compact"></v-select>
      </v-col>
    </v-row>
  </v-row>
  <v-container>
    <CommentaireCard v-for="(com, index) in listCommentaires" :key="com.id" :com="com" :index="index" @delete="handlerDelete(com)"/>
  </v-container>

  <v-dialog v-model="dialogDelete" max-width="400px">
    <v-card>
      <v-card-text> Le commentaire été supprimé avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogDelete = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, onMounted, reactive, computed, watch } from "vue";
import CommentaireCard from "@/components/admin/CommentaireCard.vue";

const url = "/api/commentaires";
const listCommentaires = ref([]);
const listFilms = ref([]);
const selectedFilm = ref(null);

const AllOption = computed(() => [
  { idFilm: null, titre: 'Tous les films' },  // Option "Tous les films"
  ...listFilms.value, // Les films récupérés
]);

const dialogDelete = ref(false);

const selectedCom = ref(null);

const fetchCommentaires = (page = 1, size = 4) => {
  fetch(`${url}?page=${page - 1}&size=${size}`)  // L'API commence les pages à 0
    .then(response => response.json())
    .then(dataJSON => {
      listCommentaires.value = dataJSON._embedded?.commentaires || [];
    })
    .catch(error => console.error("Erreur lors de la récupération des commentaires :", error));
};

/**
 * Récupérer les commentaires d'un film
 */
function fetchCommentairesByFilm(filmId) {
  if (filmId === null) {
    // Si "Tous les films" est sélectionné, on récupère tous les commentaires
    fetchCommentaires();
  } else {
    // Sinon, on récupère les commentaires du film spécifique
    fetch(`${url}/search/findByFilm_IdFilm?idFilm=${filmId}`)
      .then(response => response.json())
      .then(dataJSON => {
        listCommentaires.value = dataJSON._embedded.commentaires || [];
      })
      .catch(error => console.error("Erreur lors de la récupération des commentaires :", error));
  }
}

/**
 * Récupérer les films depuis l'API
 */
function fetchFilms() {
  fetch('/api/films')
    .then((response) => response.json())
    .then((dataJSON) => {
      listFilms.value = dataJSON._embedded.films || [];
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des films :", error),
    );
}

/**
 * Supprimer un commentaire via API
 */
function handlerDelete(com) {
  fetch(`${url}/${com.idCommentaire}`, { method: "DELETE" })
    .then(response => {
      if (response.ok) fetchCommentairesByFilm(selectedFilm.value); // Recharger les commentaires du film
      dialogDelete.value = true;
      selectedCom.value = null;
    })
    .catch(error => console.error("Erreur lors de la suppression :", error));
}

// Observer le changement de film sélectionné
watch(selectedFilm, (newFilmId) => {
  fetchCommentairesByFilm(newFilmId);  // Recharge les commentaires lorsque la sélection change
});

// Charger les films au montage
onMounted(() => {
  fetchFilms();
  fetchCommentairesByFilm(null);
});

</script>

<style scoped>
/* Bouton flottant */
.add-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
}

.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  border-radius: 50px;
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-right: 10px;
}

.btn:hover .icon {
  transform: scale(0.8);
}

.d-flex{
  margin: 5px;
}

.select{
  margin-top: 20px;
}
</style>
