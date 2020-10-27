package in.aitemconnect.driverapp.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.aitemconnect.driverapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.aitemconnect.driverapp.adapter.CompletedOrdersAdapter;
import in.aitemconnect.driverapp.pojo.order.Item;
import in.aitemconnect.driverapp.pojo.order.OrderPojo;
import in.aitemconnect.driverapp.ui.availableOrders.AvailableOrderActivity;

public class DashActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewCompletedOrders)
    RecyclerView recyclerViewCompletedOrders;

    @BindView(R.id.textViewActionBarHeading)
    TextView textViewActionBarHeading;

    @BindView(R.id.tvNoOrdersFound)
    TextView tvNoOrdersFound;

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    DashboardViewModel dashboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        textViewActionBarHeading.setText("Completed orders");

        dashboardViewModel = ViewModelProviders.of(DashActivity.this).get(DashboardViewModel.class);

        // Get the orders // Check if there is any LOOKING_FOR_DRIVER order OR just completed orders
        dashboardViewModel.getOrders(DashActivity.this);

        dashboardViewModel.requestFailed.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(DashActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            }
        });

        dashboardViewModel.orderResult.observe(this, new Observer<ArrayList<OrderPojo>>() {
            @Override
            public void onChanged(ArrayList<OrderPojo> orderPojos) {
                if (orderPojos.size() > 0) {
                    // Check if there is any available order to accept
                    // IN_CART
                    // TODO: 23-10-2020 check status here // IN_CART
//                    if (orderPojos.get(0).getOrderStatus()) {
//                    }

                    if (orderPojos.size() == 1) {
                        OrderPojo orderPojo = orderPojos.get(0);
                        String orderStatus = orderPojo.getOrderStatus();

                        if (orderStatus.equalsIgnoreCase("LOOKING_FOR_DRIVER")) {
                            // To available order
                            Intent intent = new Intent(DashActivity.this, AvailableOrderActivity.class);
                            intent.putExtra("order_pojo", orderPojo);
                            startActivity(intent);
                        } else {
                            // TODO: 27-10-2020 check for other status

                        }

                    } else {
                        recyclerViewCompletedOrders.setLayoutManager(
                                new LinearLayoutManager(DashActivity.this));

                        CompletedOrdersAdapter completedOrdersAdapter = new CompletedOrdersAdapter(orderPojos,
                                DashActivity.this);
                        recyclerViewCompletedOrders.setAdapter(completedOrdersAdapter);
                    }

                } else {
                    // Show no orders found
                    tvNoOrdersFound.setVisibility(View.VISIBLE);
                    recyclerViewCompletedOrders.setVisibility(View.GONE);
                }
            }
        });

    }
}