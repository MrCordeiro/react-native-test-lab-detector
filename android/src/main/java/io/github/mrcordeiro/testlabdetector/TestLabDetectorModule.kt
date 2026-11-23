package io.github.mrcordeiro.testlabdetector

import android.provider.Settings
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = TestLabDetectorModule.NAME)
class TestLabDetectorModule(reactContext: ReactApplicationContext) :
  NativeTestLabDetectorSpec(reactContext) {

  override fun getName() = NAME

  override fun isTestLab(): Boolean {
    val resolver = reactApplicationContext.contentResolver
    return readTestLabSetting { name -> Settings.System.getString(resolver, name) }
  }

  companion object {
    const val NAME = "TestLabDetector"
  }
}
