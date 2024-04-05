package ru.papino.restaurant.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

internal interface CoroutineProperty {

    val parentLifecycle: LifecycleOwner

    fun <T> SharedFlow<T>.bind(collector: FlowCollector<T>) {
        parentLifecycle.lifecycleScope.launch {
            this@bind.collect(collector)

        }
    }
}