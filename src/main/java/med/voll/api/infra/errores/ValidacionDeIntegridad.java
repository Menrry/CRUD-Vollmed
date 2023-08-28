package med.voll.api.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {  // sólo responde ante excepcion
    public ValidacionDeIntegridad(String s) {

        super(s);
    }
}
