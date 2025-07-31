export type PersistOptions<T> = { [K in keyof T]?: 'local' | 'secure' | 'none' } & {};
