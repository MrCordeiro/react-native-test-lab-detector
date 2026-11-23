import NativeTestLabDetector from '../NativeTestLabDetector';

it('is null when the native module is not registered, for example on iOS', () => {
  expect(NativeTestLabDetector).toBeNull();
});
