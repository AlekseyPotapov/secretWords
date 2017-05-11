package taptap.com.codenames;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by n551jm on 10.05.2017.
 */

public class BoardView extends LinearLayout {

    public static final int WORDS_ROW_NUMBER = (int) Math.sqrt(BoardUtils.WORDS_NUMBER);
    private @BoardUtils.GameType int mGameType;
    private int[] mMask;
    private ArrayList<String> mChosenWords;

    public BoardView(Context context, Set<String> chosenWords, int[] colorsMap, int gameType) {
        this(context);
        mChosenWords = new ArrayList<>();
        mChosenWords.addAll(chosenWords);
        mMask = colorsMap;
        mGameType = gameType;

        init();
    }

    public BoardView(Context context) {
        this(context, null);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        initAttributes();

        for (int i = 0; i < WORDS_ROW_NUMBER; i++) {
            LinearLayout horizontalLinearLayout = new LinearLayout(getContext());
            horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_END | LinearLayout.SHOW_DIVIDER_MIDDLE);
            horizontalLinearLayout.setDividerDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_dim_dark));

            LayoutParams hlp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            hlp.weight = 1;
            horizontalLinearLayout.setLayoutParams(hlp);

            for (int j = 0; j < WORDS_ROW_NUMBER; j++) {
                horizontalLinearLayout.addView(createCell(WORDS_ROW_NUMBER * i + j));
            }

            addView(horizontalLinearLayout);
        }
    }

    private View createCell(int index) {
        final TextView textView = new TextView(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;

        textView.setLayoutParams(lp);
        textView.setText(mChosenWords.get(index));
        textView.setGravity(Gravity.CENTER);

        final int color = mMask[index];
        if (color != 0) {
            if (mGameType == BoardUtils.TYPE_MASTER) {
                textView.setBackgroundColor(getContext().getResources().getColor(color));
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textView.setEnabled(false);
                    }
                });
            } else {
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (textView.isEnabled()) {
                            textView.setEnabled(false);
                            textView.setBackgroundColor(getContext().getResources().getColor(color));
                        } else {
                            textView.setEnabled(true);
                            textView.setBackgroundDrawable(null);
                        }
                    }
                });
            }
        }
        return textView;
    }

    private void initAttributes() {
        setPadding(
                getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin),
                getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin),
                getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin),
                getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));
        setOrientation(LinearLayout.VERTICAL);
        setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_END | LinearLayout.SHOW_DIVIDER_MIDDLE);
        setDividerDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_dim_dark));
        LayoutParams rootLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(rootLayoutParams);
    }
}
