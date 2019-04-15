package com.binar.bagitronik.ui.activity.detaillandfills

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.fullScreenAnimation
import com.binar.bagitronik.helper.logE
import com.binar.bagitronik.helper.startActivity
import com.binar.bagitronik.model.landfills.LandfillsData
import com.binar.bagitronik.ui.activity.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detail_landfills.*
import java.util.*


/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
class DetailLandfillsActivity : AppCompatActivity(), DetailLandfillsView {
    private lateinit var presenter: DetailLandfillsPresenter
    private var selectedTps: LatLng? = null
    private var passedLandFillsId: Int? = null
    private val ZOOM_LEVEL = 13f
    private var latitude: Double? = null
    private var longitude: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(com.binar.bagitronik.R.layout.activity_detail_landfills)
        presenter = DetailLandfillsPresenter(this)
        presenter.onCreate(this)
        passedLandFillsId = intent?.getIntExtra(Constant.passingLandfillsID, 0)
        presenter.onGetLandfillsData(passedLandFillsId)


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                startActivity<MainActivity> {
                    putExtra(Constant.switchBackToMain, "2")
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity> {
            putExtra(Constant.switchBackToMain, "2")
            finish()
        }
    }


    override fun onGetDetailLandFills(data: LandfillsData?) {
        selectedTps = LatLng(data?.latitude?.toDouble()!!, data.longitude?.toDouble()!!)
        val mapFragment: SupportMapFragment? =
                supportFragmentManager.findFragmentById(com.binar.bagitronik.R.id.mapLandFills) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedTps, ZOOM_LEVEL))
            it.addMarker(MarkerOptions().position(selectedTps!!))
        }
        this.latitude = data?.latitude.toDouble()
        this.longitude = data.longitude.toDouble()
        tvDataLokasi.text = data?.namatempat
        tvTitikLokasi.text = data?.alamat
        tvNoTelpon.text = data?.kontakpengelola
    }

    override fun onFailGetData(msg: String) {
        logE(msg)
    }

    override fun initView() {
        initToolbar()
        btnLihatLokasi.setOnClickListener {
            if (latitude != null && longitude != null) {
                val uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
        }
    }

    private fun initToolbar() {
        val upArrow = ContextCompat.getDrawable(this, com.binar.bagitronik.R.drawable.ic_arrow_back_blue_24dp)
        upArrow?.setColorFilter(resources.getColor(com.binar.bagitronik.R.color.colorComment), PorterDuff.Mode.SRC_ATOP)
        DetailLandFillsToolbar.title = "Detail Lokasi"
        DetailLandFillsToolbar.setTitleTextColor(resources.getColor(com.binar.bagitronik.R.color.colorComment))
        DetailLandFillsToolbar.setSubtitleTextColor(resources.getColor(com.binar.bagitronik.R.color.colorComment))
        setSupportActionBar(DetailLandFillsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

    }
}