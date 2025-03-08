<template>
  <v-container>
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
            <v-btn class="btn" @click="submitAnecdote">Enregistrer</v-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-form>
  </v-container>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";

// Récupérer les props
const props = defineProps({ anecdote: Object });
const emit = defineEmits(["edit", "cancel"]);

// Cloner l'objet anecdote pour éviter de modifier directement la prop
const anecdote = ref({ ...props.anecdote });

// Fonction de soumission
const submitAnecdote = () => {
  emit("edit", anecdote.value);
};

// Annuler et fermer le formulaire
const closeForm = () => {
  emit("cancel");
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
