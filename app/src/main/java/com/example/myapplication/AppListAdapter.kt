package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AppListAdapter(context: Context, customizedListView: List<AppModel>?) : BaseAdapter() {

    var layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var listStorage: List<AppModel>? = customizedListView

    override fun getCount(): Int {
        return listStorage!!.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View = layoutInflater.inflate(R.layout.apps_list_item_card, parent, false) as View
        val textInListView  = view.findViewById<View>(R.id.list_app_name) as TextView
        val imageInListView = view.findViewById<View>(R.id.app_icon) as ImageView
        val packageInListView = view.findViewById<View>(R.id.app_package) as TextView

        textInListView.text = listStorage!![position].name
        imageInListView.setImageDrawable(listStorage!![position].icon)
        packageInListView.text = listStorage!![position].packages
        return view
    }

}