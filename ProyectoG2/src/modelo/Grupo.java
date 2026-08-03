/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * clase que representa un grupo del torneo mundial, cada grupo tiene cuatro
 * equipos y seis partidos, es literalmente como una party o un clan de cuatro
 * jugadores en un juego, donde los cuatro compiten entre si antes de pasar a la
 * siguiente fase
 *
 * @author grupo2
 */
public class Grupo {

    // nombre identificador del grupo, como grupo a o grupo b, es el nombre de una sala o lobby en un juego online
    private String nombre;

    // arreglo de los cuatro equipos del grupo, siempre debe tener exactamente cuatro elementos,
    // como los cuatro slots de un equipo en un juego de battle royale por escuadras
    private Equipo[] equipos;

    // arreglo de los seis partidos del grupo, seis es el numero exacto de combinaciones posibles
    // entre cuatro elementos tomados de dos en dos, si el numero de equipos por grupo cambiara
    // sin ajustar este numero, el sistema se rompe, como reservar solo tres sillas para cuatro jugadores en un lobby
    private Partido[] partidos;

    /**
     * constructor del grupo, recibe el nombre y crea automaticamente los dos
     * arreglos vacios, equipos con cuatro espacios null y partidos con seis
     * espacios null, es como generar un nuevo lobby en un juego, el lobby
     * existe pero los slots todavia estan vacios
     *
     * @param nombre el nombre identificador del grupo
     */
    public Grupo(String nombre) {
        // guarda el nombre del grupo
        this.nombre = nombre;
        // crea el arreglo de equipos con tamaño fijo cuatro, todos los espacios en null al inicio
        this.equipos = new Equipo[4];
        // crea el arreglo de partidos con tamaño fijo seis, todos los espacios en null al inicio
        this.partidos = new Partido[6];
    }

    /**
     * getter para el nombre del grupo
     *
     * @return el nombre del grupo
     */
    public String getNombre() {
        return nombre; // devuelve el nombre guardado
    }

    /**
     * setter para el nombre del grupo, cambia solo la etiqueta visible
     *
     * @param nombre nuevo nombre del grupo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // reemplaza el nombre guardado
    }

    /**
     * getter para el arreglo de equipos del grupo, devuelve la referencia real,
     * no una copia
     *
     * @return arreglo de cuatro equipos
     */
    public Equipo[] getEquipos() {
        return equipos; // devuelve el arreglo tal cual esta guardado
    }

    /**
     * setter para el arreglo de equipos del grupo, se usa en la clase mundial
     * durante el sorteo, como cuando un juego reordena aleatoriamente a los
     * jugadores en los lobbies antes de una ronda, si le pasas un arreglo de
     * tamaño distinto a cuatro, mas adelante cuando otra clase intente acceder
     * a la posicion tres asumiendo que hay cuatro, el programa truena por
     * indice fuera de rango, como intentar acceder al quinto slot de un
     * inventario de minecraft que solo tiene cuatro espacios
     *
     * @param equipos nuevo arreglo de cuatro equipos
     */
    public void setEquipos(Equipo[] equipos) {
        this.equipos = equipos; // reemplaza el arreglo completo de equipos
    }

    /**
     * getter para el arreglo de partidos del grupo
     *
     * @return arreglo de seis partidos
     */
    public Partido[] getPartidos() {
        return partidos; // devuelve el arreglo tal cual esta guardado
    }

    /**
     * setter para el arreglo de partidos del grupo, se llena cuando la clase
     * mundial genera el calendario
     *
     * @param partidos nuevo arreglo de seis partidos
     */
    public void setPartidos(Partido[] partidos) {
        this.partidos = partidos; // reemplaza el arreglo completo de partidos
    }
}
