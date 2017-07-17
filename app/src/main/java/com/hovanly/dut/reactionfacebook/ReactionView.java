package com.hovanly.dut.reactionfacebook;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.List;

import static com.hovanly.dut.reactionfacebook.CommonDimen.DIVIDE;

enum StateDraw {
    BEGIN,
    CHOOSING,
    NORMAL, END
}

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 14/07/2017
 */
public class ReactionView extends View {
    public static final long DURATION_ANIMATION = 300;
    public static final long DURATION_BEGINNGING_EACH_ITE = 300;
    public static final long DURATION_BEGINNING_ANIMATION = 900;
    private static final long DURATION_BEGINNING_EACH_ITEM = 200;
    private EaseOutBack easeOutBack;
    private Board mBoard;
    private List<Emotion> mEmotions = new ArrayList<>();
    private StateDraw mStateDraw = StateDraw.BEGIN;
    private int currentPosition = 0;
    public ReactionView(Context context) {
        super(context);
        init();
    }

    public ReactionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBoard = new Board(getContext());
        setLayerType(LAYER_TYPE_SOFTWARE, mBoard.getBoardPaint());
        mEmotions.add(new Emotion(getContext(), "Like", R.drawable.ic_like));
        mEmotions.add(new Emotion(getContext(), "Love", R.drawable.ic_love));
        mEmotions.add(new Emotion(getContext(), "Haha", R.drawable.ic_haha));
        mEmotions.add(new Emotion(getContext(), "Wow", R.drawable.ic_wow));
        mEmotions.add(new Emotion(getContext(), "Cry", R.drawable.ic_cry));
        mEmotions.add(new Emotion(getContext(), "Angry", R.drawable.ic_angry));
        initElement();
    }

    private void initElement() {
        mBoard.setCurrentY(CommonDimen.HEIGHT_VIEW_REACTION + 10);
        for (Emotion emotion : mEmotions) {
            emotion.setCurrentY(mBoard.getCurrentY() + CommonDimen.DIVIDE);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBoard.drawBoard(canvas);
        for (Emotion emotion : mEmotions) {
            emotion.drawEmotion(canvas);
        }
    }

    private void beforeAnimateBeginning() {
        mBoard.setBeginHeight(Board.BOARD_HEIGHT_NORMAL);
        mBoard.setEndHeight(Board.BOARD_HEIGHT_NORMAL);
        mBoard.setBeginY(Board.BOARD_BOTTOM + 150);
        mBoard.setEndY(Board.BOARD_Y);
        easeOutBack = EaseOutBack.newInstance(DURATION_BEGINNING_EACH_ITEM, Math.abs(mBoard.getBeginY() - mBoard.getEndY()), 0);
        int size = mEmotions.size();
        for (int i = 0; i < size; i++) {
            mEmotions.get(i).setEndY(Board.BASE_LINE - Emotion.NORMAL_SIZE);
            mEmotions.get(i).setBeginY(Board.BOARD_BOTTOM + 150);
            if (i == 0) {
                mEmotions.get(i).setCurrentX(Board.BOARD_X + DIVIDE);
            } else {
                mEmotions.get(i).setCurrentX(mEmotions.get(i - 1).getCurrentX() + mEmotions.get(i - 1).getCurrentSize()+ DIVIDE);
            }
        }
    }

    private void beforeAnimateChoosing() {
        mBoard.setBeginHeight(mBoard.getCurrentHeight());
        mBoard.setEndHeight(Board.BOARD_HEIGHT_MINIMAL);
        int size = mEmotions.size();
        for (int i = 0; i < size; i++) {
            mEmotions.get(i).setBeginSize(mEmotions.get(i).getCurrentSize());
            if (i == currentPosition) {
                mEmotions.get(i).setEndSize(Emotion.CHOOSE_SIZE);
            } else {
                mEmotions.get(i).setEndSize(Emotion.MINIMAL_SIZE);
            }
        }
    }

    private void beforeAnimateNormalBack() {
        mBoard.setBeginHeight(mBoard.getCurrentHeight());
        mBoard.setEndHeight(Board.BOARD_HEIGHT_NORMAL);
        for (Emotion mEmotion : mEmotions) {
            mEmotion.setBeginSize(mEmotion.getCurrentSize());
            mEmotion.setEndSize(Emotion.NORMAL_SIZE);
        }
    }

    private void calculateInSessionChoosingAndEnding(float interpolatedTime) {
        mBoard.setCurrentHeight(mBoard.getBeginHeight() + (int) (interpolatedTime * (mBoard.getEndHeight() - mBoard.getBeginHeight())));
        for (int i = 0; i < mEmotions.size(); i++) {
            mEmotions.get(i).setCurrentSize(calculateSize(i, interpolatedTime));
            mEmotions.get(i).setCurrentY(Board.BASE_LINE - mEmotions.get(i).getCurrentSize());
        }
        calculateCoordinateX();
        invalidate();
    }

    private int calculateSize(int position, float interpolatedTime) {
        int changeSize = mEmotions.get(position).getEndSize() - mEmotions.get(position).getBeginSize();
        return mEmotions.get(position).getBeginSize() + (int) (interpolatedTime * changeSize);
    }

    private void calculateInSessionBeginning(float interpolatedTime) {
        float currentTime = interpolatedTime * DURATION_BEGINNING_ANIMATION;
        if (currentTime > 0) {
            float value = mBoard.getEndY() + easeOutBack.getCoordinateYFromTime(Math.min(currentTime, DURATION_BEGINNING_EACH_ITEM));
            mBoard.setCurrentY(value);
        }
        if (currentTime >= 100) {
            float value = mEmotions.get(0).getEndY() + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 100, DURATION_BEGINNING_EACH_ITEM));
            mEmotions.get(0).setCurrentY(value);
        }

        if (currentTime >= 200) {
            float value = mEmotions.get(1).getEndY() + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 200, DURATION_BEGINNING_EACH_ITEM));
            mEmotions.get(1).setCurrentY(value);
        }

        if (currentTime >= 300) {
            float value = mEmotions.get(2).getEndY() + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 300, DURATION_BEGINNING_EACH_ITEM));
            mEmotions.get(2).setCurrentY(value);
        }

        if (currentTime >= 400) {
            float value = mEmotions.get(3).getEndY() + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 400, DURATION_BEGINNING_EACH_ITEM));
            mEmotions.get(3).setCurrentY(value);
        }

        if (currentTime >= 500) {
            float value = mEmotions.get(4).getEndY() + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 500, DURATION_BEGINNING_EACH_ITEM));
            mEmotions.get(4).setCurrentY(value);
        }

        if (currentTime >= 600) {
            float value = mEmotions.get(5).getEndY() + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 600, DURATION_BEGINNING_EACH_ITEM));
            mEmotions.get(5).setCurrentY(value);
        }
        invalidate();
    }

    private void calculateCoordinateX() {
        int size = mEmotions.size();
        mEmotions.get(0).setCurrentX(Board.BOARD_X + DIVIDE);
        mEmotions.get(size - 1).setCurrentX(Board.BOARD_X + Board.BOARD_WIDTH - DIVIDE - mEmotions.get(size - 1).getCurrentSize());

        for (int i = 1; i < currentPosition; i++) {
            mEmotions.get(i).setCurrentX(mEmotions.get(i - 1).getCurrentX() + mEmotions.get(i - 1).getCurrentSize() + DIVIDE);
        }

        for (int i = size - 2; i > currentPosition; i--) {
            mEmotions.get(i).setCurrentX(mEmotions.get(i + 1).getCurrentX() - mEmotions.get(i).getCurrentSize() - DIVIDE);
        }

        if (currentPosition != 0 && currentPosition != size - 1) {
            if (currentPosition <= (size / 2 - 1)) {
                float value = mEmotions.get(currentPosition - 1).getCurrentX() + mEmotions.get(currentPosition - 1).getCurrentSize() + DIVIDE;
                mEmotions.get(currentPosition).setCurrentX(value);
            } else {
                float value = mEmotions.get(currentPosition + 1).getCurrentX() - mEmotions.get(currentPosition).getCurrentSize() - DIVIDE;
                mEmotions.get(currentPosition).setCurrentX(value);
            }
        }
    }

    public void showEmotions() {
        mStateDraw = StateDraw.BEGIN;
        setVisibility(VISIBLE);
        beforeAnimateBeginning();
        startAnimation(new BeginningAnimation());
    }

    private void onEmotionSelected(int position) {
        if (currentPosition == position && mStateDraw == StateDraw.CHOOSING) {
            return;
        }
        mStateDraw = StateDraw.CHOOSING;
        currentPosition = position;
        startAnimation(new ChooseEmotionAnimation());
    }

    public void onBackToNormal() {
        // TODO set normal size
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < mEmotions.size(); i++) {
                    if (event.getX() > mEmotions.get(i).getCurrentX() && event.getX() < mEmotions.get(i).getCurrentX() + mEmotions.get(i).getCurrentSize()) {
                        onEmotionSelected(i);
                        break;
                    }
                }
                handled = true;
                break;
            case MotionEvent.ACTION_UP:
                onBackToNormal();
                handled = true;
                break;
        }
        return handled;
    }

    /**
     * Thực hiện phương pháp nội suy (ước lượng các điểm dữ liệu trong một phạm vi nào đó)
     */
    public class ChooseEmotionAnimation extends Animation {
        public ChooseEmotionAnimation() {
            if (mStateDraw == StateDraw.CHOOSING) {
                beforeAnimateChoosing();
            } else if (mStateDraw == StateDraw.NORMAL) {
                beforeAnimateNormalBack();
            }
            setDuration(DURATION_ANIMATION);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            calculateInSessionChoosingAndEnding(interpolatedTime);
        }
    }

    class BeginningAnimation extends Animation {
        public BeginningAnimation() {
            beforeAnimateBeginning();
            setDuration(DURATION_BEGINNING_ANIMATION);
        }
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            calculateInSessionBeginning(interpolatedTime);
        }
    }
}
