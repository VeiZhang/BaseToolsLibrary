package com.excellence.basetoolslibrary.databinding.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * copy from {@link androidx.fragment.app.FragmentViewLifecycleOwner}
 */
public class ViewLifecycleOwner implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry = null;

    /**
     * Initializes the underlying Lifecycle if it hasn't already been created.
     */
    public void initialize() {
        if (mLifecycleRegistry == null) {
            mLifecycleRegistry = new LifecycleRegistry(this);
        }
    }

    /**
     * @return True if the Lifecycle has been initialized.
     */
    public boolean isInitialized() {
        return mLifecycleRegistry != null;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        initialize();
        return mLifecycleRegistry;
    }

    public void handleLifecycleEvent(@NonNull Lifecycle.Event event) {
        mLifecycleRegistry.handleLifecycleEvent(event);
    }
}