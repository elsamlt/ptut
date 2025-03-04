<template>
  <v-container>
    <v-card class="pa-5 mx-auto" max-width="500">
      <v-form @submit.prevent="submitForm">
        <v-row justify="center">
          <!-- Image Upload -->
          <v-col cols="12" class="text-center">
            <v-avatar size="150">
              <v-img v-if="imagePreview" :src="imagePreview"></v-img>
              <v-icon v-else size="80">mdi-image</v-icon>
            </v-avatar>
            <v-file-input label="Télécharger une image" accept="image/*" @change="onFileChange"></v-file-input>
          </v-col>

          <!-- Titre -->
          <v-col cols="12">
            <v-text-field v-model="title" label="Titre" required></v-text-field>
          </v-col>

          <!-- Description -->
          <v-col cols="12">
            <v-textarea v-model="description" label="Description" auto-grow required></v-textarea>
          </v-col>

          <!-- Bouton d'ajout -->
          <v-col cols="12" class="text-center">
            <v-btn color="primary" type="submit">Ajouter</v-btn>
          </v-col>
        </v-row>
      </v-form>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref } from "vue";

const title = ref("");
const description = ref("");
const imagePreview = ref(null);

const onFileChange = (event) => {
  const file = event.target.files[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = () => {
    imagePreview.value = reader.result;
  };
  reader.readAsDataURL(file);
};

const submitForm = () => {
  console.log("Titre :", title.value);
  console.log("Description :", description.value);
  console.log("Image :", imagePreview.value);

  // Réinitialiser le formulaire après soumission
  title.value = "";
  description.value = "";
  imagePreview.value = null;
};
</script>

<style scoped>
.v-avatar {
  border: 2px solid #ccc;
}
</style>
