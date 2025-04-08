<template>
  <v-row class="d-flex align-center gap-x-4" v-if="!showEditAnecdote && !showAddAnecdote">
    <p>Afficher les participants de :</p>
    <v-col cols="4">
      <v-select class="select" v-model="selectedFilm" :items="AllOption" item-value="idFilm" item-title="titre" density="compact"></v-select>
    </v-col>
  </v-row>
  <!-- Liste des anecdotes -->
  <v-container v-if="!showEditAnecdote && !showAddAnecdote">
    <AnecdoteCard v-for="(anecdote, index) in listAnecdotes" :key="anecdote.id" :index="index" @edit="openEditForm(anecdote)" :anecdote="anecdote"
               @delete="handlerDelete(anecdote)"/>
  </v-container>
  <v-container v-if="showAddAnecdote">
    <AddAnecdote @add="handleAnecdoteAdded" @closeForm="showAddAnecdote = false" :films="listFilms"/>
  </v-container>
  <v-container v-if="showEditAnecdote">
    <EditAnecdote :anecdote="selectedAnecdote" @edit="handleAnecdoteEdit" @cancel="showEditAnecdote = false" :films="listFilms"/>
  </v-container>

  <!-- Bouton flottant pour ajouter une anecdote -->
  <v-btn v-if="!showAddAnecdote && !showEditAnecdote" class="btn add-btn" size="40px" @click="showAddAnecdote = true; selectedAnecdote = null;">
    <v-icon class="icon">mdi-plus</v-icon>
  </v-btn>

  <!-- Dialog de confirmation -->
  <v-dialog v-model="dialogAdd" max-width="400px">
    <v-card>
      <v-card-text> L'anecdote a été ajoutée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogAdd = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogDelete" max-width="400px">
    <v-card>
      <v-card-text> L'anecdote a été supprimée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogDelete = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogEdit" max-width="400px">
    <v-card>
      <v-card-text> L'anecdote a été modifiée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogEdit = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import {ref, onMounted, reactive, computed, watch} from "vue";
import AnecdoteCard from "@/components/admin/AnecdoteCard.vue";
import AddAnecdote from "@/components/admin/AddAnecdote.vue";
import EditAnecdote from "@/components/admin/EditAnecdote.vue";

const url = "/api/anecdotes";
const listAnecdotes = reactive([]);

const dialogAdd = ref(false);
const dialogDelete = ref(false);
const dialogEdit = ref(false);

const showEditAnecdote = ref(false);
const showAddAnecdote = ref(false);

const selectedAnecdote = ref(null);

const listFilms = ref([]);
const selectedFilm = ref(null);

const AllOption = computed(() => [
  { idFilm: null, titre: 'Tous les films' },  // Option "Tous les films"
  ...listFilms.value, // Les films récupérés
]);

const openEditForm = (anecdote) => {
  selectedAnecdote.value = { ...anecdote }; // Cloner pour éviter la modification directe
  showEditAnecdote.value = true;
};

/**
 * Récupérer les anecdotes depuis l'API
 */
function fetchAnecdotes() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      listAnecdotes.splice(0, listAnecdotes.length, ...dataJSON);
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des anecdotes :", error),
    );
}

function fetchAnecdotesByFilm(filmId) {
  if (filmId === null) {
    fetchAnecdotes();
  } else {
    fetch(`/api/films/${filmId}/anecdotes`)
      .then(response => response.json())
      .then(dataJSON => {
        listAnecdotes.splice(0, listAnecdotes.length, ...dataJSON._embedded.anecdotes);
      })
      .catch(error => console.error("Erreur lors de la récupération des participants :", error));
  }
}

/**
 * Ajouter une nouvelle anecdote via API
 */
const handleAnecdoteAdded = (newAnecdote) => {
  // Ajouter l'anecdote via l'API
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      id_film:newAnecdote.idFilm,
      id_participant: newAnecdote.idParticipant,
      description: newAnecdote.description,
    }),
  })
    .then(() => {
      fetchAnecdotes(); // Rafraîchir la liste après l'ajout
      // Afficher la popup de confirmation
      dialogAdd.value = true;
      showAddAnecdote.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de l'ajout de l'anecdote :", error),
    );
};

/**
 * Supprimer une anecdote via API
 */
function handlerDelete(anecdote) {
  fetch(`${url}/${anecdote.idAnecdote}`, { method: "DELETE" })
    .then((response) => {
      if (response.ok) fetchAnecdotes();
      dialogDelete.value = true;
      selectedAnecdote.value = null;
    })
    .catch((error) => console.error("Erreur lors de la suppression :", error));
}

/**
 * Modifier une anecdote comme faite via API
 */
const handleAnecdoteEdit = (updatedAnecdote) => {
  fetch(`${url}/${updatedAnecdote.idAnecdote}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      id_film:updatedAnecdote.id_film,
      id_participant: updatedAnecdote.id_participant,
      description: updatedAnecdote.description,
    }),
  })
    .then(() => {
      fetchAnecdotes();
      dialogEdit.value = true; // Afficher le message de confirmation
      showEditAnecdote.value = false;
    })
    .catch((error) => console.error("Erreur lors de la mise à jour :", error));
};

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

// Observer le changement de film sélectionné
watch(selectedFilm, (newFilmId) => {
  fetchAnecdotesByFilm(newFilmId);
});

// Charger les anecdotes au montage
onMounted(() => {
  fetchFilms();
  fetchAnecdotesByFilm(null);
});
</script>

<style scoped>

/* Bouton flottant */
.add-btn {
  position: fixed;
  bottom: 70px;
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
</style>
