package softing.ubah4ukdev.moviesinfosearcher.ui.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui.extensions

Created by Ivan Sheynmaer

2021.05.29
v1.0
 */
fun View.visible(visible: () -> Boolean): View {
    visibility = if (visible()) {
        View.VISIBLE
    } else {
        View.GONE
    }
    return this
}

//Показать SnackBar c передачей текста в виде String
fun View.showSnakeBar(text: String, length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, text, length).show()
}

//Показать SnackBar c отображением строкового ресурса из R.String
fun View.showSnakeBar(stringResource: Int, length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, context.getString(stringResource), length).show()
}