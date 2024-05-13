package com.example.shinrai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.common.initializedfields.qual.EnsuresInitializedFields;

import java.util.ArrayList;
import java.util.List;

public class app_adapter extends RecyclerView.Adapter<app_adapter.adapter_design_backend> {

    List<app_model> appModels = new ArrayList<>();
    List<String> lockedapps = new ArrayList<>();

    public app_adapter(List<app_model> appModels, Context con) {
        this.appModels = appModels;
        this.con = con;
    }

    Context con;

    @NonNull
    @Override
    public adapter_design_backend onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(con).inflate(R.layout.adapter_design , parent, false);
        adapter_design_backend design = new adapter_design_backend( view);
        return design;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_design_backend holder, int position) {

        app_model app =appModels.get(position);

        holder.appname.setText(app.getAppname());
        holder.appicon.setImageDrawable(app.getAppicon());

        if ( app.getStatus() == 0){
            holder.appstatus.setImageResource(R.drawable.unlock);
        }
        else {
            holder.appstatus.setImageResource(R.drawable.lock);
            lockedapps.add(app.getPackagename());
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app.getStatus() == 0){
                    app.setStatus(1);
                    holder.appstatus.setImageResource(R.drawable.lock);
                    Toast.makeText(con,app.getAppname()+" is Locked",Toast.LENGTH_LONG).show();
                    lockedapps.add(app.getPackagename());
                    SharedPrefUtil.getInstance(con).putliststring(lockedapps);
                }
                else {
                    app.setStatus(0);
                    holder.appstatus.setImageResource(R.drawable.unlock);
                    Toast.makeText(con,app.getAppname()+" is Unlocked",Toast.LENGTH_LONG).show();
                    lockedapps.remove(app.getPackagename());
                    SharedPrefUtil.getInstance(con).putliststring(lockedapps);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appModels.size();
    }

    public class adapter_design_backend extends RecyclerView.ViewHolder{

        TextView appname;
        ImageView appicon, appstatus;

        public adapter_design_backend(@NonNull View itemView) {
            super(itemView);
            appname = itemView.findViewById(R.id.appname);
            appicon = itemView.findViewById(R.id.appicon);
            appstatus = itemView.findViewById(R.id.appstatus);
        }
    }
}