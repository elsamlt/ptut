<template>
  <v-container>
    <v-form>
      <v-card class="pa-5">
          <!-- Image -->
        <div class="d-flex flex-column align-center">
          <v-col class="d-flex flex-column align-center">
            <!--<img v-if="person.pdp" src="./Capture.png" height="150"/>-->
            <!--<img v-if="person.pdp" :src="person.pdp" height="150" class="mt-2"/>-->
            <!--<v-file-input
              label="Télécharger une image"
              accept="image/*"
              prepend-icon="mdi-camera"
              @change="previewImage"
            ></v-file-input>-->
            <v-row>
              <v-btn icon="mdi-download" variant="text"></v-btn>
              <v-btn @click="deleteImage" color="red-lighten-2" icon="mdi-delete" variant="text"></v-btn>
            </v-row>
          </v-col>

          <!-- Form -->
          <v-col cols="12" md="10">
            <v-row>
              <v-col cols="6">
                <v-text-field label="Nom" v-model="person.prenom" required></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-text-field label="Prénom" v-model="person.nom" required></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-text-field label="Role" v-model="person.genre" required></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-select class="select" v-model="selected" :item-props="itemProps" :items="items" label="Film"></v-select>
              </v-col>

              <v-col>
              <!-- Liste dynamique des rôles -->
              <v-row v-for="(role, index) in roles" :key="index" class="align-center">
                <v-col cols="6">
                  <v-text-field label="Role" v-model="role.name" required></v-text-field>
                </v-col>
                <v-col cols="5">
                  <v-select v-model="role.film" :items="items" label="Film"></v-select>
                </v-col>
                <v-col>
                  <v-btn icon="mdi-delete" color="red" variant="text" @click="removeRole(index)"></v-btn>
                </v-col>
              </v-row>

              <!-- Bouton Ajouter un rôle -->
              <v-row class="mt-2">
                <v-col cols="12" class="d-flex justify-center">
                  <v-btn class="btn" @click="addRole">
                    <v-icon left>mdi-plus</v-icon> Ajouter un rôle
                  </v-btn>
                </v-col>
              </v-row>
              </v-col>
            </v-row>
          </v-col>
        </div>

        <!-- Boutons Annuler et Ajouter -->
        <v-row class="mt-4">
          <v-col cols="12" class="d-flex justify-end">
            <v-btn class="mr-2" @click="closeForm">Annuler</v-btn>
            <v-btn class="btn" @click="submitPerson">Ajouter</v-btn>
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
const roles = ref([]); // Tableau dynamique des rôles
const items = ref(["Film 1", "Film 2", "Film 3"]); // Films disponibles

// Ajouter une ligne de rôle
const addRole = () => {
  roles.value.push({ name: "", person: null });
};

// Supprimer une ligne de rôle
const removeRole = (index) => {
  roles.value.splice(index, 1);
};

const person = ref({
  nom: "",
  prenom: "",
  pdp: photo,
});

// Soumettre le participant
const submitPerson = () => {
  emit("add", person.value);

  // Remise à zéro après ajout
  person.value = {
    nom: "",
    prenom: "",
    pdp: null,
  };
};

// Annuler et fermer le formulaire
const closeForm = () => {
  emit("closeForm");
};

const deleteImage = () => {
  photo.value = null;
  person.value.affiche = null;
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

