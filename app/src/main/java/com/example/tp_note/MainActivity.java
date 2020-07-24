package com.example.tp_note;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    //initialise

    String nouveaucode = null;
    int pikachu = 0;
    int i = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        textorint();
    }

    public void Entercoffre(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        Intent Coffre = new Intent(this, Coffre.class);

        if (nouveaucode != null) {

            String verif = lecode();
            if (nouveaucode.equals(verif)) {
                //on recupere le code tapé
                Coffre.putExtra("lecode", verif);
                startActivity(Coffre);

            } else {

                if (i > 0) {
                    // ON AFFICHE UNE ERREUR A CHAQUE ESSAI
                    Log.v("NEW", "NOUVEAU" + nouveaucode);
                    Log.v("VERIF", "VERIF" + verif);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Mauvais mot de passe");
                    builder.setMessage("il vous reste " + i + " essai")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    i--;
                } else {
                    this.finishAffinity();
                }
            }

        } else {
            nouveaucode = Nouveau();
            editor.putString("nouveaucode", nouveaucode);
            editor.commit();
        }
    }


    public String lecode() {
        //on recupere la valeur du champs Monpsw
        TextView recup;
        recup = findViewById(R.id.Monpsw);
        String code = recup.getText().toString();
        return code;
    }

    public String Nouveau() {

        TextView recup;
        recup = findViewById(R.id.Valider);
        TextView reset;
        reset = findViewById(R.id.Monpsw);
        String Valeurgarde = reset.getText().toString();

        //RESET les champs et boutton
        reset.setText(null);
        recup.setText("Valider");
        maskicon();


        return Valeurgarde;
    }

    public void init() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        nouveaucode = pref.getString("nouveaucode", null);

        if (nouveaucode != null) {
            TextView recup;
            recup = findViewById(R.id.Valider);
            recup.setText("Valider");
            maskicon();
        }
    }

    public void Deleteall(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer toutes les données")
                .setCancelable(true)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        nouveaucode = null;
                        i = 3;
                        setContentView(R.layout.activity_main);

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }

    public void maskicon() {

        Button iv;
        iv = findViewById(R.id.change);
        iv.setVisibility(ImageView.INVISIBLE);
    }

    public void changeinttotext(View view) {

        //on recupere la variable enregistrer
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        pikachu = pref.getInt("pikachu", 0);

        //on inverse les valeurs
        if (pikachu == 0) {
            pikachu = 1;
        } else {
            pikachu = 0;
        }
        //on enregistre la nouvelle valeur
        editor.putInt("pikachu", pikachu);
        editor.commit();
        textorint();


    }

    public void textorint() {
        //on recupere la variable enregistrer
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        pikachu = pref.getInt("pikachu", 0);

        TextView recup;
        recup = findViewById(R.id.Monpsw);
        if (pikachu == 1) {
            recup.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(5)});
            recup.setInputType(InputType.TYPE_CLASS_TEXT);
            recup.setTransformationMethod(PasswordTransformationMethod.getInstance());
            recup.setText("");

        } else {
            recup.setInputType(InputType.TYPE_CLASS_NUMBER);
            recup.setTransformationMethod(PasswordTransformationMethod.getInstance());
            recup.setText("");
        }

    }

}
