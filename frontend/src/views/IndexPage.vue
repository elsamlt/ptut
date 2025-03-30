<template>
  <div class="index-page">
    <!-- Image principale avec texte centré -->
    <div class="hero-section">
      <img src="@/assets/background-home.png" alt="Bannière médicale" class="hero-image" />
      <div class="hero-text">ISIS SE PRODUIT</div>
    </div>

    <!-- Section Actualités -->
    <News layout="horizontal" />

    <!-- Section Films -->
    <FilmsCaroussel />

    <!-- Section En Ce Moment -->
    <Moment layout="horizontal" />
  </div>
</template>

<script setup>
import News from "@/components/News.vue";
import FilmsCaroussel from "@/components/FilmsCaroussel.vue";
import Moment from "@/components/Moment.vue";
</script>

<style scoped>
/* Mise en page de la bannière avec texte centré */
.hero-section {
  position: relative;
  width: 100%;
  overflow: hidden;
}

.hero-image {
  width: 100%;
  height: auto;
  display: block;
}

.hero-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 4rem;
  font-weight: bold;
  color: white;
  text-align: center;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}
</style>
