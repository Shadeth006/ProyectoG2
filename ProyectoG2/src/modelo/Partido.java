/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * clase que representa un partido de futbol, es como una partida individual
 * dentro de un torneo en un videojuego, aca se guarda todo lo que paso en ese
 * enfrentamiento especifico, quien jugo, donde, quien arbitro, cuantos goles
 * hubo, quien anoto, quien vio tarjeta, y cuanta gente fue, se usa tanto en
 * fase de grupos como en eliminatorias
 *
 * @author grupo2
 */
public class Partido {

    // equipo local, referencia al objeto equipo que juega en casa
    private Equipo local;

    // equipo visitante, referencia al objeto equipo que juega de visita
    private Equipo visitante;

    // sede o estadio donde se juega el partido
    private Sede sede;

    // arbitro asignado para dirigir el partido
    private Arbitro arbitro;

    // goles marcados por el equipo local, arranca en cero
    private int golesLocal;

    // goles marcados por el equipo visitante, arranca en cero
    private int golesVisitante;

    // arreglo de jugadores que anotaron en este partido, tamaño fijo diez,
    // el limite diez asume que nunca habra mas de diez goles combinados en un partido,
    // si eso pasara el codigo tronaria por intentar escribir en una posicion que no existe,
    // como intentar guardar el onceavo item en una mochila de minecraft que solo tiene diez slots
    private Jugador[] goleadores;

    // arreglo de jugadores que recibieron tarjeta amarilla, tamaño fijo diez, mismo limite y mismo riesgo
    private Jugador[] tarjetasAmarillas;

    // arreglo de jugadores que recibieron tarjeta roja, tamaño fijo diez, mismo limite y mismo riesgo
    private Jugador[] tarjetasRojas;

    // indica si el partido ya fue simulado, como el estado completado o pendiente de una mision en un juego
    private boolean jugado;

    // indica si el partido se definio por penales, solo aplica en fase eliminatoria
    private boolean penales;

    // cantidad de espectadores que asistieron al partido
    private int asistencia;

    /**
     * constructor del partido, recibe los equipos, la sede y el arbitro, e
     * inicializa los goles en cero, los arreglos de goleadores y tarjetas
     * vacios con tamaño diez, el estado jugado y penales en falso, y la
     * asistencia en cero, es exactamente como cuando un juego te muestra la
     * pantalla de vs antes de un combate, ya sabe quienes van a pelear y en que
     * escenario, pero el marcador todavia esta en cero a cero, si le pasas el
     * mismo equipo como local y como visitante el codigo no lo impide, el
     * partido se simularia igual pero un equipo estaria jugando contra si mismo
     *
     * @param local equipo local
     * @param visitante equipo visitante
     * @param sede estadio donde se juega
     * @param arbitro arbitro asignado
     */
    public Partido(Equipo local, Equipo visitante, Sede sede, Arbitro arbitro) {
        this.local = local; // guarda el equipo local
        this.visitante = visitante; // guarda el equipo visitante
        this.sede = sede; // guarda la sede
        this.arbitro = arbitro; // guarda el arbitro

        // inicializa goles en cero, el partido todavia no ha empezado
        this.golesLocal = 0;
        this.golesVisitante = 0;

        // crea los arreglos para goleadores y tarjetas con tamaño diez cada uno, todos vacios al inicio
        this.goleadores = new Jugador[10];
        this.tarjetasAmarillas = new Jugador[10];
        this.tarjetasRojas = new Jugador[10];

        // marca el partido como no jugado y sin penales, todavia no ha pasado nada
        this.jugado = false;
        this.penales = false;

        // asistencia inicial en cero, todavia no hay nadie en las gradas
        this.asistencia = 0;
    }

    /**
     * getter para el equipo local
     *
     * @return equipo local
     */
    public Equipo getLocal() {
        return local; // devuelve la referencia guardada
    }

    /**
     * setter para el equipo local, cambiarlo despues de creado el partido
     * permite reemplazar quien juega sin cambiar el resto de los datos ya
     * generados, puede generar inconsistencias si el partido ya fue jugado con
     * los datos del equipo anterior
     *
     * @param local nuevo equipo local
     */
    public void setLocal(Equipo local) {
        this.local = local; // reemplaza la referencia guardada
    }

    /**
     * getter para el equipo visitante
     *
     * @return equipo visitante
     */
    public Equipo getVisitante() {
        return visitante; // devuelve la referencia guardada
    }

    /**
     * setter para el equipo visitante
     *
     * @param visitante nuevo equipo visitante
     */
    public void setVisitante(Equipo visitante) {
        this.visitante = visitante; // reemplaza la referencia guardada
    }

    /**
     * getter para la sede del partido
     *
     * @return sede del partido
     */
    public Sede getSede() {
        return sede; // devuelve la referencia guardada
    }

