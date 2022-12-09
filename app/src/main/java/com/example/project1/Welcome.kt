package com.example.project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject

class Welcome : AppCompatActivity() {
    lateinit var ReservationList:ArrayList<Reservations>
    lateinit var recyclerAdapter: SecondRecyclerAdapter //call the adapter
    lateinit var progressbar: ProgressBar
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        recyclerView = findViewById(R.id.recycler)
        progressbar= findViewById(R.id.progressbar)
        progressbar.visibility = View.VISIBLE

        val client = AsyncHttpClient(true,80,443)
        //        //pass the product list to adapter
        recyclerAdapter = SecondRecyclerAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)

        client.get(this, "https://musau.pythonanywhere.com/getreservations",
            null,
            "application/json",
            object: JsonHttpResponseHandler(){
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                    //we convert json array to a list of a given model
                    val gson = GsonBuilder().create()
                    val list = gson.fromJson(response.toString(),
                        Array<Reservations>::class.java).toList()
                    //now pass the converted list to adapter
                    recyclerAdapter.setProductListItems(list)
                    progressbar.visibility = View.GONE
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "No Reservations to display"+statusCode, Toast.LENGTH_LONG).show()
                    progressbar.visibility = View.GONE
                }
            }//end handler
        )//end post

        //now put the adapter to recycler view
        recyclerView.adapter = recyclerAdapter




    //Final project
        //1. Web application System (Flask saving records,retrieving records,signin
    //      mpesa integration, )
        //2. Data science Model that performs a specific task *(recommender system,
//            regression,classificatio,clustering
        //3. Create an mobile application

    }
}