package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityAllPostViewBinding;

public class AllPostView extends AppCompatActivity {

    ActivityAllPostViewBinding binding;
    boolean idImg = false;
    boolean idImg1 = true;
    boolean idImg2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_all_post_view);

        binding.RRBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.RRAdd.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(AllPostView.this, binding.RRAdd);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.poupup_menu_yes, popup.getMenu());
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.CreateFolder) {

                    } else if (id == R.id.DeleteFolder) {

                    }
                    return true;
                }
            });
            popup.show();
        });


        binding.img2.setOnClickListener(v -> {

            if (idImg) {
                binding.LLMainFile.setVisibility(View.VISIBLE);
                idImg = false;
            } else {
                binding.LLMainFile.setVisibility(View.GONE);
                idImg = true;
            }
        });


        binding.img4.setOnClickListener(v -> {

            if (idImg1) {
                binding.LLMainFileBlue.setVisibility(View.VISIBLE);
                idImg1 = false;
            } else {
                binding.LLMainFileBlue.setVisibility(View.GONE);
                idImg1 = true;
            }
        });

        binding.img3.setOnClickListener(v -> {

            if (idImg2) {
                binding.LLMainFilePurple.setVisibility(View.VISIBLE);
                idImg2 = false;
            } else {
                binding.LLMainFilePurple.setVisibility(View.GONE);
                idImg2 = true;
            }
        });

        binding.llGreenOne.setOnClickListener(v -> {
            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));
           // startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));
        });

        binding.llBlueOne.setOnClickListener(v -> {
            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));
        });

        binding.RRbluewThree.setOnClickListener(v -> {
            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));
        });

        binding.RRGreenTwo.setOnClickListener(v -> {
            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));
        });

        binding.RRGreenThree.setOnClickListener(v -> {
            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));
        });


        binding.LLMainFile.setOnClickListener(v -> {

            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));

        });
        binding.imgblue.setOnClickListener(v -> {
            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));
        });

        binding.llBlueOne.setOnClickListener(v -> {

            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));

        });

        binding.LLMainFileBlue.setOnClickListener(v -> {

            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));

        });

        binding.imgGreen.setOnClickListener(v -> {

            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));

        });

        binding.llGreenOne.setOnClickListener(v -> {

            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));

        });

        binding.imgPurple.setOnClickListener(v -> {

            startActivity(new Intent(AllPostView.this, WobiNichCommentActivity.class));

        });

        binding.llgi.setOnClickListener(v -> {

            startActivity(new Intent(AllPostView.this, WobiNichCommentActivity.class));

        });

        binding.LLMainFilePurple.setOnClickListener(v -> {

            startActivity(new Intent(AllPostView.this, WobiNichCommentActivity.class));

        });

        binding.RRbluewTwo.setOnClickListener(v -> {
            startActivity(new Intent(AllPostView.this, WobinichDetailsActivity.class));
        });
    }
}