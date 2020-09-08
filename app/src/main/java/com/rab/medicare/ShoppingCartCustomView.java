package com.rab.medicare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ShoppingCartCustomView extends BaseAdapter
{
    Context context;
    ArrayList<String> ProductID, ProductName, ProductPrice, PrescriptionRequired;
    ArrayList<Bitmap> ProductImage;
    String CurrencySymbol = "€";
    LoggedInDecision LID = new LoggedInDecision();

// Below is code of Holder.
    public class ViewHolder
    {
        private TextView ProductName;
        private TextView ProductPrice;
        private TextView PrescriptionRequired;
        private LinearLayout LL2;
        private Button RemoveFromCart;
        private ImageView ProductImage;
    }

    ShoppingCartCustomView(Context context, ArrayList<String> ProductID, ArrayList<String> ProductName, ArrayList<String> ProductPrice, ArrayList<String> PrescriptionRequired, ArrayList<Bitmap> ProductImage)
    {
        this.context = context;
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.ProductPrice = ProductPrice;
        this.PrescriptionRequired = PrescriptionRequired;
        this.ProductImage = ProductImage;
        System.out.println("Product ID : "+ProductID);
        System.out.println("Product Name : "+ProductName);
        System.out.println("Product Price : "+ProductPrice);
        System.out.println("Prescription Required : "+PrescriptionRequired);
        System.out.println("Product Image : "+ProductImage);
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder Holder;
        if(convertView == null)
        {
            Holder = new ViewHolder();
            LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.activity_shopping_cart_custom_view, parent, false);
            Holder.ProductImage = convertView.findViewById(R.id.ProductImage);
            Holder.ProductName = convertView.findViewById(R.id.ProductName);
            Holder.ProductPrice = convertView.findViewById(R.id.ProductPrice);
            Holder.PrescriptionRequired = convertView.findViewById(R.id.PrescriptionRequired);
            Holder.RemoveFromCart = convertView.findViewById(R.id.RemoveFromCart);
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
                Intent ProductDetailsPage = new Intent(context, ProductDetails.class);
                ProductDetailsPage.putExtra("ProductID",ProductID.get(position));
                context.startActivity(ProductDetailsPage);
            }
        });
        Holder.RemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent RemoveFromCartFunction;
                if(LID.getLoginStatus() == null)
                {
                    Intent GoToSignIn = new Intent(context, SignIn.class);
                    context.startActivity(GoToSignIn);
                }
                else
                {
                    RemoveFromCart RFC = new RemoveFromCart();
                    RFC.RemoveProduct(ProductID.get(position));
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
                    if(API_Res.getNotification().compareTo("ProductSuccessfullyRemoved") == 0)
                    {
                        Toast.makeText(context,"Product successfully removed from your cart.",Toast.LENGTH_SHORT).show();
                        Intent RefreshPage = new Intent(context, ShoppingCart.class);
                        context.startActivity(RefreshPage);
                    }
                    else
                    {
                        Toast.makeText(context,"Failed to remove product from your cart. Please check your internet connection and try again.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return convertView;
    }
}