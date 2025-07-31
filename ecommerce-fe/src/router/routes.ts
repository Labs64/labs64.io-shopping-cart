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
];

export default routes;
