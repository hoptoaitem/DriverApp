package com.aitemconnect.driverapp.ui.availableOrders;

import android.app.ProgressDialog;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.aitemconnect.driverapp.R;
import com.aitemconnect.driverapp.pojo.UpdateOrderStatusPojo;
import com.aitemconnect.driverapp.pojo.order.Contact;
import com.aitemconnect.driverapp.pojo.order.Destination;
import com.aitemconnect.driverapp.pojo.order.Item;
import com.aitemconnect.driverapp.pojo.order.OrderPojo;
import com.aitemconnect.driverapp.pojo.order.Origin;
import com.aitemconnect.driverapp.pojo.order.Retailor;
import com.aitemconnect.driverapp.ui.dashboard.DashActivity;

public class AvailableOrderActivity extends AppCompatActivity {

    @BindView(R.id.tvOrderIdOrder)
    TextView tvOrderIdOrder;
    @BindView(R.id.tvStoreNameOrder)
    TextView tvStoreNameOrder;
    @BindView(R.id.tvOriginAddressOrder)
    TextView tvOriginAddressOrder;
    @BindView(R.id.tvMobileOrder)
    TextView tvMobileOrder;


    @BindView(R.id.tvOrderIdAccept)
    TextView tvOrderIdAccept;
    @BindView(R.id.tvStoreNameAccept)
    TextView tvStoreNameAccept;
    @BindView(R.id.tvOriginAddressAccept)
    TextView tvOriginAddressAccept;
    @BindView(R.id.tvMobileAccept)
    TextView tvMobileAccept;


    @BindView(R.id.tvOrderIdClerkSign)
    TextView tvOrderIdClerkSign;
    @BindView(R.id.tvStoreNameClerkSign)
    TextView tvStoreNameClerkSign;
    @BindView(R.id.tvOriginAddressClerkSign)
    TextView tvOriginAddressClerkSign;
    @BindView(R.id.tvMobileClerkSign)
    TextView tvMobileClerkSign;
    @BindView(R.id.tvDropLocationClerkSign)
    TextView tvDropLocationClerkSign;


