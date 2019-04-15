package com.binar.bagitronik.ui.activity.detail

import android.app.Dialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.helper.Constant.passingEditProductKey
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.activity.MainActivity
import com.binar.bagitronik.ui.activity.detaildonasi.DetailDonasiActivity
import com.binar.bagitronik.ui.activity.uploadpage.UploadActivity
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.activity_revisi_detail_product.*
import kotlinx.android.synthetic.main.permintaan_donasi.view.*
import kotlinx.android.synthetic.main.permintaan_tukar.view.*
import kotlinx.android.synthetic.main.popup_batal_permintaan.view.*
import org.jetbrains.anko.textColor


/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class DetailProductActivity : AppCompatActivity(), DetailProductView {

    private lateinit var presenter: DetailProductPresenter
    private var passedData: String? = null
    private var allItemNames = mutableListOf<String>()
    private var allItem = mutableListOf<UserProducts>()
    private var arraySpinnerAdapter: ArrayAdapter<String>? = null
    private var tempUserData: UserProducts? = null
    private val userData =
            gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(com.binar.bagitronik.R.layout.activity_revisi_detail_product)
        userChecker()
    }

    private fun userChecker() {
        if (userData != null) {
            if (userData.token != null) {
                logD(userData.username?.id.toString() + "pengguna id telah terdaftar")
                presenter = DetailProductPresenter(this)
                presenter.onCreate(this)
                onNewIntent(intent)
            }
        } else {
            alertHelperToLogin("Silakan Masuk Terlebih Dahulu")
        }
    }

    override fun onSuccessCreateTukar(data: String?) {
        if (data != null) {
            finish()
//            alertHelper("Done Tukar")
//            showCustomDoneDialog()
        }
    }

    override fun onSuccessCreateDonasi(data: String?) {
        if (data != null) {
            startActivity<DetailDonasiActivity> {
                putExtra(Constant.donasiPassedID, data)
                finish()
            }
        }
    }


    private fun initUserId(): Int? {
        val userData = gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)
        return userData?.username?.id
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                startActivity<MainActivity> {
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity> {
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.getDetailedProductData(intent?.getIntExtra(Constant.passingDetailIdKey, 0))
    }

    override fun onSuccessGetDetailedData(data: UserProducts?) {
        //profile
        if (data?.userData?.userImage?.url.isNullOrBlank()) {
            ivDetailRevisiProfile?.loadDrawable(R.drawable.profile)
        } else {
            ivDetailRevisiProfile?.loadUrl(data?.userData?.userImage?.url)
            ivDetailRevisiProfile.setOnClickListener {
                val alert = Dialog(this, R.style.AppTheme)
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                alert.setContentView(com.binar.bagitronik.R.layout.activity_fullscreen)
                alert.setCanceledOnTouchOutside(true)
                alert.fullScreenImageView.loadUrlOnlyForFullScreen(data?.userData?.userImage?.url)
                alert.show()
                alert.ivClose.setOnClickListener {
                    alert.dismiss()
                }
            }
        }
        tvDetailRevisiNameProfile.text = data?.userData?.firstName
        tvDetailRevisiProfileCity.text = data?.userData?.cityUser?.city
        //product
        if (data?.imagesData?.url.isNullOrBlank()) {
            ivDetailRevisiProduct.loadDrawable(com.binar.bagitronik.R.drawable.empty_image)
        } else {
            ivDetailRevisiProduct.loadUrl(data?.imagesData?.url)
            ivDetailRevisiProduct.setOnClickListener {
                val alert = Dialog(this, R.style.AppTheme)
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                alert.setContentView(com.binar.bagitronik.R.layout.activity_fullscreen)
                alert.setCanceledOnTouchOutside(true)
                alert.fullScreenImageView.loadUrlOnlyForFullScreen(data?.imagesData?.url)
                alert.show()
                alert.ivClose.setOnClickListener {
                    alert.dismiss()
                }
            }
        }
        if (data?.userData?.id == initUserId()) {
            ivEditProductRevisiItem.visible()
        } else if (data?.dealingData?.status_id == 1 && data.dealingData.user_id == initUserId()) {
            btnAjukanPermintaanRevisi.visible()
            btnAjukanPermintaanRevisi.text = "Batalkan Permintaan"
            btnAjukanPermintaanRevisi.textColor = resources.getColor(R.color.colorComment)
            btnAjukanPermintaanRevisi.background = resources.getDrawable(R.drawable.forbuttontoregister)
            btnAjukanPermintaanRevisi.setOnClickListener {
                showCancelDialog(data.dealingData.id)
            }
        } else {
            btnAjukanPermintaanRevisi.visible()
            btnAjukanPermintaanRevisi.setOnClickListener {
                when {
                    userData.username?.firstName == null -> alertHelperToEditAccount("Ubah profil terlebih dahulu")
                    tempUserData?.categoryData?.id == 2 && allItemNames.size == 0 -> alertHelperToUploadActivity("Unggah barang terlebih dahulu")
                    tempUserData?.categoryData?.id == 1 -> setupDialog(data?.categoryData?.category)
                    else -> setupDialog(data?.categoryData?.category)
                }
            }
        }


        ivJenisRevisiProduct
        when (data?.typeData?.id) {
            1 -> ivJenisRevisiProduct.loadDrawable(R.drawable.ic_perangkatkomputer)
            2 -> ivJenisRevisiProduct.loadDrawable(R.drawable.ic_handphone)
            3 -> ivJenisRevisiProduct.loadDrawable(R.drawable.ic_kamera)
            4 -> ivJenisRevisiProduct.loadDrawable(R.drawable.ic_aksesoriselektronik)
        }
        when (data?.conditionData?.id) {
            1 -> ivConditionRevisiProduct.loadDrawable(R.drawable.ic_berfungsi)
            2 -> ivConditionRevisiProduct.loadDrawable(R.drawable.ic_rusak)
        }

        tvDetailRevisiProductName.text = data?.name
        tvDeskripsiRevisiProduct.text = data?.description
        tvJenisRevisiProduct.text = data?.typeData?.tipe
        tvDetailRevisiCategory.text = data?.categoryData?.category
        tvKondisiRevisiProduct.text = data?.conditionData?.condition

        this.tempUserData = data
        this.passedData = gson.toJson(data)
    }

    override fun onFailedGetDetailedData(msg: String) {
//        alertHelper(msg)
    }

    override fun initView() {
        initToolbar()
        ivEditProductRevisiItem.setOnClickListener {
            startActivity<UploadActivity> {
                putExtra(passingEditProductKey, passedData)
                finish()
            }
        }

    }

    override fun onGetAllListData(data: List<UserProducts>?) {
        allItem = data as MutableList<UserProducts>
        allItemNames.clear()
        data.forEach { datas ->
            if (datas.categoryData?.id == 2) {
                datas.name?.let { this.allItemNames.add(it) }
            }
        }
    }

    private fun setupDialog(categoryData: String?) {
        if (categoryData?.equals("Donasi", ignoreCase = true)!!) {
            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.permintaan_donasi, null)
            dialogView.etCatatanPermintaanDonasi.setMaxLength(300)
            dialogBuilder.setView(dialogView)
            val alert = dialogBuilder.create()
            alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
            alert.show()
            dialogView.btnKirimPermintaanDonasi.setOnClickListener {
                if (dialogView.etCatatanPermintaanDonasi.text.toString().trim().isNullOrBlank()) {
                    dialogView.etCatatanPermintaanDonasi.requestError(getString(R.string.not_null))
                } else {
                    tempUserData?.id?.let { it1 ->
                        presenter.createDonationDeals(
                                dialogView.etCatatanPermintaanDonasi.text.toString().trim(),
                                it1
                        )
                    }
                    alert.dismiss()
                }
            }
        } else {
            var tmpNameItem: String? = null
            var tmpMyProductId: Int? = null
            val dialogBuilders = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogViews = inflater.inflate(R.layout.permintaan_tukar, null)
            dialogViews.etCatatanPermintaanTukar.setMaxLength(300)
            dialogBuilders.setView(dialogViews)

            if (allItemNames.size > 0) {
                arraySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allItemNames)
                arraySpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dialogViews.spPermintaanTukar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        tmpNameItem = allItemNames[position]
                        allItem.forEach {
                            if (it.name?.equals(tmpNameItem, ignoreCase = true)!!) {
                                tmpMyProductId = it.id
                            }
                        }
                    }

                }
            } else {
                allItemNames.add("Anda tidak memiliki barang")
                arraySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allItemNames)
                arraySpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dialogViews.spPermintaanTukar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    }

                }
            }
            dialogViews.spPermintaanTukar.adapter = arraySpinnerAdapter

            val alert = dialogBuilders.create()
            alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
            alert.show()
            dialogViews.btnKirimPermintaanTukar.setOnClickListener {
                if (dialogViews.etCatatanPermintaanTukar.text.toString().trim().isNullOrBlank()) {
                    dialogViews.etCatatanPermintaanTukar.requestError(getString(R.string.not_null))
                } else {
                    tempUserData?.id?.let { it1 ->
                        tmpMyProductId?.let { it2 ->
                            presenter.createBarterDeals(
                                    dialogViews.etCatatanPermintaanTukar.text.toString().trim(),
                                    it1, it2
                            )
                        }
                    }
                    alert.dismiss()
                }
            }
        }
    }

    private fun initToolbar() {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_blue_24dp)
        upArrow?.setColorFilter(resources.getColor(R.color.colorComment), PorterDuff.Mode.SRC_ATOP)
        DetailRevisiItemToolbar.title = "DETAIL BARANG"
        DetailRevisiItemToolbar.setTitleTextColor(resources.getColor(R.color.colorComment))
        DetailRevisiItemToolbar.setSubtitleTextColor(resources.getColor(R.color.colorComment))
        setSupportActionBar(DetailRevisiItemToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

    }

    private fun showCancelDialog(dealingsID: Int?) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_batal_permintaan, null)
        dialogBuilder.setView(dialogView)
        val alert = dialogBuilder.create()
        alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        dialogView.tvPopupBatalkanTittle.text = "Apakah anda ingin membatalkan pengajuan ?"
        alert.show()
        dialogView.btnIya.setOnClickListener {
            presenter.onEditDealingsData(dealingsID.toString(), 4)
            startActivity<MainActivity> {
                alert.dismiss()
                finish()
            }
        }
        dialogView.btnTidak.setOnClickListener {
            alert.dismiss()
        }
    }
}