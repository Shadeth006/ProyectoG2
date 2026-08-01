/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.*; // importa todas las clases del modelo, equipo, jugador, partido, sede, arbitro, grupo
import java.util.Random; // clase random, es el motor de azar del juego, sin esto no habria resultados distintos cada vez
import java.util.Comparator; // clase comparator, se usa para ordenamientos personalizados

/**
 * clase principal que representa la logica de un torneo mundial de futbol, es
 * el equivalente al game manager de un videojuego, coordina todo, crea equipos,
 * arma grupos, genera el calendario, simula partidos, calcula quien pasa de
 * fase, y al final entrega un campeon, todas las demas clases son piezas, esta
 * es el tablero completo
 *
 * @author grupo2
 */
public class Mundial {

    // --- atributos de la clase ---
    private int tamanio; // cantidad total de equipos, como elegir el numero de jugadores antes de una partida de battle royale
    private int numGrupos; // cantidad de grupos, se calcula dividiendo tamanio entre cuatro
    private Equipo[] equipos; // arreglo con todos los equipos inscritos
    private Sede[] sedes; // arreglo con los estadios disponibles
    private Arbitro[] arbitros; // arreglo con los arbitros disponibles
    private Grupo[] grupos; // arreglo con todos los grupos del torneo
    private Partido[] calendario; // arreglo con todos los partidos de fase de grupos
    private Partido[] llavesOctavos; // partidos de octavos de final
    private Partido[] llavesCuartos; // partidos de cuartos de final
    private Partido[] llavesSemis; // partidos de semifinales
    private Partido[] llavesFinal; // partido de la gran final, exactamente el mismo patron de un bracket de eliminacion en un torneo de esports
    private Equipo campeon; // equipo ganador del torneo
    private Equipo subcampeon; // equipo que perdio la final
    private int partidoActualIndex; // puntero que recuerda hasta que partido del calendario ya se reviso, como un checkpoint de un juego
    private Random rand; // generador de numeros aleatorios, se usa en absolutamente todos los sorteos y resultados

    /**
     * constructor por defecto de la clase mundial, inicializa la instancia
     * aleatoria y el contador de partidos, es como cuando abres un juego de
     * gestion deportiva por primera vez, todavia no configuraste nada pero el
     * motor del juego ya esta encendido
     */
    public Mundial() {
        rand = new Random(); // crea el generador de numeros aleatorios
        partidoActualIndex = 0; // arranca el puntero de simulacion en cero
    }

    /**
     * configura el tamanio del torneo y reserva espacio para todas sus
     * estructuras, es como seleccionar el modo de juego y el numero de
     * jugadores antes de darle a empezar partida, si le pasas un numero que no
     * es multiplo de cuatro, algunos equipos podrian quedar sin grupo asignado,
     * como invitar dieciocho jugadores a un torneo de escuadras de cuatro, dos
     * se quedarian sin equipo
     *
     * @param tamanio cantidad total de equipos, por ejemplo 16, 24, 32
     */
    public void configurarTamanio(int tamanio) {
        this.tamanio = tamanio; // guarda el tamanio total de equipos
        this.numGrupos = tamanio / 4; // calcula el numero de grupos, cuatro equipos por grupo
        equipos = new Equipo[tamanio]; // crea el arreglo de equipos con la capacidad definida
        sedes = new Sede[tamanio / 2]; // crea el arreglo de sedes, la mitad del numero de equipos
        arbitros = new Arbitro[tamanio / 4]; // crea el arreglo de arbitros
        grupos = new Grupo[numGrupos]; // crea el arreglo de grupos

        // recorre y crea cada grupo con una letra consecutiva, grupo a, grupo b, grupo c, y asi sucesivamente
        for (int i = 0; i < numGrupos; i++) {
            grupos[i] = new Grupo("Grupo " + (char) ('A' + i)); // suma i al caracter a para generar letras consecutivas
        }

        // resetea todas las estructuras de partidos y resultados, como reiniciar el juego desde cero con reglas nuevas
        calendario = null; // limpia el calendario de partidos
        llavesOctavos = null; // limpia el arreglo de octavos
        llavesCuartos = null; // limpia el arreglo de cuartos
        llavesSemis = null; // limpia el arreglo de semis
        llavesFinal = null; // limpia la final
        campeon = null; // resetea el campeon
        subcampeon = null; // resetea el subcampeon
        partidoActualIndex = 0; // reinicia el puntero de simulacion
    }

    // --- metodos de acceso y asignacion, getters y setters ---
    /**
     * agrega un equipo en una posicion especifica del arreglo, solo si la
     * posicion es valida, es una proteccion silenciosa, como cuando un juego no
     * te deja soltar un item fuera del inventario, si le pasas una posicion
     * invalida el metodo simplemente no hace nada, no truena pero tampoco
     * agrega
     *
     * @param e equipo a agregar
     * @param pos posicion donde agregarlo
     */
    public void agregarEquipo(Equipo e, int pos) {
        if (pos >= 0 && pos < equipos.length) // verifica que la posicion este dentro del rango valido
        {
            equipos[pos] = e; // asigna el equipo en la posicion
        }
    }

