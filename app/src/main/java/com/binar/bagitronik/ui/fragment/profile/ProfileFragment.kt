package com.binar.bagitronik.ui.fragment.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.helper.Constant.passingProfileData
import com.binar.bagitronik.helper.Constant.userPrefKey
import com.binar.bagitronik.model.profile.AllUser
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.activity.appinformation.AppInformationActivity
import com.binar.bagitronik.ui.activity.editprofile.EditProfileActivity
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.fragment_akunku.view.*


class ProfileFragment : Fragment(), ProfileView {
    private var passedUserData: String? = null
    private var actualView: View? = null
    private var presenter: ProfilePresenter? = null
    private val userData = gson.fromJson(prefHelper.getStringInSharedPreference(userPrefKey), UserDataLogin::class.java)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (userData != null) {
            if (userData.username?.firstName == null) {
                context?.alertHelperToEditAccount("Ubah profil terlebih dahulu")
            } else if (userData.token != null) {
                presenter = ProfilePresenter(this)
                presenter?.onAttach(context)
            }
        } else {
            context?.alertHelperToLogin("Silakan Masuk terlebih dahulu")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (presenter != null) {
            presenter?.getInitailUserData()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_akunku)
        if (presenter != null) {
            views?.let { presenter?.onCreateView(it) }
        }
        return views
    }

    override fun initView(view: View) {
        this.actualView = view
        initToolbar(view)
        view.tvUserAddress?.text = userData?.username?.address
        view.tvUserCity?.text = userData?.username?.nik
        view.tvPhoneNumber?.text = userData?.username?.phone
        view.tvUserEmail?.text = userData?.username?.email
        view.tveditProfile.setOnClickListener {
            context?.startActivity<EditProfileActivity>() {
                putExtra(passingProfileData, passedUserData)
            }
        }
        view.edit_akunku.setOnClickListener {
            context?.startActivity<AppInformationActivity>()
        }
    }

    private fun initToolbar(view: View) {
        view.toolbarMyProfile.title = "Profil saya"
        view.toolbarMyProfile.setTitleTextColor(resources.getColor(R.color.colorComment))
        view.toolbarMyProfile.setSubtitleTextColor(resources.getColor(R.color.colorComment))
    }

    override fun onFailGetData(msg: String) {
        context?.alertHelper(msg)
    }

    override fun onSuccesGetData(data: AllUser?) {
        actualView?.ivFotoAkunku?.loadUrl(data?.userImage?.url)
        actualView?.ivFotoAkunku?.setOnClickListener {
            val alert = Dialog(context, R.style.AppTheme)
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setContentView(com.binar.bagitronik.R.layout.activity_fullscreen)
            alert.setCanceledOnTouchOutside(true)
            alert.fullScreenImageView.loadUrlOnlyForFullScreen(data?.userImage?.url)
            alert.show()
            alert.ivClose.setOnClickListener {
                alert.dismiss()
            }
        }
        actualView?.nama_pemilik_akun?.text = data?.firstName
        actualView?.tvUserAddress?.text = data?.address
        actualView?.tvPhoneNumber?.text = data?.phone
        actualView?.tvUserEmail?.text = data?.email
        actualView?.tvUserCity?.text = data?.cityUser?.city
        if (data?.tukar?.count != null) {
            actualView?.freq_barter?.text = data.tukar.count.toString()
        } else {
            actualView?.freq_barter?.text = "0"

        }
        if (data?.donasi?.count != null) {
            actualView?.freq_donasi?.text = data.donasi.count.toString()
        } else {
            actualView?.freq_donasi?.text = "0"
        }
        this.passedUserData = gson.toJson(data)
    }
}
