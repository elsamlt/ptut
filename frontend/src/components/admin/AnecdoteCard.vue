<template>
  <v-card class="anecdotes-card">
    <v-row align="center">
      <v-col cols="2">
        <v-icon>mdi-account</v-icon>
        <v-img src="https://via.placeholder.com/50" class="film-image"></v-img>
      </v-col>
      <v-col>
        <span class="text-h6">film</span>
        <span class="text-h6">personne</span>
        <p>{{ anecdote.description }}</p>
      </v-col>
      <v-col cols="auto">
        <v-btn class="btn" size="40px" @click="deleteAnecdote">
          <v-icon class="icon">mdi-delete</v-icon>
        </v-btn>
        <v-btn class="btn" size="40px" @click="editAnecdote">
          <v-icon class="icon">mdi-pencil</v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup>
import {ref} from "vue";
import { defineProps, defineEmits } from "vue";

// Emitteur d'événements
const emit = defineEmits(["delete", "edit"]);

const items = ["Tous les films", "User 2", "User 3"]; // Liste des options
const selected = ref(items[0]); // Définit "User 1" comme valeur par défaut

const props = defineProps({
  anecdote: Object,
});

const deleteAnecdote = () => {
  emit("delete", props.anecdote.id);
};

const editAnecdote = () => {
  emit("edit", props.anecdote); // Envoie le médicament au parent
};
</script>

<style scoped>

.anecdotes-card{
  padding: 10px;
  margin-bottom: 20px;
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

.text-h6{
  margin-right: 10px;
}
</style>
