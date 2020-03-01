package com.example.weathertest.mvp.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.weathertest.R;
import com.example.weathertest.mvp.Main2Activity;

import java.util.List;




public class RvAdapter extends RecyclerView.Adapter<RvAdapter.PersonViewHolder> {
    private List<Cities> states;
    private ClickListener listener;

   private Context context;


    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView citysCity;
        ImageView citysLogo;
        TextView citysTemp;

        PersonViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            citysCity = (TextView)itemView.findViewById(R.id.citys_city);
            citysLogo = (ImageView)itemView.findViewById(R.id.citys_logo);
            citysTemp = (TextView)itemView.findViewById(R.id.citys_temp);
            itemView.setOnClickListener(this);



        }



        @Override
        public void onClick(View v) {
            Log.d("test1", String.valueOf(getAdapterPosition()));
            Intent intent = new Intent(context, Main2Activity.class);
            intent.putExtra("city", states.get(getAdapterPosition()).getCity());
            intent.putExtra("logo", "w" + states.get(getAdapterPosition()).getLogo());
            intent.putExtra("temp", TempUpper(states.get(getAdapterPosition()).getTemp()));
            intent.putExtra("desc", states.get(getAdapterPosition()).getDesc());
            intent.putExtra("temp2", TempUpper(states.get(getAdapterPosition()).getTemp2()));
            intent.putExtra("humid", states.get(getAdapterPosition()).getHumidity());
            intent.putExtra("presure", states.get(getAdapterPosition()).getPressure());
            intent.putExtra("wind", states.get(getAdapterPosition()).getWind());
          //  Log.d("test1", String.valueOf(position));
            context.startActivity(intent);

            }


        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }




    



    public RvAdapter(List<Cities> states, ClickListener listener){
        this.states = states;
        this.listener = listener;
        // notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //  Log.d(TAG2, "onAttachedToRecyclerView ");
    }



    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        return new PersonViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false), listener);


    }
    @Override
    public void
    onViewDetachedFromWindow(@NonNull PersonViewHolder holder){



        super.onViewDetachedFromWindow(holder);
        //  Log.d(TAG2, "onViewDetachedFromWindow ");

    }
    @Override
    public void
    onViewAttachedToWindow(PersonViewHolder holder){


        super.onViewAttachedToWindow(holder);
        // notifyItemChanged(positionOld);


        //  Log.d(TAG2, "onViewAttachedToWindow ");
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.citysCity.setText(states.get(position).getCity());
        String icon = "w" + states.get(position).getLogo();
        holder.citysLogo.setImageResource(context.getResources().getIdentifier(icon, "drawable", context.getPackageName()));
        holder.citysTemp.setText(TempUpper(states.get(position).getTemp()));



    }

    @Override
    public int getItemCount() {
        //if(states==null) return 0;
        return states.size();

    }

    public String TempUpper(String temp){

        double result = Double.parseDouble(temp);
        long temp2=Math.round(result);
        if (temp2>0){temp="+"+temp2+"°";} else {temp=temp2+"°";}

        return temp;
    }

}

