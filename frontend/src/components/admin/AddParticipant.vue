<template>
  <v-container>
    <v-form>
      <v-card class="pa-5">
        <!-- Image -->
        <div class="d-flex flex-column align-center">
          <v-col cols="12" md="2" class="d-flex flex-column align-center">
            <img v-if="person.pdp" :src="person.pdp" height="100" class="mt-2"/>
            <v-row>
              <v-btn class="mt-2" icon="mdi-download" variant="text" @click="triggerFileInput"></v-btn>
              <input type="file" ref="fileInput" style="display: none" @change="handleFileUpload" />
              <v-btn @click="deleteImage" class="mt-2" color="red-lighten-2" icon="mdi-delete" variant="text"></v-btn>
            </v-row>
          </v-col>
          <!-- Affichage de l'erreur -->
          <v-col cols="10">
            <v-alert v-if="errorMessage" type="error" class="mt-2">
              {{ errorMessage }}
            </v-alert>
          </v-col>

          <!-- Form -->
          <v-col cols="12" md="10">
            <v-row>
              <v-col cols="6">
                <v-text-field class="text-input" label="Nom" v-model="person.prenom" required></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-text-field class="text-input" label="Prénom" v-model="person.nom" required></v-text-field>
              </v-col>

              <v-col>
                <!-- Liste dynamique des rôles -->
                <v-row v-for="(role, index) in roles" :key="index" class="align-center">
                  <v-col cols="4">
                    <v-select
                      class="select text-input"
                      v-model="role.id_film"
                      :items="films"
                      item-title="titre"
                      item-value="idFilm"
                      label="Film"
                      density="compact"
                    ></v-select>
                  </v-col>
                  <v-col cols="4">
                    <v-text-field
                      class="text-input"
                      label="Role"
                      v-model="role.role"
                      required
                      density="compact"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3">
                    <v-select
                      class="select text-input"
                      v-model="role.groupe"
                      :item-title="'label'"
                      :item-value="'value'"
                      :items="groupes"
                      label="Groupe"
                      density="compact"
                    ></v-select>
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

const props = defineProps({
  films: Array,
});

const emit = defineEmits(["eventAdd", "closeForm"]);

const roles = ref([]);
const fileInput = ref(null);
const groupes = ref([
  { label: "Acteur", value: "Acteur" },
  { label: "Équipe de tournage", value: "EquipeDeTournage" },
  { label: "Post-production", value: "PostProduction" },
]);

const errorMessage = ref("");

// Ajouter une ligne de rôle
const addRole = () => {
  roles.value.push({
    id_film: null,
    role: "",
    groupe: null,
  });
};

// Supprimer une ligne de rôle
const removeRole = (index) => {
  roles.value.splice(index, 1);
};

const person = ref({
  nom: "",
  prenom: "",
  pdp: null,
  roles: roles.value,
});

// Ouvrir la boîte de dialogue pour sélectionner un fichier
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click();
  }
};

// Soumettre le participant
const submitPerson = () => {
  emit("add", person.value);

  // Remise à zéro après ajout
  person.value = {
    nom: "",
    prenom: "",
    pdp: null,
    roles: roles.value,
  };
};

// Annuler et fermer le formulaire
const closeForm = () => {
  emit("closeForm");
};

const deleteImage = () => {
  person.value.pdp = null;
  errorMessage.value = "";
};

// Gestion de l'upload d'image
const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (!file) return;

  if (file.name.length > 15) {
    errorMessage.value = "Le nom du fichier est trop long.";
    return;
  }

  errorMessage.value = "";

  const reader = new FileReader();
  reader.onload = () => {
    person.value.pdp = reader.result; // Mettre à jour la prop affiche avec l'image en base64
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

