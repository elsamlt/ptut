<template>
  <v-container>
    <v-text-field
      v-model="youtubeUrl"
      label="Lien YouTube"
      placeholder="Colle le lien ici"
      @input="generateEmbedUrl"
    ></v-text-field>

    <v-row justify="center" class="mt-4">
      <v-col cols="12" md="8">
        <v-card v-if="embedUrl">
          <iframe
            width="100%"
            height="315"
            :src="embedUrl"
            frameborder="0"
            allowfullscreen>
          </iframe>
        </v-card>
      </v-col>
    </v-row>
  </v-container>

  <!-- Boutons Annuler et Ajouter -->
  <v-row class="mt-4">
    <v-col cols="12" class="d-flex justify-end">
      <v-btn class="mr-2" @click="closeForm">Annuler</v-btn>
      <v-btn class="btn" @click="submitActu">Ajouter</v-btn>
    </v-col>
  </v-row>
</template>

<script setup>
import { ref, defineEmits } from "vue";

const emit = defineEmits(["eventAdd", "closeForm"]);

const youtubeUrl = ref(""); // Stocke l'URL de YouTube
const embedUrl = ref(""); // Stocke l'URL pour l'iframe

const actu = ref({
  lien: youtubeUrl,
});

// Soumettre l'actualité
const submitActu = () => {
  emit("add", actu.value);

  // Remise à zéro après ajout
  actu.value = {
    lien: "",
  };
};

// Annuler et enlever le texte rempli
const closeForm = () => {
  emit("closeForm");
};

// Fonction pour transformer l'URL YouTube en embed
const generateEmbedUrl = () => {
  const regex = /(?:youtube\.com\/watch\?v=|youtu\.be\/)([^&]+)/;
  const match = youtubeUrl.value.match(regex);
  if (match && match[1]) {
    embedUrl.value = `https://www.youtube.com/embed/${match[1]}`;
  } else {
    embedUrl.value = "";
  }
};
</script>

<style scoped>

.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-right: 10px;
}

</style>
