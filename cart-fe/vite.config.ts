import { fileURLToPath, URL } from 'node:url';

import federation from '@originjs/vite-plugin-federation';
import vue from '@vitejs/plugin-vue';
import { defineConfig } from 'vite';
import vueDevTools from 'vite-plugin-vue-devtools';

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    federation({
      name: 'shopping-cart',
      filename: 'remoteEntry.js',
      exposes: {
        './ShoppingCartPage': './src/views/pages/ShoppingCartPage.vue',
      },
      shared: ['vue', 'pinia'],
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    port: 8081,
    cors: true,
  },
});
