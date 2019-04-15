package com.binar.bagitronik.ui.fragment.myproduct.myproduct

import android.arch.lifecycle.Observer
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
import com.binar.bagitronik.helper.Constant.passedUserIDFragmentKey
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.activity.detail.DetailProductActivity
import kotlinx.android.synthetic.main.fragment_myproduct.view.*

/**
 *
Created by Ian Damping on 30/03/2019.
Github = https://github.com/iandamping
 */
class MyProductFragment : Fragment(), MyProductView {
    private var networkListener: LiveDataNetworkChangeListener? = null
    private var presenter: MyProductPresenter? = null
    private var actualViews: View? = null
    private var userId: Int? = null
    private val userData = BagitronikApp.gson.fromJson(BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    fun newInstance(type: Int?): MyProductFragment {
        val bundle = Bundle()
        val fragment = MyProductFragment()
        type?.let { bundle.putInt(passedUserIDFragmentKey, it) }
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        userId = args?.getInt(passedUserIDFragmentKey)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (userData != null) {
            if (userData.username?.firstName == null) {
                context?.alertHelperToEditAccount("Ubah profil terlebih dahulu")
            } else if (userData.token != null) {
                presenter = MyProductPresenter(this)
                presenter?.onAttach(context)
            }
        } else {
            context?.alertHelperToLogin("Masuk terlebih dahulu")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (presenter != null) {
            networkListener = context?.let {
                LiveDataNetworkChangeListener(it).apply {
                    this.observe(this@MyProductFragment.viewLifecycleOwner, Observer { status ->
                        when (status) {
                            true -> presenter?.onGetMyProduct(userData?.username?.id)
                            false -> context?.myToast("Periksa koneksi internet anda")
                        }
                    })
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_myproduct)
        if (presenter != null) {
            views?.let { presenter?.onCreateView(it) }
        }
        return views
    }

    override fun onGetAllListData(data: List<UserProducts>?) {
        if (data?.size!! > 0) {
            actualViews?.shimmerMyProduct?.stopShimmer()
            actualViews?.shimmerMyProduct?.gone()
            actualViews?.rvAllMyProduct?.layoutManager = LinearLayoutManager(context)
            data.let {
                actualViews?.rvAllMyProduct?.adapter = MyProductAdapter(it) {
                    context?.startActivity<DetailProductActivity> {
                        putExtra(Constant.passingDetailIdKey, it.id)
                    }
                }
            }
        }
    }

    override fun onGetEmptyListData() {
        actualViews?.shimmerMyProduct?.stopShimmer()
        actualViews?.shimmerMyProduct?.gone()
        actualViews?.tvNoItem?.visible()
    }

    override fun onFailedGetData(msg: String) {
        logE(msg)
        actualViews?.shimmerMyProduct?.stopShimmer()
        actualViews?.shimmerMyProduct?.gone()
    }

    override fun initView(view: View) {
        this.actualViews = view
    }

    override fun onPause() {
        super.onPause()
        actualViews?.shimmerMyProduct?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        actualViews?.shimmerMyProduct?.startShimmer()
    }
}