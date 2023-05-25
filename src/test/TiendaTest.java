package test;

import Model.Vendedor;
import exception.CodigoInvalidoException;
import exception.PrecioInvalidoException;
import exception.VendedorExistenteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Tienda;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TiendaTest {

    private Tienda tienda;

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
}