package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.direccion.DatosDireccion;
import org.hibernate.annotations.Any;

public record DatosRegistroMedico(
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String telefono,
        @NotBlank
        @Pattern(regexp ="\\d{4,6}")
        String documento,
        @NotNull
        Especialidad especialidad,
        @NotNull
        @Valid
        DatosDireccion direccion) {


}
