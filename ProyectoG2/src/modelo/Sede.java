/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * clase que representa una sede, o sea un estadio del torneo, es el equivalente
 * a un mapa o una arena en un juego como fortnite, o un mapa de minecraft para
 * minijuegos, cada sede tiene nombre, ciudad y capacidad, se usa para asignar
 * un estadio a cada partido
 *
 * @author grupo2
 */
public class Sede {

    // nombre del estadio, como el nombre del mapa dentro del juego
    private String nombre;

    // ciudad donde esta ubicado el estadio, como el bioma en el que esta puesto el mapa
    private String ciudad;

    // capacidad maxima de espectadores, este dato es clave porque limita cuanta gente puede asistir a un partido ahi
    private int capacidad;

    /**
     * constructor de la sede, recibe los tres datos fijos y los guarda, si le
     * pones una capacidad muy baja el partido que se juegue ahi nunca podra
     * tener mas gente de la que ese numero permite, exacto como elegir un mapa
     * pequeño en un juego de gestion deportiva, el aforo maximo queda limitado
     * por el estadio elegido sin importar que tan famoso sea el equipo
     *
     * @param nombre nombre del estadio
     * @param ciudad ciudad del estadio
     * @param capacidad capacidad maxima de espectadores
     */
    public Sede(String nombre, String ciudad, int capacidad) {
        this.nombre = nombre; // guarda el nombre del estadio
        this.ciudad = ciudad; // guarda la ciudad del estadio
        this.capacidad = capacidad; // guarda la capacidad maxima
    }

    /**
     * getter para el nombre del estadio
     *
     * @return el nombre del estadio
     */
    public String getNombre() {
        return nombre; // devuelve el nombre guardado
    }

    /**
     * setter para el nombre del estadio
     *
     * @param nombre nuevo nombre del estadio
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // reemplaza el nombre guardado
    }

    /**
     * getter para la ciudad del estadio
     *
     * @return la ciudad del estadio
     */
    public String getCiudad() {
        return ciudad; // devuelve la ciudad guardada
    }

    /**
     * setter para la ciudad del estadio
     *
     * @param ciudad nueva ciudad del estadio
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad; // reemplaza la ciudad guardada
    }

    /**
     * getter para la capacidad del estadio
     *
     * @return la capacidad maxima de espectadores
     */
    public int getCapacidad() {
        return capacidad; // devuelve la capacidad guardada
    }

    /**
     * setter para la capacidad del estadio, este es el dato con mas impacto de
     * toda la clase, porque afecta directamente los ingresos totales del
     * torneo, que se calculan multiplicando la asistencia por cincuenta
     * dolares, si subes la capacidad de todas las sedes, el dinero total
     * generado por el mundial tambien sube, como subir el limite de jugadores
     * en un servidor, mas espacio significa potencialmente mas gente adentro y
     * mas ingresos si el juego cobra por entrada
     *
     * @param capacidad nueva capacidad maxima de espectadores
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad; // reemplaza la capacidad guardada
    }
}
