package in.aitemconnect.driverapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aitemconnect.driverapp.R;

public class AvailableOrderActivity extends AppCompatActivity {


    @BindView(R.id.tvRemainingTime)
    TextView tvRemainingTime;

    @BindView(R.id.textViewActionBarHeading)
    TextView textViewActionBarHeading;

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    @BindView(R.id.buttonAccept)
    Button buttonAccept;

    long timeToCount = 1000 * 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_order);
        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        textViewActionBarHeading.setText("Available order");

        CountDownTimer countDownTimer = new CountDownTimer(timeToCount, 125) {

            @Override
            public void onTick(long remainingTimeInMillisec) {
                long secondsRemaining = remainingTimeInMillisec / 1000;
                tvRemainingTime.setText(secondsRemaining + "");
            }

            @Override
            public void onFinish() {
                tvRemainingTime.setText("0");
            }
        };
        countDownTimer.start();

    }

    @OnClick(R.id.buttonAccept)
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonAccept) {
            startActivity(new Intent(AvailableOrderActivity.this, DashActivity.class));
        }

    }
}

