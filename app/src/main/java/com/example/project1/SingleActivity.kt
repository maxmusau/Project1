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
        val view=findViewById<Button>(R.id.view_reservations)
        view.setOnClickListener {
            val i = Intent(applicationContext,Welcome::class.java)
            startActivity(i)
        }
        //mpesa integration
        val amount=findViewById<EditText>(R.id.amount)
        val iphone=findViewById<EditText>(R.id.phone)
        val reserve=findViewById<Button>(R.id.make_reservation)
        amount.visibility=View.GONE
        iphone.visibility=View.GONE
        reserve.setOnClickListener {
            amount.visibility=View.VISIBLE
            iphone.visibility=View.VISIBLE
            val client = AsyncHttpClient(true,80,443)
            val body= JSONObject()
//
            body.put("amount",amount.text.toString())
            body.put("phone",iphone.text.toString())
            val con_body= StringEntity(body.toString())
            client.post(this,"https://musau.pythonanywhere.com/mpesa_payment",con_body,
                "application/json",

                object : JsonHttpResponseHandler() {

                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        println("printing after accessing onsuccess")
                        if (statusCode ==200){
                            Toast.makeText(applicationContext,"Please Confirm booking through mpesa pin",
                                Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(applicationContext,"Mpesa payment not successful $statusCode",
                                Toast.LENGTH_LONG).show()
                        }
                        //super.onSuccess(statusCode, headers, response)
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        throwable: Throwable?,
                        errorResponse: JSONObject?
                    ) {
                        println("on failure")
                        Toast.makeText(applicationContext,"Something Went wrong $statusCode", Toast.LENGTH_LONG).show()
                    }

                }
            )
        }
        //https://github.com/maxmusau/Project1
        //find ids for making reservation
        val user_id=findViewById<EditText>(R.id.user_id)
        val p_name=findViewById<EditText>(R.id.name)
        val email=findViewById<EditText>(R.id.email)
        val phone=findViewById<EditText>(R.id.iphone)
        val date=findViewById<EditText>(R.id.date)
        val start_time=findViewById<EditText>(R.id.start_time)
        val end_time=findViewById<EditText>(R.id.end_time)
        val R_id=findViewById<EditText>(R.id.R_id)
        val make_reservation=findViewById<Button>(R.id.make_reservation)
        var progress =findViewById<ProgressBar>(R.id.progressbar)
        //hide the progress bar
        progress.visibility= View.GONE

        make_reservation.setOnClickListener {
            progress.visibility=View.VISIBLE
            println("onclick")
            val client =AsyncHttpClient(true,80,443)
            val body=JSONObject()
//             user_id = json['user_id']
//    name = json['name']
//    email = json['email']
//    phone = json['phone']
//    date = json['date']
//    start_time = json['start_time']
//    end_time = json['end_time']
//    room_id=json['room_id']
//sql querry  sql ="SELECT A.name,A.email,A.date,A.start_time,A.end_time,B.room_id,B.room_name,B.cost,B.num_of_persons,B.image_url FROM reservation_details AS A  INNER JOIN conference_room as B   ON A.fk_room_id=B.room_id"
            body.put("user_id", user_id.text.toString())
            body.put("name", p_name.text.toString())
            body.put("email",email.text.toString())
            body.put("phone",phone.text.toString())
            body.put("date",date.text.toString())
            body.put("start_time",start_time.text.toString())
            body.put("end_time",end_time.text.toString())
            body.put("room_id",R_id.text.toString())
            val con_body=StringEntity(body.toString())
            println("Reached here, before the api")
            client.post(this,"https://musau.pythonanywhere.com/reservation",con_body,
                "application/json",

            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    println("printing after accessing onsuccess")
                    if (statusCode ==202){
                        progress.visibility =View.GONE
                        Toast.makeText(applicationContext,"Reservation successful",Toast.LENGTH_LONG).show()
                    }
                    else{
                        progress.visibility=View.GONE
                        Toast.makeText(applicationContext,"Reservation not successful $statusCode",Toast.LENGTH_LONG).show()
                    }
                    //super.onSuccess(statusCode, headers, response)
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    println("on failure")
                    progress.visibility=View.GONE
                    Toast.makeText(applicationContext,"Failed try again $statusCode",Toast.LENGTH_LONG).show()
                }

            }
            )
        }





















//        val reserve=findViewById<Button>(R.id.reserve)
//        reserve.setOnClickListener {
//            val i=Intent(applicationContext,Welcome::class.java)
//            startActivity(i)
//
//        }



    }
}