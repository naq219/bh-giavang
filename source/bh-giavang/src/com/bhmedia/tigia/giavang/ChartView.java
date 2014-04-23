package com.bhmedia.tigia.giavang;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bhmedia.tigia.object.BieuDoOj;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

public class ChartView extends View {
	Paint paintBan = new Paint();
	Paint paintMua ;
	Paint paintLine=new Paint();
	Canvas canvas;
	float width;
	float height;
	int eventX1;
	//int eventY1;
	Idelegate idelegate;
	int numCol = 1;
	Float[] arrMua;
	Float[] arrBan;
	float maxMua=0;
	float maxBan=0;
	float minMua=-1;
	float minBan=-1;
	ArrayList<BaseObject> ojs = new ArrayList<BaseObject>();
	float widCol;

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paintBan.setStrokeWidth(1f);
		paintBan.setColor(Color.RED);
		
		paintLine.setStrokeWidth(1f);
		paintLine.setColor(Color.BLACK);
		canvas = new Canvas();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// canvas.drawLine(10, 10, 10, 60, paint);
		// canvas.drawLine(11, 11, 11, 70, paint);
		
		if(arrBan==null||arrMua==null) return; // chua co du lieu
		
		
		//float startX=0;
		for (int i = 0; i < arrBan.length; i++) {
			
			canvas.drawLine(i*widCol, height-arrBan[i], i*widCol, height, paintBan);
			canvas.drawLine(i*widCol, height-arrMua[i], i*widCol, height, paintMua);
			
			//startX=startX+widCol;
			
		}
		
		
		canvas.drawLine(eventX1, 0, eventX1 , height, paintLine);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		eventX1 = (int) event.getX();
		//eventY1 = (int) event.getY();
		
		int getPosition= (int) (eventX1/widCol);
		Mlog.T("getPosition  "+getPosition);
		if(getPosition>=0&&getPosition<ojs.size())
		idelegate.callBack(ojs.get(getPosition), 1);
		
		invalidate();

		return true;

	}

	public void setListtener(Idelegate idelegate) {
		this.idelegate = idelegate;
	}

	public void setData(ArrayList<BaseObject> ojs) {
		
		eventX1= (int) (width/2);
		this.ojs = ojs;
		if(ojs==null||ojs.size()==0)
			return;
			
			//reset thong so
			numCol = ojs.size();
			arrBan=new Float[numCol];
			arrMua=new Float[numCol];
			 maxMua=0;
			 maxBan=0;
			 minMua=-1;
			 minBan=-1;
			
		
		for (int i = 0; i < ojs.size(); i++) { // gan du lieu that 
			try {
				arrBan[i]= Float.parseFloat(ojs.get(i).get(BieuDoOj.SALE));
				arrMua[i]= Float.parseFloat(ojs.get(i).get(BieuDoOj.BUY));
				if(minBan<0)minBan=arrBan[i];
				if(minMua<0)minMua=arrMua[i];
				
				if(arrBan[i]>maxBan)maxBan=arrBan[i];
				if(arrMua[i]>maxMua)maxMua=arrMua[i];
				
				if(arrBan[i]<minBan)minBan=arrBan[i];
				if(arrMua[i]<minMua)minMua=arrMua[i];
				
			} catch (Exception e) {
				
				Mlog.E("CharView -setData ");
			}
		}
		
		float inchPerval= height/maxBan;
		float inchPervalMua= (height*0.7f)/maxBan;
		
		for (int i = 0; i < arrBan.length; i++) { // chuyen sang du lieu pixcel
			
			
			arrBan[i]=arrBan[i]*inchPerval;
			arrMua[i]=arrMua[i]*inchPervalMua;
		}
		
		
		setPaintWidth();
		
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int desiredWidth = 100;
		int desiredHeight = 100;

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			width = Math.min(desiredWidth, widthSize);
		} else {
			width = desiredWidth;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else if (heightMode == MeasureSpec.AT_MOST) {
			height = Math.min(desiredHeight, heightSize);
		} else {
			height = desiredHeight;
		}

		setPaintWidth();

		invalidate();

		setMeasuredDimension((int)width, (int)height);
	}

	private void setPaintWidth() {
		widCol = width / numCol;
		paintLine.setStrokeWidth(widCol);
		paintBan.setStrokeWidth(widCol);
		
		paintMua=new Paint(paintBan);
		paintMua.setColor(Color.CYAN);
	}

}
