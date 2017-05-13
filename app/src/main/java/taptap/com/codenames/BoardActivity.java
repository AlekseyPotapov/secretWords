package taptap.com.codenames;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class BoardActivity extends AppCompatActivity {

    /**
     * - добавить анимацию появления карточек
     * - добавить таймер на весь экран через 5 минут
     * - при наклоне скрывать цвета
     */

    public static final String TYPE = "game_type";
    public static final int TIMER_MAX_TIME = 300000;

    private FrameLayout mRoot;
    private BoardUtils mBoardUtils;
    private ProgressBar mProgressBar;
    private CountDownTimer mCountDownTimer;

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
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mCountDownTimer = new CountDownTimer(TIMER_MAX_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressBar.setProgress((int) ((TIMER_MAX_TIME - millisUntilFinished) * 100 / TIMER_MAX_TIME));
            }

            @Override
            public void onFinish() {

            }
        };

        createNewBoard();
    }

    private void recreateBoard() {
        mBoardUtils = new BoardUtils(this, SeedKeeper.getInstance().generateWordSeed(), SeedKeeper.getInstance().generateMaskSeed());
        createNewBoard();
    }

    private void createNewBoard() {
        BoardView boardView = new BoardView(
                this,
                mBoardUtils.generateWords(),
                mBoardUtils.generateMask(),
                getIntent().getIntExtra(TYPE, BoardUtils.TYPE_CLIENT));

        boardView.setOnWordClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProgress();
            }
        });
        mRoot.addView(boardView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        startProgress();
    }

    private void startProgress() {
        mCountDownTimer.cancel();
        mCountDownTimer.start();
    }

}