package com.egenvall.namestats.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.egenvall.namestats.NameStatsApp
import com.egenvall.namestats.R
import com.egenvall.namestats.common.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    lateinit var mRouter : Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRouter = Conductor.attachRouter(this, controller_container, savedInstanceState)
        if (!mRouter.hasRootController()) {
            mRouter.setRoot(RouterTransaction.with(MainController()));
        }
    }

    override fun onBackPressed() {
        if (!mRouter.handleBack()) {
            super.onBackPressed()
        }
    }
}
