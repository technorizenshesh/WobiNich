package com.my.wobinichapp.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.act.HomeActivity;
import com.my.wobinichapp.model.GetChatData;
import com.my.wobinichapp.model.GrpChatModel;

import java.util.ArrayList;

public class Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<GrpChatModel.Result> modelList;
    private Chat_Adapter.OnItemClickListener mItemClickListener;

    public Chat_Adapter(Context context, ArrayList<GrpChatModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GrpChatModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public Chat_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_one, viewGroup, false);

        return new Chat_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof Chat_Adapter.ViewHolder) {

            final GrpChatModel.Result model = getItem(position);

            final Chat_Adapter.ViewHolder genericViewHolder = (Chat_Adapter.ViewHolder) holder;

            String UserId = Preference.get(mContext, Preference.KEY_USER_ID);

            if(!UserId.equalsIgnoreCase(model.getSenderId()))
            {
                genericViewHolder.LL_right_layOut.setVisibility(View.GONE);
                genericViewHolder.LL_left_layOut.setVisibility(View.VISIBLE);

                genericViewHolder.messageLeft.setText(model.getChatMessage());
                genericViewHolder.tvNameUserLeft.setText(model.getSenderDetail().getName());

                Glide.with(mContext)
                        .load(model.getSenderDetail().getSenderImage())
                        .centerCrop()
                        .circleCrop()
                        .into(genericViewHolder.ivUserLeft);


               /* if(!model.getSenderDetail().getImage().equalsIgnoreCase(""))
                {
                    Glide.with(mContext).load(model.getSenderDetail().getSenderImage()).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(genericViewHolder.ivUserLeft);
                }*/

            }else
            {
                genericViewHolder.LL_right_layOut.setVisibility(View.VISIBLE);
                genericViewHolder.LL_left_layOut.setVisibility(View.GONE);

                genericViewHolder.messageRight.setText(model.getChatMessage());
                genericViewHolder.tvNameUserRight.setText(model.getSenderDetail().getName());


                Glide.with(mContext)
                        .load(model.getSenderDetail().getSenderImage())
                        .centerCrop()
                        .circleCrop()
                        .into(genericViewHolder.ivUserRight);

/*
                if(!model.getSenderDetail().getImage().equalsIgnoreCase(""))
                {
                    Glide.with(mContext).load(model.getSenderDetail().getSenderImage()).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(genericViewHolder.ivUserRight);
                }*/
            }

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final Chat_Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GrpChatModel.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, GrpChatModel.Result model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout LL_left_layOut;
        private RelativeLayout LL_right_layOut;
        private TextView messageLeft;
        private TextView messageRight;
        private TextView tvNameUserLeft;
        private TextView tvNameUserRight;
        private ImageView ivUserLeft;
        private ImageView ivUserRight;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.LL_left_layOut = itemView.findViewById(R.id.itemLeft);
            this.LL_right_layOut = itemView.findViewById(R.id.itemRight);
            this.messageLeft = itemView.findViewById(R.id.messageLeft);
            this.messageRight = itemView.findViewById(R.id.messageRight);
            this.tvNameUserLeft = itemView.findViewById(R.id.tvNameUserLeft);
            this.tvNameUserRight = itemView.findViewById(R.id.tvNameUserRight);
            this.ivUserLeft = itemView.findViewById(R.id.ivUserLeft);
            this.ivUserRight = itemView.findViewById(R.id.ivUserRight);

      /*  this.LL_left_layOut = itemView.findViewById(R.id.LL_left_layOut);
        this.LL_right_layOut = itemView.findViewById(R.id.LL_right_layOut);
        this.txt_left = itemView.findViewById(R.id.txt_left);
        this.txt_Right = itemView.findViewById(R.id.txt_Right);
        this.txt_status_left = itemView.findViewById(R.id.txt_status_left);
        this.txt_status_Right = itemView.findViewById(R.id.txt_status_Right);*/



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }


}

