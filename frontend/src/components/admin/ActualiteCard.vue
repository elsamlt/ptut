<template>
      <div class="d-flex flex-column align-center">
        <v-card class="card" v-if="embedUrl">
          <div class="iframe-container">
            <iframe
              :src="embedUrl"
              frameborder="0"
              allowfullscreen>
            </iframe>
          </div>
        </v-card>
        <div>
          <v-btn class="btn" size="40px" @click="editActu">
            <v-icon class="icon">mdi-pencil</v-icon>
          </v-btn>
        </div>
      </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, watch } from "vue";

const props = defineProps({
  actu: Object,
});

// Emitteur d'événements
const emit = defineEmits(["edit"]);

const editActu = () => {
  emit("edit", props.actu); // Envoie le médicament au parent
};

const embedUrl = ref(""); // Stocke l'URL pour l'iframe

// Fonction pour mettre à jour `embedUrl`
const updateEmbedUrl = (lien) => {
  if (!lien) {
    embedUrl.value = ""; // Ne rien afficher si le lien est vide
    return;
  }

  const regex = /(?:youtube\.com\/watch\?v=|youtu\.be\/)([^&]+)/;
  const match = lien.match(regex);
  if (match && match[1]) {
    embedUrl.value = `https://www.youtube.com/embed/${match[1]}`;
  } else {
    embedUrl.value = lien; // Si ce n'est pas YouTube, utiliser le lien tel quel
  }
};

// Watch pour détecter les changements de `actu.lien`
watch(() => props.actu?.lien, (newLien) => {
  updateEmbedUrl(newLien);
}, { immediate: true }); // Exécuter immédiatement au montage du composant
</script>

<style scoped>

/* Ajuste la taille de la card */
.card {
  max-width: 350px;  /* Définit la largeur max */
  width: 100%;
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
  margin-top: 20px;
}

/* Conteneur de l'iframe pour s'adapter */
.iframe-container {
  width: 300px;  /* Ajuste la largeur */
  height: 170px; /* Ajuste la hauteur */
}

/* Ajuste l'iframe pour s'adapter au conteneur */
.iframe-container iframe {
  width: 100%;
  height: 100%;
  border-radius: 8px; /* Optionnel : coins arrondis */
}

.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  border-radius: 50px;
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-right: 10px;
}

.btn:hover .icon {
  transform: scale(0.8);
}
</style>
