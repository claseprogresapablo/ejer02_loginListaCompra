package pablo.conejos.chirivella.ejer02_loginlistacompra;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import pablo.conejos.chirivella.ejer02_loginlistacompra.adapters.ProductosAdapter;
import pablo.conejos.chirivella.ejer02_loginlistacompra.databinding.ActivityMainBinding;
import pablo.conejos.chirivella.ejer02_loginlistacompra.modelos.Producto;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProductosAdapter adapter;
    private RecyclerView.LayoutManager lm;
    private List<Producto> productoList;

    private FirebaseDatabase dataBase;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBase = FirebaseDatabase.getInstance("https://ejer02-loginlistacompra-default-rtdb.europe-west1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = dataBase.getReference(uid).child("lista");


        productoList = new ArrayList<>();
        adapter = new ProductosAdapter(productoList, R.layout.protucto_view_holder,this, reference);
        lm = new LinearLayoutManager(this);

        //ESTO SALTA CADA VEZ QUE HAY UN CAMBIO EN LA REFERENCIA (nodo de la bd)
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productoList.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Producto>> gti = new GenericTypeIndicator<ArrayList<Producto>>() {};
                    productoList.addAll(snapshot.getValue(gti));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(lm);


        setSupportActionBar(binding.toolbar);



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProducto().show();
            }
        });


    }
    private AlertDialog createProducto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nuevo Producto");
        builder.setCancelable(false);

        View productoView = getLayoutInflater().inflate(R.layout.producto_alert, null);
        EditText txtNombre = productoView.findViewById(R.id.txtNombreAlert);
        EditText txtCantidad = productoView.findViewById(R.id.txtCantidadAlert);
        EditText txtPrecio = productoView.findViewById(R.id.txtPrecioAlert);
        builder.setView(productoView);

        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nombre = txtNombre.getText().toString();
                String cantidad = txtCantidad.getText().toString();
                String precio = txtPrecio.getText().toString();

                if (!nombre.isEmpty() && !cantidad.isEmpty() &&!precio.isEmpty()){
                    productoList.add(new Producto(nombre, Integer.parseInt(cantidad), Float.parseFloat(precio)));
                    //adapter.notifyItemInserted(productoList.size()-1);
                    //SOLO METE LA DIFERENCIA
                    reference.setValue(productoList); //1-Aqui se mete en la bd //2-Esto dispara el evento que se trae la lista
                }
            }
        });

        return builder.create();
    }

    /**
     * Especifica que menu se va a cargar en la actividad
     * @param menu -> es el hueco donde aparece el menú
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.logout_menu, menu);

        return true;
    }

    /**
     * Discriminar las diferentes acciones en base al lelemento del menu seleccionado
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.btnLogOut) {
            FirebaseAuth.getInstance().signOut(); //Desloguearse
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        } else if (item.getItemId() == R.id.btnAbrirDatos){
            startActivity(new Intent(this, DatosActivity.class));
        }

        return true;
    }
}