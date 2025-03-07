<template>
  <v-container>
    <v-form>
      <v-card class="pa-5">
        <v-row class="d-flex align-center">
          <!-- Image -->
          <v-col cols="12" md="2" class="d-flex flex-column align-center">
            <img v-if="moment.nomImg" :src="moment.nomImg" height="100" class="mt-2"/>
            <v-row>
              <v-btn class="mt-2" icon="mdi-download" variant="text" @click="triggerFileInput"></v-btn>
              <input type="file" ref="fileInput" style="display: none" @change="handleFileUpload" />
              <v-btn @click="deleteImage" class="mt-2" color="red-lighten-2" icon="mdi-delete" variant="text"></v-btn>
            </v-row>
          </v-col>

          <!-- Form -->
          <v-col cols="12" md="10">
            <!-- Titre -->
            <v-col cols="12">
              <v-text-field class="text-input" v-model="moment.titre" label="Titre" required></v-text-field>
            </v-col>

            <!-- Description -->
            <v-col cols="12">
              <v-textarea class="text-input" v-model="moment.description" label="Description" auto-grow required></v-textarea>
            </v-col>
          </v-col>
        </v-row>

        <!-- Boutons Annuler et Ajouter -->
        <v-row class="mt-4">
          <v-col cols="12" class="d-flex justify-end">
            <v-btn class="mr-2" @click="closeForm">Annuler</v-btn>
            <v-btn class="btn" @click="submitMoment">Ajouter</v-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref, defineEmits, defineProps } from "vue";

// Récupérer les props
const props = defineProps({ moment: Object });
const emit = defineEmits(["edit", "cancel"]);
console.log(props.moment)

// Cloner l'objet moment pour éviter de modifier directement la prop
const moment = ref({ ...props.moment });
const fileInput = ref(null);

// Supprimer l'image
const deleteImage = () => {
  moment.value.nomImg = null;
};

// Fonction de soumission
const submitMoment = () => {
  if (moment.value.nomImg == props.moment.nomImg) {
    moment.value.nomImg = props.moment.nomImg; // photo initiale
  }
  emit("edit", moment.value);
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
