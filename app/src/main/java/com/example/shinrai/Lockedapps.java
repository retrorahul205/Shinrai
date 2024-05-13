package com.example.shinrai;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Lockedapps extends AppCompatActivity {
    RecyclerView recyclerView;
    List<app_model> appModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    app_adapter adapter;

    Context con;
    ImageButton goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lockedapps);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.newone);

        adapter = new app_adapter(appModelList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager( this));

        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                getinstalledapps();
            }
        });

        goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void onResume() {

        super.onResume();

        progressDialog.setTitle("Lets take a quick look at your android");
        progressDialog.setMessage("loading");
        progressDialog.show();
    }


    public void getinstalledapps(){
        con=this;
        List<String> list;
        list = SharedPrefUtil.getInstance(con).getliststring();

        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);

        for(int i=0 ; i < packageInfos.size() ; i ++){
            String name = packageInfos.get(i).applicationInfo.loadLabel(getPackageManager()).toString();

            Drawable icon = packageInfos.get(i).applicationInfo.loadIcon(getPackageManager());

            String packname = packageInfos.get(i).packageName;



            if (!list.isEmpty()) {
                if (list.contains(packname)) {
                    appModelList.add(new app_model(name, icon, 1, packname));
                } else {
                    continue;
                }
            }
        }

        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

}