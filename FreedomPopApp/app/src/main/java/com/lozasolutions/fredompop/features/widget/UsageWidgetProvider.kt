package com.lozasolutions.fredompop.features.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.lozasolutions.fredompop.FreedompopChecker
import com.lozasolutions.fredompop.R
import com.lozasolutions.fredompop.data.remote.model.UsageResponse
import com.lozasolutions.fredompop.features.splash.SplashActivity

/**
 * Created by BISITE on 16/11/16.
 */

class UsageWidgetProvider : AppWidgetProvider() {

    private var lastUsageIngo: UsageResponse? = null

    override fun onReceive(context: Context, intent: Intent) {

        if(intent.hasExtra(LAST_USAGE))
            lastUsageIngo = intent.getParcelableExtra<UsageResponse>(LAST_USAGE)
        super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val remoteViews = RemoteViews(context.packageName, R.layout.widget)

        if (!FreedompopChecker.get(context).component.sessionManager().isTokenAvailable()) {

            remoteViews.setViewVisibility(R.id.widgetBody, View.GONE)
            remoteViews.setViewVisibility(R.id.loginView, View.VISIBLE)
            // Create an Intent to launch LoginActivity
            val intent = Intent(context, SplashActivity::class.java)
            val loginPendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            remoteViews.setOnClickPendingIntent(R.id.loginButton, loginPendingIntent)

        } else {

            // Get the layout for the App Widget.
            remoteViews.setViewVisibility(R.id.loginView, View.GONE)
            remoteViews.setViewVisibility(R.id.widgetBody, View.VISIBLE)

            lastUsageIngo = FreedompopChecker.get(context).component.infoManager().getLastUsageAvailable()

            val inMB = 1024*1024
            val percentageLeft = 100.minus(lastUsageIngo?.percentUsed?.toInt()?: 0)
            val usedInMB = lastUsageIngo?.planLimitUsed?.toInt()?.div(inMB)
            val totalInMB = lastUsageIngo?.totalLimit?.toInt()?.div(inMB)

            val content = java.lang.String.format(context.getString(R.string.left_string),
                    usedInMB, totalInMB, percentageLeft)

            remoteViews.setTextViewText(R.id.textUsage,content)
            remoteViews.setProgressBar(R.id.progressUsage,100, percentageLeft ,false)

            // Create an Intent to launch LoginActivity
            val intent = Intent(context, SplashActivity::class.java)
            val loginPendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            remoteViews.setOnClickPendingIntent(R.id.refreshButton, loginPendingIntent)

        }

        // update views on all widgets
        var i = 0
        val len = appWidgetIds.size
        while (i < len) {
            val appWidgetId = appWidgetIds[i]
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
            i++
        }
    }

    companion object {

        val LAST_USAGE = "LAST_USAGE"
        const val USAGE_EXTRA = "usage_extra"

    }
}
