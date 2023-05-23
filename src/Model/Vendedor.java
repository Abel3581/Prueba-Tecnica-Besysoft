package Model;

import java.util.ArrayList;
import java.util.List;

public class Vendedor {

    private Long codigo;
    private String nombre;
    private Double sueldo;
    private List<Producto> ventas;

    public Vendedor() {
    }

    public Vendedor(Long codigo, String nombre, Double sueldo, List<Producto> ventas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.ventas = new ArrayList<>();
    }

    public void agregarVenta(Producto producto) {
        ventas.add(producto);
    }

    private void validarCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código del vendedor no puede estar vacío.");
        }
    }

    private void validarSueldo(double sueldo) {
        if (sueldo <= 0) {
            throw new IllegalArgumentException("El sueldo del vendedor debe ser mayor que cero.");
        }
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
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


}
