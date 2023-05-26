package model;

import exception.CodigoInvalidoException;
import exception.PrecioInvalidoException;

import java.util.Objects;

public class Producto {

    private String codigo;
    private String nombre;
    private Double precio;
    private String categoria;

    public Producto() {
    }

    public Producto(String codigo, String nombre, Double precio, String categoria) throws CodigoInvalidoException, PrecioInvalidoException {

        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        validarCodigo(codigo);
        validarPrecio(precio);
    }

    private void validarCodigo(String codigo) throws CodigoInvalidoException {
        if (codigo == null || codigo.isEmpty()) {
            throw new CodigoInvalidoException("El código del producto no puede estar vacío.");
        }
    }

    private void validarPrecio(double precio) throws PrecioInvalidoException {
        if (precio <= 0.0) {
            throw new PrecioInvalidoException("El precio del producto debe ser mayor que cero.");
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto producto = (Producto) o;

        if (!Objects.equals(codigo, producto.codigo)) return false;
        if (!Objects.equals(nombre, producto.nombre)) return false;
        if (!Objects.equals(precio, producto.precio)) return false;
        return Objects.equals(categoria, producto.categoria);
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (precio != null ? precio.hashCode() : 0);
        result = 31 * result + (categoria != null ? categoria.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
            sb.append("+------------------------------------------------------------------+\n");
            sb.append(String.format("| %-10s | %-20s | %-10s | %-15s |\n", "Código", "Nombre", "Precio", "Categoría"));
            sb.append("+------------------------------------------------------------------+\n");
            sb.append(String.format("| %-10s | %-20s | %-10s | %-15s |\n", codigo, nombre, precio, categoria));
            sb.append("+------------------------------------------------------------------+");
        return sb.toString();
    }
}
