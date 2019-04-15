package com.binar.bagitronik.helper

import android.animation.Animator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import com.binar.bagitronik.R
import com.binar.bagitronik.ui.activity.MainActivity
import com.binar.bagitronik.ui.activity.editprofile.EditProfileActivity
import com.binar.bagitronik.ui.activity.login.LoginActivity
import com.binar.bagitronik.ui.activity.uploadpage.UploadActivity
import kotlinx.android.synthetic.main.custom_failed_dialog.view.*
import kotlinx.android.synthetic.main.custom_succes_dialog.view.*
import kotlinx.android.synthetic.main.dialog_custom_loading_done.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.yesButton

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
fun FragmentActivity.alertHelper(tittle: String?) {
    tittle?.let {
        this.alert(it) {
            yesButton {
                it.dismiss()
                this@alertHelper.finish()
            }
        }.show()
    }
}

fun Context.alertHelper(tittle: String?) {
    tittle?.let {
        this.alert(it) {
            yesButton {
                it.dismiss()
            }
        }.show()
    }
}

fun Context.alertHelperToEditAccount(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@alertHelperToEditAccount.startActivity<EditProfileActivity>()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()

}

fun Context.alertHelperToLogin(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@alertHelperToLogin.startActivity<LoginActivity>()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()

}

fun FragmentActivity.alertHelperToEditAccount(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@alertHelperToEditAccount.startActivity<EditProfileActivity>()
        finish()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()

}


fun FragmentActivity.alertHelperToLogin(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@alertHelperToLogin.startActivity<LoginActivity>()
        finish()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()
}

fun FragmentActivity.alertHelperToUploadActivity(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@alertHelperToUploadActivity.startActivity<UploadActivity>()
        finish()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()


}

fun FragmentActivity.alertHelperToMainActivity(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_failed_dialog, null)
    dialogView.tvAlertMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@alertHelperToMainActivity.startActivity<MainActivity>()
        finish()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()


}

fun FragmentActivity.successAlertHelper(tittle: String?) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_succes_dialog, null)
    dialogView.tvAlertDoneMessage.text = tittle
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        this@successAlertHelper.startActivity<MainActivity>()
        finish()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.setCancelable(false)
    alert.show()
}

fun FragmentActivity.showCustomDoneDialog() {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_custom_loading_done, null)
    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alert.setCancelable(false)
    alert.setCanceledOnTouchOutside(false)
    alert.show()
    dialogView.lottieAnimationDone.addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            logD("Animation repeating")
        }

        override fun onAnimationEnd(animation: Animator?) {
            alert.dismiss()
            finish()
        }

        override fun onAnimationCancel(animation: Animator?) {
            logD("Animation cancel")
        }

        override fun onAnimationStart(animation: Animator?) {
            logD("Animation start")
        }

    })
}