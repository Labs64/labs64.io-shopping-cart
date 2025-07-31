/// <reference types="vite/client" />
interface ImportMetaEnv {
    // Router
  readonly VITE_ROUTER_HASH_MODE: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
