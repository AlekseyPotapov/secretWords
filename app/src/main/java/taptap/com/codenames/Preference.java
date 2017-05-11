package taptap.com.codenames;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Set;

/**
 * Created by n551jm on 25.12.2016.
 */
public class Preference {

    public static final String WORDS = "words";
    public static final String COLOR_MAP = "color_map";

    public static void saveWords(Context context, Set<String> chosenWords) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet(WORDS, chosenWords).apply();
    }

    public static void saveColorMap(Context context, int[] colorMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int color : colorMap) {
            stringBuilder.append(color);
        }
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(COLOR_MAP, stringBuilder.toString()).apply();
    }

    public static int[] getColorMap(Context context) {
        String colorMapString = PreferenceManager.getDefaultSharedPreferences(context).getString(COLOR_MAP, null);
        if (TextUtils.isEmpty(colorMapString)) {
            return null;
        }
        int[] colorMap = new int[colorMapString.length()];
        char[] charArray = colorMapString.toCharArray();
        for (int i = 0; i < colorMap.length; i++) {
            char c = charArray[i];
            colorMap[i] = Integer.parseInt(String.valueOf(c));
        }
        return colorMap;
    }
}
