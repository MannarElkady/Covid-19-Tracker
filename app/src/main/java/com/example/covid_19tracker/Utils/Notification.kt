package com.example.covid_19tracker.Utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.covid_19tracker.R

object Covid_19Notification {
    fun displayApplicationNotification(context: Context, channelID: String, title:String, message: String){
        var builder = NotificationCompat.Builder(context,channelID)
            .setSmallIcon(R.drawable.bell_ring)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_HIGH).setVibrate(longArrayOf(1000,1000,1000,1000,1000)).setSound(
                Settings.System.DEFAULT_NOTIFICATION_URI)
        builder.setDefaults(Notification.DEFAULT_SOUND)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createCovid_19NotificationChannel(channelID, context)
        }
        NotificationManagerCompat.from(context).notify(1,builder.build())
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createCovid_19NotificationChannel(channelID: String, context: Context){
            val name =  context.getString(R.string.ApplicationChannelName)
            val descriptionText = context.getText(R.string.ApplicationChannelDescription)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID,name, importance).apply {
                description = descriptionText.toString()
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
    }
}