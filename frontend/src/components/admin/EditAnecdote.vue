<template>
  <v-container>
    <v-form>
      <v-card class="pa-5">
        <v-container>
          <v-row justify="center" class="mt-4">
            <v-col cols="12" md="10">
              <v-text-field class="text-input" label="Description" v-model="anecdote.description" required></v-text-field>
            </v-col>
            <v-col cols="12" md="10">
              <v-select class="text-input" label="Film" v-model="selectedFilm" :items="films" item-value="idFilm" item-title="titre"></v-select>
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
            <v-btn class="btn" @click="submitAnecdote">Enregistrer</v-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-form>
  </v-container>
</template>

<script setup>
import {ref, defineProps, defineEmits, watch} from "vue";

// Récupérer les props
const props = defineProps({
  anecdote: Object,
  films: Array,
});

const emit = defineEmits(["edit", "cancel"]);

// Cloner l'objet anecdote pour éviter de modifier directement la prop
const anecdote = ref({ ...props.anecdote });
const selectedFilm = ref(null);
const selectedPerson = ref(null);
const personsArray = ref([]);

selectedFilm.value = props.films.find(film => film.idFilm === props.anecdote.id_film) || null;

// Charger les participants dès qu’on a un film
if (selectedFilm.value) {
  fetchPersonsByFilm(selectedFilm.value.idFilm);
}

// Fonction de soumission
const submitAnecdote = () => {
  emit("edit", anecdote.value);
};

function fetchPersonsByFilm(filmId) {
  fetch(`/api/joues/films/participants?idFilm=${filmId}`)
    .then(response => response.json())
    .then(dataJSON => {
      personsArray.value = dataJSON.joues.map(joue => ({
        value: joue.participant.idParticipant,
        title: `${joue.participant.nom} ${joue.participant.prenom}`,
      }));

      // Important: définir selectedPerson.value APRÈS avoir rempli personsArray
      if (props.anecdote.id_participant) {
        // Trouver le participant correspondant dans le tableau nouvellement rempli
        const foundPerson = personsArray.value.find(
          person => person.value === props.anecdote.id_participant
        );

        // Assigner directement la valeur trouvée ou null
        selectedPerson.value = foundPerson ? foundPerson.value : null;

      }
    })
    .catch(error => console.error("Erreur lors de la récupération des participants :", error));
}

// Annuler et fermer le formulaire
const closeForm = () => {
  emit("cancel");
};

watch(selectedFilm, (newFilm) => {
  // Mettre à jour les participants
  if (newFilm) {
    fetchPersonsByFilm(newFilm.idFilm || newFilm);
    // Réinitialiser la sélection du participant
    selectedPerson.value = null;
    // Mettre à jour l'ID du film dans l'anecdote
    anecdote.value.id_film = newFilm.idFilm || newFilm;
  }
});

watch(selectedPerson, (newPerson) => {
  if (newPerson) {
    anecdote.value.id_participant = newPerson;
  }
});

watch(
  () => props.films,  // Observer les changements de films
  (newFilms) => {
    if (newFilms.length > 0) {
      // Si les films sont chargés et existent
      selectedFilm.value = newFilms.find(film => film.idFilm === props.anecdote.id_film) || null;
      // Si le film est trouvé, charger les participants
      if (selectedFilm.value) {
        fetchPersonsByFilm(selectedFilm.value.idFilm);
      }
    }
  },
  { immediate: true }  // S'exécuter immédiatement au montage
);

watch(
  () => props.anecdote,  // Observer si l'anecdote change
  (newAnecdote) => {
    if (newAnecdote && selectedFilm.value) {
      fetchPersonsByFilm(selectedFilm.value.idFilm); // Rechercher les participants à chaque changement de l'anecdote
    }
  }
);

</script>

<style scoped>
.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-right: 10px;
}
</style>
