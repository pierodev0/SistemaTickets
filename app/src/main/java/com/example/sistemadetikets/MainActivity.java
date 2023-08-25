package com.example.sistemadetikets;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txt_Reg;
    private EditText etEmail, etPass;
    private Button btnLogin;
    private Basededatos basededatos;

    //Linea agregada piero

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.txt_correo);
        etPass = findViewById(R.id.txt_pwd);
        btnLogin = findViewById(R.id.btn_inic);
        txt_Reg = findViewById(R.id.txt_reg);

        basededatos = new Basededatos(this);

        txt_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrodeUsuarios.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();

                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email es obligatorio");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    etPass.setError("Contraseña es obligatoria");
                    return;
                }

                if (!basededatos.checkUserCredentials(email, password)){

                    Toast.makeText(MainActivity.this, "Usuario no existe en la base de datos", Toast.LENGTH_LONG).show();

                    etEmail.setText("");
                    etPass.setText("");

                }

                if (basededatos.checkUserCredentials(email, password)){
                    String userName = basededatos.obtenerNombreUsuarioDesdeBD(email);
                    String userLastName = basededatos.obtenerApellidoUsuarioDesdeBD(email);
                    String userId = basededatos.obtenerIdUsuario(email);
                    etEmail.setText("");
                    etPass.setText("");

                    // Redirigir a la Actividad de Usuario y pasar los datos
                    Intent intent = new Intent(MainActivity.this, Usuario.class);
                    intent.putExtra("user_name", userName);
                    intent.putExtra("user_last_name", userLastName);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);
                }else {

                }
            }

        });
    }
}