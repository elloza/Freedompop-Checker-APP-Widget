package com.lozasolutions.fredompop.features.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.OnClick
import com.github.florent37.viewanimator.ViewAnimator
import com.lozasolutions.fredompop.R
import com.lozasolutions.fredompop.features.base.BaseActivity
import com.lozasolutions.fredompop.features.main.MainActivity
import java.util.regex.Pattern
import javax.inject.Inject


class LoginActivity : BaseActivity(), LoginMvpView {

    @Inject lateinit var loginPresenter: LoginPresenter

    @BindView(R.id.loginButton) @JvmField var loginButton: Button? = null
    @BindView(R.id.usernameTextLayout) @JvmField var usernameTextLayout: TextInputLayout? = null
    @BindView(R.id.passwordTextLayout) @JvmField var passwordTextLayout: TextInputLayout? = null
    @BindView(R.id.username) @JvmField var username: EditText? = null
    @BindView(R.id.password) @JvmField var password: EditText? = null
    @BindView(R.id.logoFreedompop) @JvmField var logoFreedompop: ImageView? = null
    @BindView(R.id.mainLayout) @JvmField var mainLayout: ConstraintLayout? = null
    @BindView(R.id.progress) @JvmField var progress: ProgressBar? = null

    val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)

        loginPresenter.attachView(this)

        ViewAnimator
                .animate(logoFreedompop).fadeIn().duration(1000)
                .andAnimate(usernameTextLayout, passwordTextLayout, loginButton).fadeIn().duration(1000)
                .andAnimate(usernameTextLayout, passwordTextLayout, loginButton).scale(0.5f, 0.7f, 1f).accelerate().duration(1000)
                .start();
    }

    override val layout: Int
        get() = R.layout.activity_login

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.detachView()
    }


    @OnClick(R.id.loginButton)
    fun onLoginButtonClick(view: View) {

        val usernameText = username?.text.toString()
        val passwordText = password?.text.toString()

        if (validateData(username = usernameText, password = passwordText))
            loginPresenter.login(usernameText, passwordText)
    }


    fun validateData(username: String, password: String): Boolean {

        val validUsername = isValidUsername(username)
        val validPassword = isValidPassword(password)

        if (!validUsername)
            errorUsername()

        if (!validPassword)
            errorPassword()

        return validUsername && validPassword
    }

    fun errorPassword() {
        passwordTextLayout?.setError(getString(R.string.error_invalid_password))
    }

    fun errorUsername() {
        usernameTextLayout?.setError(getString(R.string.error_invalid_username))
    }

    fun isValidPassword(password: String): Boolean {
        return !password.isBlank()
    }

    fun isValidUsername(username: String): Boolean {
        return !username.isBlank() && validate(username)
    }

    override fun showProgress(show: Boolean) {

        if (show) {
            progress?.visibility = View.VISIBLE
        } else {
            progress?.visibility = View.INVISIBLE
        }
    }

    fun validate(emailStr: String): Boolean {
        val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    override fun showError(error: Throwable) {

        val snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_login), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), {
                    val usernameText = username?.text.toString()
                    val passwordText = password?.text.toString()
                    loginPresenter.login(usernameText, passwordText)
                })

        snackbar.show()
    }

    override fun showMainScreen() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }

    companion object {

        private val POKEMON_COUNT = 20

        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }
}