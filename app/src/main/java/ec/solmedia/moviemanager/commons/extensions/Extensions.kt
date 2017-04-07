@file:JvmName("ExtensionsUtils")

package ec.solmedia.moviemanager.commons.extensions

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import ec.solmedia.moviemanager.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachRoot)
}

fun ImageView.loadImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Picasso.with(context)
                .load(R.mipmap.ic_launcher)
                .into(this)
    } else {
        Picasso.with(context)
                .load(imageUrl)
                .into(this)
    }
}

fun Context.toast(text: CharSequence, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

inline fun DrawerLayout.consume(f: () -> Unit): Boolean {
    f()
    closeDrawers()
    return true
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}