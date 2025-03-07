<template>
  <v-footer class="custom-footer d-flex justify-center" padless>
      <v-row align="center" justify="space-between" no-gutters style="padding-top: 10px">
        <!-- Logo de l'école à gauche (redirige vers le site de l'école) -->
        <v-col>
          <a href="https://isis.univ-jfc.fr/" target="_blank" rel="noopener noreferrer">
            <img :src="logo" alt="Logo École" />
          </a>
        </v-col>

        <!-- Liens de navigation -->
        <v-col>
          <router-link class="a" to="/">Accueil</router-link>
          <router-link class="a" to="/films">Films</router-link>
          <router-link class="a" to="/about">À propos</router-link>
          <a class="a" href="https://isis.univ-jfc.fr/mentions-legales" target="_blank" rel="noopener noreferrer">Mentions légales</a>
        </v-col>

        <!-- Réseaux sociaux -->
        <v-col >
          <a href="https://www.instagram.com/isis_ingenieur/" target="_blank" rel="noopener noreferrer">
            <v-icon class="social-icon">mdi-instagram</v-icon>
          </a>
          <a href="https://www.linkedin.com/school/ecole-dingenieurs-isis/posts/?feedView=all" target="_blank" rel="noopener noreferrer">
            <v-icon class="social-icon">mdi-linkedin</v-icon>
          </a>
          <a href="https://www.facebook.com/isis.castres/?locale=fr_FR" target="_blank" rel="noopener noreferrer">
            <v-icon class="social-icon">mdi-facebook</v-icon>
          </a>
          <a href="https://x.com/isis_ingenieur" target="_blank" rel="noopener noreferrer">
            <v-icon class="social-icon">mdi-twitter</v-icon>
          </a>
        </v-col>
      </v-row>
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
  padding: 0;
}

.a{
  margin-right: 10px;
  margin-left: 10px;
}

.a:hover {
  color: var(--color-primary);
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
