package softing.ubah4ukdev.moviesinfosearcher.ui.contact

import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.fragment.app.Fragment
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.databinding.FragmentContactBinding
import softing.ubah4ukdev.moviesinfosearcher.viewBinding

class ContactsFragment : Fragment(R.layout.fragment_contact) {

    private val viewBinding: FragmentContactBinding by viewBinding(
        FragmentContactBinding::bind
    )

    private val adapter = ContactsCursorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentResolver: ContentResolver = requireContext().contentResolver

        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " DESC"
        )

        val safeCursor = cursor ?: return
        adapter.setCursor(safeCursor)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.contactsList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()

        adapter.close()
    }
}