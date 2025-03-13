<template>
  <v-row v-if="!showEditPerson && !showAddPerson" class="d-flex align-center mt-1 gap-x-4">
    <v-row class="d-flex align-center gap-x-4">
      <p>Afficher les participants de :</p>
      <v-col cols="4">
        <v-select class="select" v-model="selected" :item-props="itemProps" :items="items" density="compact"></v-select>
      </v-col>
    </v-row>
    <v-col cols="3">
      <v-text-field
        v-model="searchQuery"
        label="Rechercher un participant..."
        variant="outlined"
        prepend-inner-icon="mdi-magnify"
      ></v-text-field>
    </v-col>
  </v-row>
  <v-container v-if="!showEditPerson && !showAddPerson">
    <ParticipantsCard v-for="(person, index) in listPersons" :key="person.id" :person="person" :index="index" @edit="openEditForm(selectedPerson)" @delete="handlerDelete(selectedPerson)"/>
    <!-- Pagination controls -->
    <v-row justify="center" class="mt-4">
      <v-btn :disabled="currentPage === 1" @click="prevPage">Précédent</v-btn>
      <span class="mx-3">Page {{ currentPage }} / {{ totalPages }}</span>
      <v-btn :disabled="currentPage === totalPages" @click="nextPage">Suivant</v-btn>
    </v-row>
  </v-container>
  <v-container v-if="showAddPerson">
    <AddParticipant @add="handlePersonAdded" @closeForm="showAddPerson = false"/>
  </v-container>
  <v-container v-if="showEditPerson">
    <EditParticipant :person="selectedPerson" @edit="handlePersonEdit" @cancel="showEditPerson = false" />
  </v-container>

  <!-- Bouton flottant pour ajouter un participant -->
  <v-btn v-if="!showEditPerson && !showAddPerson" class="btn add-btn" size="40px" @click="showAddPerson = true; selectedPerson = null;">
    <v-icon class="icon">mdi-plus</v-icon>
  </v-btn>

  <!-- Dialog de confirmation -->
  <v-dialog v-model="dialogAdd" max-width="400px">
    <v-card>
      <v-card-text> Le participant a été ajouté avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogAdd = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogDelete" max-width="400px">
    <v-card>
      <v-card-text> Le participant été supprimé avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogDelete = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>

import ParticipantsCard from "@/components/admin/ParticipantsCard.vue";
import EditParticipant from "@/components/admin/EditParticipant.vue";
import AddParticipant from "@/components/admin/AddParticipant.vue";

import { ref, onMounted, reactive, computed } from "vue";

const url = "/api/participants";
const listPersons = ref([]);
const totalPages = ref(1);
const currentPage = ref(1);

const dialogAdd = ref(false);
const dialogDelete = ref(false);
const dialogEdit = ref(false);

const showEditPerson = ref(false);
const showAddPerson = ref(false);

const selectedPerson = ref(null);

const openEditForm = (person) => {
  selectedPerson.value = { ...person }; // Cloner pour éviter la modification directe
  showEditPerson.value = true;
};

/**
 * Récupérer les participants depuis l'API
 */
/*function fetchPersons() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      console.log(dataJSON)
      listPersons.splice(0, listPersons.length, ...dataJSON._embedded.participants);
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des partcipants :", error),
    );
}*/
const fetchPersons = (page = 1, size = 4) => {
  fetch(`${url}?page=${page - 1}&size=${size}`)  // L'API commence les pages à 0
    .then(response => response.json())
    .then(dataJSON => {
      console.log("Données récupérées :", dataJSON);
      listPersons.value = dataJSON._embedded?.participants || [];
      totalPages.value = dataJSON.page?.totalPages || 1;
    })
    .catch(error => console.error("Erreur lors de la récupération des participants :", error));
};

// Fonction pour changer de page
const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
    fetchPersons(currentPage.value);
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
    fetchPersons(currentPage.value);
  }
};

/**
 * Sélectionner un participant et afficher ses détails
 */
function fetchPersonDetail(person) {
  fetch(`${url}/${person.id}`)
    .then((response) => response.json())
    .then((dataJSON) => {
      selectedPerson.value = dataJSON;
      showAddPerson.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des participants :", error),
    );
}

/**
 * Ajouter un nouveau participant via API
 */
const handlePersonAdded = (newPerson) => {
  // Ajouter le participant via l'API
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      nom: newPerson.nom,
      prenom: newPerson.prenom,
      pdp: newPerson.pdp,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchPersons(); // Rafraîchir la liste après l'ajout
      // Afficher la popup de confirmation
      dialogAdd.value = true;
      showAddPerson.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de l'ajout du participant :", error),
    );
};

/**
 * Supprimer un participant via API
 */
function handlerDelete(person) {
  fetch(`${url}/${person.id}`, { method: "DELETE" })
    .then((response) => {
      if (response.ok) fetchPersons();
      dialogDelete.value = true;
      selectedPerson.value = null;
    })
    .catch((error) => console.error("Erreur lors de la suppression :", error));
}

/**
 * Modifier un participant comme faite via API
 */
const handlePersonEdit = (updatedPerson) => {
  fetch(`${url}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      nom: updatedPerson.nom,
      prenom: updatedPerson.prenom,
      pdp: updatedPerson.pdp,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchPersonDetail(updatedPerson);
      dialogEdit.value = true; // Afficher le message de confirmation
      showEditPerson.value = false;
    })
    .catch((error) => console.error("Erreur lors de la mise à jour :", error));
};

// Charger les participants au montage
onMounted(fetchPersons);
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

.d-flex{
  margin: 5px;
}

.select{
  margin-top: 20px;
}
</style>
