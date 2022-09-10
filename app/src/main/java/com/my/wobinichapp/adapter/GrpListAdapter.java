package com.my.wobinichapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.R;
import com.my.wobinichapp.act.HomeActivity;
import com.my.wobinichapp.model.GetGrpModel;
import com.my.wobinichapp.model.HomModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GrpListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetGrpModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public GrpListAdapter(Context context, ArrayList<GetGrpModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetGrpModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetGrpModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

           genericViewHolder.txtGrpName.setText(model.getGroupName());

            if(model.getCount().equals("0"))
            {
                genericViewHolder.txtCount.setVisibility(View.GONE);

            }else
            {
                genericViewHolder.txtCount.setVisibility(View.VISIBLE);
            }

            genericViewHolder.txtCount.setText(model.getCount()+"");

            try
            {
                Glide.with(mContext)
                        .load(model.getGroupImage())
                        .centerCrop()
                        .circleCrop()
                        .into(genericViewHolder.imgLeftMenu1);
            }catch (Exception e)
            {

            }

           /* try
            {
            Glide.with(this)
                        .load(model.getGroupImage())
                        .centerCrop()
                        .into(genericViewHolder.imgLeftMenu);

                Glide.with(mContext).load(model.getGroupImage()).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(genericViewHolder.imgLeftMenu);

            }catch (Exception e)
            {
                e.printStackTrace();
            }*/

         /*if(model.getGroupImage()!=null)
            {
                Glide.with(mContext).load(model.getGroupImage()).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(genericViewHolder.imgLeftMenu);
            }*/

        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetGrpModel.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, GetGrpModel.Result model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGrpName;
        private TextView txtCount;
        private CircleImageView imgLeftMenu1;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txtGrpName=itemView.findViewById(R.id.txtGrpName);
            this.txtCount=itemView.findViewById(R.id.txtCount);
            this.imgLeftMenu1=itemView.findViewById(R.id.imgLeftMenu1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}

