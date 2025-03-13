<template>
  <v-card class="film-card">
    <v-row align="center">
      <v-col cols="2">
        <v-img v-if="film.affiche" :src="`${film.affiche}`" class="film-image" alt="Affiche du film">
          <!-- Afficher l'image si elle existe -->
        </v-img>
        <v-icon v-else class="film-image icon-placeholder" color="grey lighten-2">mdi-camera</v-icon>
      </v-col>
      <v-col>
        <span class="text-h6">{{film.titre}}</span>
      </v-col>
      <v-col cols="auto">
        <v-btn class="btn" size="40px" @click="deleteFilm">
          <v-icon class="icon">mdi-delete</v-icon>
        </v-btn>
        <v-btn class="btn" size="40px"  @click="editFilm">
          <v-icon class="icon">mdi-pencil</v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  film: Object,
});
// Emitteur d'événements
const emit = defineEmits(["delete", "edit"]);

const deleteFilm = () => {
  emit("delete", props.film.id);
};

const editFilm = () => {
  emit("edit", props.film); // Envoie le médicament au parent
};
</script>

<style scoped>
/* Carte du film */
.film-card {
  margin-top: 20px;
}

/* Image du film */
.film-image {
  width: 50px;
  height: 75px;
  margin-left: 20px;
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
