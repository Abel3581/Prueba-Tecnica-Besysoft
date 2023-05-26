package test;

import model.Producto;
import model.Vendedor;
import exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Tienda;

import java.util.HashMap;
import java.util.List;

class TiendaTest {

    private Tienda tienda;

    TiendaTest() {
    }

    @BeforeEach
    void setUp() {
        tienda = new Tienda();
    }

    @Test
    public void agregarVendedor() throws CodigoInvalidoException, PrecioInvalidoException, VendedorExistenteException {
        Vendedor vendedor = new Vendedor("v1","Abel",1000.0);

        tienda.agregarVendedor(vendedor);

        HashMap<String, Vendedor> vendedores = tienda.getVendedores();
        Assertions.assertTrue(vendedores.containsKey("v1"));
        Assertions.assertEquals(vendedor, vendedores.get("v1"));

    }

    @Test
    public void gregarVendedorVendedorExistenteLanzaExcepcion() throws CodigoInvalidoException, PrecioInvalidoException {
        Vendedor vendedor = new Vendedor("v1", "Abel", 2000.0);
        tienda.getVendedores().put("v1", vendedor);
        Assertions.assertThrows(VendedorExistenteException.class, () -> {
            tienda.agregarVendedor(vendedor);
        });
    }

    @Test
    public void agregarProducto() throws CodigoInvalidoException, PrecioInvalidoException, ProductoExistenteException {
        Producto producto = new Producto("p1", "Producto1", 2000.0, "cat1");
        tienda.agregarProducto(producto);

        HashMap<String, Producto> productos = tienda.getProductos();
        Assertions.assertTrue(productos.containsKey("p1"));
        Assertions.assertEquals(producto, productos.get("p1"));

    }

    @Test
    public void agregarProductoProductoExistenteLanzaExcepcion() throws CodigoInvalidoException, PrecioInvalidoException {
        Producto producto = new Producto("p1", "Producto1", 2000.0, "cat1");
        tienda.getProductos().put("p1", producto);
        Assertions.assertThrows(ProductoExistenteException.class, () -> {
            tienda.agregarProducto(producto);
        });
    }

    @Test
    public void registrarVentaProductoYVendedorExistente() throws CodigoInvalidoException, PrecioInvalidoException, VendedorExistenteException, ProductoExistenteException, RegistroNoEncontradoException {
        Vendedor vendedor = new Vendedor("v1", "Nombre", 200.00);
        tienda.agregarVendedor(vendedor);
        Producto producto = new Producto("p1", "Nombre", 20.00, "cate");
        tienda.agregarProducto(producto);

        tienda.registrarVenta(producto, vendedor);
        Assertions.assertTrue(vendedor.getVentas().contains(producto));
    }

    @Test
    public void eliminarVentaProductoYVendedorExistente() throws CodigoInvalidoException, PrecioInvalidoException, VendedorExistenteException, ProductoExistenteException, RegistroNoEncontradoException {
        Vendedor vendedor = new Vendedor("v1", "Nombre", 200.00);
        tienda.agregarVendedor(vendedor);
        Producto producto = new Producto("p1", "Nombre", 20.00, "cate");
        tienda.agregarProducto(producto);

        tienda.registrarVenta(producto, vendedor);
        tienda.eliminarVenta(producto, vendedor);

        Assertions.assertFalse(vendedor.getVentas().contains(producto));
    }

    @Test
    public void eliminarProductoProductoExistente() throws CodigoInvalidoException, PrecioInvalidoException, ProductoExistenteException, RegistroNoEncontradoException {
        Producto producto = new Producto("p1", "Camiseta", 29.99, "Ropa");
        tienda.agregarProducto(producto);

        tienda.eliminarProducto("p1");

        HashMap<String, Producto> productos = tienda.getProductos();
        Assertions.assertFalse(productos.containsKey("p1"));
    }

    @Test
    public void eliminarVendedorVendedorExistente() throws CodigoInvalidoException, PrecioInvalidoException, VendedorExistenteException, RegistroNoEncontradoException {
        Vendedor vendedor = new Vendedor("001", "John Doe", 1000.0);
        tienda.agregarVendedor(vendedor);

        tienda.eliminarVendedor("001");
        HashMap<String, Vendedor> vendedores = tienda.getVendedores();

        Assertions.assertFalse(vendedores.containsKey("001"));
    }

