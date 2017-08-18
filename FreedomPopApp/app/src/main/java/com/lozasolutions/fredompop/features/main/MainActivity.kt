package com.lozasolutions.fredompop.features.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import butterknife.BindView
import com.lozasolutions.fredompop.R
import com.lozasolutions.fredompop.data.remote.model.UsageResponse
import com.lozasolutions.fredompop.features.base.BaseActivity
import com.lozasolutions.fredompop.features.common.ErrorView
import devlight.io.library.ArcProgressStackView
import org.joda.time.Interval
import timber.log.Timber
import java.util.*
import javax.inject.Inject




class MainActivity : BaseActivity(), MainMvpView, ErrorView.ErrorListener {


    @Inject lateinit var mMainPresenter: MainPresenter
    @BindView(R.id.view_error) @JvmField var mErrorView: ErrorView? = null
    @BindView(R.id.toolbar) @JvmField var mToolbar: Toolbar? = null

    //Progress
    @BindView(R.id.progress) @JvmField var mProgress: ProgressBar? = null
    @BindView(R.id.arcProgress) @JvmField var arcProgressStackView: ArcProgressStackView? = null

    //Info texts
    @BindView(R.id.infoLeft) @JvmField var infoLeft: TextView? = null
    @BindView(R.id.infoRight) @JvmField var infoRight: TextView? = null
    @BindView(R.id.detailUsageText) @JvmField var detailUsageText: TextView? = null
    @BindView(R.id.detailQuota) @JvmField var detailQuota: TextView? = null


    //Alerts
    @BindView(R.id.alertSwitch) @JvmField var alertSwitch: Switch? = null
    @BindView(R.id.timeSpinner) @JvmField var timeSpinner: Spinner? = null
    @BindView(R.id.alertText) @JvmField var alertText: TextView? = null
    @BindView(R.id.alertSeekBar) @JvmField var alertSeekBar: SeekBar? = null


    val MODEL_COUNT = 2
    val inMB = 1024*1024

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)

        setSupportActionBar(mToolbar)

        val startColors = resources.getStringArray(R.array.devlight)
        val endColors = resources.getStringArray(R.array.default_preview)
        val bgColors = resources.getStringArray(R.array.polluted_waves)

        // Parse colors

        mErrorView?.setErrorListener(this)

        mMainPresenter.getUserUsage()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_refresh -> {

                mMainPresenter.getUserUsage()
                return true
            }
            R.id.action_logout -> {

                //TODO Logout
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override val layout: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }

    override fun showUserUsage(userUsageResponse:UsageResponse) {

        val models = ArrayList<ArcProgressStackView.Model>()

        val planLimitedUsedInMB = userUsageResponse.totalLimit /inMB
        val usedInMB = userUsageResponse.planLimitUsed / inMB
        val percentageUsed = userUsageResponse.percentUsed *100


        val intervalBilling = Interval(userUsageResponse.startTime,userUsageResponse.endTime)
        val totalDays = intervalBilling.toDuration().toStandardDays().days

        val intervalToday = Interval(System.currentTimeMillis(),userUsageResponse.endTime)
        val daysLeft = intervalToday.toDuration().toStandardDays().days
        val percentage = (100f*daysLeft)/totalDays

        //Percentage usage
        var usageString = "%3d MB".format(usedInMB.toInt())

        if(usedInMB >= 1000){
            usageString = "%2.0f GB".format(usedInMB/1000f)
        }

        //Texts

        models.add(ArcProgressStackView.Model(usageString,
                percentageUsed,
                ContextCompat.getColor(baseContext, R.color.primary_light_light),
                ContextCompat.getColor(baseContext, R.color.primary)))

        // Day left
        val days = "%d days left".format(daysLeft)
        models.add(ArcProgressStackView.Model(days, percentage,
                ContextCompat.getColor(baseContext, R.color.primary_light_light),
                ContextCompat.getColor(baseContext, R.color.primary_light)))

        //texts

        arcProgressStackView?.models = models


        arcProgressStackView?.requestLayout();
        arcProgressStackView?.postInvalidate();


    }


    override fun showProgress(show: Boolean) {
        if (show) {

            mErrorView?.visibility = View.GONE
        } else {
            mProgress?.visibility = View.GONE
        }
    }

    override fun showError(error: Throwable) {
        mErrorView?.visibility = View.VISIBLE
        Timber.e(error, "There was an error retrieving the pokemon")
    }

    override fun onReloadData() {
        mMainPresenter.getUserUsage()
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }
}