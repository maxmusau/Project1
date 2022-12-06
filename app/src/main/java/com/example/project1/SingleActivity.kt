package com.example.project1
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class SingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        //access shared prefferences
        val prefs: SharedPreferences = getSharedPreferences("store",
            Context.MODE_PRIVATE)

        //access the saved product_name from prefferences and put in the TextView
        val id = prefs.getString("room_id", "")
        val room_id = findViewById(R.id.room_id) as TextView
        room_id.text = id

        //access the saved product_desc from prefferences and put in the TextView
        val name = prefs.getString("room_name", "")
        val room_name = findViewById(R.id.room_name) as TextView
        room_name.text = name

        //access the saved product_cost from prefferences and put in the TextView
        val desc = prefs.getString("room_desc", "")
        val room_desc= findViewById(R.id.room_desc) as TextView
        room_desc.text = desc

        val persons = prefs.getString("num_of_persons", "")
        val num_persons= findViewById(R.id.num_of_persons) as TextView
        num_persons.text = persons

        val availability = prefs.getString("availability", "")
        val status= findViewById(R.id.availability) as TextView
        status.text = availability

        val cost = prefs.getString("cost", "")
        val room_cost= findViewById(R.id.cost) as TextView
        room_cost.text = cost

        //access the saved image from prefferences and put in the ImageView Using Glide
        val image_url = prefs.getString("image_url", "")
        val image = findViewById(R.id.image_url) as ImageView
        Glide.with(applicationContext).load(image_url)
            .apply(RequestOptions().centerCrop())
            .into(image)

        //find ids for making reservation
        val user_id=findViewById<EditText>(R.id.user_id)
        val p_name=findViewById<EditText>(R.id.name)
        val email=findViewById<EditText>(R.id.email)
        val phone=findViewById<EditText>(R.id.phone)
        val date=findViewById<EditText>(R.id.date)
        val start_time=findViewById<EditText>(R.id.start_time)
        val end_time=findViewById<EditText>(R.id.end_time)
        val make_reservation=findViewById<Button>(R.id.make_reservation)
        var progress =findViewById<ProgressBar>(R.id.progressbar)
        //hide the progress bar
        progress.visibility= View.GONE //on opening the signup hide progress bar
        //justpaste.it/9eiki
        //loopj -library
        make_reservation.setOnClickListener {
            //start the progress bar
            progress.visibility= View.VISIBLE //show progress bar
            val client = AsyncHttpClient(true,80,443)
            val body = JSONObject()
            //access the details inserted by user -values from the edittexts
            //put them details inside a body of json object
            body.put("user_id",user_id.text.toString())
            body.put("name",p_name.text.toString())
            body.put("email",email.text.toString())
            body.put("phone",phone.text.toString())
            body.put("date",date.text.toString())
            body.put("start_time",start_time.text.toString())
            body.put("end_time",end_time.text.toString())

            val con_body = StringEntity(body.toString())
            // https://musau.pythonanywhere.com/reservation
            client.post(this,"https://musau.pythonanywhere.com/reservation",con_body,
                "application/json",
                object : JsonHttpResponseHandler() {
                    //create a function for onsuccess
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {

                        //check if status code is success (200)
                        if (statusCode == 202){
                            progress.visibility=View.GONE
                            Toast.makeText(applicationContext,"You have successfuly made one reservation" +statusCode,
                                Toast.LENGTH_LONG).show()

//                            val i = Intent(applicationContext,Signin::class.java)
//                            startActivity(i)
//                            finish()
                        } //end of if
                        else{
                            Toast.makeText(applicationContext,"Please try again "+ statusCode,
                                Toast.LENGTH_LONG).show()
                        }
                        //super.onSuccess(statusCode, headers, response)
                    } //end of onsuccess
                    //https://github.com/maxmusau/Navigation_Drawer/tree/master
                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        throwable: Throwable?,
                        errorResponse: JSONObject?
                    ) {
                        progress.visibility = View.GONE
                        Toast.makeText(applicationContext,"Something went wrong from the Application side"
                                + " " + statusCode,
                            Toast.LENGTH_LONG).show()

                        //super.onFailure(statusCode, headers, throwable, errorResponse)
                    }

// https://github.com/maxmusau/Project1.git
                }
            )

        }

    }
}