# @capacitor/phone-logger

This Capacitor plugin is used to fetch recent phone logs from user's device

## Install

```bash
npm install @capacitor/phone-logger
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`makeCall(...)`](#makecall)
* [`requestPermissions()`](#requestpermissions)
* [`setDefaultDialer()`](#setdefaultdialer)
* [`getCallLogs()`](#getcalllogs)
* [`callComplete()`](#callcomplete)
* [`addListener(string, ...)`](#addlistenerstring-)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### makeCall(...)

```typescript
makeCall(options: { phoneNumber: string; }) => Promise<void>
```

| Param         | Type                                  |
| ------------- | ------------------------------------- |
| **`options`** | <code>{ phoneNumber: string; }</code> |

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<void>
```

--------------------


### setDefaultDialer()

```typescript
setDefaultDialer() => Promise<void>
```

--------------------


### getCallLogs()

```typescript
getCallLogs() => Promise<{ logData: any[]; }>
```

**Returns:** <code>Promise&lt;{ logData: any[]; }&gt;</code>

--------------------


### callComplete()

```typescript
callComplete() => Promise<{ callStatus: boolean; }>
```

**Returns:** <code>Promise&lt;{ callStatus: boolean; }&gt;</code>

--------------------


### addListener(string, ...)

```typescript
addListener(eventName: string, listenerFunc: (data: any) => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                |
| ------------------ | ----------------------------------- |
| **`eventName`**    | <code>string</code>                 |
| **`listenerFunc`** | <code>(data: any) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>
