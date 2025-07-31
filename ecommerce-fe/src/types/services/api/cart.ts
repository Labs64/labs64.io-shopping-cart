export interface CartItem {
  name: string;
  price: number;
  currency: string;
  quantity: number;
}

export interface CartAPI {
  fetchCart(code: string): Promise<{ items: CartItem[] }>;
}
