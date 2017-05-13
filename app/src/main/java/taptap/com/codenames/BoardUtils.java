package taptap.com.codenames;

import android.content.Context;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by n551jm on 10.05.2017.
 */

public class BoardUtils {

    @Retention(SOURCE)
    @IntDef({TYPE_CLIENT, TYPE_MASTER})
    public @interface GameType {}
    public static final int TYPE_CLIENT = 0;
    public static final int TYPE_MASTER = 1;

    public static final int WORDS_NUMBER = 25;
    public static final int FIRST_WORDS_NUMBER = 9;
    public static final int SECOND_WORDS_NUMBER = 8;
    public static final int BLACK_WORDS_NUMBER = 1;

    private final Context mContext;
    private Random mWordRandom;
    private Random mMaskRandom;

    public BoardUtils(Context context, String wordSeed, long maskSeed) {
        mContext = context;
        mWordRandom = new Random(wordSeed.hashCode());
        mMaskRandom = new Random(maskSeed);
    }

    public ArrayList<String> generateWords() {
        ArrayList<String> words = WordsConfig.getInstance().getWords(mContext);
        ArrayList<String> chosenWords = new ArrayList<>(WORDS_NUMBER);

        final String[] strings = words.toArray(new String[words.size()]);

        while (chosenWords.size() < WORDS_NUMBER) {
            int index = mWordRandom.nextInt(words.size());
            chosenWords.add(strings[index]);
        }
        return chosenWords;
    }

    public int[] generateMask() {
        int[] colorMap = new int[WORDS_NUMBER];
        boolean isRedFirst = mMaskRandom.nextInt(2) == 0;

        setColorToMap(colorMap, isRedFirst ? android.R.color.holo_red_light : android.R.color.holo_blue_light, FIRST_WORDS_NUMBER);
        setColorToMap(colorMap, isRedFirst ? android.R.color.holo_blue_light : android.R.color.holo_red_light, SECOND_WORDS_NUMBER);
        setColorToMap(colorMap, R.color.grey, BLACK_WORDS_NUMBER);

        setDefault(colorMap, R.color.light_grey);
        return colorMap;
    }

    private void setDefault(int[] colorMap, int color) {
        for (int i = 0; i < colorMap.length; i++) {
            if (colorMap[i] == 0) {
                colorMap[i] = color;
            }
        }
    }

    private void setColorToMap(int[] colorMap, int color, int colorNumber) {
        int counter = 0;
        while (counter < colorNumber) {
            int index = mMaskRandom.nextInt(WORDS_NUMBER);
            int currentColor = colorMap[index];
            if (currentColor == 0) {
                colorMap[index] = color;
                counter++;
            }
        }
    }
}