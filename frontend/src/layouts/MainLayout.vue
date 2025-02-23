<template>
  <v-app>
    <Header class="header" />
    <router-view />
    <Footer />
  </v-app>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
</script>

<style scoped>
.header {
  max-width: calc(100% - 20px); /* Enlève la marge pour centrer */
  margin: 10px; /* Centre le conteneur avec 20px de marge en haut et en bas */
  text-align: center; /* Centre le contenu du header si c'est du texte */
}
</style>