    /**
     * obtiene el equipo ubicado en una posicion especifica, aca no hay
     * validacion de rango, si le pasas una posicion invalida el programa si
     * lanzaria un error, a diferencia de agregarequipo
     *
     * @param pos posicion a consultar
     * @return el equipo correspondiente
     */
    public Equipo getEquipo(int pos) {
        return equipos[pos]; // retorna el equipo en esa posicion sin verificar nada
    }

    /**
     * retorna el arreglo completo de equipos, devuelve la referencia real, no
     * una copia, cualquier cambio que hagas afuera sobre este arreglo afecta
     * directamente el original
     *
     * @return arreglo completo de equipos
     */
    public Equipo[] getEquipos() {
        return equipos; // devuelve la referencia directa al arreglo
    }

    /**
     * agrega una sede en una posicion especifica, mismo patron de validacion
     * que agregarequipo
     *
     * @param s sede a agregar
     * @param pos posicion donde agregarla
     */
    public void agregarSede(Sede s, int pos) {
        if (pos >= 0 && pos < sedes.length) // comprueba si el indice es valido
        {
            sedes[pos] = s; // asigna la sede en el arreglo
        }
    }

    /**
     * obtiene la sede en una posicion especifica
     *
     * @param pos posicion a consultar
     * @return la sede pedida
     */
    public Sede getSede(int pos) {
        return sedes[pos]; // retorna la sede sin verificar nada
    }

    /**
     * agrega un arbitro en una posicion especifica, mismo patron de validacion
     *
     * @param a arbitro a agregar
     * @param pos posicion donde agregarlo
     */
    public void agregarArbitro(Arbitro a, int pos) {
        if (pos >= 0 && pos < arbitros.length) // verifica que el indice no desborde
        {
            arbitros[pos] = a; // asigna el arbitro
        }
    }

    /**
     * obtiene el arbitro en la posicion indicada
     *
     * @param pos posicion a consultar
     * @return el arbitro
     */
    public Arbitro getArbitro(int pos) {
        return arbitros[pos]; // retorna el arbitro sin verificar nada
    }

    /**
     * retorna el arreglo completo con todos los grupos del torneo, tambien es
     * una referencia directa
     *
     * @return arreglo de grupos
     */
    public Grupo[] getGrupos() {
        return grupos; // devuelve la referencia directa al arreglo
    }

