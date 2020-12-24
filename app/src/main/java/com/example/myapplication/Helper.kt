package com.example.myapplication

class Helper() {

    companion object {
        fun getGroupName(groupPermision: String): String? {
            if (groupPermision.contains("RECOGNITION") || groupPermision.contains("ACTIVITY_RECOGNITION")) {
                return "Activity Recognition"
            } else if (groupPermision.contains("CALENDAR")) {
                return "Calendar"
            } else if (groupPermision.contains("CALL_LOG")) {
                return "Call Log"
            } else if (groupPermision.contains("CAMERA")) {
                return "Camera"
            } else if (groupPermision.contains("CONTACTS")) {
                return "Contacts"
            } else if (groupPermision.contains("LOCATION")) {
                return "Location"
            } else if (groupPermision.contains("MICROPHONE") || groupPermision.contains("RECORD_AUDIO")) {
                return "Microphone"
            } else if (groupPermision.contains("PHONE")) {
                return "Phone"
            } else if (groupPermision.contains("SENSORS")) {
                return "Sensors"
            } else if (groupPermision.contains("SMS")) {
                return "Sms"
            } else if (groupPermision.contains("STORAGE")) {
                return "Storage"
            }
            return null
        }
    }
}