package com.my.wobinichapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.act.GrpChatDetails;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.model.HomModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GrpMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetUserModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public GrpMemberAdapter(Context context, ArrayList<GetUserModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetUserModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_details, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetUserModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;


            genericViewHolder.txtGrpName.setText(model.getName());

            if(model.getMessageCount().toString().equalsIgnoreCase("0"))
            {
                genericViewHolder.txtCount.setVisibility(View.GONE);

            }else
            {
                genericViewHolder.txtCount.setVisibility(View.VISIBLE);
            }

            if(!Preference.get(mContext,Preference.KEY_USER_ID).equalsIgnoreCase(model.getId()))
            {
                genericViewHolder.txtCount.setText(model.getMessageCount()+"");

            }else
            {
                genericViewHolder.txtCount.setVisibility(View.GONE);
            }


            Glide.with(mContext)
                    .load(model.getImage())
                    .centerCrop()
                    .circleCrop()
                    .error(R.drawable.user_default)
                    .into(genericViewHolder.ImgIcon);
          /*
            if(!model.getImage().equalsIgnoreCase(""))
            {
                Glide.with(mContext).load(model.getImage()).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(genericViewHolder.ImgIcon);
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

    private GetUserModel.Result getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, GetUserModel.Result model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGrpName;
        private TextView txtCount;
        private CircleImageView ImgIcon;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.txtGrpName=itemView.findViewById(R.id.txtGrpName);
            this.txtCount=itemView.findViewById(R.id.txtCount);
            this.ImgIcon=itemView.findViewById(R.id.ImgIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}

