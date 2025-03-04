<template>
  <router-view />
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router';
import { onMounted } from "vue";

/*const setUserInfo = async (token)
 => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };

    const query = qs.stringify({
      populate: ['role', 'enterprise', 'enterprise.logo', "photo", "organisations", "services"],
    }, {
      encodeValuesOnly: true,
    })

    const res = await api.get(`/api/users/me?${query}`, config);
    useUserStore().user = res.data

    if (useUserStore().user.role.name === 'SuperAdmin' && !useUserStore().user.enterprise){
      updateUser()
    }

    localStorage.setItem("user", JSON.stringify(res.data));

    return true;
  } catch (error) {
    throw error;
  }
}

const getExpDate = (token) => {
  const payload = token.split(".")[1];
  const decode = JSON.parse(window.atob(payload));
  return decode.exp;
}

const isAuthenticated = async () => {
  try {
    const token = localStorage.getItem("jwt");
    let expirationDate = 7;

    if (token) {
      const exp = getExpDate(token);
      let current_time = new Date().valueOf() / 1000;

      expirationDate = (exp - current_time) / 60 / 60 / 24;

      await setUserInfo(token)

    }

    if (!token || expirationDate < 1) {
      throw "Session expired";
    }

  } catch (error) {
    throw error;
  }
}

onMounted(async () => {
  const isAuth = await isAuthenticated();
  if (isAuth) {
    const token = localStorage.getItem("jwt");
    await setUserInfo(token);
  } else {
    return
  }
});*/
</script>


