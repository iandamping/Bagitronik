package com.binar.bagitronik.ui.fragment.landfills

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.helper.Constant.passingLandfillsID
import com.binar.bagitronik.model.landfills.LandfillsData
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.activity.detaillandfills.DetailLandfillsActivity
import kotlinx.android.synthetic.main.fragment_landfills.view.*

/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
class LandfillsFragment : Fragment(), LandfillsView {
    private var presenter: LandfillsPresenter? = null
    private var actualView: View? = null
    private val userData = BagitronikApp.gson.fromJson(BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (userData != null) {
            if (userData.username?.firstName == null) {
                context?.alertHelperToEditAccount("Ubah profil terlebih dahulu")
            } else if (userData.token != null) {
                presenter = LandfillsPresenter(this)
                presenter?.onAttach(context)
            }
        } else {
            context?.alertHelperToLogin("Masuk terlebih dahulu")
        }
//        presenter = LandfillsPresenter(this)
//        presenter.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_landfills)
        if (presenter != null) {
            views?.let { presenter?.onCreateView(it) }
        }
        return views
    }

    override fun onFailGetData(msg: String) {
        actualView?.shimmer_landfills?.stopShimmer()
        actualView?.shimmer_landfills?.gone()
        context?.alertHelper(msg)
    }

    override fun onSuccesGetData(data: List<LandfillsData>?) {
        actualView?.shimmer_landfills?.stopShimmer()
        actualView?.shimmer_landfills?.gone()
        actualView?.rvLandfills?.layoutManager = LinearLayoutManager(context)
        data?.let {
            actualView?.rvLandfills?.adapter = LandfillsAdapter(it) { data ->
                context?.startActivity<DetailLandfillsActivity> {
                    putExtra(passingLandfillsID, data.id)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        actualView?.shimmer_landfills?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        actualView?.shimmer_landfills?.startShimmer()
    }

    override fun initView(view: View) {
        this.actualView = view
        initToolbar(view)
    }

    private fun initToolbar(view: View) {
        view.toolbarLandfills.title = "Daftar Lokasi"
        view.toolbarLandfills.setTitleTextColor(resources.getColor(R.color.colorComment))
        view.toolbarLandfills.setSubtitleTextColor(resources.getColor(R.color.colorComment))
    }
}