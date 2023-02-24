package com.example.drawingapp

import android.app.Dialog
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private var drawingView:DrawingView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView=findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())
        val ib_brush:ImageButton=findViewById(R.id.ib_sel)
        val smallBtn:ImageButton=ib_brush.findViewById(R.id.ib_small_brush)
        val mediumBtn:ImageButton=ib_brush.findViewById(R.id.ib_medium_brush)
        val largeBrush:ImageButton=ib_brush.findViewById(R.id.imageButton)
        ib_brush.setOnClickListener{
            showBrushSizeChooseDialog()
        }
    }


    private fun showBrushSizeChooseDialog(){
        val brushDialog= Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size:")

        brushDialog.show()

    }
}