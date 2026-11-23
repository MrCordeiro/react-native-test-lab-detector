function loadWithNativeModule(nativeModule: unknown) {
  jest.resetModules();
  jest.doMock('../TestLabDetectorModule', () => ({ __esModule: true, default: nativeModule }));
  return require('../index') as typeof import('../index');
}

describe('isTestLab', () => {
  it('returns false when the native module is not available', () => {
    const { isTestLab } = loadWithNativeModule(null);

    expect(isTestLab()).toBe(false);
  });

  it.each([true, false])('returns the native value %p', (nativeValue) => {
    const { isTestLab } = loadWithNativeModule({ isTestLab: () => nativeValue });

    expect(isTestLab()).toBe(nativeValue);
  });

  it('returns false when the native call throws', () => {
    const { isTestLab } = loadWithNativeModule({
      isTestLab: () => {
        throw new Error('native failure');
      },
    });

    expect(isTestLab()).toBe(false);
  });
});
