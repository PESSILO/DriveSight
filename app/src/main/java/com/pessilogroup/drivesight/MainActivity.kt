package com.pessilogroup.drivesight

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.appcompat.app.AppCompatActivity
import com.pessilogroup.drivesight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()

        // Check for notification listener permission and prompt user if not granted
        if (!isNotificationServiceEnabled()) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(intent)
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val contentResolver = contentResolver
        val enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        val packageName = packageName
        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName)
    }

    class NotificationListener : NotificationListenerService() {
        override fun onNotificationPosted(sbn: StatusBarNotification) {
            val notification = sbn.notification
            val title = notification.extras.getString("android.title") ?: ""
            val content = notification.extras.getString("android.text") ?: ""
            val timestamp = sbn.postTime

            // Call the native method
            onNotificationReceived(title, content, timestamp)
        }
    }

    /**
     * A native method that is implemented by the 'drivesight' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'drivesight' library on application startup.
        init {
            System.loadLibrary("drivesight")
        }

        // This is the JNI function we'll call from the NotificationListenerService
        @JvmStatic
        external fun onNotificationReceived(title: String, content: String, timestamp: Long): Boolean
    }
}
