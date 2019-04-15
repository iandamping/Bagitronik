package com.binar.bagitronik.ui.fragment.myproduct.tukar

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.model.dealings.*
import com.binar.bagitronik.ui.activity.detailbarter.DetailBarterActivity
import kotlinx.android.synthetic.main.fragment_tukar.view.*

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
class TukarFragment : Fragment(), TukarView {
    private var networkListener: LiveDataNetworkChangeListener? = null
    private lateinit var presenter: TukarPresenter
    private var actualView: View? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = TukarPresenter(this)
        presenter.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        networkListener = context?.let {
            LiveDataNetworkChangeListener(it).apply {
                this.observe(this@TukarFragment.viewLifecycleOwner, Observer { status ->
                    when (status) {
                        true -> presenter.getTukarData()
                        false -> context?.myToast("Periksa koneksi internet anda")
                    }
                })
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_tukar)
        views?.let { presenter.onCreateView(it) }
        return views
    }

    override fun onPause() {
        super.onPause()
        actualView?.shimmerMyTukar?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        actualView?.shimmerMyTukar?.startShimmer()
    }

    override fun onGetTukar(data: List<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>?) {
        actualView?.shimmerMyTukar?.stopShimmer()
        actualView?.shimmerMyTukar?.gone()
        actualView?.rvAllMyTukar?.layoutManager = LinearLayoutManager(context)
        actualView?.rvAllMyTukar?.adapter = data?.let {
            TukarAdapter(it) { data ->
                context?.startActivity<DetailBarterActivity> {
                    putExtra(Constant.tukarPassedID, data.id)
                }
            }
        }
    }


    override fun onGetEmptyListData() {
        actualView?.shimmerMyTukar?.stopShimmer()
        actualView?.shimmerMyTukar?.gone()
        actualView?.tvTukarNoItem?.visible()
    }


    override fun onFailedGetDetailedData(msg: String) {
        logE(msg)
        actualView?.shimmerMyTukar?.stopShimmer()
        actualView?.shimmerMyTukar?.gone()
    }

    override fun initView(view: View) {
        this.actualView = view
    }
}