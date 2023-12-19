package com.example.suleiman_smarthomeenergymanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapterViewAlarms extends RecyclerView.Adapter <ImageAdapterViewAlarms.ImageViewHolder> {
    private Context mContext;
    private List<Model> mUploads;
    //private ImageAdapter2.buttonClickListener buttonClickListener;


    public ImageAdapterViewAlarms(Context context, List<Model> userUploads){
        mContext= context;
        mUploads= userUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item_view_alarm, parent, false);
        return new ImageViewHolder(v);
        // return new ImageViewHolder(v, buttonClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Model uploadCurrent= mUploads.get(position);
        holder.txtTitle.setText(uploadCurrent.getTitle());
        holder.txtDate.setText("Alarm Date: "+ uploadCurrent.getDate());
        holder.txtTime.setText("Alarm Time: " +uploadCurrent.getTime());

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public TextView txtDate;
        public TextView txtTime;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle1);
            txtDate = itemView.findViewById(R.id.txtDate1);
            txtTime = itemView.findViewById(R.id.txtTime1);
        }
    }
}
