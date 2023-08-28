package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo medico en la base de datos")
    public ResponseEntity<DatosRespuestaMedico> registrarMedico (@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
        // luego de crearlo en la base de datos retorna un 201 que signigica creado,
        // además retorna lo que está en DatosRespuestaMedico
    }



    //public List<DatosListadoMedico> listadoMedicos(){  // al trabajar con listas
    //return medicoRepository.findAll().stream().map(DatosListadoMedico::new).toList();

    @GetMapping                                                           // Aquí le digo que envíe 2
    @Operation(summary = "Obtiene el listado de medicos")
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){  // para prabajar con paginas
       return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));//Aplicando del Delete lógico
//Ahora estádentro de un wrapper con envoltura para nuestra request HTTP

       // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new); //antes del Delete lógico

       //return medicoRepository.findAll();  // retorna toda la entidad
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza los datos de un medico existente")
    public ResponseEntity actualizarMedico(@RequestBody DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
            medico.getTelefono(), medico.getEspecialidad().toString(),
            new DatosDireccion(medico.getDireccion().getCalle(),
                    medico.getDireccion().getDistrito(),
                    medico.getDireccion().getCiudad(),
                    medico.getDireccion().getNumero(),
                    medico.getDireccion().getComplemento())));
    }
// Delete Lógico

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un medico registrandolo como - inactivo")
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();  //Retorna 204 despues de eliminación lógica. No content
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los registros del medico con ID")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }




/*
     // Delete en Base de datos
     @DeleteMapping("/{id}")
     @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }
*/

}