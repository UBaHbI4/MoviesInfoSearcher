package softing.ubah4ukdev.moviesinfosearcher.ui.contact;

import android.database.Cursor
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import softing.ubah4ukdev.moviesinfosearcher.R

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui.contact

Created by Ivan Sheynmaer

2021.06.18
v1.0
 */
class ContactsCursorAdapter : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private lateinit var cursor: Cursor

    fun setCursor(cursorToSet: Cursor) {
        if (::cursor.isInitialized && cursor != cursorToSet) {
            cursor.close()
        }
        cursor = cursorToSet

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsAdapter.ContactViewHolder =
        ContactsAdapter.ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )

    override fun onBindViewHolder(holder: ContactsAdapter.ContactViewHolder, position: Int) {
        cursor.moveToPosition(position)

        val displayName =
            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

        (holder.itemView as TextView).text = displayName
    }

    override fun getItemCount(): Int = cursor.count

    fun close() {
        if (::cursor.isInitialized && cursor.isClosed.not()) {
            cursor.close()
        }
    }
}
