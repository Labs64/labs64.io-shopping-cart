// types
import type { CartAPI } from '@/types/services/api/cart';

const cartPoolA = [
  { name: 'Milk', price: 29.99, quantity: 2, currency: 'EUR' },
  { name: 'Bread', price: 15.5, quantity: 1, currency: 'EUR' },
  { name: 'Cheese', price: 50, quantity: 1, currency: 'EUR' },
  { name: 'Butter', price: 22.75, quantity: 2, currency: 'EUR' },
  { name: 'Eggs (12 pack)', price: 31.0, quantity: 1, currency: 'EUR' },
];

const cartPoolB = [
  { name: 'Milk', price: 29.99, quantity: 2, currency: 'EUR' },
  { name: 'Bread', price: 15.5, quantity: 1, currency: 'EUR' },
  { name: 'Cheese', price: 50, quantity: 1, currency: 'EUR' },
];

export const fetchCart = async (code: string) => {
  if (code === 'codeA') {
    return { items: cartPoolA };
  }

  if (code === 'codeB') {
    return { items: cartPoolB };
  }

  return { items: [] };
};

export default <CartAPI>{
  fetchCart,
};
