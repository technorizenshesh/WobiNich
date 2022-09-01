package com.my.wobinichapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ItemAnswerBinding;
import com.my.wobinichapp.databinding.ItemPostAnswerBinding;
import com.my.wobinichapp.listener.OnAnsChkListener;
import com.my.wobinichapp.model.ChallengeModel;

import java.util.ArrayList;

public class AdapterPostAnswer extends RecyclerView.Adapter<AdapterPostAnswer.MyViewHolder> {
    Context context;
    ArrayList<ChallengeModel.Result.UserAnswer> arrayList;
    OnAnsChkListener listener;

    public AdapterPostAnswer(Context context, ArrayList<ChallengeModel.Result.UserAnswer> arrayList, OnAnsChkListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostAnswerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_post_answer, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (arrayList.get(position) != null) {
            holder.binding.tvUserName.setText(arrayList.get(position).getUsername());
            holder.binding.tvUserAns.setText(arrayList.get(position).getAnswer());
            Glide.with(context).load(arrayList.get(position).getUserimage())
                    .placeholder(R.drawable.user_default).error(R.drawable.user_default).into(holder.binding.ivUser);

            if (arrayList.get(position).getAnsChk().equals("check")) {

                if (arrayList.get(position).getAnswer_is().equals("Wrong")) {
                    holder.binding.ivWrong.setVisibility(View.VISIBLE);
                    holder.binding.ivRight.setVisibility(View.GONE);

                } else if(arrayList.get(position).getAnswer_is().equals("Wrong")) {
                    holder.binding.ivWrong.setVisibility(View.GONE);
                    holder.binding.ivRight.setVisibility(View.VISIBLE);
                }
                else if(arrayList.get(position).getAnswer_is().equals("NotCheck")) {
                    holder.binding.ivWrong.setVisibility(View.VISIBLE);
                    holder.binding.ivRight.setVisibility(View.VISIBLE);
                }


            } else {
                holder.binding.ivWrong.setVisibility(View.VISIBLE);
                holder.binding.ivRight.setVisibility(View.VISIBLE);

            }

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPostAnswerBinding binding;

        public MyViewHolder(@NonNull ItemPostAnswerBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.ivRight.setOnClickListener(v -> listener.onChk(arrayList.get(getAdapterPosition()).getId(), true));

            binding.ivWrong.setOnClickListener(v -> listener.onChk(arrayList.get(getAdapterPosition()).getId(), false));

        }
    }
}
