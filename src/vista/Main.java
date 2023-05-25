package vista;

import Model.Producto;
import service.Menu;
import service.Tienda;
import Model.Vendedor;
import exception.*;


public class Main {
    public static void main(String[] args) throws CodigoInvalidoException, PrecioInvalidoException,
            VendedorExistenteException, ProductoExistenteException, RegistroNoEncontradoException {

        System.out.println("\n########### Tienda de Productos ###########\n");

        Tienda tienda = new Tienda();

        // Agrego algunos productos y vendedores de ejemplo
        tienda.agregarProducto(new Producto("p1", "Auricular", 1000.0, "Electrodomestico"));
        tienda.agregarProducto(new Producto("p2", "Mantecol", 150.0, "Alimentos"));
        tienda.agregarProducto(new Producto("p3", "Crema Corporal", 200.0, "Alimentos"));
        tienda.agregarVendedor(new Vendedor("V1", "Vendedor 1", 10000.0));
        tienda.agregarVendedor(new Vendedor("V2", "Vendedor 2", 2500.0));
        tienda.agregarVendedor(new Vendedor("V3", "Vendedor 3", 3000.0));

        Menu menu = new Menu(tienda);
        menu.mostrarMenu();

    }
}



