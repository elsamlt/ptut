<template>
  <div>
    <div v-if="loading" class="loading">Chargement...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else>
      <div v-for="film in films" :key="film.idFilm" class="film">
        <h1>{{ film.titre }}</h1>
        <p class="annee">{{ film.annee }} - {{ film.genre }} - {{ film.duree }}</p>
        <p class="resume">{{ film.synopsis }}</p>
        <div class="Boutton">
          <button name="Regarder" @click="watchFilm(film)">REGARDER</button>
          <button name="Bande annonce" @click="watchTrailer(film)">BANDE D'ANNONCE</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FilmDetail',
  data() {
    return {
      films: [],
      loading: true,
      error: null
    }
  },
  mounted() {
    fetch('/api/films')
      .then(response => {
        if (!response.ok) {
          throw new Error('Erreur réseau');
        }
        return response.json();
      })
      .then(data => {
        this.films = data._embedded.films;
      })
      .catch(error => {
        console.error('Erreur lors de la récupération des films:', error);
        this.error = "Erreur lors du chargement des données.";
      })
      .finally(() => {
        this.loading = false;
      });
  },
  methods: {
    watchFilm(film) {
      if (film.urlFilm && film.urlFilm.trim() !== "") {
        window.location.href = film.urlFilm;
      }
    },
    watchTrailer(film) {
      if (film.urlBA && film.urlBA.trim() !== "") {
        window.location.href = film.urlBA;
      }
    }
  }
}
</script>

<style scoped>
.loading, .error {
  text-align: center;
  font-size: 1.5rem;
  margin-top: 50px;
}

.error {
  color: red;
}

@import url('https://fonts.googleapis.com/css2?family=Cinzel+Decorative:wght@700&family=Playfair+Display:wght@600&family=Roboto:wght@400;700&display=swap');

html, body {
  margin: 0;
  padding: 0;
  background-color: #000000;
  color: #FFFFFF;
  font-family: 'Roboto', sans-serif;
}

h1 {
  font-family: 'Cinzel Decorative', cursive;
  font-size: 3rem;
  margin: 20px 0;
}

h2 {
  font-family: 'Playfair Display', serif;
  font-size: 2rem;
  margin: 20px 0 10px 0;
}

p {
  font-size: 1rem;
  margin: 5px 0;
}

.Boutton {
  position: fixed;
  bottom: 20px;
  left: 20px;
  display: flex;
  gap: 10px;
}

.Boutton button,
.bouton-rond,
.publier-btn {
  background-color: #6C63FF;
  color: #FFFFFF;
  border: none;
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
  transition: background-color 0.3s ease;
}

.bouton-rond {
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 1.2rem;
  display: inline-flex;
  justify-content: center;
  margin: 5px;
}

.Boutton button {
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 1rem;
}

.Boutton button:hover,
.bouton-rond:hover,
.publier-btn:hover {
  background-color: #5A52E5;
}

.film {
  margin-bottom: 40px;
  padding: 20px;
  border-bottom: 1px solid #555;
}
</style>
<template>
  <div>
    <div v-if="loading" class="loading">Chargement...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else>
      <div v-for="film in films" :key="film.idFilm" class="film">
        <h1>{{ film.titre }}</h1>
        <p class="annee">{{ film.annee }} - {{ film.genre }} - {{ film.duree }}</p>
        <p class="resume">{{ film.synopsis }}</p>
        <div class="Boutton">
          <button name="Regarder" @click="watchFilm(film)">REGARDER</button>
          <button name="Bande annonce" @click="watchTrailer(film)">BANDE D'ANNONCE</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FilmDetail',
  data() {
    return {
      films: [],
      loading: true,
      error: null
    }
  },
  mounted() {
    fetch('/api/films')
      .then(response => {
        if (!response.ok) {
          throw new Error('Erreur réseau');
        }
        return response.json();
      })
      .then(data => {
        this.films = data._embedded.films;
      })
      .catch(error => {
        console.error('Erreur lors de la récupération des films:', error);
        this.error = "Erreur lors du chargement des données.";
      })
      .finally(() => {
        this.loading = false;
      });
  },
  methods: {
    watchFilm(film) {
      if (film.urlFilm && film.urlFilm.trim() !== "") {
        window.location.href = film.urlFilm;
      }
    },
    watchTrailer(film) {
      if (film.urlBA && film.urlBA.trim() !== "") {
        window.location.href = film.urlBA;
      }
    }
  }
}
</script>

<style scoped>
.loading, .error {
  text-align: center;
  font-size: 1.5rem;
  margin-top: 50px;
}

.error {
  color: red;
}

@import url('https://fonts.googleapis.com/css2?family=Cinzel+Decorative:wght@700&family=Playfair+Display:wght@600&family=Roboto:wght@400;700&display=swap');

html, body {
  margin: 0;
  padding: 0;
  background-color: #000000;
  color: #FFFFFF;
  font-family: 'Roboto', sans-serif;
}

h1 {
  font-family: 'Cinzel Decorative', cursive;
  font-size: 3rem;
  margin: 20px 0;
}

h2 {
  font-family: 'Playfair Display', serif;
  font-size: 2rem;
  margin: 20px 0 10px 0;
}

p {
  font-size: 1rem;
  margin: 5px 0;
}

.Boutton {
  position: fixed;
  bottom: 20px;
  left: 20px;
  display: flex;
  gap: 10px;
}

.Boutton button,
.bouton-rond,
.publier-btn {
  background-color: #6C63FF;
  color: #FFFFFF;
  border: none;
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
  transition: background-color 0.3s ease;
}

.bouton-rond {
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 1.2rem;
  display: inline-flex;
  justify-content: center;
  margin: 5px;
}

.Boutton button {
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 1rem;
}

.Boutton button:hover,
.bouton-rond:hover,
.publier-btn:hover {
  background-color: #5A52E5;
}

.film {
  margin-bottom: 40px;
  padding: 20px;
  border-bottom: 1px solid #555;
}
</style>
