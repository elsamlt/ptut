<template>
  <!-- Liste des films -->
  <v-container v-if="!showEditFilm && !showAddFilm">
    <FilmsCard v-for="(film, index) in listFilms" :key="film.id" :index="index" @edit="openEditForm(film)" :film="film"
               @delete="handlerDelete(selectedFilm)"/>
  </v-container>
  <v-container v-if="showAddFilm">
    <AddFilm @add="handleFilmAdded" @closeForm="showAddFilm = false"/>
  </v-container>
  <v-container v-if="showEditFilm">
    <EditFilm :film="selectedFilm" @edit="handleFilmEdit" @cancel="showEditFilm = false" />
  </v-container>

  <!-- Bouton flottant pour ajouter un film -->
  <v-btn v-if="!showAddFilm && !showEditFilm" class="btn add-btn" size="40px" @click="showAddFilm = true; selectedFilm = null;">
    <v-icon class="icon">mdi-plus</v-icon>
  </v-btn>

  <!-- Dialog de confirmation -->
  <v-dialog v-model="dialogAdd" max-width="400px">
    <v-card>
      <v-card-text> Le film a été ajouté avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogAdd = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogDelete" max-width="400px">
    <v-card>
      <v-card-text> Le film a été supprimé avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogDelete = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogEdit" max-width="400px">
    <v-card>
      <v-card-text> Le film a été modifié avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogEdit = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
  import FilmsCard from "@/components/admin/FilmsCard.vue";
  import EditFilm from "@/components/admin/EditFilm.vue";
  import AddFilm from "@/components/admin/AddFilm.vue";

  import { ref, onMounted, reactive } from "vue";

  const url = "/api/films";
  const listFilms = reactive([]);

  const dialogAdd = ref(false);
  const dialogDelete = ref(false);
  const dialogEdit = ref(false);

  const showEditFilm = ref(false);
  const showAddFilm = ref(false);

  const selectedFilm = ref(null);

  const openEditForm = (film) => {
    selectedFilm.value = { ...film }; // Cloner pour éviter la modification directe
    showEditFilm.value = true;
  };

  /**
   * Récupérer les films depuis l'API
   */
  function fetchFilms() {
    fetch(url)
      .then((response) => response.json())
      .then((dataJSON) => {
        listFilms.splice(0, listFilms.length, ...dataJSON._embedded.films);
      })
      .catch((error) =>
        console.error("Erreur lors de la récupération des films :", error),
      );
  }

  /**
   * Sélectionner un film et afficher ses détails
   */
  function fetchFilmDetail(film) {
    fetch(`${url}/${film.id}`)
      .then((response) => response.json())
      .then((dataJSON) => {
        selectedFilm.value = dataJSON;
        showAddFilm.value = false;
      })
      .catch((error) =>
        console.error("Erreur lors de la récupération des films :", error),
      );
  }

  /**
   * Ajouter un nouveau film via API
   */
  const handleFilmAdded = async (newFilm) => {
    let filmData = { ...newFilm };

    // Vérifier si l'affiche est un Data URL (base64)
    if (newFilm.affiche && newFilm.affiche.startsWith('data:')) {
      // Convertir le base64 en blob
      const response = await fetch(newFilm.affiche);
      const blob = await response.blob();
      const file = new File([blob], "image.jpg", { type: "image/jpeg" });

      // Créer un FormData pour l'upload
      const formData = new FormData();
      formData.append('file', file);

      try {
        // Uploader l'image au serveur
        const uploadResponse = await fetch('/api/files/upload', {
          method: 'POST',
          body: formData
        });

        if (!uploadResponse.ok) {
          throw new Error("Erreur lors de l'upload de l'image");
        }

        const uploadData = await uploadResponse.text();

        // Au lieu de simplement faire un substring depuis l'index de '/img/'
        // Utilisez une expression régulière pour extraire uniquement le chemin de l'image
        const fileUrlMatch = uploadData.match(/\/img\/[^"'}]+/);
        const fileUrl = fileUrlMatch ? fileUrlMatch[0] : '';

        // Remplacer le base64 par le chemin de l'image
        filmData.affiche = fileUrl;
      } catch (error) {
        console.error("Erreur lors de l'upload de l'image :", error);
        return;
      }
    }

    // Maintenant, ajouter le film avec le chemin de l'image
    fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(filmData),
    })
      .then((response) => response.json())
      .then(() => {
        fetchFilms(); // Rafraîchir la liste après l'ajout
        // Afficher la popup de confirmation
        dialogAdd.value = true;
        showAddFilm.value = false;
      })
      .catch((error) =>
        console.error("Erreur lors de l'ajout du film :", error),
      );
  };


  /**
   * Supprimer un film via API
   */
  function handlerDelete(film) {
    fetch(`${url}/${film.id}`, { method: "DELETE" })
      .then((response) => {
        if (response.ok) fetchFilms();
        dialogDelete.value = true;
        selectedFilm.value = null;
      })
      .catch((error) => console.error("Erreur lors de la suppression :", error));
  }

  /**
   * Modifier un film comme faite via API
   */
  const handleFilmEdit = (updatedFilm) => {
    fetch(`${url}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        titre: updatedFilm.titre,
        synopsis: updatedFilm.synopsis,
        genre: updatedFilm.genre,
        annee: updatedFilm.annee,
        duree: updatedFilm.duree,
        affiche: updatedFilm.affiche,
        urlFilm: updatedFilm.urlFilm,
        urlBA: updatedFilm.urlBA,
      }),
    })
      .then((response) => response.json())
      .then(() => {
        fetchFilmDetail(updatedFilm);
        dialogEdit.value = true; // Afficher le message de confirmation
        showEditFilm.value = false;
      })
      .catch((error) => console.error("Erreur lors de la mise à jour :", error));
  };

  // Charger les films au montage
  onMounted(fetchFilms);
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
