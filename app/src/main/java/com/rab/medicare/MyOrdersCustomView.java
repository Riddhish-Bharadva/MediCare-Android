package com.rab.medicare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MyOrdersCustomView extends BaseAdapter
{
    Context context;
    ArrayList<String> L_OrderID, L_OrderType, L_OrderStatus, L_OrderTotal;
    String CurrencySymbol = "€";
    LoggedInDecision LID = new LoggedInDecision();
// Below is code of Holder.
    public class ViewHolder
    {
        private TextView OrderID;
        private TextView OrderType;
        private TextView OrderStatus;
        private TextView OrderTotal;
        private LinearLayout LL;
    }

    MyOrdersCustomView(Context context, ArrayList<String> OrderID, ArrayList<String> OrderType, ArrayList<String> OrderStatus, ArrayList<String> OrderTotal)
    {
        this.context = context;
        this.L_OrderID = OrderID;
        this.L_OrderType = OrderType;
        this.L_OrderStatus = OrderStatus;
        this.L_OrderTotal = OrderTotal;

//        System.out.println("Order ID : "+OrderID);
//        System.out.println("Order Type : "+OrderType);
//        System.out.println("Order Status : "+OrderStatus);
//        System.out.println("Order Total : "+OrderTotal);
    }

    @Override
    public int getCount() {
        return L_OrderID.size();
    }

    @Override
    public Object getItem(int position) {
        return L_OrderID.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder Holder;
        if(convertView == null)
        {
            Holder = new MyOrdersCustomView.ViewHolder();
            LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.activity_my_orders_custom_view, parent, false);
            Holder.OrderID = convertView.findViewById(R.id.OrderID);
            Holder.OrderType = convertView.findViewById(R.id.OrderType);
            Holder.OrderStatus = convertView.findViewById(R.id.OrderStatus);
            Holder.OrderTotal = convertView.findViewById(R.id.OrderTotal);
            Holder.LL = convertView.findViewById(R.id.LL);
            convertView.setTag(Holder);
        }
        else
        {
            Holder = (MyOrdersCustomView.ViewHolder) convertView.getTag();
        }
        Holder.OrderID.setText("OrderID : "+L_OrderID.get(position));
        Holder.OrderType.setText(L_OrderType.get(position));
        Holder.OrderStatus.setText(L_OrderStatus.get(position));
        Holder.OrderTotal.setText(CurrencySymbol+" "+L_OrderTotal.get(position));
        Holder.LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent OrderDetailsPage = new Intent(context, OrderDetails.class);
                OrderDetailsPage.putExtra("OrderID",L_OrderID.get(position));
                context.startActivity(OrderDetailsPage);
            }
        });
        return convertView;
    }
}