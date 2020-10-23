package in.aitemconnect.driverapp.pojo.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderParentPojo {

    @Expose
    ArrayList<OrderPojo> order;

    public ArrayList<OrderPojo> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<OrderPojo> order) {
        this.order = order;
    }
}
