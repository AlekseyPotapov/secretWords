package taptap.com.codenames;

import java.util.Random;

/**
 * Created by n551jm on 10.05.2017.
 */

public class SeedKeeper {

    public static final int WORD_SEED_SIZE = 8;
    public static final int MASK_SEED_SIZE = 8;
    private static SeedKeeper mInstance;
    private String mWordSeed;
    private Long mMaskSeed;

    private SeedKeeper() {

    }

    public static SeedKeeper getInstance() {
        if (mInstance == null) {
            mInstance = new SeedKeeper();
        }
        return mInstance;
    }

    public String generateWordSeed() {
        Random random = new Random(System.currentTimeMillis());
        char[] seed = new char[WORD_SEED_SIZE];
        for (int i = 0; i < seed.length; i++) {
            seed[i] = (char)(random.nextInt(26) + 'a');
        }
        mWordSeed = new String(seed).toUpperCase();
        return mWordSeed;
    }

    public String getWordSeed() {
        return mWordSeed;
    }

    public void setWordSeed(String seed) {
        mWordSeed = seed;
    }

    public Long generateMaskSeed() {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < WORD_SEED_SIZE; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        mMaskSeed = Long.valueOf(stringBuilder.toString());
        return mMaskSeed;
    }

    public Long getMaskSeed() {
        return mMaskSeed;
    }

    public void setMaskSeed(long seed) {
        mMaskSeed = seed;
    }
}
