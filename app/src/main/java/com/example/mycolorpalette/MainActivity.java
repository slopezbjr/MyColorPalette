package com.example.mycolorpalette;

import static androidx.core.widget.TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView JtextArr[];
    TextView JLabelArr[];
    CheckBox CheckBoxArr[];
    Random Rand;
    int contador;
    String CalorChange;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigProperties();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ConfigProperties() {
        CalorChange="hola";
        Rand = new Random();
        JtextArr = new TextView[]{findViewById(R.id.Color1), findViewById(R.id.Color2), findViewById(R.id.Color3), findViewById(R.id.Color4), findViewById(R.id.Color5)};
        JLabelArr = new TextView[]{findViewById(R.id.textViewTextColor), findViewById(R.id.textViewTextColor1), findViewById(R.id.textViewTextColor2), findViewById(R.id.textViewTextColor3), findViewById(R.id.textViewTextColor4)};
        CheckBoxArr = new CheckBox[]{findViewById(R.id.checkBoxColor1),findViewById(R.id.checkBoxColor2),findViewById(R.id.checkBoxColor3),findViewById(R.id.checkBoxColor4),findViewById(R.id.checkBoxColor5)};
        FirstColorRandom();
        Intent recibir = getIntent();
        String datos = recibir.getStringExtra("NombreColor");
        String CP = recibir.getStringExtra("CalcularPaleta");
        if (datos!=null){
            searchAndCopy(datos);
        }
        if (CP!=null){
            if (CP.equals("si")){
                ButtonOnClick(findViewById(R.id.button));
                CP="no";
            }

        }
        NewPalette();
    }

    private void NewPalette() {

        EditText text = (EditText)findViewById(R.id.TextCodigoColor);
        String value = text.getText().toString();

        if (value!=null){
            CalorChange=value;
            if (CalorChange!=null){
                CalorChange="hola";
            }
        }else{
            CalorChange="hola";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void FirstColorRandom() {
        String NumberHex = "";
        String numberHexPlus="";
        for (int i = 0; i < JLabelArr.length; i++) {
            int r = Rand.nextInt(255);
            int g = Rand.nextInt(255);
            int b = Rand.nextInt(255);
            NumberHex = TranslateDecToEx(r, g, b);
            JLabelArr[i].setText(NumberHex.toUpperCase());
            Color randomColor = Color.valueOf(Color.rgb(r,g,b));
            CheckBox cb = (CheckBox) findViewById(R.id.checkBoxColor2);
            if (r + g + b <= 0xff * 3 / 2) {
                JLabelArr[i].setTextColor(Color.parseColor("#ffffff"));
            }
            else{
                JLabelArr[i].setTextColor(Color.parseColor("#000000"));
            }

            JtextArr[i].setBackgroundColor(Color.parseColor("#"+NumberHex));
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

    public void ButtonOnClick(View view) {

        int r = 0, g = 0, b = 0;
        boolean NCharsOK = false, HexadecimalV = true;
        String PossibleColor, PossibleNumber;
        String[] StringArr = new String[3];
        String NumberHex = "";
        int[] ArrInt = new int[3];
        EditText text = (EditText)findViewById(R.id.TextCodigoColor);
        String value = text.getText().toString();

        char[] chars = value.toCharArray();
        NCharsOK = NChars(chars);
        StringArr = PartirChars(value);
        if (NCharsOK == true) {
            for (int i = 0; i < StringArr.length; i++) {
                if (HexadecimalV == true) {
                    PossibleNumber = StringArr[i];
                    HexadecimalV = HexVerification(PossibleNumber);
                } else {
                    HexadecimalV = false;
                }
            }
            if (HexadecimalV == true) {
                ArrInt = TraductorHexToDec(StringArr);
                r = ArrInt[0];
                g = ArrInt[1];
                b = ArrInt[2];

                if (CalorChange.equals(value)){
                    contador=1;
                }else{
                    contador=0;
                    for (int i = 0; i < JtextArr.length; i++){
                        CheckBoxArr[0].setChecked(false);
                    }
                }
                for (int i = 0; i < JtextArr.length; i++) {
                    if (CheckBoxArr[i].isChecked()==false){
                        if (contador == 0) {
                            CheckBoxArr[0].setChecked(true);
                            Color color = CreateColor(r, g, b);
                            NumberHex = TranslateDecToEx(r, g, b);
                            JLabelArr[i].setText(NumberHex.toUpperCase());
                            if (r + g + b <= 0xff * 3 / 2) {
                                JLabelArr[i].setTextColor(Color.parseColor("#ffffff"));
                            }
                            else{
                                JLabelArr[i].setTextColor(Color.parseColor("#000000"));
                            }
                            JtextArr[i].setBackgroundColor(Color.parseColor("#"+NumberHex));
                            contador++;
                        } else {
                            for (int j = 0; j < 3; j++) {
                                if (j == 1) {
                                    int max = r;
                                    int min = r - 50;
                                    r = NumRandom(max, min);
                                } else if (j == 2) {
                                    int max = g;
                                    int min = g - 50;
                                    g = NumRandom(max, min);
                                } else if (j == 3) {
                                    int max = b;
                                    int min = b - 50;
                                    b = NumRandom(max, min);
                                }
                            }
                        }
                        NumberHex = TranslateDecToEx(r, g, b);
                        JLabelArr[i].setText(NumberHex.toUpperCase());
                        Color color = CreateColor(r, g, b);
                        if (r + g + b <= 0xff * 3 / 2) {
                            JLabelArr[i].setTextColor(Color.parseColor("#ffffff"));
                        }
                        else{
                            JLabelArr[i].setTextColor(Color.parseColor("#000000"));
                        }
                        JtextArr[i].setBackgroundColor(Color.parseColor("#"+NumberHex));
                    }
                }
            }
            CalorChange=value;
        }





    }

    private int NumRandom(int max, int min) {
        int random;
        if (min < 0) {
            min = 0;
            max = 100;
            random = (int) (Math.random() * (max - min) + min);
        } else {
            random = (int) (Math.random() * (max - min) + min);
        }

        return random;
    }

    private Color CreateColor(int r, int g, int b) {
        Color randomColor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            randomColor = Color.valueOf(Color.rgb(r,g,b));
        }
        return randomColor;

    }

    private int[] TraductorHexToDec(String[] StringArr) {
        int TraducirStrToInt;
        int[] ArrInt = new int[3];
        for (int i = 0; i < StringArr.length; i++) {
            TraducirStrToInt = Integer.parseInt(StringArr[i], 16);
            ArrInt[i] = TraducirStrToInt;
        }
        return ArrInt;
    }


    private boolean HexVerification(String PossibleNumber) {
        if (PossibleNumber.length() == 0
                || (PossibleNumber.charAt(0) != '-' && Character.digit(PossibleNumber.charAt(0), 16) == -1)) {
            String message = "Numero no valido";
            AlertMessage(message);
            return false;
        }
        if (PossibleNumber.length() == 1 && PossibleNumber.charAt(0) == '-') {
            String message = "Numero no valido";
            AlertMessage(message);
            return false;
        }

        for (int i = 1; i < PossibleNumber.length(); i++) {
            if (Character.digit(PossibleNumber.charAt(i), 16) == -1) {
                String message = "Numero no valido";
                AlertMessage(message);
                return false;
            }
        }
        return true;


    }

    private String[] PartirChars(String PossibleColor) {
        int pos1 = 0, pos2 = 2;
        String number = PossibleColor;
        String[] StringArr = new String[3];
        char[] chars1 = new char[2];

        try {
            for (int i = 0; i < StringArr.length; i++) {
                number.getChars(pos1, pos2, chars1, 0);
                StringArr[i] = String.valueOf(chars1);
                pos1 = pos2;
                pos2 = pos2 + 2;
            }

        } catch (Exception e) {
            String message = "Numero no valido";
            AlertMessage(message);
        }
        return StringArr;
    }

    private boolean NChars(char[] chars) {
        boolean OK;
        if (chars.length == 6) {
            OK = true;
        } else {
            OK = false;
            String message = "Longitud no valida, (6 digitos)";
            AlertMessage(message);
        }
        return OK;
    }


    private void AlertMessage(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void ButtonSearch(View view) {
        openColorsGallery();
    }

    private void openColorsGallery() {
        Intent intent = new Intent(this, ColorsGallery.class);
        startActivity(intent);
    }



    public void searchAndCopy(String name){
        EditText text = findViewById(R.id.TextCodigoColor);
        text.setText(name);

    }
}