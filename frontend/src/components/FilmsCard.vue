<template>
  <v-card class="mb-4 card">
    <!-- Bouton en haut à droite -->
    <v-btn class="btn" size="40px" @click="handleFilmClick(film.idFilm)">
      <v-icon class="icon">mdi-information</v-icon>
    </v-btn>

    <v-row class="d-flex align-center">
      <v-col cols="3">
        <v-img :src="film.affiche ? film.affiche : defaultImage" aspect-ratio="1"></v-img>
      </v-col>
      <v-col cols="9" class="pa-4">
        <h3>{{ film.titre }}</h3>
        <p class="details">{{ film.annee }} - {{ film.duree }} - {{ film.genre }}</p>
        <p><strong>Synopsis :</strong> {{ film.synopsis }}</p>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup>
import {defineProps} from "vue";

const props = defineProps({
  film: Object,
});

const defaultImage = '/images/affiche-default.jpg';

const handleFilmClick = (idFilm) => {
  console.log("Film sélectionné, ID :", idFilm);
};

</script>

<style scoped>
.details{
  margin-bottom: 15px;
}

h3{
  font-size: 22px;
}

.card{
  margin-top: 20px;
}

.btn:hover .icon {
  transform: scale(0.8);
  transition: transform 0.3s ease;
}

/* Position du bouton en haut à droite */
.btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: var(--color-button);
  color: var(--color-text);
  border-radius: 50px;
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
}

.icon {
  font-size: 24px;
}
</style>
