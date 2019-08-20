package ru.teamdroid.recipecraft.data.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationCompat
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.Constants
import ru.teamdroid.recipecraft.ui.base.extensions.getProperty
import java.util.*

class RecipecraftFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        remoteMessage.data.isNotEmpty().let {
            val title = remoteMessage.data["title"]
            val description = remoteMessage.data["description"]

            if (applicationContext.getProperty(Constants.NOTIFICATION, 1) == 1) {
                val notification = NotificationCompat.Builder(this, MAIN_NOTIFICATION_CHANNEL)
                        .setContentTitle(title)
                        .setContentText(description)
                        .setGroupSummary(true)
                        .setGroup(MAIN_GROUP)
                        .setSmallIcon(R.drawable.ic_about)
                        .build()

                val manager = NotificationManagerCompat.from(applicationContext)

                val id: Int = (Date().time / 1000L % Integer.MAX_VALUE).toInt()
                manager.notify(id, notification)
            }
        }
    }

    companion object {
        const val MAIN_GROUP = "MAIN_GROUP"
        const val MAIN_NOTIFICATION_CHANNEL = "1"
    }

}
