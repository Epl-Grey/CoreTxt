package com.example.coretxt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class answerSimpleAdapter(context: Context, arrayList: ArrayList<answerView>) :
    ArrayAdapter<MsgView?>(context, 0, arrayList!! as List<MsgView?>) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // convertView which is recyclable view
        var currentItemView = convertView

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(context).inflate(R.layout.answer_message, parent, false)
        }

        // get the position of the view from the ArrayAdapter
        val currentNumberPosition: MsgView? = getItem(position)

        // then according to the position of the view assign the desired image for the same
        val nameText = currentItemView!!.findViewById<TextView>(R.id.messageText)
        assert(currentNumberPosition != null)
        if (currentNumberPosition != null) {
            nameText.setText(currentNumberPosition.msgText)
            System.out.println(currentNumberPosition.msgText)
        }
        // then according to the position of the view assign the desired TextView 1 for the same

        // then return the recyclable view
        return currentItemView
    }
}