package com.binar.bagitronik.ui.activity.listproduct

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.*
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.model.profile.UserDataLogin
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_all_list.*
import org.jetbrains.anko.backgroundResource

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class AllProductAdapter(private val data: List<UserProducts>, private val listener: (UserProducts) -> Unit) : RecyclerView.Adapter<AllProductAdapter.AllProductViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AllProductViewHolder {
        return AllProductViewHolder(p0.inflates(R.layout.item_all_list))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: AllProductViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }

    class AllProductViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val userData = BagitronikApp.gson.fromJson(BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

        fun bind(data: UserProducts, listener: (UserProducts) -> Unit) {
            if (data.imagesData?.url != null) {
                ivAllListProduct.loadUrl(data.imagesData.url)
            } else {
                ivAllListProduct.loadDrawable(R.drawable.empty_image)
            }
            if (data.categoryData?.category.equals("donasi", ignoreCase = true)) {
                tvAllCategory.backgroundResource = R.drawable.tv_bgg
            } else if (data.categoryData?.category.equals("tukar", ignoreCase = true)) {
                tvAllCategory.backgroundResource = R.drawable.tv_bggg
            }
            tvAllCategory.visible()
            tvAllCategory.text = data.categoryData?.category
            tvAllListTitle.text = data.name
            tvAllProductCity.text = data.userData?.cityUser?.city
            itemView.setOnClickListener { listener(data) }
        }
    }
}