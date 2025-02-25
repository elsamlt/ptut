<template>
  <v-row class="d-flex align-center mt-1 gap-x-4">
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

  <v-card class="participants-card">
    <v-row align="center">
      <v-col cols="2">
        <v-icon>mdi-account</v-icon>
        <v-img src="https://via.placeholder.com/50" class="film-image"></v-img>
      </v-col>
      <v-col>
        <span class="text-h6">Nom</span>
        <span class="text-h6">Prénom</span>
        <p>Role</p>
      </v-col>
      <v-col cols="auto">
        <v-btn class="btn" size="40px">
          <v-icon class="icon" @click="deletePerson">mdi-delete</v-icon>
        </v-btn>
        <v-btn class="btn" size="40px">
          <v-icon class="icon" @click="editPerson">mdi-pencil</v-icon>
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
  person: Object,
});

const deletePerson = () => {
  emit("delete", props.film.id);
};

const editPerson = () => {
  emit("edit", props.film); // Envoie le médicament au parent
};
</script>

<style scoped>
 .d-flex{
   margin: 5px;
 }

 .select{
   margin-top: 20px;
 }

 .participants-card{
   padding: 10px;
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
