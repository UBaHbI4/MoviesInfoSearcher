package softing.ubah4ukdev.moviesinfosearcher

import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher

Created by Ivan Sheynmaer

2021.05.19
v1.0
 */
fun <V : ViewBinding> Fragment.viewBinding(bind: (view: View) -> V) =
    ViewBindingDelegate(this, bind)

class ViewBindingDelegate<V : ViewBinding>(
    fragment: Fragment,
    private val bind: (view: View) -> V
) :
    ReadOnlyProperty<Fragment, V> {
    private var viewBinding: V? = null
    private val mainThreadHandler = android.os.Handler(Looper.getMainLooper())

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) {
            it.lifecycle.addObserver(object : LifecycleObserver {

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun destroyed() {
                    mainThreadHandler.post {
                        viewBinding = null
                    }
                }
            })
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
        return viewBinding ?: run {
            val view = thisRef.requireView()
            bind.invoke(view).also {
                viewBinding = it
            }
        }
    }
}