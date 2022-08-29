package com.my.wobinichapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.R;
import com.my.wobinichapp.model.GetUserModel;

import java.util.ArrayList;

public class AddNewMemebrsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetUserModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public AddNewMemebrsAdapter(Context context, ArrayList<GetUserModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetUserModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetUserModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txtUserName.setText(model.getName());

            genericViewHolder.txtStatusUSer.setText(model.getStatus());

            if(model.getImage()!=null)
            {
                Glide.with(mContext).load(model.getImage()).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(genericViewHolder.imgProfile);
            }

           genericViewHolder.RRUser.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);

            genericViewHolder.RRUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    model.setSelected(!model.isSelected());

                    genericViewHolder.RRUser.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetUserModel.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, GetUserModel.Result model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtUserName;
        private TextView txtStatusUSer;
        private ImageView imgProfile;
        private RelativeLayout RRUser;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txtUserName=itemView.findViewById(R.id.txtUserName);
            this.txtStatusUSer=itemView.findViewById(R.id.txtStatusUSer);
            this.imgProfile=itemView.findViewById(R.id.imgProfile);
            this.RRUser=itemView.findViewById(R.id.RRUser);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }


}

