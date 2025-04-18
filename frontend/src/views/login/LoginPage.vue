<template>
  <v-container class="fill-height" fluid>
    <v-row justify="center" align="center">
      <v-col cols="12" md="4">
        <v-card>
          <v-card-title class="headline text-center">
            Connexion
          </v-card-title>
          <v-card-subtitle class="text-center">
            Entrez vos identifiants pour vous connecter
          </v-card-subtitle>
          <v-card-text>
            <v-alert
              v-if="errorMessage"
              type="error"
              variant="tonal"
            >
              {{ errorMessage }}
            </v-alert>
            <v-form v-model="valid" @submit.prevent="login">
              <v-text-field class="text-input"
                v-model="email"
                label="Email"
                :rules="[rules.required, rules.email]"
                required
              ></v-text-field>
              <v-text-field class="text-input"
                v-model="password"
                label="Mot de passe"
                :rules="[rules.required]"
                type="password"
                required
              ></v-text-field>
              <v-btn
                :disabled="!valid"
                :loading="loading"
                type="submit"
                block
                class="mt-4 btn"
              >
                Se connecter
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const email = ref("");
const password = ref("");
const valid = ref(false);
const loading = ref(false);
const errorMessage = ref("");

const rules = {
  required: v => !!v || "Ce champ est requis",
  email: v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || "L'email doit être valide"
};

const login = async () => {
  loading.value = true;
  errorMessage.value = "";

  try {
    const response = await fetch("/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: email.value.trim(),
        password: password.value,
      }),
    });

    if (!response.ok) {
      const errorText = await response.text(); // Récupère la réponse en texte brut
      try {
        const errorData = JSON.parse(errorText); // Essaie de parser en JSON
        throw new Error(errorData.message || "Identifiants incorrects");
      } catch {
        throw new Error(errorText || "Identifiants incorrects");
      }
    }

    const data = await response.json();
    const token = data.token;

    if (!token) {
      throw new Error("Token non reçu, problème d'authentification");
    }

    localStorage.setItem("token", token);

    if (data.email) {
      localStorage.setItem("user", JSON.stringify({
        email: data.email,
      }));
    }

    // Redirection vers la page d'accueil ou la page demandée
    const redirectPath = route.query.redirect || "/";
    await router.push(redirectPath);
  } catch (error) {
    console.error("Erreur de connexion:", error);
    errorMessage.value = error.message;
  } finally {
    password.value = "";
    loading.value = false;
  }
};

</script>

<style scoped>
.btn {
  background-color: var(--color-button);
  color: var(--color-text);
  box-shadow: inset 0 0 5px rgba(255, 255, 255, 0.6);
  margin-right: 10px;
}
</style>
