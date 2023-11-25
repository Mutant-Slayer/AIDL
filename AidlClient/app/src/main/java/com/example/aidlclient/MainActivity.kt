package com.example.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aidl.IAdditionInterface

class MainActivity : AppCompatActivity() {

    private var additionService: IAdditionInterface? = null
    private var connected = false
    private lateinit var num1: EditText
    private lateinit var num2: EditText
    private lateinit var answer: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private var result: Int = 0

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            additionService = IAdditionInterface.Stub.asInterface(service)
            connected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            additionService = null
            connected = false
        }
    }

    private fun performOperation(type: String) {

        if (type == "add") {
            result =
                additionService?.add(num1.text.toString().toInt(), num2.text.toString().toInt())!!

        } else if (type == "subtract") {
            result =
                additionService?.subtract(
                    num1.text.toString().toInt(),
                    num2.text.toString().toInt()
                )!!
        }

        answer.text = result.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        num1 = findViewById(R.id.etNum1)
        num2 = findViewById(R.id.etNum2)
        answer = findViewById(R.id.tvAnswer)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubtract = findViewById(R.id.btnSubtract)

        if (!connected) {
            Intent().also { intent ->
                intent.action = "com.example.aidl.REMOTE_CONNECTION"
                intent.setPackage("com.example.aidlserver")
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        }

        btnAdd.setOnClickListener {
            if (connected) {
                performOperation("add")
            }
        }

        btnSubtract.setOnClickListener {
            if (connected) {
                performOperation("subtract")
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (connected) {
            unbindService(serviceConnection)
            connected = false
        }
    }
}