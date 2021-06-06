package softing.ubah4ukdev.moviesinfosearcher.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import softing.ubah4ukdev.moviesinfosearcher.R

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui

Created by Ivan Sheynmaer

2021.06.02
v1.0
 */
class ConnectivityActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, context.getString(R.string.connectivity_action), Toast.LENGTH_SHORT)
            .show()
    }
}