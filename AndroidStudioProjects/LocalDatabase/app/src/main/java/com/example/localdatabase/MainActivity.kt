package com.example.localdatabase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
//import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val DATABASE_NAME = "babynames"
    private val START_YEAR = 1880
    private val END_YEAR = 2010
    private val MAX_RANKE = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val graph_view = findViewById<GraphView>(R.id.graph_view)
        graph_view.title = "custom title"
		//set X and Y bounds
        graph_view.viewport.setMinX(START_YEAR.toDouble())
        graph_view.viewport.setMaxX(END_YEAR.toDouble())
        graph_view.viewport.setMinY(0.0)
        graph_view.viewport.setMaxY(MAX_RANKE.toDouble())
    }

    fun searchClick(view: View){
        doQuery()
    }

    @SuppressLint("Range")
    private fun doQuery() {
        val name = findViewById<EditText>(R.id.name_field).text.toString()
        val sex = if (findViewById<Switch>(R.id.sex_switch).isChecked) "F" else "M"

        //either create a new empty database with that name or opens an existing one
        val db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null)

        val query =
            "SELECT years, rank FROM ranks WHERE name = '$name' AND sex = '$sex' ORDER BY years"

        val cursor = db.rawQuery(query, null)
        //var sortedMap = sortedMapOf<Double,Double>()
        Log.d("Wang2","------")

        val maxPoints = 20
        val series = LineGraphSeries<DataPoint>()   //add line to the graph

        while (cursor.moveToNext()) {
            val year = cursor.getInt(cursor.getColumnIndex("years"))
            var rank = cursor.getInt(cursor.getColumnIndex("rank"))
            rank = MAX_RANKE - rank
            //sortedMap.put(year.toDouble(),rank.toDouble())
            series.appendData(DataPoint(year.toDouble(),rank.toDouble()),false,maxPoints)
        }
        cursor.close()

        val graph_view = findViewById<GraphView>(R.id.graph_view)
        graph_view.removeAllSeries()  //in case there is a previous line
        graph_view.addSeries(series)
    }

    fun createClick(view: View){
        Log.d("Wang","Clicked create")
        if(!getDatabasePath("babynames").exists()) {
            importDatabase("babynames")
        }
    }

    fun deleteClick(view: View){
        Log.d("Wang","Clicked delete")
    }

    // 纯手工导入数据
    private fun importDatabase(dbName: String){
        val db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE,null)
        val resId = resources.getIdentifier(dbName,"raw",packageName)
        val scan = Scanner(resources.openRawResource(resId))

        var sql = ""
        while (scan.hasNextLine()){
            val line = scan.nextLine()
            if (line.trim().startsWith("--"))
                continue
            sql += "$line\n"
            if (sql.trim().endsWith(";")) {
                db.execSQL(sql)
                sql = ""
            }
        }
    }

}
