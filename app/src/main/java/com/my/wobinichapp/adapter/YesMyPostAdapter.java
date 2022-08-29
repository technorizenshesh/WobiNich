package com.my.wobinichapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.my.wobinichapp.R;
import com.my.wobinichapp.model.GetFolderModel;
import com.my.wobinichapp.model.GetPostModel;

import java.util.ArrayList;

public class YesMyPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetPostModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public YesMyPostAdapter(Context context, ArrayList<GetPostModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetPostModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_yes_post, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetPostModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.relative_searchBtn.setOnClickListener(v -> {
                Toast.makeText(mContext, "Single Time", Toast.LENGTH_SHORT).show();
            });
            genericViewHolder.relative_searchBtn.setOnLongClickListener(v -> {

                Toast.makeText(mContext, "Long Time", Toast.LENGTH_SHORT).show();
                return false;
            });
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

    private GetPostModel.Result getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, GetPostModel.Result model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGrpName;
        private ImageView ImgIcon;
        private RelativeLayout relative_searchBtn;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.txtGrpName=itemView.findViewById(R.id.txtGrpName);
            this.relative_searchBtn=itemView.findViewById(R.id.relative_searchBtn);
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

