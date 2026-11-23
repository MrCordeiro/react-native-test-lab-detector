import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

// Codegen reads this spec and generates NativeTestLabDetectorSpec for Android.
export interface Spec extends TurboModule {
  isTestLab(): boolean;
}

// The native module exists only on Android. On iOS this is null.
export default TurboModuleRegistry.get<Spec>('TestLabDetector');
