package com.example.eva02v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eva02v1.modelos.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    SharedPreferences Pref;
    FirebaseAuth mAuth;

    DatabaseReference mDatabase;

    Button buttonRegister2;
    TextInputEditText editTextEmailRegister, editTextPasswordRegister, editTextUsernameRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Pref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        buttonRegister2 = findViewById(R.id.buttonRegister2);

        editTextEmailRegister = findViewById(R.id.editTextEmailRegister);

        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);

        editTextUsernameRegister = findViewById(R.id.editTextUsernameRegister);




        buttonRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
     void registerUser(){


        String usuario= editTextUsernameRegister.getText().toString();
        String email= editTextEmailRegister.getText().toString();
        String password = editTextPasswordRegister.getText().toString();

        if(!email.isEmpty() && !password.isEmpty() && !usuario.isEmpty()){
            if(password.length() >= 6){
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            saveUser(usuario,email);

                        }
                        else{
                            Toast.makeText(Registro.this,"Regisotro Exitoso", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
            else{
                Toast.makeText(this,"La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
     }

     void saveUser(String name, String email){
         String selectedUser = Pref.getString("user","");
         User user = new User();
         user.setName(name);
         user.setEmail(email);

        if(selectedUser.equals("usuario")){
            mDatabase.child("Users").child("usuarios").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registro.this,"Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Registro.this,"Registro Fallido", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else if(selectedUser.equals("")){

        }

     }
}