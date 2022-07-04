/**
 * CLASE PEDIDO
 * @author Martin Antonio Cordoba Getar
 * @version 2.0
 * @date 25/04/2022
 */

package GestionPedidos;

import java.text.DecimalFormat;
import java.util.*;

public class Pedido {
    //ATRIBUTOS
    private Cliente cliente;
    private Date fechaHora;
    private ArrayList<Producto> productos;
    private double importeTotal;
    private PasarelaDePago pago;
    private final String [] ESTADOS = {"PAGADO","PREPARANDO","LISTO","SERVIDO"};
    private boolean estadoPagado = false; /** Cuando esta variable cambie a true,
                                            * se entiende que el pedido ya se ha pagado
                                            * y no podrá pagarse nuevamente.
                                            */
    //CONSTRUCTORES
    public Pedido (){
        this.cliente = new Cliente();
        this.fechaHora = Calendar.getInstance().getTime();
        this.productos = new ArrayList<Producto>();
        this.pago = new PasarelaDePago();
        this.importeTotal = 0;
    }
    public Pedido (Cliente cliente){
        this.cliente = cliente;
        this.fechaHora = Calendar.getInstance().getTime();
        this.productos = new ArrayList<Producto>();
        this.pago = new PasarelaDePago();
        this.importeTotal = 0;
    }

    //Getters
    //Los getters sin uso se mantienen con fines de depuración o ampliación del código.
    public Cliente getCliente(){
        return this.cliente;
    }
    public Date getFechaHora(){
        return this.fechaHora;
    }
    public ArrayList<Producto> getProductos(){
        return this.productos;
    }
    public double getImporteTotal(){
        return this.importeTotal;
    }
    public boolean getEstadoPagado(){
        return this.estadoPagado;
    }
    public PasarelaDePago getPago(){
        return this.pago;
    }

    //MÉTODOS

    //Método para asignar cliente único al pedido
    public void asignaCliente(Cliente cliente){
        if (this.cliente.getTelefono() == 0){   //Utilizamos el número de teléfono para identificar a cada cliente.
            this.cliente = cliente;             //Si el pedido tuviera un cliente el número de teléfono no sería 0
            System.out.println("Cliente " + this.cliente.getTelefono() + " asignado al pedido.");
        }
        //En caso de que el teléfono sea distinto de cero el pedido ya tiene un cliente asignado. Se preguntará si desea cambiarlo
        //pero no pueden haber 2 clientes asignados al mismo pedido.
        else{
            System.out.println("El pedido ya tiene un cliente asignado. ¿Desea sustituirlo? (S/N)");
            String respuesta = new Scanner(System.in).nextLine().substring(0,1);
            if (respuesta.toUpperCase().equals("S")){
                this.cliente = cliente;
                System.out.println("Cliente " + this.cliente.getTelefono() + " asignado al pedido.");
            }
            else if (respuesta.toUpperCase().equals("N")){
                System.out.println("No se cambia el cliente.");
            }
            else{
                System.out.println("Opción no válida. Se cancela el cambio.");
            }
        }
    }

    //Método para agregar productos al pedido.
    public void agregarProductos(Producto producto){
        //Si el pedido está pagado no se pueden agregar más productos.
        if (this.estadoPagado){
            System.out.println("No es posible agregar más productos al pedido, ya que está pagado.");
        }
        //Si el pedido no está pagado...
        else {
            //Si el producto ya existe en el pedido su cantidad se aumenta en 1 pero no se añade de nuevo
            if (this.productos.contains(producto)){
                int cantidad = this.productos.get(this.productos.indexOf(producto)).getCantidad();
                this.productos.get(this.productos.indexOf(producto)).setCantidad(cantidad+1);
            }
            //En caso de que el producto no exista en el pedido, se añade al listado de productos.
            else{
                this.productos.add(producto);
            }
            //En todos los casos de añadir producto o aumentar la cantidad, se actualiza el importe total.
            this.importeTotal += producto.getPrecio();
        }
    }

