package app.dictionary.com.nikeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import app.dictionary.com.nikeapp.R
import app.dictionary.com.nikeapp.data.local.EntryData

class EntryRecycleViewAdapter : RecyclerView.Adapter<EntryRecycleViewAdapter.EntryViewHolder>() {
    private var data: List<EntryData> = ArrayList()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: EntryViewHolder, position: Int) {
        holder.bind(data[position])
    }


    fun setData(data: List<EntryData>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getData() : List<EntryData>{
        return data
    }

    class EntryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var definition: TextView = itemView.findViewById(R.id.definition)
        var thumbsUp: TextView = itemView.findViewById(R.id.thumbs_up)
        var thumbsDown: TextView = itemView.findViewById(R.id.thumbs_down)

        fun bind(item: EntryData) {

            definition.text = item.definition
            thumbsDown.text = item.thumbDown.toString()
            thumbsUp.text = item.thumbUp.toString()

        }
    }
}


