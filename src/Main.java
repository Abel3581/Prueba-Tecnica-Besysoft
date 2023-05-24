import Model.Producto;
import service.Tienda;
import Model.Vendedor;
import exception.*;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws CodigoInvalidoException, PrecioInvalidoException,
            VendedorExistenteException, ProductoExistenteException {

        System.out.println("\n########### Tienda de Productos ###########\n");

        Tienda tienda = new Tienda();

        // Agregar algunos productos y vendedores de ejemplo
        tienda.agregarProducto(new Producto("p1", "Auricular", 1000.0, "Electrodomestico"));
        tienda.agregarProducto(new Producto("p2", "Mantecol", 150.0, "Alimentos"));
        tienda.agregarProducto(new Producto("p3", "Crema Corporal", 200.0, "Alimentos"));
        tienda.agregarVendedor(new Vendedor("V1", "Vendedor 1", 10000.0));
        tienda.agregarVendedor(new Vendedor("V2", "Vendedor 2", 2500.0));
        tienda.agregarVendedor(new Vendedor("V3", "Vendedor 3", 3000.0));
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            final StringBuilder sb = new StringBuilder();
            sb.append("+------------------------------------------+\n");
            sb.append("|             === MENÚ ===                 |\n");
            sb.append("+------------------------------------------+\n");
            sb.append(String.format("| %-2s | %-35s |\n", "1.", "Registrar venta"));
            sb.append(String.format("| %-2s | %-35s |\n", "2.", "Registrar producto"));
            sb.append(String.format("| %-2s | %-35s |\n", "3.", "Calcular comisión de un vendedor"));
            sb.append(String.format("| %-2s | %-35s |\n", "4.", "Buscar productos por categoría"));
            sb.append(String.format("| %-2s | %-35s |\n", "5.", "Eliminar Venta"));
            sb.append(String.format("| %-2s | %-35s |\n", "6.", "Eliminar Producto"));
            sb.append(String.format("| %-2s | %-35s |\n", "7.", "Busqueda personalizada de productos"));
            sb.append(String.format("| %-2s | %-35s |\n", "8.", "Actualizar producto"));
            sb.append(String.format("| %-2s | %-35s |\n", "9.", "Informacion del vendedor"));
            sb.append(String.format("| %-2s | %-34s |\n", "10.", "Salir"));
            sb.append("+------------------------------------------+");
            System.out.println(sb.toString());

            opcion = obtenerNumero(scanner, "Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    System.out.println("\n=== Registrar Venta ===");
                    System.out.print("Ingrese el código del producto: ");
                    String codigoProducto = scanner.nextLine();
                    System.out.print("Ingrese el código del vendedor: ");
                    String codigoVendedor = scanner.nextLine();

                    Producto producto = tienda.getProductos().get(codigoProducto);
                    Vendedor vendedor = tienda.getVendedores().get(codigoVendedor);

                    try {
                        tienda.registrarVenta(producto, vendedor);
                        System.out.println("\nVenta registrada exitosamente.");
                        System.out.println();
                    } catch (RegistroNoEncontradoException e) {
                        System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
                    }
                    break;
                case 2:
                    System.out.println("\n=== Registrar Producto ===");
                    System.out.print("Ingrese el código del producto: ");
                    String codigo = scanner.nextLine();
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese el precio del producto: ");
                    double precio = scanner.nextDouble();
                    scanner.nextLine(); // Consumir carácter de nueva línea pendiente
                    System.out.print("Ingrese la categoria del producto: ");
                    String cate = scanner.nextLine();

                    Producto pro = new Producto(codigo, nombre, precio, cate);
                    try {
                        tienda.agregarProducto(pro);
                        System.out.println("\nProducto registrada exitosamente.");
                        System.out.println();
                    } catch (ProductoExistenteException e) {
                        System.out.println("\nError: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\n=== Calcular Comisión ===");
                    System.out.print("\nIngrese el código del vendedor: ");
                    codigoVendedor = scanner.nextLine();

                    vendedor = tienda.getVendedores().get(codigoVendedor);

                    if (vendedor != null) {
                        double comision = tienda.calcularComision(vendedor);
                        System.out.println("\nComisión del vendedor: " + comision);
                    } else {
                        System.out.println("\nVendedor no encontrado.");
                    }
                    break;
                case 4:
                    System.out.println("\n=== Buscar Productos por Categoría ===");
                    System.out.print("Ingrese la categoría: ");
                    String categoria = scanner.nextLine();

                    List<Producto> productosEnCategoria = tienda.buscarPorCategoria(categoria);
                    if (productosEnCategoria.isEmpty()) {
                        System.out.println("\nNo se encontraron productos en la categoría especificada.");
                    } else {
                        System.out.println("\nProductos encontrados:\n");
                        productosEnCategoria.forEach(p -> System.out.println(p.toString()));

                    }
                    break;
                case 5:
                    System.out.println("\n=== Eliminar Venta ===");
                    System.out.print("Ingrese el código del producto: ");
                    String codigoProd = scanner.nextLine();
                    System.out.print("Ingrese el código del vendedor: ");
                    String codigoVend = scanner.nextLine();

                    Producto eliminar = tienda.getProductos().get(codigoProd);
                    Vendedor vaciar = tienda.getVendedores().get(codigoVend);

                    try {
                        tienda.eliminarVenta(eliminar, vaciar);
                        System.out.println("\nVenta eliminada exitosamente.");
                    }catch (RegistroNoEncontradoException e) {
                        System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
                    }
                    break;
                case 6:
                    System.out.println("\n=== Eliminar Producto ===");
                    System.out.print("Ingrese el código del producto: ");
                    String codPro = scanner.nextLine();
                    try{
                        tienda.eliminarProducto(codPro);
                        System.out.println("\nProducto eliminada exitosamente.");
                    }catch (RegistroNoEncontradoException e){
                        System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
                    }
                    break;
                case 7:
                    System.out.println("\n=== Busqueda personalizada de Productos ===");
                    System.out.print("Ingrese un nombre, categoria, codigo o precio  de un producto: ");
                    String atributo = scanner.nextLine();
                    List<Producto> buscarPorAtributo = tienda.buscarPorAtributo(atributo);
                    if (buscarPorAtributo.isEmpty()) {
                        System.out.println("\nNo se encontraron productos con esa especificacion.");
                    } else {
                        System.out.println("Productos encontrados:\n");
                        buscarPorAtributo.forEach(p -> System.out.println(p.toString()));
                    }
                    break;
                case 8:
                    System.out.println("\n=== Actualizar Producto ===");
                    System.out.print("Ingrese el código del producto: ");
                    String c = scanner.nextLine();
                    System.out.print("Ingrese el nombre del producto: ");
                    String n = scanner.nextLine();
                    System.out.print("Ingrese el precio del producto: ");
                    double p = scanner.nextDouble();
                    scanner.nextLine(); // Consumir carácter de nueva línea pendiente
                    System.out.print("Ingrese la categoria del producto: ");
                    String cat = scanner.nextLine();
                    try {
                        tienda.actualizarProducto(c, n, p, cat);
                        System.out.println("\nProducto actualizado exitosamente.");
                    }catch (RegistroNoEncontradoException e){
                        System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
                    }
                    break;
                case 9:
                    System.out.println("\n=== Informacion del vendedor ===");
                    System.out.print("Ingrese el código del vendedor: ");
                    String codVendedor = scanner.nextLine();
                    try {
                        Vendedor infoVendedor = tienda.buscarVendedor(codVendedor);
                        System.out.println(infoVendedor.toString());
                    }catch (RegistroNoEncontradoException e){
                        System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
                    }
                    break;
                case 10:
                    System.out.println("\nSaliendo...");
                    break;
                default:
                    System.out.println("\n==== Opción inválida. ====");
            }

            System.out.println();
        } while (opcion != 10);

        scanner.close();
    }

    public static int obtenerNumero(Scanner scanner, String mensaje) {
        int numero;
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                numero = Integer.parseInt(entrada);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: la entrada '" + entrada + "' no es un número válido.");
            }
        }
        return numero;
    }
}


