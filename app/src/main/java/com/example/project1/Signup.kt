package com.example.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        //find the ids
        var name=findViewById<EditText>(R.id.name)
        var email =findViewById(R.id.email) as EditText
        var password =findViewById<EditText>(R.id.password)
        var confirm = findViewById<EditText>(R.id.confirm)
        var phone =findViewById<EditText>(R.id.phone)
        var signup =findViewById<Button>(R.id.signup)
        var progress =findViewById<ProgressBar>(R.id.progress)
        //hide the progress bar
        progress.visibility= View.GONE //on opening the signup hide progress bar
        //justpaste.it/9eiki
        //loopj -library
        signup.setOnClickListener {
            //start the progress bar
            progress.visibility=View.VISIBLE //show progress bar
//             val client =AsyncHttpClient(true,80,443)
//             val body =JSONObject()
//             //access the details inserted by user -values from the edittexts
//             //put them details inside a body of json object
//             body.put("Name",name.text.toString())
//             body.put("Email",email.text.toString())
//             body.put("Password",password.text.toString())
//             body.put("Confirm",confirm.text.toString())
//             body.put("Phone",phone.text.toString())

//             val con_body =StringEntity(body.toString())
//             // https://musau.pythonanywhere.com/signup
//             client.post(this,"https://musau.pythonanywhere.com/signup",con_body,
//                 "application/json",
//                 object : JsonHttpResponseHandler() {
//                     //create a function for onsuccess
//                     override fun onSuccess(
//                         statusCode: Int,
//                         headers: Array<out Header>?,
//                         response: JSONObject?
//                     ) {

//                         //check if status code is success (200)
//                         if (statusCode == 201){
//                             Toast.makeText(applicationContext,"You have been successfully registered" +statusCode,
//                                 Toast.LENGTH_LONG).show()
//                             val i =Intent(applicationContext,Signin::class.java)
//                             startActivity(i)
//                             finish()
//                         } //end of if
//                         else{
//                             Toast.makeText(applicationContext,"Please try again "+ statusCode,Toast.LENGTH_LONG).show()
//                         }
//                         //super.onSuccess(statusCode, headers, response)
//                     } //end of onsuccess
// //https://github.com/maxmusau/Navigation_Drawer/tree/master
//                     override fun onFailure(
//                         statusCode: Int,
//                         headers: Array<out Header>?,
//                         throwable: Throwable?,
//                         errorResponse: JSONObject?
//                     ) {
//                         progress.visibility =View.GONE
//                         Toast.makeText(applicationContext,"Something went wrong from the Application side"
//                                 + " " + statusCode,
//                         Toast.LENGTH_LONG).show()

//                         //super.onFailure(statusCode, headers, throwable, errorResponse)
//                     }

// // https://github.com/maxmusau/Project1.git
//                 }
//             )

//         }
//echo "# Project1" >> README.md
//git init
//git add README.md
//git commit -m "first commit"
//git branch -M main
//git remote add origin https://github.com/maxmusau/Project1.git
//git push -u origin main
//git remote add origin https://github.com/maxmusau/Project1.git
//git branch -M main
//git push -u origin main

    }
}
