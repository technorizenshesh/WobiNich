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
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.model.GrpChatModel;
import com.my.wobinichapp.model.SingleGetChatModel;

import java.util.ArrayList;

public class Chat_Adapter_single extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<SingleGetChatModel.Result> modelList;
    private Chat_Adapter_single.OnItemClickListener mItemClickListener;

    public Chat_Adapter_single(Context context, ArrayList<SingleGetChatModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<SingleGetChatModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public Chat_Adapter_single.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_single, viewGroup, false);

        return new Chat_Adapter_single.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof Chat_Adapter_single.ViewHolder) {

            final SingleGetChatModel.Result model = getItem(position);

            final Chat_Adapter_single.ViewHolder genericViewHolder = (Chat_Adapter_single.ViewHolder) holder;

            String UserId = Preference.get(mContext, Preference.KEY_USER_ID);

            if(!UserId.equalsIgnoreCase(model.getSenderId()))
            {
                genericViewHolder.LL_right_layOut.setVisibility(View.GONE);
                genericViewHolder.LL_left_layOut.setVisibility(View.VISIBLE);

                genericViewHolder.messageLeft.setText(model.getChatMessage());


            }else
            {
                genericViewHolder.LL_right_layOut.setVisibility(View.VISIBLE);
                genericViewHolder.LL_left_layOut.setVisibility(View.GONE);

                genericViewHolder.messageRight.setText(model.getChatMessage());
            }

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final Chat_Adapter_single.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private SingleGetChatModel.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, SingleGetChatModel.Result model);

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


           /* this.LL_left_layOut = itemView.findViewById(R.id.itemLeft);
            this.LL_right_layOut = itemView.findViewById(R.id.itemRight);
            this.messageLeft = itemView.findViewById(R.id.messageLeft);
            this.messageRight = itemView.findViewById(R.id.messageRight);
            this.tvNameUserLeft = itemView.findViewById(R.id.tvNameUserLeft);
            this.tvNameUserRight = itemView.findViewById(R.id.tvNameUserRight);
            this.ivUserLeft = itemView.findViewById(R.id.ivUserLeft);
            this.ivUserRight = itemView.findViewById(R.id.ivUserRight);
*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }


}

