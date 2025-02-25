<template>
  <v-card class="film-card">
    <v-row align="center">
      <v-col cols="2">
        <v-img src="https://via.placeholder.com/50" class="film-image"></v-img>
      </v-col>
      <v-col>
        <span class="text-h6">2023</span>
      </v-col>
      <v-col cols="auto">
        <v-btn class="btn" size="40px">
          <v-icon class="icon" @click="deleteFilm">mdi-delete</v-icon>
        </v-btn>
        <v-btn class="btn" size="40px">
          <v-icon class="icon" @click="editFilm">mdi-pencil</v-icon>
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
