package com.rab.medicare;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class OrderDetailsCustomView extends BaseAdapter {

    Context context;
    ArrayList<Bitmap> ProductImage;
    ArrayList<String> ProductName, ProductStatus, BroughtFrom, PharmacyAddress;
    ArrayList<Integer> PrescriptionRequired, ProductQuantity;
    ArrayList<Float> ProductPrice;
    String CurrencySymbol = "€";

    public OrderDetailsCustomView(Context context, ArrayList<Bitmap> ProductImage, ArrayList<String> ProductName, ArrayList<Integer> PrescriptionRequired, ArrayList<Integer> ProductQuantity, ArrayList<Float> ProductPrice, ArrayList<String> ProductStatus, ArrayList<String> BroughtFrom, ArrayList<String> PharmacyAddress) {
        this.context = context;
        this.ProductImage = ProductImage;
        this.ProductName = ProductName;
        this.PrescriptionRequired = PrescriptionRequired;
        this.ProductQuantity = ProductQuantity;
        this.ProductPrice = ProductPrice;
        this.ProductStatus = ProductStatus;
        this.BroughtFrom = BroughtFrom;
        this.PharmacyAddress = PharmacyAddress;
//        System.out.println("ProductName: "+ProductName);
//        System.out.println("Product Quantity: "+ProductQuantity);
//        System.out.println("Product Price: "+ProductPrice);
    }

// Below is code of Holder.
    public class ViewHolder
    {
        private ImageView PImage;
        private TextView PName;
        private TextView PRequired;
        private TextView PQuantity;
        private TextView PPrice;
        private TextView PStatus;
        private TextView BF;
        private TextView PAddress;
    }

    @Override
    public int getCount() {
        return ProductName.size();
    }

    @Override
    public Object getItem(int position) {
        return ProductName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder Holder;
        if(convertView == null)
        {
            Holder = new ViewHolder();
            LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.activity_order_details_custom_view, parent, false);
            Holder.PImage = convertView.findViewById(R.id.ProductImage);
            Holder.PName = convertView.findViewById(R.id.ProductName);
            Holder.PRequired = convertView.findViewById(R.id.PrescriptionRequired);
            Holder.PQuantity = convertView.findViewById(R.id.PQuantity);
            Holder.PPrice = convertView.findViewById(R.id.Price);
            Holder.PStatus = convertView.findViewById(R.id.ProductStatus);
            Holder.BF = convertView.findViewById(R.id.BroughtFrom);
            Holder.PAddress = convertView.findViewById(R.id.PharmacyAddress);
            convertView.setTag(Holder);
        }
        else
        {
            Holder = (ViewHolder) convertView.getTag();
        }
        Holder.PImage.setImageBitmap(ProductImage.get(position));
        Holder.PName.setText(ProductName.get(position));
        if(PrescriptionRequired.get(position) == 1){
            Holder.PRequired.setText("Prescription is uploaded for this product.");
        }
        else
        {
            Holder.PRequired.setText("This product does not require prescription.");
        }
        Holder.PQuantity.setText("Quantity: "+ProductQuantity.get(position));
        Holder.PPrice.setText(CurrencySymbol+" "+ProductPrice.get(position));
        Holder.PStatus.setText("Status: "+ProductStatus.get(position));
        Holder.BF.setText("Brought From : "+BroughtFrom.get(position));
        Holder.PAddress.setText("Pharmacy Address : "+PharmacyAddress.get(position));
        return convertView;
    }
}