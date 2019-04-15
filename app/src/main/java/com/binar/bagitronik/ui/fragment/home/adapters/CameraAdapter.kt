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
import kotlinx.android.synthetic.main.item_camera.*
import org.jetbrains.anko.backgroundResource

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class CameraAdapter(private val data: List<UserProducts>, private val listener: (UserProducts) -> Unit) :
        RecyclerView.Adapter<CameraAdapter.CameraViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CameraViewHolder {
        return CameraViewHolder(p0.inflates(R.layout.item_camera))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: CameraViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }


    class CameraViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(data: UserProducts, listener: (UserProducts) -> Unit) {
            if (data.imagesData?.url != null) {
                ivCameraProduct.loadUrl(data.imagesData.url)
            } else {
                ivCameraProduct.loadDrawable(R.drawable.empty_image)
            }
            if (data.categoryData?.category.equals("donasi", ignoreCase = true)) {
                tvCameraCategory.backgroundResource = R.drawable.tv_bgg
            } else if (data.categoryData?.category.equals("tukar", ignoreCase = true)) {
                tvCameraCategory.backgroundResource = R.drawable.tv_bggg
            }
            tvCameraCategory.visible()
            tvCameraCategory.text = data.categoryData?.category
            if (data.name?.length!! >= 9) {
                tvCameraTitle.text = data.name.substring(0, 9) + " ..."
            } else {
                tvCameraTitle.text = data.name
            }
            tvAllCameraCity.text = data.userData?.cityUser?.city
            itemView.setOnClickListener { listener(data) }
        }
    }
}