package com.egenvall.namestats.main

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.egenvall.namestats.NameStatsApp
import com.egenvall.namestats.R
import com.egenvall.namestats.common.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    lateinit var mRouter : Router
    var permissionsChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRouter = Conductor.attachRouter(this, controller_container, savedInstanceState)
        checkPermissions()
    }

    override fun onBackPressed() {
        if (!mRouter.handleBack()) {
            super.onBackPressed()
        }
    }

    private fun launchMainScreen(){
        if (!mRouter.hasRootController()) {
            mRouter.setRoot(RouterTransaction.with(MainController()));
        }
    }

    //===================================================================================
// Request permissions for API 23+
//===================================================================================
    private fun checkPermissions() {
        val permissions = ArrayList<String>()

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.READ_CONTACTS)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.INTERNET)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.SEND_SMS)

        if (!permissions.isEmpty())
            ActivityCompat.requestPermissions(this,
                    permissions.toTypedArray(),
                    0)
        else{
            permissionsChecked = true
            launchMainScreen()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val activity = this

        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder(this).setTitle("Permissions")
                        .setMessage(getString(R.string.app_name)
                                + " needs certain permissions to run. Do you wan't to give "
                                + getString(R.string.app_name) + " these permissions?")
                        .setPositiveButton(android.R.string.yes) { dialog, which -> checkPermissions()
                        }.setNegativeButton(android.R.string.no) { dialog, which -> activity.finishAffinity()
                }.setIcon(android.R.drawable.ic_dialog_alert).show()
                return

            }
        }
        permissionsChecked = true

    }
}