    @BindView(R.id.buttonDecline)
    Button buttonDecline;

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
    CountDownTimer countDownTimer;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_order);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        textViewActionBarHeading.setText("Available order");

        progressDialog = new ProgressDialog(AvailableOrderActivity.this);

        availableOrdersViewModel = ViewModelProviders.of(this).get(AvailableOrdersViewModel.class);

        // ORDER DECLINED
        availableOrdersViewModel.orderDeclined.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {

                if (progressDialog != null) {
                    progressDialog.hide();
                }

                toDashboard();
            }
        });

        // All failed response
        // and
        // ORDER ACCEPTED here
        availableOrdersViewModel.orderAccepted.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {

                if (progressDialog != null) {
                    progressDialog.hide();
                }

                if (string.equalsIgnoreCase("success")) {
                    updateUiTillAccept();

//                    Toast.makeText(AvailableOrderActivity.this, "Order accepted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AvailableOrderActivity.this, "Request failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // ORDER DELIVERED
        availableOrdersViewModel.orderDelivered.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {

                if (progressDialog != null) {
                    progressDialog.hide();
                }

                if (string.equalsIgnoreCase("success")) {
                    // To dashboard
                    toDashboard();

                    Toast.makeText(AvailableOrderActivity.this, "Order delivered", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AvailableOrderActivity.this, "Request failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // TODO: 23-10-2020 CHECK CODE
        Intent intent = getIntent();
        order_pojo = (OrderPojo) intent.getSerializableExtra("order_pojo");
        String id = order_pojo.getId();

        setTexts(order_pojo);

        // If order is already accepted
        String orderStatus = order_pojo.getOrderStatus();
        if (orderStatus.equalsIgnoreCase(getString(R.string.orderAcceptedByDriver))) {
            updateUiTillAccept();
        } else {
            // If order is not accepted
            countDownTimer = new CountDownTimer(timeToCount, 125) {

                @Override
                public void onTick(long remainingTimeInMillisec) {
                    long secondsRemaining = remainingTimeInMillisec / 1000;
                    tvRemainingTime.setText(secondsRemaining + "");
                }

                @Override
                public void onFinish() {
                    tvRemainingTime.setText("0");
                    toDashboard();
                }
            };
            countDownTimer.start();
        }
    }

    private void toDashboard() {
        Intent intent = new Intent(AvailableOrderActivity.this,
                DashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.buttonAccept, R.id.buttonNextOnAccept,
            R.id.buttonNextOnClerkSignature, R.id.buttonFinishOnShopperSignature,
            R.id.buttonDecline})
    public void onClick(View view) {
        String orderIdD = order_pojo.getId();

        int id = view.getId();
        switch (id) {

            case R.id.buttonDecline:
//                Toast.makeText(AvailableOrderActivity.this, "Order declined", Toast.LENGTH_LONG).show();
//                toDashboard();

                String orderDeclined = getResources().getString(R.string.orderDeclinedByDriver);

                UpdateOrderStatusPojo updateOrderStatusPojo3 = new UpdateOrderStatusPojo();
                updateOrderStatusPojo3.setOrderId(orderIdD + "");
                updateOrderStatusPojo3.setOrderStatus(orderDeclined);

                availableOrdersViewModel.updateOrder(
                        AvailableOrderActivity.this,
                        updateOrderStatusPojo3,
                        orderDeclined);

                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                // Cancel the countdown timer
                cancelCountDownTimer();

                break;

            case R.id.buttonAccept:
                String orderAccepted = getResources().getString(R.string.orderAcceptedByDriver);

                UpdateOrderStatusPojo updateOrderStatusPojo = new UpdateOrderStatusPojo();
                updateOrderStatusPojo.setOrderId(orderIdD + "");
                updateOrderStatusPojo.setOrderStatus(orderAccepted);

                availableOrdersViewModel.updateOrder(
                        AvailableOrderActivity.this,
                        updateOrderStatusPojo,
                        orderAccepted);

                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                // Cancel the countdown timer
                cancelCountDownTimer();

                break;


            case R.id.buttonNextOnAccept:
                updateUiTillClerkSign();
                break;

            case R.id.buttonNextOnClerkSignature:
                if (buttonNextOnClerkSignature.getText().toString().trim()
                        .equalsIgnoreCase(getResources().getString(R.string.verifyClerksSign))) {
                    String clerkSign = etClerkSignature.getText().toString().trim();

                    if (clerkSign.isEmpty()) {
                        Toast.makeText(AvailableOrderActivity.this,
                                "Order should be signed by the clerk!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        llDropInstructionsAndAddress.setVisibility(View.VISIBLE);
                        buttonNextOnClerkSignature.setText("NEXT");
                    }

                } else {
                    updateUiTillShopperSign();
                }
                break;

            case R.id.buttonFinishOnShopperSignature:
                String orderDelivered = getResources().getString(R.string.orderDelivered);
                String orderId2 = order_pojo.getId();
                UpdateOrderStatusPojo updateOrderStatusPojo2 = new UpdateOrderStatusPojo();
                updateOrderStatusPojo2.setOrderId(orderId2 + "");

                updateOrderStatusPojo2.setOrderStatus(orderDelivered);

                availableOrdersViewModel.updateOrder(
                        AvailableOrderActivity.this,
                        updateOrderStatusPojo2,
                        orderDelivered);

                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                break;

            default:

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


    void setTexts(OrderPojo order_pojo) {
        String order_pojoId = order_pojo.getId();

        Origin origin1 = order_pojo.getOrigin();

        String addressNameStoreName = origin1.getAddressName();

        String streetAddress = origin1.getStreetAddress();
        String streetAddress1 = origin1.getStreetAddress1();
        String city = origin1.getCity();
        String state = origin1.getState();
        String zip = origin1.getZip();

        Contact contact = order_pojo.getContact();
        Retailor retailor = contact.getRetailor();
        String retailorPhone = retailor.getPhone();

        String finalAddress = streetAddress + " " + streetAddress1 + " " + city + " " + state + " " + zip;

        tvOrderIdOrder.setText("" + order_pojoId);
        tvOrderIdAccept.setText("" + order_pojoId);
        tvOrderIdClerkSign.setText("" + order_pojoId);

        tvStoreNameOrder.setText("" + addressNameStoreName);
        tvStoreNameAccept.setText("" + addressNameStoreName);
        tvStoreNameClerkSign.setText("" + addressNameStoreName);

        tvOriginAddressOrder.setText("" + finalAddress);
        tvOriginAddressAccept.setText("" + finalAddress);
        tvOriginAddressClerkSign.setText("" + finalAddress);

        tvMobileOrder.setText("" + retailorPhone);
        tvMobileAccept.setText("" + retailorPhone);
        tvMobileClerkSign.setText("" + retailorPhone);

        Destination destination = order_pojo.getDestination();
        String streetAddressD = destination.getStreetAddress();
        String streetAddress1D = destination.getStreetAddress1();
        String cityD = destination.getCity();
        String stateD = destination.getState();
        String zipD = destination.getZip();

        String finalAddressDest = streetAddressD + " " + streetAddress1D + " " + cityD + " " + stateD + " " + zipD;

        tvDropLocationClerkSign.setText("" + finalAddressDest);


        Origin origin = order_pojo.getOrigin();
        List<Item> items = order_pojo.getItems();

        String id = order_pojo.getId();
        String createdAt = order_pojo.getCreatedAt();

//        Toast.makeText(AvailableOrderActivity.this, "id " + id, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelCountDownTimer();
    }

    private void updateUiTillClerkSign() {
        viewAcceptRight.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewClerkSignLeft.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewClerkSign.setImageDrawable(getResources().getDrawable(
                R.drawable.icon_check_hollow_checked));


        rlAcceptedParent.setVisibility(View.GONE);
        rlClerkSignParent.setVisibility(View.VISIBLE);
    }

    private void updateUiTillShopperSign() {
        viewClerkSignRight.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewShopperSignLeft.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewShopperSign.setImageDrawable(getResources().getDrawable(
                R.drawable.icon_check_hollow_checked));

        rlClerkSignParent.setVisibility(View.GONE);
        rlShoppersSignParent.setVisibility(View.VISIBLE);
    }

    private void cancelCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

