<template>
  <!-- Liste des films -->
  <v-container v-if="!showEditFilm && !showAddFilm">
    <FilmsCard
      v-for="(film, index) in listFilms"
      :key="film.id"
      :index="index"
      @edit="openEditForm(film)"
      :film="film"
      @delete="handlerDelete(film)"
    />
  </v-container>

  <!-- Graphiques des participants -->
  <v-container v-if="listFilms.length > 0 && !showEditFilm && !showAddFilm">
    <v-row>
      <!-- Participants par Film -->
      <v-col cols="12" md="6">
        <ApexCharts
          type="bar"
          :options="chartOptions"
          :series="chartData"
          height="350"
        />
      </v-col>

      <!-- Participants par Groupe -->
      <v-col cols="12" md="6">
        <ApexCharts
          type="donut"
          :options="groupChartOptions"
          :series="groupChartData"
          height="350"
        />
      </v-col>
    </v-row>
  </v-container>


  <!-- Ajout / édition -->
  <v-container v-if="showAddFilm">
    <AddFilm @add="handleFilmAdded" @closeForm="showAddFilm = false" />
  </v-container>

  <v-container v-if="showEditFilm">
    <EditFilm :film="selectedFilm" @edit="handleFilmEdit" @cancel="showEditFilm = false" />
  </v-container>

  <!-- Bouton flottant -->
  <v-btn
    v-if="!showAddFilm && !showEditFilm"
    class="btn add-btn"
    size="40px"
    @click="showAddFilm = true; selectedFilm = null;"
  >
    <v-icon class="icon">mdi-plus</v-icon>
  </v-btn>

  <!-- Dialogs -->
  <v-dialog v-model="dialogAdd" max-width="400px">
    <v-card>
      <v-card-text> Le film a été ajouté avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogAdd = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogDelete" max-width="400px">
    <v-card>
      <v-card-text> Le film a été supprimé avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogDelete = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="dialogEdit" max-width="400px">
    <v-card>
      <v-card-text> Le film a été modifié avec succès ! </v-card-text>
      <v-card-actions>
        <v-btn color="green" text @click="dialogEdit = false">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import FilmsCard from "@/components/admin/FilmsCard.vue";
import EditFilm from "@/components/admin/EditFilm.vue";
import AddFilm from "@/components/admin/AddFilm.vue";
import { ref, onMounted, reactive } from "vue";
import ApexCharts from "vue3-apexcharts";

const url = "/api/films";
const listFilms = reactive([]);
const chartData = ref([]);
const chartOptions = ref({
  chart: { id: "films-participants", toolbar: { show: false } },
  title: { text: "Nombre de Participants par Film", align: "center" },
  xaxis: { categories: [] },
  yaxis: { title: { text: "Participants" } },
  colors: ['var(--color-primary)'],
});

const groupChartData = ref([]);
const groupChartOptions = ref({
  chart: {
    id: "group-participants-chart",
  },
  labels: [], // Groupes (Acteur, Réalisateur, etc.)
  title: {
    text: "Participants par Groupe",
    align: "center"
  },
  legend: {
    position: 'bottom'
  },
  colors: ['var(--color-primary-dark)', 'var(--color-primary)', 'var(--color-secondary)', 'var(--color-accent)'],
});


const dialogAdd = ref(false);
const dialogDelete = ref(false);
const dialogEdit = ref(false);
const showEditFilm = ref(false);
const showAddFilm = ref(false);
const selectedFilm = ref(null);

const openEditForm = (film) => {
  selectedFilm.value = { ...film };
  showEditFilm.value = true;
};

function fetchFilms() {
  fetch(url)
    .then((response) => response.json())
    .then((dataJSON) => {
      listFilms.splice(0, listFilms.length, ...dataJSON._embedded.films);
      updateChartData();
      updateGroupChartData();
    })
    .catch((error) => console.error("Erreur récupération films :", error));
}

