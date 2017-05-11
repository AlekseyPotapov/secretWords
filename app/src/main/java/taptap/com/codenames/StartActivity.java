package taptap.com.codenames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by n551jm on 10.05.2017.
 */

public class StartActivity extends AppCompatActivity {

    private TextView mWordSeedKeeper;
    private TextView mMaskSeedKeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViewById(android.R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, BoardActivity.class);
                intent.putExtra(BoardActivity.TYPE, BoardUtils.TYPE_CLIENT);
                startActivity(intent);
            }
        });
        findViewById(android.R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, BoardActivity.class);
                intent.putExtra(BoardActivity.TYPE, BoardUtils.TYPE_MASTER);
                startActivity(intent);
            }
        });

        mWordSeedKeeper = (TextView) findViewById(R.id.word_seed_keeper);
        mWordSeedKeeper.setMaxLines(SeedKeeper.WORD_SEED_SIZE);
        mWordSeedKeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog changeSeedAlertDialog = new ChangeSeedDialog(ChangeSeedDialog.WORDS, view.getContext()).create();
                changeSeedAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        mWordSeedKeeper.setText(SeedKeeper.getInstance().getWordSeed());
                    }
                });
                changeSeedAlertDialog.show();
            }
        });
        mWordSeedKeeper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mWordSeedKeeper.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return false;
            }
        });


        mMaskSeedKeeper = (TextView) findViewById(R.id.mask_seed_keeper);
        mMaskSeedKeeper.setMaxLines(SeedKeeper.WORD_SEED_SIZE);

        mMaskSeedKeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog changeSeedAlertDialog = new ChangeSeedDialog(ChangeSeedDialog.MASK, view.getContext()).create();
                changeSeedAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        mMaskSeedKeeper.setText("" + SeedKeeper.getInstance().getMaskSeed());
                    }
                });
                changeSeedAlertDialog.show();
            }
        });
        mMaskSeedKeeper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mMaskSeedKeeper.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SeedKeeper.getInstance().getWordSeed() == null) {
            mWordSeedKeeper.setText(SeedKeeper.getInstance().generateWordSeed());
        } else {
            mWordSeedKeeper.setText(SeedKeeper.getInstance().getWordSeed());
        }

        if (SeedKeeper.getInstance().getMaskSeed() == null) {
            mMaskSeedKeeper.setText("" + SeedKeeper.getInstance().generateMaskSeed());
        } else {
            mMaskSeedKeeper.setText("" + SeedKeeper.getInstance().getMaskSeed());
        }
    }
}
