package in.aitemconnect.driverapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aitemconnect.driverapp.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.buttonLogin)
    Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);

/*        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LoginActivity.this, "  genius..", Toast.LENGTH_SHORT).show();

            }
        });*/

    }

    @OnClick(R.id.buttonLogin)
    public void Onclick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.buttonLogin) {
            startActivity(new Intent(this, AvailableOrderActivity.class));
        }
    }
}