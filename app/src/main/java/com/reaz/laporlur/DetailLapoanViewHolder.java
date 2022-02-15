package com.reaz.laporlur;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailLapoanViewHolder extends RecyclerView.ViewHolder {

    public TextView judul;
    public TextView isi;
    public TextView waktutxt;
    public ImageView tanggapan;
    public CardView detaillaporanbtn;

    public DetailLapoanViewHolder(@NonNull View itemView) {
        super(itemView);
        judul = itemView.findViewById(R.id.judultxt);
        isi = itemView.findViewById(R.id.isitxt);
        waktutxt = itemView.findViewById(R.id.waktutxt);
        tanggapan = itemView.findViewById(R.id.statustanggapan);
        detaillaporanbtn = itemView.findViewById(R.id.detaillaporanbtn);
    }

    public void bindToLaporan(ListLaporan listLaporan, View.OnClickListener onClickListener) {
        judul.setText(listLaporan.judul);
        isi.setText(listLaporan.isi);
        waktutxt.setText(listLaporan.waktu);
        Glide.with(tanggapan.getContext()).load(listLaporan.tanggapan).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(tanggapan);
        detaillaporanbtn.setOnClickListener(onClickListener);
    }

}
