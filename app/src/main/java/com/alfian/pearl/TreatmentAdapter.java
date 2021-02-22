package com.alfian.pearl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListTreatment> listTreatment;


    public TreatmentAdapter(Context c, ArrayList<ListTreatment> p){
        context = c;
        listTreatment = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new MyViewHolder(LayoutInflater.
                from(context).inflate(R.layout.item_treatment,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {

        myViewHolder.xnama_treatment.setText(listTreatment.get(i).getNama_treatment());
        myViewHolder.xketerangan.setText(listTreatment.get(i).getKeterangan());
        myViewHolder.xharga_treatment.setText(String.valueOf(listTreatment.get(i).getHarga_treatment()));
        Picasso.with(context).load(listTreatment.get(i).getPhoto_treatment()).centerCrop().fit()
                .into(myViewHolder.xphoto_treatment);

        final String getNamaTreatment = listTreatment.get(i).getNama_treatment();

        myViewHolder.button_detail_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gototreatmentdetail = new Intent(context, DetailTreatment.class);
                gototreatmentdetail.putExtra("nama_treatment", getNamaTreatment);
                context.startActivity(gototreatmentdetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTreatment.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_treatment, xketerangan, xharga_treatment;
        ImageView xphoto_treatment;
        Button button_detail_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_treatment = itemView.findViewById(R.id.xnama_treatment);
            xketerangan = itemView.findViewById(R.id.xketerangan);
            xharga_treatment = itemView.findViewById(R.id.xharga_treatment);
            xphoto_treatment = itemView.findViewById(R.id.xphoto_treatment);
            button_detail_item = itemView.findViewById(R.id.button_detail_item);

        }
    }

}
