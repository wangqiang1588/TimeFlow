package com.mobibrw.timeflowmgr.biz.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.mobibrw.timeflowmgr.biz.R;

/**
 * Created by longsky on 17-8-7.
 */

public class FloatingActionButtonContainerView extends FrameLayout {
    private final static int INIT_SIZE = 5; /*默认的容器中的FloatingActionButton的数量*/

    private static final int DO_ROTATE = 1;//旋转动画
    private static final int RECOVER_ROTATE = -1;//恢复旋转之前的状态
    private static final int UNFOLDING = 2;//菜单展开状态
    private static final int FOLDING = 3;//菜单折叠状态

    private final int mWidth = 400;//viewGroup的宽
    private final int mHeight = 620;//ViewGroup的高
    private final int length = 200;//子view展开的距离
    private final float mScale = 0.8f;//展开之后的缩放比例
    private final int mDuration = 400;//动画时长
    private int flag = FOLDING;//菜单展开与折叠的状态
    private FloatingActionButton ctrlButton;//在Activity中显示的button

    public FloatingActionButtonContainerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initContainerView();
    }

    public FloatingActionButtonContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initContainerView();
    }

    private void initContainerView() {
        this.removeAllViews();
        setupCtrlButton();
        setContainerSize(INIT_SIZE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量子view的宽高 这是必不可少的 不然子view会没有宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //设置该viewGroup的宽高
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutCtrlButton();
        layoutExpandChildButton();
    }

    private void layoutCtrlButton() {
        //获取宽高
        int width = ctrlButton.getMeasuredWidth();
        int height = ctrlButton.getMeasuredHeight();
        //1：相对于父布局  控件的left
        //2：控件的top
        //3：右边缘的left
        //4：底部的top
        //所以后两个直接用left加上宽 以及 top加上height就好
        ctrlButton.layout(mWidth - width, (mHeight - height) / 2, mWidth, (mHeight - height) / 2 + height);
    }

    private void layoutExpandChildButton() {
        final int cCount = getChildCount();
        final int width = ctrlButton.getMeasuredWidth();
        final int height = ctrlButton.getMeasuredHeight();
        //设置子view的初始位置  与mainButton重合  并且设置为不可见
        for (int i = 1; i < cCount; i++) {
            final View view = getChildAt(i);
            view.layout(mWidth - width, (mHeight - height) / 2, mWidth, (mHeight - height) / 2 + height);
            view.setVisibility(INVISIBLE);
        }
    }

    private void setupCtrlButton() {
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        ctrlButton = new FloatingActionButton(this.getContext());
        ctrlButton.setImageResource(android.R.drawable.ic_input_add);
        final ColorStateList csl = getResources().getColorStateList(R.color.colorMainFloatActionButtonBackgroundTint);
        ctrlButton.setBackgroundTintList(csl);
        //设置主按钮的点击事件
        setCtrlButtonListener(ctrlButton);
        this.addView(ctrlButton, lp);
    }

    public void setContainerSize(int size) {
        boolean reqLayout = false;
        final int childCount = this.getChildCount();
        final int expandChild = childCount - 1;
        if (size > expandChild) {
            for (int i = 0; i < (size - expandChild); i++) {
                final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                final FloatingActionButton btnFloatingAction = new FloatingActionButton(this.getContext());
                setChildButtonListener(btnFloatingAction);
                btnFloatingAction.setVisibility(View.INVISIBLE);
                btnFloatingAction.setImageResource(android.R.drawable.ic_delete);
                this.addView(btnFloatingAction, lp);
                reqLayout = true;
            }
        } else if (size < expandChild) {
            if (size < 0) {
                size = 0;
            }
            for (int i = 0; i < (expandChild - size); i++) {
                this.removeViewAt(this.getChildCount() - 1);
                reqLayout = true;
            }
        }
        if (reqLayout) {
            this.requestLayout();
        }
    }


    /**
     * 设置主按钮的点击事件
     *
     * @param view view
     */
    private void setCtrlButtonListener(final View view) {

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == FOLDING) {//折叠状态
                    final int cCount = FloatingActionButtonContainerView.this.getChildCount();
                    for (int i = 1; i < cCount; i++) {
                        View view = getChildAt(i);
                        view.setVisibility(VISIBLE);
                        //开始平移  第一个参数是view 第二个是角度
                        setTranslation(view, 180 / (cCount - 2) * (i - 1));
                    }
                    flag = UNFOLDING;//展开状态
                    //开始旋转
                    setRotateAnimation(view, DO_ROTATE);

                } else {
                    setBackTranslation();
                    flag = FOLDING;
                    //开始反向旋转 恢复原来的样子
                    setRotateAnimation(view, RECOVER_ROTATE);
                }
            }
        });
    }

    public void setTranslation(View view, int angle) {
        final int x = (int) (length * Math.sin(Math.toRadians(angle)));
        final int y = (int) (length * Math.cos(Math.toRadians(angle)));
        final ObjectAnimator tX = ObjectAnimator.ofFloat(view, "translationX", -x);
        final ObjectAnimator tY = ObjectAnimator.ofFloat(view, "translationY", -y);
        final ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1);
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mScale);
        final ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mScale);

        final AnimatorSet set = new AnimatorSet();
        set.play(tX).with(tY).with(alpha);
        set.play(scaleX).with(scaleY).with(tX);
        set.setDuration(mDuration);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    private void setBackTranslation() {
        int cCount = getChildCount();
        for (int i = 1; i < cCount; i++) {
            final View view = getChildAt(i);
            final ObjectAnimator tX = ObjectAnimator.ofFloat(view, "translationX", 0);
            final ObjectAnimator tY = ObjectAnimator.ofFloat(view, "translationY", 0);
            final ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0);//透明度 0为完全透明
            final AnimatorSet set = new AnimatorSet(); //动画集合
            set.play(tX).with(tY).with(alpha);
            set.setDuration(mDuration); //持续时间
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.start();
            //动画完成后 设置为不可见
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(INVISIBLE);
                }
            });
        }
    }

    public void setRotateAnimation(View view, int flag) {
        ObjectAnimator rotate;
        if (flag == DO_ROTATE)
            rotate = ObjectAnimator.ofFloat(view, "rotation", 135);
        else rotate = ObjectAnimator.ofFloat(view, "rotation", 0);
        rotate.setDuration(mDuration);
        rotate.start();
    }


    /**
     * 执行child的点击事件
     */
    private void setChildButtonListener(final View view) {
        //设置点击时候执行点击事件并且缩回原来的位置
        view.setOnTouchListener(new OnTouchListener() {
            int x, y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getX();
                        y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if ((int) event.getX() == x && (int) event.getY() == y) {
                            //如果手指点击时 与抬起时的x y 坐标相等 那么我们认为手指点了该view
                            setBackTranslation();  //折叠菜单
                            setRotateAnimation(ctrlButton, RECOVER_ROTATE); //旋转mainButton
                            flag = UNFOLDING;//设置为展开状态
                            //执行该view的点击事件
                            view.callOnClick();
                        }
                        break;
                }
                return true;
            }
        });
    }

}