package taptap.com.codenames;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.Set;

public class BoardActivity extends AppCompatActivity {

    /**
     * - добавить анимацию появления карточек
     * - добавить таймер на весь экран через 5 минут
     */

    public static final String TYPE = "game_type";

    private FrameLayout mRoot;
    private BoardUtils mBoardUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mBoardUtils = new BoardUtils(this, SeedKeeper.getInstance().getWordSeed(), SeedKeeper.getInstance().getMaskSeed());

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRoot.removeAllViews();
                        recreateBoard();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 200);
            }
        });
        mRoot = (FrameLayout) findViewById(R.id.root);

        createNewBoard();
    }

    private void recreateBoard() {
        mBoardUtils = new BoardUtils(this, SeedKeeper.getInstance().generateWordSeed(), SeedKeeper.getInstance().generateMaskSeed());
        createNewBoard();
    }

    private void createNewBoard() {
        mRoot.addView(new BoardView(
                this,
                mBoardUtils.generateWords(),
                mBoardUtils.generateMask(),
                getIntent().getIntExtra(TYPE, BoardUtils.TYPE_CLIENT)),
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}