    /**
     * setter para la sede del partido
     *
     * @param sede nueva sede del partido
     */
    public void setSede(Sede sede) {
        this.sede = sede; // reemplaza la referencia guardada
    }

    /**
     * getter para el arbitro del partido
     *
     * @return arbitro del partido
     */
    public Arbitro getArbitro() {
        return arbitro; // devuelve la referencia guardada
    }

    /**
     * setter para el arbitro del partido
     *
     * @param arbitro nuevo arbitro del partido
     */
    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro; // reemplaza la referencia guardada
    }

    /**
     * getter para los goles del local
     *
     * @return goles del local
     */
    public int getGolesLocal() {
        return golesLocal; // devuelve el valor guardado
    }

    /**
     * setter para los goles del local, si lo usas manualmente el marcador
     * cambia pero las estadisticas de goleadores no se actualizan solas,
     * quedarian desincronizadas
     *
     * @param golesLocal nuevos goles del local
     */
    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal; // reemplaza el valor guardado
    }

    /**
     * getter para los goles del visitante
     *
     * @return goles del visitante
     */
    public int getGolesVisitante() {
        return golesVisitante; // devuelve el valor guardado
    }

    /**
     * setter para los goles del visitante
     *
     * @param golesVisitante nuevos goles del visitante
     */
    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante; // reemplaza el valor guardado
    }

    /**
     * getter para el arreglo de goleadores del partido
     *
     * @return arreglo de jugadores que anotaron
     */
    public Jugador[] getGoleadores() {
        return goleadores; // devuelve el arreglo guardado
    }

    /**
     * setter para el arreglo de goleadores del partido
     *
     * @param goleadores nuevo arreglo de goleadores
     */
    public void setGoleadores(Jugador[] goleadores) {
        this.goleadores = goleadores; // reemplaza el arreglo guardado
    }

    /**
     * getter para el arreglo de tarjetas amarillas del partido
     *
     * @return arreglo de jugadores amonestados
     */
    public Jugador[] getTarjetasAmarillas() {
        return tarjetasAmarillas; // devuelve el arreglo guardado
    }

    /**
     * setter para el arreglo de tarjetas amarillas del partido
     *
     * @param tarjetasAmarillas nuevo arreglo de amarillas
     */
    public void setTarjetasAmarillas(Jugador[] tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas; // reemplaza el arreglo guardado
    }

    /**
     * getter para el arreglo de tarjetas rojas del partido
     *
     * @return arreglo de jugadores expulsados
     */
    public Jugador[] getTarjetasRojas() {
        return tarjetasRojas; // devuelve el arreglo guardado
    }

    /**
     * setter para el arreglo de tarjetas rojas del partido
     *
     * @param tarjetasRojas nuevo arreglo de rojas
     */
    public void setTarjetasRojas(Jugador[] tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas; // reemplaza el arreglo guardado
    }

    /**
     * revisa si el partido ya se jugo, es una pregunta tipo si o no, como
     * preguntarle al juego si una mision ya esta completada
     *
     * @return true si el partido ya fue jugado
     */
    public boolean isJugado() {
        return jugado; // devuelve el estado guardado
    }

    /**
     * setter para el estado jugado, si pones jugado en true sin haber corrido
     * la simulacion antes, el partido va a aparecer como completado en metodos
     * que revisan esto pero con el marcador todavia en cero a cero, como marcar
     * una mision como completada desde el menu de pausa sin haber hecho nada
     * dentro de la mision
     *
     * @param jugado nuevo estado del partido
     */
    public void setJugado(boolean jugado) {
        this.jugado = jugado; // reemplaza el estado guardado
    }

    /**
     * revisa si el partido se definio por penales
     *
     * @return true si hubo penales
     */
    public boolean isPenales() {
        return penales; // devuelve el estado guardado
    }

    /**
     * setter para el estado de penales, solo se pone en true dentro de la
     * simulacion eliminatoria cuando hay empate en fase eliminatoria
     *
     * @param penales nuevo estado de penales
     */
    public void setPenales(boolean penales) {
        this.penales = penales; // reemplaza el estado guardado
    }

    /**
     * getter para la asistencia del partido
     *
     * @return cantidad de espectadores
     */
    public int getAsistencia() {
        return asistencia; // devuelve el valor guardado
    }

    /**
     * setter para la asistencia del partido, este numero despues se suma en el
     * total del torneo y se multiplica por cincuenta para calcular ingresos, no
     * hay validacion que compare la asistencia contra la capacidad de la sede,
     * si pones un numero mayor a la capacidad real, el sistema lo acepta igual,
     * como meter mas gente de la que caben las gradas en un juego de gestion de
     * estadios
     *
     * @param asistencia nueva cantidad de espectadores
     */
    public void setAsistencia(int asistencia) {
        this.asistencia = asistencia; // reemplaza el valor guardado
    }
}
