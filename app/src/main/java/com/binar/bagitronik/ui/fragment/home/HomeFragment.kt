package com.binar.bagitronik.ui.fragment.home

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.helper.Constant.passingDataToListActivity
import com.binar.bagitronik.helper.Constant.passingDetailIdKey
import com.binar.bagitronik.model.product.ProductType
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.ui.activity.detail.DetailProductActivity
import com.binar.bagitronik.ui.activity.listproduct.AllProductListActivity
import com.binar.bagitronik.ui.fragment.home.adapters.AccecoriesAdapter
import com.binar.bagitronik.ui.fragment.home.adapters.CameraAdapter
import com.binar.bagitronik.ui.fragment.home.adapters.ComputerAdapter
import com.binar.bagitronik.ui.fragment.home.adapters.PhoneAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 *
Created by Ian Damping on 25/03/2019.
Github = https://github.com/iandamping
 */
class HomeFragment : Fragment(), HomeFragmentView {
    private lateinit var presenter: HomeFragmentPresenter
    private var networkListener: LiveDataNetworkChangeListener? = null
    private var views: View? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = HomeFragmentPresenter(this)
        presenter.onAttach(context)

    }

    private fun initToolbar(view: View) {
        view.btnSearchMain?.gone()
        view.btnSearchMain?.setOnClickListener {
            if (view.searchMain?.visibility == View.GONE) {
                view.searchMain?.visible()
                view.ivMain?.gone()
            } else if (views?.searchMain?.visibility == View.VISIBLE) {
                if (view.searchMain.text.toString().length != 0) {
                    context?.myToast(view.searchMain.text.toString())
                } else {
                    view.searchMain?.gone()
                    view.ivMain?.visible()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        networkListener = context?.let {
            LiveDataNetworkChangeListener(it).apply {
                this.observe(this@HomeFragment.viewLifecycleOwner, Observer { status ->
                    when (status) {
                        true -> presenter.getAllTypeData()
                        false -> context?.myToast("Periksa Koneksi Internet Anda")
                    }
                })
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_home)
        views?.let { presenter.onCreateView(it) }
        return views
    }

    override fun initView(view: View) {
        this.views = view
        initToolbar(view)
        view.swipe.setOnRefreshListener {
            presenter.getAllTypeData()
            view.swipe.isRefreshing = false
        }
    }

    override fun onPause() {
        super.onPause()
        views?.shimmer_container?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        views?.shimmer_container?.startShimmer()
    }

    override fun onFailedGetData(msg: String) {
        if (views != null) {
            views?.shimmer_container?.stopShimmer()
            views?.shimmer_container?.gone()
        }
    }

    override fun onGetTypeData(data: List<ProductType>?) {

    }

    override fun onGetPerangkatKomputerData(data: List<UserProducts>) {
        if (views != null) {

//            views?.showingBackground?.background = context?.resources?.getDrawable(R.drawable.gradient_bg)
            val snapHelper = RecyclerHorizontalSnapHelper()
            if (views?.rvPerangkatKomputer?.onFlingListener == null) {
                snapHelper.attachToRecyclerView(rvPerangkatKomputer)
            }
            views?.rvPerangkatKomputer?.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            views?.rvPerangkatKomputer?.adapter = ComputerAdapter(data) {

                context?.startActivity<DetailProductActivity> {
                    putExtra(passingDetailIdKey, it.id)
                }
            }
            views?.tvSeeAllPerangkatKomputer?.setOnClickListener {
                context?.startActivity<AllProductListActivity> {
                    putExtra(passingDataToListActivity, 1)
                }
            }
        }
    }

    override fun onGetTeleponSelularData(data: List<UserProducts>) {
        if (views != null) {
            val snapHelper = RecyclerHorizontalSnapHelper()
            if (views?.rvSmartPhone?.onFlingListener == null) {
                snapHelper.attachToRecyclerView(rvSmartPhone)
            }
            views?.rvSmartPhone?.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            views?.rvSmartPhone?.adapter = PhoneAdapter(data) {
                context?.startActivity<DetailProductActivity> {
                    putExtra(passingDetailIdKey, it.id)
                }
            }
            views?.tvSeeAllSmartPhone?.setOnClickListener {
                context?.startActivity<AllProductListActivity> {
                    putExtra(passingDataToListActivity, 2)
                }
            }
        }
    }

    override fun onGetKameraData(data: List<UserProducts>) {
        if (views != null) {
            val snapHelper = RecyclerHorizontalSnapHelper()
            if (views?.rvCamera?.onFlingListener == null) {
                snapHelper.attachToRecyclerView(rvCamera)
            }
            views?.rvCamera?.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            views?.rvCamera?.adapter = CameraAdapter(data) {
                context?.startActivity<DetailProductActivity> {
                    putExtra(passingDetailIdKey, it.id)
                }
            }
            views?.tvSeeAllCamera?.setOnClickListener {
                context?.startActivity<AllProductListActivity> {
                    putExtra(passingDataToListActivity, 3)
                }
            }
        }
    }

    override fun onGetAksesorisElektronikData(data: List<UserProducts>) {
        if (views != null) {
            views?.shimmer_container?.stopShimmer()
            views?.shimmer_container?.gone()
            val snapHelper = RecyclerHorizontalSnapHelper()
            if (views?.rvAksesorisElektronik?.onFlingListener == null) {
                snapHelper.attachToRecyclerView(rvAksesorisElektronik)
            }
            views?.rvAksesorisElektronik?.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            views?.rvAksesorisElektronik?.adapter = AccecoriesAdapter(data) {
                context?.startActivity<DetailProductActivity> {
                    putExtra(passingDetailIdKey, it.id)
                }
            }
            views?.tvSeeAllAksesorisElektronik?.setOnClickListener {
                context?.startActivity<AllProductListActivity> {
                    putExtra(passingDataToListActivity, 4)
                }
            }
        }
    }
}
