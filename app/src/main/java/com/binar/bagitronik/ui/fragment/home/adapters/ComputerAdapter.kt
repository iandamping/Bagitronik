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
import kotlinx.android.synthetic.main.item_home_fragment.*
import org.jetbrains.anko.backgroundResource

/**
 *
Created by Ian Damping on 25/03/2019.
Github = https://github.com/iandamping
 */
class ComputerAdapter(private val data: List<UserProducts>, private val listener: (UserProducts) -> Unit) :
        RecyclerView.Adapter<ComputerAdapter.HomeViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeViewHolder {
        return HomeViewHolder(p0.inflates(R.layout.item_home_fragment))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: HomeViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }


    class HomeViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(data: UserProducts, listener: (UserProducts) -> Unit) {
            if (data.imagesData?.url != null) {
                ivProduct.loadUrl(data.imagesData.url)
            } else {
                ivProduct.loadDrawable(R.drawable.empty_image)
            }
            if (data.categoryData?.category.equals("donasi", ignoreCase = true)) {
                tvProductCategory.backgroundResource = R.drawable.tv_bgg
            } else if (data.categoryData?.category.equals("tukar", ignoreCase = true)) {
                tvProductCategory.backgroundResource = R.drawable.tv_bggg
            }
            tvProductCategory.visible()
            tvProductCategory.text = data.categoryData?.category
            if (data.name?.length!! >= 9) {
                tvTitle.text = data.name.substring(0, 9) + " ..."
            } else {
                tvTitle.text = data.name
            }
            tvAllComputerCity.text = data.userData?.cityUser?.city
            itemView.setOnClickListener { listener(data) }
        }
    }
}