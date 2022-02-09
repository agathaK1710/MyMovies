package com.android.workout

class Workout(val name: String, val description: String) {
    override fun toString(): String {
        return name
    }

    companion object {
        val workouts = arrayListOf<Workout>(
            Workout(
                "The Limb Loosener",
                "5 Handstand push-ups\n10 1-legged squats\n15 Pull-ups"
            ),
            Workout(
                "Core Agony",
                "100 Pull-ups\n100 Push-ups\n100 Sit-ups\n100 Squats"
            ),
            Workout(
                "The Wimp Special",
                "5 Pull-ups\n10 Push-ups\n15 Squats"
            ),
            Workout(
                "Strength and Length",
                "500 meter run\n21 x 1.5 pood kettleball swing\n21 x pull-ups"
            )
        )
    }
}