package com.hovanly.dut.reactionfacebook;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 14/07/2017
 */
public class Board {
    public static final int BOARD_WIDTH = 6 * Emotion.NORMAL_SIZE + 7 * CommonDimen.DIVIDE; //DIVIDE = 5dp, Emotion.NORMAL_SIZE = 40dp

    public static final int BOARD_HEIGHT_NORMAL = DisplayUtils.dpToPx(50);

    public static final int BOARD_HEIGHT_MINIMAL = DisplayUtils.dpToPx(38);

    public static final float BOARD_X = 10;

    public static final float BOARD_BOTTOM = CommonDimen.HEIGHT_VIEW_REACTION - 200;

    public static final float BOARD_Y = BOARD_BOTTOM - BOARD_HEIGHT_NORMAL;

    public static final float BASE_LINE = BOARD_Y + Emotion.NORMAL_SIZE + CommonDimen.DIVIDE;

    private float currentHeight = BOARD_HEIGHT_NORMAL;

    private float currentY = BOARD_Y;

    private float beginHeight;

    private float endHeight;

    private float beginY;

    private float endY;

    private Paint boardPaint;


    public Board(Context context) {
        initPaint(context);
    }

    private void initPaint(Context context) {
        boardPaint = new Paint();
        boardPaint.setAntiAlias(true);
        boardPaint.setStyle(Paint.Style.FILL);
        boardPaint.setColor(context.getResources().getColor(R.color.colorBoard));
        boardPaint.setShadowLayer(5.0f, 0.0f, 2.0f, 0xFF000000);
    }

    public void setCurrentHeight(float newHeight) {
        currentHeight = newHeight;
        currentY = BOARD_BOTTOM - currentHeight;
    }

    public float getCurrentHeight() {
        return currentHeight;
    }

    public void drawBoard(Canvas canvas) {
        float radius = currentHeight / 2;
        RectF board = new RectF(BOARD_X, currentY, BOARD_X + BOARD_WIDTH, currentY + currentHeight);
        canvas.drawRoundRect(board, radius, radius, boardPaint);
    }

    public float getCurrentY() {
        return currentY;
    }

    public void setCurrentY(float currentY) {
        this.currentY = currentY;
    }

    public float getBeginHeight() {
        return beginHeight;
    }

    public void setBeginHeight(float beginHeight) {
        this.beginHeight = beginHeight;
    }

    public float getEndHeight() {
        return endHeight;
    }

    public void setEndHeight(float endHeight) {
        this.endHeight = endHeight;
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

    public Paint getBoardPaint() {
        return boardPaint;
    }

    public void setBoardPaint(Paint boardPaint) {
        this.boardPaint = boardPaint;
    }
}
