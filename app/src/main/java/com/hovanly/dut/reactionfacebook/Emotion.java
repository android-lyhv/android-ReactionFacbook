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
public class Emotion {
    public static final int MINIMAL_SIZE = DisplayUtils.dpToPx(28);
    public static final int NORMAL_SIZE = DisplayUtils.dpToPx(40);
    public static final int CHOOSE_SIZE = DisplayUtils.dpToPx(100);
    public static final int DISTANCE = DisplayUtils.dpToPx(15);
    public static final int MAX_WIDTH_TITLE = DisplayUtils.dpToPx(70);
    private int currentSize = NORMAL_SIZE;
    private Context mContext;
    private int beginSize;
    private int endSize;
    private float currentX;
    private float currentY;
    private float beginY;
    private float endY;
    private Bitmap imageEmotion;
    private Bitmap imageTitle;
    private Paint emotionPaint;
    private Paint titlePaint;
    private float ratioWH;

    Emotion(Context context, String title, int imageResource) {
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

    private void setAlphaTitle(int alpha) {
        titlePaint.setAlpha(alpha);
    }

    public void drawEmotion(Canvas canvas) {
        canvas.drawBitmap(imageEmotion, null, new RectF(currentX, currentY, currentX + currentSize, currentY + currentSize), emotionPaint);
        drawTitle(canvas);
    }

    private void drawTitle(Canvas canvas) {
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

    private Context getContext() {
        return mContext;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public int getBeginSize() {
        return beginSize;
    }

    public void setBeginSize(int beginSize) {
        this.beginSize = beginSize;
    }

    public int getEndSize() {
        return endSize;
    }

    public void setEndSize(int endSize) {
        this.endSize = endSize;
    }

    public float getCurrentX() {
        return currentX;
    }

    public void setCurrentX(float currentX) {
        this.currentX = currentX;
    }

    public float getCurrentY() {
        return currentY;
    }

    public void setCurrentY(float currentY) {
        this.currentY = currentY;
    }

    public float getBeginY() {
        return beginY;
    }

    public void setBeginY(float beginY) {
        this.beginY = beginY;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public Bitmap getImageEmotion() {
        return imageEmotion;
    }

    public void setImageEmotion(Bitmap imageEmotion) {
        this.imageEmotion = imageEmotion;
    }

    public Bitmap getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(Bitmap imageTitle) {
        this.imageTitle = imageTitle;
    }

    public Paint getEmotionPaint() {
        return emotionPaint;
    }

    public void setEmotionPaint(Paint emotionPaint) {
        this.emotionPaint = emotionPaint;
    }

    public Paint getTitlePaint() {
        return titlePaint;
    }

    public void setTitlePaint(Paint titlePaint) {
        this.titlePaint = titlePaint;
    }

    public float getRatioWH() {
        return ratioWH;
    }

    public void setRatioWH(float ratioWH) {
        this.ratioWH = ratioWH;
    }
}
