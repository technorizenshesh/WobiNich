package com.my.wobinichapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.wobinichapp.R;
import com.my.wobinichapp.model.HomModel;

import java.util.ArrayList;

public class GrpListDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<HomModel> modelList;
    private OnItemClickListener mItemClickListener;

    public GrpListDetailsAdapter(Context context, ArrayList<HomModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<HomModel> modelList) {
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
            final HomModel model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txtGrpName.setText(model.getName());

          /*
          if(position ==5)
            genericViewHolder.ImgIcon.setImageResource(R.drawable.img_yellow);
            else
                genericViewHolder.ImgIcon.setImageResource(R.drawable.grp_icon);
                */
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private HomModel getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, HomModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGrpName;
        private ImageView ImgIcon;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.txtGrpName=itemView.findViewById(R.id.txtGrpName);
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

