package com.binar.bagitronik.ui.activity.uploadpage

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.helper.Constant.RequestOpenCamera
import com.binar.bagitronik.helper.Constant.RequestSelectGalleryImage
import com.binar.bagitronik.helper.Constant.maxHeight
import com.binar.bagitronik.helper.Constant.maxWidth
import com.binar.bagitronik.helper.Constant.passingEditProductKey
import com.binar.bagitronik.helper.Constant.saveCaptureImagePath
import com.binar.bagitronik.helper.Constant.switchBackToMain
import com.binar.bagitronik.helper.ImageHelper.Companion.bitmapToFile
import com.binar.bagitronik.model.product.ProductType
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.activity.MainActivity
import com.binar.bagitronik.ui.activity.detail.DetailProductActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.activity_upload_page.*
import java.io.File

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class UploadActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, UploadView {

    private var imageDecodes: File? = null
    private var intents: Intent = Intent()
    private var isPermissionGranted = false
    private var allCategory = mutableListOf<String>()
    private var selectedType: String? = null
    private var selectedCategory: String? = null
    private var selectedCondition: String? = null
    private var arraySpinnerAdapter: ArrayAdapter<String>? = null
    private lateinit var presenter: UploadPresenter
    private var isOnEditState = false
    private var isImageisSame = false
    private var productId: Int? = null
    private var itemType: String? = null
    private var passedData: UserProducts? = null
    private var isImageIsReady: Boolean? = false
    private val userData = BagitronikApp.gson.fromJson(
            BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey),
            UserDataLogin::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_upload_page)
        userChecker()
    }

    private fun userChecker() {
        if (userData != null) {
            if (userData.username?.firstName == null) {
                alertHelperToEditAccount("Silakan ubah profil terlebih dahulu")
            } else if (userData.token != null) {
                getAllPermission()
                presenter = UploadPresenter(this, this)
                presenter.onCreate(this)
                onNewIntent(intent)
            }
        } else {
            alertHelperToLogin("Silakan Masuk terlebih dahulu")
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.getPassedEditProductData(intent?.getStringExtra(passingEditProductKey))
        this.passedData =
                BagitronikApp.gson.fromJson(intent?.getStringExtra(passingEditProductKey), UserProducts::class.java)
        if (passedData != null) {
            itemType = passedData?.typeData?.tipe
            lnTerimaAtauTolak.visible()
        } else if (passedData == null) {
            btnUnggah.visible()
        }
    }

    override fun onSuccessUpload(userId: Int?) {
        startActivity<DetailProductActivity> {
            putExtra(Constant.passingDetailIdKey, userId)
            finish()
        }
    }

    override fun onFailedUpload(msg: String) {
        logD(msg)
    }

    override fun onSuccessDeleteProduct(msg: String) {
        successAlertHelper(msg)
    }

    override fun onFailedDeleteProduct(msg: String) {
        alertHelperToMainActivity(msg)
    }


    override fun initView() {
        initToolbar()
        etNamaBarang.setMaxLength(20)
        etDesBarang.setMaxLength(300)
//        if (passedData==null){
//            lnTerimaAtauTolak.visible()
//        } else if (passedData!=null){
//            btnUnggah.visible()
//        }

        btnSaveEditProduct.setOnClickListener {
            uploadImage(isOnEditState)
        }
        btnDeleteEditProduct.setOnClickListener {
            presenter.deleteProduct(passedData?.id)
        }
        btnUnggah.setOnClickListener {
            uploadImage(isOnEditState)
        }
        btnUnggahFoto.setOnClickListener {
            openGalleryAndCamera(isPermissionGranted)
        }
    }

    private fun initToolbar() {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_blue_24dp)
        upArrow?.setColorFilter(resources.getColor(R.color.colorComment), PorterDuff.Mode.SRC_ATOP)
        toolbarUpload.title = "Unggah Barang"
        toolbarUpload.setTitleTextColor(resources.getColor(R.color.colorComment))
        toolbarUpload.setSubtitleTextColor(resources.getColor(R.color.colorComment))
        setSupportActionBar(toolbarUpload)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

    }

    private fun initRadioButton() {
        if (rbTukar.isChecked) {
            selectedCategory = "2"
        }
        if (rbDonasi.isChecked) {
            selectedCategory = "1"
        }

        if (rbBerfungsi.isChecked) {
            selectedCondition = "1"
        }

        if (rbRusak.isChecked) {
            selectedCondition = "2"
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestSelectGalleryImage) {
                val paths = data?.data
                val bitmap = ImageHelper.getBitmapFromGallery(this, paths)
                btnUnggahFoto.gone()
                tvInfoUpload.gone()
                ivPickPhoto.visible()
                if (bitmap != null) {
                    ivPickPhoto.setImageBitmap(bitmap)
                    ivPickPhoto.setOnClickListener {
                        val alert = Dialog(this, R.style.AppTheme)
                        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        alert.setContentView(R.layout.activity_fullscreen)
                        alert.setCanceledOnTouchOutside(true)
                        alert.fullScreenImageView.setImageBitmap(bitmap)
                        alert.show()
                        alert.ivClose.setOnClickListener {
                            alert.dismiss()
                        }
                    }
                }
                isImageIsReady = true
                imageDecodes = bitmapToFile(this, bitmap)
                isImageisSame = true
            } else if (requestCode == RequestOpenCamera) {
                val bitmap = ImageHelper.decodeSampledBitmapFromFile(
                        createImageFileFromPhoto(), reqWidth = maxWidth, reqHeight = maxHeight
                )
                btnUnggahFoto.gone()
                tvInfoUpload.gone()
                ivPickPhoto.visible()
                if (bitmap != null) {
                    ivPickPhoto.setImageBitmap(bitmap)
                    ivPickPhoto.setOnClickListener {
                        val alert = Dialog(this, R.style.AppTheme)
                        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        alert.setContentView(R.layout.activity_fullscreen)
                        alert.setCanceledOnTouchOutside(true)
                        alert.fullScreenImageView.setImageBitmap(bitmap)
                        alert.show()
                        alert.ivClose.setOnClickListener {
                            alert.dismiss()
                        }
                    }
                }
                isImageIsReady = true
                imageDecodes = bitmapToFile(this, bitmap)
                isImageisSame = true
            }
        }
    }

    private fun openGalleryAndCamera(status: Boolean?) {
        val options = arrayOf("Buka Galeri", "Gunakan Kamera")
        AlertDialog.Builder(this)
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> openImageFromGallery(status)
                        1 -> openImageFromCamera(status)
                    }
                    dialog.dismiss()
                }
                .show()
    }

    private fun openImageDialogFromSnackbar(views: View) {
        val snackbar = Snackbar
                .make(views, "PILIH GAMBAR DULU", Snackbar.LENGTH_LONG)
                .setAction("BUKA") {
                    openGalleryAndCamera(isPermissionGranted)
                }
        snackbar.show()
    }

    private fun uploadImage(editStatus: Boolean?) {
        initRadioButton()
        val itemName = etNamaBarang.text.toString().trim()
        val itemDesc = etDesBarang.text.toString().trim()
        var tempFile: File? = null
        when {
            itemName.isNullOrBlank() -> etNamaBarang.requestError(resources.getString(R.string.item_name_checker))
            itemDesc.isNullOrBlank() -> etDesBarang.requestError(resources.getString(R.string.item_description_checker))
            selectedCondition == null -> myToast("Kondisi tidak boleh kosong")
            selectedCategory == null -> myToast("Kategori tidak boleh kosong")
            isImageIsReady == false -> openImageDialogFromSnackbar(rootUploadPage)
            else -> {
                imageDecodes?.let {
                    tempFile = it
                    presenter.uploadData(itemName, itemDesc, it, selectedType, selectedCondition, selectedCategory)
                }
                if (editStatus!!) {
                    presenter.onEditProductData(
                            productId,
                            itemName,
                            itemDesc,
                            tempFile,
                            selectedType,
                            selectedCondition,
                            selectedCategory
                    )
                    if (isImageisSame) {
                        presenter.onEditProductData(
                                productId,
                                itemName,
                                itemDesc,
                                null,
                                selectedType,
                                selectedCondition,
                                selectedCategory
                        )
                    }
                }
            }
        }


    }


    private fun openImageFromGallery(status: Boolean?) {
        if (status != null) {
            if (status) {
                intents = Intent(Intent.ACTION_PICK)
                intents.type = "image/*"
                startActivityForResult(intents, RequestSelectGalleryImage)
            } else {
                alertHelper(resources.getString(R.string.permission_not_granted))
            }
        }
    }

    private fun openImageFromCamera(status: Boolean?) {
        if (status != null) {
            if (status) {
                val pictureUri: Uri = FileProvider.getUriForFile(
                        this,
                        resources.getString(R.string.package_name),
                        createImageFileFromPhoto()
                )
                intents = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intents.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
                intents.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                startActivityForResult(intents, RequestOpenCamera)
            }
        }
    }

    private fun nonVoidCustomMediaScannerConnection(ctx: Context?, paths: String?): File {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val passingFile = File(directory, paths)
        MediaScannerConnection.scanFile(ctx, arrayOf(passingFile.toString()), null) { path, uri ->
            Log.i("ExternalStorage", "Scanned $path:")
            Log.i("ExternalStorage", "-> uri=$uri")
        }
        return passingFile
    }

    private fun createImageFileFromPhoto(): File {
        return nonVoidCustomMediaScannerConnection(this, saveCaptureImagePath)
    }

    private fun getAllPermission() {
        Dexter.withActivity(this@UploadActivity).withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report?.areAllPermissionsGranted()!!) {
                    isPermissionGranted = report.areAllPermissionsGranted()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
            ) {
            }

        }).check()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                startActivity<MainActivity> {
                    putExtra(switchBackToMain, "1")
//                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity> {
            putExtra(switchBackToMain, "1")
//            finish()
        }
    }

    fun onRadioButtonClicked(view: View) {
        var checked = view as RadioButton
        if (rbDonasi == checked) {
            message(rbDonasi.text.toString() + if (rbDonasi.isChecked) " Checked " else " UnChecked ")
        }
        if (rbBerfungsi == checked) {
            message(rbBerfungsi.text.toString() + if (rbBerfungsi.isChecked) " Checked " else " UnChecked ")
        }
    }

    override fun onGetDataType(data: List<ProductType>?) {
        data?.let {
            it.forEach { data ->
                data.tipe?.let { it1 -> allCategory.add(it1) }
            }
        }
        mySpinner!!.setOnItemSelectedListener(this)
        arraySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allCategory)
        arraySpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.setAdapter(arraySpinnerAdapter)
        getPosition(itemType)
    }

    private fun getPosition(location: String?) {
        allCategory.forEachIndexed { index, s ->
            if (location != null) {
                if (location == s) {
                    mySpinner.setSelection(index)
                }
            }

        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when {
            allCategory[position].equals("Perangkat Komputer", ignoreCase = true) -> selectedType = "1"
            allCategory[position].equals("Telepon Selular", ignoreCase = true) -> selectedType = "2"
            allCategory[position].equals("Kamera", ignoreCase = true) -> selectedType = "3"
            allCategory[position].equals("Aksesoris Elektronik", ignoreCase = true) -> selectedType = "4"
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    fun message(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()

    }

    override fun onGetPassedDataType(data: UserProducts?) {
        if (data != null) {
            isOnEditState = true
            isImageIsReady = true
//            btnUnggahFoto.gone()
            tvInfoUpload.gone()
            ivPickPhoto.visible()
            productId = data.id
            if (data.imagesData?.url != null) {
                ivPickPhoto.loadUrl(data.imagesData.url)
                ivPickPhoto.setOnClickListener {
                    val alert = Dialog(this, R.style.AppTheme)
                    alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    alert.setContentView(R.layout.activity_fullscreen)
                    alert.setCanceledOnTouchOutside(true)
                    alert.fullScreenImageView.loadUrlOnlyForFullScreen(data.imagesData.url)
                    alert.show()
                    alert.ivClose.setOnClickListener {
                        alert.dismiss()
                    }
                }
            }

            etNamaBarang.setText(data.name)
            etDesBarang.setText(data.description)
            arraySpinnerAdapter?.getPosition(data.typeData?.tipe)?.let { mySpinner.setSelection(it) }
            if (data.categoryData?.id == 2) {
                rbTukar.isChecked = true
            } else if (data.categoryData?.id == 1) {
                rbDonasi.isChecked = true
            }
            if (data.conditionData?.id == 2) {
                rbRusak.isChecked = true
            } else if (data.conditionData?.id == 1) {
                rbBerfungsi.isChecked = true
            }

        }
    }

}