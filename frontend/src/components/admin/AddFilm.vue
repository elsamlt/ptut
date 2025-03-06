<template>
  <v-container>
    <v-form>
      <v-card class="pa-5">
        <v-row class="d-flex align-center">
          <!-- Image -->
          <v-col cols="12" md="2" class="d-flex flex-column align-center">
            <img v-if="film.affiche" :src="film.affiche" height="100" class="mt-2"/>
            <v-row>
              <v-btn class="mt-2" icon="mdi-download" variant="text" @click="triggerFileInput"></v-btn>
              <input type="file" ref="fileInput" style="display: none" @change="handleFileUpload" />
              <v-btn @click="deleteImage" class="mt-2" color="red-lighten-2" icon="mdi-delete" variant="text"></v-btn>
            </v-row>
          </v-col>

          <!-- Form -->
          <v-col cols="12" md="10">
            <v-row>
              <v-col cols="12" md="6">
                <v-text-field label="Titre" v-model="film.titre" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field label="Année de sortie" v-model="film.annee" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field label="Genre" v-model="film.genre" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field label="Durée" v-model="film.duree" required></v-text-field>
              </v-col>
            </v-row>

            <v-textarea label="Synopsis" v-model="film.synopsis" auto-grow required></v-textarea>

            <v-row>
              <v-col cols="12" md="6">
                <v-text-field label="Lien du film" v-model="film.urlFilm" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field label="Lien de la bande d’annonce" v-model="film.urlBA"></v-text-field>
              </v-col>
            </v-row>
          </v-col>
        </v-row>

        <!-- Boutons Annuler et Ajouter -->
        <v-row class="mt-4">
          <v-col cols="12" class="d-flex justify-end">
            <v-btn class="mr-2" @click="closeForm">Annuler</v-btn>
            <v-btn class="btn" @click="submitFilm">Ajouter</v-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";

const emit = defineEmits(["add", "closeForm"]);
const fileInput = ref(null);

const film = ref({
  titre: "",
  synopsis: "",
  genre: "",
  annee: "",
  duree: "",
  affiche: null,
  urlFilm: "",
  urlBA: "",
});

// Soumettre le film
const submitFilm = () => {
  emit("add", film.value);
  console.log("Film ajouté :", film.value);

  // Remise à zéro après ajout
  film.value = {
    titre: "",
    synopsis: "",
    genre: "",
    annee: "",
    duree: "",
    affiche: null,
    urlFilm: "",
    urlBA: "",
  };
};

// Annuler et fermer le formulaire
const closeForm = () => {
  emit("closeForm");
};

// Supprimer l'image
const deleteImage = () => {
  film.value.affiche = null;
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
    film.value.affiche = reader.result; // Mettre à jour la prop affiche avec l'image en base64
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

