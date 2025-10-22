import { PluginListenerHandle } from "@capacitor/core";

export interface PhoneLoggerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  makeCall(options: { phoneNumber: string }): Promise<void>;
  requestPermissions(): Promise<void>;
  setDefaultDialer(): Promise<void>;
  getCallLogs(): Promise<{ logData: any[] }>;
  callComplete(): Promise<{ callStatus: boolean }>;

  addListener(eventName: string, listenerFunc: (data: any) => void): Promise<PluginListenerHandle>;

}
