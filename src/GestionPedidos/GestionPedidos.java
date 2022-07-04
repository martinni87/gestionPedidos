/**
 * APP GESTIÓN DE PEDIDOS
 * @description Aplicación de gestión de pedidos. Incluye menú general y submenús de gestión de clientes, productos y pedidos.
 *              Al realizar pedidos y pagarlos guarda un historial de pedidos realizados por cada cliente.
 *              Si los pedidos no se pagan y se vuelve a la pantalla de bienvenida, se resetean todos los valores a excepción
 *              del listado de clientes que mantiene el historial de clientes y sus pedidos pagados.
 * @author Martin Antonio Cordoba Getar
 * @version 2.0
 * @date 19/05/2022
 */

package GestionPedidos;

import java.util.*;

public class GestionPedidos {
    /**
     * MÉTODO MAIN con el funcionamiento general del programa.
     * Resto de funciones static para el funcionamiento de menús y submenús pueden verse debajo de main.
     */
    public static void main(String[] args) {
        /**
         * COLECCIONES
         * 1. Colección de clientes de tipo clave valor en donde se utilizará el número de teléfono como clave única.
         * 2. Colección ordenada de productos no repetidos, asignamos clave numérica. Esto es: las existencias.
         * */
        TreeMap<Integer,Cliente> listaClientes = new TreeMap<Integer, Cliente>();
        //TreeSet<Producto> listaProductos = new TreeSet<Producto>(new ComparaProducto())   //Línea comentada puesto que se inicializará
                                                                                            //como parámetro de la función del menú principal.
                                                                                            //de tal forma que en cada nuevo pedido, las cantidades
                                                                                            //de producto se vuelvan a 0.

        /**
         * VARIABLES DE USO GENERAL
         * Se declaran e inicializan los objetos y variables que serán usados a lo largo del programa.
         */
        boolean salir = false; //Para salir del bucle principal de bienvenida al programa.

        /**
         * MENÚ PRINCIPAL
         * Visualmente el programa inicia con este menú en donde un camarero comenzará a seleccionar opciones para realizar el pedido de un cliente.
         */
        do{

            System.out.println("\n\nBIENVENIDO AL PROGRAMA DE GESTIÓN DE PEDIDOS");
            System.out.println("==============================================");
            System.out.println("Seleccione una opción del listado (marque 1 ó 2 y presione ENTER)");
            System.out.println("1. Iniciar pedido nuevo.");
            System.out.println("2. Salir del programa.");

            //Comprobamos que el usuario introduce un valor entero como opción.
            try{
                int opcion = new Scanner(System.in).nextInt();
                //Opción 1 nos manda al menú principal para escoger submenú cliente, productos o pedido.
                if(opcion == 1){
                    menuPrincipal(new Pedido(), listaClientes, new TreeSet<Producto>(new ComparaProducto()));
                }
                //Opción 2 sale del programa.
                else if (opcion == 2){
                    System.out.println("Se sale del programa");
                    salir = true;
                }
                //Otras opciones no son válidas. Se lanza mensaje de error y se vuelve a intentar.
                else{
                    System.out.println("Opción no válida.");
                }
            }
            //Si se introduce una letra, se lanza aviso de error y se vuelve a intentar.
            catch (Exception e){
                System.out.println("Opción no válida. Presiona ENTER...");
                new Scanner(System.in).nextLine();
            }
        }while (!salir);

        System.out.println("¡Muchas gracias por utilizar nuestro software!");
        //Se termina el programa
    }


    /**
     * MÉTODOS PARA MENÚS Y SUBMENUS: PRINCIPAL, CLIENTE, PRODUCTO, PEDIDO Y OTROS MÉTODOS AUXILIARES
     */

