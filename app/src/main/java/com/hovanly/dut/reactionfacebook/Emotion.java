package com.hovanly.dut.reactionfacebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 14/07/2017
 */
class Emotion {
    public static final int MINIMAL_SIZE = DisplayUtils.dpToPx(28);
    public static final int NORMAL_SIZE = DisplayUtils.dpToPx(40);
    public static final int CHOOSE_SIZE = DisplayUtils.dpToPx(100);
    public static final int DISTANCE = DisplayUtils.dpToPx(15);
    public static final int MAX_WIDTH_TITLE = DisplayUtils.dpToPx(70);
    public int currentSize = NORMAL_SIZE;
    private Context mContext;
    public int beginSize;

    public int endSize;

    public float currentX;

    public float currentY;

    public float beginY;

    public float endY;

    public Bitmap imageEmotion;

    public Bitmap imageTitle;

    public Paint emotionPaint;

    public Paint titlePaint;

    public float ratioWH;
    public Emotion(Context context, String title, int imageResource) {
        mContext = context;
        imageEmotion = BitmapFactory.decodeResource(context.getResources(), imageResource);
        emotionPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        emotionPaint.setAntiAlias(true);
        titlePaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        titlePaint.setAntiAlias(true);
        generateTitleView(title);
    }

    private void generateTitleView(String title) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View titleView = inflater.inflate(R.layout.title_view, null);
        ((TextView) titleView).setText(title);

        int w = (int) getContext().getResources().getDimension(R.dimen.width_title);
        int h = (int) getContext().getResources().getDimension(R.dimen.height_title);
        ratioWH = (w * 1.0f) / (h * 1.0f);
        imageTitle = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(imageTitle);
        titleView.layout(0, 0, w, h);
        ((TextView) titleView).getPaint().setAntiAlias(true);
        titleView.draw(c);
    }

    public void setAlphaTitle(int alpha) {
        titlePaint.setAlpha(alpha);
    }

    public void drawEmotion(Canvas canvas) {
        canvas.drawBitmap(imageEmotion, null, new RectF(currentX, currentY, currentX + currentSize, currentY + currentSize), emotionPaint);
        drawTitle(canvas);
    }

    public void drawTitle(Canvas canvas) {
        int width = (currentSize - NORMAL_SIZE) * 7 / 6;
        int height = (int) (width / ratioWH);

        setAlphaTitle(Math.min(CommonDimen.MAX_ALPHA * width / MAX_WIDTH_TITLE, CommonDimen.MAX_ALPHA));

        if (width <= 0 || height <= 0) {
            return;
        }

        float x = currentX + (currentSize - width) / 2;
        float y = currentY - DISTANCE - height;

        canvas.drawBitmap(imageTitle, null, new RectF(x, y, x + width, y + height), titlePaint);
    }

    public Context getContext() {
        return mContext;
    }
}
