package com.reaz.laporlur;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ListUserViewHolder extends RecyclerView.ViewHolder {

    public TextView namauser;
    public CardView listuserbtn;

    public ListUserViewHolder(@NonNull View itemView) {
        super(itemView);
        namauser = itemView.findViewById(R.id.namauser);

        listuserbtn = itemView.findViewById(R.id.listuser_btn);
    }

    public void bindToLaporan(ListLUser listLUser, View.OnClickListener onClickListener) {
        namauser.setText(listLUser.nama);
        listuserbtn.setOnClickListener(onClickListener);
    }

}
