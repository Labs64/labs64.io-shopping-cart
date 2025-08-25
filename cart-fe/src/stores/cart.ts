// pinia
import { defineStore } from 'pinia';

// api
import { fetchCart as fetchCartAPI } from '@/services/api/cart';

// types
import type { CartState } from '@/types/stores/cart';

export default defineStore('cart', {
  // persist: {
  //   items: 'secure',
  // },

  state(): CartState {
    return {
      items: [],
    };
  },

  getters: {},

  actions: {
    async fetchCart(code: string): Promise<void> {
      const cart = await fetchCartAPI(code);
      this.items = cart.items;
    },
  },
});
