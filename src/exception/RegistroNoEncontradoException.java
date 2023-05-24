package exception;

public class RegistroNoEncontradoException extends Exception{
    private String tipoRegistro;

    public RegistroNoEncontradoException(String mensaje, String tipoRegistro) {
        super(mensaje);
        this.tipoRegistro = tipoRegistro;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }
}
