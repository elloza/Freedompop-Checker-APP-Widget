package com.lozasolutions.fredompop.features.splash

import android.os.Bundle
import com.lozasolutions.fredompop.R
import com.lozasolutions.fredompop.features.base.BaseActivity
import com.lozasolutions.fredompop.features.login.LoginActivity
import com.lozasolutions.fredompop.features.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashMvpView {

    @Inject lateinit var splashPresenter:SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        splashPresenter.attachView(this)


        Thread.sleep(2000)
        if(splashPresenter.isTokenAvailable()){
            startActivity(MainActivity.getStartIntent(this))
        }else{
            startActivity(LoginActivity.getStartIntent(this))
        }


    }

    override val layout: Int
        get() = R.layout.activity_splash

    override fun onDestroy() {
        super.onDestroy()
        splashPresenter.detachView()
    }

    companion object {
        private val POKEMON_COUNT = 20
    }
}