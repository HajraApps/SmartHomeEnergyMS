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

public class ImageAdapterDeleteAlarm extends RecyclerView.Adapter <ImageAdapterDeleteAlarm.ImageViewHolder> {
    private Context mContext;
    private List<Model> mUploads;
    // private ImageAdapter1.OnItemClickListener mListener;
    private buttonClickListener buttonClickListener;


    public ImageAdapterDeleteAlarm(Context context, List<Model> userUploads){
        mContext= context;
        mUploads= userUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item_delete_alarm, parent, false);
        return new ImageViewHolder(v, buttonClickListener);

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

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtTitle;
        public TextView txtDate;
        public TextView txtTime;
        Button btnComplete;
        buttonClickListener buttonClickListener;

        public ImageViewHolder(@NonNull View itemView, buttonClickListener buttonClickListener) {
            super(itemView);
            txtTitle= itemView.findViewById(R.id.txtTitle);
            txtDate= itemView.findViewById(R.id.txtDate);
            txtTime= itemView.findViewById(R.id.txtTime);
            btnComplete= itemView.findViewById(R.id.btnDelete);

            this.buttonClickListener= buttonClickListener;
            btnComplete.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (buttonClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    buttonClickListener.onbuttonClick(position);
                }
            }
        }


    }

    public interface buttonClickListener{
        void onbuttonClick(int position);
    }

    public void setOnButtonClickListener(buttonClickListener listener){
        buttonClickListener= listener;
    }



}
