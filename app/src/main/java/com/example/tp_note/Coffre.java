package com.example.tp_note;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Coffre extends AppCompatActivity {

    String lesecretstring = null;
    String MuliI = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffre);
        recup();
        actualiser();
    }

    public void Save(View view) {

        TextView lesecret;
        lesecret = findViewById(R.id.editTextTextMultiLine);

        String lavarsecret = lesecret.getText().toString();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        // on set pour chaque string la valeur ecrite
        editor.putString(MuliI, lavarsecret);
        Log.v("multi", MuliI);
        editor.commit();


    }

    public void close(View view) {
        final Intent Main = new Intent(this, MainActivity.class);
        startActivity(Main);

        // fermer proprement
        finish();
    }

    public void recup() {

        // Recuperer et afficher le code de la page precedent
        String code = getIntent().getStringExtra("lecode");
        TextView afficher;
        afficher = findViewById(R.id.textView4);
        afficher.setText("le code est : " + code);

        //On affiche l'ancienne valeur ou null
        TextView lesecret;
        lesecret = findViewById(R.id.editTextTextMultiLine);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        lesecret.setText(pref.getString(MuliI, null));

    }

    public void changemdp(View view) {

        //on recupere la variable enregistrer
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        int pikachu = pref.getInt("pikachu", 0);


        //On créé une nouvelle fenetre
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Changer le mot de passe ?");
        final EditText input = new EditText(this);
        if (pikachu == 1) {
            input.setInputType(InputType.TYPE_CLASS_TEXT);


        } else {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);

        }

        //Pour rendre un peu plus jolie
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5), new InputFilter.AllCaps()});
        input.setBackgroundColor(Color.parseColor("#B5B3B7"));
        input.setGravity(Gravity.CENTER_HORIZONTAL);
        input.setPadding(0, 100, 0, 0);
        builder.setView(input);


        //POSITIVE YES
        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String txt = input.getText().toString();
                Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();

                //on enregistre la variable
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("nouveaucode", txt);
                editor.commit();

                //raffraichir le code
                //setContentView(R.layout.activity_coffre);
                TextView afficher;
                afficher = findViewById(R.id.textView4);
                afficher.setText("le code : " + txt);
            }
        });

        //On appel la nouvelle fenetre
        final AlertDialog ad = builder.create();
        ad.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, 0xB5B3B3));
        ad.show();


    }

    public void newactualiser_sauvegarder() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        TextView lesecret;
        //Spinner afficher;
        //afficher = findViewById(R.id.i);
        //MuliI = afficher.toString();

        lesecret = findViewById(R.id.editTextTextMultiLine);
        lesecretstring = pref.getString(MuliI, null);
        lesecret.setText(lesecretstring);
        // Log.d("tada", lesecretstring);


    }

    public void actualiser() {

        //function onchange
        Spinner lenombre;
        lenombre = findViewById(R.id.i);
        lenombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (parent.getItemAtPosition(position).equals("0")) {
                    MuliI = "0";
                    newactualiser_sauvegarder();
                } else {

                    String mavar = String.valueOf(position);
                    MuliI = mavar;
                    newactualiser_sauvegarder();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}