package com.demo7802.feedme.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.demo7802.feedme.CartActivity;
import com.demo7802.feedme.Database.Database;
import com.demo7802.feedme.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private List<com.demo7802.feedme.Order> orders;
    private CartActivity context;
    private com.demo7802.feedme.ItemClickListener itemClickListener;

    public CartAdapter(CartActivity context, List<com.demo7802.feedme.Order> orders, com.demo7802.feedme.ItemClickListener itemClickListener) {
        this.orders = orders;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public void setItemClickListener(com.demo7802.feedme.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewItemName, textViewItemPrice;
        private ElegantNumberButton btnQuantity;
//        private ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);

            textViewItemName = itemView.findViewById(R.id.cart_item_name);
            textViewItemPrice = itemView.findViewById(R.id.cart_item_price);
            btnQuantity = itemView.findViewById(R.id.cart_change_quantity);
//            textViewTotalPrice =  itemView.findViewById(R.id.order_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onclick(view, viewHolder.getAdapterPosition(), false);
            }
        });

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textViewItemName.setText(orders.get(position).getProductName());

        Locale locale = new Locale("en", "US");
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        final int price = Integer.parseInt(orders.get(position).getPrice()) * Integer.parseInt(orders.get(position).getQuantity())
            - Integer.parseInt(orders.get(position).getDiscount()) * Integer.parseInt(orders.get(position).getQuantity());
        holder.textViewItemPrice.setText(numberFormat.format(price));
        holder.btnQuantity.setNumber(orders.get(position).getQuantity());

        holder.btnQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                //Updating the price after quantity is changed
                int result = Integer.parseInt(orders.get(position).getPrice()) * newValue;
                holder.textViewItemPrice.setText(numberFormat.format(result));

                //Update database
                com.demo7802.feedme.Order order = orders.get(position);
                order.setQuantity(String.valueOf(newValue));
                new Database(context).updateCart(order);

                //Update total amount
                int total = 0;
                for(com.demo7802.feedme.Order cartOrder: orders)
                {
                    total += (Integer.parseInt(cartOrder.getPrice()) * Integer.parseInt(cartOrder.getQuantity())
                            - Integer.parseInt(cartOrder.getDiscount()) * Integer.parseInt(cartOrder.getQuantity()));
                }
                context.textViewPrice.setText(String.format(" $%s", total));
            }
        });

//        TextDrawable textDrawable = TextDrawable.builder().buildRound(orders.get(position).getQuantity(), Color.RED);
//        holder.imageView.setImageDrawable(textDrawable);
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

}
