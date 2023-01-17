package pablo.conejos.chirivella.ejer02_loginlistacompra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pablo.conejos.chirivella.ejer02_loginlistacompra.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding; //activar binding en la pagina
    private FirebaseAuth auth; //esta variable contiene todos los metodos para dar soporte a la verificacion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance(); //Inicializar el auth para poder utilizarlo

        //Este metodo debe verificar que los datos son correctos y mandarla la info al metodo que lo registre
        binding.btnDoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.txtEmailLogin.getText().toString();
                String password = binding.txtPasswordLogin.getText().toString();

                if(!email.isEmpty() && password.length() > 5){
                    doLogin(email, password);
                }
            }
        });

        //Este metodo deben verificar que los datos son correctos y mandarla la info al metodo que lo registre
        binding.btnDoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.txtEmailLogin.getText().toString();
                String password = binding.txtPasswordLogin.getText().toString();

                if(!email.isEmpty() && password.length() > 5){
                    doRegistrer(email, password);
                }
            }
        });
    }

    //Hacer login con los datos ya verificados
    private void doRegistrer(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //comprobar si la tarea ha acabado bien o mal
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser(); //guardar el usuario
                    upDateUI(user);
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Hacer Login con los datos ya verificados
    private void doLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //comprobar si la tarea ha acabado bien o mal
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser(); //guardar el usuario
                    upDateUI(user);
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //este metodo comprueba que l user no sea uno y si no es nulo abrir la otra ventana
    private void upDateUI(FirebaseUser user) {
        if(user != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        upDateUI(auth.getCurrentUser());
    }
}