# react-native-test-lab-detector

Detect Firebase Test Lab devices in a React Native app. Works in bare React Native and in Expo apps. The Google Play pre-launch report uses these devices.

## The problem

After you upload a release to Google Play, the pre-launch report runs a robot (the Robo crawler) on Firebase Test Lab devices. The robot opens your app and taps through it. Your analytics tool records each robot as a real user.

In one app, these robots were 89 of 111 users in the analytics tool for one month. Use this package to skip analytics, ads, or other side effects on these devices.

## Install

```sh
npm install react-native-test-lab-detector
```

In an Expo app, use `npx expo install react-native-test-lab-detector` instead.

This package contains native Android code (a Turbo Module). After you install it, make a new native build, for example `npx react-native run-android`, `npx expo run:android`, or an EAS build. Expo Go does not include this module. In Expo Go, `isTestLab()` returns `false`.

## Usage

```ts
import { isTestLab } from 'react-native-test-lab-detector';

if (isTestLab()) {
  // This is a Firebase Test Lab device.
}
```

### Example: skip PostHog on Test Lab devices

```ts
import PostHog from 'posthog-react-native';
import { isTestLab } from 'react-native-test-lab-detector';

// Do not make a PostHog client on Test Lab devices.
export const posthog = isTestLab()
  ? null
  : new PostHog('<your-project-api-key>', { host: 'https://eu.i.posthog.com' });

// Use optional chaining everywhere you send events.
posthog?.capture('app_opened');
```

## API

### `isTestLab(): boolean`

- Returns `true` when the app runs on a Firebase Test Lab device.
- Returns `false` in all other cases.
- The function is synchronous.
- The function never throws. If the check fails, it returns `false`.

## Platform behavior

| Platform | Behavior                                                                                 |
| -------- | ---------------------------------------------------------------------------------------- |
| Android  | Reads the system setting `firebase.test.lab`. Returns `true` when the value is `"true"`. |
| iOS      | Always returns `false`. The package has no iOS native code.                              |
| Web      | Always returns `false`.                                                                  |

## How it works

Google documents this check on the page [Firebase Test Lab and Google Play](https://firebase.google.com/docs/test-lab/android/test-lab-play):

```kotlin
Settings.System.getString(contentResolver, "firebase.test.lab") == "true"
```

The package does only this check. It does not need Firebase in your app.

## Known risk: Firebase Test Lab shuts down in 2027

Google shuts down Firebase Test Lab on **2027-09-30**. Google replaces it with the Developer Device Platform.

This check is **documented today**. It is not a permanent guarantee. As of 2026-11-23, no Google page says what happens after that date to:

- the Google Play pre-launch report, or
- the `firebase.test.lab` system setting.

After that date, `isTestLab()` can return `false` on the new robot devices. Check your analytics after the change. If you find a new documented signal, please open an issue.

## Development

```sh
pnpm install
pnpm lint
pnpm typecheck
pnpm test          # Jest tests for the JS layer
pnpm test:kotlin   # JUnit tests for the Android check
```

`pnpm test:kotlin` needs Java 11 to 21, `curl`, and `unzip`. It does not need Gradle or an Android SDK. It downloads a pinned Kotlin compiler and JUnit into `.tools/`.

## License

MIT
