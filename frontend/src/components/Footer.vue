<template>
  <v-footer class="custom-footer d-flex justify-center" padless>
    <v-container fluid>
      <v-row align="center" justify="space-between" no-gutters>
        <!-- Logo de l'école à gauche (redirige vers le site de l'école) -->
        <v-col cols="auto">
          <a href="https://isis.univ-jfc.fr/" target="_blank" rel="noopener noreferrer">
            <v-btn icon>
              <img :src="logo" alt="Logo École" />
            </v-btn>
          </a>
        </v-col>

        <!-- Liens de navigation -->
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

        <!-- Réseaux sociaux -->
        <v-col cols="auto" class="d-flex align-center">
          <a href="https://www.instagram.com/isis_ingenieur/" target="_blank" rel="noopener noreferrer">
            <v-btn icon>
              <v-icon class="social-icon">mdi-instagram</v-icon>
            </v-btn>
          </a>
          <a href="https://www.linkedin.com/school/ecole-dingenieurs-isis/posts/?feedView=all" target="_blank" rel="noopener noreferrer">
            <v-btn icon>
              <v-icon class="social-icon">mdi-linkedin</v-icon>
            </v-btn>
          </a>
          <a href="https://www.facebook.com/isis.castres/?locale=fr_FR" target="_blank" rel="noopener noreferrer">
            <v-btn icon>
              <v-icon class="social-icon">mdi-facebook</v-icon>
            </v-btn>
          </a>
          <a href="https://x.com/isis_ingenieur" target="_blank" rel="noopener noreferrer">
            <v-btn icon>
              <v-icon class="social-icon">mdi-twitter</v-icon>
            </v-btn>
          </a>
        </v-col>
      </v-row>
    </v-container>
  </v-footer>
</template>

<script setup>
import logo from '@/assets/logo.png';
</script>

<style scoped>
.custom-footer {
  background-color: var(--color-background-white);
  height: 50px;
  max-width: 100%;
  width: 100%;
  text-align: center;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  position: fixed;
  bottom: 0;
  left: 0;
}

img {
  max-height: 40px;
  max-width: 40px;
}

.social-icon {
  color: var(--color-primary);
}

.social-icon:hover {
  color: var(--color-text-dark);
}
</style>
