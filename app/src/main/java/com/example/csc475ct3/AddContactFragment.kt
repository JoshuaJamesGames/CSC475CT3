package com.example.csc475ct3
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AddContactFragment : Fragment() {

    private fun saveContact(fragment: Fragment) {

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {

            val newContactName = activity?.findViewById<EditText>(R.id.etName)?.text.toString()
            val newContactPhone = activity?.findViewById<EditText>(R.id.etPhone)?.text.toString()
            val newContactEmail = activity?.findViewById<EditText>(R.id.etEmail)?.text.toString()
            if(newContactName.isNotEmpty()) {
                putStringSet(
                    newContactName,
                    setOf("Phone: $newContactPhone", "Email: $newContactEmail")
                )
            }
            apply()
        }

        activity?.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_contact, container,false)
        val btnAddContact = view.findViewById<Button>(R.id.btnAddContact)
        if(arguments?.isEmpty == false){
            view.findViewById<EditText>(R.id.etName).setText(requireArguments().getString("contactName"))
            view.findViewById<EditText>(R.id.etPhone).setText(requireArguments().getString("contactPhone"))
            view.findViewById<EditText>(R.id.etEmail).setText(requireArguments().getString("contactEmail"))
        }
        btnAddContact.setOnClickListener {
            saveContact(fragment =ContactListFragment())
        }
        return view
    }



}