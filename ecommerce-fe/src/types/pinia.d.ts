import 'pinia';
import type { PersistOptions } from '@/types/stores/plugins/PersistPlugin.ts';

declare module 'pinia' {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  export interface DefineStoreOptionsBase<S, Store> {
    persist?: PersistOptions<S>;
  }

  export interface PiniaCustomProperties<Id, S, G, A> {
    $options: {
      id: Id;
      state?: () => S;
      getters?: G;
      actions?: A;
      persist?: PersistOptions<S>;
    };
  }
}
