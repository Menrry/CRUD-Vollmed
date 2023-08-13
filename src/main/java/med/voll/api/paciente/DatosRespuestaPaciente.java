package med.voll.api.paciente;

import med.voll.api.direccion.DatosDireccion;

public record DatosRespuestaPaciente(long id, String nombre, String email, String telefono, String documento,
                                     DatosDireccion datosDireccion) {
}
