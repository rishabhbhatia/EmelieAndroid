package com.satiate.emelie.cardslidepanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.satiate.emelie.R;
import com.satiate.emelie.models.Story;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({"HandlerLeak", "NewApi", "ClickableViewAccessibility"})
public class CardSlidePanel extends ViewGroup {

    private List<CardItemView> viewList = new ArrayList<CardItemView>();
    private List<View> releasedViewList = new ArrayList<View>();

    private final ViewDragHelper mDragHelper;
    private int initCenterViewX = 0, initCenterViewY = 0;
    private int allWidth = 0;
    private int allHeight = 0;
    private int childWith = 0;

    private static final float SCALE_STEP = 0.08f;
    private static final int MAX_SLIDE_DISTANCE_LINKAGE = 500;

    private View bottomLayout;

    private int itemMarginTop = 10;
    private int bottomMarginTop = 40;
    private int yOffsetStep = 40;
    private int mTouchSlop = 5;

    private static final int X_VEL_THRESHOLD = 800;
    private static final int X_DISTANCE_THRESHOLD = 300;

    public static final int VANISH_TYPE_LEFT = 0;
    public static final int VANISH_TYPE_RIGHT = 1;

    private Object obj1 = new Object();

    private CardSwitchListener cardSwitchListener;
    private List<Story> stories;
    private int isShowing = 0;
    private View leftBtn, rightBtn;
    private boolean btnLock = false;
    private GestureDetectorCompat moveDetector;
    private OnClickListener btnListener;
    private Point downPoint = new Point();

    public CardSlidePanel(Context context) {
        this(context, null);
    }

