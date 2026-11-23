import TestLabDetectorModule from './TestLabDetectorModule';

/**
 * Returns true when the app runs on a Firebase Test Lab device,
 * for example a Google Play pre-launch report robot.
 * Returns false on iOS, on web, and when the check fails. Never throws.
 */
export function isTestLab(): boolean {
  if (!TestLabDetectorModule) {
    return false;
  }
  try {
    return TestLabDetectorModule.isTestLab() === true;
  } catch {
    return false;
  }
}
