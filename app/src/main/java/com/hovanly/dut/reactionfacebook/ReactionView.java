package com.hovanly.dut.reactionfacebook;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

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
    private Emotion[] mEmotions = new Emotion[6];
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
        setLayerType(LAYER_TYPE_SOFTWARE, mBoard.boardPaint);

        mEmotions[0] = new Emotion(getContext(), "Like", R.drawable.ic_like);
        mEmotions[1] = new Emotion(getContext(), "Love", R.drawable.ic_love);
        mEmotions[2] = new Emotion(getContext(), "Haha", R.drawable.ic_haha);
        mEmotions[3] = new Emotion(getContext(), "Wow", R.drawable.ic_wow);
        mEmotions[4] = new Emotion(getContext(), "Cry", R.drawable.ic_cry);
        mEmotions[5] = new Emotion(getContext(), "Angry", R.drawable.ic_angry);

        //BEGIN: Đoạn này để đặt các thành phần vào vị trí ban đầu để xem kết quả thui,
        //chứ các thành phần ban đầu sẽ bị ẩn đi, vì chưa click like mà :D
        for (int i = 0; i < mEmotions.length; i++) {
            mEmotions[i].currentY = Board.BASE_LINE - Emotion.NORMAL_SIZE;
            mEmotions[i].currentX = i == 0 ? Board.BOARD_X + DIVIDE : mEmotions[i - 1].currentX + mEmotions[i - 1].currentSize + DIVIDE;
        }
        //END

        initElement();
    }

    private void initElement() {
        mBoard.currentY = CommonDimen.HEIGHT_VIEW_REACTION + 10;
        for (Emotion e : mEmotions) {
            e.currentY = mBoard.currentY + CommonDimen.DIVIDE;
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
        mBoard.beginHeight = Board.BOARD_HEIGHT_NORMAL;
        mBoard.endHeight = Board.BOARD_HEIGHT_NORMAL;

        mBoard.beginY = Board.BOARD_BOTTOM + 150;
        mBoard.endY = Board.BOARD_Y;

        easeOutBack = EaseOutBack.newInstance(DURATION_BEGINNING_EACH_ITEM, Math.abs(mBoard.beginY - mBoard.endY), 0);

        for (int i = 0; i < mEmotions.length; i++) {
            mEmotions[i].endY = Board.BASE_LINE - Emotion.NORMAL_SIZE;
            mEmotions[i].beginY = Board.BOARD_BOTTOM + 150;
            mEmotions[i].currentX = i == 0 ? Board.BOARD_X + DIVIDE : mEmotions[i - 1].currentX + mEmotions[i - 1].currentSize + DIVIDE;
        }
    }

    private void beforeAnimateChoosing() {
        mBoard.beginHeight = mBoard.getCurrentHeight();
        mBoard.endHeight = Board.BOARD_HEIGHT_MINIMAL;
        for (int i = 0; i < mEmotions.length; i++) {
            mEmotions[i].beginSize = mEmotions[i].currentSize;
            if (i == currentPosition) {
                mEmotions[i].endSize = Emotion.CHOOSE_SIZE;
            } else {
                mEmotions[i].endSize = Emotion.MINIMAL_SIZE;
            }
        }
    }

    private void beforeAnimateNormalBack() {
        mBoard.beginHeight = mBoard.getCurrentHeight();
        mBoard.endHeight = Board.BOARD_HEIGHT_NORMAL;
        for (Emotion mEmotion : mEmotions) {
            mEmotion.beginSize = mEmotion.currentSize;
            mEmotion.endSize = Emotion.NORMAL_SIZE;
        }
    }

    private void calculateInSessionChoosingAndEnding(float interpolatedTime) {
        mBoard.setCurrentHeight(mBoard.beginHeight + (int) (interpolatedTime * (mBoard.endHeight - mBoard.beginHeight)));
        for (int i = 0; i < mEmotions.length; i++) {
            mEmotions[i].currentSize = calculateSize(i, interpolatedTime);
            mEmotions[i].currentY = Board.BASE_LINE - mEmotions[i].currentSize;
        }
        calculateCoordinateX();
        invalidate();
    }

    private int calculateSize(int position, float interpolatedTime) {
        int changeSize = mEmotions[position].endSize - mEmotions[position].beginSize;
        return mEmotions[position].beginSize + (int) (interpolatedTime * changeSize);
    }

    private void calculateInSessionBeginning(float interpolatedTime) {
        float currentTime = interpolatedTime * DURATION_BEGINNING_ANIMATION;

        if (currentTime > 0) {
            mBoard.currentY = mBoard.endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime, DURATION_BEGINNING_EACH_ITEM));
        }

        if (currentTime >= 100) {
            mEmotions[0].currentY = mEmotions[0].endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 100, DURATION_BEGINNING_EACH_ITEM));
        }

        if (currentTime >= 200) {
            mEmotions[1].currentY = mEmotions[1].endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 200, DURATION_BEGINNING_EACH_ITEM));
        }

        if (currentTime >= 300) {
            mEmotions[2].currentY = mEmotions[2].endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 300, DURATION_BEGINNING_EACH_ITEM));
        }

        if (currentTime >= 400) {
            mEmotions[3].currentY = mEmotions[3].endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 400, DURATION_BEGINNING_EACH_ITEM));
        }

        if (currentTime >= 500) {
            mEmotions[4].currentY = mEmotions[4].endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 500, DURATION_BEGINNING_EACH_ITEM));
        }

        if (currentTime >= 600) {
            mEmotions[5].currentY = mEmotions[5].endY + easeOutBack.getCoordinateYFromTime(Math.min(currentTime - 600, DURATION_BEGINNING_EACH_ITEM));
        }

        invalidate();
    }

    private void calculateCoordinateX() {
        mEmotions[0].currentX = Board.BOARD_X + DIVIDE;
        mEmotions[mEmotions.length - 1].currentX = Board.BOARD_X + Board.BOARD_WIDTH - DIVIDE - mEmotions[mEmotions.length - 1].currentSize;

        for (int i = 1; i < currentPosition; i++) {
            mEmotions[i].currentX = mEmotions[i - 1].currentX + mEmotions[i - 1].currentSize + DIVIDE;
        }

        for (int i = mEmotions.length - 2; i > currentPosition; i--) {
            mEmotions[i].currentX = mEmotions[i + 1].currentX - mEmotions[i].currentSize - DIVIDE;
        }

        if (currentPosition != 0 && currentPosition != mEmotions.length - 1) {
            if (currentPosition <= (mEmotions.length / 2 - 1)) {
                mEmotions[currentPosition].currentX = mEmotions[currentPosition - 1].currentX + mEmotions[currentPosition - 1].currentSize + DIVIDE;
            } else {
                mEmotions[currentPosition].currentX = mEmotions[currentPosition + 1].currentX - mEmotions[currentPosition].currentSize - DIVIDE;
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

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < mEmotions.length; i++) {
                    if (event.getX() > mEmotions[i].currentX && event.getX() < mEmotions[i].currentX + mEmotions[i].currentSize) {
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
