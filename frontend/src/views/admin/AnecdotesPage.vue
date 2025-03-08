<template>
  <!-- Liste des anecdotes -->
  <v-container v-if="!showEditAnecdote && !showAddAnecdote">
    <AnecdoteCard v-for="(anecdote, index) in listAnecdotes" :key="anecdote.id" :index="index" @edit="openEditForm(anecdote)" :anecdote="anecdote"
               @delete="handlerDelete(selectedAnecdote)"/>
  </v-container>
  <v-container v-if="showAddAnecdote">
    <AddAnecdote @add="handleAnecdoteAdded" @closeForm="showAddAnecdote = false"/>
  </v-container>
  <v-container v-if="showEditAnecdote">
    <EditAnecdote :anecdote="selectedAnecdote" @edit="handleAnecdoteEdit" @cancel="showEditAnecdote = false" />
  </v-container>

  <!-- Bouton flottant pour ajouter une anecdote -->
  <v-btn v-if="!showAddAnecdote && !showEditAnecdote" class="btn add-btn" size="40px" @click="showAddAnecdote = true; selectedAnecdote = null;">
    <v-icon class="icon">mdi-plus</v-icon>
  </v-btn>

  <!-- Dialog de confirmation -->
  <v-dialog v-model="dialogAdd" max-width="400px">
    <v-card>
      <v-card-text> L'anecdote a été ajoutée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogAdd = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogDelete" max-width="400px">
    <v-card>
      <v-card-text> L'anecdote a été supprimée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogDelete = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogEdit" max-width="400px">
    <v-card>
      <v-card-text> L'anecdote a été modifiée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogEdit = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, onMounted, reactive } from "vue";
import AnecdoteCard from "@/components/admin/AnecdoteCard.vue";
import AddAnecdote from "@/components/admin/AddAnecdote.vue";
import EditAnecdote from "@/components/admin/EditAnecdote.vue";

const url = "/api/anecdotes";
const listAnecdotes = reactive([]);

const dialogAdd = ref(false);
const dialogDelete = ref(false);
const dialogEdit = ref(false);

const showEditAnecdote = ref(false);
const showAddAnecdote = ref(false);

const selectedAnecdote = ref(null);

const openEditForm = (anecdote) => {
  selectedAnecdote.value = { ...anecdote }; // Cloner pour éviter la modification directe
  showEditAnecdote.value = true;
};

/**
 * Récupérer les anecdotes depuis l'API
 */
function fetchAnecdotes() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      listAnecdotes.splice(0, listAnecdotes.length, ...dataJSON._embedded.anecdotes);
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des anecdotes :", error),
    );
}

/**
 * Sélectionner une anecdote et afficher ses détails
 */
function fetchAnecdoteDetail(anecdote) {
  fetch(`${url}/${anecdote.id}`)
    .then((response) => response.json())
    .then((dataJSON) => {
      selectedAnecdote.value = dataJSON;
      showAddAnecdote.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des anecdotes :", error),
    );
}

/**
 * Ajouter une nouvelle anecdote via API
 */
const handleAnecdoteAdded = (newAnecdote) => {
  // Ajouter l'anecdote via l'API
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      id_film:newAnecdote.id_film,
      id_participant: newAnecdote.id_participant,
      description: newAnecdote.description,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchAnecdotes(); // Rafraîchir la liste après l'ajout
      // Afficher la popup de confirmation
      dialogAdd.value = true;
      showAddAnecdote.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de l'ajout de l'anecdote :", error),
    );
};

/**
 * Supprimer une anecdote via API
 */
function handlerDelete(anecdote) {
  fetch(`${url}/${anecdote.id}`, { method: "DELETE" })
    .then((response) => {
      if (response.ok) fetchAnecdotes();
      dialogDelete.value = true;
      selectedAnecdote.value = null;
    })
    .catch((error) => console.error("Erreur lors de la suppression :", error));
}

/**
 * Modifier une anecdote comme faite via API
 */
const handleAnecdoteEdit = (updatedAnecdote) => {
  fetch(`${url}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      id_film:updatedAnecdote.id_film,
      id_participant: updatedAnecdote.id_participant,
      description: updatedAnecdote.description,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchAnecdoteDetail(updatedAnecdote);
      dialogEdit.value = true; // Afficher le message de confirmation
      showEditAnecdote.value = false;
    })
    .catch((error) => console.error("Erreur lors de la mise à jour :", error));
};

// Charger les anecdotes au montage
onMounted(fetchAnecdotes);
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
