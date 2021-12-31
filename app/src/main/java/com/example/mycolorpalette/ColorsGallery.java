package com.example.mycolorpalette;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ColorsGallery extends AppCompatActivity {


    Button Buttons[];
    Random Rand;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors_gallery);
        InitProperties();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void InitProperties() {
        Rand = new Random();
        Buttons = new Button[]{findViewById(R.id.buttonGallery1), findViewById(R.id.buttonGallery2), findViewById(R.id.buttonGallery3), findViewById(R.id.buttonGallery4),
                findViewById(R.id.buttonGallery5), findViewById(R.id.buttonGallery6), findViewById(R.id.buttonGallery8), findViewById(R.id.buttonGallery9),
                findViewById(R.id.buttonGallery10), findViewById(R.id.buttonGallery11), findViewById(R.id.buttonGallery12), findViewById(R.id.buttonGallery13),
                findViewById(R.id.buttonGallery14), findViewById(R.id.buttonGallery15), findViewById(R.id.buttonGallery16) , findViewById(R.id.buttonGallery17),
                findViewById(R.id.buttonGallery18), findViewById(R.id.buttonGallery19), findViewById(R.id.buttonGallery20), findViewById(R.id.buttonGallery21),
                findViewById(R.id.buttonGallery22)};



        GenerateColors();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GenerateColors() {
        String NumberHex = "";
        String numberHexPlus="";
        for (int i = 0; i < Buttons.length; i++) {
            int r = Rand.nextInt(255);
            int g = Rand.nextInt(255);
            int b = Rand.nextInt(255);

            NumberHex = TranslateDecToEx(r, g, b);
            Buttons[i].setText(NumberHex.toUpperCase());
            Color randomColor = Color.valueOf(Color.rgb(r,g,b));
            if (r + g + b <= 0xff * 3 / 2) {
                Buttons[i].setTextColor(Color.parseColor("#ffffff"));
            }
            else{
                Buttons[i].setTextColor(Color.parseColor("#000000"));
            }
            Buttons[i].setBackgroundColor(Color.parseColor("#"+NumberHex));
    }


}

    private String TranslateDecToEx(int r, int g, int b) {
        String NumberHex = "";
        String Conversor="";
        int[] ColorHex = new int[3];
        ColorHex[0] = r;
        ColorHex[1] = g;
        ColorHex[2] = b;

        for (int j = 0; j < ColorHex.length; j++) {
            Conversor=Integer.toHexString(ColorHex[j]);
            if (Conversor.length()<2){
                Conversor="0"+Integer.toHexString(ColorHex[j]);
            }
            NumberHex += Conversor;
        }

        return NumberHex;
    }

    public void ButtonCopyCode(View view) {
        Button b = (Button)view;
        String value = b.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", value);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(ColorsGallery.this, "Copied", Toast.LENGTH_SHORT).show();
        openMainActivity(value);
    }

    private void openMainActivity(String name) {
        Bundle BundleColor = new Bundle();
        BundleColor.putString("NombreColor",name);
        BundleColor.putString("CalcularPaleta","si");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(BundleColor);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void RandomizeColor(View view) {
        GenerateColors();
    }

    public void BackMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}