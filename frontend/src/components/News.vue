<template>
  <div class="news-container">
    <h2 class="news-title">ACTUALITÉS</h2>
    <div :class="layoutClass">
      <div v-for="news in newsList" :key="news.idActu" class="news-item">
        <div class="video-wrapper">
          <iframe
            :src="getYouTubeEmbedUrl(news.lien)"
            allowfullscreen
            class="news-video"
          ></iframe>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';

const props = defineProps({
  layout: {
    type: String,
    default: 'horizontal', // Par défaut, affichage horizontal
  },
});

const newsList = ref([]);

// Fonction pour récupérer les actualités depuis l'API
const fetchNews = async () => {
  try {
    const response = await fetch('/api/actualites');
    newsList.value = await response.json();
  } catch (error) {
    console.error("Erreur lors de la récupération des actualités :", error);
  }
};

// Fonction pour transformer le lien YouTube en version intégrable
const getYouTubeEmbedUrl = (url) => {
  const videoId = url.split('v=')[1]?.split('&')[0]; // Extraction de l'ID vidéo
  return videoId ? `https://www.youtube.com/embed/${videoId}` : '';
};

// Déclencher la récupération des données au montage du composant
onMounted(fetchNews);

const layoutClass = computed(() => ({
  'news-horizontal': props.layout === 'horizontal',
  'news-vertical': props.layout === 'vertical',
}));
</script>

<style scoped>
/* Conteneur général */
.news-container {
  padding: 20px;
  background: black; /* Fond sombre pour effet high-tech */
}

/* Titre */
.news-title {
  font-size: 22px;
  color: white;
  margin-bottom: 20px;
  margin-left: 20px;
}

/* Mises en page */
.news-horizontal {
  display: flex;
  justify-content: center; /* Centrer les vidéos */
  gap: 30px;
  overflow-x: auto; /* Scroll horizontal si trop d'éléments */
}

.news-vertical {
  display: flex;
  flex-direction: column;
  align-items: center; /* Centrer les vidéos */
  gap: 20px;
}

/* Style des vidéos */
.news-item {
  position: relative;
  width: 100%;
  max-width: 400px;
}

.video-wrapper {
  position: relative;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0px 0px 15px rgba(111, 66, 193, 0.6); /* Ombre violette */
  transition: box-shadow 0.3s ease-in-out;
}

.news-video {
  width: 100%;
  height: 230px;
  border: none;
}

/* Effet au survol */
.video-wrapper:hover {
  box-shadow: 0px 0px 30px rgba(111, 66, 193, 0.9); /* Ombre plus intense au survol */
}

/* Responsive */
@media (max-width: 768px) {
  .news-horizontal {
    flex-direction: column;
    align-items: center;
  }
}
</style>
