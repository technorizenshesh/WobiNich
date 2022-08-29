package com.my.wobinichapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.act.LoveActivity;
import com.my.wobinichapp.act.YesActivity;
import com.my.wobinichapp.model.CreateFolderModel;
import com.my.wobinichapp.model.GetFolderModel;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.utils.RetrofitClients;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GerFolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetFolderModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    private View promptsView;
    private AlertDialog alertDialog;
    private AlertDialog alertDialog1;

    public GerFolderAdapter(Context context, ArrayList<GetFolderModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetFolderModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_folder, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetFolderModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txtGrpName.setText(model.getFolderName());

            genericViewHolder.relativeFolder.setOnLongClickListener(v -> {
                genericViewHolder.relativeFolder.setBackgroundResource(R.drawable.border_btn);
                PopupMenu popup = new PopupMenu(mContext, genericViewHolder.relativeFolder);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.poupup_menu_delete_option, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.DeleteFolder) {

                            AlertFolder(model.getId(),position);

                        } else if (id == R.id.RenameFolder) {

                            AlertFolderRename(model.getId(),model.getFolderName());

                        }else if (id == R.id.ChaneFolderImage) {

                            AlertCreateFolder();

                        }
                        return true;
                    }
                });
                popup.show();

                return false;
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetFolderModel.Result getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, GetFolderModel.Result model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGrpName;
        private ImageView ImgIcon;
        private RelativeLayout relativeFolder;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.txtGrpName=itemView.findViewById(R.id.txtGrpName);
            this.relativeFolder=itemView.findViewById(R.id.relativeFolder);
          //  this.ImgIcon=itemView.findViewById(R.id.ImgIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }

    private void AlertFolder(String id,int position) {

        LayoutInflater li;
        TextView txtSave;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(mContext);
        promptsView = li.inflate(R.layout.alert_delete_folder, null);
        txtSave = (TextView) promptsView.findViewById(R.id.txtSave);
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptsView);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFolderMethod(id);
                modelList.remove(position);
                notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void AlertFolderRename(String FolderId,String FolderName) {

        LayoutInflater li;
        TextView txtSave;
        TextView edtName;
        TextView txtCancel;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(mContext);
        promptsView = li.inflate(R.layout.alert_rename_folder, null);
        txtSave = (TextView) promptsView.findViewById(R.id.txtSave);
        txtCancel = (TextView) promptsView.findViewById(R.id.txtCancel);
        edtName = (EditText) promptsView.findViewById(R.id.edtName);
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptsView);

        edtName.setText(FolderName);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FoldeName1=edtName.getText().toString();
                if(!FoldeName1.equalsIgnoreCase(""))
                {
                    alertDialog.dismiss();
                    ChangeFolderNameMethod(FolderId,FoldeName1);
                }else
                {
                    Toast.makeText(mContext, "Please enter Folder Name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void DeleteFolderMethod(String id)
    {
        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .delete_folder(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("Remove>>>","");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


    public void ChangeFolderNameMethod(String folder_id,String Name)
    {
        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .folder_rename(folder_id,Name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(mContext, "Success.", Toast.LENGTH_SHORT).show();
                    ((YesActivity)mContext).dialog();

                    Log.e("Remove>>>","");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    private void AlertCreateFolder() {

        LayoutInflater li;
        TextView txtSave;
        TextView txtCancel;
        EditText edtName;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(mContext);
        promptsView = li.inflate(R.layout.alert_folder_image, null);
        txtSave = (TextView) promptsView.findViewById(R.id.txtSave);
        txtCancel = (TextView) promptsView.findViewById(R.id.txtCancel);
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptsView);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        txtCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }



}

