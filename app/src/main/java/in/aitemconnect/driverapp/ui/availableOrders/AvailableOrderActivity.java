package in.aitemconnect.driverapp.ui.availableOrders;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aitemconnect.driverapp.R;
import in.aitemconnect.driverapp.pojo.order.Destination;
import in.aitemconnect.driverapp.pojo.order.Item;
import in.aitemconnect.driverapp.pojo.order.OrderPojo;
import in.aitemconnect.driverapp.pojo.order.Origin;
import in.aitemconnect.driverapp.ui.dashboard.DashActivity;

public class AvailableOrderActivity extends AppCompatActivity {

    @BindView(R.id.buttonNextOnClerkSignature)
    Button buttonNextOnClerkSignature;

    @BindView(R.id.etClerkSignature)
    EditText etClerkSignature;

    @BindView(R.id.llDropInstructionsAndAddress)
    LinearLayout llDropInstructionsAndAddress;

    @BindView(R.id.linearLayoutRemainingTime)
    LinearLayout linearLayoutRemainingTime;

    @BindView(R.id.rlShoppersSignParent)
    RelativeLayout rlShoppersSignParent;

    @BindView(R.id.rlClerkSignParent)
    RelativeLayout rlClerkSignParent;

    @BindView(R.id.rlAcceptedParent)
    RelativeLayout rlAcceptedParent;

    @BindView(R.id.rlRecentOrderParent)
    RelativeLayout rlRecentOrderParent;


    @BindView(R.id.imageViewShopperSign)
    ImageView imageViewShopperSign;

    @BindView(R.id.imageViewClerkSign)
    ImageView imageViewClerkSign;

    @BindView(R.id.imageViewAccept)
    ImageView imageViewAccept;

    @BindView(R.id.viewShopperSignLeft)
    View viewShopperSignLeft;

    @BindView(R.id.viewClerkSignRight)
    View viewClerkSignRight;

    @BindView(R.id.viewClerkSignLeft)
    View viewClerkSignLeft;

    @BindView(R.id.viewAcceptRight)
    View viewAcceptRight;

    @BindView(R.id.viewAcceptLeft)
    View viewAcceptLeft;

    @BindView(R.id.viewOrderRight)
    View viewOrderRight;

    @BindView(R.id.tvRemainingTime)
    TextView tvRemainingTime;

    @BindView(R.id.textViewActionBarHeading)
    TextView textViewActionBarHeading;

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    @BindView(R.id.buttonAccept)
    Button buttonAccept;

    long timeToCount = 1000 * 120;

    OrderPojo order_pojo;
    AvailableOrdersViewModel availableOrdersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_order);
        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        textViewActionBarHeading.setText("Available order");

        availableOrdersViewModel = ViewModelProviders.of(this).get(AvailableOrdersViewModel.class);

        // TODO: 23-10-2020 CHECK CODE
        Intent intent = getIntent();
        order_pojo = (OrderPojo) intent.getSerializableExtra("order_pojo");
        String id = order_pojo.getId();

        setTexts(order_pojo);

        Toast.makeText(this, "id " + id, Toast.LENGTH_SHORT).show();


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

    void setTexts(OrderPojo order_pojo) {
        Destination destination = order_pojo.getDestination();
        Origin origin = order_pojo.getOrigin();
        List<Item> items = order_pojo.getItems();

        String id = order_pojo.getId();
        String createdAt = order_pojo.getCreatedAt();

        Toast.makeText(AvailableOrderActivity.this, "id " + id, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.buttonAccept, R.id.buttonNextOnAccept,
            R.id.buttonNextOnClerkSignature, R.id.buttonFinishOnShopperSignature})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.buttonAccept:
                availableOrdersViewModel.acceptOrders(AvailableOrderActivity.this);
//            todo    updateUiTillAccept();
                break;

            case R.id.buttonNextOnAccept:
                updateUiTillClerkSign();
                break;

            case R.id.buttonNextOnClerkSignature:
                if (buttonNextOnClerkSignature.getText().toString().trim()
                        .equalsIgnoreCase(getResources().getString(R.string.verifyClerksSign))) {
                    String clerkSign = etClerkSignature.getText().toString().trim();

                    if (clerkSign.isEmpty()) {
                        Toast.makeText(AvailableOrderActivity.this, "Order should be signed by the clerk!", Toast.LENGTH_LONG).show();
                    } else {
                        llDropInstructionsAndAddress.setVisibility(View.VISIBLE);
                        buttonNextOnClerkSignature.setText("NEXT");
                    }

                } else {
                    updateUiTillShopperSign();
                }
                break;

            case R.id.buttonFinishOnShopperSignature:
                availableOrdersViewModel.finishOrder(AvailableOrderActivity.this);

//              todo  startActivity(new Intent(AvailableOrderActivity.this, DashActivity.class));
                break;

        }

    }


    private void updateUiTillAccept() {
        viewOrderRight.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewAcceptLeft.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewAccept.setImageDrawable(getResources().getDrawable(R.drawable.icon_check_hollow_checked));

        linearLayoutRemainingTime.setVisibility(View.GONE);
        rlRecentOrderParent.setVisibility(View.GONE);
        rlAcceptedParent.setVisibility(View.VISIBLE);
    }

    private void updateUiTillClerkSign() {
        viewAcceptRight.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewClerkSignLeft.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewClerkSign.setImageDrawable(getResources().getDrawable(R.drawable.icon_check_hollow_checked));


        rlAcceptedParent.setVisibility(View.GONE);
        rlClerkSignParent.setVisibility(View.VISIBLE);
    }

    private void updateUiTillShopperSign() {
        viewClerkSignRight.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewShopperSignLeft.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewShopperSign.setImageDrawable(getResources().getDrawable(R.drawable.icon_check_hollow_checked));

        rlClerkSignParent.setVisibility(View.GONE);
        rlShoppersSignParent.setVisibility(View.VISIBLE);
    }
}

