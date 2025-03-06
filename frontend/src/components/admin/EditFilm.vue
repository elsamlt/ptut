<template>
  <v-container>
    <v-form>
      <v-card class="pa-5">
        <v-row class="d-flex align-center">
          <!-- Image -->
          <v-col cols="12" md="2" class="d-flex flex-column align-center">
            <img v-if="film.affiche" :src="`/img/${film.affiche}`" height="150" class="mt-2"/>
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
                <v-text-field class="text-input" label="Titre" v-model="film.titre" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field class="text-input" label="Année de sortie" v-model="film.annee" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field class="text-input" label="Genre" v-model="film.genre" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field class="text-input" label="Durée" v-model="film.duree" required></v-text-field>
              </v-col>
            </v-row>

            <v-textarea class="text-input" label="Synopsis" v-model="film.synopsis" auto-grow required></v-textarea>

            <v-row>
              <v-col cols="12" md="6">
                <v-text-field class="text-input" label="Lien du film" v-model="film.urlFilm" required></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field class="text-input" label="Lien de la bande d’annonce" v-model="film.urlBA"></v-text-field>
              </v-col>
            </v-row>
          </v-col>
        </v-row>

        <!-- Boutons Annuler et Ajouter -->
        <v-row class="mt-4">
          <v-col cols="12" class="d-flex justify-end">
            <v-btn class="mr-2" @click="closeForm">Annuler</v-btn>
            <v-btn class="btn" @click="submitFilm">Enregistrer</v-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";

// Récupérer les props
const props = defineProps({ film: Object });
const emit = defineEmits(["edit", "cancel"]);

// Cloner l'objet film pour éviter de modifier directement la prop
const film = ref({ ...props.film });
const fileInput = ref(null);

// Fonction de soumission
const submitFilm = () => {
  if (film.value.photo == props.film.photo) {
    film.value.photo = props.film.photo; // photo initiale
  }
  emit("edit", film.value);
};

// Annuler et fermer le formulaire
const closeForm = () => {
  emit("cancel");
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
    film.value.photo = reader.result;
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
