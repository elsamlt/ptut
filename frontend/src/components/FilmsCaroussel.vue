<template>
  <div class="films-container">
    <div class="films-header">
      <div>
        <h2>NOS FILMS</h2>
        <p>Petit texte</p>
      </div>
    </div>

    <div class="swiper-container">
      <Swiper
        :modules="[Navigation]"
        :slides-per-view="3"
        :navigation="true"
        class="films-swiper"
      >
        <SwiperSlide v-for="film in films" :key="film.idFilm" @click="handleFilmClick(film.idFilm)">
          <div class="film-card">
            <img :src="film.affiche ? film.affiche : defaultImage" :alt="film.titre" style="object-fit: cover;"/>
            <p class="film-year">{{ film.annee }}</p>
          </div>
        </SwiperSlide>
      </Swiper>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Swiper, SwiperSlide } from 'swiper/vue';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';
import {useRouter} from "vue-router";

const films = ref([]);
const defaultImage = '/images/affiche-default.jpg';
const router = useRouter();

const fetchFilms = async () => {
  try {
    const response = await fetch('/api/films');
    const data = await response.json();
    films.value = data._embedded.films;
  } catch (error) {
    console.error("Erreur lors du chargement des films :", error);
  }
};

const handleFilmClick = (idFilm) => {
  router.push(`/films/${idFilm}`);
};

onMounted(fetchFilms);
</script>

<style scoped>
.films-container {
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.films-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.swiper-container {
  position: relative;
}

.films-swiper {
  width: 100%;
}

.swiper-slide {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  cursor: pointer;
}

.film-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: transform 0.3s ease-in-out;
  margin-top: 10px;
  margin-bottom: 5px;
}

.film-card:hover {
  transform: scale(1.05);
}

.film-card img {
  width: 200px;
  height: 300px;
  border-radius: 8px;
  box-shadow: 0px 4px 2px rgba(0, 0, 0, 0.2);
}

.film-year {
  margin-top: 8px;
  font-size: 14px;
  font-weight: bold;
}

/* Personnalisation des flèches de navigation */
:deep(.swiper-button-prev),
:deep(.swiper-button-next) {
  background-color: var(--color-button);
  color: var(--color-text);
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

:deep(.swiper-button-prev)::after,
:deep(.swiper-button-next)::after {
  font-size: 16px;
  font-weight: bold;
  color: var(--color-text);
  transition: transform 0.3s ease;
}

:deep(.swiper-button-prev:hover)::after,
:deep(.swiper-button-next:hover)::after {
  transform: scale(0.8);
}

</style>
