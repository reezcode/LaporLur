package com.reaz.laporlur;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.reaz.laporlur.R;

public class BeritaViewHolder extends RecyclerView.ViewHolder {

    public TextView judul;
    public TextView isi;
    public ImageView urlimage;
    public ImageView btnOpen;

    public BeritaViewHolder(@NonNull View itemView) {
        super(itemView);
        judul = itemView.findViewById(R.id.judul);
        isi = itemView.findViewById(R.id.isi);
        urlimage = itemView.findViewById(R.id.urlimage);
        btnOpen = itemView.findViewById(R.id.btnopen);
    }

    public void bindToBerita(Berita berita, View.OnClickListener onClickListener) {
        judul.setText(berita.judul);
        isi.setText(berita.isi);
        Glide.with(urlimage.getContext()).load(berita.urlimage).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(urlimage);
        btnOpen.setOnClickListener(onClickListener);
    }

}
