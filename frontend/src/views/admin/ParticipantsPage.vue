<template>
  <v-row v-if="!showEditPerson && !showAddPerson" class="d-flex align-center mt-1 gap-x-4">
    <v-row class="d-flex align-center gap-x-4">
      <p>Afficher les participants de :</p>
      <v-col cols="4">
        <v-select class="select" v-model="selectedFilm" :items="AllOption" item-value="idFilm" item-title="titre" density="compact"></v-select>
      </v-col>
    </v-row>
    <v-col cols="3" style="display: none">
      <v-text-field
        v-model="searchQuery"
        label="Rechercher un participant..."
        variant="outlined"
        prepend-inner-icon="mdi-magnify"
      ></v-text-field>
    </v-col>
  </v-row>
  <v-container v-if="!showEditPerson && !showAddPerson">
    <ParticipantsCard v-for="(person, index) in listPersons" :key="person.id" :person="person" :index="index" @edit="openEditForm(person)" @delete="handlerDelete(person)"/>
    <!-- Pagination controls -->
    <v-row justify="center" class="mt-4">
      <v-pagination
        v-model="currentPage"
        :length="totalPages"
        :total-visible="5"
      ></v-pagination>
    </v-row>
  </v-container>
  <v-container v-if="showAddPerson">
    <AddParticipant :films="listFilms" @add="handlePersonAdded" @closeForm="showAddPerson = false"/>
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

import { ref, onMounted, reactive, computed, watch } from "vue";

const url = "/api/participants";
const listPersons = ref([]);
const itemsPerPage = 4;
const currentPage = ref(1);

const totalPages = ref(1);

const dialogAdd = ref(false);
const dialogDelete = ref(false);
const dialogEdit = ref(false);

const showEditPerson = ref(false);
const showAddPerson = ref(false);

const selectedPerson = ref(null);

const listFilms = ref([]);
const selectedFilm = ref(null);

const AllOption = computed(() => [
  { idFilm: null, titre: 'Tous les films' },  // Option "Tous les films"
  ...listFilms.value, // Les films récupérés
]);

const openEditForm = (person) => {
  selectedPerson.value = { ...person }; // Cloner pour éviter la modification directe
  showEditPerson.value = true;
};

/**
 * Récupérer les participants depuis l'API
 */
const fetchPersons = (page = 1) => {
  fetch(`${url}?page=${page - 1}&size=${itemsPerPage}`) // L’API commence les pages à 0
    .then(response => response.json())
    .then(dataJSON => {
      listPersons.value = dataJSON._embedded?.participants || [];
      totalPages.value = dataJSON.page?.totalPages || 1;
    })
    .catch(error => console.error("Erreur lors de la récupération des participants :", error));
};

function fetchPersonsByFilm(filmId, page = 1) {
  if (filmId === null) {
    fetchPersons(page);
  } else {
    fetch(`/api/films/participants?idFilm=${filmId}&page=${page - 1}&size=${itemsPerPage}`)
      .then(response => response.json())
      .then(dataJSON => {
        // Convertir l'objet en tableau en ignorant la clé "page"
        const participantsArray = Object.entries(dataJSON)
          .filter(([key]) => key !== "page")
          .map(([_, value]) => value);

        // Extraire uniquement les participants
        listPersons.value = participantsArray.map(item => item.participant);

        // Récupérer les infos de pagination
        totalPages.value = dataJSON.page?.totalPages || 1;
      })
      .catch(error => console.error("Erreur lors de la récupération des participants :", error));
  }
}

/**
 * Récupérer les films depuis l'API
 */
function fetchFilms() {
  fetch('/api/films')
    .then((response) => response.json())
    .then((dataJSON) => {
      listFilms.value = dataJSON._embedded.films || [];
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des films :", error),
    );
}

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

// Fonction pour récupérer l'ID du participant à partir du lien
async function getParticipantId(url) {
  try {
    // Faire la requête pour récupérer les données du participant
    const response = await fetch(url);
    const participantData = await response.json();

    // Retourner l'ID du participant
    return participantData.idParticipant;
  } catch (error) {
    console.error('Erreur lors de la récupération de l\'ID du participant:', error);
  }
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

// Observer le changement de film sélectionné
watch(selectedFilm, (newFilmId) => {
  fetchPersonsByFilm(newFilmId);
});

watch(currentPage, (newPage) => {
  if (selectedFilm.value === null) {
    fetchPersons(newPage);
  } else {
    fetchPersonsByFilm(selectedFilm.value, newPage);
  }
});

// Charger les participants au montage
// Charger les films au montage
onMounted(() => {
  fetchFilms();
  fetchPersonsByFilm(null);
});
</script>

<style scoped>
/* Bouton flottant */
.add-btn {
  position: fixed;
  bottom: 70px;
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
