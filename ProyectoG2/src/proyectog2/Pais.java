/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectog2;

/**
 *
 * @author jcamp
 */
public class Pais {
    private String pais;
    private String directorTecnico;

    public Pais(String pais, String directorTecnico) {
        this.pais = pais;
        this.directorTecnico = directorTecnico;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDirectorTecnico() {
        return directorTecnico;
    }

    public void setDirectorTecnico(String directorTecnico) {
        this.directorTecnico = directorTecnico;
    }

    @Override
    public String toString() {
        return "País{" + "País= " + pais + ", Director Tecnico= " + directorTecnico + '}';
    }
    
    
}
