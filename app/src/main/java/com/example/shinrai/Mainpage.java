package com.example.shinrai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AppOpsManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class Mainpage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton buttondrawertoggle;
    RecyclerView recyclerView;
    List<app_model> appModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    app_adapter adapter;
Button btn;
    private Context con;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


        drawerLayout =findViewById(R.id.drawerlayout);
        buttondrawertoggle = findViewById(R.id.returnbutton);
        navigationView = findViewById(R.id.navigationview);
        buttondrawertoggle.setOnClickListener(v -> drawerLayout.open());

        recyclerView = findViewById(R.id.applocklist);

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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                if(itemId == R.id.profile){
                    Toast.makeText(Mainpage.this,"Profile Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(Mainpage.this, Porfile_page.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_lefy);
                }
                if(itemId == R.id.help){
                    Toast.makeText(Mainpage.this,"Permissions Clicked", Toast.LENGTH_SHORT).show();
                    if (!isAccessGranted()) {
                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Mainpage.this,"Not Allowed", Toast.LENGTH_SHORT).show();
                    }
                }
                if(itemId == R.id.share){
                    Toast.makeText(Mainpage.this,"Redirecting", Toast.LENGTH_SHORT).show();
                    gotoUrl("https://mail.google.com/mail/u/0/#inbox");
                }
                if(itemId == R.id.weblink){
                    Toast.makeText(Mainpage.this,"Redirecting", Toast.LENGTH_SHORT).show();
                    gotoUrl("https://preetindersingh7.github.io/Shinrai/");
                }
                drawerLayout.close();

                return false;
            }
            private void gotoUrl(String s){
                Uri uri=Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }

            private boolean isAccessGranted() {
                try {
                    PackageManager packageManager = getPackageManager();
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
                    AppOpsManager appOpsManager = (AppOpsManager) getSystemService(APP_OPS_SERVICE);
                    int mode = 0;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                                applicationInfo.uid, applicationInfo.packageName);
                    }
                    return (mode == AppOpsManager.MODE_ALLOWED);

                } catch (PackageManager.NameNotFoundException e) {
                    return false;
                }
            }

            private void openEmailApp() {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"rahul205chaudhary@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Help Request");
                intent.setPackage("com.google.android.gm");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(Mainpage.this, "Gmail app not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn =findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Mainpage.this,Lockedapps.class);
                startActivity(intent);
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
                    appModelList.add(new app_model(name, icon, 0, packname));
                }
            }
            else {
                appModelList.add(new app_model(name, icon, 0, packname));
            }
        }

        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }



}