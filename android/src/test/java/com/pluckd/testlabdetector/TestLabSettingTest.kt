package com.pluckd.testlabdetector

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TestLabSettingTest {
  @Test
  fun readsTheDocumentedSetting() {
    var requested: String? = null
    isTestLab { name -> requested = name; null }
    assertEquals("firebase.test.lab", requested)
  }

  @Test
  fun trueWhenSettingIsTrue() {
    assertTrue(isTestLab { "true" })
  }

  @Test
  fun falseWhenSettingIsMissing() {
    assertFalse(isTestLab { null })
  }

  @Test
  fun falseWhenSettingHasAnotherValue() {
    assertFalse(isTestLab { "false" })
    assertFalse(isTestLab { "" })
  }

  @Test
  fun falseWhenReadThrows() {
    assertFalse(isTestLab { throw SecurityException("denied") })
  }
}
