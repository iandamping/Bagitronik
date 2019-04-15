package com.binar.bagitronik.ui.fragment.myproduct.tukar

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
import kotlinx.android.synthetic.main.item_barter_revisi.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource

/**
 *
Created by Ian Damping on 02/04/2019.
Github = https://github.com/iandamping
 */
class TukarAdapter(private val data: List<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>, private val listener: (BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>) -> Unit) : RecyclerView.Adapter<TukarAdapter.TukarViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TukarViewHolder {
        return TukarViewHolder(p0.inflates(R.layout.item_barter_revisi))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: TukarViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }


    class TukarViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val userData = BagitronikApp.gson.fromJson(BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

        fun bind(data: BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>, listener: (BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>) -> Unit) {
            if (data.productNya?.imagesData?.url != null) {
                ivBarterProductRevisi.loadUrl(data.productNya.imagesData.url)
            } else {
                ivBarterProductRevisi.loadDrawable(R.drawable.empty_image)
            }
            tvJenisBarterRevisi.text = data.productNya?.type?.tipe
            tvKondisiBarterRevisi.text = data.productNya?.condition?.condition
            tvBarterTitleRevisi.text = data.productNya?.name

            when (data.productNya?.users?.id == userData?.username?.id) {
                false -> ivBarterStatus.imageResource = R.drawable.ic_arrow_upward
                true -> ivBarterStatus.imageResource = R.drawable.ic_arrow_downward
            }
            when (data.productNya?.type?.id) {
                1 -> ivJenisBarterRevisi.loadDrawable(R.drawable.ic_perangkatkomputer)
                2 -> ivJenisBarterRevisi.loadDrawable(R.drawable.ic_handphone)
                3 -> ivJenisBarterRevisi.loadDrawable(R.drawable.ic_kamera)
                4 -> ivJenisBarterRevisi.loadDrawable(R.drawable.ic_aksesoriselektronik)
            }
            when (data.productNya?.condition?.id) {
                1 -> ivConditionBarterRevisi.loadDrawable(R.drawable.ic_berfungsi)
                2 -> ivConditionBarterRevisi.loadDrawable(R.drawable.ic_rusak)
            }
            when (data.status?.id) {
                1 -> {
                    tvBarterStatusDealings.text = data.status.status
                    tvBarterStatusDealings.backgroundResource = R.drawable.dealing_status_process
                }
                2 -> {
                    tvBarterStatusDealings.text = data.status.status
                    tvBarterStatusDealings.backgroundResource = R.drawable.dealing_status_accept
                }
                3 -> {
                    tvBarterStatusDealings.text = data.status.status
                    tvBarterStatusDealings.backgroundResource = R.drawable.dealing_status_denied
                }
                5 -> {
                    tvBarterStatusDealings.text = data.status.status
                    tvBarterStatusDealings.backgroundResource = R.drawable.dealing_status_finish
                }

                6 -> {
                    tvBarterStatusDealings.text = data.status.status
                    tvBarterStatusDealings.backgroundResource = R.drawable.dealing_status_denied
                }
                7 -> {
                    tvBarterStatusDealings.text = data.status.status
                    tvBarterStatusDealings.backgroundResource = R.drawable.dealing_status_process
                }

            }
            itemView.setOnClickListener { listener(data) }
        }
    }

}