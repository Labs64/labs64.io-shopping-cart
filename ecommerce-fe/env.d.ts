/// <reference types="vite/client" />
interface ImportMetaEnv {
    // Router
  readonly VITE_ROUTER_HASH_MODE: string;

  readonly VITE_BASE_API_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
