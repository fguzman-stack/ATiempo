package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.ui.translation.AppLanguage
import com.example.data.RewardPolicy
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("atiempo_prefs", Context.MODE_PRIVATE)

    var isOnboardingCompleted: Boolean
        get() = prefs.getBoolean("onboarding_completed", false)
        set(value) = prefs.edit().putBoolean("onboarding_completed", value).apply()

    var isEthicsAccepted: Boolean
        get() = prefs.getBoolean("ethics_accepted", false)
        set(value) = prefs.edit().putBoolean("ethics_accepted", value).apply()

    var selectedLanguage: AppLanguage
        get() {
            val code = prefs.getString("selected_language", null) ?: return AppLanguage.ES
            return AppLanguage.values().find { it.code == code } ?: AppLanguage.ES
        }
        set(value) = prefs.edit().putString("selected_language", value.code).apply()

    var selectedTheme: String // "system", "light", "dark", "blue", "pink", "green"
        get() = (prefs.getString("selected_theme", "system") ?: "system").let {
            if (RewardPolicy.isThemeUnlocked(it, totalCompletions)) it else "system"
        }
        set(value) {
            if (RewardPolicy.isThemeUnlocked(value, totalCompletions)) {
                prefs.edit().putString("selected_theme", value).apply()
            }
        }

    var isHapticEnabled: Boolean
        get() = prefs.getBoolean("haptic_enabled", true)
        set(value) = prefs.edit().putBoolean("haptic_enabled", value).apply()

    var totalCompletions: Int
        get() = prefs.getInt("total_completions", 0)
        set(value) = prefs.edit().putInt("total_completions", value).apply()

    fun incrementTotalCompletions(): Int {
        val current = totalCompletions
        val next = current + 1
        totalCompletions = next
        return next
    }

    fun observeTotalCompletions() = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == null || key == "total_completions") trySend(totalCompletions)
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        trySend(totalCompletions)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    var lastStreakCalcDate: String
        get() = prefs.getString("last_streak_calc_date", "") ?: ""
        set(value) = prefs.edit().putString("last_streak_calc_date", value).apply()

    var currentStreakSnapshot: Int
        get() = prefs.getInt("current_streak_snapshot", 0)
        set(value) = prefs.edit().putInt("current_streak_snapshot", value).apply()

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
