package com.example.data

/** Rewards are earned through completed intentions, never purchases or ads. */
object RewardPolicy {
    const val PREMIUM_COMPLETIONS = 50
    const val GALACTIC_COMPLETIONS = 100

    fun isThemeUnlocked(theme: String, completions: Int): Boolean = when (theme) {
        "system", "light", "dark" -> true
        "blue", "pink", "green", "purple" -> completions >= PREMIUM_COMPLETIONS
        "galactic" -> completions >= GALACTIC_COMPLETIONS
        else -> false
    }
}
