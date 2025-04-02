<template>
  <v-container>
    <v-row>
      <v-col cols="9" style="margin-bottom: 50px">
        <h1>TOUS LES FILMS</h1>
        <p>Découvrez une sélection de films captivants où humour, originalité et imagination se rencontrent. Chaque histoire vous transporte dans un monde à part, que ce soit au sein d’une école de médecine pas comme les autres, dans un futur proche intriguant ou au cœur d’aventures surprenantes.</p>
        <FilmsCard v-for="(film, index) in listFilms" :key="film.id" :index="index" :film="film"/>
      </v-col>
      <v-col cols="3">
        <News  layout="vertical" />
        <Moment layout="vertical" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import FilmsCard from "@/components/FilmsCard.vue";
import News from "@/components/News.vue";
import Moment from "@/components/Moment.vue";
import {onMounted, reactive} from "vue";

const url = "/api/films";
const listFilms = reactive([]);

/**
 * Récupérer les films depuis l'API
 */
function fetchFilms() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      listFilms.splice(0, listFilms.length, ...dataJSON._embedded.films);
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des films :", error),
    );
}

onMounted(fetchFilms);
</script>

<style scoped>

</style>

