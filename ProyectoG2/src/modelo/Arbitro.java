/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * clase que representa a un arbitro de futbol, es una clase sencilla, como un
 * npc guardia en un juego de rol, solo tiene nombre y nacionalidad, no tiene
 * comportamiento propio, es puramente informativa, se usa para asignar arbitros
 * a los partidos
 *
 * @author grupo2
 */
public class Arbitro {

    // nombre del arbitro, como el nombre de un npc comerciante que aparece en el mundo del juego
    private String nombre;

    // nacionalidad del arbitro, como el bioma de origen de un npc en un juego con region de spawn
    private String nacionalidad;

    /**
     * constructor de la clase arbitro, recibe nombre y nacionalidad y los
     * guarda directamente, no hay logica adicional, es como poner un npc fijo
     * en el mundo con datos que no cambian solos
     *
     * @param nombre el nombre del arbitro
     * @param nacionalidad la nacionalidad del arbitro
     */
    public Arbitro(String nombre, String nacionalidad) {
        this.nombre = nombre; // guarda el nombre recibido tal cual
        this.nacionalidad = nacionalidad; // guarda la nacionalidad recibida tal cual
    }

    /**
     * getter para el nombre del arbitro, permite leerlo desde otras clases
     *
     * @return el nombre del arbitro
     */
    public String getNombre() {
        return nombre; // devuelve el nombre guardado
    }

    /**
     * setter para el nombre del arbitro, permite modificarlo desde otras clases
     *
     * @param nombre el nuevo nombre del arbitro
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // reemplaza el nombre guardado
    }

    /**
     * getter para la nacionalidad del arbitro
     *
     * @return la nacionalidad del arbitro
     */
    public String getNacionalidad() {
        return nacionalidad; // devuelve la nacionalidad guardada
    }

    /**
     * setter para la nacionalidad del arbitro, cambiarla no afecta ninguna otra
     * parte del sistema, es un dato puramente decorativo, como el color de piel
     * de un npc que no afecta sus stats de combate
     *
     * @param nacionalidad la nueva nacionalidad del arbitro
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad; // reemplaza la nacionalidad guardada
    }
}
