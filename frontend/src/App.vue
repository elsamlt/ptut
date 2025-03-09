<template>
  <router-view />
</template>

<script setup>
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const getExpDate = (token) => {
  try {
    const payload = token.split(".")[1];
    const decoded = JSON.parse(atob(payload));
    return decoded.exp;
  } catch (error) {
    console.error("Erreur lors du décodage du token :", error);
    return null;
  }
};

const isAuthenticated = async () => {
  return new Promise((resolve, reject) => {
    try {
      const token = localStorage.getItem("jwt");
      if (!token) throw new Error("Aucun token trouvé");

      const exp = getExpDate(token);
      if (!exp) throw new Error("Token invalide");

      const currentTime = Date.now() / 1000;
      if (exp < currentTime) throw new Error("Session expirée");

      resolve(true);
    } catch (error) {
      console.warn("Authentification échouée :", error.message);
      localStorage.removeItem("jwt");
      reject(error);
    }
  });
};

onMounted(() => {
  isAuthenticated();
});
</script>


