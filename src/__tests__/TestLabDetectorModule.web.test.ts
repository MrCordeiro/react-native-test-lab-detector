import TestLabDetectorModule from '../TestLabDetectorModule.web';

it('has no native module on web', () => {
  expect(TestLabDetectorModule).toBeNull();
});
