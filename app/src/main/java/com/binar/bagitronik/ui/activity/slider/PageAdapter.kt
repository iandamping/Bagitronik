package com.binar.bagitronik.ui.activity.slider

import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.inflates
import com.binar.bagitronik.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_slide_third.view.*

class PageAdapter : PagerAdapter {

    override fun isViewFromObject(view: View, objects: Any): Boolean {
        return view == objects
    }

    private var statusUser: Boolean = false
    private var intent: Intent = Intent()
    var inflater: LayoutInflater
    var con: Context

    constructor(con: Context, status: Boolean) : super() {
        this.con = con
        inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.statusUser = status
    }


    override fun getCount(): Int {

        return 3

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view: View = container.inflates(R.layout.first_slide)
        when (position) {
            0 -> view = container.inflates(R.layout.activity_slide_first)
            1 -> view = container.inflates(R.layout.activity_slide_second)
            2 -> {
                view = container.inflates(R.layout.activity_slide_third)
                view.btn_mulai.setOnClickListener {
                    intent.setClass(con, MainActivity::class.java)
                    con.startActivity(intent)
                }
            }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, objects: Any) {

        var view: View = objects as View
        container.removeView(view)
    }


}