async function updateChartData() {
  const categories = [];
  const seriesData = [];

  for (const film of listFilms) {
    try {
      const res = await fetch(`/api/joues/films/participants?idFilm=${film.idFilm}`);
      const dataJSON = await res.json();
      const count = dataJSON.page?.totalElements || 0;
      categories.push(film.titre);
      seriesData.push(count);
    } catch (err) {
      console.error(`Erreur participants film ${film.titre} :`, err);
    }
  }

  chartData.value = [
    {
      name: "Participants",
      data: seriesData,
    },
  ];

  chartOptions.value = {
    ...chartOptions.value,
    xaxis: {
      ...chartOptions.value.xaxis,
      categories: [...categories],
    }
  }
}

async function updateGroupChartData() {
  try {
    const res = await fetch("/api/joues?page=0&size=107");
    const dataJSON = await res.json();

    const groupCountMap = new Map();

    for (const joue of dataJSON.joues) {
      const groupe = joue.groupe || "Inconnu";
      groupCountMap.set(groupe, (groupCountMap.get(groupe) || 0) + 1);
    }

    groupChartOptions.value = {
      ...groupChartOptions.value,
      labels: [...groupCountMap.keys()],
    };

    groupChartData.value = [...groupCountMap.values()];
  } catch (err) {
    console.error("Erreur lors de la récupération des groupes :", err);
  }
}

const handleFilmAdded = async (newFilm) => {
  let filmData = { ...newFilm };
  if (newFilm.affiche?.startsWith('data:')) {
    const res = await fetch(newFilm.affiche);
    const blob = await res.blob();
    const file = new File([blob], "image.jpg", { type: "image/jpeg" });
    const formData = new FormData();
    formData.append('file', file);

    try {
      const uploadRes = await fetch('/api/files/upload', { method: 'POST', body: formData });
      const uploadData = await uploadRes.text();
      const fileUrlMatch = uploadData.match(/\/img\/[^"'}]+/);
      filmData.affiche = fileUrlMatch ? fileUrlMatch[0] : '';
    } catch (err) {
      console.error("Erreur upload image :", err);
      return;
    }
  }

  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(filmData),
  })
    .then((res) => res.json())
    .then(() => {
      fetchFilms();
      dialogAdd.value = true;
      showAddFilm.value = false;
    })
    .catch((err) => console.error("Erreur ajout film :", err));
};

function handlerDelete(film) {
  fetch(`${url}/${film.idFilm}`, { method: "DELETE" })
    .then((response) => {
      if (response.ok) fetchFilms();
      dialogDelete.value = true;
    })
    .catch((err) => console.error("Erreur suppression :", err));
}

const handleFilmEdit = async (updatedFilm) => {
  let filmData = { ...updatedFilm };
  if (updatedFilm.affiche?.startsWith("data:")) {
    const res = await fetch(updatedFilm.affiche);
    const blob = await res.blob();
    const file = new File([blob], "image.jpg", { type: "image/jpeg" });
    const formData = new FormData();
    formData.append("file", file);

    try {
      const uploadRes = await fetch("/api/files/upload", { method: "POST", body: formData });
      const uploadData = await uploadRes.text();
      const fileUrlMatch = uploadData.match(/\/img\/[^"'}]+/);
      filmData.affiche = fileUrlMatch ? fileUrlMatch[0] : "";
    } catch (err) {
      console.error("Erreur upload image :", err);
      return;
    }
  }

  fetch(`${url}/${updatedFilm.idFilm}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(filmData),
  })
    .then((res) => res.json())
    .then(() => {
      fetchFilms();
      dialogEdit.value = true;
      showEditFilm.value = false;
    })
    .catch((err) => console.error("Erreur mise à jour film :", err));
};

onMounted(fetchFilms);
</script>

<style scoped>
.add-btn {
  position: fixed;
  bottom: 70px;
  right: 20px;
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
