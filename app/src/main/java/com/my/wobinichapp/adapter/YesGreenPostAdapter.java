package com.my.wobinichapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.R;
import com.my.wobinichapp.model.GetPostBluVoilet;

import java.util.ArrayList;

public class YesGreenPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetPostBluVoilet.Greendatum> modelList;
    private OnItemClickListener mItemClickListener;

    public YesGreenPostAdapter(Context context, ArrayList<GetPostBluVoilet.Greendatum> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetPostBluVoilet.Greendatum> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_yes_post_blue, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetPostBluVoilet.Greendatum model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            if(model.getImage()!=null)
            {
                Glide.with(mContext).load(model.getImage()).placeholder(R.drawable.dummy).error(R.drawable.frame).into(genericViewHolder.imgBlue);
                genericViewHolder.tvBlueName.setText(model.getGroupName());
                genericViewHolder.tvBlueAddress.setText(model.getMaps());
                genericViewHolder.tvBlueDate.setText(model.getDateTime());


            }
            genericViewHolder.relative_searchBtn.setOnClickListener(v -> {
                mItemClickListener.onItemClick( position, modelList.get(position));

            });

          /*  genericViewHolder.relative_searchBtn.setOnClickListener(v -> {
                // Toast.makeText(mContext, "Single Time", Toast.LENGTH_SHORT).show();
            });
            genericViewHolder.relative_searchBtn.setOnLongClickListener(v -> {

                // Toast.makeText(mContext, "Long Time", Toast.LENGTH_SHORT).show();
                return false;
            });*/

            // genericViewHolder.txtGrpName.setText(model.getFolderName());
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetPostBluVoilet.Greendatum getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick( int position, GetPostBluVoilet.Greendatum model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGrpName,tvBlueName,tvBlueAddress,tvBlueDate;
        private ImageView imgBlue;
        private RelativeLayout relative_searchBtn;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.txtGrpName=itemView.findViewById(R.id.txtGrpName);
            this.tvBlueName=itemView.findViewById(R.id.tvBlueName);
            this.tvBlueAddress=itemView.findViewById(R.id.tvBlueAddress);
            this.tvBlueDate=itemView.findViewById(R.id.tvBlueDate);

            this.imgBlue=itemView.findViewById(R.id.imgBlue);
            this.relative_searchBtn=itemView.findViewById(R.id.relative_searchBtn);
            //  this.ImgIcon=itemView.findViewById(R.id.ImgIcon);


        }
    }


}
