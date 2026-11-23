package io.github.mrcordeiro.testlabdetector

// Documented by Google: https://firebase.google.com/docs/test-lab/android/test-lab-play
internal const val TEST_LAB_SETTING = "firebase.test.lab"

// Never throws. If the read fails, the device does not count as a Test Lab device.
internal fun readTestLabSetting(readSetting: (String) -> String?): Boolean =
  try {
    readSetting(TEST_LAB_SETTING) == "true"
  } catch (e: Exception) {
    false
  }
