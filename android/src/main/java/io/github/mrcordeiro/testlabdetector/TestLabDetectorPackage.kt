package io.github.mrcordeiro.testlabdetector

import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider

class TestLabDetectorPackage : BaseReactPackage() {
  override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? =
    if (name == TestLabDetectorModule.NAME) TestLabDetectorModule(reactContext) else null

  override fun getReactModuleInfoProvider() = ReactModuleInfoProvider {
    mapOf(
      TestLabDetectorModule.NAME to ReactModuleInfo(
        TestLabDetectorModule.NAME, // name
        TestLabDetectorModule.NAME, // className
        false, // canOverrideExistingModule
        false, // needsEagerInit
        false, // isCxxModule
        true, // isTurboModule
      )
    )
  }
}
