/**
 * CLASE PASARELA DE PAGO
 * @author Martin Antonio Cordoba Getar
 * @version 2.0
 * @date 25/04/2022
 */

package GestionPedidos;

import java.util.Date;
import java.util.Scanner;

public class PasarelaDePago {
    //Atributos
    private double importe;
    private long codigoDePago;

    //Constructor vacío
    public PasarelaDePago(){
        this.importe = 0;
        this.codigoDePago = 0;
    }

    //Constructor con parámetros
    public PasarelaDePago(double importe){
        this.importe = (double)Math.round(importe*100)/100;
        this.codigoDePago = 0;
    }

    //Setters
    //setImporte se utiliza en pedido, en selección de método de pago.
    public void setImporte(double importe){
        this.importe = (double)Math.round(importe*100)/100;
    }
    //No lo utilizamos a lo largo del código, pero se mantiene para ampliar las funcionalidades del programa en un futuro.
    public void setCodigoDePago(long codigoDePago){
        this.codigoDePago = codigoDePago;
    }

    //Getters
    //getImporte no se utiliza a lo largo del programa, pero se mantiene con fines de depuración
    public double getImporte(){
        return this.importe;
    }
    //getCodigoDePago se utiliza en Cliente para mostrar el código de pago de un pedido añadido al historial y en pedido en el ticket pagado.
    public long getCodigoDePago(){
        return this.codigoDePago;
    }

    //Método para pagar en efectivo
    private void pagarEnEfectivo(double dinero){
        System.out.println("Dinero a utilizar: " + dinero);
        double cambio = dinero - this.importe;
        cambio = (double)Math.round(cambio*100)/100;
        System.out.println("Pago en efectivo realizado con éxito");
        System.out.println("Cambio: " + cambio);
    }
    //Método para pagar con tarjeta
    //Por simplificar el código se introduce núm. tarjeta como String, pero podría obligarse a introducir solo
    // números como en se hace en otras partes del código. (Ver caso de introducir número de teléfono en Cliente.java)
    private void pagarConTarjeta(String numeroTarjeta){
        System.out.println("Tarjeta a utilizar: " + numeroTarjeta);
        System.out.println("Pago con tarjeta realizado con éxito");
    }
    //Método para pagar con cuenta
    //Igual que en pagoConTarjeta, se introduce String por simplificar código.
    private void pagarConCuenta(String numeroCuenta){
        System.out.println("Cuenta a utilizar: " + numeroCuenta);
        System.out.println("Pago con cuenta realizado con éxito");
    }

    //Método para seleccionar y realizar el tipo de pago que desee el cliente
    public void seleccionDeMetodoDePago(){
        Scanner sc = new Scanner(System.in);
        boolean salida = false; //Salida de bucles do while
        int opcion = 0; //Selección de opción de pago

        //Se indica el importe a pagar.
        System.out.println("Importe a pagar: " + this.importe);
        //Menú de opciones de pago.
        do{
            System.out.println("Seleccione el método de pago a realizar:");
            System.out.println("1. Pago en efectivo");
            System.out.println("2. Pago con tarjeta");
            System.out.println("3. Pago con cuenta");
            //Identificamos con try & catch si el usuario introduce un valor int.
            try{
                opcion = sc.nextInt();
                if (opcion <1 || opcion >3) {
                    System.out.println("Opción no válida");
                }
                else{
                    salida = true;
                    sc.nextLine();
                }
            }
            //Mensaje de error en caso de no introducir un int.
            catch(Exception e){
                System.out.println("Error. Debe introducir una opción del listado (1,2,3)");
                sc.nextLine(); //Limpiamos buffer (registro de tecla ENTER previo)
            }
        }while (!salida); //El bucle termina cuando salida = true, es decir, cuando opción es válida.

        //Volvemos a salida false para el siguiente bucle.
        salida = false;

        //Caso pago en efectivo
        if(opcion == 1){
            double dinero = 0;
            System.out.println("Introduzca la cantidad de dinero para pagar:");
            do{
                try{
                    dinero = sc.nextDouble();
                    if (dinero < this.importe) {
                        System.out.println("El importe introducido es menor que el importe a pagar.");
                    }
                    else{
                        salida = true;
                    }
                }
                catch(Exception e){
                    System.out.println("Error. Debe introducir un número");
                    sc.nextLine();
                }
            }while (!salida);

            this.pagarEnEfectivo(dinero);
        }
        //Caso pago con tarjeta
        else if(opcion == 2){
            System.out.println("Introduzca el número de tarjeta:");
            String numeroTarjeta = sc.nextLine();
            this.pagarConTarjeta(numeroTarjeta);
        }
        //Otros casos: único caso restante pago con número de cuenta
        else {
            System.out.println("Introduzca el número de cuenta:");
            String numeroCuenta = sc.nextLine();
            this.pagarConCuenta(numeroCuenta);
        }

        //Realizado el pago, el importe a pagar se hace 0 y se genera el código de pago.
        this.importe = 0;
        //Este código de pago se podrá ver luego en el historial de pedido y en el ticket que se imprime al finalizar el pedido.
        this.codigoDePago = new Date().getTime();
    }
}
