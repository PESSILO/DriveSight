package com.pessilogroup.drivesight

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class BuildGradleTest {

    @Test
    fun `applicationId is correct`() {
        assertThat(BuildConfig.APPLICATION_ID).isEqualTo("com.pessilogroup.drivesight")
    }

    @Test
    fun `versionCode is correct`() {
        assertThat(BuildConfig.VERSION_CODE).isEqualTo(1)
    }

    @Test
    fun `versionName is correct`() {
        assertThat(BuildConfig.VERSION_NAME).isEqualTo("1.0")
    }
}
