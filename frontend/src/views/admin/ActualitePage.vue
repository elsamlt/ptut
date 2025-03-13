<template>
  <!-- Liste des actualité -->
  <v-container v-if="!showEditActu && !showAddActu" class="d-flex flex-column align-center">
    <v-row class="gap">
      <ActualiteCard v-for="(actu, index) in listActu" :key="actu.id" :actu="actu" :index="index" @edit="openEditForm(actu)"/>
    </v-row>
  </v-container>
  <v-container v-if="showAddActu">
    <AddActualite @add="handleActuAdded" @closeForm="showAddActu = false"/>
  </v-container>
  <v-container v-if="showEditActu">
    <EditActualite :actu="selectedActu" @edit="handleActuEdit" @cancel="showEditActu = false" />
  </v-container>

  <!-- Bouton flottant pour ajouter une actualité -->
  <v-btn v-if="!showEditActu && !showAddActu" class="btn add-btn" size="40px" @click="showAddActu = true; selectedActu = null;">
    <v-icon class="icon">mdi-plus</v-icon>
  </v-btn>

  <!-- Dialog de confirmation -->
  <v-dialog v-model="dialogAdd" max-width="400px">
    <v-card>
      <v-card-text> L'actualité a été ajoutée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogAdd = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogEdit" max-width="400px">
    <v-card>
      <v-card-text> L'actualité a été modifiée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogEdit = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import ActualiteCard from "@/components/admin/ActualiteCard.vue";
import EditActualite from "@/components/admin/EditActualite.vue";
import AddActualite from "@/components/admin/AddActualite.vue";

import { ref, onMounted, reactive } from "vue";

const url = "/api/actualites";
const listActu = reactive([]);

const dialogAdd = ref(false);
const dialogEdit = ref(false);

const showEditActu = ref(false);
const showAddActu = ref(false);

const selectedActu = ref(null);

const openEditForm = (actu) => {
  selectedActu.value = { ...actu }; // Cloner pour éviter la modification directe
  showEditActu.value = true;
};

/**
 * Récupérer les actualités depuis l'API
 */
function fetchActu() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      listActu.splice(0, listActu.length, ...dataJSON);
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des actualités :", error),
    );
}

/**
 * Sélectionner une actualité et afficher ses détails
 */
function fetchActuDetail(actu) {
  fetch(`${url}/${actu.id}`)
    .then((response) => response.json())
    .then((dataJSON) => {
      selectedActu.value = dataJSON;
      showAddActu.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des actualités :", error),
    );
}

/**
 * Ajouter une nouvelle actualité via API
 */
const handleActuAdded = (newActu) => {
  // Ajouter l'actualité via l'API
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      lien: newActu.lien,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchActu(); // Rafraîchir la liste après l'ajout
      // Afficher la popup de confirmation
      dialogAdd.value = true;
      showAddActu.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de l'ajout de l'actualité :", error),
    );
};

/**
 * Modifier une actualité comme faite via API
 */
const handleActuEdit = (updatedActu) => {
  fetch(`${url}/${updatedActu.idActu}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      lien: updatedActu.lien,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchActu();
      dialogEdit.value = true; // Afficher le message de confirmation
      showEditActu.value = false;
    })
    .catch((error) => console.error("Erreur lors de la mise à jour :", error));
};

// Charger les actualités au montage
onMounted(fetchActu);
</script>

<style scoped>
.gap{
  gap: 20px;
}

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
</style>
