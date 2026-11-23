import { NativeModule, requireOptionalNativeModule } from 'expo';

declare class TestLabDetectorModule extends NativeModule {
  isTestLab(): boolean;
}

// The native module exists only on Android. On iOS this is null.
export default requireOptionalNativeModule<TestLabDetectorModule>('TestLabDetector');
