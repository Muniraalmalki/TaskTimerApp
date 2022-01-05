package com.example.tasktimerapp.services

class TimeFormat {
    companion object {
        fun getTimerText(time: Double): String {
            val rounded = Math.round(time).toInt()
            val seconds = rounded % 86400 % 3600 % 60
            val minutes = rounded % 86400 % 3600 / 60
            val hours = rounded % 86400 / 3600
            return formatTime(seconds, minutes, hours)
        }

        private fun formatTime(seconds: Int, minutes: Int, hours: Int): String {
            return String.format("%02d", hours) + " : " + String.format("%02d",
                minutes) + " : " + String.format("%02d", seconds)
        }
    }
}