package com.github.htdangkhoa.nexterp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.github.htdangkhoa.nexterp.R


class CheckableSpinnerAdapter<T> internal constructor(
    private val context: Context,
    private val headerText: String,
    private val allItems: MutableList<SpinnerItem<T>>,
    private val selectedItems: MutableSet<T>
) : BaseAdapter() {
    internal class SpinnerItem<T>(val item: T, val txt: String)

    override fun getCount(): Int {
        return allItems.size + 1
    }

    override fun getItem(position: Int): Any? {
        return if (position < 1) {
            null
        } else {
            allItems[position - 1]
        }
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    @NonNull
    override fun getView(position: Int, convertView: View?, @NonNull parent: ViewGroup?): View? {
        var convertView: View? = convertView
        var holder: ViewHolder

        val layoutInflater = LayoutInflater.from(context)
        convertView = layoutInflater.inflate(R.layout.checkable_spinner_item, parent, false)
        holder = ViewHolder(convertView, position)
        convertView.tag = holder

        if (position < 1) {
            holder.mCheckBox.visibility = View.GONE
            holder.mTextView.text = headerText
        } else {
            val listPos = position - 1
            holder.mCheckBox.visibility = View.VISIBLE
            holder.mTextView.text = allItems[listPos].txt
            val item = allItems[listPos].item
            val isSel = selectedItems.contains(item)

            holder.mCheckBox.setOnCheckedChangeListener(null)
            holder.mCheckBox.isChecked = isSel
            holder.mCheckBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(item)
                } else {
                    selectedItems.remove(item)
                }
            })
            holder.mTextView.setOnClickListener {
                holder.mCheckBox.toggle()

            }
        }
        return convertView
    }

    private inner class ViewHolder(v: View, position: Int) : RecyclerView.ViewHolder(v) {
        val mCheckBox : CheckBox = v.findViewById(R.id.checkbox)
        val mTextView : TextView = v.findViewById(R.id.text)
    }

}