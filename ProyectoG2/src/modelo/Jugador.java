/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * clase que representa a un jugador de futbol, es como un personaje o un mob
 * dentro de un juego, asi como un zombie en minecraft tiene vida y velocidad,
 * aca el jugador tiene nombre, goles y tarjetas, es la pieza mas pequeña de
 * toda la jerarquia, muchos jugadores forman un equipo completo
 *
 * @author grupo2
 */
public class Jugador {

    // nombre del jugador, funciona como el nametag que le pones a una mascota en minecraft, cambiarlo no cambia su rendimiento
    private String nombre;

    // goles marcados, arranca en cero como el contador de kills al inicio de una partida en un shooter
    private int goles;

    // tarjetas amarillas recibidas, funcionan como advertencias antes de una sancion mayor, con dos ya se considera jugador sancionado
    private int tarjetasAmarillas;

    // tarjetas rojas recibidas, funcionan como un ban directo, con solo una ya se considera jugador sancionado
    private int tarjetasRojas;

    /**
     * constructor de la clase jugador, recibe el nombre e inicializa todo lo
     * demas en cero, es igual que cuando usas el comando summon en minecraft,
     * la entidad nace con sus stats base en cero y el mundo se encarga despues
     * de irlas modificando segun lo que le pase en cada partido
     *
     * @param nombre el nombre del jugador
     */
    public Jugador(String nombre) {
        // guarda el nombre recibido, como ponerle nombre a un personaje nuevo en un rpg
        this.nombre = nombre;
        // arranca en cero porque el jugador todavia no ha anotado nada, como un marcador recien encendido
        this.goles = 0;
        // arranca en cero porque todavia no ha recibido ninguna advertencia
        this.tarjetasAmarillas = 0;
        // arranca en cero porque todavia no ha sido expulsado de ningun partido
        this.tarjetasRojas = 0;
    }

    /**
     * getter para el nombre del jugador, funciona como leer el nametag actual
     *
     * @return el nombre del jugador
     */
    public String getNombre() {
        return nombre; // simplemente devuelve lo que hay guardado, sin cambiar nada
    }

    /**
     * setter para el nombre del jugador, es como usar el editor de nombre de un
     * personaje, cambiar el nombre no afecta sus goles ni sus tarjetas, solo
     * cambia la etiqueta visible
     *
     * @param nombre nuevo nombre del jugador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // reemplaza el nombre guardado por el nuevo
    }

    /**
     * getter para los goles del jugador
     *
     * @return numero de goles
     */
    public int getGoles() {
        return goles; // devuelve el contador actual de goles
    }

    /**
     * setter para los goles del jugador, permite forzar un valor especifico, es
     * como usar un comando de administrador para poner tu contador de kills en
     * cualquier numero sin jugar, ojo que no hay validacion, si le pones un
     * numero negativo el sistema lo acepta igual y eso puede romper rankings
     * despues
     *
     * @param goles nuevo numero de goles
     */
    public void setGoles(int goles) {
        this.goles = goles; // reemplaza el contador con el numero recibido, sin revisar si tiene sentido
    }

    /**
     * incrementa en uno el contador de goles del jugador, se usa cada vez que
     * el jugador anota, es como sumar un punto cuando rompes un bloque especial
     * en un minijuego de mineria, no recibe ningun parametro, simplemente
     * avanza el contador un paso
     */
    public void incrementarGoles() {
        this.goles++; // aumenta el valor guardado en uno
    }

    /**
     * getter para las tarjetas amarillas del jugador
     *
     * @return numero de tarjetas amarillas
     */
    public int getTarjetasAmarillas() {
        return tarjetasAmarillas; // devuelve el contador actual de amarillas
    }

    /**
     * setter para las tarjetas amarillas, permite forzar cualquier numero
     * manualmente
     *
     * @param tarjetasAmarillas nuevo numero de tarjetas amarillas
     */
    public void setTarjetasAmarillas(int tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas; // reemplaza el valor guardado
    }

    /**
     * incrementa en uno el contador de amarillas, es como cuando en un servidor
     * de minecraft te dan un warn por romper una regla, una sola no te expulsa
     * pero queda en tu historial
     */
    public void incrementarAmarilla() {
        this.tarjetasAmarillas++; // aumenta el contador de amarillas en uno
    }

    /**
     * getter para las tarjetas rojas del jugador
     *
     * @return numero de tarjetas rojas
     */
    public int getTarjetasRojas() {
        return tarjetasRojas; // devuelve el contador actual de rojas
    }

    /**
     * setter para las tarjetas rojas, permite forzar cualquier numero
     * manualmente
     *
     * @param tarjetasRojas nuevo numero de tarjetas rojas
     */
    public void setTarjetasRojas(int tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas; // reemplaza el valor guardado
    }

    /**
     * incrementa en uno el contador de rojas, es el equivalente a un ban
     * directo dentro del partido, en el reporte disciplinario de la clase
     * mundial, con una sola roja ya se considera jugador sancionado
     */
    public void incrementarRoja() {
        this.tarjetasRojas++; // aumenta el contador de rojas en uno
    }
}
