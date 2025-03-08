<template>
  <v-form>
    <v-card class="pa-5">
      <v-container>
        <v-row justify="center" class="mt-4">
          <v-col cols="12" md="10">
            <v-text-field class="text-input" label="Description" v-model="anecdote.description" required></v-text-field>
          </v-col>
          <v-col cols="12" md="10">
            <v-select class="text-input" label="Film" v-model="selected" :item-props="itemProps" :items="items" ></v-select>
          </v-col>
          <v-col cols="12" md="10">
            <v-select class="text-input" label="Participant" v-model="selected" :item-props="itemProps" :items="items" ></v-select>
          </v-col>
        </v-row>
      </v-container>

      <!-- Boutons Annuler et Ajouter -->
      <v-row class="mt-4">
        <v-col cols="12" class="d-flex justify-end">
          <v-btn class="mr-2" @click="closeForm">Annuler</v-btn>
          <v-btn class="btn" @click="submitAnecdote">Ajouter</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-form>
</template>

<script setup>
import { ref, defineEmits } from "vue";

const emit = defineEmits(["add", "closeForm"]);

const anecdote = ref({
  id_film:'',
  id_participant: '',
  description: '',
});

// Soumettre l'anecdote
const submitAnecdote = () => {
  emit("add", anecdote.value);

  // Remise à zéro après ajout
  anecdote.value = {
    id_film:'',
    id_participant: '',
    description: '',
  };
};

// Annuler et enlever le texte rempli
const closeForm = () => {
  emit("closeForm");
};

</script>

<style scoped>

.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-right: 10px;
}

</style>
