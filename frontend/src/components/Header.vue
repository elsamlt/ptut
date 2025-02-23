<template>
  <v-app-bar class="custom-app-bar d-flex justify-center" flat>
    <div class="d-flex justify-space-between align-center custom-div">
      <!-- Logo à gauche -->
      <router-link to="/" >
        <v-btn icon>
          <img :src="logo"/>
        </v-btn>
      </router-link>

      <div>
        <!-- Liens de navigation -->
        <router-link to="/" >
          <v-btn>accueil</v-btn>
        </router-link>
        <router-link to="/films">
          <v-btn>films</v-btn>
        </router-link>
        <router-link to="/about">
          <v-btn>à propos</v-btn>
        </router-link>
      </div>


      <!-- Icône de connexion à droite -->
      <router-link to="/admin">
        <v-btn class="btn" size="40px">
          <v-icon class="icon">mdi-lock</v-icon>
        </v-btn>
      </router-link>
    </div>
  </v-app-bar>
</template>

<script setup>
import logo from '@/assets/logo.png';

</script>

<style scoped>

.btn{
  background-color: var(--color-button);
  color: var(--color-text);
  border-radius: 50px;
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
}

.btn:hover .icon {
  transform: scale(0.8); /* Réduit la taille de l'icône à 80% */
}

.custom-app-bar {
  border-radius: 50px;
  background-color: var(--color-background-white);
  height: 50px;
}

.custom-div{
  width: 100%;
  margin-left: 20px;
  margin-right: 20px;
}

img{
  max-height: 40px;
  max-width: 40px;
}

</style>
