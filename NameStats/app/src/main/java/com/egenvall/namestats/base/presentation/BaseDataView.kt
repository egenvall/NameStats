package com.egenvall.namestats.base.presentation

import android.content.Context

/**
 * The view class for each MVP candidate which loads data can implement this interface.
 * For each View in the app you should extend this
 * interface via an interface specific to that View.
 */
interface BaseDataView {

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    fun showProgress()

    /**
     * Hide a loading view.
     */
    fun hideProgress()


}