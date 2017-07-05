package gengjiawen.me.switchdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;
import timber.log.Timber;

public class ChooseTypeRadioGroup extends LinearLayout {

    @BindView(R.id.ridePriceTextView)
    TextView ridePriceTextView;
    @BindView(R.id.rideLinearLayout)
    LinearLayout rideLinearLayout;
    @BindView(R.id.fastTextView)
    TextView fastTextView;
    @BindView(R.id.kuaiLabelTextView)
    TextView kuaiLabelTextView;
    @BindView(R.id.kuaiLinearLayout)
    LinearLayout kuaiLinearLayout;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private LinearLayout rootView;
    private int checkIndex;

    public ChooseTypeRadioGroup(Context context) {
        super(context);
        init(context);
    }

    public ChooseTypeRadioGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        checkIndex = -1;
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = (LinearLayout) inflater.inflate(R.layout.view_choose_type, this, false);
        ButterKnife.bind(this, rootView);

        rideLinearLayout.setOnClickListener(v -> {
            Timber.i("ride");
            setChecked(0);
        });

        kuaiLinearLayout.setOnClickListener(v -> {
            Timber.i("kuai");
            setChecked(1);
        });

        kuaiLabelTextView.setBackgroundResource(R.drawable.tags_rounded_corners);

        addView(rootView);
    }

    public interface OnCheckedChangeListener {
        public void onCheckedChanged(int checkedId);
    }

    public void setChecked(int position) {
        if (checkIndex == position) {
            return;
        }

        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(position);
        }
        checkIndex = position;

        GradientDrawable background = (GradientDrawable) kuaiLabelTextView.getBackground();
        background.setColor(Color.TRANSPARENT);
        if (position == 0) {
            setViewTextColor(rideLinearLayout, Color.RED);
            setViewTextColor(kuaiLinearLayout, Color.BLACK);
            background.setStroke(1, Color.BLACK);
        } else {
            setViewTextColor(rideLinearLayout, Color.BLACK);
            setViewTextColor(kuaiLinearLayout, Color.RED);
            background.setStroke(1, Color.RED);
        }
    }

    private void setViewTextColor(ViewGroup viewGroup, int color) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            } else if (view instanceof ViewGroup) {
               setViewTextColor((ViewGroup) view, color);
            }
        }
    }

    public Observable<Integer> checkedChanged() {
        return Observable.create(e -> {
            this.mOnCheckedChangeListener = checkedId -> {
                if (!e.isDisposed()) {
                    e.onNext(checkedId);
                }
            };

            e.setDisposable(new MainThreadDisposable() {
                @Override
                protected void onDispose() {
                    ChooseTypeRadioGroup.this.mOnCheckedChangeListener = null;
                }
            });
        });
    }

}
