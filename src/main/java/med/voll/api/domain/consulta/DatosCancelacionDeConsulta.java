package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelacionDeConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelacion motivo
) {

}
