package in.aitemconnect.driverapp.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import in.aitemconnect.driverapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.aitemconnect.driverapp.adapter.CompletedOrdersAdapter;

public class DashActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewCompletedOrders)
    RecyclerView recyclerViewCompletedOrders;

    @BindView(R.id.textViewActionBarHeading)
    TextView textViewActionBarHeading;

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

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        // Get the orders
        dashboardViewModel.getOrders(DashActivity.this);

        ArrayList<String> activity_dash = new ArrayList<>();
        activity_dash.add("Adf");
        activity_dash.add("Adf");
        activity_dash.add("Adf");
        activity_dash.add("Adf");

        recyclerViewCompletedOrders.setLayoutManager(new LinearLayoutManager(DashActivity.this));
        CompletedOrdersAdapter completedOrdersAdapter = new CompletedOrdersAdapter(activity_dash, DashActivity.this);
        recyclerViewCompletedOrders.setAdapter(completedOrdersAdapter);
    }
}