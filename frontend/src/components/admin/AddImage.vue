<template>
  <v-container>
    <v-form>
      <v-card class="pa-5">
        <div class="d-flex flex-column align-center">
          <!-- Image -->
          <v-col cols="12" md="2" class="d-flex flex-column align-center">
            <img v-if="image.img" :src="image.img" height="100" class="mt-2"/>
            <v-row>
              <v-btn class="mt-2" icon="mdi-download" variant="text" @click="triggerFileInput"></v-btn>
              <input type="file" ref="fileInput" style="display: none" @change="handleFileUpload" />
              <v-btn @click="deleteImage" class="mt-2" color="red-lighten-2" icon="mdi-delete" variant="text"></v-btn>
            </v-row>
          </v-col>
          <v-col cols="4">
            <v-select class="text-input"
              v-model="selectedFilm"
              :items="films"
              item-value="idFilm"
              item-title="titre"
              density="compact"
              label="Film"
            ></v-select>
          </v-col>
        </div>
        <!-- Boutons Annuler et Ajouter -->
        <v-row class="mt-4">
          <v-col cols="12" class="d-flex justify-end">
            <v-btn class="mr-2" @click="closeForm">Annuler</v-btn>
            <v-btn class="btn" @click="submitImage">Ajouter</v-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref, defineEmits, defineProps, watch } from "vue";

const emit = defineEmits(["add", "closeForm"]);
const props = defineProps({
  films: Array,
});

const fileInput = ref(null);
const selectedFilm = ref(null);

const image = ref({
  img: null,
  id_film: null,
});

watch(selectedFilm, (newFilm) => {
  image.value.idFilm = newFilm;
});

// Soumettre l'image
const submitImage = () => {
  emit("add", image.value);

  // Remise à zéro après ajout
  image.value = {
    img: null,
    id_film: "",
  };
};

// Annuler et fermer le formulaire
const closeForm = () => {
  emit("closeForm");
};

// Supprimer l'image
const deleteImage = () => {
  image.value.img = null;
};

// Ouvrir la boîte de dialogue pour sélectionner un fichier
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click();
  }
};

// Gestion de l'upload d'image
const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = () => {
    image.value.img = reader.result; // Mettre à jour la prop affiche avec l'image en base64
  };
  reader.readAsDataURL(file);
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

