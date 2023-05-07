package com.example.coretxt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var messageList: ListView = findViewById(R.id.messgeList)
        var sendBtn: Button = findViewById(R.id.sendBtn)
        var editText: EditText = findViewById(R.id.edt)

        var MsgList: ArrayList<MsgView> = java.util.ArrayList<MsgView>()
        var msgAdapter: msgSimpleAdapter = msgSimpleAdapter(this, MsgList)

        var answerList: ArrayList<answerView> = java.util.ArrayList<answerView>()
        var answerAdapter: answerSimpleAdapter = answerSimpleAdapter(this, answerList)

        sendBtn.setOnClickListener {
            var textMSg: String = editText.text.toString()
            println(textMSg)
            MsgList.add(MsgView(textMSg))
            answerList.add(answerView(textMSg))
            msgAdapter = msgSimpleAdapter(this, MsgList)
            answerAdapter = answerSimpleAdapter(this, answerList)

            messageList.adapter = msgAdapter
            messageList.adapter = answerAdapter




        }

    }
}