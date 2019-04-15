package com.binar.bagitronik.ui.fragment.home.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.inflates
import com.binar.bagitronik.helper.loadDrawable
import com.binar.bagitronik.helper.loadUrl
import com.binar.bagitronik.helper.visible
import com.binar.bagitronik.model.product.UserProducts
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_phone.*
import org.jetbrains.anko.backgroundResource

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class PhoneAdapter(private val data: List<UserProducts>, private val listener: (UserProducts) -> Unit) :
        RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhoneViewHolder {
        return PhoneViewHolder(p0.inflates(R.layout.item_phone))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: PhoneViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }


    class PhoneViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: UserProducts, listener: (UserProducts) -> Unit) {
            if (data.imagesData?.url != null) {
                ivPhoneProduct.loadUrl(data.imagesData.url)
            } else {
                ivPhoneProduct.loadDrawable(R.drawable.empty_image)
            }
            if (data.categoryData?.category.equals("donasi", ignoreCase = true)) {
                tvPhoneCategory.backgroundResource = R.drawable.tv_bgg
            } else if (data.categoryData?.category.equals("tukar", ignoreCase = true)) {
                tvPhoneCategory.backgroundResource = R.drawable.tv_bggg
            }
            tvPhoneCategory.visible()
            tvPhoneCategory.text = data.categoryData?.category
            if (data.name?.length!! >= 9) {
                tvPhoneTitle.text = data.name.substring(0, 9) + " ..."
            } else {
                tvPhoneTitle.text = data.name
            }
            tvAllSmartPhoneCity.text = data.userData?.cityUser?.city
            itemView.setOnClickListener { listener(data) }
        }
    }
}