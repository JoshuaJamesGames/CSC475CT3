package com.example.csc475ct3
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val dataSet: MutableMap<String, *>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var listener: OnContactClickListener? = null
    fun setOnButtonClickListener(listener: OnContactClickListener){
        this.listener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvContactName: TextView = view.findViewById(R.id.tvContactName)
        val tvContactPhone: TextView = view.findViewById(R.id.tvContactPhone)
        val tvContactEmail: TextView = view.findViewById(R.id.tvContactEmail)

        val btnEditContact: Button = view.findViewById(R.id.btnEditContact)
        val btnDeleteContact: Button = view.findViewById(R.id.btnDeleteContact)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.contact_row, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val dataSetKeys = dataSet.keys.toTypedArray()
        val dataSetKey = dataSetKeys[position]
        val contactValues =  dataSet[dataSetKey].toString().substring(1,dataSet[dataSetKey].toString().length-1).split(", ").sorted()
        val contactPhone = contactValues[1].split("Phone: ").toString().substring(3, contactValues[1].length-4)
        val contactEmail = contactValues[0].split("Email: ").toString().substring(3, contactValues[0].length-4)

        viewHolder.tvContactName.text = dataSetKey
        viewHolder.tvContactPhone.text = contactPhone
        viewHolder.tvContactEmail.text = contactEmail

        viewHolder.btnDeleteContact.setOnClickListener {
            listener?.onButtonDeleteClick(dataSetKey)
        }
        viewHolder.btnEditContact.setOnClickListener {
            listener?.onButtonEditClick(dataSetKey,contactPhone,contactEmail)
        }


    }

    override fun getItemCount() = dataSet.size

    interface OnContactClickListener{
        fun onButtonDeleteClick (dataSetKey: String)
        fun onButtonEditClick(dataSetKey: String, contactPhone: String, contactEmail: String)
    }
}