    /**
     * genera datos de demostracion automaticos para poblar equipos, plantillas,
     * sedes y arbitros, es el equivalente a un boton de generar mundo aleatorio
     * en minecraft, en vez de escribir cada dato a mano, este metodo lo hace
     * todo usando catalogos predefinidos de nombres
     */
    public void generarDatosDemo() {
        // catalogo de 150 paises de todos los continentes
        String[] paises = {
            // Africa (50)
            "Angola", "Argelia", "Benin", "Botswana", "BurkinaFaso", "Burundi", "CaboVerde", "Camerun",
            "Chad", "Comoras", "Congo", "CostaMarfil", "Egipto", "Eritrea", "Etiopia", "Gabon",
            "Gambia", "Ghana", "Guinea", "GuineaBissau", "GuineaEcuatorial", "Kenia", "Lesoto", "Liberia",
            "Libia", "Madagascar", "Malawi", "Mali", "Marruecos", "Mauricio", "Mauritania", "Mozambique",
            "Namibia", "Niger", "Nigeria", "RepublicaCentroafricana", "RepublicaDemocraticaCongo",
            "Ruanda", "SantoTome", "Senegal", "Seychelles", "SierraLeona", "Somalia", "Sudafrica",
            "Sudan", "SudanSur", "Tanzania", "Togo", "Tunez", "Uganda", "Yibuti", "Zambia", "Zimbabue",
            // Asia (40)
            "Afganistan", "ArabiaSaudita", "Banglades", "Butan", "Brunei", "Camboya", "China", "CoreaNorte",
            "CoreaSur", "EmiratosArabes", "Filipinas", "India", "Indonesia", "Irak", "Iran", "Japon",
            "Jordania", "Kazajistan", "Kuwait", "Kirguistan", "Laos", "Libano", "Malasia", "Maldivas",
            "Mongolia", "Birmania", "Nepal", "Oman", "Pakistan", "Palestina", "Qatar", "Singapur",
            "Siria", "SriLanka", "Tailandia", "Taiwan", "Tayikistan", "TimorOriental", "Turkmenistan",
            "Uzbekistan", "Vietnam", "Yemen",
            // Europa (45)
            "Albania", "Alemania", "Andorra", "Armenia", "Austria", "Azerbaiyan", "Belgica", "Bielorrusia",
            "Bosnia", "Bulgaria", "Chipre", "Croacia", "Dinamarca", "Eslovaquia", "Eslovenia", "España",
            "Estonia", "Finlandia", "Francia", "Gales", "Georgia", "Grecia", "Holanda", "Hungria",
            "Inglaterra", "Irlanda", "Islandia", "Italia", "Kosovo", "Letonia", "Liechtenstein",
            "Lituania", "Luxemburgo", "Macedonia", "Malta", "Moldavia", "Monaco", "Montenegro",
            "Noruega", "Polonia", "Portugal", "RepublicaCheca", "Rumania", "Rusia", "SanMarino",
            "Serbia", "Suecia", "Suiza", "Turquia", "Ucrania",
            // America del Norte y Central (20)
            "Antigua", "Bahamas", "Barbados", "Belice", "Canada", "CostaRica", "Cuba", "Dominica",
            "ElSalvador", "EEUU", "Granada", "Guatemala", "Haiti", "Honduras", "Jamaica", "Mexico",
            "Nicaragua", "Panama", "RepublicaDominicana", "Trinidad",
            // America del Sur (15)
            "Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Ecuador", "Guyana", "Paraguay",
            "Peru", "Surinam", "Uruguay", "Venezuela", "GuayanaFrancesa", "Malvinas", "IslasGalapagos",
            // Oceania (10)
            "Australia", "Fiyi", "IslasMarshall", "IslasSalomon", "Kiribati", "NuevaZelanda", "Palaos",
            "PapuaNuevaGuinea", "Samoa", "Tuvalu"
        };

        // catalogo de 16 directores tecnicos
        String[] tecnicos = {"Pep", "Klopp", "Ancelotti", "Zidane", "Simeone", "Pochettino", "Nagelsmann", "Tuchel",
            "LuisEnrique", "Scaloni", "Bielsa", "Gareca", "Berizzo", "Sampaoli", "Gallardo", "Almeyda"};

        // recorre y crea cada uno de los equipos segun el tamaño configurado
        for (int i = 0; i < tamanio; i++) {
            String pais = paises[i % paises.length]; // toma el nombre del pais reutilizando el catalogo con modulo
            if (i >= paises.length) {
                pais = pais + (i / paises.length + 1); // si se supera el catalogo agrega un numero al final, como brasil2, brasil3
            }
            String tecnico = tecnicos[rand.nextInt(tecnicos.length)]; // elige un tecnico al azar del catalogo
            Equipo eq = new Equipo(pais, tecnico, 23); // crea el equipo con veintitres jugadores
            Jugador[] plantilla = eq.getPlantilla(); // obtiene el arreglo de la plantilla recien creada

            // llena la plantilla completa con jugadores generados automaticamente
            for (int j = 0; j < plantilla.length; j++) {
                plantilla[j] = new Jugador("Jug" + (j + 1) + "-" + pais); // nombre combinado, jug1-brasil, jug2-brasil, y asi sucesivamente
            }
            equipos[i] = eq; // guarda el equipo configurado en el arreglo general
        }

        // catalogo de ciudades para las sedes
        String[] ciudades = {"Mexico", "Rio", "BuenosAires", "Londres", "Paris", "Berlin", "Madrid", "Roma"};
        // recorre y crea cada una de las sedes necesarias
        for (int i = 0; i < sedes.length; i++) {
            String ciudad = ciudades[i % ciudades.length]; // elige la ciudad del catalogo con modulo
            if (i >= ciudades.length) {
                ciudad = ciudad + (i / ciudades.length + 1); // evita nombres repetidos agregando un numero
            }            // crea la sede con capacidad aleatoria entre treinta mil y ochenta mil, como generar edificios con tamano aleatorio dentro de un rango
            sedes[i] = new Sede("Estadio " + (i + 1), ciudad, 30000 + rand.nextInt(50000));
        }

        // catalogo de nombres propios para los arbitros
        String[] nombresArb = {"Juan", "Carlos", "Miguel", "Javier", "Antonio", "Jose", "Manuel", "Francisco"};
        // recorre y crea cada uno de los arbitros necesarios
        for (int i = 0; i < arbitros.length; i++) {
            String nombre = nombresArb[i % nombresArb.length] + " " + (i + 1); // asigna nombre y numero de identificacion
            String nacionalidad = paises[(i * 3) % paises.length]; // asigna nacionalidad saltando de tres en tres en el catalogo
            arbitros[i] = new Arbitro(nombre, nacionalidad); // crea el arbitro
        }
    }

    /**
     * realiza el sorteo de grupos mezclando aleatoriamente los equipos y
     * repartiendolos en los grupos, usa el algoritmo fisher yates para la
     * mezcla, como cuando un juego de cartas baraja el mazo antes de repartir,
     * cada carta tiene la misma probabilidad de terminar en cualquier posicion,
     * si llamas este metodo sin haber llenado antes el arreglo de equipos,
     * estarias mezclando espacios null y el programa tronaria mas adelante al
     * intentar usar equipos vacios
     */
    public void sortearGrupos() {
        // algoritmo fisher yates, recorre el arreglo de atras hacia adelante intercambiando posiciones al azar
        for (int i = equipos.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1); // genera un indice aleatorio menor o igual a i
            Equipo temp = equipos[i]; // guarda temporalmente el equipo actual
            equipos[i] = equipos[j]; // intercambia el equipo de i con el de j
            equipos[j] = temp; // coloca el equipo guardado en la posicion j
        }