    @Test
    void calcularComisionCantidadVentasMenorIgualA2CalculaComisionCorrectamente() throws CodigoInvalidoException, PrecioInvalidoException {
        Vendedor vendedor = new Vendedor("v1", "John Doe", 1000.0);
        Producto producto1 = new Producto("p1", "Camiseta", 29.99, "Ropa");
        Producto producto2 = new Producto("p2", "Pantalón", 39.99, "Ropa");

        tienda.getVendedores().put("v1", vendedor);
        vendedor.agregarVenta(producto1);
        vendedor.agregarVenta(producto2);

        double comision = tienda.calcularComision(vendedor);

        Assertions.assertEquals(3.4989999999999997, comision);

    }

    @Test
    void buscarPorCategoriaProductosConCategoriaExistenteDevuelveProductosCorrectos() throws ProductoExistenteException, CodigoInvalidoException, PrecioInvalidoException {
        Producto producto1 = new Producto("P001", "Camiseta", 29.99, "Ropa");
        Producto producto2 = new Producto("P002", "Pantalón", 39.99, "Ropa");
        Producto producto3 = new Producto("P003", "Zapatos", 59.99, "Calzado");

        tienda.agregarProducto(producto1);
        tienda.agregarProducto(producto2);
        tienda.agregarProducto(producto3);

        List<Producto> productos = tienda.buscarPorCategoria("Ropa");

        Assertions.assertEquals(2, productos.size());
        Assertions.assertTrue(productos.contains(producto1));
        Assertions.assertTrue(productos.contains(producto2));
    }

    @Test
    public void buscarPorAtributoDeProducto() throws CodigoInvalidoException, PrecioInvalidoException, ProductoExistenteException {
        Producto producto1 = new Producto("P001", "Camiseta", 29.99, "Ropa");
        Producto producto2 = new Producto("P002", "Pantalón", 39.99, "Ropa");
        Producto producto3 = new Producto("P003", "Zapatos", 59.99, "Calzado");

        tienda.agregarProducto(producto1);
        tienda.agregarProducto(producto2);
        tienda.agregarProducto(producto3);

        List<Producto> productos = tienda.buscarPorAtributo("P003");

        Assertions.assertEquals(1, productos.size());
        Assertions.assertTrue(productos.contains(producto3));

    }

    @Test
    void actualizarProductoProductoExistente() throws RegistroNoEncontradoException, ProductoExistenteException, CodigoInvalidoException, PrecioInvalidoException {
        Producto producto = new Producto("P001", "Camiseta", 29.99, "Ropa");
        tienda.agregarProducto(producto);

        tienda.actualizarProducto("P001", "Nueva Camiseta", 39.99, "Ropa Nueva");

        Producto productoActualizado = tienda.getProductos().get("P001");
        Assertions.assertEquals("Nueva Camiseta", productoActualizado.getNombre());
        Assertions.assertEquals(39.99, productoActualizado.getPrecio());
        Assertions.assertEquals("Ropa Nueva", productoActualizado.getCategoria());
    }

    @Test
    void gananciasVendedorVendedorExistente() throws RegistroNoEncontradoException, VendedorExistenteException, CodigoInvalidoException, PrecioInvalidoException {
        Vendedor vendedor = new Vendedor("001", "John Doe", 1000.0);
        Producto producto1 = new Producto("P001", "Camiseta", 29.99, "Ropa");
        Producto producto2 = new Producto("P002", "Pantalón", 39.99, "Ropa");
        tienda.getVendedores().put("001", vendedor);
        vendedor.agregarVenta(producto1);
        vendedor.agregarVenta(producto2);

        double ganancias = tienda.gananciasVendedor("001");

        Assertions.assertEquals(1003.499, ganancias);
    }

    @Test
    public void buscarPorVendedorVendedorExistente() throws CodigoInvalidoException, PrecioInvalidoException, VendedorExistenteException, RegistroNoEncontradoException {
        Vendedor vendedor = new Vendedor("v1", "Nombre", 1000.0);
        tienda.agregarVendedor(vendedor);

        HashMap<String, Vendedor> vendedors = tienda.getVendedores();
        Vendedor vendedorEncontrado = tienda.buscarVendedor("v1");

        Assertions.assertEquals(vendedor, vendedorEncontrado);

    }

    @Test
    void buscarVendedorVendedorNoExistenteLanzaExcepcionRegistroNoEncontrado() {
        String codigoVendedor = "002";

        Assertions.assertThrows(RegistroNoEncontradoException.class, () -> {
            tienda.buscarVendedor(codigoVendedor);
        });
    }

}