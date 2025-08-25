import useEnv from '@/composables/useEnv';
import type { Middleware } from '@/types/router/middleware.ts';

/**
 * Middleware for fetching dynamic env.
 *
 * This middleware loads env.
 *
 * @param to - The target route the user is navigating to.
 * @param from - The route the user is coming from.
 * @param next - The function to proceed with navigation.
 */
const middleware: Middleware = async (to, from, next) => {
  const { isLoaded, loadEnv } = useEnv();

  try {
    if (!isLoaded) {
      await loadEnv();
    }
    next();
  } catch (error) {
    console.error('Failed to load env.json:', error);
    next({ name: '500' });
  }
};

export default middleware;
