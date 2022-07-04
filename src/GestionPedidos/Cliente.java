/**
 * CLASE CLIENTE
 * @author Martin Antonio Cordoba Getar
 * @version 2.0
 * @date 25/04/2022
 */

package GestionPedidos;

import java.text.SimpleDateFormat;
import java.util.*;

public class Cliente {
    //Atributos
    private String nombre;
    private String apellidos;
    private Date fechaDeAlta;
    private int telefono;
    private String direccion;
    private ArrayList<Pedido> historial;

    //Constructor vacío
    public Cliente() {
        this.nombre = "";
        this.apellidos = "";
        this.fechaDeAlta = Calendar.getInstance().getTime(); //Establece la fecha y hora actual por defecto.
        this.telefono = 0; //Inicializamos teléfono a 0. Significa que no hay cliente asignado.
        this.direccion = "";
        this.historial = new ArrayList<Pedido>(); //Historial de compras inicializado vacío
    }

    //Constructor con parámetros
    public Cliente(String nombre, String apellidos, int telefono) {
        this.nombre = nombre.toLowerCase();
        this.apellidos = apellidos.toUpperCase();
        this.fechaDeAlta = fechaDeAlta;
        this.telefono = telefono; //Al instanciar objeto cliente con parámetros, teléfono != 0, entonces existe cliente.
        this.direccion = direccion;
        this.historial = new ArrayList<Pedido>();//Historial de compras inicializado vacío
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre.toLowerCase();
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos.toUpperCase();
    }
    public void setFechaDeAlta(Date fechaDeAlta) {
        this.fechaDeAlta = fechaDeAlta;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public Date getFechaDeAlta() {
        return fechaDeAlta;
    }
    public int getTelefono() {
        return telefono;
    }
    public String getDireccion() {
        return direccion;
    }

    //Método para agregar pedido a historial
    //Se utilizará en el método de pago únicamente cuando el pedido haya sido pagado. Se añade al historial el objeto pedido completo con todos los datos.
    public void agregarPedido(Pedido pedido) {
        if(pedido.getEstadoPagado()){
            this.historial.add(pedido);//Añadimos el pedido con todos sus datos al historial.
        }
        else{
            System.out.println("Antes de añadir el pedido, debe pagarse.");
        }
    }

    //Método para introducir número de teléfono válido
    //Se puede introducir en distintas partes del código para evitar que se introduzcan valores de teléfono erróneos.
    public int introducirTelefonoValido() {
        Scanner sc = new Scanner(System.in);
        int numero = 0; //Recibirá el número por entrada estándar para ser evaluado. Si es válido se devuelve.
        boolean validation = false;
        do{
            System.out.println("Introduzca un número de teléfono o marque 0 para salir.");
            try{
                numero = sc.nextInt();
                if(numero > 600000000 && numero < 999999999){
                    validation = true;
                }
                else if (numero == 0){
                    System.out.println("Operación cancelada.");
                    validation = true;
                }
                else{
                    System.out.println("Valor no válido. Debe introducir un número de teléfono de 9 dígitos, sin espacios, que empiece por 6,7,8 o 9.");
                }
                sc.nextLine(); //Limpiar buffer
            }
            catch (Exception error){
                System.out.println("Error, tipo de dato no válido.");
                sc.nextLine(); //Limpiar buffer
            }
        }while(!validation);
        return numero;
    }

    //Método para introducir una fecha válida
    //Si el usuario introduce día, mes, año, hora o minutos superiores a los límites del calendario o reloj, este método lo transforma a formatos válidos.
    //Ejemplo: hora 25, minutos 65, la hora se transformará a 01 y aumenta 1 día, el minuto se transformará a 05 y aumenta 1 hora, luego serán 02:05 más 1 día.
    public Date introducirFechaValida(){
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); //Para formatear la visualización de la fecha
        Date fecha = new Date();
        String aux; //Variable auxiliar. Almacena el valor a evaluar.
        boolean validation = false;
        //El siguiente bucle analiza si la fecha introducida es válida o no. Si lo es, sale. Caso contrario, se repite.
        do{
            System.out.println("Introduzca una fecha con el formato \"dd/mm/aaaa HH:mm\" o deje en blanco y presione ENTER para seleccionar la fecha y hora actual.");
            aux = sc.nextLine();
            if (aux.equals("")){
                System.out.println("Se selecciona la fecha y hora actual.");
                validation = true;
            }
            else{
                try{
                    fecha = df.parse(aux);
                    validation = true;
                    System.out.println("La fecha de registro se establece en: " + fecha);
                }
                catch (Exception error){
                    System.out.println("Formato incorrecto, introduzca una fecha con el formato \"dd/MM/aaaa HH:mm\".");
                }
            }
        }while (!validation);
        //Fuera del bucle, la fecha es válida y se puede devolver como resultado del método.
        return fecha;
    }

    //Método para imprimir por pantalla el conjunto de datos de un cliente
    public void imprimirDatosCliente(){
        System.out.println("################# DATOS DE CLIENTE #################");
        System.out.println("- Nombre: " + this.nombre);
        System.out.println("- Apellido: " + this.apellidos);
        System.out.println("- Teléfono: " + this.telefono);
        System.out.println("- Dirección: " + this.direccion);
        System.out.println("- Fecha de alta: " + this.fechaDeAlta);
        imprimeCodigosDePago(); //Ver método abajo
    }

    //Método que extrae los códigos de pago de los pedidos del historial y los imprime por pantalla
    private void imprimeCodigosDePago(){
        long codigo;
        System.out.print("- Historial de compras: {-");
        for (int i = 0; i < this.historial.size(); i++){
            codigo = this.historial.get(i).getPago().getCodigoDePago();
            System.out.print(codigo + "-");
        }
        System.out.println("}");
    }
}
