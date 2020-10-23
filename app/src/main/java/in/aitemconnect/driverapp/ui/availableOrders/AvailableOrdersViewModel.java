package in.aitemconnect.driverapp.ui.availableOrders;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

public class AvailableOrdersViewModel extends ViewModel implements AvailableOrdersModel.AvailableOrdersInterface {

    AvailableOrdersModel availableOrdersModel = new AvailableOrdersModel();

    void finishOrder(Context context) {
        FinishOrder finishOrder = new FinishOrder(context);
        finishOrder.execute();
    }

    void acceptOrders(Context context) {
        AcceptOrders acceptOrders = new AcceptOrders(context);
        acceptOrders.execute();
    }


    class AcceptOrders extends AsyncTask<Void, Void, Void> {
        Context context;

        public AcceptOrders(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            availableOrdersModel.acceptOrder(context);
            return null;
        }
    }


    class FinishOrder extends AsyncTask<Void, Void, Void> {
        Context context;

        public FinishOrder(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            availableOrdersModel.finishOrder(context);
            return null;
        }
    }

}
