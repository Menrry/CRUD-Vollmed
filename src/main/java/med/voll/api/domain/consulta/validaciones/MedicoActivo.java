package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MedicoActivo implements ValidadorDeConsultas {

    @Autowired
    private MedicoRepository repository;

    public void validar(DatosAgendarConsulta datos){
        if(datos.idMedico()==null){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }
        var medicoActivo = repository.findActivoById(datos.idMedico());

        if(!medicoActivo){
            throw  new ValidationException("No se puede permitir agendar citas con medicos inactivos en el sistema");
        }
    }
}
