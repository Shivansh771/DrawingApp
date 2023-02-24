package com.example.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs:AttributeSet) : View(context, attrs){
// when we want to draw something it should be of type view
        private var mDrawPath: CustomPath?= null
        private var mCanvasBitmap:Bitmap?=null
        private var mDrawPaint: Paint?=null
        private var mCanvasPaint:Paint?=null
        private var mBrushSize :Float=0.toFloat()
        private var color=Color.BLACK
        private var canvas: Canvas?=null
        private val mPaths=ArrayList<CustomPath>()
        init{
            setUpDrawing()
        }
    private fun setUpDrawing(){
        mDrawPaint=Paint()
        mDrawPath=CustomPath(color,mBrushSize)
        mDrawPaint!!.color=color
        mDrawPaint!!.style=Paint.Style.STROKE //there are multiple styles like FILL, Fill and stroke, Stroke
        mDrawPaint!!.strokeJoin=Paint.Join.ROUND
    //how the begining and ending of stroke should be
        mDrawPaint!!.strokeCap=Paint.Cap.ROUND
        //cap sets the paint's line cap style, used whenever teh paint's style is Stroke or StrokeFill
        mCanvasPaint=Paint(Paint.DITHER_FLAG) //shaking enable
        //mBrushSize=20.toFloat()


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888) //ARGB_8888 each pixel is stored in 4 bytes. each channel(RGB and alpha for translucency) is stored with 8 bits of precision(256 possible values)
        canvas=Canvas(mCanvasBitmap!!)
    }
    //change canvas to canvas?? if fails
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)

        for(path in mPaths){
            mDrawPaint!!.strokeWidth=path.brushThickness
            mDrawPaint!!.color=path!!.color
            canvas.drawPath(path,mDrawPaint!!)
        }
        if(!mDrawPath!!.isEmpty){
            mDrawPaint!!.strokeWidth=mDrawPath!!.brushThickness
            mDrawPaint!!.color=mDrawPath!!.color
            canvas.drawPath(mDrawPath!!,mDrawPaint!!)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX=event?.x
        val touchY=event?.y

        when(event?.action){
            //MotionEvent is a class that takes care of motion events
            //MotionEvent controls three action down>> when we get our finger on the screen, action move >>when we move our finger on the screen and action up>> when we lift our finger from the screen
            MotionEvent.ACTION_DOWN->{
                // -> is a lamda expression
                mDrawPath!!.color=color
                mDrawPath!!.brushThickness=mBrushSize
                mDrawPath!!.reset()
                mDrawPath!!.moveTo(touchX!!,touchY!!)
            }
            MotionEvent.ACTION_MOVE->{
                mDrawPath!!.lineTo(touchX!!,touchY!!)
            }
            MotionEvent.ACTION_UP->{
                mPaths.add(mDrawPath!!)
                mDrawPath=CustomPath(color,mBrushSize)
            }
            else->return false
        }

        invalidate() //invalidate the whole view if the view is visible
        return true
    }

    fun setSizeForBrush(newSize:Float){
        mBrushSize=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,newSize,resources.displayMetrics)  //displayMetrics gives the dimensions of the display in which it is being used
        mDrawPaint!!.strokeWidth=mBrushSize
    }
    internal inner class CustomPath(var color:Int,var brushThickness:Float) : Path(){

    }


}