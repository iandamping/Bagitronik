package com.binar.bagitronik.ui.fragment.myproduct.myproduct

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.inflates
import com.binar.bagitronik.helper.loadDrawable
import com.binar.bagitronik.helper.loadUrl
import com.binar.bagitronik.model.product.UserProducts
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_myitem_revisi.*

/**
 *
Created by Ian Damping on 08/04/2019.
Github = https://github.com/iandamping
 */
class MyProductAdapter(private val data: List<UserProducts>, private val listener: (UserProducts) -> Unit) : RecyclerView.Adapter<MyProductAdapter.MyItemViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyItemViewHolder {
        return MyItemViewHolder(p0.inflates(R.layout.item_myitem_revisi))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: MyItemViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }


    class MyItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: UserProducts, listener: (UserProducts) -> Unit) {
            if (data.imagesData?.url != null) {
                ivMyItemProductRevisi.loadUrl(data.imagesData.url)
            } else {
                ivMyItemProductRevisi.loadDrawable(R.drawable.empty_image)
            }
            tvJenisMyItemRevisi.text = data.typeData?.tipe
            tvKondisiMyItemRevisi.text = data.conditionData?.condition
            tvMyItemTitleRevisi.text = data.name

            when (data.typeData?.id) {
                1 -> ivJenisMyItemRevisi.loadDrawable(R.drawable.ic_perangkatkomputer)
                2 -> ivJenisMyItemRevisi.loadDrawable(R.drawable.ic_handphone)
                3 -> ivJenisMyItemRevisi.loadDrawable(R.drawable.ic_kamera)
                4 -> ivJenisMyItemRevisi.loadDrawable(R.drawable.ic_aksesoriselektronik)
            }
            when (data.conditionData?.id) {
                1 -> ivConditionMyItemRevisi.loadDrawable(R.drawable.ic_berfungsi)
                2 -> ivConditionMyItemRevisi.loadDrawable(R.drawable.ic_rusak)
            }
            itemView.setOnClickListener { listener(data) }
        }
    }

}