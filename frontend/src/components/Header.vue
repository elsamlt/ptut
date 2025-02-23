<template>
  <v-app-bar class="custom-app-bar d-flex justify-center" flat>
    <v-container fluid>
      <v-row align="center" justify="space-between" no-gutters>
        <!-- Logo à gauche -->
        <v-col cols="auto">
          <router-link to="/">
            <v-btn icon>
              <img :src="logo" />
            </v-btn>
          </router-link>
        </v-col>

        <!-- Boutons de navigation (Desktop) -->
        <v-col class="d-none d-md-flex justify-center">
          <router-link to="/">
            <v-btn>Accueil</v-btn>
          </router-link>
          <router-link to="/films">
            <v-btn>Films</v-btn>
          </router-link>
          <router-link to="/about">
            <v-btn>À propos</v-btn>
          </router-link>
        </v-col>

        <!-- Icône de connexion -->
        <v-col cols="auto" class="d-none d-md-flex">
          <router-link to="/admin">
            <v-btn class="btn" size="40px">
              <v-icon class="icon">mdi-lock</v-icon>
            </v-btn>
          </router-link>
        </v-col>

        <!-- Icône du menu burger (Mobile) -->
        <v-col cols="auto" class="d-md-none">
          <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
        </v-col>
      </v-row>
    </v-container>
  </v-app-bar>

  <!-- Menu latéral pour mobile -->
  <v-navigation-drawer v-model="drawer" temporary>
    <v-list>
      <v-list-item to="/" @click="drawer = false">Accueil</v-list-item>
      <v-list-item to="/films" @click="drawer = false">Films</v-list-item>
      <v-list-item to="/about" @click="drawer = false">À propos</v-list-item>
      <v-list-item to="/admin" @click="drawer = false">
        <v-icon left size="15px">mdi-lock</v-icon> Admin
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script setup>
import { ref } from 'vue';
import logo from '@/assets/logo.png';

const drawer = ref(false);
</script>

<style scoped>
.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  border-radius: 50px;
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
}

.btn:hover .icon {
  transform: scale(0.8);
}

.custom-app-bar {
  border-radius: 50px;
  background-color: var(--color-background-white);
  height: 50px;
  max-width: calc(100% - 20px); /* Enlève la marge pour centrer */
  margin: 10px; /* Centre le conteneur avec 20px de marge en haut et en bas */
  text-align: center; /* Centre le contenu du header si c'est du texte */
}

img {
  max-height: 40px;
  max-width: 40px;
}
</style>
