import { createRouter, createWebHistory } from 'vue-router'
import {useAdministrateur} from "@/composables/administrateur.js";

function isAdministrator(to, from) {
  const user = useAdministrateur();

  if (user) return from;
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: () => import("../layouts/MainLayout.vue"),
      children: [
        { path: "", component: () => import("../views/IndexPage.vue") },
        {
          path: "films",
          component: () => import("../views/FilmsView.vue"),
        },
        {
          path: "films",
          children: [
            {
              path: ":filmId",
              component: () => import("../views/DetailsFilmsView.vue"),
            },
          ],
        },
        {
          path: "about",
          component: () => import("../views/AboutView.vue"),
        },
      ],
    },
    {
      path: "/admin",
      component: () => import("../layouts/MainLayout.vue"),
      /*children: [
        {
          path: "",
          component: () => import("../views/admin/IndexPage.vue"),
          beforeEnter: [isAdministrator],
          children: [
            {
              path: "enterprise",
              component: () => import("pages/admin/EnterprisePage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "organisations",
              component: () => import("pages/admin/OrganisationsPage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "services",
              component: () => import("pages/admin/ServicesPage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "users",
              component: () => import("pages/admin/UsersPage.vue"),
              beforeEnter: [isAdministrator],
            },
          ],
        },
      ],*/
    },
    {
      path: "/login",
      component: () => import("../layouts/LoginLayout.vue"),
      children: [
        { path: "", component: () => import("../views/login/LoginPage.vue") },
      ],
    },
  ],
})

export default router
