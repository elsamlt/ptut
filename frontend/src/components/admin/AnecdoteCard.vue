<template>
  <v-card class="anecdotes-card">
    <v-row align="center">
      <v-col cols="2">
        <v-skeleton-loader v-if="loading" type="avatar" class="skeleton" />
        <template v-else>
          <img v-if="person.pdp" :src="`${person.pdp}`" class="person-image" alt="Photo de profil du participant" />
          <v-icon v-else class="icon-placeholder" color="grey lighten-2">mdi-account</v-icon>
        </template>
      </v-col>

      <v-col>
        <v-skeleton-loader v-if="loading" type="text" width="100px" class="skeleton" />
        <template v-else>
          <span class="text-h6">{{ person.prenom }} {{ person.nom }}</span>
          <p>{{ anecdote.description }}</p>
        </template>
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
import { onMounted, ref, defineProps, defineEmits } from "vue";

const emit = defineEmits(["delete", "edit"]);

const props = defineProps({
  anecdote: Object,
});

const person = ref(null);
const loading = ref(true); // Ajout de l'état de chargement

const deleteAnecdote = () => {
  emit("delete", props.anecdote.id);
};

const editAnecdote = () => {
  emit("edit", props.anecdote);
};

async function fetchPersonAnecdote(anecdote) {
  try {
    const response = await fetch(`/api/anecdotes/${anecdote.idAnecdote}/participant`);
    if (!response.ok) throw new Error("Erreur lors de la récupération des données");

    const dataJSON = await response.json();
    person.value = dataJSON;
  } catch (error) {
    console.error("Erreur lors de la récupération du participant :", error);
  } finally {
    loading.value = false; // Désactiver le chargement après réception des données
  }
}

onMounted(() => {
  fetchPersonAnecdote(props.anecdote);
});

</script>

<style scoped>
.anecdotes-card {
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

.text-h6 {
  margin-right: 10px;
}

.person-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  margin-left: 20px;
}

.icon-placeholder {
  margin-left: 30px;
}

.skeleton {
  margin-left: 20px;
}
</style>
