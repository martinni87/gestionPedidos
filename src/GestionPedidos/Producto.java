/**
 * CLASE PRODUCTO
 * @author Martin Antonio Cordoba Getar
 * @version 2.0
 * @date 25/04/2022
 */

package GestionPedidos;

public class Producto {
    //Atributos
    private String nombre;
    private double precio;
    private int cantidad; //Se utilizará como contador para listado de pedido en clase Pedido.

    //Constructor vacío
    public Producto(){
        this.nombre = "";
        this.precio = 0;
        this.cantidad = 0;
    }

    //Constructor con parámetros
    public Producto (String nombre, double precio){
        this.nombre = nombre.toUpperCase();
        this.precio = Math.round(precio*100.0)/100.0;
        this.cantidad = 1;
    }

    //Setters
    public void setNombre(String nombre){
        this.nombre = nombre.toUpperCase();
    }
    public void setPrecio(double precio){
        this.precio = (double)(Math.round(precio*100)/100);
    }
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    //Getters
    public String getNombre(){
        return this.nombre;
    }
    public double getPrecio(){
        return this.precio;
    }
    public int getCantidad(){
        return this.cantidad;
    }
}
