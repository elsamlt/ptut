<template>
  <div class="container" style="margin-bottom: 30px">
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

        <!-- Espaceur pour pousser le bouton en bas -->
        <v-spacer></v-spacer>

        <!-- Bouton de déconnexion -->
        <v-list-item @click="logout" class="logout-item">
          <template v-slot:prepend>
            <v-icon color="red">mdi-logout</v-icon>
          </template>
          <v-list-item-title class="logout-text">Se Déconnecter</v-list-item-title>
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

function logout() {
  // Récupérer le token JWT depuis le localStorage ou sessionStorage
  const token = localStorage.getItem('jwtToken'); // ou sessionStorage.getItem('jwtToken')

  // Définir les headers avec le token d'authentification
  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  // Appeler l'API de déconnexion
  return fetch('/api/logout', {
    method: 'POST',
    headers: headers
  })
    .then(response => {
      if (response.ok) {
        // Si la déconnexion réussit, supprimer le token du stockage
        localStorage.removeItem('token'); // ou sessionStorage.removeItem('jwtToken')

        // Supprimer également toutes les autres données utilisateur si nécessaire
        localStorage.removeItem('user');
        // ... autres données à supprimer

        // Rediriger vers la page de connexion ou la page d'accueil
        window.location.href = '/'; // ou une autre page

        return true;
      } else {
        // Gérer les erreurs
        console.error('Erreur lors de la déconnexion');
        return false;
      }
    })
    .catch(error => {
      console.error('Erreur réseau:', error);
      return false;
    });
}
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
  height: auto; /* Fait toute la hauteur de l'écran */
  min-height: 100vh;
}

  /* Style pour le bouton de déconnexion */
.logout-item {
  position: absolute;
  bottom: 20px;
  width: 100%;
  transition: background-color 0.3s;
}

.logout-item:hover {
  background-color: rgba(255, 0, 0, 0.2);
}
</style>
