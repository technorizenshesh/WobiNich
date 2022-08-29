package com.my.wobinichapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.wobinichapp.R;
import com.my.wobinichapp.model.GetAllWordModel;

import java.util.ArrayList;

public class GetAllPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetAllWordModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public GetAllPostAdapter(Context context, ArrayList<GetAllWordModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetAllWordModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grp_all_post, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetAllWordModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;


            genericViewHolder.txtGrpName.setText(model.getComment());

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

        private TextView txtGrpName;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txtGrpName=itemView.findViewById(R.id.txtGrpName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}

