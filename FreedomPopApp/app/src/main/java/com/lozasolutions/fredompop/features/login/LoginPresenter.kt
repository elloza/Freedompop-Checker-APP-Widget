package com.lozasolutions.fredompop.features.login

import com.lozasolutions.fredompop.data.DataManager
import com.lozasolutions.fredompop.features.base.BasePresenter
import com.lozasolutions.fredompop.injection.ConfigPersistent
import com.lozasolutions.fredompop.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class LoginPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<LoginMvpView>() {

    override fun attachView(mvpView: LoginMvpView) {
        super.attachView(mvpView)
    }

    fun getPokemon(limit: Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getPokemonList(limit)
                .compose(SchedulerUtils.ioToMain<List<String>>())
                .subscribe({ pokemons ->
                    mvpView?.showProgress(false)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

}