package Model;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Tienda {

    private HashMap<Long, Vendedor> vendedores;
    private HashMap<Long, Producto> productos;

    public Tienda() {
        vendedores = new HashMap<>();
        productos = new HashMap<>();
    }

    public void agregarVendedor(Vendedor vendedor) {
        vendedores.put(vendedor.getCodigo(), vendedor);
    }

    public void agregarProducto(Producto producto) {
        productos.put(producto.getCodigo(), producto);
    }


    private void validarProducto(Producto producto) {
        if (!productos.containsValue(producto)) {
            throw new IllegalArgumentException("El producto no está registrado en la tienda.");
        }
    }

    private void validarVendedor(Vendedor vendedor) {
        if (!vendedores.containsValue(vendedor)) {
            throw new IllegalArgumentException("El vendedor no está registrado en la tienda.");
        }
    }

    public void registrarVenta(Producto producto, Vendedor vendedor) {
        validarProducto(producto);
        validarVendedor(vendedor);

        vendedor.agregarVenta(producto);
    }

    public double calcularComision(Vendedor vendedor) {
        long cantidadVentas = vendedor.getVentas().stream().count();

        return cantidadVentas <= 2 ? cantidadVentas * 0.05 : cantidadVentas * 0.1;
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productos.values().stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

}
