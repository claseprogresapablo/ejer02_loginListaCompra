package pablo.conejos.chirivella.ejer02_loginlistacompra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pablo.conejos.chirivella.ejer02_loginlistacompra.databinding.ActivityDatosBinding;
import pablo.conejos.chirivella.ejer02_loginlistacompra.databinding.ActivityMainBinding;
import pablo.conejos.chirivella.ejer02_loginlistacompra.modelos.Persona;

public class DatosActivity extends AppCompatActivity {

    private ActivityDatosBinding binding;

    private FirebaseDatabase dataBase;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDatosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBase = FirebaseDatabase.getInstance("https://ejer02-loginlistacompra-default-rtdb.europe-west1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = dataBase.getReference(uid).child("datos");



        binding.btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona p = new Persona();

                if (!binding.txtNombreDatos.getText().toString().isEmpty() &&
                        !binding.txtTelefonoDatos.getText().toString().isEmpty()){
                    p.setNombre(binding.txtNombreDatos.getText().toString());
                    p.setTelefono(binding.txtTelefonoDatos.getText().toString());


                   reference.setValue(p);

                }



            }
        });

    }
}