package com.binar.bagitronik.ui.activity.detailbarter

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
import com.binar.bagitronik.model.dealings.*
import com.binar.bagitronik.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_detail_barter.*
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.pop_up_question_tukar.view.*
import kotlinx.android.synthetic.main.pop_up_tukar.view.*
import kotlinx.android.synthetic.main.popup_batal_permintaan.view.*
import org.jetbrains.anko.textColor

/**
 *
Created by Ian Damping on 02/04/2019.
Github = https://github.com/iandamping
 */
class DetailBarterActivity : AppCompatActivity(), DetailBarterView {
    private lateinit var presenter: DetailBarterPresenter
    private var passedId: Int? = null
    private var isDoneTukar: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_detail_barter)
        presenter = DetailBarterPresenter(this)
        presenter.onCreate(this)
        passedId = intent?.getIntExtra(Constant.tukarPassedID, 0)
        presenter.onGetDealingsData(passedId.toString())
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

    override fun onGetTukarData(
            data: BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>?,
            status: Boolean?
    ) {
        if (data?.productKu?.imagesData?.url.isNullOrBlank()) {
            ivDetailProductnyaBarter.loadDrawable(com.binar.bagitronik.R.drawable.empty_image)
        } else {
            ivDetailProductnyaBarter.loadUrl(data?.productKu?.imagesData?.url)
            ivDetailProductnyaBarter.setOnClickListener {
                val alert = Dialog(this, R.style.AppTheme)
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                alert.setContentView(com.binar.bagitronik.R.layout.activity_fullscreen)
                alert.setCanceledOnTouchOutside(true)
                alert.fullScreenImageView.loadUrlOnlyForFullScreen(data?.productKu?.imagesData?.url)
                alert.show()
                alert.ivClose.setOnClickListener {
                    alert.dismiss()
                }
            }
        }
        tvBarterProductNya.text = data?.productKu?.name
        tvDeskripsiProductBarterNya.text = data?.productKu?.description
        when (data?.productKu?.type?.id) {
            1 -> ivJenisProductBarter.loadDrawable(R.drawable.ic_perangkatkomputer)
            2 -> ivJenisProductBarter.loadDrawable(R.drawable.ic_handphone)
            3 -> ivJenisProductBarter.loadDrawable(R.drawable.ic_kamera)
            4 -> ivJenisProductBarter.loadDrawable(R.drawable.ic_aksesoriselektronik)
        }

        tvJenisProductBarterNya.text = data?.productKu?.type?.tipe
        when (data?.productKu?.condition?.id) {
            1 -> ivConditionProductBarter.loadDrawable(R.drawable.ic_berfungsi)
            2 -> ivConditionProductBarter.loadDrawable(R.drawable.ic_rusak)
        }

        tvKondisiProductBarterNya.text = data?.productKu?.condition?.condition
        tvBarterNoteNya.text = data?.note


        tvBarterMyProduct.text = data?.productNya?.name
        tvJenisBarterMyProduct.text = data?.productNya?.name
        tvKondisiBarterMyProduct.text = data?.productNya?.condition?.condition
        when (data?.productNya?.type?.id) {
            1 -> ivBarterCategoryMyProduct.loadDrawable(R.drawable.ic_perangkatkomputer)
            2 -> ivBarterCategoryMyProduct.loadDrawable(R.drawable.ic_handphone)
            3 -> ivBarterCategoryMyProduct.loadDrawable(R.drawable.ic_kamera)
            4 -> ivBarterCategoryMyProduct.loadDrawable(R.drawable.ic_aksesoriselektronik)
        }
        when (data?.productNya?.condition?.id) {
            1 -> ivKondisiBarterMyProduct.loadDrawable(R.drawable.ic_berfungsi)
            2 -> ivKondisiBarterMyProduct.loadDrawable(R.drawable.ic_rusak)
        }


        ivBarterProfile

        if (data?.user?.images?.url.isNullOrBlank()) {
            ivBarterProfile.loadDrawable(com.binar.bagitronik.R.drawable.empty_image)
        } else {
            ivBarterProfile.loadUrl(data?.user?.images?.url)
            ivBarterProfile.setOnClickListener {
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
        tvBarterNameNya.text = data?.user?.firstName + " " + data?.user?.lastName
        tvBarterProfileCity.text = data?.user?.cityId?.city

        if (status!!) {
            lnTerimaAtauTolak.visible()
            btnTerimaBarterProduKu.setOnClickListener {
                setupDialogQuestion(data, false)
//                presenter.onEditDealingsData(passedId.toString(), 2)
            }
            btnTolakBarterProduKu.setOnClickListener {
                setupDialogQuestion(data, true)
//                presenter.onEditDealingsData(passedId.toString(), 3)
            }
        } else {
            lnFinishBarter.visible()
            btnBatalkanPermintaanBarterNya.visible()
        }

        when (data?.productNya?.dealing?.status_satu) {
            1 -> {
                if (!status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNew.visible()
                    btnBatalkanPermintaanBarterNew.text = "Batalkan Permintaan"
                    btnBatalkanPermintaanBarterNew.textColor = resources.getColor(R.color.colorComment)
                    btnBatalkanPermintaanBarterNew.background = resources.getDrawable(android.R.color.transparent)
                    btnBatalkanPermintaanBarterNew.setOnClickListener {
                        setupDialogSatu(true)
//                        presenter.onEditDealingsDataSatu(passedId.toString(), 7, 3)
                    }

                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                    btnBatalkanPermintaanBarterNya.setOnClickListener {
                        setupDialogSatu(false)
                    }
                }


                if (status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNew.visible()
                    btnBatalkanPermintaanBarterNew.text = "Batalkan Permintaan"
                    btnBatalkanPermintaanBarterNew.textColor = resources.getColor(R.color.colorComment)
                    btnBatalkanPermintaanBarterNew.background = resources.getDrawable(android.R.color.transparent)
                    btnBatalkanPermintaanBarterNew.setOnClickListener {
                        //                        presenter.onEditDealingsDataDua(passedId.toString(), 7, 3)
                        setupDialogDua(true)
                    }


                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                    btnBatalkanPermintaanBarterNya.setOnClickListener {
                        setupDialogDua(false)
                    }
                }
            }

            2 -> {
                if (!status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNya?.isClickable = false
                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                }
            }

            3 -> {
                if (!status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNew.gone()
                    btnBatalkanPermintaanBarterNya?.isClickable = false
                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                }

//                if (status) {
//                    lnBarterOwnerProductKu.gone()
//                    lnFinishBarter.visible()
//                    tvFinishBarterName.visible()
//                    tvFinishBarterPhoneNumber.visible()
//                    tvFinishBarterName.text =
//                        data?.productNya?.users?.firstName
//                    tvFinishBarterPhoneNumber.text = data?.user?.phone
//                    btnBatalkanPermintaanBarterNya.visible()
//                    btnBatalkanPermintaanBarterNew.gone()
//                    btnBatalkanPermintaanBarterNya?.isClickable = false
//                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
//                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
//                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
//                }
            }
        }
        when (data?.productNya?.dealing?.status_dua) {
            1 -> {
                if (status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNew.visible()
                    btnBatalkanPermintaanBarterNew.text = "Batalkan Permintaan"
                    btnBatalkanPermintaanBarterNew.textColor = resources.getColor(R.color.colorComment)
                    btnBatalkanPermintaanBarterNew.background = resources.getDrawable(android.R.color.transparent)
                    btnBatalkanPermintaanBarterNew.setOnClickListener {
                        presenter.onEditDealingsDataDua(passedId.toString(), 7, 3)
                        setupDialogDua(true)
                    }

                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                    btnBatalkanPermintaanBarterNya.setOnClickListener {
                        setupDialogDua(false)
                    }
                }

                if (!status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNew.visible()
                    btnBatalkanPermintaanBarterNew.text = "Batalkan Permintaan"
                    btnBatalkanPermintaanBarterNew.textColor = resources.getColor(R.color.colorComment)
                    btnBatalkanPermintaanBarterNew.background = resources.getDrawable(android.R.color.transparent)
                    btnBatalkanPermintaanBarterNew.setOnClickListener {
                        setupDialogSatu(true)
//                        presenter.onEditDealingsDataSatu(passedId.toString(), 7, 3)
                    }

                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                    btnBatalkanPermintaanBarterNya.setOnClickListener {
                        setupDialogSatu(false)
                    }
                }

            }

            2 -> {
                if (status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    btnBatalkanPermintaanBarterNew.gone()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNya?.isClickable = false
                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanBarterNya.background =
                            resources.getDrawable(R.drawable.forbuttonloginregister)
                }
            }

            3 -> {
                if (status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNew.gone()
                    btnBatalkanPermintaanBarterNya?.isClickable = false
                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                }

//                if (!status) {
//                    lnBarterOwnerProductKu.gone()
//                    lnFinishBarter.visible()
//                    tvFinishBarterName.visible()
//                    tvFinishBarterPhoneNumber.visible()
//                    tvFinishBarterName.text =
//                        data?.productNya?.users?.firstName
//                    tvFinishBarterPhoneNumber.text = data?.user?.phone
//                    btnBatalkanPermintaanBarterNya.visible()
//                    btnBatalkanPermintaanBarterNew.gone()
//                    btnBatalkanPermintaanBarterNya?.isClickable = false
//                    btnBatalkanPermintaanBarterNya.text = "Proses Penyelesaian"
//                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
//                    btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
//                }
            }
        }


        if (!isDoneTukar) {
            if (data?.productNya?.dealing?.status_dua == 2 && data.productNya.dealing.status_satu == 2) {
                presenter.onEditDealingsData(passedId.toString(), 5)
                isDoneTukar = true
            }

            if (data?.productNya?.dealing?.status_dua == 3 && data.productNya.dealing.status_satu == 2) {
                presenter.onEditDealingsData(passedId.toString(), 6)
                isDoneTukar = true
            } else if (data?.productNya?.dealing?.status_dua == 2 && data.productNya.dealing.status_satu == 3) {
                presenter.onEditDealingsData(passedId.toString(), 6)
                isDoneTukar = true
            } else if (data?.productNya?.dealing?.status_dua == 3 && data.productNya.dealing.status_satu == 3) {
                presenter.onEditDealingsData(passedId.toString(), 6)
                isDoneTukar = true
            }
        }

        when (data?.status?.id) {
            1 -> {
                btnBatalkanPermintaanBarterNya.setOnClickListener {
                    showCancelDialog()
                }
            }
            2 -> {
                presenter.onEditDealingsDataSatu(passedId.toString(), 7, 1)
                presenter.onEditDealingsDataDua(passedId.toString(), 7, 1)
            }
            3 -> {
                lnBarterOwnerProductKu.gone()
                btnBatalkanPermintaanBarterNya.visible()
                btnBatalkanPermintaanBarterNya.text = "Ditolak"
                btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.dc_gray)
                btnBatalkanPermintaanBarterNew.background = resources.getDrawable(android.R.color.transparent)
                btnBatalkanPermintaanBarterNya.isClickable = false
            }
            4 -> {
                lnBarterOwnerProductKu.gone()
                btnBatalkanPermintaanBarterNya.visible()
                btnBatalkanPermintaanBarterNya.text = "Ajukan Permintaan"
                btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.white)
                btnBatalkanPermintaanBarterNya.background = resources.getDrawable(R.drawable.forbuttonloginregister)
                btnBatalkanPermintaanBarterNya.setOnClickListener {
                    presenter.onEditDealingsData(passedId.toString(), 1)
                    startActivity<DetailBarterActivity> {
                        putExtra(Constant.donasiPassedID, passedId)
                        finish()
                    }

                }
            }
            5 -> {
                lnBarterOwnerProductKu.gone()
                lnFinishBarter.visible()
                tvFinishBarterName.visible()
                tvFinishBarterPhoneNumber.visible()
                tvFinishBarterName.text = data?.productNya?.users?.firstName
                tvFinishBarterPhoneNumber.text = data?.user?.phone
                btnBatalkanPermintaanBarterNya.visible()
                btnBatalkanPermintaanBarterNew.gone()
                btnBatalkanPermintaanBarterNya?.isClickable = false
                btnBatalkanPermintaanBarterNya.text = "Selesai"
                btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.dc_gray)
                btnBatalkanPermintaanBarterNew.background = resources.getDrawable(android.R.color.transparent)
            }
            6 -> {
                if (!status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNya?.isClickable = false
                    btnBatalkanPermintaanBarterNya.text = "Tidak Selesai"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.dc_gray)
                    btnBatalkanPermintaanBarterNew.background = resources.getDrawable(android.R.color.transparent)
                } else if (status) {
                    lnBarterOwnerProductKu.gone()
                    lnFinishBarter.visible()
                    tvFinishBarterName.visible()
                    tvFinishBarterPhoneNumber.visible()
                    tvFinishBarterName.text =
                            data?.productNya?.users?.firstName
                    tvFinishBarterPhoneNumber.text = data?.user?.phone
                    btnBatalkanPermintaanBarterNya.visible()
                    btnBatalkanPermintaanBarterNya?.isClickable = false
                    btnBatalkanPermintaanBarterNya.text = "Tidak Selesai"
                    btnBatalkanPermintaanBarterNya.textColor = resources.getColor(R.color.dc_gray)
                    btnBatalkanPermintaanBarterNew.background = resources.getDrawable(android.R.color.transparent)
                }
            }
        }
    }

    private fun setupDialogQuestion(data: BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>?, isCancel: Boolean) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.pop_up_question_tukar, null)
        if (!isCancel) {
            dialogView.tvDialogBarterQuestion.text = "Apakah kamu yakin akan menerima penawaran dari ${data?.productKu?.users?.firstName + " " + data?.productKu?.users?.lastName}"
        } else {
            dialogView.tvDialogBarterQuestion.text = "Apakah kamu yakin akan membatalkan permintaan penukaran ini?"
        }
        dialogBuilder.setView(dialogView)
        val alert = dialogBuilder.create()
        alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        alert.show()
        if (!isCancel) {
            dialogView.btnDialogFinishBarterQuestion.setOnClickListener {
                presenter.onEditDealingsData(passedId.toString(), 2)
                alert.dismiss()
            }
        } else if (isCancel) {
            dialogView.btnDialogFinishBarterQuestion.setOnClickListener {
                presenter.onEditDealingsData(passedId.toString(), 3)
                alert.dismiss()
            }

            dialogView.btnDialogNotFinishBarterQuestion.setOnClickListener {
                alert.dismiss()

            }
        }
    }

    private fun setupDialogSatu(isCancel: Boolean) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.pop_up_tukar, null)
        if (isCancel) {
            dialogView?.tvDialogBarter?.text = "Apakah kamu yakin akan membatalkan permintaan tukar ini?"
        }
        dialogBuilder.setView(dialogView)
        val alert = dialogBuilder.create()
        alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        alert.show()
        if (isCancel) {
            dialogView.btnDialogFinishBarter.setOnClickListener {
                presenter.onEditDealingsDataSatu(passedId.toString(), 7, 3)
                alert.dismiss()
            }
        } else {
            dialogView.btnDialogFinishBarter.setOnClickListener {
                presenter.onEditDealingsDataSatu(passedId.toString(), 7, 2)
                alert.dismiss()
            }
        }
        dialogView.btnDialogNotFinishBarter.setOnClickListener {
            alert.dismiss()

        }
    }

    private fun setupDialogDua(isCancel: Boolean) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.pop_up_tukar, null)
        if (isCancel) {
            dialogView?.tvDialogBarter?.text = "Apakah kamu yakin akan membatalkan permintaan tukar ini?"
        }
        dialogBuilder.setView(dialogView)
        val alert = dialogBuilder.create()
        alert?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        alert.show()
        if (isCancel) {
            dialogView.btnDialogFinishBarter.setOnClickListener {
                presenter.onEditDealingsDataDua(passedId.toString(), 7, 3)
                alert.dismiss()
            }
        } else {
            dialogView.btnDialogFinishBarter.setOnClickListener {
                presenter.onEditDealingsDataDua(passedId.toString(), 7, 2)
                alert.dismiss()
            }
        }
        dialogView.btnDialogNotFinishBarter.setOnClickListener {
            alert.dismiss()

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
            presenter.onEditDealingsData(passedId.toString(), 4)
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
        detailFixedToolbarBarter.setTitleTextColor(resources.getColor(R.color.black))
        detailFixedToolbarBarter.setSubtitleTextColor(resources.getColor(R.color.black))
        detailFixedToolbarBarter.title = "Penawaran Baru"
        setSupportActionBar(detailFixedToolbarBarter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

}