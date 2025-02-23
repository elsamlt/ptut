<template>
  <!-- Liste des films -->
  <v-card class="film-card">
    <v-row align="center">
      <v-col cols="2">
        <v-img src="https://via.placeholder.com/50" class="film-image"></v-img>
      </v-col>
      <v-col>
        <span class="text-h6 font-weight-bold">2023</span>
      </v-col>
      <v-col cols="auto">
        <v-btn icon color="purple">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
        <v-btn icon color="purple">
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </v-card>

  <!-- Bouton flottant pour ajouter un film -->
  <v-btn class="add-btn" fab color="purple" dark>
    <v-icon>mdi-plus</v-icon>
  </v-btn>
</template>

<script setup>

</script>

<style scoped>
/* Carte du film */
.film-card {
  padding: 10px;
  margin-top: 20px;
}

/* Image du film */
.film-image {
  width: 50px;
  height: 75px;
}

/* Bouton flottant */
.add-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
}
</style>
