package com.rab.medicare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;

public class HomePageCustomView extends BaseAdapter
{
    Context context, Page;
    ArrayList<String> ProductID, ProductName, ProductPrice, PrescriptionRequired;
    ArrayList<Bitmap> ProductImage;
    String CurrencySymbol = "€";
    LoggedInDecision LID = new LoggedInDecision();

    public HomePageCustomView(){}
// Below is code of Holder.
    public class ViewHolder
    {
        private TextView ProductName;
        private TextView ProductPrice;
        private TextView PrescriptionRequired;
        private LinearLayout LL2;
        private Button AddToCart;
        private ImageView ProductImage;
    }
    HomePageCustomView(Context context, ArrayList<String> ProductID, ArrayList<String> ProductName, ArrayList<String> ProductPrice, ArrayList<String> PrescriptionRequired, ArrayList<Bitmap> ProductImage, Context Page)
    {
        this.context = context;
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.ProductPrice = ProductPrice;
        this.PrescriptionRequired = PrescriptionRequired;
        this.ProductImage = ProductImage;
        this.Page = Page;

//        System.out.println("Product ID : "+ProductID);
//        System.out.println("Product Name : "+ProductName);
//        System.out.println("Product Price : "+ProductPrice);
//        System.out.println("Product Price : "+PrescriptionRequired);
//        System.out.println("Product Image : "+ProductImage);
    }

    @Override
    public int getCount() {
        return ProductID.size();
    }

    @Override
    public Object getItem(int position) {
        return ProductID.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder Holder;
        if(convertView == null)
        {
            Holder = new ViewHolder();
            LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.activity_home_page_custom_view, parent, false);
            Holder.ProductImage = convertView.findViewById(R.id.ProductImage);
            Holder.ProductName = convertView.findViewById(R.id.ProductName);
            Holder.ProductPrice = convertView.findViewById(R.id.ProductPrice);
            Holder.PrescriptionRequired = convertView.findViewById(R.id.PrescriptionRequired);
            Holder.AddToCart = convertView.findViewById(R.id.AddToCart);
            Holder.LL2 = convertView.findViewById(R.id.LL2);
            convertView.setTag(Holder);
        }
        else
        {
            Holder = (ViewHolder) convertView.getTag();
        }
        Holder.ProductImage.setImageBitmap(ProductImage.get(position));
        Holder.ProductName.setText(ProductName.get(position));
        Holder.ProductPrice.setText(CurrencySymbol+" "+ProductPrice.get(position));
        if(PrescriptionRequired.get(position).compareTo("1") == 0){
            Holder.PrescriptionRequired.setText("You will need a prescription to buy this product.");
        }
        else
        {
            Holder.PrescriptionRequired.setText("Prescription is not required to buy this product.");
        }
        Holder.LL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent ProductDetailsPage = new Intent(Page, ProductDetails.class);
                ProductDetailsPage.putExtra("ProductID",ProductID.get(position));
                context.startActivity(ProductDetailsPage);
            }
        });
        Holder.AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent AddToCartFunction;
                if(LID.getLoginStatus() == null)
                {
                    AddToCartFunction = new Intent(Page, SignIn.class);
                    context.startActivity(AddToCartFunction);
                }
                else
                {
                    AddToCart ATC = new AddToCart();
                    ATC.AddProduct(ProductID.get(position));
                    try
                    {
                        Thread.sleep(250);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    API_Response API_Res = new API_Response();
//                    System.out.println(API_Res.getNotification());
                    if(API_Res.getNotification().compareTo("ProductSuccessfullyAdded") == 0)
                    {
                        Toast.makeText(Page,"Product successfully added to your cart.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Page,"Failed to add product to your cart. Please check your internet connection and try again.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return convertView;
    }
}