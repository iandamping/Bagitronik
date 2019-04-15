package com.binar.bagitronik.ui.activity.detaildonasi

import android.app.Dialog
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.Window
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.helper.Constant.donasiPassedID
import com.binar.bagitronik.model.dealings.*
import com.binar.bagitronik.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_detail_donasi.*
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.pop_up_donasi.view.*
import kotlinx.android.synthetic.main.popup_batal_permintaan.view.*
import org.jetbrains.anko.textColor

/**
 *
Created by Ian Damping on 02/04/2019.tvDonasiNameNya
Github = https://github.com/iandamping
 */
class DetailDonasiActivity : AppCompatActivity(), DetailDonasiView {
    private var passedId: String? = null
    private lateinit var presenter: DetailDonasiPresenter
    private var isDoneDonasi: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_detail_donasi)
        presenter = DetailDonasiPresenter(this)
        presenter.onCreate(this)
        passedId = intent?.getStringExtra(donasiPassedID)
        presenter.onGetDealingsData(passedId)
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onFailedGetDetailedData(msg: String) {
        alertHelper(msg)
    }

//    override fun onGetUserRequestData(imageUrl: String?, userName: String?, userAddres: String?) {
//        tvDonasiNameNya.text = userName
//        tvDonasiProfileCity.text = userAddres
//        if (imageUrl.isNullOrBlank()){
//            ivDonasiProfile?.loadDrawable(R.drawable.profile)
//        } else {
//            ivDonasiProfile?.loadUrl(imageUrl)
//            ivDonasiProfile.setOnClickListener {
//                val alert = Dialog(this, R.style.AppTheme)
//                alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                alert.setContentView(com.binar.bagitronik.R.layout.activity_fullscreen)
//                alert.setCanceledOnTouchOutside(true)
//                alert.fullScreenImageView.loadUrlOnlyForFullScreen(imageUrl)
//                alert.show()
//                alert.ivClose.setOnClickListener {
//                    alert.dismiss()
//                }
//            }
//        }
//    }

    override fun onGetDonasiData(
            data: BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>?,
            status: Boolean?
    ) {
        if (data?.productNya?.imagesData?.url.isNullOrBlank()) {
            ivDetailProductnyaDonasi.loadDrawable(com.binar.bagitronik.R.drawable.empty_image)
        } else {
            ivDetailProductnyaDonasi.loadUrl(data?.productNya?.imagesData?.url)
            ivDetailProductnyaDonasi.setOnClickListener {
                val alert = Dialog(this, R.style.AppTheme)
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                alert.setContentView(com.binar.bagitronik.R.layout.activity_fullscreen)
                alert.setCanceledOnTouchOutside(true)
                alert.fullScreenImageView.loadUrlOnlyForFullScreen(data?.productNya?.imagesData?.url)
                alert.show()
                alert.ivClose.setOnClickListener {
                    alert.dismiss()
                }
            }
        }
        tvDonasiProductNya.text = data?.productNya?.name
        tvDeskripsiProductDonasiNya.text = data?.productNya?.description
//        tvFinishDonasiName.text
        when (data?.productNya?.type?.id) {
            1 -> ivJenisProductDonasi.loadDrawable(R.drawable.ic_perangkatkomputer)
            2 -> ivJenisProductDonasi.loadDrawable(R.drawable.ic_handphone)
            3 -> ivJenisProductDonasi.loadDrawable(R.drawable.ic_kamera)
            4 -> ivJenisProductDonasi.loadDrawable(R.drawable.ic_aksesoriselektronik)
        }

        tvJenisProductDonasiNya.text = data?.productNya?.type?.tipe
        when (data?.productNya?.condition?.id) {
            1 -> ivConditionProductDonasi.loadDrawable(R.drawable.ic_berfungsi)
            2 -> ivConditionProductDonasi.loadDrawable(R.drawable.ic_rusak)
        }

        tvKondisiProductDonasiNya.text = data?.productNya?.condition?.condition
        tvDonasiNoteNya.text = data?.note



        if (data?.user?.images?.url.isNullOrBlank()){
            ivDonasiProfile.loadDrawable(com.binar.bagitronik.R.drawable.empty_image)
        } else{
            ivDonasiProfile.loadUrl(data?.user?.images?.url)
            ivDonasiProfile.setOnClickListener {
                val alert = Dialog(this, R.style.AppTheme)
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                alert.setContentView(com.binar.bagitronik.R.layout.activity_fullscreen)
                alert.setCanceledOnTouchOutside(true)
                alert.fullScreenImageView.loadUrlOnlyForFullScreen(data?.user?.images?.url)
                alert.show()
                alert.ivClose.setOnClickListener {
                    alert.dismiss()
                }
            }
        }
        tvDonasiProfileCity.text = data?.user?.address
        tvDonasiNameNya.text = data?.user?.firstName
        tvDonasiProfileCity.text = data?.user?.cityId?.city

        if (status!!) {
            lnTerimaDonasiAtauTolak.visible()
            lnDonasiOwnerProductKu.visible()
            tvFinishDeniedNote.text = data?.productNya?.description
            btnTerimaDonasiProduKu.setOnClickListener {
                presenter.onEditDealingsData(passedId.toString(), 2)
            }
            btnTolakDonasiProduKu.setOnClickListener {
                presenter.onEditDealingsData(passedId.toString(), 3)
            }
        } else {
            lnFinishDonasi.visible()
            btnBatalkanPermintaanDonasiNya.visible()
        }


        when (data?.productNya?.dealing?.status_satu) {
            1 -> {
                if (!status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNew.visible()
                    btnBatalkanPermintaanDonasiNew.text = "Batalkan Permintaan"
                    btnBatalkanPermintaanDonasiNew.textColor = resources.getColor(R.color.colorComment)
                    btnBatalkanPermintaanDonasiNew.background = resources.getDrawable(android.R.color.transparent)
                    btnBatalkanPermintaanDonasiNew.setOnClickListener {
                        setupDialogSatu(true)
//                        presenter.onEditDealingsDataSatu(passedId.toString(), 7, 3)
                    }

                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                    btnBatalkanPermintaanDonasiNya.setOnClickListener {
                        setupDialogSatu(false)
                    }
                }


                if (status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNew.visible()
                    btnBatalkanPermintaanDonasiNew.text = "Batalkan Permintaan"
                    btnBatalkanPermintaanDonasiNew.textColor = resources.getColor(R.color.colorComment)
                    btnBatalkanPermintaanDonasiNew.background = resources.getDrawable(android.R.color.transparent)
                    btnBatalkanPermintaanDonasiNew.setOnClickListener {
                        //                        presenter.onEditDealingsDataDua(passedId.toString(), 7, 3)
                        setupDialogDua(true)
                    }


                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                    btnBatalkanPermintaanDonasiNya.setOnClickListener {
                        setupDialogDua(false)
                    }
                }
            }

            2 -> {
                if (!status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNya?.isClickable = false
                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                }
            }

            3 -> {
                if (!status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNew.gone()
                    btnBatalkanPermintaanDonasiNya?.isClickable = false
                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                }
                if (status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNew.gone()
                    btnBatalkanPermintaanDonasiNya?.isClickable = false
                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                }
            }
        }
        when (data?.productNya?.dealing?.status_dua) {
            1 -> {
                if (status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNew.visible()
                    btnBatalkanPermintaanDonasiNew.text = "Batalkan Permintaan"
                    btnBatalkanPermintaanDonasiNew.textColor = resources.getColor(R.color.colorComment)
                    btnBatalkanPermintaanDonasiNew.background = resources.getDrawable(android.R.color.transparent)
                    btnBatalkanPermintaanDonasiNew.setOnClickListener {
                        presenter.onEditDealingsDataDua(passedId.toString(), 7, 3)
                        setupDialogDua(true)
                    }

                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                    btnBatalkanPermintaanDonasiNya.setOnClickListener {
                        setupDialogDua(false)
                    }
                }

                if (!status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNew.visible()
                    btnBatalkanPermintaanDonasiNew.text = "Batalkan Permintaan"
                    btnBatalkanPermintaanDonasiNew.textColor = resources.getColor(R.color.colorComment)
                    btnBatalkanPermintaanDonasiNew.background = resources.getDrawable(android.R.color.transparent)
                    btnBatalkanPermintaanDonasiNew.setOnClickListener {
                        setupDialogSatu(true)
//                        presenter.onEditDealingsDataSatu(passedId.toString(), 7, 3)
                    }

                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                    btnBatalkanPermintaanDonasiNya.setOnClickListener {
                        setupDialogSatu(false)
                    }
                }

            }

            2 -> {
                if (status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    btnBatalkanPermintaanDonasiNew.gone()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNya?.isClickable = false
                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background =
                            resources.getDrawable(R.drawable.forbuttonloginregister)
                }
            }

            3 -> {
                if (status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNew.gone()
                    btnBatalkanPermintaanDonasiNya?.isClickable = false
                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                }
                if (!status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNew.gone()
                    btnBatalkanPermintaanDonasiNya?.isClickable = false
                    btnBatalkanPermintaanDonasiNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                }
            }
        }


        if (!isDoneDonasi) {
            if (data?.productNya?.dealing?.status_dua == 2 && data.productNya.dealing.status_satu == 2) {
                presenter.onEditDealingsData(passedId.toString(), 5)
                isDoneDonasi = true
            }

            if (data?.productNya?.dealing?.status_dua == 3 && data.productNya.dealing.status_satu == 2) {
                presenter.onEditDealingsData(passedId.toString(), 6)
                isDoneDonasi = true
            } else if (data?.productNya?.dealing?.status_dua == 2 && data.productNya.dealing.status_satu == 3) {
                presenter.onEditDealingsData(passedId.toString(), 6)
                isDoneDonasi = true
            } else if (data?.productNya?.dealing?.status_dua == 3 && data.productNya.dealing.status_satu == 3) {
                presenter.onEditDealingsData(passedId.toString(), 6)
                isDoneDonasi = true
            }
        }

        when (data?.status?.id) {
            1 -> {
                btnBatalkanPermintaanDonasiNya.setOnClickListener {
                    showCancelDialog()
                }
            }
            2 -> {
                presenter.onEditDealingsDataSatu(passedId.toString(), 7, 1)
                presenter.onEditDealingsDataDua(passedId.toString(), 7, 1)

            }
            3 -> {
                lnDonasiOwnerProductKu.gone()
                btnBatalkanPermintaanDonasiNya.visible()
                btnBatalkanPermintaanDonasiNya.text = "Ditolak"
                btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.dc_gray)
                btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(android.R.color.transparent)
                btnBatalkanPermintaanDonasiNya.isClickable = false
            }
            4 -> {
                lnDonasiOwnerProductKu.gone()
                btnBatalkanPermintaanDonasiNya.visible()
                btnBatalkanPermintaanDonasiNya.text = "Ajukan Permintaan"
                btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.white)
                btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                btnBatalkanPermintaanDonasiNya.setOnClickListener {
                    presenter.onEditDealingsData(passedId.toString(), 1)
                    startActivity<DetailDonasiActivity> {
                        putExtra(Constant.donasiPassedID, passedId)
                        finish()
                    }

                }
            }
            5 -> {
                lnDonasiOwnerProductKu.gone()
                lnFinishDonasi.visible()
                tvFinishDonasiName.visible()
                tvFinishDonasiPhoneNumber.visible()
                tvFinishDonasiName.text = data?.productNya?.users?.firstName
                tvFinishDonasiPhoneNumber.text = data?.user?.phone
                btnBatalkanPermintaanDonasiNya.visible()
                btnBatalkanPermintaanDonasiNew.gone()
                btnBatalkanPermintaanDonasiNya?.isClickable = false
                btnBatalkanPermintaanDonasiNya.text = "Selesai"
                btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.dc_gray)
                btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(android.R.color.transparent)
            }

            6 -> {
                if (!status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNya?.isClickable = false
                    btnBatalkanPermintaanDonasiNya.text = "Tidak Selesai"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.dc_gray)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(android.R.color.transparent)
                } else if (status) {
                    lnDonasiOwnerProductKu.gone()
                    lnFinishDonasi.visible()
                    tvFinishDonasiName.visible()
                    tvFinishDonasiPhoneNumber.visible()
                    tvFinishDonasiName.text =
                            data?.productNya?.users?.firstName
                    tvFinishDonasiPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanDonasiNya.visible()
                    btnBatalkanPermintaanDonasiNya?.isClickable = false
                    btnBatalkanPermintaanDonasiNya.text = "Tidak Selesai"
                    btnBatalkanPermintaanDonasiNya.textColor = resources.getColor(R.color.dc_gray)
                    btnBatalkanPermintaanDonasiNya.background = resources.getDrawable(android.R.color.transparent)
                }
            }
        }
    }

    private fun showCancelDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_batal_permintaan, null)
        dialogBuilder.setView(dialogView)
        val alert = dialogBuilder.create()
        alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        dialogView.tvPopupBatalkanTittle.text = "Apakah anda ingin membatalkan pengajuan ?"
        alert.show()
        dialogView.btnIya.setOnClickListener {
            presenter.onEditDealingsData(passedId, 4)
            startActivity<MainActivity> {
                alert.dismiss()
                finish()
            }
        }
        dialogView.btnTidak.setOnClickListener {
            alert.dismiss()
        }
    }

    override fun initView() {
        setupToolbar()
    }

    private fun setupToolbar() {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_back)
        upArrow?.setColorFilter(resources.getColor(R.color.colorComment), PorterDuff.Mode.SRC_ATOP)
        detailFixedToolbarDonasi.setTitleTextColor(resources.getColor(R.color.black))
        detailFixedToolbarDonasi.setSubtitleTextColor(resources.getColor(R.color.black))
        detailFixedToolbarDonasi.title = "Penawaran Baru"
        setSupportActionBar(detailFixedToolbarDonasi)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

    private fun setupDialogSatu(isCancel: Boolean) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.pop_up_donasi, null)
        if (isCancel) {
            dialogView?.tvDialogDonasi?.text = "Apakah kamu yakin akan membatalkan permintaan donasi ini?"
        }
        dialogBuilder.setView(dialogView)
        val alert = dialogBuilder.create()
        alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        alert.show()
        if (isCancel) {
            dialogView.btnDialogFinishDonasi.setOnClickListener {
                presenter.onEditDealingsDataSatu(passedId.toString(), 7, 3)
                alert.dismiss()
            }
        } else {
            dialogView.btnDialogFinishDonasi.setOnClickListener {
                presenter.onEditDealingsDataSatu(passedId.toString(), 7, 2)
                alert.dismiss()
            }
        }
        dialogView.btnDialogNotFinishDonasi.setOnClickListener {
            myToast("Gagal")
            alert.dismiss()

        }
    }

    private fun setupDialogDua(isCancel: Boolean) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.pop_up_donasi, null)
        if (isCancel) {
            dialogView?.tvDialogDonasi?.text = "Apakah kamu yakin akan membatalkan permintaan donasi ini?"
        }
        dialogBuilder.setView(dialogView)
        val alert = dialogBuilder.create()
        alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        alert.show()
        if (isCancel) {
            dialogView.btnDialogFinishDonasi.setOnClickListener {
                presenter.onEditDealingsDataDua(passedId.toString(), 7, 3)
                alert.dismiss()
            }
        } else {
            dialogView.btnDialogFinishDonasi.setOnClickListener {
                presenter.onEditDealingsDataDua(passedId.toString(), 7, 2)
                alert.dismiss()
            }
        }
        dialogView.btnDialogNotFinishDonasi.setOnClickListener {
            myToast("Gagal")
            alert.dismiss()

        }
    }
}