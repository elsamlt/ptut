import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    /*vueDevTools(),*/
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  // Change build paths to make them Maven compatible
  build: {
    outDir: 'target/dist',
    assetsDir: 'static',
  },
  // Utile uniquement quand oon utilise le serveur de développement node
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8989', // Serveur backend
        changeOrigin: true,
      },
      '/img': {
        target: 'http://localhost:8989', // Proxy pour les images
        changeOrigin: true,
      }
    },
  },
})
