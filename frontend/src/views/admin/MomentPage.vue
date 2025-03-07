<template>
  <v-container v-if="!showEditMoment && !showAddMoment">
    <MomentCard v-for="(moment, index) in listMoment" :key="moment.id" :moment="moment" :index="index" @edit="openEditForm(selectedMoment)"/>
  </v-container>
  <v-container v-if="showAddMoment">
    <AddMoment @add="handleMomentAdded" @closeForm="showAddMoment = false"/>
  </v-container>
  <v-container v-if="showEditMoment">
    <EditMoment :moment="selectedMoment" @edit="handleMomentEdit" @cancel="showEditMoment = false" />
  </v-container>

  <!-- Bouton flottant pour ajouter un moment -->
  <v-btn v-if="!showAddMoment" class="btn add-btn" size="40px">
    <v-icon class="icon" @click="showAddMoment = true; selectedMoment = null;">mdi-plus</v-icon>
  </v-btn>

  <!-- Dialog de confirmation -->
  <v-dialog v-model="dialogAdd" max-width="400px">
    <v-card>
      <v-card-text> Le moment a été ajouté avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogAdd = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogEdit" max-width="400px">
    <v-card>
      <v-card-text> Le moment a été modifié avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogEdit = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import AddMoment from "@/components/admin/AddMoment.vue";
import EditMoment from "@/components/admin/EditMoment.vue";
import MomentCard from "@/components/admin/MomentCard.vue";

import { ref, onMounted, reactive } from "vue";
import FilmsCard from "@/components/admin/FilmsCard.vue";
import ParticipantsCard from "@/components/admin/ParticipantsCard.vue";

const url = "/api/moments";
const listMoment = reactive([]);

const dialogAdd = ref(false);
const dialogEdit = ref(false);

const showEditMoment = ref(false);
const showAddMoment = ref(false);

const selectedMoment = ref(null);

const openEditForm = (moment) => {
  selectedMoment.value = { ...moment }; // Cloner pour éviter la modification directe
  showEditMoment.value = true;
};

/**
 * Récupérer les moments depuis l'API
 */
function fetchMoments() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      listMoment.splice(0, listMoment.length, ...dataJSON._embedded?.moments);
      console.log(listMoment)
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des moments :", error),
    );
}

/**
 * Sélectionner un momet et afficher ses détails
 */
function fetchMomentDetail(moment) {
  fetch(`${url}/${moment.id}`)
    .then((response) => response.json())
    .then((dataJSON) => {
      selectedMoment.value = dataJSON;
      showAddMoment.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des moments :", error),
    );
}

/**
 * Ajouter un nouveau moment via API
 */
const handleMomentAdded = (newMoment) => {
  // Ajouter le moment via l'API
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      titre: newMoment.titre,
      nomImg: newMoment.nomImg,
      description: newMoment.description,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchMoments(); // Rafraîchir la liste après l'ajout
      // Afficher la popup de confirmation
      dialogAdd.value = true;
      showAddMoment.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de l'ajout du moment :", error),
    );
};

/**
 * Modifier un moment comme faite via API
 */
const handleMomentEdit = (updatedMoment) => {
  fetch(`${url}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      titre: updatedMoment.titre,
      nomImg: updatedMoment.nomImg,
      description: updatedMoment.description,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchMomentDetail(updatedMoment);
      dialogEdit.value = true; // Afficher le message de confirmation
      showEditMoment.value = false;
    })
    .catch((error) => console.error("Erreur lors de la mise à jour :", error));
};

// Charger le moment au montage
onMounted(fetchMoments());
</script>

<style scoped>

/* Bouton flottant */
.add-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
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
