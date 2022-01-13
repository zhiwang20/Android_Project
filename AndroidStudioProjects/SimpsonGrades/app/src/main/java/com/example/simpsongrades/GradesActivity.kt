package com.example.simpsongrades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.simpsongrades.Grade
import com.google.firebase.database.*

class GradesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grades)

        //extract parameters from intent
        val intent = intent
        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")
        findViewById<TextView>(R.id.heading).text = "$name's Grades"

        //look up grade for this student
        //look up this person's password in Firebase
        val fb: DatabaseReference = FirebaseDatabase.getInstance().reference
        val grades = fb.child("simpsons/grades")
        val bartGrades : Query = grades.orderByChild("student_id").equalTo(id.toDouble())

        bartGrades.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                //gets called when data arrives
                processData(data)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun processData(data: DataSnapshot) {
        val list = ArrayList<String>()
        for (child in data.children) {
            val grade = child.getValue(Grade::class.java)!! //use Grade class
            list.add(grade.grade + " in " + grade.course_name)
        }
        findViewById<ListView>(R.id.gradelist).adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)

    }
}