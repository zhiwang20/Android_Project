package com.example.simpsongrades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    companion object {
        const val  FIREBASE_USERNAME = "John@ccny.edu"
        const val  FIREBASE_PASSWORD = "csroxx"
    }

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(FIREBASE_USERNAME, FIREBASE_PASSWORD)

    }


    fun loginClick(view: View) {
        val name = findViewById<EditText>(R.id.name).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        //look up this person's password in Firebase
        val fb: DatabaseReference = FirebaseDatabase.getInstance().reference
        val students = fb.child("simpsons/students")
        val bart : Query = students.orderByChild("name").equalTo(name)

        bart.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                //gets called when data arrives
                processData(name, password, data)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun processData(name: String, password: String, arr: DataSnapshot) {
        if (!arr.hasChildren()) {
            Toast.makeText(this, "no such user $name", Toast.LENGTH_SHORT).show()
            return
        }
        val data = arr.children.iterator().next()
        val student = data.getValue(Student::class.java)!!
        val hisName = student.name
        val hisPassword = student.password
        if (password ==hisPassword) {
            val myIntent = Intent(this, GradesActivity::class.java)   //go to GradesActivity
            myIntent.putExtra("id",123)
            myIntent.putExtra("name", name)
            startActivity(myIntent)
        } else {
            Toast.makeText(this, "Wrong! The password should have been $hisPassword", Toast.LENGTH_SHORT).show()

        }
    }
}