    public CardSlidePanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardSlidePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.card);

        itemMarginTop = (int) a.getDimension(R.styleable.card_itemMarginTop, itemMarginTop);
        bottomMarginTop = (int) a.getDimension(R.styleable.card_bottomMarginTop, bottomMarginTop);
        yOffsetStep = (int) a.getDimension(R.styleable.card_yOffsetStep, yOffsetStep);
        mDragHelper = ViewDragHelper
                .create(this, 10f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
        a.recycle();

        btnListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.maskView) {
                    if (null != cardSwitchListener && view.getScaleX() > 1 - SCALE_STEP) {
                        cardSwitchListener.onItemClick(view, isShowing);
                    }
                } else {
                    btnLock = true;
                    int type = -1;
                    if (view == leftBtn) {
                        type = VANISH_TYPE_LEFT;
                    } else if (view == rightBtn) {
                        type = VANISH_TYPE_RIGHT;
                    }
                    vanishOnBtnClick(type);
                }
            }
        };

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        moveDetector = new GestureDetectorCompat(context,
                new MoveDetector());
        moveDetector.setIsLongpressEnabled(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        viewList.clear();
        int num = getChildCount();
        for (int i = num - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            if (childView.getId() == R.id.card_bottom_layout) {
                bottomLayout = childView;
                initBottomLayout();
            } else {
                CardItemView viewItem = (CardItemView) childView;
                viewItem.setParentView(this);
                viewItem.setTag(i + 1);
                viewItem.maskView.setOnClickListener(btnListener);
                viewList.add(viewItem);
            }
        }

        CardItemView bottomCardView = viewList.get(viewList.size() - 1);
        bottomCardView.setAlpha(0);
    }

    private void initBottomLayout() {
        leftBtn = bottomLayout.findViewById(R.id.card_left_btn);
        rightBtn = bottomLayout.findViewById(R.id.card_right_btn);

        leftBtn.setOnClickListener(btnListener);
        rightBtn.setOnClickListener(btnListener);
    }

    class MoveDetector extends SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx,
                                float dy) {
            return Math.abs(dy) + Math.abs(dx) > mTouchSlop;
        }
    }


    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            onViewPosChanged((CardItemView) changedView);
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            if (child == bottomLayout || stories == null || stories.size() == 0
                    || child.getVisibility() != View.VISIBLE || child.getScaleX() <= 1.0f - SCALE_STEP) {
                return false;
            }

            if (btnLock) {
                return false;
            }

            int childIndex = viewList.indexOf(child);
            if (childIndex > 0) {
                return false;
            }

            ((CardItemView) child).onStartDragging();
            return ((CardItemView) child).shouldCapture(downPoint.x, downPoint.y);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 256;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            animToSide((CardItemView) releasedChild, (int) xvel, (int) yvel);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }
    }


    public void onViewPosChanged(CardItemView changedView) {
        int index = viewList.indexOf(changedView);
        if (index + 2 > viewList.size()) {
            return;
        }

        processLinkageView(changedView);
    }

    private void orderViewStack() {
        synchronized (obj1) {
            if (releasedViewList.size() == 0) {
                return;
            }

            CardItemView changedView = (CardItemView) releasedViewList.get(0);
            if (changedView.getLeft() == initCenterViewX) {
                releasedViewList.remove(0);
                return;
            }

            changedView.offsetLeftAndRight(initCenterViewX
                    - changedView.getLeft());
            changedView.offsetTopAndBottom(initCenterViewY
                    - changedView.getTop() + yOffsetStep * 2);
            float scale = 1.0f - SCALE_STEP * 2;
            changedView.setScaleX(scale);
            changedView.setScaleY(scale);
            changedView.setAlpha(0);

            int num = viewList.size();
            for (int i = num - 1; i > 0; i--) {
                CardItemView tempView = viewList.get(i);
                tempView.setAlpha(1);
                tempView.bringToFront();
            }

            int newIndex = isShowing + 4;
            if (newIndex < stories.size()) {
                Story story = stories.get(newIndex);
                changedView.fillData(story);
            } else {
                changedView.setVisibility(View.INVISIBLE);
            }

            viewList.remove(changedView);
            viewList.add(changedView);
            releasedViewList.remove(0);

            if (isShowing + 1 < stories.size()) {
                isShowing++;
            }
            if (null != cardSwitchListener) {
                cardSwitchListener.onShow(isShowing);
            }
        }
    }

    private void processLinkageView(View changedView) {
        int changeViewLeft = changedView.getLeft();
        int changeViewTop = changedView.getTop();
        int distance = Math.abs(changeViewTop - initCenterViewY)
                + Math.abs(changeViewLeft - initCenterViewX);
        float rate = distance / (float) MAX_SLIDE_DISTANCE_LINKAGE;

        float rate1 = rate;
        float rate2 = rate - 0.1f;

        if (rate > 1) {
            rate1 = 1;
        }

        if (rate2 < 0) {
            rate2 = 0;
        } else if (rate2 > 1) {
            rate2 = 1;
        }

        ajustLinkageViewItem(changedView, rate1, 1);
        ajustLinkageViewItem(changedView, rate2, 2);

        CardItemView bottomCardView = viewList.get(viewList.size() - 1);
        bottomCardView.setAlpha(rate2);
    }

    private void ajustLinkageViewItem(View changedView, float rate, int index) {
        int changeIndex = viewList.indexOf(changedView);
        int initPosY = yOffsetStep * index;
        float initScale = 1 - SCALE_STEP * index;

        int nextPosY = yOffsetStep * (index - 1);
        float nextScale = 1 - SCALE_STEP * (index - 1);

        int offset = (int) (initPosY + (nextPosY - initPosY) * rate);
        float scale = initScale + (nextScale - initScale) * rate;

        View ajustView = viewList.get(changeIndex + index);
        ajustView.offsetTopAndBottom(offset - ajustView.getTop()
                + initCenterViewY);
        ajustView.setScaleX(scale);
        ajustView.setScaleY(scale);
    }

    private void animToSide(CardItemView changedView, int xvel, int yvel) {
        int finalX = initCenterViewX;
        int finalY = initCenterViewY;
        int flyType = -1;

        int dx = changedView.getLeft() - initCenterViewX;
        int dy = changedView.getTop() - initCenterViewY;

        final float xyRate = 3f;
        if (xvel > X_VEL_THRESHOLD && Math.abs(yvel) < xvel * xyRate) {
            finalX = allWidth;
            finalY = yvel * (childWith + changedView.getLeft()) / xvel + changedView.getTop();
            flyType = VANISH_TYPE_RIGHT;
        } else if (xvel < -X_VEL_THRESHOLD && Math.abs(yvel) < -xvel * xyRate) {
            finalX = -childWith;
            finalY = yvel * (childWith + changedView.getLeft()) / (-xvel) + changedView.getTop();
            flyType = VANISH_TYPE_LEFT;
        } else if (dx > X_DISTANCE_THRESHOLD && Math.abs(dy) < dx * xyRate) {
            finalX = allWidth;
            finalY = dy * (childWith + initCenterViewX) / dx + initCenterViewY;
            flyType = VANISH_TYPE_RIGHT;
        } else if (dx < -X_DISTANCE_THRESHOLD && Math.abs(dy) < -dx * xyRate) {
            finalX = -childWith;
            finalY = dy * (childWith + initCenterViewX) / (-dx) + initCenterViewY;
            flyType = VANISH_TYPE_LEFT;
        }

        if (finalY > allHeight) {
            finalY = allHeight;
        } else if (finalY < -allHeight / 2) {
            finalY = -allHeight / 2;
        }

        if (finalX == initCenterViewX) {
            changedView.animTo(initCenterViewX, initCenterViewY);
        } else {
            releasedViewList.add(changedView);
            if (mDragHelper.smoothSlideViewTo(changedView, finalX, finalY)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }

            if (flyType >= 0 && cardSwitchListener != null) {
                cardSwitchListener.onCardVanish(isShowing, flyType);
            }
        }
    }

    private void vanishOnBtnClick(int type) {
        synchronized (obj1) {
            View animateView = viewList.get(0);
            if (animateView.getVisibility() != View.VISIBLE || releasedViewList.contains(animateView)) {
                return;
            }

            int finalX = 0;
            int extraVanishDistance = 100; // 为加快vanish的速度，额外添加消失的距离
            if (type == VANISH_TYPE_LEFT) {
                finalX = -childWith - extraVanishDistance;
            } else if (type == VANISH_TYPE_RIGHT) {
                finalX = allWidth + extraVanishDistance;
            }

            if (finalX != 0) {
                releasedViewList.add(animateView);
                if (mDragHelper.smoothSlideViewTo(animateView, finalX, initCenterViewY + allHeight / 2)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            }

            if (type >= 0 && cardSwitchListener != null) {
                cardSwitchListener.onCardVanish(isShowing, type);
            }
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            // 动画结束
            synchronized (this) {
                if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_IDLE) {
                    orderViewStack();
                    btnLock = false;
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            this.downPoint.x = (int) ev.getX();
            this.downPoint.y = (int) ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        boolean moveFlag = moveDetector.onTouchEvent(ev);
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_SETTLING) {
                mDragHelper.abort();
            }
            orderViewStack();

            mDragHelper.processTouchEvent(ev);
        }

        return shouldIntercept && moveFlag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        try {
            mDragHelper.processTouchEvent(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));

        allWidth = getMeasuredWidth();
        allHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            View viewItem = viewList.get(i);
            int childHeight = viewItem.getMeasuredHeight();
            int viewLeft = (getWidth() - viewItem.getMeasuredWidth()) / 2;
            viewItem.layout(viewLeft, itemMarginTop, viewLeft + viewItem.getMeasuredWidth(), itemMarginTop + childHeight);
            int offset = yOffsetStep * i;
            float scale = 1 - SCALE_STEP * i;
            if (i > 2) {
                offset = yOffsetStep * 2;
                scale = 1 - SCALE_STEP * 2;
            }

            viewItem.offsetTopAndBottom(offset);
            viewItem.setScaleX(scale);
            viewItem.setScaleY(scale);
        }

        if (null != bottomLayout) {
            int layoutTop = viewList.get(0).getBottom() + bottomMarginTop;
            bottomLayout.layout(left, layoutTop, right, layoutTop
                    + bottomLayout.getMeasuredHeight());
        }

        initCenterViewX = viewList.get(0).getLeft();
        initCenterViewY = viewList.get(0).getTop();
        childWith = viewList.get(0).getMeasuredWidth();
    }

    public void fillData(List<Story> stories) {
        this.stories = stories;

        int num = viewList.size();
        for (int i = 0; i < num; i++) {
            CardItemView itemView = viewList.get(i);
            itemView.fillData(stories.get(i));
            itemView.setVisibility(View.VISIBLE);
        }

        if (null != cardSwitchListener) {
            cardSwitchListener.onShow(0);
        }
    }

    public void setCardSwitchListener(CardSwitchListener cardSwitchListener) {
        this.cardSwitchListener = cardSwitchListener;
    }

    public interface CardSwitchListener {

        public void onShow(int index);

        public void onCardVanish(int index, int type);

        public void onItemClick(View cardImageView, int index);
    }
}