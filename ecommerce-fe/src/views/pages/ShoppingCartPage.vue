<template>
  <div class="container my-5">
    <h1 class="mb-4">Shopping Cart</h1>
    <p>Code: {{ code }}</p>

    <div class="list-group mb-4">
      <div
        v-for="(item, index) in items"
        :key="index"
        class="list-group-item d-flex justify-content-between align-items-center"
      >
        <div>
          <h5 class="mb-1">{{ item.name }}</h5>
          <small>Quantity: {{ item.quantity }}</small>
        </div>
        <div>
          <strong>{{ formatPrice(item) }}</strong>
        </div>
      </div>
    </div>

    <div class="d-flex justify-content-between align-items-center mb-3">
      <h4>Total:</h4>
      <h4>{{ totalPrice }} {{ currency }}</h4>
    </div>

    <div class="mb-4">
      <label
        for="payment-method"
        class="form-label"
        >Payment Method</label
      >
      <select
        id="payment-method"
        class="form-select"
        v-model="selectedPaymentMethod"
      >
        <option
          disabled
          value=""
        >
          Choose a payment method
        </option>
        <option value="cash">Cash</option>
        <option value="card">Card</option>
        <option value="applePay">Apple Pay</option>
      </select>
    </div>

    <button
      class="btn btn-primary w-100"
      :disabled="!selectedPaymentMethod"
      @click="purchase"
    >
      Purchase
    </button>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref, onMounted, watch } from 'vue';

// stores
import useCartShop from '@/stores/cart';

// types
import type { CartItem } from '@/types/services/api/cart';

defineOptions({
  name: 'ShoppingCartPage',
});

const props = defineProps<{
  code?: string;
}>();

// stores
const cartShop = useCartShop();

// state
const selectedPaymentMethod = ref('');
const loading = ref(false);

// computed
const items = computed(() => cartShop.items);
const totalPrice = computed(() => items.value.reduce((sum, item) => sum + item.price * item.quantity, 0));
const currency = computed(() => items.value[0]?.currency);

// watchers
watch(
  () => props.code,
  async (newCode) => {
    if (newCode) {
      await cartShop.fetchCart(newCode);
    }
  },
  { immediate: true },
);

// methods
const formatPrice = (item: CartItem) => `${(item.price * item.quantity).toFixed(2)} ${item.currency}`;

const purchase = () => {
  alert(`Purchased with ${selectedPaymentMethod.value}`);
};

// lifecycles
onMounted(async () => {
  if (props.code) {
    loading.value = true;
    try {
      await cartShop.fetchCart(props.code);
    } finally {
      loading.value = false;
    }
  }
});
</script>
