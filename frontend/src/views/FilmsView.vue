<template>
  <v-container>
    <v-row>
      <v-col cols="9">
        <h1>TOUS LES FILMS</h1>
        <p>PETIT TEXTE</p>
        <v-card v-for="n in 3" :key="n" class="mb-4">
          <v-row no-gutters>
            <v-col cols="3">
              <v-img src="/path/to/image.png" aspect-ratio="1"></v-img>
            </v-col>
            <v-col cols="9" class="pa-4">
              <h3>THE HOSPITAL</h3>
              <p>2023 - 14m28</p>
              <p><strong>Genre :</strong> Comédie</p>
              <p><strong>Synopsis :</strong> Dans une école un peu spéciale, les élèves ne rêvent pas de maths ou de littérature : ici, tout le monde s'entraîne à devenir médecin... ou presque...</p>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
      <v-col cols="3">
        <News  layout="vertical" />
        <Moment layout="vertical" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import FilmsCard from "@/components/FilmsCard.vue";
import News from "@/components/News.vue";
import Moment from "@/components/Moment.vue";

export default {
  name: 'FilmsPage',
  components: {News, Moment},
};
</script>

<style scoped>

</style>

