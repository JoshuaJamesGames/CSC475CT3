package com.example.csc475ct3
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ContactListFragment: Fragment() {

    fun replace(fragment: Fragment) {

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()

    }

    private fun getContacts(view: View){
        //Load Data from shared preferences
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val contactNames = sharedPref.all

        //Create a scrolling list with RecyclerView
        val customAdapter = ContactAdapter(contactNames)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvContacts)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = customAdapter

        customAdapter.setOnButtonClickListener(object: ContactAdapter.OnContactClickListener{
            override fun onButtonDeleteClick(dataSetKey: String) {
                sharedPref.edit().remove(dataSetKey).apply()
                replace(fragment = ContactListFragment())
            }
            override fun onButtonEditClick(dataSetKey: String,contactPhone: String, contactEmail: String) {

                val bundle = Bundle()
                bundle.putString("contactName", dataSetKey)
                bundle.putString("contactPhone", contactPhone)
                bundle.putString("contactEmail", contactEmail)
                val fragment = AddContactFragment()
                fragment.arguments = bundle
                replace(fragment)

            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.contact_list, container,false)
        getContacts(view)
        return view
    }
}