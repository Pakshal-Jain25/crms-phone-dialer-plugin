import { WebPlugin } from '@capacitor/core';

import type { PhoneLoggerPlugin } from './definitions';

export class PhoneLoggerWeb extends WebPlugin implements PhoneLoggerPlugin {
  makeCall(_options: { phoneNumber: string }): Promise<void> {
    throw new Error('Method not implemented.');
  }
  callComplete(): Promise<{ callStatus: boolean; }> {
    throw new Error('Method not implemented.');
  }
  requestPermissions(): Promise<void> {
    throw new Error('Method not implemented.');
  }
  setDefaultDialer(): Promise<void> {
    throw new Error('Method not implemented.');
  }
  getCallLogs(): Promise<{ logData: any[] }> {
    throw new Error('Method not implemented.');
  }
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