    //MENÚ PRINCIPAL
    public static void menuPrincipal(Pedido pedido, TreeMap<Integer,Cliente>listaClientes,TreeSet<Producto>listaProductos){
        boolean salir = false;

        //Generamos la lista dentro del pedido de tal forma que la cantidad de productos comience en 1 en cada pedido.
        generaListadoProductos(listaProductos);

        do{
            int opcion = 0;
            System.out.println("\n\n### MENÚ PRINCIPAL ###");
            System.out.println("Seleccione una opción (1, 2, 3 o 4):");
            System.out.println("1. Menú Clientes");
            System.out.println("2. Menú Productos");
            System.out.println("3. Menú Pedidos");
            System.out.println("4. Salir");

            try{
                opcion = new Scanner(System.in).nextInt();
            }
            catch (Exception e){
                //new Scanner(System.in).nextLine();
                System.out.print("Error. Debe marcar un número de la lista. ");
            }

            switch (opcion){
                case 1:
                    System.out.println("Seleccionado menú clientes");
                    menuClientes(listaClientes);
                    break;
                case 2:
                    System.out.println("Seleccionado menú productos");
                    menuProductos(pedido, listaProductos);
                    break;
                case 3:
                    System.out.println("Seleccionado menú pedidos");
                    menuPedidos(pedido, listaClientes);
                    break;
                case 4:
                    //Al salir del menú principal los pedidos y productos se resetean (los clientes no). Para evitar perder pedidos en curso no pagados
                    //se pregunta al usuario si desea continuar y perder los pedidos no pagados o seguir en el menú principal y terminar de pagar.
                    System.out.println("Ha seleccionado la opción salir. Volverá a la pantalla de bienvenida y se perderán los pedidos no pagados.");
                    System.out.println("¿Desea continuar? (S/N)");
                    //Capturamos la respuesta y con substring reducimos a la primera letra. El usuario puede escribir S, s, sí, si, Sí, Si...
                    String respuesta = new Scanner(System.in).nextLine().substring(0,1);
                    //Salimos al menú inicial de bienvenida.
                    if (respuesta.toUpperCase().equals("S")){
                        System.out.println("Se sale al menú inicial.");
                        salir = true; //Habilitamos la salida del bucle.
                    }
                    //Continuamos en el menú principal.
                    else if (respuesta.toUpperCase().equals("N")){
                        System.out.println("Ha seleccionado no salir.");
                    }
                    //Opción no válida, continuamos en el menú principal.
                    else{
                        System.out.println("Opción no válida.");
                    }
                    break;
                //Opciones no válidas repiten el bucle.
                default: System.out.println("Opción no válida. Marque 1, 2, 3 o 4.");
            }
        }while (!salir); //Cuando se marque la opción 4 salimos del bucle y se termina el programa
    }

