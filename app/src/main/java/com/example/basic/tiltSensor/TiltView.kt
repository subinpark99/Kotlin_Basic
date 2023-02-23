package com.example.basic.tiltSensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context) : View(context) {

    private val greenPaint:Paint= Paint()
    private val blackPaint:Paint= Paint()

    private var cX:Float=0f
    private var cY:Float=0f
    private var xCoord:Float=0f
    private var yCoord:Float=0f

    init {
        greenPaint.color=Color.GREEN
        blackPaint.style=Paint.Style.STROKE  // 검은색 테두리
    }

    fun onSensorEvent(event:SensorEvent){

        // 화면이 가로 모드이기 때문에 x축과 y축을 서로 바꿈
        xCoord=event.values[0] * 20
        yCoord=event.values[1] * 20

        invalidate()  // onDraw 다시 호출
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {  // 중점 좌표 계산
        cX=w/2f
        cY=h/2f
    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.drawCircle(cX,cY,100f,blackPaint)
        canvas?.drawCircle(xCoord+cX,yCoord+cY,100f,greenPaint)
        canvas?.drawLine(cX-20,cY,cX+20,cY,blackPaint)  // 가운데 십자가
        canvas?.drawLine(cX,cY-20,cX,cY+20,blackPaint)
    }

}