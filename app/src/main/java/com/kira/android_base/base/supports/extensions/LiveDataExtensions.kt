package com.kira.android_base.base.supports.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveData<T> : MutableLiveData<T?>() {
    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        super.observe(owner) { t ->
            if (t != null) {
                observer.onChanged(t)
                postValue(null)
            }
        }
    }
}
