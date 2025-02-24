<template>
  <div class="container">
    <!-- Barre de navigation latérale -->
      <v-list class="navigation-drawer" dense>
        <v-list-item
          v-for="item in items"
          :key="item.link"
          :to="item.link"
          :class="{ 'active-item': $route.path === item.link }"
        >
          <template v-slot:prepend>
            <v-icon>{{ item.icon }}</v-icon>
          </template>
          <v-list-item-title>{{ item.label }}</v-list-item-title>
        </v-list-item>
      </v-list>

    <hr class="custom-hr">

    <!-- Contenu principal -->
    <div class="active-page">
      <h1 class="text-h5 font-weight-bold">ESPACE ADMINISTRATION</h1>
      <p><strong>{{ currentTitle }}</strong></p>
      <router-view />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const items = ref([
  {
    label: "Films",
    icon: "mdi-film",
    link: "/admin/films",
  },
  {
    label: "Participants",
    icon: "mdi-account-group",
    link: "/admin/participants",
  },
  {
    label: "Actualité",
    icon: "mdi-newspaper",
    link: "/admin/actualites",
  },
  {
    label: "En ce moment",
    icon: "mdi-clock",
    link: "/admin/moment",
  },
  {
    label: "Anecdotes",
    icon: "mdi-message-text",
    link: "/admin/anecdotes",
  },
  {
    label: "Images Film",
    icon: "mdi-image-multiple",
    link: "/admin/images",
  },
  {
    label: "Commentaires",
    icon: "mdi-comment-multiple",
    link: "/admin/commentaires",
  },
]);

const currentTitle = computed(() => {
  const currentRoute = route.path.split('/').pop();
  const item = items.value.find(item => item.link.includes(currentRoute));
  return item ? item.label : "";
});
</script>

<style scoped>
/* Conteneur principal qui gère la mise en page */
.container {
  display: flex;
  flex-direction: row;
}

/* Barre de navigation */
.navigation-drawer {
  width: 250px; /* Fixe une largeur à la barre de navigation */
  flex-shrink: 0; /* Empêche la barre de navigation de rétrécir */
}

/* Contenu principal à droite de la navigation */
.active-page {
  flex-grow: 1;
  padding: 20px;
  overflow-y: auto;
}

/* Style pour l'élément actif du menu */
.active-item {
  background-color: var(--color-button);
  color: var(--color-text);
}

.custom-hr {
  width: 3px; /* Épaisseur du séparateur */
  background-color: var(--color-button);
  border: none; /* Supprime la bordure par défaut */
  height: 100vh; /* Fait toute la hauteur de l'écran */

}
</style>
