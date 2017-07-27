package com.lozasolutions.fredompop.features.detail

import com.lozasolutions.fredompop.data.model.Pokemon
import com.lozasolutions.fredompop.data.model.Statistic
import com.lozasolutions.fredompop.features.base.MvpView

interface DetailMvpView : MvpView {

    fun showPokemon(pokemon: Pokemon)

    fun showStat(statistic: Statistic)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}