package com.example.coretxt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

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

//        System.out.println(RestApi.sendText("some text 123"))
    }
}