    //MENU CLIENTES
    public static void menuClientes(TreeMap<Integer,Cliente>listaClientes){
        boolean atras = false;

        //Opciones del menú
        do{
            int opcion = 0;
            int numero = 0;

            System.out.println("\n\n### MENÚ CLIENTES ###");
            System.out.println("Seleccione una opción (1, 2, 3, 4 o 5):");
            System.out.println("1. Añadir Clientes.");
            System.out.println("2. Eliminar Clientes.");
            System.out.println("3. Mostrar clientes registrados.");
            System.out.println("4. Mostrar datos de un cliente.");
            System.out.println("5. Atrás.");

            //Se verifica la introducción de un valor entero como opción.
            try{
                opcion = new Scanner(System.in).nextInt();
            }
            catch (Exception e){
                System.out.print("Error. Debe marcar un número de la lista. ");
            }

            //Casos para la selección de opción del usuario
            switch (opcion){

                //Añadir clientes
                case 1:
                    System.out.println("\nAÑADIR CLIENTES\n");
                    numero = new Cliente().introducirTelefonoValido();

                    //Si la lista de clientes NO está vacía y SÍ contiene la clave teléfono:
                    if (!listaClientes.isEmpty() && listaClientes.containsKey(numero) ){
                        System.out.println("\nEl cliente con teléfono " + numero + " ya existe:\n");
                        listaClientes.get(numero).imprimirDatosCliente();
                    }
                    //Resto de casos: la lista está vacía o el teléfono no existe en una lista no vacía:
                    else if(numero != 0){
                        Cliente cliente = new Cliente();
                        cliente.setTelefono(numero);
                        addCliente(cliente,listaClientes);//función añadir cliente definida debajo de ésta
                        System.out.println("\nCliente nuevo añadido:");
                        listaClientes.get(cliente.getTelefono()).imprimirDatosCliente();
                    }
                    break;

                //Eliminar clientes
                case 2:
                    System.out.println("\nELIMINAR CLIENTES\n");
                    if (listaClientes.isEmpty()){
                        System.out.println("No hay nada que eliminar.");
                    }
                    else{
                        System.out.println("Seleccione un teléfono de la siguiente lista para eliminar un cliente.");
                        mostrarClientes(listaClientes);
                        int item = new Cliente().introducirTelefonoValido();
                        if (listaClientes.containsKey(item)){
                            listaClientes.remove(item);
                            System.out.println("Cliente con número de teléfono " + item + " eliminado.");
                        }
                        else if (item != 0){
                            System.out.println("El número " + item + " no pertenece a ningún cliente");
                        }
                    }
                    break;

                //Mostrar listado de clientes.
                case 3:
                    System.out.println("\nCLIENTES REGISTRADOS\n");
                    mostrarClientes(listaClientes);
                    break;
                //Mostrar datos de un cliente concreto.
                case 4:
                    if (listaClientes.isEmpty()){
                        System.out.println("No hay clientes registrados.");
                    }
                    else{
                        System.out.println("Seleccione un teléfono para ver los datos del cliente.");
                        System.out.println("\nCLIENTES REGISTRADOS:");
                        mostrarClientes(listaClientes);
                        System.out.println("");
                        numero = new Cliente().introducirTelefonoValido();
                        if (listaClientes.containsKey(numero)){
                            listaClientes.get(numero).imprimirDatosCliente();
                        }
                        else if (numero != 0){
                            System.out.println("El número " + numero + " no pertenece a ningún cliente.");
                        }
                    }
                    break;
                //Volver atrás
                case 5:
                    atras = true;
                    break;

                //Opción seleccionada no existe
                default: System.out.println("Opción no válida. Marque 1, 2, 3, 4 o 5");
            }

        }while (!atras); //Cuando se marque la opción 5 salimos del bucle y volvemos al menú anterior.
    }
    //FUNCIÓN AÑADIR CLIENTE DENTRO DEL MENÚ CLIENTES
    public static void addCliente(Cliente cliente, TreeMap<Integer,Cliente>listaClientes){
        Scanner sc = new Scanner(System.in);

        System.out.println("NUEVO CLIENTE.");
        System.out.println("A continuación rellene los datos solicitados:");

        System.out.print("Nombre: ");
        cliente.setNombre(sc.nextLine());
        System.out.print("Apellidos: ");
        cliente.setApellidos(sc.nextLine());
        System.out.print("Dirección: ");
        cliente.setDireccion(sc.nextLine());
        cliente.setFechaDeAlta(cliente.introducirFechaValida());

        listaClientes.put(cliente.getTelefono(),cliente);
    }
    //FUNCIÓN MOSTRAR CLIENTES DENTRO DEL MENÚ CLIENTES
    public static void mostrarClientes(TreeMap<Integer,Cliente>listaClientes){
        if (!listaClientes.isEmpty()){
            for (Map.Entry<Integer,Cliente> cliente: listaClientes.entrySet()){
                System.out.println("Teléfono: " + cliente.getKey() + ". Nombre y Apellidos: " + cliente.getValue().getNombre() + " " + cliente.getValue().getApellidos());
            }
        }
        else{
            System.out.println("El listado está vacío, no hay nada para mostrar.");
        }
    }

