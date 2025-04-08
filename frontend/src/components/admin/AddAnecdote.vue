<template>
  <v-form>
    <v-card class="pa-5">
      <v-container>
        <v-row justify="center" class="mt-4">
          <v-col cols="12" md="10">
            <v-text-field class="text-input" label="Description" v-model="anecdote.description" required></v-text-field>
          </v-col>
          <v-col cols="12" md="10">
            <v-select class="text-input" v-model="selectedFilm" :items="films" item-value="idFilm" item-title="titre" label="Film"></v-select>
          </v-col>
          <v-col cols="12" md="10">
            <v-select class="text-input" label="Participant" :disabled="!selectedFilm" v-model="selectedPerson" :items="personsArray" item-value="value"></v-select>
          </v-col>
        </v-row>
      </v-container>

      <!-- Boutons Annuler et Ajouter -->
      <v-row class="mt-4">
        <v-col cols="12" class="d-flex justify-end">
          <v-btn class="mr-2" @click="closeForm">Annuler</v-btn>
          <v-btn class="btn" @click="submitAnecdote">Ajouter</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-form>
</template>

<script setup>
import {ref, defineEmits, defineProps, watch} from "vue";

const emit = defineEmits(["add", "closeForm"]);

const props = defineProps({
  films: Array,
});

const selectedFilm = ref(null);
const selectedPerson = ref(null);
const listPersons = ref([]);
const personsArray = ref([]);

const anecdote = ref({
  idFilm:'',
  idParticipant: '',
  description: '',
});

watch(selectedFilm, (newFilm) => {
  anecdote.value.idFilm = newFilm;
});

watch(selectedPerson, (newAnecdote) => {
  anecdote.value.idParticipant = newAnecdote;
});

function fetchPersonsByFilm(filmId) {
  fetch(`/api/joues/films/participants?idFilm=${filmId}`)
    .then(response => response.json())
    .then(dataJSON => {
      personsArray.value = dataJSON.joues.map(joue => ({
        value: joue.participant.idParticipant,
        title: `${joue.participant.nom} ${joue.participant.prenom}`,
      }));
    })
    .catch(error => console.error("Erreur lors de la récupération des participants :", error));
}

// Soumettre l'anecdote
const submitAnecdote = () => {
  emit("add", anecdote.value);

  // Remise à zéro après ajout
  anecdote.value = {
    id_film:'',
    id_participant: '',
    description: '',
  };
};

// Annuler et enlever le texte rempli
const closeForm = () => {
  emit("closeForm");
};

watch(selectedFilm, (newFilmId) => {
  fetchPersonsByFilm(newFilmId);
  selectedPerson.value = null;
});

</script>

<style scoped>

.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-right: 10px;
}

</style>
