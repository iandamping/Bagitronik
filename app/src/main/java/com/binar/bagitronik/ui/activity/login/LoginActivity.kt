package com.binar.bagitronik.ui.activity.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.ui.activity.MainActivity
import com.binar.bagitronik.ui.activity.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_revisi_login.*

class LoginActivity : AppCompatActivity(), LoginView {


    private lateinit var presenter: LoginPresenter
    private val data: MutableMap<String, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_revisi_login)
        presenter = LoginPresenter(this)
        presenter.onCreate(this)
    }

    override fun onSuccessLogin(msg: String) {
        etEmailLogin.cleanUp()
        etPasswordLogin.cleanUp()
        startActivity<MainActivity>().also {
            finish()
        }
    }

    override fun onFailedLogin(msg: String) {
        etEmailLogin.cleanUp()
        etPasswordLogin.cleanUp()
        myToast(msg)
    }

    override fun initView() {
        btnRegisterFromLogin.setOnClickListener {
            startActivity<RegisterActivity> {

            }
        }
        btnLogin.setOnClickListener {
            validateData()
        }

    }

    private fun validateData() {
        val email = etEmailLogin?.text.toString().trim()
        val password = etPasswordLogin?.text.toString().trim()

        when {
            email.isNullOrBlank() -> {
                etEmailLogin.requestError(getString(R.string.not_null))
                return
            }
            password.isNullOrBlank() -> {
                etPasswordLogin.requestError(getString(R.string.not_null))
                return
            }
            else -> {
                data["password"] = password
                data["email"] = email
                presenter.loginUser(data)
            }

        }
    }
}
