package com.example.assignment.gradle.util

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class WeekRefDelegate<V : Any>(value: V? = null) {
    private var weakRef: WeakReference<V>? = if (value != null) WeakReference(value) else null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): V? {
        return weakRef?.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: V?) {
        if (value != null) {
            weakRef = WeakReference(value)
        }
    }
}
