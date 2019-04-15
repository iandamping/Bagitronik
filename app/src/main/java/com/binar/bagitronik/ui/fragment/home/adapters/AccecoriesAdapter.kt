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
import kotlinx.android.synthetic.main.item_aceccories.*
import org.jetbrains.anko.backgroundResource

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class AccecoriesAdapter(private val data: List<UserProducts>, private val listener: (UserProducts) -> Unit) :
        RecyclerView.Adapter<AccecoriesAdapter.AccecoriesViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AccecoriesViewHolder {
        return AccecoriesViewHolder(p0.inflates(R.layout.item_aceccories))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: AccecoriesViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }


    class AccecoriesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(data: UserProducts, listener: (UserProducts) -> Unit) {
            if (data.imagesData?.url != null) {
                ivAccecoriesProduct.loadUrl(data.imagesData.url)
            } else {
                ivAccecoriesProduct.loadDrawable(R.drawable.empty_image)
            }
            if (data.categoryData?.category.equals("donasi", ignoreCase = true)) {
                tvAccecoriesCategory.backgroundResource = R.drawable.tv_bgg
            } else if (data.categoryData?.category.equals("tukar", ignoreCase = true)) {
                tvAccecoriesCategory.backgroundResource = R.drawable.tv_bggg
            }
            tvAccecoriesCategory.visible()
            tvAccecoriesCategory.text = data.categoryData?.category
            if (data.name?.length!! >= 9) {
                tvAccecoriesTitle.text = data.name.substring(0, 9) + " ..."
            } else {
                tvAccecoriesTitle.text = data.name
            }
            tvAllAccecoriesCity.text = data.userData?.cityUser?.city
            itemView.setOnClickListener { listener(data) }
        }
    }
}