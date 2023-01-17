package pablo.conejos.chirivella.ejer02_loginlistacompra.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import pablo.conejos.chirivella.ejer02_loginlistacompra.R;
import pablo.conejos.chirivella.ejer02_loginlistacompra.modelos.Producto;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoVH> {


    private List<Producto> objects;
    private int resource;
    private Context context;
    private DatabaseReference reference;

    public ProductosAdapter(List<Producto> objects, int resource, Context context, DatabaseReference reference) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.reference = reference;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoView = LayoutInflater.from(context).inflate(resource, null);
        productoView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ProductoVH(productoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {

        Producto p = objects.get(position);
        holder.lblNombre.setText(p.getNombre());
        holder.lblCantidad.setText(p.getCantidadTexto());
        holder.lblPrecio.setText(p.getPrecioMoneda());

        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HAY QUE PEDIR CONFIRMACION
                objects.remove(p);
                //notifyItemRemoved(holder.getAdapterPosition());
                reference.setValue(objects);
            }
        });

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class ProductoVH extends RecyclerView.ViewHolder {

        TextView lblNombre;
        TextView lblPrecio;
        TextView lblCantidad;
        ImageButton btnBorrar;
        public ProductoVH(@NonNull View itemView) {
            super(itemView);

            lblNombre = itemView.findViewById(R.id.lblNombreCard);
            lblPrecio = itemView.findViewById(R.id.lblPrecioCard);
            lblCantidad = itemView.findViewById(R.id.lblCantidadCard);
            btnBorrar = itemView.findViewById(R.id.btnDeleteCard);
        }
    }
}
