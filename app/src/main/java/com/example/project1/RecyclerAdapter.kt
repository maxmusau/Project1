package com.example.project1
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide import com.bumptech.glide.request.RequestOptions

class RecyclerAdapter(var context: Context):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    var conferenceRoom : List<Conference_Room> = listOf() // empty  list

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }
    //so far item view is same as single item
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val id = holder.itemView.findViewById(R.id.room_id) as TextView
        val name = holder.itemView.findViewById(R.id.room_name) as TextView
        val desc = holder.itemView.findViewById(R.id.room_desc) as TextView
        val persons = holder.itemView.findViewById(R.id.num_of_persons) as TextView
        val availability = holder.itemView.findViewById(R.id.availability) as TextView
        val cost = holder.itemView.findViewById(R.id.cost) as TextView
        val image = holder.itemView.findViewById(R.id.image_url) as ImageView

        //bind
        val item = conferenceRoom[position]
        id.text=item.room_id
        name.text = item.room_name
        desc.text = item.room_desc
        persons.text=item.num_of_persons
        availability.text=item.availability
        cost.text = item.cost
    //
        Glide.with(context).load(item.image_url)
            .apply(RequestOptions().centerCrop())
            .into(image)

        //image.setImageResource(item.image)

        holder.itemView.setOnClickListener {
            //create a shared prefferences variable to store our clicked product
            val prefs: SharedPreferences = context.getSharedPreferences(
                "store",
                Context.MODE_PRIVATE
            )
            //save the product
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("room_id", item.room_id)
            editor.putString("room_name", item.room_name)
            editor.putString("room_desc", item.room_desc)
            editor.putString("num_of_persons", item.num_of_persons)
            editor.putString("availability", item.availability)
            editor.putString("cost", item.cost)
            editor.putString("image_url", item.image_url)
            editor.apply()

            //Navigate to SingleACtivity, Created Earlier
            val i = Intent(context, SingleActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }

    }
    override fun getItemCount(): Int { //count the number items coming from the API
        return conferenceRoom.size
    }
    //we will call this function on Loopj response
    fun setProductListItems(conferenceRoom: List<Conference_Room>){
        this.conferenceRoom = conferenceRoom
        notifyDataSetChanged()
    }

    }

    //View holder holds the views in single item.xml