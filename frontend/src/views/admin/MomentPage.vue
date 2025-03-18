<template>
  <v-container v-if="!showEditMoment && !showAddMoment">
    <MomentCard v-for="(moment, index) in listMoment" :key="moment.id" :moment="moment" :index="index" @edit="openEditForm(moment)"/>
  </v-container>
  <v-container v-if="showAddMoment">
    <AddMoment @add="handleMomentAdded" @closeForm="showAddMoment = false"/>
  </v-container>
  <v-container v-if="showEditMoment">
    <EditMoment :moment="selectedMoment" @edit="handleMomentEdit" @cancel="showEditMoment = false" />
  </v-container>

  <!-- Bouton flottant pour ajouter un moment -->
  <v-btn v-if="!showEditMoment && !showAddMoment" class="btn add-btn" size="40px" @click="showAddMoment = true; selectedMoment = null;">
    <v-icon class="icon">mdi-plus</v-icon>
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
      listMoment.splice(0, listMoment.length, ...dataJSON);
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des moments :", error),
    );
}

/**
 * Ajouter un nouveau moment via API
 */
const handleMomentAdded = async (newMoment) => {
  let momentData = { ...newMoment };

  // Vérifier si nomImg est un Data URL (base64)
  if (newMoment.nomImg && newMoment.nomImg.startsWith('data:')) {
    try {
      // Convertir le base64 en blob
      const response = await fetch(newMoment.nomImg);
      const blob = await response.blob();
      const file = new File([blob], "moment.jpg", { type: "image/jpeg" });

      // Créer un FormData pour l'upload
      const formData = new FormData();
      formData.append('file', file);

      // Uploader l'image au serveur
      const uploadResponse = await fetch('/api/files/upload', {
        method: 'POST',
        body: formData
      });

      if (!uploadResponse.ok) {
        throw new Error("Erreur lors de l'upload de l'image");
      }

      const uploadData = await uploadResponse.text();

      // Extraire uniquement le chemin de l'image avec une regex
      const fileUrlMatch = uploadData.match(/\/img\/[^"'}]+/);
      const fileUrl = fileUrlMatch ? fileUrlMatch[0] : '';

      // Remplacer le base64 par le chemin de l'image
      momentData.nomImg = fileUrl;
    } catch (error) {
      console.error("Erreur lors de l'upload de l'image :", error);
      return;
    }
  }

  // Ajouter le moment via l'API
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      titre: momentData.titre,
      nomImg: momentData.nomImg,
      description: momentData.description,
    }),
  })
    .then((response) => response.json())
    .then(() => {
      fetchMoments(); // Rafraîchir la liste après l'ajout
      dialogAdd.value = true; // Afficher la popup de confirmation
      showAddMoment.value = false;
    })
    .catch((error) =>
      console.error("Erreur lors de l'ajout du moment :", error),
    );
};

/**
 * Modifier un moment comme faite via API
 */
const handleMomentEdit = async (updatedMoment) => {
  let momentData = { ...updatedMoment };

  // Vérifier si nomImg est un Data URL (base64)
  if (updatedMoment.nomImg && updatedMoment.nomImg.startsWith("data:")) {
    try {
      // Convertir le base64 en blob
      const response = await fetch(updatedMoment.nomImg);
      const blob = await response.blob();
      const file = new File([blob], "moment.jpg", { type: "image/jpeg" });

      // Créer un FormData pour l'upload
      const formData = new FormData();
      formData.append("file", file);

      // Uploader l'image au serveur
      const uploadResponse = await fetch("/api/files/upload", {
        method: "POST",
        body: formData,
      });

      if (!uploadResponse.ok) {
        throw new Error("Erreur lors de l'upload de l'image");
      }

      const uploadData = await uploadResponse.text();
      const fileUrlMatch = uploadData.match(/\/img\/[^"'}]+/);
      const fileUrl = fileUrlMatch ? fileUrlMatch[0] : "";

      // Remplacer le base64 par l'URL de l'image
      momentData.nomImg = fileUrl;
    } catch (error) {
      console.error("Erreur lors de l'upload de l'image :", error);
      return;
    }
  }

  // Envoyer la requête de mise à jour
  fetch(`${url}/${updatedMoment.idMoment}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(momentData),
  })
    .then((response) => {
      if (!response.ok) throw new Error("Erreur lors de la mise à jour");
      return response.json();
    })
    .then(() => {
      fetchMoments(); // Rafraîchir la liste après l'édition
      dialogEdit.value = true; // Afficher confirmation
      showEditMoment.value = false;
    })
    .catch((error) => console.error("Erreur lors de la mise à jour :", error));
};


// Charger le moment au montage
onMounted(fetchMoments);
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
</style>
