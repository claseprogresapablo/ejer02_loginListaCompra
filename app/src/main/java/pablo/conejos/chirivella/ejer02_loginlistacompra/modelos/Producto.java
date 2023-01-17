package pablo.conejos.chirivella.ejer02_loginlistacompra.modelos;

import java.text.NumberFormat;

public class Producto {

    private String nombre;
    private int cantidad;
    private float precio;

    private static final NumberFormat nf;
    private static final NumberFormat nfNumeros;
    static{ //Aqui es donde se deben in inicializar variables estaticas
        nf = NumberFormat.getCurrencyInstance();
        nfNumeros = NumberFormat.getNumberInstance();
    }

    //Llamares a este metodo cuando quiera obtener el precio con su moneda
    public String getPrecioMoneda(){
        return nf.format(this.precio);
    }

    public String getCantidadTexto(){
        return nfNumeros.format(this.cantidad);
    }

    public Producto() {
    }

    public Producto(String nombre, int cantidad, float precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }
}
