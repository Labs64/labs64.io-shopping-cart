import type { Routes } from '@/types/router/routes';

const routes: Routes = [
  // ShoppingCart
  {
    path: '/',
    name: 'ShoppingCart',
    component: () => import('@/views/pages/ShoppingCartPage.vue'),
    props: (route) => ({
      code: route.query.code ?? '',
    }),
  },

  {
    path: '/500',
    name: '500',
    component: () => import('@/views/pages/500Page.vue'),
  },
];

export default routes;
