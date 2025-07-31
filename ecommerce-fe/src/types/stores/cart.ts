import type { CartItem } from '@/types/services/api/cart';

export interface CartState {
  items: CartItem[],
}