    //Método para eliminar productos del pedido.
    public void eliminarProducto (int posicion){
        //En caso de que se quiera eliminar un producto en una posición fuera de rango, se lanza el mensaje
        if (posicion >= this.productos.size() || posicion < 0){
            System.out.println("Error. Opción no válida.");
        }
        //Si la posición está dentro del rango del arrayList:
        else{
            //Asigno cantidad de producto almacenado en posicion
            int cantidad = this.productos.get(posicion).getCantidad();
            //Como siempre se elimina de a 1 producto, se resta esa cantidad al importe total
            this.importeTotal -= this.productos.get(posicion).getPrecio();
            //Si la cantidad de producto es mayor de 1, se resta la cantidad al objeto en posicion
            if (cantidad > 1){
                this.productos.get(posicion).setCantidad(cantidad-1);
            }
            //Si la cantidad de producto es 1, se elimina el producto.
            else{
                this.productos.remove(posicion);
            }
        }
    }

    //Método privado que simula el paso del tiempo para ver el cambio de estado del pedido.
    //Este método solo se utiliza al finalizar la selección de método de pago, para hacer pasar el tiempo.
    private void cambioEstado(){
        for (int i = 0; i < this.ESTADOS.length; i++){
            System.out.println("Estado del pedido: " + this.ESTADOS[i]);
            System.out.println("Presione ENTER para simular el paso del tiempo...");
            new Scanner(System.in).nextLine();
        }
    }

    //Método para seleccionar y realizar el tipo de pago que desee el cliente
    public void seleccionDeMetodoDePago(){
        if (this.cliente.getTelefono() == 0){
            System.out.println("Antes de realizar el pago, agregue un cliente al pedido.");
        }
        else if (this.importeTotal == 0){
            System.out.println("Nada que pagar, el importe es 0€");
        }
        else if (this.estadoPagado){
            System.out.println("El pedido ya se ha pagado.");
        }
        else{
            pago.setImporte(this.importeTotal);
            pago.seleccionDeMetodoDePago(); //Método incluido en pasarela de pago para selección de tipo de pago.
            this.estadoPagado = true; //Una vez pagado el estadoPagado cambia a true.
            this.cambioEstado(); //Método que simula el cambio de estados después de pagar.
            this.cliente.agregarPedido(this); //Añadimos el pedido al historial del cliente.
            System.out.println("TICKET PAGADO. Código de pedido: " + pago.getCodigoDePago());
            this.mostrarPedido();
        }
    }

    //Método toString para imprimir el listado de productos del pedido.
    //Los productos llevan contador de cantidad
    public void mostrarPedido(){
        //Configuramos df para tener 2 decimales en el resultado del ticket.
        DecimalFormat df = new DecimalFormat("0.00");
        //Imprimimos ticket. NOTA IMPORTANTE: las tabulaciones y espacios varían según el terminal donde se ejecute.
        System.out.println("ITEM\tCANT.\tPRODUCTO\t\t\t\t€ ud.\t\t\tTOTAL €");
        System.out.println("===============================================================");
        for (int i = 0; i < this.getProductos().size();i++){
            System.out.print((i+1) + "\t\t");
            System.out.print(this.getProductos().get(i).getCantidad() + "\t\t");
            System.out.print(this.getProductos().get(i).getNombre() + "\t\t\t");
            System.out.print(df.format(this.getProductos().get(i).getPrecio()) + "\t\t\t");
            System.out.println(df.format(this.getProductos().get(i).getPrecio()*this.getProductos().get(i).getCantidad()));
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println("TOTAL..................................................... " + df.format(this.getImporteTotal()) + "€\n");

        //Indicamos si el pedido está pagado o no.
        String estado = this.getEstadoPagado()?"PEDIDO PAGADO":"PEDIDO NO PAGADO";
        System.out.println("Estado: " + estado + "\n");

        //Indicamos el cliente asignado al pedido. Si aun no hay ninguno asignado se muestra 0 (teléfono = 0).
        System.out.println("Cliente asignado: " + this.cliente.getNombre() + " " + this.cliente.getApellidos() + " " + this.cliente.getTelefono() + "\n");
    }
}
