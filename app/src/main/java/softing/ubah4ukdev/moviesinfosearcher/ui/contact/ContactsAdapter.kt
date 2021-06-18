package softing.ubah4ukdev.moviesinfosearcher.ui.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.domain.model.Contact

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui.contact

Created by Ivan Sheynmaer

2021.06.18
v1.0
 */
class ContactsAdapter : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(
    object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem == newItem
    }
) {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        (holder.itemView as TextView).text = getItem(position).displayName
    }
}