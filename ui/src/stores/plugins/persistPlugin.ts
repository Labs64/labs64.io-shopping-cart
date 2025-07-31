// lodash
import { forEach, isEmpty, isObject } from 'lodash-es';

// pinia
import type { PiniaPluginContext } from 'pinia';

// storage
import StorageService from '@/services/storage';

// types
import type { PersistOptions } from '@/types/stores/plugins/persistPlugin';

export default ({ store, options }: PiniaPluginContext) => {
  const persist: PersistOptions<typeof store.$state> = options.persist || {};

  if (isEmpty(persist)) {
    return;
  }

  const storageID = store.$id.trim();
  const getStorageKey = (key: string): string => `${storageID}.${key.trim()}`;

  const shouldPersistKey = (key: string, mode?: string): boolean => {
    if (!(key in store.$state)) {
      console.warn(`[Persist Plugin] Key "${key}" from persist options does not exist in the 
      "${store.$id}" store state.`);
      return false;
    }

    return !!mode && mode !== 'none';
  };

  forEach(persist, (mode, key) => {
    if (!shouldPersistKey(key, mode)) {
      return;
    }

    const keyStorage = getStorageKey(key);

    if (StorageService.has(keyStorage)) {
      const storedValue = StorageService.get(keyStorage);

      store.$patch((state) => {
        state[key] = storedValue;
      });
    }
  });

  store.$subscribe((_, state) => {
    forEach(persist, (mode, key) => {
      if (!shouldPersistKey(key, mode)) {
        return;
      }

      const keyStorage = getStorageKey(key);
      const value = state[key];

      if (!value || (isObject(value) && isEmpty(value))) {
        StorageService.remove(keyStorage);
      } else {
        StorageService.set(keyStorage, value, mode === 'secure');
      }
    });
  });
};