    //MENU PRODUCTOS
    public static void menuProductos(Pedido pedido, TreeSet<Producto> listaProductos){
        boolean atras = false;

        //Opciones del menú
        do{
            int opcion = 0;

            System.out.println("\n\n### MENÚ PRODUCTOS DISPONIBLES ###");
            System.out.println("Seleccione una opción (1,2 o 3):");
            System.out.println("1. Añadir producto al pedido.");
            System.out.println("2. Eliminar producto.");
            System.out.println("3. Listado de productos añadidos al pedido.");
            System.out.println("4. Atrás");

            try{
                opcion = new Scanner(System.in).nextInt();
            }
            catch (Exception e){
                System.out.print("Error. Debe marcar un número de la lista. ");
            }
            //Casos para la selección de opción del usuario
            switch (opcion){

                //Añadir productos
                case 1:
                    System.out.println("\nAÑADIR PRODUCTOS AL PEDIDO\n");
                    //Si pedido no pagado, se pueden agregar productos.
                    if (!pedido.getEstadoPagado()){
                        agregarProductos(pedido, listaProductos);
                    }
                    //Si pedido pagado, no se pueden agregar productos.
                    else{
                        System.out.println("No es posible añadir productos puesto que el pedido está pagado.");
                    }
                    break;

                //Eliminar productos
                case 2:
                    System.out.println("\nELIMINAR PRODUCTOS DEL PEDIDO\n");
                    //Si pedido no pagado, se pueden eliminar productos.
                    if (!pedido.getEstadoPagado()){
                        //Si lista productos no vacía, se pueden eliminar productos.
                        if(!pedido.getProductos().isEmpty()){
                            eliminarProducto(pedido);
                        }
                        //Si lista productos vacía, no hay nada que eliminar.
                        else{
                            System.out.println("No hay productos para eliminar.");
                        }
                    }
                    //Si pedido pagado no se pueden eliminar productos.
                    else{
                        System.out.println("No es posible eliminar productos puesto que el pedido está pagado.");
                    }
                    break;

                //Mostrar listado de productos añadidos al pedido
                case 3:
                    System.out.println("\nLISTADO DE PRODUCTOS AÑADIDOS AL PEDIDO\n");
                    if (pedido.getProductos().isEmpty()){
                        System.out.println("La selección de productos está vacía.");
                    }
                    else{
                        pedido.mostrarPedido();
                    }
                    break;
                //Volver atrás
                case 4:
                    atras = true;
                    break;
                //Opción seleccionada no existe
                default: System.out.println("Opción no válida. Marque 1, 2, 3 o 4");
            }

        }while (!atras); //Cuando se marque la opción 4 salimos del bucle y volvemos al menú anterior.






    }
    //ASIGNACIÓN DE PRODUCTOS AL LISTADO DE PRODUCTOS DISPONIBLES
    public static void generaListadoProductos(TreeSet<Producto> listaProductos){
        //Definimos productos disponibles con TreeSet configurado para ordenar según el nombre de producto
        listaProductos.add(new Producto("Agua natural", 0.55));
        listaProductos.add(new Producto("Cerveza Mahou", 1.05));
        listaProductos.add(new Producto("Coca Cola Zero", 1.50));
        listaProductos.add(new Producto("Hamburguesa XL", 4.50));
        listaProductos.add(new Producto("Pizza calzone", 6.00));
        listaProductos.add(new Producto("Bocadillo XL", 3.35));
    }
    //AGREGAR PRODUCTOS
    public static void agregarProductos(Pedido pedido, TreeSet<Producto> listaProductos){
        boolean atras = false;
        do{
            int i = 0;
            int opcion = 0;

            System.out.println("Agregue los productos que desee a su pedido (0 para finalizar):");

            //Mostramos listado de productos
            for (Producto item: listaProductos){
                i++;
                String nombre = item.getNombre();
                double valor = item.getPrecio();
                System.out.println((i) + ". " + nombre + " " + valor + " €");
            }

            //Comprobamos que el usuario introduce un entero como opción de selección de producto.
            try{
                System.out.print("::: ");
                opcion = new Scanner(System.in).nextInt();
                i = 0;

                //Recorriendo el listado de productos, añadimos aquel que coincida con la opción del usuario
                for (Producto item: listaProductos){
                    i++;
                    if (opcion == i){
                        pedido.agregarProductos(item);
                        pedido.mostrarPedido();
                        System.out.println("Producto añadido al pedido: " + item.getNombre() + "\n");
                        break;
                    }
                    //Con 0 se sale de la selección de productos.
                    else if (opcion == 0){
                        System.out.println("Se sale al menú previo.\n");
                        atras = true;
                        break;
                    }
                }
            }
            catch (Exception e){
                System.out.println("Error. Debe marcar un número de la lista.");
            }
        }while (!atras);
    }
    //ELIMINAR PRODUCTOS
    public static void eliminarProducto(Pedido pedido){
        boolean salir = false;
        int opcion = 0;
        int i = 0;

        do{
            System.out.println("Seleccione un item a eliminar del siguiente listado (0 para salir):");
            pedido.mostrarPedido();
            System.out.print("::: ");
            try{
                opcion = new Scanner(System.in).nextInt();
                //Si opcion = 0, se sale del menú.
                if (opcion == 0){
                    System.out.println("Ha seleccionado salir.");
                    salir = true;
                }
                //Si opción !=0 y es válida, se elimina 1ud del producto seleccionado.
                else{
                    System.out.println("Se elimina 1ud de " + pedido.getProductos().get(opcion-1).getNombre());
                    pedido.eliminarProducto(opcion-1);
                }

                //Aparte, si ya no hay productos para eliminar, las lineas anteriores no se ejecutan y se lanza el siguiente aviso.
                if (pedido.getProductos().isEmpty()){
                    System.out.println("Aviso: No hay más productos para eliminar. Se sale del menú.");
                    salir = true;
                }
            }
            //Caso de introducir una opción no válida.
            catch (Exception e){
                System.out.println("Opción no válida. Introduzca algún item de la lista (1, 2, 3...)");
            }
        }while (!salir);
    }

