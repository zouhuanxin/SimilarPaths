package com.example.similarpaths

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.similarpaths.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var paintview1: PaintView? = null
    private var paintview2: PaintView? = null
    private var b2: Button? = null
    private var x1: DoubleArray? = null
    private  var y1:kotlin.DoubleArray? = null
    private  var x2:kotlin.DoubleArray? = null
    private  var y2:kotlin.DoubleArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        paintview1 = findViewById<View>(R.id.paintview1) as PaintView
        paintview2 = findViewById<View>(R.id.paintview2) as PaintView
        b2 = findViewById<View>(R.id.b2) as Button
        b2!!.setOnClickListener(View.OnClickListener { Verify() })
    }

    open external fun init(
        x1: DoubleArray?,
        y1: DoubleArray?,
        x2: DoubleArray?,
        y2: DoubleArray?
    ): Double


    private fun Verify() {
        try {
            val array1 = JSONArray(paintview1!!.getCoordinates())
            x1 = DoubleArray(array1.length())
            y1 = DoubleArray(array1.length())
            for (i in 0 until array1.length()) {
                val s: String = array1.getString(i).replace("[", "").replace("]", "")
                val t = s.split(",").toTypedArray()
                x1!![i] = t[0].toDouble()
                y1!![i] = t[1].toDouble()
            }
            val array2 = JSONArray(paintview2!!.getCoordinates())
            x2 = DoubleArray(array2.length())
            y2 = DoubleArray(array2.length())
            for (i in 0 until array2.length()) {
                val s: String = array2.getString(i).replace("[", "").replace("]", "")
                val t = s.split(",").toTypedArray()
                x2!![i] = t[0].toDouble()
                y2!![i] = t[1].toDouble()
            }
            val res: Double = init(x1, y1, x2, y2)
            Toast.makeText(this@MainActivity, "轨迹相似度:$res", Toast.LENGTH_SHORT).show()
        } catch (e: JSONException) {
            println("解析错误")
            e.printStackTrace()
        }
    }

    companion object {
        init {
            System.loadLibrary("similarpaths")
        }
    }
}