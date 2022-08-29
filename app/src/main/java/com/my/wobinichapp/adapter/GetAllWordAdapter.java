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
import com.my.wobinichapp.act.EmailActivity;
import com.my.wobinichapp.model.GetAllWordModel;
import com.my.wobinichapp.model.GetGrpModel;

import java.util.ArrayList;

public class GetAllWordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetAllWordModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public GetAllWordAdapter(Context context, ArrayList<GetAllWordModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetAllWordModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grp_all_worl, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetAllWordModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;


            genericViewHolder.txtName.setText(model.getComment());
            genericViewHolder.txtPlace.setText(model.getMaps());
            genericViewHolder.txtDate.setText(model.getDateTime());

            Glide.with(mContext).load(model.getImage()).placeholder(R.drawable.frame).into(genericViewHolder.imgPost);

        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetAllWordModel.Result getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, GetAllWordModel.Result model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtPlace;
        private TextView txtDate;
        private ImageView imgPost;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txtName=itemView.findViewById(R.id.txtName);
            this.txtPlace=itemView.findViewById(R.id.txtPlace);
            this.txtDate=itemView.findViewById(R.id.txtDate);
            this.imgPost=itemView.findViewById(R.id.imgPost);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}

