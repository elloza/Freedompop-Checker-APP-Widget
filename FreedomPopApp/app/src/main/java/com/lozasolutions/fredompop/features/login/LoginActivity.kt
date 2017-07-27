package com.lozasolutions.fredompop.features.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import com.lozasolutions.fredompop.R
import com.lozasolutions.fredompop.features.base.BaseActivity
import com.lozasolutions.fredompop.features.common.ErrorView
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginMvpView, ErrorView.ErrorListener {


    @Inject lateinit var mMainPresenter: LoginPresenter

    @BindView(R.id.view_error) @JvmField var mErrorView: ErrorView? = null
    @BindView(R.id.progress) @JvmField var mProgress: ProgressBar? = null
    @BindView(R.id.recycler_pokemon) @JvmField var mPokemonRecycler: RecyclerView? = null
    @BindView(R.id.swipe_to_refresh) @JvmField var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    @BindView(R.id.toolbar) @JvmField var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)

        setSupportActionBar(mToolbar)

        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeResource(R.color.primary)
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.white)
        mSwipeRefreshLayout?.setOnRefreshListener { mMainPresenter.getPokemon(POKEMON_COUNT) }

        mErrorView?.setErrorListener(this)

        mMainPresenter.getPokemon(POKEMON_COUNT)
    }

    override val layout: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }

    override fun showProgress(show: Boolean) {
        Toast.makeText(applicationContext,"Progress",Toast.LENGTH_LONG).show()
    }

    override fun showError(error: Throwable) {
        mPokemonRecycler?.visibility = View.GONE
        mSwipeRefreshLayout?.visibility = View.GONE
        mErrorView?.visibility = View.VISIBLE
        Timber.e(error, "There was an error retrieving the pokemon")
    }


    override fun onReloadData() {
        mMainPresenter.getPokemon(POKEMON_COUNT)
    }

    companion object {

        private val POKEMON_COUNT = 20

        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }
}