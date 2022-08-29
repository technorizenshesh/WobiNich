package com.my.wobinichapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.R;
import com.my.wobinichapp.act.HomeActivity;
import com.my.wobinichapp.model.GetTypeModel;
import com.my.wobinichapp.model.GetUserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LandScapeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetTypeModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public LandScapeAdapter(Context context, ArrayList<GetTypeModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetTypeModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_landscape, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetTypeModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;
        //   String State= getAddress(mContext,model.getLat(),model.getLon());
       //    genericViewHolder.txtPlace.setText(""+State);
           genericViewHolder.txtTitle.setText(model.getType());

            if(!model.getImage().equalsIgnoreCase(""))
            {
                Glide.with(mContext).load(model.getImage()).placeholder(R.drawable.kotl_one).error(R.drawable.kotl_one).into(genericViewHolder.img2);
            }
        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetTypeModel.Result getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position, GetTypeModel.Result model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPlace;
        private TextView txtTitle;
        private TextView txtStatusUSer;
        private ImageView img2;
        private RelativeLayout RRUser;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txtPlace=itemView.findViewById(R.id.txtPlace);
            this.txtTitle=itemView.findViewById(R.id.txtTitle);
            this.img2=itemView.findViewById(R.id.img2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }

    public String getAddress(Context context, String latitude1, String longitute1) {

        double latitude= Double.parseDouble(latitude1);
        double longitute= Double.parseDouble(longitute1);

        List<Address> addresses;
        String state="";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitute, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            String  addressStreet = addresses.get(0).getAddressLine(0);
            // address2 = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            //  city = addresses.get(0).getLocality();
             state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String region = addresses.get(0).getAdminArea();

            //   Preference.save(getActivity(), Preference.KEY_address, addressStreet);

//            Log.e("region====", region);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return state;
    }

}

