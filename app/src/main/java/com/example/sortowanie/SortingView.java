package com.example.sortowanie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

public class SortingView extends View {

    private List<Integer> info;
    private Paint kolor;

    public SortingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        kolor = new Paint();
        kolor.setColor(0xFF00796B);
    }

    public void ustaw(List<Integer> info) {
        this.info = info;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (info != null && !info.isEmpty()) {
            int width = getWidth();
            int height = getHeight();
            int barWidth = width / info.size();
            int maxValue = 100;

            for (int i = 0; i < info.size(); i++) {
                int rozmiar = (int) ((info.get(i) / (float) maxValue) * height);
                canvas.drawRect(i * barWidth, height - rozmiar, (i + 1) * barWidth, height, kolor);
            }
        }
    }
}
