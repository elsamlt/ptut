<template>
  <div :class="['wrapper', { 'horizontal-background': layout === 'horizontal' }]">
    <h2 class="titre">EN CE MOMENT</h2>
    <div v-for="(moment, index) in listMoment" :key="moment.id" :class="layoutClass" class="layout">
      <div class="image-container">
        <img :src="`${moment.nomImg}`" alt="Actualité en ce moment" />
      </div>
      <div class="content">
        <h3>{{ moment.titre }}</h3>
        <br>
        <p>{{ moment.description }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, reactive} from 'vue';

// Définition des props
const props = defineProps({
  layout: {
    type: String,
    default: 'horizontal' // 'horizontal' ou 'vertical'
  }
});

// État réactif pour stocker les données du moment
const listMoment = reactive([]);

// URL de base de l'API
const url = '/api/moments';

// Calculer la classe CSS en fonction de la disposition
const layoutClass = computed(() => ({
  'horizontal-layout': props.layout === 'horizontal',
  'vertical-layout': props.layout === 'vertical'
}));

/**
 * Récupérer les moments depuis l'API
 */
function fetchMoments() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      listMoment.splice(0, listMoment.length, ...dataJSON);
      console.log(listMoment)
    })
    .catch((error) =>
      console.error("Erreur lors de la récupération des moments :", error),
    );
}

// Charger les données au montage du composant
onMounted(fetchMoments);
</script>

<style scoped>

.horizontal-layout {
  gap: 20px;
}

.horizontal-background{
  background-color: var(--color-background);
  color: var(--color-text);
}

.layout{
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 20px;
}

.titre{
  margin-left: 40px;
  margin-top: 20px;
}

img {
  width: 650px;
  height: auto;
  border-radius: 8px;
}

.vertical-layout {
  flex-direction: column;
  background-color: var(--color-background-white);
  color: var(--color-text-dark);
  width: 20%;
}

.vertical-layout img{
  width: 270px;
}
</style>
