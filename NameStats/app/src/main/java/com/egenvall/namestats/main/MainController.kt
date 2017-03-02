package com.egenvall.namestats.main

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.rxlifecycle.RxController
import com.egenvall.namestats.R


class MainController : RxController() {
    @LayoutRes val layoutResId: Int = R.layout.screen_main
    private val TAG = "MainController"

    //===================================================================================
    // Lifecycle methods and initialization
    //===================================================================================

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(layoutResId, container, false)
        onViewBound(view)
        return view
    }

    fun onViewBound(view: View) {
        //Initiate view stuff
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }

}

