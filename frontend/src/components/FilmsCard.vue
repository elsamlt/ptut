<template>
  <v-card class="mb-4 card">
    <v-row class="d-flex align-center">
      <v-col cols="3">
        <v-img :src="film.affiche ? film.affiche : defaultImage" aspect-ratio="1"></v-img>
      </v-col>
      <v-col cols="9" class="pa-4">
        <h3>{{film.titre}}</h3>
        <p class="details">{{film.annee}} - {{film.duree}} - {{film.genre}}</p>
        <p><strong>Synopsis :</strong> {{film.synopsis}}</p>
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
</style>
