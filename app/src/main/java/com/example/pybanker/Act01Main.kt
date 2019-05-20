package com.example.pybanker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class Act01Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkStoragePermissions()

        btnLogin.setOnClickListener {
            if (etAccessCode.text.toString() == "8989") { // todo Get access key from Database and don't hardcode
                val intent = Intent(this, Act02NavDrawer::class.java)
                startActivity(intent)
                finish()
            } else {
                etAccessCode.setText("")
                Toast.makeText(this, "Invalid Access Key", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }
}
