package com.example.shinrai;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.Key;

public class Porfile_page extends AppCompatActivity {


    AppCompatButton myButton;
    Button logout;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_porfile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logout = findViewById(R.id.button3);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Porfile_page.this,"Successfully Logout", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Porfile_page.this, Loginactivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_lefy);
                finish();
            }
        });
        pass = SharedPrefUtil.getInstance(this).getString(Key);

        ImageButton returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(Porfile_page.this, Mainpage.class);
            startActivity(intent);
        });

        myButton = findViewById(R.id.Setpasswd);
        final Context con = this;

        if(pass.isEmpty()){
            myButton.setText("Set Password");
        }
        else {
            myButton.setText("Update Password");
        }

        myButton.setOnClickListener(v -> {
            if(pass.isEmpty()){
                setpassword(con);
            }
            else {
                updatepassword(con);
            }
        });
    }


    String pass;

    static final String Key ="pass";

    private void setpassword(Context con){

        pass = SharedPrefUtil.getInstance(this).getString(Key);

        AlertDialog.Builder dialog = new AlertDialog.Builder( con );
        LinearLayout ll = new LinearLayout(con);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView t1 = new TextView(con);
        t1.setText("Enter The Password");

        ll.addView(t1);

        EditText input = new EditText(con);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        ll.addView(input);

        dialog.setView( ll );

        dialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefUtil.getInstance(con).putString(Key, input.getText().toString());
                        Toast.makeText(con , "Password Set Successfully",Toast.LENGTH_LONG).show();
                        pass = input.getText().toString();
                    }
                }).setNegativeButton("CANCEL", (dialog12, which) -> dialog12.dismiss());
        dialog.show();

    }
    private void updatepassword(Context con){
        AlertDialog.Builder dialog = new AlertDialog.Builder( con );
        LinearLayout ll = new LinearLayout(con);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView t1 = new TextView(con);
        t1.setText("Enter Previous Password");

        ll.addView(t1);

        EditText input = new EditText(con);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        ll.addView(input);


        TextView t2 = new TextView(con);
        t2.setText("Enter New Password");

        ll.addView(t2);

        EditText input2 = new EditText(con);
        input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        ll.addView(input2);

        dialog.setView( ll );

        dialog.setPositiveButton("CONFIRM", (dialog1, which) -> {

            if (pass.equals(input.getText().toString())){
                SharedPrefUtil.getInstance(con).putString(Key , input2.getText().toString());
                Toast.makeText(con , "Password Updated Successfully",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(con , "Sorry Old Password is Incorrect",Toast.LENGTH_LONG).show();
            }

        }).setNegativeButton("CANCEL", (dialog12, which) -> dialog12.dismiss());
        dialog.show();

    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
    }
}