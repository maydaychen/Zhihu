package io.github.maydaychen.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.github.maydaychen.mylibrary.R;


/**
 * 作者：JTR on 2016/8/31 15:36
 * 邮箱：2091320109@qq.com
 */
public class CustomTitle extends RelativeLayout {

    private TextView ivLeft, ivRight;
    private TextView tvTitle;

    public void setTvLeft(TextView ivLeft) {
        this.ivLeft = ivLeft;
    }

    public void setTvRight(TextView ivRight) {
        this.ivRight = ivRight;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle.setText(tvTitle);
    }

    private LeftRightImgClickListener leftRightImgClickListener = null;

    public interface LeftRightImgClickListener {
        void leftClick(Boolean click);

        void rightClick(Boolean click);

    }

    public CustomTitle(Context context) {
        super(context);
    }

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.custom_title, this);
        ivLeft = (TextView) findViewById(R.id.iv_left);
        ivRight = (TextView) findViewById(R.id.iv_right);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitle);
        CharSequence drawableLeft = typedArray.getText(R.styleable.CustomTitle_srcLeftTv);
        if (drawableLeft != null) ivLeft.setText(drawableLeft);
        CharSequence drawableRight = typedArray.getText(R.styleable.CustomTitle_srcRightTv);
        if (drawableRight != null) ivRight.setText(drawableRight);
        CharSequence textTitle = typedArray.getText(R.styleable.CustomTitle_textTitle);
        if (textTitle != null) tvTitle.setText(textTitle);

        typedArray.recycle();

        ivLeft.setOnClickListener(view -> leftRightImgClickListener.leftClick(true));
        ivRight.setOnClickListener(view -> leftRightImgClickListener.rightClick(true));
    }

    public void setLeftRightImgClickListener(LeftRightImgClickListener leftRightImgClickListener) {

        this.leftRightImgClickListener = leftRightImgClickListener;
    }
}
