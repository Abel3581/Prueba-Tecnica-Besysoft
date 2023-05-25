package Model;

import exception.CodigoInvalidoException;
import exception.PrecioInvalidoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vendedor {

    private String codigo;
    private String nombre;
    private Double sueldo;
    private List<Producto> ventas = new ArrayList<>();

    public Vendedor() {
    }

    public Vendedor(String codigo, String nombre, Double sueldo, List<Producto> ventas) throws CodigoInvalidoException, PrecioInvalidoException {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.ventas = ventas;
        validarCodigo(codigo);
        validarSueldo(sueldo);
    }

    public Vendedor(String codigo, String nombre, Double sueldo) throws CodigoInvalidoException, PrecioInvalidoException {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sueldo = sueldo;
        validarCodigo(codigo);
        validarSueldo(sueldo);
    }


    public void agregarVenta(Producto producto) {
        ventas.add(producto);
    }

    private void validarCodigo(String codigo) throws CodigoInvalidoException {
        if (codigo == null || codigo.isEmpty()) {
            throw new CodigoInvalidoException("El código del vendedor no puede estar vacío.");
        }
    }

    private void validarSueldo(double sueldo) throws PrecioInvalidoException {
        if (sueldo <= 0) {
            throw new PrecioInvalidoException("El sueldo del vendedor debe ser mayor que cero.");
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

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public List<Producto> getVentas() {
        return ventas;
    }

    public void setVentas(List<Producto> ventas) {
        this.ventas = ventas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vendedor vendedor = (Vendedor) o;

        if (!Objects.equals(codigo, vendedor.codigo)) return false;
        if (!Objects.equals(nombre, vendedor.nombre)) return false;
        if (!Objects.equals(sueldo, vendedor.sueldo)) return false;
        return Objects.equals(ventas, vendedor.ventas);
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (sueldo != null ? sueldo.hashCode() : 0);
        result = 31 * result + (ventas != null ? ventas.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("+--------------------------------------------------------------------+\n");
        sb.append(String.format("| %-10s | %-20s | %-10s | %-17s |\n", "Código", "Nombre", "Sueldo", "Ventas"));
        sb.append("+--------------------------------------------------------------------+\n");

        sb.append(String.format("| %-10s | %-20s | %-10s | %-17s |\n", codigo, nombre, sueldo, ""));

        sb.append("+--------------------------------------------------------------------+\n");
        sb.append("| Código     | Nombre               | Precio     | Categoría         |\n");
        sb.append("+--------------------------------------------------------------------+\n");

        for (Producto producto : ventas) {
            sb.append(String.format("| %-10s | %-20s | %-10s | %-17s |\n", producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getCategoria()));
        }

        sb.append("+--------------------------------------------------------------------+\n");
        return sb.toString();
    }

    public void eliminarVenta(Producto producto) {
        ventas.remove(producto);
    }
}
