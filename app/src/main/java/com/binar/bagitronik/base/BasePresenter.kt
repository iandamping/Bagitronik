package com.binar.bagitronik.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import com.binar.bagitronik.R
import org.jetbrains.anko.layoutInflater

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
abstract class BasePresenter : BasePresenterHelper {
    private lateinit var alert: AlertDialog
    private lateinit var dialog: ProgressDialog

    protected fun setBaseDialog(ctx: Context, msg: String) {
        val dialogBuilder = AlertDialog.Builder(ctx)
        val inflater = ctx.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_loading, null)

        dialogBuilder.setView(dialogView)
        alert = dialogBuilder.create()
        alert.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        dialogView.tvCustomDialogTittle.text = dialogTittle
//        dialogView.tvCustomDialogMessage.text = msg
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(false)


//        dialog = ProgressDialog(ctx)
//        dialog.setTitle(dialogTittle)
//        dialog.setMessage(msg)
//        dialog.setCancelable(false)
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.isIndeterminate = true
    }

    protected fun setDialogShow(status: Boolean) {
//        if (status) {
//            dialog.dismiss()
//        } else {
//            dialog.show()
//        }

        if (status) {
            alert.dismiss()
        } else {
            alert.show()
        }
    }

}