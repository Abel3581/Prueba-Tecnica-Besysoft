package service;

import model.Producto;
import model.Vendedor;
import exception.ProductoExistenteException;
import exception.RegistroNoEncontradoException;
import exception.VendedorExistenteException;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Tienda {

    //Se usa HashMap son mas eficientes que la List
    private HashMap<String, Vendedor> vendedores;
    private HashMap<String, Producto> productos;

    public Tienda() {
        vendedores = new HashMap<>();
        productos = new HashMap<>();
    }


    public HashMap<String, Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(HashMap<String, Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public HashMap<String, Producto> getProductos() {
        return productos;
    }

    public void setProductos(HashMap<String, Producto> productos) {
        this.productos = productos;
    }

    public void agregarVendedor(Vendedor vendedor) throws VendedorExistenteException {
        if (vendedores.containsKey(vendedor.getCodigo())) {
            throw new VendedorExistenteException("El vendedor ya está registrado en la tienda.");
        }
        vendedores.put(vendedor.getCodigo(), vendedor);
    }

    public void agregarProducto(Producto producto) throws ProductoExistenteException {
        if(productos.containsKey(producto.getCodigo())){
            throw new ProductoExistenteException("El codigo del producto ya esta registrado");
        }
        productos.put(producto.getCodigo(), producto);
    }


    private void validarProducto(Producto producto) throws RegistroNoEncontradoException {
        if (!productos.containsValue(producto)) {
            throw new RegistroNoEncontradoException("El producto no está registrado en la tienda.", "Product");
        }
    }

    private void validarVendedor(Vendedor vendedor) throws RegistroNoEncontradoException {
        if (!vendedores.containsValue(vendedor)) {
            throw new RegistroNoEncontradoException("El vendedor no está registrado en la tienda.", "Vendedor");
        }
    }

    public void registrarVenta(Producto producto, Vendedor vendedor) throws RegistroNoEncontradoException {
        validarProducto(producto);
        validarVendedor(vendedor);
        vendedor.agregarVenta(producto);
    }

    public void eliminarVenta(Producto producto, Vendedor vendedor) throws RegistroNoEncontradoException {
        validarProducto(producto);
        validarVendedor(vendedor);
        vendedor.eliminarVenta(producto);
    }

    public void eliminarProducto(String codigo) throws RegistroNoEncontradoException {
        Producto producto = productos.get(codigo);
        validarProducto(producto);
        productos.remove(codigo);

    }

    public void eliminarVendedor(String codigo) throws RegistroNoEncontradoException {
        Vendedor vendedor = vendedores.get(codigo);
        validarVendedor(vendedor);
        vendedores.remove(codigo);
    }

    public double calcularComision(Vendedor vendedor) {
        double totalVentas = vendedor.getVentas()
                .stream()
                .mapToDouble(Producto::getPrecio)
                .sum();

        long cantidadVentas = vendedor.getVentas()
                .stream()
                .count();

        double comision = cantidadVentas <= 2 ? totalVentas * 0.05 : totalVentas * 0.1;
        return comision;

    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productos.values().stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    public List<Producto> buscarPorAtributo(String valorBusqueda) {
        return productos.values().stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(valorBusqueda) ||
                        p.getNombre().equalsIgnoreCase(valorBusqueda) ||
                        p.getCodigo().equalsIgnoreCase(valorBusqueda) ||
                        String.valueOf(p.getPrecio()).equalsIgnoreCase(valorBusqueda))
                .collect(Collectors.toList());
    }

    public void actualizarProducto(String c, String n, double p, String cat) throws RegistroNoEncontradoException {
        Producto producto = productos.get(c);
        validarProducto(producto);
        producto.setCategoria(cat);
        producto.setNombre(n);
        producto.setPrecio(p);
    }

    public double gananciasVendedor(String codigoVendedor) throws RegistroNoEncontradoException {
        Vendedor vendedor = vendedores.get(codigoVendedor);
        validarVendedor(vendedor);
        double gananciasTotales = vendedor.getSueldo() + calcularComision(vendedor);
        return gananciasTotales;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tienda{");
        sb.append("vendedores=").append(vendedores);
        sb.append(", productos=").append(productos);
        sb.append('}');
        return sb.toString();
    }


    public Vendedor buscarVendedor(String codVendedor) throws RegistroNoEncontradoException {
        Vendedor vendedor = vendedores.get(codVendedor);
        validarVendedor(vendedor);
        return vendedor;
    }


}
