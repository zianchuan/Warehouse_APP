package com.example.warehouse_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val button1 = findViewById<Button>(R.id.buttonA1)
        val button2 = findViewById<Button>(R.id.buttonB1)
        val button3 = findViewById<Button>(R.id.buttonC1)
        val button4 = findViewById<Button>(R.id.buttonD1)
        val button5 = findViewById<Button>(R.id.buttonE1)
        val button6 = findViewById<Button>(R.id.buttonE2)
        val button7 = findViewById<Button>(R.id.buttonE3)
        val button8 = findViewById<Button>(R.id.buttonF1)
        val button9 = findViewById<Button>(R.id.buttonF2)
        val button10 = findViewById<Button>(R.id.buttonG1)
        val button11 = findViewById<Button>(R.id.buttonH1)
        val button12 = findViewById<Button>(R.id.buttonI1)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        button10.setOnClickListener(this)
        button11.setOnClickListener(this)
        button12.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val b = v as Button
        val buttonText = b.text.toString()
        when (v.getId()) {
            R.id.buttonA1 -> {
                openRack(buttonText)
            }
            R.id.buttonB1 -> {
                openRack(buttonText)
            }
            R.id.buttonC1 -> {
                openRack(buttonText)
            }
            R.id.buttonD1 -> {
                openRack(buttonText)
            }
            R.id.buttonE1 -> {
                openRack(buttonText)
            }
            R.id.buttonE2 -> {
                openRack(buttonText)
            }
            R.id.buttonE3 -> {
                openRack(buttonText)
            }
            R.id.buttonF1 -> {
                openRack(buttonText)
            }
            R.id.buttonF2 -> {
                openRack(buttonText)
            }
            R.id.buttonG1 -> {
                openRack(buttonText)
            }
            R.id.buttonH1 -> {
                openRack(buttonText)
            }
            R.id.buttonI1 -> {
                openRack(buttonText)
            }
        }
    }

    fun openRack(buttonText:String) {
        val intent = Intent(this, RackActivity::class.java)
        intent.putExtra("RACK_ID", buttonText)
        startActivity(intent)
    }
}