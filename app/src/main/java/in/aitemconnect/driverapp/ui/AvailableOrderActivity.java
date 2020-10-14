package in.aitemconnect.driverapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.aitemconnect.driverapp.R;

public class AvailableOrderActivity extends AppCompatActivity {


    @BindView(R.id.textViewActionBarHeading)
    TextView textViewActionBarHeading;

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_order);
        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        textViewActionBarHeading.setText("Available order");

    }
}