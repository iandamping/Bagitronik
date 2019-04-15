package com.binar.bagitronik.ui.fragment.myproduct.donasi

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
import com.binar.bagitronik.helper.Constant.donasiPassedID
import com.binar.bagitronik.model.dealings.*
import com.binar.bagitronik.ui.activity.detaildonasi.DetailDonasiActivity
import kotlinx.android.synthetic.main.fragment_donasi.view.*

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
class DonasiFragment : Fragment(), DonasiView {
    private var networkListener: LiveDataNetworkChangeListener? = null
    private var actualView: View? = null
    private lateinit var presenter: DonasiPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = DonasiPresenter(this)
        presenter.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        networkListener = context?.let {
            LiveDataNetworkChangeListener(it).apply {
                this.observe(this@DonasiFragment.viewLifecycleOwner, Observer { status ->
                    when (status) {
                        true -> presenter.getDonasiData()
                        false -> context?.myToast("Periksa koneksi internet anda")
                    }
                })
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_donasi)
        views?.let { presenter.onCreateView(it) }
        return views
    }

    override fun onGetEmptyListData() {
        actualView?.shimmerMyDonasi?.stopShimmer()
        actualView?.shimmerMyDonasi?.gone()
        actualView?.tvDonasiNoItem?.visible()
    }

    override fun onPause() {
        super.onPause()
        actualView?.shimmerMyDonasi?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        actualView?.shimmerMyDonasi?.startShimmer()
    }

    override fun onGetDonation(data: List<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>?) {
        actualView?.shimmerMyDonasi?.stopShimmer()
        actualView?.shimmerMyDonasi?.gone()
        actualView?.rvAllMyDonasi?.layoutManager = LinearLayoutManager(context)
        actualView?.rvAllMyDonasi?.adapter = data?.let {
            DonasiAdapter(it) { data ->
                context?.startActivity<DetailDonasiActivity> {
                    putExtra(donasiPassedID, data.id.toString())
                }
            }
        }

    }

    override fun onFailedGetDetailedData(msg: String) {
        actualView?.shimmerMyDonasi?.stopShimmer()
        actualView?.shimmerMyDonasi?.gone()
        logE(msg)
    }

    override fun initView(view: View) {
        this.actualView = view
    }
}