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
      component: () => import("@/layouts/MainLayout.vue"),
      children: [
        { path: "", component: () => import("@/views/IndexPage.vue") },
        { path: "films", component: () => import("@/views/FilmsView.vue") },
        {
          path: "films/:filmId",
          component: () => import("@/views/DetailsFilmsView.vue"),
        },
        { path: "about", component: () => import("@/views/AboutView.vue") },
      ],
    },
    {
      path: "/admin",
      component: () => import("@/layouts/MainLayout.vue"),
      beforeEnter: isAdministrator, // Protection d'accès pour toute la section admin
      children: [
        {
          path: "",
          component: () => import("@/views/admin/IndexPage.vue"),
          beforeEnter: [isAdministrator],
          children: [
            {
              path: "films",
              component: () => import("@/views/admin/FilmsPage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "participants",
              component: () => import("@/views/admin/ParticipantsPage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "actualites",
              component: () => import("@/views/admin/ActualitePage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "moment",
              component: () => import("@/views/admin/MomentPage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "anecdotes",
              component: () => import("@/views/admin/AnecdotesPage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "images",
              component: () => import("@/views/admin/ImagesFilmsPage.vue"),
              beforeEnter: [isAdministrator],
            },
            {
              path: "commentaires",
              component: () => import("@/views/admin/CommentairesPage.vue"),
              beforeEnter: [isAdministrator],
            },
          ],
        },
      ],
    },
    {
      path: "/login",
      component: () => import("@/layouts/LoginLayout.vue"),
      children: [
        { path: "", component: () => import("@/views/login/LoginPage.vue") },
      ],
    },
  ],
});

export default router;
