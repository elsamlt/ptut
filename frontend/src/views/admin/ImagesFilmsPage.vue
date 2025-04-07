<template>
    <v-row class="d-flex align-center gap-x-4 selectimage" v-if="!showAddImage">
      <p>Afficher les images de :</p>
      <v-col cols="4">
        <v-select v-model="selectedFilm" :items="AllOption" item-value="idFilm" item-title="titre" density="compact"></v-select>
      </v-col>
    </v-row>
  <!-- Liste des images -->
  <v-container v-if="!showAddImage">
    <v-row>
      <v-col v-for="(image, index) in listImages" :key="image.id" class="d-flex child-flex" cols="3">
        <ImageCard :index="index" :image="image" @delete="handlerDelete(image)"/>
      </v-col>
    </v-row>
  </v-container>
  <v-container v-if="showAddImage">
    <AddImage @add="handleImageAdded" @closeForm="showAddImage = false" :films="listFilms" />
  </v-container>

  <!-- Bouton flottant pour ajouter une image -->
  <v-btn v-if="!showAddImage" class="btn add-btn" size="40px" @click="showAddImage = true; selectedImage = null;">
    <v-icon class="icon">mdi-plus</v-icon>
  </v-btn>

  <!-- Dialog de confirmation -->
  <v-dialog v-model="dialogAdd" max-width="400px">
    <v-card>
      <v-card-text> L'image a été ajoutée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogAdd = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogDelete" max-width="400px">
    <v-card>
      <v-card-text> L'image a été supprimée avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogDelete = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import {ref, onMounted, reactive, computed, watch} from "vue";
import ImageCard from "@/components/admin/ImageCard.vue";
import AddImage from "@/components/admin/AddImage.vue";

const url = "/api/images";
const listImages = reactive([]);
const listFilms = ref([]);
const selectedFilm = ref(null);

const dialogAdd = ref(false);
const dialogDelete = ref(false);

const showAddImage = ref(false);

const selectedImage = ref(null);

const AllOption = computed(() => [
  { idFilm: null, titre: 'Tous les films' },  // Option "Tous les films"
  ...listFilms.value, // Les films récupérés
]);

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
 * Récupérer les images depuis l'API
 */
function fetchImages() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      listImages.splice(0, listImages.length, ...dataJSON);
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des images :", error),
    );
}

function fetchImagesByFilm(filmId) {
  if (filmId === null) {
    // Si "Tous les films" est sélectionné, on récupère toutes les imgaes
    fetchImages();
  } else {
    // Sinon, on récupère les images du film spécifique
    fetch(`${url}/search/findByFilm_IdFilm?idFilm=${filmId}`)
      .then(response => response.json())
      .then(dataJSON => {
        listImages.splice(0, listImages.length, ...dataJSON._embedded.images);
      })
      .catch(error => console.error("Erreur lors de la récupération des commentaires :", error));
  }
}

const handleImageAdded = async (newImage) => {
  let imageData = { ...newImage };

  // Vérifier si l'image est en base64
  if (newImage.img && newImage.img.startsWith('data:')) {
    try {
      // Convertir le base64 en blob
      const response = await fetch(newImage.img);
      const blob = await response.blob();
      const file = new File([blob], "image.jpg", { type: "image/jpeg" });

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

      // Récupérer l'URL de l'image après l'upload
      const uploadData = await uploadResponse.text();
      const fileUrlMatch = uploadData.match(/\/img\/[^"'}]+/);
      const fileUrl = fileUrlMatch ? fileUrlMatch[0] : '';

      // Remplacer le base64 par le chemin de l'image
      console.log(imageData)
      imageData.img = fileUrl;
    } catch (error) {
      console.error("Erreur lors de l'upload de l'image :", error);
      return;
    }
  }

  // Envoyer les données de l'image à l'API
  try {
    console.log('imageData', imageData);
    const response = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        id_film: Number(imageData.id_film),
        img: imageData.img,
      }),
    });
    if (!response.ok) {
      throw new Error("Erreur lors de l'ajout de l'image");
    }

    // Rafraîchir les images après l'ajout
    await fetchImages();

    // Afficher la popup de confirmation
    dialogAdd.value = true;
    showAddImage.value = false;
  } catch (error) {
    console.error("Erreur lors de l'ajout de l'image :", error);
  }
};

/**
 * Supprimer une image via API
 */
function handlerDelete(image) {
  fetch(`${url}/${image.idImage}`, { method: "DELETE" })
    .then((response) => {
      if (response.ok) fetchImages();
      dialogDelete.value = true;
      selectedImage.value = null;
    })
    .catch((error) => console.error("Erreur lors de la suppression :", error));
}

// Observer le changement de film sélectionné
watch(selectedFilm, (newFilmId) => {
  fetchImagesByFilm(newFilmId);  // Recharge les images lorsque la sélection change
});

// Charger les images au montage
onMounted(() => {
  fetchFilms();
  fetchImagesByFilm(null);
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

.selectimage{
  margin: 10px;
}

</style>
