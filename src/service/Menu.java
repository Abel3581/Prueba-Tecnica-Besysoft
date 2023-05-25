package service;

import Model.Producto;
import Model.Vendedor;
import exception.CodigoInvalidoException;
import exception.PrecioInvalidoException;
import exception.ProductoExistenteException;
import exception.RegistroNoEncontradoException;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private Tienda tienda;
    private Scanner scanner;

    public Menu(Tienda tienda) {
        this.tienda = tienda;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() throws CodigoInvalidoException, PrecioInvalidoException, RegistroNoEncontradoException {
        int opcion;
        do {
            imprimirMenu();
            opcion = obtenerNumero("Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    registrarVenta();
                    break;
                case 2:
                    registrarProducto();
                    break;
                case 3:
                    calcularComision();
                    break;
                case 4:
                    buscarPorCategoria();
                    break;
                case 5:
                    eliminarVenta();
                    break;
                case 6:
                    eliminarProducto();
                    break;
                case 7:
                    busquedaPersonalizada();
                    break;
                case 8:
                    actualizarProducto();
                    break;
                case 9:
                    informacionVendedor();
                    break;
                case 10:
                    eliminarVendedor();
                    break;
                case 11:
                    gananciasTotalPorMes();
                    break;
                case 12:
                    System.out.println("\nSaliendo...");
                    break;
                default:
                    System.out.println("\n==== Opción inválida. ====");
            }

            System.out.println();
        } while (opcion != 12);

        scanner.close();
    }

    private void gananciasTotalPorMes() throws RegistroNoEncontradoException {
        System.out.println("=== Calcula Ganancia Vendedor ===");
        System.out.print("Ingrese el código del vendedor: ");
        String codVend = scanner.nextLine();
        try{
            double ganancias = tienda.gananciasVendedor(codVend);
            System.out.println("\nGanancias por mes: " + ganancias);
        }catch (RegistroNoEncontradoException e){
            System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
        }
    }

    private void eliminarVendedor() {
        System.out.println("=== Eliminar Vendedor ===");
        System.out.print("Ingrese el código del vendedor: ");
        String eliminarVendedor = scanner.nextLine();
        try{
            tienda.eliminarVendedor(eliminarVendedor);
            System.out.println("\nVendedor eliminada exitosamente.");
        }catch (RegistroNoEncontradoException e){
            System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
        }
    }

    private void informacionVendedor() {
        System.out.println("\n=== Informacion del vendedor ===");
        System.out.print("Ingrese el código del vendedor: ");
        String codVendedor = scanner.nextLine();
        try {
            Vendedor infoVendedor = tienda.buscarVendedor(codVendedor);
            System.out.println(infoVendedor.toString());
        }catch (RegistroNoEncontradoException e){
            System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
        }
    }

    private void actualizarProducto() {
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
    }

    private void busquedaPersonalizada() {
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
    }

    private void eliminarProducto() {
        System.out.println("\n=== Eliminar Producto ===");
        System.out.print("Ingrese el código del producto: ");
        String codPro = scanner.nextLine();
        try{
            tienda.eliminarProducto(codPro);
            System.out.println("\nProducto eliminada exitosamente.");
        }catch (RegistroNoEncontradoException e){
            System.out.println("\nError: " + e.getMessage() + " (" + e.getTipoRegistro() + ")");
        }
    }

    private void eliminarVenta() {
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
    }

    private void buscarPorCategoria() {
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
    }

    private void calcularComision() {
        System.out.println("\n=== Calcular Comisión ===");
        System.out.print("\nIngrese el código del vendedor: ");
        String codigoVendedor = scanner.nextLine();

        Vendedor vendedor = tienda.getVendedores().get(codigoVendedor);

        if (vendedor != null) {
            double comision = tienda.calcularComision(vendedor);
            System.out.println("\nComisión del vendedor: " + comision);
        } else {
            System.out.println("\nVendedor no encontrado.");
        }
    }

    private void imprimirMenu() {
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
        sb.append(String.format("| %-2s | %-34s |\n", "10.", "Eliminar Vendedor"));
        sb.append(String.format("| %-2s | %-34s |\n", "11.", "Ver Ganancias vendedor"));
        sb.append(String.format("| %-2s | %-34s |\n", "12.", "Salir"));
        sb.append("+------------------------------------------+");
        System.out.println(sb.toString());
    }

    private int obtenerNumero(String mensaje) {
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

    private void registrarVenta() {
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
    }

    private void registrarProducto() throws CodigoInvalidoException, PrecioInvalidoException {
        System.out.println("\n=== Registrar Producto ===");
        System.out.print("Ingrese el código del producto: ");
        String codigo = scanner.nextLine();
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        double precio = obtenerNumero("Ingrese el precio del producto: ");
        System.out.print("Ingrese la categoría del producto: ");
        String categoria = scanner.nextLine();

        Producto producto = new Producto(codigo, nombre, precio, categoria);
        try {
            tienda.agregarProducto(producto);
            System.out.println("\nProducto registrado exitosamente.");
            System.out.println();
        } catch (ProductoExistenteException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

}
