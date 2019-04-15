package com.binar.bagitronik.ui.fragment.home

import android.content.Context
import android.view.View
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.base.BaseFragmentPresenter
import com.binar.bagitronik.helper.Constant.dialogCommonResponse
import com.binar.bagitronik.helper.logD
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *
Created by Ian Damping on 25/03/2019.
Github = https://github.com/iandamping
 */
class HomeFragmentPresenter(private val mView: HomeFragmentView) : BaseFragmentPresenter() {
    private var ctx: Context? = null
    private lateinit var compose: CompositeDisposable

    override fun onAttach(context: Context?) {
        this.ctx = context
        compose = CompositeDisposable()
        setBaseDialog(ctx, dialogCommonResponse)
    }


    fun getAllTypeData() {
//        setDialogShow(false)
        compose.addAll(api.getProductTypeRx().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMapIterable {
            return@flatMapIterable it
        }.doOnNext {
            getAllType(it.id)
        }.subscribe({
        }, { logD(it.localizedMessage) }))
    }

    private fun getAllType(tipe: Int?) {
//        setDialogShow(true)
        when (tipe) {
            1 -> api.getAllType(tipe.toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe { mView.onGetPerangkatKomputerData(it) }
            2 -> api.getAllType(tipe.toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe { mView.onGetTeleponSelularData(it) }
            3 -> api.getAllType(tipe.toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe { mView.onGetKameraData(it) }
            4 -> api.getAllType(tipe.toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe { mView.onGetAksesorisElektronikData(it) }
        }
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }
}