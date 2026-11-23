package com.pluckd.testlabdetector

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TestLabSettingTest {
  @Test
  fun readsTheDocumentedSetting() {
    var requested: String? = null
    readTestLabSetting { name -> requested = name; null }
    assertEquals("firebase.test.lab", requested)
  }

  @Test
  fun trueWhenSettingIsTrue() {
    assertTrue(readTestLabSetting { "true" })
  }

  @Test
  fun falseWhenSettingIsMissing() {
    assertFalse(readTestLabSetting { null })
  }

  @Test
  fun falseWhenSettingHasAnotherValue() {
    assertFalse(readTestLabSetting { "false" })
    assertFalse(readTestLabSetting { "" })
  }

  @Test
  fun falseWhenReadThrows() {
    assertFalse(readTestLabSetting { throw SecurityException("denied") })
  }
}
