// Styles globaux
import "./assets/main.css";

// Vue et bibliothèques
import { createApp } from "vue";
import { createPinia } from "pinia";
import router from "./router/index.jsx";

// Vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

// Material Design Icons
import "@mdi/font/css/materialdesignicons.css";

// Composant principal
import App from "./App.vue";

// Initialisation de Vuetify
const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: "mdi", // Utiliser les icônes MDI avec Vuetify
  },
});

// Création de l'application Vue avec Pinia et Vue Router
const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(vuetify);

app.mount("#app");
