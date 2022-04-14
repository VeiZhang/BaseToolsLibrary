package com.excellence.basetoolslibrary.databinding.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * copy from [androidx.fragment.app.FragmentViewLifecycleOwner]
 */
class ViewLifecycleOwner : LifecycleOwner {

    private var mLifecycleRegistry: LifecycleRegistry? = null

    /**
     * Initializes the underlying Lifecycle if it hasn't already been created.
     */
    fun initialize() {
        if (mLifecycleRegistry == null) {
            mLifecycleRegistry = LifecycleRegistry(this)
        }
    }

    /**
     * @return True if the Lifecycle has been initialized.
     */
    fun isInitialized(): Boolean {
        return mLifecycleRegistry != null
    }

    override fun getLifecycle(): Lifecycle {
        initialize()
        return mLifecycleRegistry!!
    }

    fun handleLifecycleEvent(event: Lifecycle.Event) {
        initialize()
        mLifecycleRegistry!!.handleLifecycleEvent(event)
    }
}