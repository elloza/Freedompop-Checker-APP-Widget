package com.lozasolutions.fredompop.features.detail

import com.lozasolutions.fredompop.data.remote.FreedompopAPI
import com.lozasolutions.fredompop.features.base.BasePresenter
import com.lozasolutions.fredompop.injection.ConfigPersistent
import javax.inject.Inject

@ConfigPersistent
class DetailPresenter @Inject
constructor(private val apiFreedompopAPI: FreedompopAPI) : BasePresenter<DetailMvpView>() {

    override fun attachView(mvpView: DetailMvpView) {
        super.attachView(mvpView)
    }

    fun getPokemon(name: String) {
        checkViewAttached()
        mvpView?.showProgress(true)

    }
}