        int idx = 0; // indice rastreador para recorrer los equipos ya mezclados
        // recorre y reparte los equipos mezclados de cuatro en cuatro por cada grupo
        for (int g = 0; g < numGrupos; g++) {
            Equipo[] eqGrupo = new Equipo[4]; // arreglo temporal de cuatro equipos para el grupo actual
            for (int i = 0; i < 4; i++) {
                eqGrupo[i] = equipos[idx++]; // toma los siguientes cuatro equipos del arreglo general
            }
            grupos[g].setEquipos(eqGrupo); // asigna el grupo de cuatro equipos al objeto grupo correspondiente
        }
    }

    /**
     * genera todos los partidos de la fase de grupos, todos contra todos dentro
     * de cada grupo, es el generador de calendario automatico de cualquier liga
     * en un juego de gestion deportiva, si un grupo no tuviera exactamente
     * cuatro equipos, el codigo tronaria porque la tabla de combinaciones esta
     * escrita a mano asumiendo cuatro posiciones fijas
     */
    public void generarCalendario() {
        int totalPartidos = numGrupos * 6; // cada grupo de cuatro equipos genera exactamente seis partidos posibles
        calendario = new Partido[totalPartidos]; // crea el arreglo para el calendario completo del torneo
        int idx = 0; // contador de partidos en el calendario global

        // recorre cada grupo para programar sus enfrentamientos
        for (int g = 0; g < numGrupos; g++) {
            Equipo[] eq = grupos[g].getEquipos(); // obtiene los cuatro equipos del grupo
            // combinaciones fijas de indices, las unicas seis formas de emparejar cuatro elementos de dos en dos
            int[][] combos = {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}};
            Partido[] partidosGrupo = new Partido[6]; // arreglo local para los seis partidos del grupo

            // crea cada uno de los seis partidos del grupo
            for (int i = 0; i < 6; i++) {
                int[] par = combos[i]; // extrae la pareja de indices que se va a enfrentar
                Sede s = sedes[rand.nextInt(sedes.length)]; // elige un estadio al azar
                Arbitro a = arbitros[rand.nextInt(arbitros.length)]; // elige un arbitro al azar

                Partido p = new Partido(eq[par[0]], eq[par[1]], s, a); // crea el partido con esos datos
                calendario[idx] = p; // lo agrega al calendario general
                partidosGrupo[i] = p; // lo guarda tambien en la lista interna del grupo
                idx++; // avanza el contador global de partidos
            }
            grupos[g].setPartidos(partidosGrupo); // asigna los seis partidos al objeto grupo correspondiente
        }
    }

    /**
     * simula internamente el resultado y las estadisticas de un partido
     * individual de fase de grupos, es privado, solo la propia clase mundial lo
     * puede llamar directamente, es el corazon de la simulacion, como el
     * generador de daño aleatorio en un juego de rol, cuando atacas el juego
     * tira un numero entre un minimo y un maximo, aca en vez de daño son goles
     *
     * @param p partido a simular
     */
    private void simularPartido(Partido p) {
        int gL = rand.nextInt(6); // genera goles del local, un numero entre cero y cinco
        int gV = rand.nextInt(6); // genera goles del visitante, un numero entre cero y cinco
        p.setGolesLocal(gL); // guarda los goles del local en el partido
        p.setGolesVisitante(gV); // guarda los goles del visitante en el partido
        p.setJugado(true); // marca el partido como ya disputado

        Equipo local = p.getLocal(); // obtiene la referencia al equipo local
        Equipo visitante = p.getVisitante(); // obtiene la referencia al equipo visitante
        Jugador[] plantillaLocal = local.getPlantilla(); // obtiene la plantilla completa del local
        Jugador[] plantillaVisitante = visitante.getPlantilla(); // obtiene la plantilla completa del visitante

        Jugador[] goleadores = new Jugador[10]; // reserva espacio para registrar quienes anotaron
        int countGol = 0; // contador de anotaciones ya procesadas

        // reparte los goles locales entre jugadores al azar de la plantilla local
        for (int i = 0; i < gL; i++) {
            int idxJug = rand.nextInt(plantillaLocal.length); // elige un jugador al azar
            plantillaLocal[idxJug].incrementarGoles(); // le suma un gol individual a ese jugador
            goleadores[countGol++] = plantillaLocal[idxJug]; // lo agrega a la lista de anotadores del partido
        }
        // reparte los goles visitantes entre jugadores al azar de la plantilla visitante
        for (int i = 0; i < gV; i++) {
            int idxJug = rand.nextInt(plantillaVisitante.length); // elige un jugador al azar
            plantillaVisitante[idxJug].incrementarGoles(); // le suma un gol individual
            goleadores[countGol++] = plantillaVisitante[idxJug]; // lo registra como anotador
        }
        p.setGoleadores(goleadores); // guarda la lista completa de anotadores en el partido

        // simulacion de tarjetas amarillas, entre cero y tres por partido
        int numAmarillas = rand.nextInt(4); // genera cuantas amarillas hubo en el encuentro
        Jugador[] amarillas = new Jugador[10]; // arreglo para guardar a los sancionados
        int countAmar = 0; // contador de amarillas ya repartidas
        for (int i = 0; i < numAmarillas; i++) {
            // elige al azar si la tarjeta es para el local o el visitante, como lanzar una moneda
            Jugador jug = rand.nextBoolean() ? plantillaLocal[rand.nextInt(plantillaLocal.length)]
                    : plantillaVisitante[rand.nextInt(plantillaVisitante.length)];
            jug.incrementarAmarilla(); // registra la amarilla en el perfil del jugador
            amarillas[countAmar++] = jug; // agrega al jugador a la lista de amarillas del partido
        }
        p.setTarjetasAmarillas(amarillas); // guarda la lista completa de amarillas en el partido

        // simulacion de tarjetas rojas, este partido solo permite cero o una roja como maximo,
        // si quisieras permitir mas rojas por partido tendrias que subir el numero de rand.nextint(2)
        int numRojas = rand.nextInt(2); // genera cero o una roja
        Jugador[] rojas = new Jugador[10]; // arreglo para guardar a los expulsados
        int countRojas = 0; // contador de rojas ya repartidas
        for (int i = 0; i < numRojas; i++) {
            // elige al azar el equipo y el jugador expulsado, mismo sistema de moneda que las amarillas
            Jugador jug = rand.nextBoolean() ? plantillaLocal[rand.nextInt(plantillaLocal.length)]
                    : plantillaVisitante[rand.nextInt(plantillaVisitante.length)];
            jug.incrementarRoja(); // suma una roja al registro del jugador
            rojas[countRojas++] = jug; // agrega el expulsado al listado
        }
        p.setTarjetasRojas(rojas); // guarda la lista completa de rojas en el partido

        // simulacion de asistencia al estadio, nunca baja del cincuenta por ciento de aforo
        int capacidad = p.getSede().getCapacidad(); // obtiene la capacidad maxima de la sede
        p.setAsistencia(capacidad / 2 + rand.nextInt(capacidad / 2)); // asistencia entre el cincuenta y el cien por ciento

        // actualizacion de estadisticas acumuladas en cada seleccion
        local.setGolesFavor(local.getGolesFavor() + gL); // suma goles a favor al local
        local.setGolesContra(local.getGolesContra() + gV); // suma goles recibidos al local
        visitante.setGolesFavor(visitante.getGolesFavor() + gV); // suma goles a favor al visitante
        visitante.setGolesContra(visitante.getGolesContra() + gL); // suma goles recibidos al visitante

        // reparte los puntos segun el resultado, tres al ganador, uno a cada uno si empatan
        if (gL > gV) {
            local.setPuntos(local.getPuntos() + 3); // victoria del local, suma tres puntos
        } else if (gL < gV) {
            visitante.setPuntos(visitante.getPuntos() + 3); // victoria del visitante, suma tres puntos
        } else {
            local.setPuntos(local.getPuntos() + 1); // empate, suma un punto al local
            visitante.setPuntos(visitante.getPuntos() + 1); // empate, suma un punto al visitante
        }

        local.actualizarDiferencia(); // recalcula la diferencia de goles del local
        visitante.actualizarDiferencia(); // recalcula la diferencia de goles del visitante
    }

    /**
     * simula paso a paso unicamente el proximo partido no jugado del
     * calendario, es como el boton de siguiente turno en un juego de estrategia
     * por turnos, cada llamada resuelve solo el siguiente evento pendiente
     *
     * @return el partido recien simulado, o null si ya se jugaron todos
     */
    public Partido simularSiguientePartido() {
        if (calendario == null) {
            return null; // si el calendario no esta generado, no hay nada que simular
        }        // recorre el calendario buscando el primer partido que falte por jugar
        while (partidoActualIndex < calendario.length) {
            Partido p = calendario[partidoActualIndex]; // toma el partido indicado por el puntero
            if (!p.isJugado()) { // revisa si el partido esta pendiente
                simularPartido(p); // simula ese partido
                partidoActualIndex++; // avanza el puntero al siguiente partido
                return p; // devuelve el partido recien simulado
            }
            partidoActualIndex++; // si ya estaba jugado, avanza igual el puntero
        }
        return null; // ya no quedan partidos pendientes
    }

    /**
     * simula de forma directa e inmediata todos los partidos pendientes de la
     * fase de grupos, es como el boton de simular resto de temporada en un
     * juego de gestion deportiva, resuelve todo de golpe en vez de partido por
     * partido
     */
    public void simularFaseCompleta() {
        if (calendario == null) {
            return; // si no hay calendario, no hay nada que hacer
        }        // recorre y simula cada partido pendiente del calendario
        for (Partido p : calendario) {
            if (!p.isJugado()) {
                simularPartido(p);
            }
        }
        partidoActualIndex = calendario.length; // mueve el puntero hasta el final
    }

    /**
     * comprueba si todos los partidos de la fase de grupos ya fueron jugados,
     * es la pregunta interna que hace un juego antes de dejarte avanzar al
     * siguiente nivel
     *
     * @return true si la fase termino, false si hay partidos pendientes
     */
    public boolean faseGruposCompleta() {
        if (calendario == null) {
            return false; // si no existe calendario, la fase no puede estar completa
        }
        for (Partido p : calendario) {
            if (!p.isJugado()) {
                return false; // encontro un partido sin jugar
            }
        }
        return true; // todos los partidos ya fueron jugados
    }

    /**
     * ordena la tabla de posiciones interna de un grupo segun las reglas del
     * torneo, primero puntos, despues diferencia de goles, despues goles a
     * favor, es identico al sistema de desempate de cualquier liga real o juego
     * de gestion deportiva
     *
     * @param g grupo a ordenar
     * @return arreglo de cuatro equipos ordenados de mayor a menor rendimiento
     */
    private Equipo[] ordenarGrupo(Grupo g) {
        Equipo[] copia = new Equipo[4]; // crea una copia para no alterar el orden original del grupo
        System.arraycopy(g.getEquipos(), 0, copia, 0, 4); // copia los cuatro equipos del grupo en la replica

        // ordenamiento personalizado usando una expresion lambda
        java.util.Arrays.sort(copia, (a, b) -> {
            if (b.getPuntos() != a.getPuntos()) {
                return b.getPuntos() - a.getPuntos(); // criterio uno, mayor cantidad de puntos primero
            }
            if (b.getDiferenciaGoles() != a.getDiferenciaGoles()) {
                return b.getDiferenciaGoles() - a.getDiferenciaGoles(); // criterio dos, mejor diferencia de goles
            }
            return b.getGolesFavor() - a.getGolesFavor(); // criterio tres, mayor cantidad de goles anotados
        });
        return copia; // devuelve la lista ya ordenada
    }

    /**
     * determina y extrae los equipos que avanzan a la fase de eliminacion
     * directa, si el tamanio del torneo no es 24 ni 32, este calculo asume que
     * deben clasificar 32 equipos aunque no existan tantos, eso generaria un
     * error, es una regla escrita a mano que solo funciona bien con esos dos
     * tamaños especificos de torneo
     *
     * @return arreglo de equipos clasificados, o null si la fase de grupos no
     * ha terminado
     */
    public Equipo[] clasificarEliminatorias() {
        if (!faseGruposCompleta()) {
            return null; // exige haber completado todos los partidos antes de clasificar
        }
        int clasificados = (tamanio == 24 || tamanio == 32) ? 16 : 32; // decide si avanzan 16 o 32 selecciones
        Equipo[] avanzan = new Equipo[clasificados]; // arreglo para guardar a los clasificados
        int idx = 0; // indice de insercion

        // obtiene automaticamente el primer y segundo lugar de cada grupo
        for (Grupo g : grupos) {
            Equipo[] ord = ordenarGrupo(g); // obtiene la tabla ya ordenada del grupo
            avanzan[idx++] = ord[0]; // clasifica al primer lugar
            avanzan[idx++] = ord[1]; // clasifica al segundo lugar
        }

        // si todavia faltan cupos por llenar, se recurre a los mejores terceros lugares
        int necesarios = clasificados - idx; // calcula cuantos cupos faltan
        if (necesarios > 0) {
            Equipo[] terceros = new Equipo[numGrupos]; // arreglo para juntar a los terceros de cada grupo
            for (int i = 0; i < numGrupos; i++) {
                Equipo[] ord = ordenarGrupo(grupos[i]); // ordena cada grupo otra vez
                terceros[i] = ord[2]; // captura al tercer lugar
            }
            // ordena el ranking de terceros lugares entre si por puntos y diferencia de goles
            java.util.Arrays.sort(terceros, (a, b) -> {
                if (b.getPuntos() != a.getPuntos()) {
                    return b.getPuntos() - a.getPuntos(); // criterio puntos
                }
                return b.getDiferenciaGoles() - a.getDiferenciaGoles(); // criterio diferencia de gol
            });
            // agrega a los mejores terceros hasta completar el cupo necesario
            for (int i = 0; i < necesarios; i++) {
                avanzan[idx++] = terceros[i];
            }
        }
        return avanzan; // retorna los equipos clasificados a la fase final
    }

    /**
     * crea el cuadro inicial de octavos de final emparejando a los
     * clasificados, usa emparejamiento por siembra, el primero contra el
     * ultimo, el segundo contra el penultimo, exactamente el mismo sistema que
     * usan los brackets de torneos de videojuegos competitivos, para que en
     * teoria los mejores rankeados no se enfrenten entre si tan pronto
     *
     * @param clasificados arreglo con los equipos que avanzaron de la fase de
     * grupos
     */
    public void generarLlaves(Equipo[] clasificados) {
        int n = clasificados.length; // total de clasificados
        llavesOctavos = new Partido[n / 2]; // la cantidad de partidos es la mitad de los clasificados

        // empareja al primer clasificado contra el ultimo, el segundo contra el penultimo, y asi sucesivamente
        for (int i = 0; i < n / 2; i++) {
            Sede s = sedes[rand.nextInt(sedes.length)]; // asigna sede aleatoria
            Arbitro a = arbitros[rand.nextInt(arbitros.length)]; // asigna arbitro aleatorio
            llavesOctavos[i] = new Partido(clasificados[i], clasificados[n - 1 - i], s, a); // crea el cruce
        }

        // reserva el espacio para las rondas siguientes, todavia vacias
        llavesCuartos = new Partido[n / 4]; // espacio para cuartos
        llavesSemis = new Partido[n / 8]; // espacio para semis
        llavesFinal = new Partido[1]; // espacio para la final
    }

    /**
     * simula un partido de eliminacion directa, donde no puede haber empates,
     * si el resultado queda igualado, fuerza un desempate simplificado sumando
     * un gol extra a uno de los dos equipos elegido al azar, y marca el partido
     * como definido en penales, como en algunos juegos deportivos donde en vez
     * de mostrar la secuencia completa de penales, el juego tira una moneda
     * especial y te dice quien gano sin mostrarte el proceso
     *
     * @param p partido a simular
     */
    private void simularEliminatoria(Partido p) {
        int gL = rand.nextInt(4); // genera goles del local, entre cero y tres
        int gV = rand.nextInt(4); // genera goles del visitante, entre cero y tres

        // si el partido queda empatado, fuerza el desempate
        if (gL == gV) {
            if (rand.nextBoolean()) {
                gL++;
            } else {
                gV++; // suma un gol decisivo aleatoriamente a uno de los dos
            }
            p.setPenales(true); // marca la bandera de desempate en penales
        }
        p.setGolesLocal(gL); // guarda los goles finales del local
        p.setGolesVisitante(gV); // guarda los goles finales del visitante
        p.setJugado(true); // marca el partido como concluido
        p.setAsistencia(p.getSede().getCapacidad() / 2 + rand.nextInt(p.getSede().getCapacidad() / 2)); // genera la asistencia
    }

    /**
     * simula una ronda eliminatoria completa y genera los emparejamientos para
     * la siguiente ronda, toma los partidos de dos en dos, determina ganadores,
     * y los cruza entre si, es la logica de cualquier bracket de eliminacion
     * simple, ganas y avanzas, pierdes y quedas fuera
     *
     * @param rondaActual arreglo de partidos de la ronda que se va a disputar
     * @return arreglo con los nuevos partidos emparejados para la siguiente
     * fase
     */
    private Partido[] simularRonda(Partido[] rondaActual) {
        if (rondaActual == null) {
            return null; // valida que la ronda exista antes de continuar
        }
        Partido[] siguiente = new Partido[rondaActual.length / 2]; // la siguiente ronda tiene la mitad de partidos
        int idx = 0; // rastreador de la nueva lista

        // toma los partidos de dos en dos para enfrentar a los ganadores entre si
        for (int i = 0; i < rondaActual.length; i += 2) {
            if (!rondaActual[i].isJugado()) {
                simularEliminatoria(rondaActual[i]); // simula el primer partido si no se ha jugado
            }
            if (!rondaActual[i + 1].isJugado()) {
                simularEliminatoria(rondaActual[i + 1]); // simula el segundo partido si no se ha jugado
            }
            // determina el ganador del primer choque comparando los goles
            Equipo g1 = (rondaActual[i].getGolesLocal() > rondaActual[i].getGolesVisitante()) ? rondaActual[i].getLocal() : rondaActual[i].getVisitante();
            // determina el ganador del segundo choque comparando los goles
            Equipo g2 = (rondaActual[i + 1].getGolesLocal() > rondaActual[i + 1].getGolesVisitante()) ? rondaActual[i + 1].getLocal() : rondaActual[i + 1].getVisitante();

            Sede s = sedes[rand.nextInt(sedes.length)]; // asigna estadio aleatorio
            Arbitro a = arbitros[rand.nextInt(arbitros.length)]; // asigna arbitro aleatorio
            siguiente[idx++] = new Partido(g1, g2, s, a); // crea el partido cruzando a los dos ganadores
        }
        return siguiente; // retorna las llaves de la siguiente fase
    }

    /**
     * ejecuta en secuencia toda la fase eliminatoria desde octavos hasta la
     * final, definiendo al campeon, es como el boton de simular torneo completo
     * de un juego de gestion, resuelve todo el camino de golpe sin pausas
     * intermedias
     */
    public void simularEliminatorias() {
        if (llavesOctavos == null) {
            return; // si no hay octavos generados, no se puede continuar
        }
        llavesCuartos = simularRonda(llavesOctavos); // simula octavos y genera cuartos
        llavesSemis = simularRonda(llavesCuartos); // simula cuartos y genera semis
        llavesFinal = simularRonda(llavesSemis); // simula semis y genera la final

        // simula el partido final
        if (llavesFinal != null && llavesFinal.length > 0) {
            Partido finalP = llavesFinal[0]; // captura el partido final
            if (!finalP.isJugado()) {
                simularEliminatoria(finalP); // simula la gran final si estaba pendiente
            }
            // asigna al campeon comparando quien anoto mas goles en la final
            campeon = (finalP.getGolesLocal() > finalP.getGolesVisitante()) ? finalP.getLocal() : finalP.getVisitante();
            // el subcampeon es el equipo perdedor del encuentro
            subcampeon = (campeon == finalP.getLocal()) ? finalP.getVisitante() : finalP.getLocal();
        }
    }

    // --- getters de fases finales y selecciones premiadas ---
    /**
     * @return arreglo de partidos de octavos de final
     */
    public Partido[] getLlavesOctavos() {
        return llavesOctavos; // devuelve la referencia guardada
    }

    /**
     * @return arreglo de partidos de cuartos de final
     */
    public Partido[] getLlavesCuartos() {
        return llavesCuartos; // devuelve la referencia guardada
    }

    /**
     * @return arreglo de partidos de semifinales
     */
    public Partido[] getLlavesSemis() {
        return llavesSemis; // devuelve la referencia guardada
    }

    /**
     * @return arreglo con el partido de la final
     */
    public Partido[] getLlavesFinal() {
        return llavesFinal; // devuelve la referencia guardada
    }

    /**
     * @return la seleccion campeona del torneo
     */
    public Equipo getCampeon() {
        return campeon; // devuelve la referencia guardada
    }

    /**
     * @return la seleccion subcampeona del torneo
     */
    public Equipo getSubcampeon() {
        return subcampeon; // devuelve la referencia guardada
    }

    /**
     * genera la tabla de goleadores con los n maximos anotadores de todo el
     * torneo, es identico al leaderboard de kills en un juego de disparos,
     * junta a todos, ordena por la estadistica que importa, y te muestra solo a
     * los mejores segun el numero pedido
     *
     * @param top numero de jugadores a listar, por ejemplo los cinco mejores
     * @return arreglo ordenado con los mejores goleadores
     */
    public Jugador[] getTopGoleadores(int top) {
        int total = tamanio * 23; // calcula el total teorico de jugadores, asume veintitres por equipo
        Jugador[] todos = new Jugador[total]; // arreglo para juntar a todos los futbolistas
        int idx = 0; // indice de insercion

        // extrae a todos los jugadores de cada equipo y los junta en el arreglo global
        for (Equipo e : equipos) {
            if (e == null) {
                continue; // salta las posiciones vacias
            }
            for (Jugador j : e.getPlantilla()) {
                todos[idx++] = j; // agrega cada jugador a la lista
            }
        }

        // ordena a todos los futbolistas de mayor a menor segun sus goles anotados
        java.util.Arrays.sort(todos, 0, idx, (a, b) -> b.getGoles() - a.getGoles());

        int count = Math.min(top, idx); // evita pedir mas jugadores de los que realmente existen
        Jugador[] res = new Jugador[count]; // arreglo resultado con el tamaño solicitado
        System.arraycopy(todos, 0, res, 0, count); // copia los mejores goleadores al resultado final
        return res; // devuelve la tabla de goleadores
    }

    /**
     * obtiene la lista de jugadores sancionados, con una roja o dos o mas
     * amarillas, es como la lista de jugadores baneados o suspendidos en un
     * servidor de un juego competitivo, primero se muestran las sanciones mas
     * graves y despues las mas leves
     *
     * @return arreglo con los futbolistas infractores ordenados por gravedad
     */
    public Jugador[] getReporteDisciplinario() {
        int total = tamanio * 23; // total teorico de jugadores del torneo
        Jugador[] todos = new Jugador[total]; // arreglo para juntar todas las plantillas
        int idx = 0; // indice de insercion

        // recopila los jugadores de todas las selecciones
        for (Equipo e : equipos) {
            if (e == null) {
                continue; // salta posiciones vacias
            }
            for (Jugador j : e.getPlantilla()) {
                todos[idx++] = j; // agrega el jugador a la lista global
            }
        }

        // cuenta cuantos futbolistas cumplen la condicion de sancion
        int count = 0;
        for (int i = 0; i < idx; i++) {
            if (todos[i].getTarjetasRojas() > 0 || todos[i].getTarjetasAmarillas() >= 2) {
                count++;
            }
        }

        Jugador[] infractores = new Jugador[count]; // arreglo con el tamaño exacto de sancionados
        int pos = 0; // indice para el arreglo de infractores

        // filtra y guarda a los jugadores sancionados
        for (int i = 0; i < idx; i++) {
            if (todos[i].getTarjetasRojas() > 0 || todos[i].getTarjetasAmarillas() >= 2) {
                infractores[pos++] = todos[i];
            }
        }

        // ordena la lista, primero por rojas y despues por amarillas en caso de empate
        java.util.Arrays.sort(infractores, (a, b) -> {
            if (b.getTarjetasRojas() != a.getTarjetasRojas()) {
                return b.getTarjetasRojas() - a.getTarjetasRojas(); // mayor cantidad de rojas primero
            }
            return b.getTarjetasAmarillas() - a.getTarjetasAmarillas(); // mayor cantidad de amarillas primero
        });
        return infractores; // devuelve el reporte disciplinario completo
    }

    /**
     * suma la asistencia de todos los partidos jugados en todo el torneo,
     * revisa fase de grupos y todas las rondas eliminatorias, si alguna lista
     * todavia es null simplemente la salta gracias a las validaciones if, sin
     * tronar
     *
     * @return suma global de asistencia
     */
    public int getTotalAsistencia() {
        int total = 0; // acumulador de espectadores

        // suma la asistencia de la fase de grupos si el calendario existe
        if (calendario != null) {
            for (Partido p : calendario) {
                if (p.isJugado()) {
                    total += p.getAsistencia();
                }
            }
        }
        // suma la asistencia de octavos de final
        if (llavesOctavos != null) {
            for (Partido p : llavesOctavos) {
                if (p.isJugado()) {
                    total += p.getAsistencia();
                }
            }
        }
        // suma la asistencia de cuartos de final
        if (llavesCuartos != null) {
            for (Partido p : llavesCuartos) {
                if (p.isJugado()) {
                    total += p.getAsistencia();
                }
            }
        }
        // suma la asistencia de semifinales
        if (llavesSemis != null) {
            for (Partido p : llavesSemis) {
                if (p.isJugado()) {
                    total += p.getAsistencia();
                }
            }
        }
        // suma la asistencia de la gran final
        if (llavesFinal != null) {
            for (Partido p : llavesFinal) {
                if (p.isJugado()) {
                    total += p.getAsistencia();
                }
            }
        }

        return total; // retorna la asistencia total del mundial
    }

    /**
     * calcula la estimacion de ingresos monetarios usando una entrada promedio
     * de cincuenta dolares, usa tipo long porque el resultado puede superar el
     * limite maximo de un int en torneos grandes, como cuando un juego de
     * gestion economica cambia tu dinero a un tipo de dato mas grande para
     * evitar que el contador se desborde y de la vuelta a numeros negativos
     *
     * @return monto total recaudado
     */
    public long getTotalIngresos() {
        return getTotalAsistencia() * 50L; // multiplica la asistencia total por cincuenta, el sufijo l indica tipo long
    }
}
