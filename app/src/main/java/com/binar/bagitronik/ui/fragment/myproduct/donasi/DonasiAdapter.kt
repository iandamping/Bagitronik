package com.binar.bagitronik.ui.fragment.myproduct.donasi

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.inflates
import com.binar.bagitronik.helper.loadDrawable
import com.binar.bagitronik.helper.loadUrl
import com.binar.bagitronik.model.dealings.*
import com.binar.bagitronik.model.profile.UserDataLogin
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_donasi_revisi.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource

/**
 *
Created by Ian Damping on 02/04/2019.
Github = https://github.com/iandamping
 */
class DonasiAdapter(
        private val data: List<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>,
        private val listener: (BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>) -> Unit
) : RecyclerView.Adapter<DonasiAdapter.DonasiViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DonasiViewHolder {
        return DonasiViewHolder(p0.inflates(R.layout.item_donasi_revisi))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: DonasiViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }


    class DonasiViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val userData = BagitronikApp.gson.fromJson(
                BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey),
                UserDataLogin::class.java
        )

        fun bind(
                data: BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>,
                listener: (BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>) -> Unit
        ) {
            if (data.productNya?.imagesData?.url != null) {
                ivDonasiProductRevisi.loadUrl(data.productNya.imagesData.url)
            } else {
                ivDonasiProductRevisi.loadDrawable(R.drawable.empty_image)
            }
            tvJenisDonasiRevisi.text = data.productNya?.type?.tipe
            tvKondisiDonasiRevisi.text = data.productNya?.condition?.condition
            tvDonasiTitleRevisi.text = data.productNya?.name

            when (data.productNya?.users?.id == userData?.username?.id) {
                false -> ivDonasiStatus.imageResource = R.drawable.ic_arrow_upward
                true -> ivDonasiStatus.imageResource = R.drawable.ic_arrow_downward
            }
            when (data.productNya?.type?.id) {
                1 -> ivJenisDonasiRevisi.loadDrawable(R.drawable.ic_perangkatkomputer)
                2 -> ivJenisDonasiRevisi.loadDrawable(R.drawable.ic_handphone)
                3 -> ivJenisDonasiRevisi.loadDrawable(R.drawable.ic_kamera)
                4 -> ivJenisDonasiRevisi.loadDrawable(R.drawable.ic_aksesoriselektronik)
            }
            when (data.productNya?.condition?.id) {
                1 -> ivConditionDonasiRevisi.loadDrawable(R.drawable.ic_berfungsi)
                2 -> ivConditionDonasiRevisi.loadDrawable(R.drawable.ic_rusak)
            }
            when (data.status?.id) {
                1 -> {
                    tvDonasiStatusDealings.text = data.status.status
                    tvDonasiStatusDealings.backgroundResource = R.drawable.dealing_status_process
                }
                2 -> {
                    tvDonasiStatusDealings.text = data.status.status
                    tvDonasiStatusDealings.backgroundResource = R.drawable.dealing_status_accept
                }
                3 -> {
                    tvDonasiStatusDealings.text = data.status.status
                    tvDonasiStatusDealings.backgroundResource = R.drawable.dealing_status_denied
                }
                5 -> {
                    tvDonasiStatusDealings.text = data.status.status
                    tvDonasiStatusDealings.backgroundResource = R.drawable.dealing_status_finish
                }

                6 -> {
                    tvDonasiStatusDealings.text = data.status.status
                    tvDonasiStatusDealings.backgroundResource = R.drawable.dealing_status_denied
                }
                7 -> {
                    tvDonasiStatusDealings.text = data.status.status
                    tvDonasiStatusDealings.backgroundResource = R.drawable.dealing_status_process
                }
            }

            itemView.setOnClickListener { listener(data) }
        }
    }

}