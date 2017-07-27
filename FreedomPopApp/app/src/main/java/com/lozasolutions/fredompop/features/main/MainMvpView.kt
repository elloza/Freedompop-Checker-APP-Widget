package com.lozasolutions.fredompop.features.main

import com.lozasolutions.fredompop.features.base.MvpView

interface MainMvpView : MvpView {

    fun showPokemon(pokemon: List<String>)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}