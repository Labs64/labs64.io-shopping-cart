import './assets/css/main.css'

import { createPinia } from 'pinia'
import { createApp } from 'vue'

import persistPlugin from '@/stores/plugins/persistPlugin';

import router from './router'
import App from './views/App.vue'

const pinia = createPinia();

pinia.use(persistPlugin);

const app = createApp(App)

app.use(pinia)
app.use(router)

app.mount('#app')
