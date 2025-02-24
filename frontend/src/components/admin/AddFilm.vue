<template>
  <v-container>
    <v-form>
      <v-card class="pa-5">
        <v-row class="d-flex align-center">
          <!-- Image -->
          <v-col cols="12" md="2" class="d-flex flex-column align-center">
            <!--<img v-if="film.affiche" src="./Capture.png" height="150"/>-->
            <!--<img v-if="film.affiche" :src="film.affiche" height="150" class="mt-2"/>-->
            <v-file-input
              label="Télécharger une image"
              accept="image/*"
              prepend-icon="mdi-camera"
              @change="previewImage"
            ></v-file-input>
            <v-row>
              <v-btn class="mt-2" icon="mdi-download" variant="text"></v-btn>
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

const emit = defineEmits(["eventAdd", "closeForm"]);

const photo = ref(null);

const film = ref({
  titre: "",
  synopsis: "",
  genre: "",
  annee: "",
  duree: "",
  affiche: photo,
  urlFilm: "",
  urlBA: "",
});

// Soumettre le film
const submitFilm = () => {
  emit("add", film.value);
  console.log(film.value.affiche);

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

// Annuler et enlever le texte rempli
const closeForm = () => {
  emit("closeForm");
};

const deleteImage = () => {
  photo.value = null;
  film.value.affiche = null;
};

const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (!file) return; // Si l'utilisateur annule la sélection du fichier, on ne fait rien
  // FileReader est un objet JavaScript permettant de lire le contenu d'un fichier
  // de manière asynchrone.
  const reader = new FileReader();
  reader.onload = () => {
    // definir le traitement asynchrone du contenu du fichier
    photo.value = reader.result; // --> convertit le contenu du fichier en base64
  };
  reader.readAsDataURL(file); // lance la lecture du fichier et donc la conversion en base64
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

