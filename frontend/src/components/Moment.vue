<template>
  <div v-if="moment" :class="layoutClass">
    <div class="image-container">
      <img :src="`/img/${moment.nomImg}`" alt="Actualité en ce moment" />
    </div>
    <div class="content">
      <h2>EN CE MOMENT</h2>
      <h3>{{ moment.titre }}</h3>
      <p>{{ moment.description }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';

// Définition des props
const props = defineProps({
  idMoment: {
    type: Number,
    required: true
  },
  layout: {
    type: String,
    default: 'horizontal' // 'horizontal' ou 'vertical'
  }
});

// État réactif pour stocker les données du moment
const moment = ref(null);

// URL de base de l'API
const apiUrl = '/api/moments';

// Calculer la classe CSS en fonction de la disposition
const layoutClass = computed(() => ({
  'horizontal-layout': props.layout === 'horizontal',
  'vertical-layout': props.layout === 'vertical'
}));

// Charger les données du moment depuis l'API
const fetchMoment = async () => {
  try {
    const response = await fetch(`${apiUrl}/${props.idMoment}`);
    if (!response.ok) throw new Error('Erreur lors de la récupération du moment');

    moment.value = await response.json();
  } catch (error) {
    console.error(error);
  }
};

// Charger les données au montage du composant
onMounted(fetchMoment);
</script>

<style scoped>
.horizontal-layout {
  display: flex;
  align-items: center;
  background-color: black;
  color: white;
  padding: 20px;
}

.horizontal-layout .image-container {
  flex: 1;
  margin-right: 20px;
}

.horizontal-layout img {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.horizontal-layout .content {
  flex: 2;
}

.vertical-layout {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  background-color: white;
  color: black;
  padding: 20px;
}

.vertical-layout .image-container {
  width: 100%;
  margin-bottom: 15px;
}

.vertical-layout img {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.vertical-layout .content {
  max-width: 90%;
}
</style>
