package com.pluckd.testlabdetector

import android.provider.Settings
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class TestLabDetectorModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("TestLabDetector")

    Function("isTestLab") {
      val resolver = appContext.reactContext?.contentResolver ?: return@Function false
      isTestLab { name -> Settings.System.getString(resolver, name) }
    }
  }
}
