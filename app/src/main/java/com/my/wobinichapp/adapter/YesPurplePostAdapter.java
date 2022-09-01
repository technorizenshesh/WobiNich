package com.my.wobinichapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.R;
import com.my.wobinichapp.model.GetPostBluVoilet;

import java.util.ArrayList;

public class YesPurplePostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetPostBluVoilet.Voiletdatum> modelList;
    private OnItemClickListener mItemClickListener;

    public YesPurplePostAdapter(Context context, ArrayList<GetPostBluVoilet.Voiletdatum> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetPostBluVoilet.Voiletdatum> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_yes_post_purple, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetPostBluVoilet.Voiletdatum model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            if(model.getImage()!=null) {
                Glide.with(mContext).load(model.getImage()).placeholder(R.drawable.dummy).error(R.drawable.frame).into(genericViewHolder.imgBlue);
            }
            genericViewHolder.txtPurple.setText(model.getGroupName());

        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetPostBluVoilet.Voiletdatum getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, GetPostBluVoilet.Voiletdatum model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPurple;
        private ImageView imgBlue;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.txtPurple=itemView.findViewById(R.id.txtPurple);
            this.imgBlue=itemView.findViewById(R.id.imgBlue);
          //  this.ImgIcon=itemView.findViewById(R.id.ImgIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}

