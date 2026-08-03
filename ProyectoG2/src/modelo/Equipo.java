/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * clase que representa un equipo, o sea una seleccion nacional participante en
 * el mundial, es como el perfil de un clan completo en un juego de guerra, o el
 * perfil de un equipo en un modo carrera de un juego de futbol, contiene el
 * pais, el entrenador, la plantilla de jugadores, y las estadisticas de
 * rendimiento en el torneo
 *
 * @author grupo2
 */
public class Equipo {

    // nombre del pais o seleccion, como el nombre de tu clan o tu gremio dentro del juego
    private String nombrePais;

    // nombre del director tecnico, como el lider de clan que no juega pero influye en el equipo
    private String directorTecnico;

    // arreglo de jugadores que forman la plantilla, tamaño definido en el constructor,
    // como el roster completo de tu escuadra en un juego de esports
    private Jugador[] plantilla;

    // puntos acumulados en el torneo, arranca en cero, exacto como el puntaje de tu equipo
    // en una liga de un juego deportivo, sube tres si ganas, uno si empatas, cero si pierdes
    private int puntos;

    // goles a favor acumulados en todo el torneo
    private int golesFavor;

    // goles en contra acumulados en todo el torneo
    private int golesContra;

    // diferencia entre goles a favor y en contra, se usa como criterio de desempate,
    // como el kill death ratio en un shooter, entre mas alto mejor es tu rendimiento neto
    private int diferenciaGoles;

    /**
     * constructor de la clase equipo, recibe pais, tecnico y tamaño de
     * plantilla, crea el arreglo de plantilla vacio con ese tamaño, y pone
     * puntos, goles y diferencia en cero porque el equipo apenas esta empezando
     * el torneo, es como crear un nuevo perfil de clan, defines cuantos
     * miembros maximo puede tener pero todavia no reclutas a nadie, si le pasas
     * un tamaño de plantilla en cero, el equipo existiria pero sin ningun
     * espacio para jugadores, como crear un clan con capacidad maxima de cero
     * miembros
     *
     * @param nombrePais nombre del pais
     * @param directorTecnico nombre del entrenador
     * @param tamanioPlantilla numero de jugadores que tendra la plantilla, por
     * ejemplo 23
     */
    public Equipo(String nombrePais, String directorTecnico, int tamanioPlantilla) {
        // guarda el nombre del pais
        this.nombrePais = nombrePais;

        // guarda el nombre del director tecnico
        this.directorTecnico = directorTecnico;

        // crea el arreglo de jugadores con el tamaño indicado, todos los espacios en null al inicio,
        // se llenaran despues desde afuera de la clase
        this.plantilla = new Jugador[tamanioPlantilla];

        // inicializa puntos en cero porque el equipo todavia no ha jugado nada
        this.puntos = 0;

        // inicializa goles a favor en cero
        this.golesFavor = 0;

        // inicializa goles en contra en cero
        this.golesContra = 0;

        // inicializa diferencia de goles en cero
        this.diferenciaGoles = 0;
    }

    /**
     * getter para el nombre del pais
     *
     * @return nombre del pais
     */
    public String getNombrePais() {
        return nombrePais; // devuelve el nombre guardado
    }

    /**
     * setter para el nombre del pais
     *
     * @param nombrePais nuevo nombre del pais
     */
    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais; // reemplaza el nombre guardado
    }

    /**
     * getter para el director tecnico
     *
     * @return nombre del entrenador
     */
    public String getDirectorTecnico() {
        return directorTecnico; // devuelve el nombre guardado
    }

    /**
     * setter para el director tecnico, cambiarlo no afecta el rendimiento del
     * equipo en la simulacion actual porque el nombre del tecnico no se usa en
     * ningun calculo, es puramente cosmetico, como cambiar el skin de tu
     * personaje sin afectar sus stats
     *
     * @param directorTecnico nuevo nombre del entrenador
     */
    public void setDirectorTecnico(String directorTecnico) {
        this.directorTecnico = directorTecnico; // reemplaza el nombre guardado
    }

    /**
     * getter para la plantilla de jugadores, devuelve la referencia real, no
     * una copia
     *
     * @return arreglo de jugadores
     */
    public Jugador[] getPlantilla() {
        return plantilla; // devuelve el arreglo tal cual esta guardado
    }

    /**
     * setter para la plantilla de jugadores, si reemplazas la plantilla por un
     * arreglo de tamaño distinto al original, cualquier parte del codigo que
     * recorra la plantilla asumiendo el tamaño viejo puede fallar, igual que
     * cambiar el limite de inventario de un personaje a la mitad de camino en
     * un juego sin avisarle al resto del sistema
     *
     * @param plantilla nuevo arreglo de jugadores
     */
    public void setPlantilla(Jugador[] plantilla) {
        this.plantilla = plantilla; // reemplaza el arreglo completo de jugadores
    }

    /**
     * getter para los puntos del equipo
     *
     * @return puntos acumulados
     */
    public int getPuntos() {
        return puntos; // devuelve el valor guardado
    }

    /**
     * setter para los puntos del equipo, permite forzar cualquier numero
     * manualmente, es lo que usa internamente simularpartido cuando reparte
     * tres puntos al ganador o uno a cada uno en empate, si usaras el setter
     * para poner puntos negativos, el marcador dejaria de tener sentido
     * futbolistico aunque el ordenamiento siga funcionando matematicamente,
     * como hackear tu propio puntaje con comandos de consola en un juego
     *
     * @param puntos nuevos puntos del equipo
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos; // reemplaza el valor guardado
    }

    /**
     * getter para los goles a favor
     *
     * @return goles a favor acumulados
     */
    public int getGolesFavor() {
        return golesFavor; // devuelve el valor guardado
    }

    /**
     * setter para los goles a favor
     *
     * @param golesFavor nuevos goles a favor
     */
    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor; // reemplaza el valor guardado
    }

    /**
     * getter para los goles en contra
     *
     * @return goles en contra acumulados
     */
    public int getGolesContra() {
        return golesContra; // devuelve el valor guardado
    }

    /**
     * setter para los goles en contra
     *
     * @param golesContra nuevos goles en contra
     */
    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra; // reemplaza el valor guardado
    }

    /**
     * getter para la diferencia de goles
     *
     * @return diferencia de goles, goles a favor menos goles en contra
     */
    public int getDiferenciaGoles() {
        return diferenciaGoles; // devuelve el valor guardado
    }

    /**
     * setter para la diferencia de goles, normalmente no se usa a mano porque
     * existe el metodo actualizardiferencia que la recalcula solo, si usas este
     * setter con un valor que no coincide con golesfavor menos golescontra, la
     * diferencia queda mintiendo, como editar manualmente el kd ratio en un
     * juego sin que coincida con las kills reales
     *
     * @param diferenciaGoles nueva diferencia de goles
     */
    public void setDiferenciaGoles(int diferenciaGoles) {
        this.diferenciaGoles = diferenciaGoles; // reemplaza el valor guardado
    }

    /**
     * recalcula la diferencia de goles usando los valores actuales de
     * golesfavor y golescontra, no recibe parametros, se llama despues de cada
     * partido simulado para mantener todo sincronizado, es como el sistema
     * automatico de un juego que recalcula tu ranking al terminar cada partida,
     * sin que tu tengas que hacerlo manualmente
     */
    public void actualizarDiferencia() {
        this.diferenciaGoles = this.golesFavor - this.golesContra; // resta directa entre los dos valores guardados
    }
}