    //MENU PEDIDOS
    public static void menuPedidos(Pedido pedido, TreeMap<Integer,Cliente>listaClientes){
        boolean atras = false;

        do{
            int opcion = 0;
            System.out.println("\n\n### MENÚ PEDIDOS ###");
            System.out.println("Seleccione una opción (1,2,3 o 4):");
            System.out.println("1. Mostrar pedido en curso.");
            System.out.println("2. Asignar o cambiar cliente del pedido en curso.");
            System.out.println("3. Procesar pago del pedido.");
            System.out.println("4. Volver atrás.");

            try{
                opcion = new Scanner(System.in).nextInt();
            }
            catch (Exception e){
                System.out.println("Error. Debe marcar un número de la lista.");
            }

            switch (opcion){
                //Mostrar pedido en curso
                case 1:
                    System.out.println("\nMOSTRAR PEDIDO EN CURSO\n");
                    //Se puede consultar el pedido en cualquier momento, esté pagado o no. Se indicará en el ticket si está pagado.
                    pedido.mostrarPedido();
                    break;
                //Asignar o cambiar cliente. Cada pedido sólo admite un cliente, por lo que si ya tiene un cliente asignado se preguntará si desea cambiarlo.
                case 2:
                    System.out.println("\nASIGNAR O CAMBIAR CLIENTE\n");
                    //Si el pedido está pagado, no se admiten más cambios de clientes.
                    if (pedido.getEstadoPagado()){
                        System.out.println("No se puede cambiar el cliente porque el pedido ya se ha pagado.");
                    }
                    //Si el pedido no está pagado sí es posible cambiar de cliente.
                    else{
                        asignaClientePedido(pedido,listaClientes);
                    }
                    break;
                //Procesar pago del pedido. Sólo puede realizarse si el importe total > 0, si hay cliente asignado y si el estado del pedido es NO PAGADO.
                case 3:
                    System.out.println("\nPROCESAR PAGO DEL PEDIDO\n");
                    pedido.seleccionDeMetodoDePago();
                    break;
                //Volver al menú anterior. Se guardan los datos de pedido.
                case 4:
                    System.out.println("\nVOLVER ATRÁS\n");
                    atras = true;
                    break;
                //Opción seleccionada no existe
                default: System.out.println("Opción no válida. Marque 1, 2, 3 o 4");
            }
        }while (!atras); //Cuando se marque la opción 4 salimos del bucle y se termina el programa
    }
    //MÉTODO PARA ASIGNAR CLIENTES AL PEDIDO
    public static void asignaClientePedido(Pedido pedido, TreeMap<Integer,Cliente> listaClientes){
        boolean salir = false;

        do{

            System.out.println("Seleccione el número de teléfono del siguiente listado (0 para cancelar)");
            mostrarClientes(listaClientes);
            int numero = new Cliente().introducirTelefonoValido();

            //Si el número se encuentra en el listado de clientes, se añade dicho cliente al pedido.
            if (listaClientes.containsKey(numero)){
                pedido.asignaCliente(listaClientes.get(numero));
                salir = true;
            }
            //Si el número no se encuentra en el listadod e clientes y es distinto de 0, se lanza el siguiente aviso.
            else if (numero != 0){
                System.out.println("El número " + numero + " no pertenece a ningún cliente. Vuelva a intentarlo.\nPresione ENTER...");
                new Scanner(System.in).nextLine();
            }
            //Si el número introducido es 0 se vuelve al menú anterior.
            else{
                //Operación cancelada, sale del bucle.
                salir = true;
            }

        }while(!salir);
    }
}