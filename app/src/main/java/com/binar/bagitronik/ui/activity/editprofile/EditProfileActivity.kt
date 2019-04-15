package com.binar.bagitronik.ui.activity.editprofile

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
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.helper.Constant.RequestOpenCamera
import com.binar.bagitronik.helper.Constant.RequestSelectGalleryImage
import com.binar.bagitronik.helper.Constant.maxHeight
import com.binar.bagitronik.helper.Constant.maxWidth
import com.binar.bagitronik.helper.Constant.passingProfileData
import com.binar.bagitronik.helper.ImageHelper.Companion.bitmapToFile
import com.binar.bagitronik.model.profile.AllUser
import com.binar.bagitronik.model.profile.CitiesData
import com.binar.bagitronik.model.profile.User
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.activity.MainActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.io.File

/**
 *
Created by Ian Damping on 31/03/2019.
Github = https://github.com/iandamping
 */
class EditProfileActivity : AppCompatActivity(), EditProfileView {
    private var intents: Intent = Intent()
    private var imageDecodes: File? = null
    private var isImageisSame = false
    private var tmpData: String? = null
    private var isPermissionGranted = false
    private var tmpSelectedCityID: Int? = null
    private var allCities = mutableListOf<String>()
    private var citiesComparator = mutableListOf<CitiesData>()
    private var arraySpinnerAdapter: ArrayAdapter<String>? = null
    private var isOnEditState: Boolean? = false
    private var isImageIsReady: Boolean? = false
    private var cityType: String? = null
    private var passedData: AllUser? = null
    private lateinit var presenter: EditProfilePresenter
    private val userData =
            gson.fromJson(BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        fullScreenAnimation()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_edit_profile)
        getAllPermission()
        presenter = EditProfilePresenter(this)
        presenter.onCreate(this)
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.onGetPassedData(intent?.getStringExtra(Constant.passingProfileData))
        this.passedData = gson.fromJson(intent?.getStringExtra(passingProfileData), AllUser::class.java)
        if (passedData != null) {
            if (passedData?.cityUser?.city != null) {
                cityType = passedData?.cityUser?.city
            }
        }
    }

    override fun onFailedGetData(msg: String) {
        logE(msg)
    }

    override fun onSuccesUpdateData(msg: String) {
        startActivity<MainActivity>() {
            putExtra(Constant.switchBackToMain, "5")
        }
    }


    override fun onGetCitiesData(data: List<CitiesData>?) {
        allCities.clear()
        citiesComparator.clear()
        this.citiesComparator = data as MutableList<CitiesData>
        data.forEach {
            it.city?.let { it1 -> allCities.add(it1) }
        }
        arraySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allCities)
        arraySpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEditUser.adapter = arraySpinnerAdapter
        getPosition(cityType)
        spinnerEditUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tmpData = allCities[position]
            }

        }
    }

    private fun getPosition(location: String?) {
        allCities.forEachIndexed { index, s ->
            if (location != null) {
                if (location == s) {
                    spinnerEditUser.setSelection(index)
                }
            }

        }
    }

    private fun openImageDialogFromSnackbar(views: View) {
        val snackbar = Snackbar
                .make(views, "PILIH GAMBAR DULU", Snackbar.LENGTH_LONG)
                .setAction("BUKA") {
                    openGalleryAndCamera(isPermissionGranted)
                }
        snackbar.show()
    }

    override fun onGetPassedData(data: AllUser?) {
        isOnEditState = true
        isImageisSame = true
        isImageIsReady = true
        data?.let {
            etEditUserAddress.setText(data.address)
            etEditUserKtpNumber.setText(data.nik)
            etEditUserPhoneNumber.setText(data.phone)
            etEditUserEmail.setText(data.email)
            etEditFirstName.setText(data.firstName)

            foto_akunku_edit.loadUrl(data.userImage?.url)
            foto_akunku_edit.setOnClickListener {
                val alert = Dialog(this, R.style.AppTheme)
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                alert.setContentView(R.layout.activity_fullscreen)
                alert.setCanceledOnTouchOutside(true)
                alert.fullScreenImageView.loadUrlOnlyForFullScreen(data.userImage?.url)
                alert.show()
                alert.ivClose.setOnClickListener {
                    alert.dismiss()
                }
            }
        }
    }

    override fun onGetDefaultData(data: User?) {

    }

    override fun initView() {
        if (passedData == null) {
            etEditUserEmail.setText(userData?.username?.email)

        }
        initToolbar()
        ivPickPhotoProfile.setOnClickListener {
            openGalleryAndCamera(isPermissionGranted)
        }
        btnSaveEditUser.setOnClickListener {
            citiesComparator.forEach {
                when {
                    tmpData?.equals(it.city)!! -> tmpSelectedCityID = it.id
                }
            }
            validateEditText()

        }
    }

    private fun validateEditText() {
        var tempFile: File? = null
        val phoneNumber = etEditUserPhoneNumber?.text?.toString()?.trim()
        val name = etEditFirstName?.text?.toString()?.trim()
        val email = etEditUserEmail?.text.toString().trim()
        val password = etPasswordEdit?.text.toString().trim()
        val passwordConfirmation = etPasswordConfirmationEdit?.text.toString().trim()
        val address = etEditUserAddress?.text?.toString()?.trim()
        val cityId = tmpSelectedCityID
        imageDecodes?.let {
            tempFile = it
        }

        when {
            phoneNumber.isNullOrBlank() -> {
                etEditUserPhoneNumber.requestError(getString(R.string.not_null))
                return
            }
            name.isNullOrBlank() -> {
                etEditFirstName.requestError(getString(R.string.not_null))
                return
            }
            email.isNullOrBlank() -> {
                etEditUserEmail.requestError(getString(R.string.not_null))
                return
            }
            password.isNullOrBlank() -> {
                etPasswordEdit.requestError(getString(R.string.not_null))
                return
            }
            passwordConfirmation.isNullOrBlank() -> {
                etPasswordConfirmationEdit.requestError(getString(R.string.not_null))
                return
            }
            address.isNullOrBlank() -> {
                etEditUserAddress.requestError(getString(R.string.not_null))
                return
            }
            cityId == null -> {
                openImageDialogFromSnackbar(rootEditProfile)
                return
            }

            isImageIsReady == false -> myToast("Pilih Gambar Terlebih dahulu")

            !password.equals(passwordConfirmation, ignoreCase = true) -> myToast("Kata sandi tidak Sama")

            else -> {
                if (passedData == null) {
                    presenter.updateUser(email, password, name, address, phoneNumber, cityId, tempFile)
                }
                if (isOnEditState!!) {
                    presenter.updateUser(email, password, name, address, phoneNumber, cityId, tempFile)
                }
                if (isOnEditState!! && isImageisSame) {
                    presenter.updateUser(email, password, name, address, phoneNumber, cityId, null)
                }

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestSelectGalleryImage) {
                val paths = data?.data
                val bitmap = ImageHelper.getBitmapFromGallery(this, paths)
                if (bitmap != null) {
                    foto_akunku_edit.setImageBitmap(bitmap)
                    foto_akunku_edit.setOnClickListener {
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
                isImageisSame = false
            } else if (requestCode == RequestOpenCamera) {
                val bitmap = ImageHelper.decodeSampledBitmapFromFile(
                        createImageFileFromPhoto(), reqWidth = maxWidth, reqHeight = maxHeight
                )
                if (bitmap != null) {
                    foto_akunku_edit.setImageBitmap(bitmap)
                    foto_akunku_edit.setOnClickListener {
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
                isImageisSame = false
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

    private fun openImageFromGallery(status: Boolean?) {
        if (status != null) {
            if (status) {
                intents = Intent(Intent.ACTION_PICK)
                intents.type = "image/*"
                startActivityForResult(intents, Constant.RequestSelectGalleryImage)
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
                startActivityForResult(intents, Constant.RequestOpenCamera)
            }
        }
    }

    private fun createImageFileFromPhoto(): File {
        return nonVoidCustomMediaScannerConnection(this, Constant.saveCaptureImagePath)
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

    private fun getAllPermission() {
        Dexter.withActivity(this@EditProfileActivity).withPermissions(
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


    private fun initToolbar() {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_blue_24dp)
        upArrow?.setColorFilter(resources.getColor(R.color.colorComment), PorterDuff.Mode.SRC_ATOP)
        toolbarEditUser.title = "Ubah Profil"
        toolbarEditUser.setTitleTextColor(resources.getColor(R.color.colorComment))
        toolbarEditUser.setSubtitleTextColor(resources.getColor(R.color.colorComment))
        setSupportActionBar(toolbarEditUser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                startActivity<MainActivity> {
                    putExtra(Constant.switchBackToMain, "4")
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity> {
            putExtra(Constant.switchBackToMain, "4")
            finish()
        }
    }


}