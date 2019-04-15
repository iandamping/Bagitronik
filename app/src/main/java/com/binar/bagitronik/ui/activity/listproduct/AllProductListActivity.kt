package com.binar.bagitronik.ui.activity.listproduct

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.helper.Constant.passingDataToListActivity
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.ui.activity.detail.DetailProductActivity
import kotlinx.android.synthetic.main.activity_allproduct_list.*

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class AllProductListActivity : AppCompatActivity(), AllProductView {
    lateinit var presenter: AllProductPresenter
    private var passedId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_allproduct_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = AllProductPresenter(this)
        presenter.onCreate(this)
        passedId = intent?.getIntExtra(passingDataToListActivity, 0)
        setupToolbar(passedId)
        presenter.getAllDataByType(passedId)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onGetAllListData(data: List<UserProducts>?) {
        shimmerGridListContainer.stopShimmer()
        shimmerGridListContainer.gone()
        rvAllProductList.layoutManager = GridLayoutManager(this, 2)
        data?.let {
            rvAllProductList.adapter = AllProductAdapter(it) {
                startActivity<DetailProductActivity> {
                    putExtra(Constant.passingDetailIdKey, it.id)
                }
            }
        }
    }

    override fun onFailedGetData(msg: String) {
        shimmerGridListContainer.stopShimmer()
        shimmerGridListContainer.gone()
        myToast(msg)
    }

    override fun initView() {
        swipeAllProductList.setOnRefreshListener {
            presenter.getAllDataByType(passedId)
            swipeAllProductList.isRefreshing = false
        }
    }

    override fun onPause() {
        super.onPause()
        shimmerGridListContainer.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        shimmerGridListContainer.startShimmer()
    }

    private fun setupToolbar(passedId: Int?) {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_back)
        upArrow?.setColorFilter(resources.getColor(R.color.colorComment), PorterDuff.Mode.SRC_ATOP)
        detailAllProductToolbar.setTitleTextColor(resources.getColor(R.color.black))
        detailAllProductToolbar.setSubtitleTextColor(resources.getColor(R.color.black))
        when (passedId) {
            1 -> {
                detailAllProductToolbar.title = "Perangkat Komputer"
            }
            2 -> {
                detailAllProductToolbar.title = "Telepon Selular"
            }
            3 -> {
                detailAllProductToolbar.title = "Kamera"
            }
            4 -> {
                detailAllProductToolbar.title = "Aksesoris Elektronik"
            }
        }
        setSupportActionBar(detailAllProductToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }
}