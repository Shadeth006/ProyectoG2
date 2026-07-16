/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectog2;

/**
 *
 * @author jcamp
 */
public class Arbitros {
    private String nombreArbitro;

    public Arbitros(String nombreArbitro) {
        this.nombreArbitro = nombreArbitro;
    }

    public String getNombreArbitro() {
        return nombreArbitro;
    }

    public void setNombreArbitro(String nombreArbitro) {
        this.nombreArbitro = nombreArbitro;
    }

    @Override
    public String toString() {
        return "Arbitros{" + "nombreArbitro=" + nombreArbitro + '}';
    }
    
    
}
