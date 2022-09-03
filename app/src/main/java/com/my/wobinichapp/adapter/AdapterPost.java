package com.my.wobinichapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.act.EmailActivity;
import com.my.wobinichapp.databinding.ItemAnswerBinding;
import com.my.wobinichapp.listener.OnAnsChkListener;
import com.my.wobinichapp.listener.OnAnsRightWrongListener;
import com.my.wobinichapp.listener.OnAnswerListener;
import com.my.wobinichapp.model.ChallengeModel;

import java.util.ArrayList;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.MyViewHolder> implements OnAnsChkListener {
    Context context;
    ArrayList<ChallengeModel.Result> arrayList;
    OnAnswerListener listener;
    OnAnsRightWrongListener listener2;

    public AdapterPost(Context context, ArrayList<ChallengeModel.Result> arrayList, OnAnswerListener listener, OnAnsRightWrongListener listener2) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
        this.listener2 = listener2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnswerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_answer, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.tvPostDate.setText(arrayList.get(position).getDateTime());
        Glide.with(context).load(arrayList.get(position).getImage())
                .placeholder(R.drawable.dummy).
                error(R.drawable.dummy).into(holder.binding.imgpurple11);

        if (arrayList.get(position).getUserId().equals(Preference.get(context, Preference.KEY_USER_ID))) {
            holder.binding.rlMain.setVisibility(View.VISIBLE);
            //  holder.binding.llOne.setVisibility(View.VISIBLE);
            holder.binding.RRBottom.setVisibility(View.GONE);
            holder.binding.tvUserName.setText(arrayList.get(position).getGroupName());
            holder.binding.rvPostAnswer.setVisibility(View.VISIBLE);
            holder.binding.rvPostAnswer.setAdapter(new AdapterPostAnswer(context, arrayList.get(position).getUserAnswer(), AdapterPost.this));

        } else {
            if (arrayList.get(position).getAnswered().equals("Yes")) {
                holder.binding.rlMain.setVisibility(View.GONE);
                //     holder.binding.llOne.setVisibility(View.GONE);
                holder.binding.RRBottom.setVisibility(View.GONE);

            } else if (arrayList.get(position).getAnswered().equals("check")) {
                holder.binding.rlMain.setVisibility(View.VISIBLE);
                //     holder.binding.llOne.setVisibility(View.GONE);
                holder.binding.RRBottom.setVisibility(View.GONE);

            } else if (arrayList.get(position).getAnswered().equals("No")) {
                holder.binding.rlMain.setVisibility(View.VISIBLE);
                //     holder.binding.llOne.setVisibility(View.VISIBLE);
                holder.binding.RRBottom.setVisibility(View.VISIBLE);
            }
            holder.binding.rvPostAnswer.setVisibility(View.GONE);
            holder.binding.tvUserName.setText(arrayList.get(position).getUserName());


        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onChk(String id, boolean chk) {
        listener2.onRightWrong(id, chk);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemAnswerBinding binding;

        public MyViewHolder(@NonNull ItemAnswerBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.LLSend.setOnClickListener(v -> {
                listener.onAnswer(getAdapterPosition(), binding.edAnswer.getText().toString());
                binding.edAnswer.setText("");
            });
        }
    }
}
