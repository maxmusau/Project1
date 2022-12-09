package com.example.project1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
class SecondRecyclerAdapter(var context: Context):
RecyclerView.Adapter<SecondRecyclerAdapter.ViewHolder>() {
    var ReservationList : List<Reservations> = listOf() // empty  list

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.second_single_item, parent, false)
        return ViewHolder(view)
    }
    //so far item view is same as single item
    override fun onBindViewHolder(holder: SecondRecyclerAdapter.ViewHolder, position: Int) {
        val id = holder.itemView.findViewById(R.id.room_id) as TextView
        val name = holder.itemView.findViewById(R.id.name) as TextView
        val room_name = holder.itemView.findViewById(R.id.room_name) as TextView
        val email = holder.itemView.findViewById(R.id.email) as TextView
        val persons = holder.itemView.findViewById(R.id.num_of_persons) as TextView
        val date = holder.itemView.findViewById(R.id.date) as TextView
        val start_time = holder.itemView.findViewById(R.id.start_time) as TextView
        val end_time = holder.itemView.findViewById(R.id.end_time) as TextView
        val cost = holder.itemView.findViewById(R.id.cost) as TextView
        val image = holder.itemView.findViewById(R.id.image_url) as ImageView

        //bind
        val item = ReservationList[position]
        id.text=item.room_id
        name.text = item.name
        room_name.text = item.room_name
        persons.text=item.num_of_persons
        email.text=item.email
        cost.text = item.cost
        date.text = item.date
        start_time.text = item.start_time
        end_time.text = item.end_time
        //
        Glide.with(context).load(item.image_url)
            .apply(RequestOptions().centerCrop())
            .into(image)

    }
    override fun getItemCount(): Int { //count the number items coming from the API
        return ReservationList.size
    }
    //we will call this function on Loopj response
    fun setProductListItems(ReservationList: List<Reservations>){
        this.ReservationList = ReservationList
        notifyDataSetChanged()
    }

}

