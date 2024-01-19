package com.example.applicationsalle.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.applicationsalle.R;
import com.example.applicationsalle.entity.ReservationEntity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<ReservationEntity> reservations = new ArrayList<>();
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteClickListener;
    private OnUpdateClickListener updateClickListener;

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ReservationEntity currentReservation = reservations.get(position);
        holder.textViewDateDebut.setText("DateDebut: " + formatDate(currentReservation.getDateDebut()));
        holder.textViewDateFin.setText("DateFin: " + formatDate(currentReservation.getDateFin()));
        holder.textViewDescription.setText("Description: " + currentReservation.getDescription());
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public void setOnUpdateClickListener(OnUpdateClickListener listener) {
        this.updateClickListener = listener;
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDateDebut;
        private TextView textViewDateFin;
        private TextView textViewDescription;
        private ImageView deleteIcon;
        private ImageView updateIcon;

        public ReservationViewHolder(View itemView) {
            super(itemView);

            textViewDateDebut = itemView.findViewById(R.id.textViewDateDebut);
            textViewDateFin = itemView.findViewById(R.id.textViewDateFin);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            deleteIcon = itemView.findViewById(R.id.item_delete);
            updateIcon = itemView.findViewById(R.id.item_update);


            // Set click listener for the item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(reservations.get(position));
                    }
                }
            });

            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (deleteClickListener != null && position != RecyclerView.NO_POSITION) {
                        deleteClickListener.onDeleteClick(reservations.get(position));
                    }
                }
            });

            updateIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (updateClickListener != null && position != RecyclerView.NO_POSITION) {
                        updateClickListener.onUpdateClick(reservations.get(position));
                    }
                }
            });
        }


    }

    public interface OnItemClickListener {
        void onItemClick(ReservationEntity reservation);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(ReservationEntity reservation);
    }

    public interface OnUpdateClickListener {
        void onUpdateClick(ReservationEntity reservation);
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }
}
