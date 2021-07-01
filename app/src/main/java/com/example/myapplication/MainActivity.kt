package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var upSpeed: String = ""
    private var downSpeed: String = ""

    private var currentDateAndTime: String = ""


    private lateinit var database: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (checkNetwork()) {
            Toast.makeText(this, "Network connection is available", Toast.LENGTH_SHORT).show()
        } else if (!checkNetwork()) {
            Toast.makeText(this, "Network connection is not available", Toast.LENGTH_SHORT).show()
        }


        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        currentDateAndTime = simpleDateFormat.format(Date())
        binding.timeStamp.setText(currentDateAndTime)

        // Connectivity Manager

        val cm =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Network Capabilities of Active Network
        val nc = cm.getNetworkCapabilities(cm.activeNetwork)

        // DownSpeed in MBPS
        downSpeed = ((nc!!.linkDownstreamBandwidthKbps) / 1000).toString()
        // UpSpeed  in MBPS
        upSpeed = ((nc.linkUpstreamBandwidthKbps) / 1000).toString()
        // Toast to Display DownSpeed and UpSpeed
        Toast.makeText(
            applicationContext,

            "Up Speed: $upSpeed Mbps \nDown Speed: $downSpeed Mbps",

            Toast.LENGTH_LONG
        ).show()
        binding.uploadSpeed.setText(upSpeed)

        database = Firebase.database.reference


    }

    fun onSubmit(view: View) {

        writeNewUser("", "", "", "")


    }

    fun writeNewUser(userId: String, upSpeed: String, timeStamp: String, mobileNumber: String) {
        val mobileDetails = Mobile(
            binding.uploadSpeed.text.toString(),
            binding.timeStamp.text.toString(),
            binding.mobileNumber.text.toString()
        )

        database.child("mobile_list").child(userId).setValue(mobileDetails)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        checkNetwork()

    }


    private fun checkNetwork(): Boolean {
        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        "Network type is " + networkInfo!!.typeName
        return true
    }
}