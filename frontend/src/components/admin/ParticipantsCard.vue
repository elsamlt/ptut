<template>
  <v-card class="participants-card">
    <v-row align="center">
      <v-col cols="2">
        <img v-if="person.pdp" :src="`${person.pdp}`" class="person-image" alt="Photo de profil du participant">
          <!-- Afficher l'image si elle existe -->
        </img>
        <v-icon v-else class="icon-placeholder" color="grey lighten-2">mdi-camera</v-icon>
      </v-col>
      <v-col>
        <span class="text-h6">{{ person.nom }}</span>
        <span class="text-h6">{{ person.prenom }}</span>
      </v-col>
      <v-col cols="auto">
        <v-btn class="btn" size="40px" @click="deletePerson">
          <v-icon class="icon">mdi-delete</v-icon>
        </v-btn>
        <v-btn class="btn" size="40px" @click="editPerson">
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

 .participants-card{
   padding: 10px;
   margin-bottom: 20px;
 }

 .person-image {
   width: 40px; /* Définir la taille de l'image */
   height: 40px; /* Définir la hauteur pour que l'image soit carrée */
   border-radius: 50%; /* Rendre l'image ronde */
   object-fit: cover; /* Assurer que l'image s'adapte sans distorsion */
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

 .text-h6{
   margin-right: 10px;
 }

 .icon-placeholder{
   margin-left: 30px;
 }
</style>
