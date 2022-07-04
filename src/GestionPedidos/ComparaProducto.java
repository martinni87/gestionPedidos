/**
 * CLASE COMPARA PRODUCTO
 * @description Clase utilizada dentro de TreeSets con objetos Producto para poder ordenarlos alfabéticamente según el nombre del producto.
 * @author Martin Antonio Cordoba Getar
 * @version 1.0
 * @date 19/05/2022
 */

package GestionPedidos;

import java.util.Comparator;

public class ComparaProducto implements Comparator<Producto> {
    //Ordenar ascendete según nombre de producto
    public int compare(Producto a, Producto b){
        return a.getNombre().compareTo(b.getNombre());
    }
}
