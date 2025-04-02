<template>
  <v-card class="participants-card">
    <v-row align="center">
      <v-col cols="2">
      </v-col>
      <v-col>
        <span class="text-h6">{{ film ? film.titre : 'Chargement du film...' }}</span>
        <p>{{ com.description }}</p>
      </v-col>
      <v-col cols="auto">
        <v-btn class="btn" size="40px" @click="deleteCommentaire">
          <v-icon class="icon">mdi-delete</v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup>
import {ref} from "vue";
import { defineProps, defineEmits, onMounted, watch } from "vue";

// Emitteur d'événements
const emit = defineEmits(["delete"]);

const props = defineProps({
  com: Object,
});

const film = ref(null);

const deleteCommentaire = () => {
  emit("delete", props.com.idCommentaire);
};

/**
 * Récupérer le film depuis l'API
 */
// Fonction pour récupérer le film associé au commentaire
const fetchFilm = () => {
  const urlToFetch = props.com?._links?.film?.href
    ? props.com._links.film.href
    : `/api/films/${props.com.id_film}`;

  fetch(urlToFetch)
    .then((response) => response.json())
    .then((dataJSON) => {
      film.value = dataJSON; // On stocke le film dans `film`
    })
    .catch((error) =>
      console.error('Erreur lors de la récupération des films :', error)
    );
};

// Appel de `fetchFilm` dès que le composant est monté
onMounted(() => {
  fetchFilm();
});

// Regarde si le commentaire change et recharge le film
watch(() => props.com, fetchFilm, { deep: true });
</script>

<style scoped>

.participants-card{
  padding: 10px;
  margin-bottom: 20px;
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

.text-h6{
  margin-right: 10px;
}
</style>
