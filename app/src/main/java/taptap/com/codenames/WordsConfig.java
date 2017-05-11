package taptap.com.codenames;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by n551jm on 09.05.2017.
 */

public class WordsConfig {

    private static volatile WordsConfig mInstance;
    private HashSet<String> mWords;

    private WordsConfig() {
    }

    //br.close();
    public HashSet<String> getWords(Context context) {
        if (mWords != null) {
            return mWords;
        }
        mWords = new HashSet<>();
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open("words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                mWords.add(line.toUpperCase());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return mWords;
    }

    public static WordsConfig getInstance() {
        if (mInstance == null) {
            mInstance = new WordsConfig();
        }
        return mInstance;
    }


}
