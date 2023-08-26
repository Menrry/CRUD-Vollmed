package med.voll.api.domain.consulta;


import med.voll.api.domain.medico.Especialidad;
import med.voll.api.domain.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    boolean existsByIdAndPacienteIdAndMedicoIdAndData(Long id, Long idPaciente, Long idMedico, LocalDateTime data);



}
