package med.voll.api.direccion;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private String calle;
    private String numero;
    private String complemento;
    private String distrito;
    private String ciudad;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.complemento = direccion.complemento();
        this.ciudad = direccion.ciudad();
    }

    public Direccion actualizarDatos(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.complemento = direccion.complemento();
        this.ciudad = direccion.ciudad();
        return this;
    }

    public void atualizarInformacion(DatosDireccion direccion) {
        if (direccion.calle() != null){
            this.calle = direccion.calle();
        }
        if (direccion.numero() != null){
            this.numero = direccion.numero();
        }
        if (direccion.distrito() != null){
            this.distrito = direccion.distrito();
        }
        if (direccion.complemento() != null){
            this.complemento = direccion.complemento();
        }
        if (direccion.ciudad() != null){
            this.ciudad = direccion.ciudad();
        }
    }
}
