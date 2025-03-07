<template>
  <v-container>
    <v-card class="card-container d-flex" >
      <!-- Image à gauche -->
      <v-img
        src="https://via.placeholder.com/300x200"
        height="100%"
        width="300"
        class="image-left"
        cover
      ></v-img>

      <!-- Contenu à droite -->
      <div class="content px-5 py-3">
        <v-card-title class="text-h5 font-weight-bold">
          {{ moment.titre }}
        </v-card-title>
        <v-card-subtitle class="text-body-1">
          Voici une description brève du moment. Vous pouvez ajouter plus de détails ici selon vos besoins.
        </v-card-subtitle>

        <!-- Boutons -->
        <v-col cols="auto">
          <v-btn class="btn" size="40px">
            <v-icon class="icon" @click="editMoment">mdi-pencil</v-icon>
          </v-btn>
        </v-col>
      </div>
    </v-card>
  </v-container>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  moment: Object,
});
console.log(props.moment)
// Emitteur d'événements
const emit = defineEmits(["edit"]);

const editMoment = () => {
  emit("edit", props.moment); // Envoie le médicament au parent
};
</script>

<style scoped>
.image-left {
  flex: 1;
  max-width: 300px;
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
