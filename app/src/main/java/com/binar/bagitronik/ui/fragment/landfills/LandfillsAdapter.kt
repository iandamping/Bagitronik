package com.binar.bagitronik.ui.fragment.landfills

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.inflates
import com.binar.bagitronik.model.landfills.LandfillsData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_landfills.*

/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
class LandfillsAdapter(private val data: List<LandfillsData>, private val listener: (LandfillsData) -> Unit) : RecyclerView.Adapter<LandfillsAdapter.LandfillsViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LandfillsViewHolder {
        return LandfillsViewHolder(p0.inflates(R.layout.item_landfills))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: LandfillsViewHolder, p1: Int) {
        p0.bind(data[p1], listener)
    }

    class LandfillsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: LandfillsData, listener: (LandfillsData) -> Unit) {
            tvLandFillsName.text = data.namatempat
            tvLandFillsCity.text = data.city_id
            tvLandFillsAddress.text = data.alamat
            itemView.setOnClickListener { listener(data) }
        }
    }
}