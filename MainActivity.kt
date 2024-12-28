package com.appa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sending an Intent to App B
        val intent = Intent()
        intent.action = "com.appb.ACTION_OPEN"  // The custom action defined in App B
        intent.putExtra("data", "Hello from App A!")
        intent.setPackage("com.appb")  // Ensures it goes to App B
        startActivity(intent)
    }
}
