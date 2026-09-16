package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.RewardPolicy
import com.example.data.preferences.PreferenceManager
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class RewardPolicyTest {
    @Test fun rewardsRequireTheirExactMilestone() {
        for (theme in listOf("blue", "pink", "green", "purple")) {
            assertFalse(RewardPolicy.isThemeUnlocked(theme, 0))
            assertFalse(RewardPolicy.isThemeUnlocked(theme, 49))
            assertTrue(RewardPolicy.isThemeUnlocked(theme, 50))
        }
        assertFalse(RewardPolicy.isThemeUnlocked("galactic", 99))
        assertTrue(RewardPolicy.isThemeUnlocked("galactic", 100))
        assertTrue(RewardPolicy.isThemeUnlocked("dark", 0))
        assertFalse(RewardPolicy.isThemeUnlocked("unknown", 100))
    }

    @Test fun lockedThemeCannotBeSelectedOrRestoredAndResetLocksRewards() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val prefs = PreferenceManager(context)
        prefs.clearAll()
        prefs.selectedTheme = "galactic"
        assertEquals("system", prefs.selectedTheme)
        context.getSharedPreferences("atiempo_prefs", Context.MODE_PRIVATE)
            .edit().putString("selected_theme", "blue").commit()
        assertEquals("system", prefs.selectedTheme)
        prefs.totalCompletions = 50
        prefs.selectedTheme = "blue"
        assertEquals("blue", prefs.selectedTheme)
        prefs.clearAll()
        assertEquals(0, prefs.totalCompletions)
        assertEquals("system", prefs.selectedTheme)
    }
}
