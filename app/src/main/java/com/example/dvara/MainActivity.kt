package com.example.dvara

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dvara.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var upSpeed: String = ""
    //private var downSpeed: String = ""

    private var currentDateAndTime: String = ""


    private lateinit var database: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (checkNetwork()) {
            binding.internet.text = "Network connection is available"

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            currentDateAndTime = simpleDateFormat.format(Date())
            binding.timeStamp.setText(currentDateAndTime)

            // Connectivity Manager

            val cm =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // Network Capabilities of Active Network
            val nc = cm.getNetworkCapabilities(cm.activeNetwork)

            // DownSpeed in MBPS
            // downSpeed = ((nc!!.linkDownstreamBandwidthKbps) / 1000).toString()
            // UpSpeed  in MBPS
            upSpeed = ((nc?.linkUpstreamBandwidthKbps)?.div(1000)).toString()
            // Toast to Display DownSpeed and UpSpeed
            /*Toast.makeText(
                applicationContext,

                "Up Speed: $upSpeed Mbps \nDown Speed: $downSpeed Mbps",

                Toast.LENGTH_LONG
            ).show()*/

            binding.uploadSpeed.setText(upSpeed)

            database = Firebase.database.reference
        } else if (!checkNetwork()) {
            binding.internet.text = "Network connection is not available"
            binding.uploadSpeedTextField.visibility = View.GONE
            binding.timeStampTextField.visibility = View.GONE
            binding.mobileNumberTextField.visibility = View.GONE
            binding.submitButton.visibility = View.GONE

        }


    }

    private fun validateMobileNumber(): Boolean {

        binding.mobileNumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {

                binding.mobileNumberTextField.error = ""
            }
        })

        if (binding.mobileNumber.text.isNullOrEmpty()) {
            binding.mobileNumberTextField.error = "Enter mobile number"
            binding.mobileNumber.requestFocus()
            return false
        } else {
            binding.mobileNumberTextField.error = ""
        }
        return true
    }

    fun onSubmit(view: View) {

        if (!validateMobileNumber()) {
            return
        } else {
            val mobileDetails = Mobile(
                binding.uploadSpeed.text.toString(),
                binding.timeStamp.text.toString(),
                binding.mobileNumber.text.toString()
            )

            database.child("mobile_list").push().setValue(mobileDetails)
            startActivity(Intent(this, SearchActivity::class.java))
        }


    }


    override fun onResume() {
        super.onResume()
        checkNetwork()
        clearText()

    }

    private fun clearText() {
        binding.mobileNumber.text?.clear()
    }

    private fun checkNetwork(): Boolean {
        /*val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        "Network type is " + networkInfo!!.typeName*/

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return true


        // val isMetered = cm.isActiveNetworkMetered
    }
}