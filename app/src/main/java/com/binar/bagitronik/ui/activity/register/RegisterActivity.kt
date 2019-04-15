package com.binar.bagitronik.ui.activity.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.cleanUp
import com.binar.bagitronik.helper.fullScreenAnimation
import com.binar.bagitronik.helper.requestError
import com.binar.bagitronik.helper.startActivity
import com.binar.bagitronik.ui.activity.MainActivity
import com.binar.bagitronik.ui.activity.login.LoginActivity
import kotlinx.android.synthetic.main.activity_revisi_register.*

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class RegisterActivity : AppCompatActivity(), RegisterView {

    private val data: MutableMap<String, String> = mutableMapOf()
    private lateinit var presenter: RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_revisi_register)
        presenter = RegisterPresenter(this)
        presenter.onCreate(this)

    }

    override fun initView() {
        btnRegisRevisi.setOnClickListener {
            val email = etEmailRegisRevisi?.text.toString().trim()
            val password = etRegisPasswordRevisi?.text.toString().trim()

            when {
                email.isNullOrBlank() -> {
                    etEmailRegisRevisi.requestError(getString(R.string.not_null))
                }
                password.isNullOrBlank() -> {
                    etRegisPasswordRevisi.requestError(getString(R.string.not_null))
                }
                else -> {
                    data["password"] = password
                    data["password_confirmation"] = password
                    data["email"] = email
                    presenter.createUser(data)
                }

            }
        }
        btnLoginScreenRevisi.setOnClickListener {
            startActivity<LoginActivity> {
                finish()
            }
        }
    }

    override fun onSuccessRegister() {
        etEmailRegisRevisi.cleanUp()
        etRegisPasswordRevisi.cleanUp()
        startActivity<MainActivity> {
            finish()
        }
    }

    override fun onFailedRegister(msg: String) {
        etEmailRegisRevisi.cleanUp()
        etRegisPasswordRevisi.cleanUp()
    }
}