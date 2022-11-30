package com.example.project1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var user:String
    private val sharedPrefFile = "kotlinsharedpreference"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signup =findViewById<TextView>(R.id.register)
        val progress =findViewById<ProgressBar>(R.id.progressbar)
        progress.visibility =View.GONE
        signup.setOnClickListener {
            val i = Intent(applicationContext, Signup::class.java)
            startActivity(i)
            finish() //kills the previous activity
            //find email,password
        }
            val email =findViewById<EditText>(R.id.email)
            val password =findViewById<EditText>(R.id.password)
            val signin =findViewById<Button>(R.id.login)

        signin.setOnClickListener {
                progress.visibility=View.VISIBLE //show progress bar
                val client =AsyncHttpClient(true,80,443)
                val body =JSONObject()
                //access the details inserted by user -values from the edittexts
                //put them details inside a body of json object
                body.put("Email",email.text.toString())
                body.put("Password",password.text.toString())
                val con_body =StringEntity(body.toString())
                // https://musau.pythonanywhere.com/signup
                client.post(this,"https://musau.pythonanywhere.com/signin",con_body,
                    "application/json",
                    object : JsonHttpResponseHandler() {
                        //create a function for onsuccess
                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            response: JSONObject?
                        ) {
                            //check if status code is success (200)
                            if (statusCode == 200){
//
                            fun onClick(v: View?) {
                                val value = editText.text.toString().trim { it <= ' ' }
                                val sharedPref = getSharedPreferences("myKey", MODE_PRIVATE)
                                val editor = sharedPref.edit()
                                editor.putString("value", value)
                                editor.apply()
                                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                                startActivity(intent)
                            }

                                Toast.makeText(applicationContext,"You have signed in successfully",
                                    Toast.LENGTH_LONG).show()
                                val i =Intent(applicationContext,Welcome::class.java)
                                startActivity(i)
                            } //end of if
                            else{
                                Toast.makeText(applicationContext,"Wrong credentials: Please try again "+ statusCode,Toast.LENGTH_LONG).show()
                            }
                            //super.onSuccess(statusCode, headers, response)
                        } //end of onsuccess

                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            throwable: Throwable?,
                            errorResponse: JSONObject?
                        ) {
                            progress.visibility =View.GONE
                            Toast.makeText(applicationContext,"Something went wrong from the Application side"
                                    + " " + statusCode,
                                Toast.LENGTH_LONG).show()

                            //super.onFailure(statusCode, headers, throwable, errorResponse)
                        }


                    }
                )

            }


            }


        }


//val prefs: SharedPreferences = applicationContext.getSharedPreferences(
//                                    "store",
//                                    Context.MODE_PRIVATE
//                                )
//                                val editor: SharedPreferences.Editor = prefs.edit()
//                                editor.putString("Email", user.email)
//                                editor.putString("product_desc", item.product_desc)
//                                editor.putString("product_cost", item.product_cost)
//                                editor.putString("image_url", item.image_url)
//                                editor.apply()
