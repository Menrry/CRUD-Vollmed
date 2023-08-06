package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public void registrarMedico (@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        medicoRepository.save(new Medico(datosRegistroMedico));
    }



    //public List<DatosListadoMedico> listadoMedicos(){  // al trabajar con listas
    //return medicoRepository.findAll().stream().map(DatosListadoMedico::new).toList();

    @GetMapping                                                           // Aquí le digo que envíe 2 datos
    public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){  // para prabajar con paginas
//Aplicando el delete Lógico
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);//Aplicando del Delete lógico


       // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new); //antes del Delete lógico

       //return medicoRepository.findAll();  // retorna toda la entidad
    }

    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
    }
// Delete Lógico

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
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