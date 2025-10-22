import { registerPlugin } from '@capacitor/core';

import type { PhoneLoggerPlugin } from './definitions';

const PhoneLogger = registerPlugin<PhoneLoggerPlugin>('PhoneLogger', {
  web: () => import('./web').then((m) => new m.PhoneLoggerWeb()),
});

export * from './definitions';
export { PhoneLogger };
