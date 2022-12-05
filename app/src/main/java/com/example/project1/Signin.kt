package com.example.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class Signin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val signup =findViewById<TextView>(R.id.register)
        val progress =findViewById<ProgressBar>(R.id.progressbar)
        progress.visibility = View.GONE
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
            progress.visibility= View.VISIBLE //show progress bar
            val client = AsyncHttpClient(true,80,443)
            val body = JSONObject()
            //access the details inserted by user -values from the edittexts
            //put them details inside a body of json object
            body.put("Email",email.text.toString())
            body.put("Password",password.text.toString())
            val con_body = StringEntity(body.toString())
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

                            Toast.makeText(applicationContext,"You have signed in successfully",
                                Toast.LENGTH_LONG).show()
                            val i = Intent(applicationContext,MainActivity::class.java)
                            startActivity(i)
                        } //end of if
                        else{
                            Toast.makeText(applicationContext,"Wrong credentials: Please try again "+ statusCode,
                                Toast.LENGTH_LONG).show()
                        }
                        //super.onSuccess(statusCode, headers, response)
                    } //end of onsuccess

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


                }
            )

        }

    }
}