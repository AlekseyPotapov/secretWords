package taptap.com.codenames;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

/**
 * Created by n551jm on 10.05.2017.
 */

public class ChangeSeedDialog extends AlertDialog.Builder {

    public static final int WORDS = 0;
    public static final int MASK = 1;

    private int mType;

    public ChangeSeedDialog(int type, Context context) {
        this(context);
        mType = type;
        createDialog();
    }

    public ChangeSeedDialog(@NonNull Context context) {
        this(context, 0);
    }

    public ChangeSeedDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    private void createDialog() {
        final EditText editText = createEditText();
        setView(editText);
        setPositiveButton(R.string.seed_apply, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mType == WORDS) {
                    SeedKeeper.getInstance().setWordSeed(editText.getText().toString());
                } else {
                    SeedKeeper.getInstance().setMaskSeed(Long.parseLong(editText.getText().toString()));
                }
            }
        });
        setTitle(R.string.seed_title);
        setNegativeButton(R.string.seed_cancel, null);
    }

    private EditText createEditText() {
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setGravity(Gravity.CENTER);
        if (mType == WORDS) {
            input.setFilters(new InputFilter[]{
                    filter,
                    new InputFilter.LengthFilter(SeedKeeper.WORD_SEED_SIZE),
                    new InputFilter.AllCaps()});
        } else {
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(SeedKeeper.MASK_SEED_SIZE)});
        }
        return input;
    }

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]*").
                        matcher(String.valueOf(source.charAt(i))).matches()) {
                    return "";
                }
            }

            return null;
        }
    };

}
