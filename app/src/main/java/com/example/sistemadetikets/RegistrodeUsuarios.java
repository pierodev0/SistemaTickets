package com.example.sistemadetikets;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrodeUsuarios extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo, etPass,etRazon;
    private Button btnRegistrarse;
    private Basededatos basededatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrode_usuarios);

        etNombre = findViewById(R.id.edittxt_nom);
        etApellido = findViewById(R.id.editext_ape);
        etCorreo = findViewById(R.id.edittxt_correo);
        etPass = findViewById(R.id.edittxt_pass);
        //New
        etRazon = findViewById(R.id.editext_razon);
        btnRegistrarse = findViewById(R.id.btn_regis);

        basededatos = new Basededatos(this);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString();
                String apellido = etApellido.getText().toString();
                String email = etCorreo.getText().toString();
                String password = etPass.getText().toString();
                //New
                String razon = etRazon.getText().toString();

                if(TextUtils.isEmpty(nombre)){
                    etNombre.setError("El nombre es obligatorio");
                    return;
                }

                if(TextUtils.isEmpty(apellido)){
                    etApellido.setError("El apellido es obligatorio");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    etCorreo.setError("El correo es obligatorio");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    etPass.setError("La contraseña es obligatoria");
                    return;
                }

                if(TextUtils.isEmpty(razon)){
                    etRazon.setError("La Razon social es obligatoria");
                    return;
                }

                if (basededatos.checkUserCredentials(email, password)){

                    Toast.makeText(RegistrodeUsuarios.this, "Este correo ya esta registrado", Toast.LENGTH_LONG).show();

                    etCorreo.setText("");
                    etPass.setText("");
                    return;

                }

                basededatos.insertUser(nombre,apellido,email,password);

                startActivity(new Intent(RegistrodeUsuarios.this, MainActivity.class));
                finish();
            }
        });
    }
}