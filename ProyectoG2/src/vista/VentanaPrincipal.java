/*
 * click nbfs://nbhost/systemfilesystem/templates/licenses/license-default.txt to change this license
 * click nbfs://nbhost/systemfilesystem/templates/classes/class.java to edit this template
 */
package vista;

// importacion del controlador que contiene toda la logica del negocio
import controlador.Mundial;
// importacion de todas las clases del modelo (equipo, jugador, partido, etc)
import modelo.*;
// importacion de componentes swing para la interfaz grafica
import javax.swing.*;
import javax.swing.UIManager;
// importacion del modelo de tablas para poder mostrar datos en tablas
import javax.swing.table.DefaultTableModel;
// importacion de clases para layouts y graficos 2d
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
/**
 * ventana principal del proyecto copa mundial java contiene pestañas para
 * configuracion, grupos, simulacion, eliminatorias y estadisticas es el punto
 * de entrada de la interfaz de usuario extiende jframe para ser una ventana con
 * bordes y controles de sistema
 *
 * @author grupo2
 */
public class VentanaPrincipal extends JFrame {
    private void cambiarLookAndFeel() {
    try {
        // Opciones disponibles:
        // 1. Nimbus (moderno)
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.put("nimbusGradient", new Color(0, 102, 204));
        UIManager.put("nimbusSelectionBackground", new Color(0, 102, 204));
        UIManager.put("nimbusBase", new Color(0, 102, 204));
        UIManager.put("nimbusBlueGrey", new Color(200, 215, 230));
        
        // ⭐ Configurar botones para que usen colores sólidos
       // UIManager.put("Button.background", new Color(0, 102, 204));
       // UIManager.put("Button.foreground", Color.WHITE);
       // UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 16));
        //UIManager.put("Button.focusWidth", 0);
        

        // PASO 3: Configurar colores de las pestañas (claves específicas de Nimbus)
        // Texto de pestañas no seleccionadas
        UIManager.put("TabbedPane:TabbedPaneTab.textForeground", Color.WHITE);
        // Texto de pestaña seleccionada
        UIManager.put("TabbedPane:TabbedPaneTab.selectedTextForeground", Color.WHITE);
        // Fuente de las pestañas
        UIManager.put("TabbedPane:TabbedPaneTab.font", new Font("Century Gothic", Font.BOLD, 13));

        // Fondo de pestañas no seleccionadas
        UIManager.put("TabbedPane:TabbedPaneTab.background", new Color(0, 80, 160));
        // Fondo de pestaña seleccionada
        UIManager.put("TabbedPane:TabbedPaneTab.selectedBackground", new Color(0, 102, 204));

        // Fondo del área de pestañas
        UIManager.put("TabbedPane.background", new Color(0, 102, 204));

        SwingUtilities.updateComponentTreeUI(this);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private JButton crearBotonPersonalizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        UIManager.put("nimbusGradient", new Color(0, 102, 204)); // Gradient sólido
        UIManager.put("nimbusSelectionBackground", new Color(0, 102, 204));
        
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Century Gothic", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true);
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
               
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
               
            }
        });
        
        return boton;
    }
    
    // instancia del controlador que maneja toda la logica del torneo
    // es el objeto que contiene los arreglos de equipos, grupos, partidos y metodos de simulacion
    private Mundial mundial;

    // panel con pestañas que organiza las diferentes secciones de la aplicacion
    private JTabbedPane tabbedPane;

    // panel donde se muestran las tablas de grupos con sus estadisticas
    // se actualiza dinamicamente tras cada simulacion
    private JPanel panelGrupos;

    // area de texto para mostrar los resultados de los partidos simulados
    // actua como un registro de eventos en tiempo real
    private DefaultTableModel modeloTablaSimulacion;
    private JTable tablaSimulacion;

    // area de texto para mostrar las estadisticas finales del torneo
    // incluye campeon, goleadores, disciplina y finanzas
    private JTextArea areaEstadisticas;

    // panel personalizado que dibuja el arbol de llaves (bracket) de eliminatorias
    // utiliza paintcomponent para graficar los enfrentamientos
    private PanelBracket panelBracket;

    private static final Color COLOR_PRIMARIO = new Color(0, 102, 204);
    private static final Color COLOR_SECUNDARIO = new Color(255, 215, 0);
    private static final Color COLOR_FONDO = new Color(240, 248, 255);
    private static final Color COLOR_TEXTO = new Color(255, 255, 255);
    private static final Color COLOR_EXITO = new Color(46, 139, 87);
    private static final Color COLOR_INFO = new Color(70, 130, 180);
    /**
     * constructor de la ventana principal inicializa el controlador, configura
     * la ventana, crea las pestañas y las deshabilita inicialmente hasta que se
     * completen los pasos previos es el metodo que se ejecuta al abrir la
     * aplicacion
     */
    public VentanaPrincipal() {
        // crea una nueva instancia del controlador mundial
        // esto prepara el motor del torneo pero aun sin datos
        mundial = new Mundial();
        cambiarLookAndFeel();
        // ---- configuracion de la ventana ----
        // establece el titulo que aparece en la barra superior de la ventana
        setTitle("Copa Mundial Java");
        // al cerrar la ventana, el programa finaliza completamente
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // define el tamaño de la ventana en pixeles: ancho 1200, alto 800
        // si se cambia a 800x600 los componentes se comprimen y pueden solaparse
        setSize(1200, 800);
        // centra la ventana en la pantalla del usuario
        // si se pasa otro componente como parametro, se alinea a ese componente
        setLocationRelativeTo(null);

        // ---- creacion del panel de pestañas ----
        // jtabbedpane permite agrupar contenido en pestañas seleccionables
        UIManager.put("TabbedPane.foreground", Color.WHITE);
        UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Century Gothic", Font.BOLD, 13));
        tabbedPane.setBackground(COLOR_PRIMARIO);
        
        // añade cada pestaña con su panel correspondiente
        // el primer parametro es el titulo de la pestaña
        // el segundo es el panel que se mostrara al seleccionar la pestaña
        tabbedPane.addTab("Configuración", crearPanelConfiguracion());
        tabbedPane.addTab("Grupos", crearPanelGrupos());
        tabbedPane.addTab("Simulación", crearPanelSimulacion());
        tabbedPane.addTab("Eliminatorias", crearPanelEliminatorias());
        tabbedPane.addTab("Estadísticas", crearPanelEstadisticas());

        // inicialmente deshabilita las pestañas que dependen de datos previos
        // esto evita que el usuario acceda a funciones sin haber configurado el torneo
        // el indice 0 es configuracion (siempre habilitada)
        // indice 1 es grupos (se habilita tras sortear)
        // indice 2 es simulacion (se habilita tras sortear)
        // indice 3 es eliminatorias (se habilita tras completar fase de grupos)
        // indice 4 es estadisticas (se habilita tras terminar eliminatorias)
        tabbedPane.setEnabledAt(2, false); // simulacion
        tabbedPane.setEnabledAt(3, false); // eliminatorias
        tabbedPane.setEnabledAt(4, false); // estadisticas

        // añade el panel de pestañas a la ventana principal
        // ocupa todo el espacio disponible dentro del jframe
        add(tabbedPane);

        // hace visible la ventana para que el usuario pueda interactuar
        // sin esta linea no se veria nada aunque ya este todo construido
        setVisible(true);
       
    }

    /**
     * crea el panel de configuracion contiene un combo para elegir el tamaño
     * del mundial (24, 32, 48, 64) y tres botones: configurar, generar datos
     * demo y sortear grupos este panel es el punto de partida para iniciar el
     * torneo
     *
     * @return jpanel configurado con todos los componentes
     */
    private JPanel crearPanelConfiguracion() {
        cambiarLookAndFeel();
        // panel principal con borderlayout que divide en regiones (norte, centro, sur, etc)
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // panel superior con flowlayout que coloca los elementos en una fila horizontal
           JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
           top.setBackground(COLOR_FONDO);
    


        // ---- componentes de la barra superior ----
        // combo desplegable con las opciones de cantidad de equipos
        // el usuario puede seleccionar 24, 32, 48 o 64
        JComboBox<Integer> combo = new JComboBox<>(new Integer[]{24, 32, 48, 64});
        combo.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        
        // boton que configura el tamaño del mundial
        JButton btnConfig = crearBotonPersonalizado("Configurar", COLOR_PRIMARIO);
        // boton que genera automaticamente equipos, jugadores, sedes y arbitros ficticios
        JButton btnDemo = crearBotonPersonalizado("Generar Datos Demo",COLOR_PRIMARIO);
        // boton que sortea los equipos en grupos de 4 y genera el calendario
        JButton btnSortear = crearBotonPersonalizado("Sortear Grupos", COLOR_PRIMARIO);

        // añade los componentes al panel superior en orden
        top.add(new JLabel("Tamaño:"));
        top.add(combo);
        top.add(btnConfig);
        top.add(btnDemo);
        top.add(btnSortear);
        // coloca el panel superior en la region norte del panel principal
        panel.add(top, BorderLayout.NORTH);

        // ---- panel central (placeholder para futuras listas) ----
        // actualmente son listas vacias que sirven como espacio reservado
        // para futuras funcionalidades de edicion manual de entidades
        JPanel info = new JPanel(new GridLayout(1, 3));
        info.add(new JScrollPane(new JList<>())); // lista de equipos
        info.add(new JScrollPane(new JList<>())); // lista de sedes
        info.add(new JScrollPane(new JList<>())); // lista de arbitros
        panel.add(info, BorderLayout.CENTER);
       

        panel.add(info, BorderLayout.CENTER);
        // ---- accion del boton "configurar" ----
        // se usa una expresion lambda para el event listener
        btnConfig.addActionListener(e -> {
            // obtiene el tamaño seleccionado del combo y lo convierte a int
            // si se selecciona 48, se pasara 48 al controlador
            int tamanio = (int) combo.getSelectedItem();
            // llama al metodo del controlador que dimensiona todos los arreglos
            mundial.configurarTamanio(tamanio);
            // muestra un mensaje emergente confirmando la operacion
            JOptionPane.showMessageDialog(this, "Mundial configurado.");
            // deshabilita las pestañas que dependen de tener datos
            // porque aun no se ha generado ni sorteado nada
            tabbedPane.setEnabledAt(2, false);
            tabbedPane.setEnabledAt(3, false);
            tabbedPane.setEnabledAt(4, false);
        });

        // ---- accion del boton "generar datos demo" ----
        btnDemo.addActionListener(e -> {
            // llama al controlador para llenar todos los arreglos con datos ficticios
            // esto crea equipos con 23 jugadores, sedes con capacidades aleatorias y arbitros
            mundial.generarDatosDemo();
            // mensaje de confirmacion
            JOptionPane.showMessageDialog(this, "Datos demostrativos generados.");
        });

        // ---- accion del boton "sortear grupos" ----
        btnSortear.addActionListener(e -> {
            // verifica que existan equipos en el arreglo
            // si no hay equipos (porque no se configuro o no se generaron datos)
            if (mundial.getEquipos() == null || mundial.getEquipos().length == 0) {
                // muestra un mensaje de error y sale del listener
                JOptionPane.showMessageDialog(this, "Primero configure y genere datos.");
                return;
            }
            // llama al controlador para mezclar los equipos y distribuirlos en grupos de 4
            mundial.sortearGrupos();
            // genera el calendario de partidos de fase de grupos (todos contra todos)
            mundial.generarCalendario();
            // actualiza la pestaña de grupos para mostrar las tablas con los equipos
            actualizarTablasGrupos();
            // habilita la pestaña de simulacion porque ya hay partidos para jugar
            tabbedPane.setEnabledAt(2, true);
            // mensaje de confirmacion
            JOptionPane.showMessageDialog(this, "Grupos sorteados.");
        });

        // devuelve el panel completamente construido
        return panel;
    }

    /**
     * crea el panel de grupos (inicialmente vacio) se llena dinamicamente con
     * el metodo actualizartablasgrupos() este panel muestra las tablas de
     * clasificacion de cada grupo
     *
     * @return jpanel para grupos
     */
    private JPanel crearPanelGrupos() {
         
        // gridlayout con 0 filas (se calculan automaticamente), 4 columnas y espacio de 10 pixeles
        // si se cambia el 4 a 3, se veran 3 grupos por fila en lugar de 4
        panelGrupos = new JPanel(new GridLayout(0, 4, 20, 20));
        panelGrupos.setBackground(COLOR_FONDO);
        panelGrupos.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        panelGrupos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panelGrupos;
    }

    /**
     * actualiza el panel de grupos con las tablas de cada grupo obtiene los
     * grupos del controlador y crea una tabla por cada uno este metodo se llama
     * despues de cada simulacion para refrescar los datos
     */
    private void actualizarTablasGrupos() {
        // elimina todos los componentes del panel para reconstruirlo desde cero
        panelGrupos.removeAll();

        // obtiene el arreglo de grupos del controlador
        Grupo[] grupos = mundial.getGrupos();
        // si no hay grupos (aun no se ha sorteado) muestra un mensaje
        if (grupos == null) {
            panelGrupos.add(new JLabel("Sin grupos"));
            return;
        }

        // recorre cada grupo para crear su tabla
        for (Grupo g : grupos) {
            // panel individual para cada grupo con borderlayout
             JPanel p = new JPanel(new BorderLayout(5, 5));
             
            // pone un borde con el nombre del grupo como titulo (ej "grupo a")
            p.setBorder(BorderFactory.createTitledBorder(g.getNombre()));

            // modelo de tabla con 5 columnas: equipo, puntos, goles a favor, goles en contra, diferencia
            DefaultTableModel model = new DefaultTableModel(
                    new Object[]{"Equipo", "Pts", "GF", "GC", "DG"}, 0
            );

            // llena la tabla con los equipos del grupo
            for (Equipo e : g.getEquipos()) {
                // solo añade el equipo si no es null
                if (e != null) {
                    model.addRow(new Object[]{
                        e.getNombrePais(), // nombre del pais
                        e.getPuntos(), // puntos acumulados
                        e.getGolesFavor(), // goles a favor
                        e.getGolesContra(), // goles en contra
                        e.getDiferenciaGoles() // diferencia = gf - gc
                    });
                }
            }

            // crea una tabla con el modelo y la envuelve en un scrollpane para desplazamiento
            p.add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
            // añade el panel del grupo al panel principal de grupos
            panelGrupos.add(p);
        }

        // refresca el layout para que los nuevos componentes se organicen correctamente
        panelGrupos.revalidate();
        // vuelve a pintar el panel para mostrar los cambios visuales
        panelGrupos.repaint();
    }

    /**
     * crea el panel de simulacion contiene botones para simular partido a
     * partido o fase completa y un area de texto para mostrar los resultados en
     * tiempo real
     *
     * @return jpanel de simulacion
     */
    private JPanel crearPanelSimulacion() {
        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBackground(new Color(40,44,52));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,10));
        top.setOpaque(false);

        // boton para simular un solo partido (el siguiente no jugado)
        JButton btnPartido = crearBotonPersonalizado("Simular Partido a Partido", new Color(25,30,40));
        // boton para simular todos los partidos pendientes de la fase de grupos
        JButton btnFase = crearBotonPersonalizado("Simular Fase Completa",new Color(25,30,40));
        top.add(btnPartido);
        top.add(btnFase);
        panel.add(top, BorderLayout.NORTH);
        
        String[] columnas ={"Local","Resultado","Visitante"};
        modeloTablaSimulacion=new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }    
        };
        tablaSimulacion = new JTable(modeloTablaSimulacion);
        tablaSimulacion.setRowHeight(36);
        tablaSimulacion.setShowGrid(false);
        tablaSimulacion.setIntercellSpacing(new Dimension(0,0));
        tablaSimulacion.setFont(new Font("Century Gothic",Font.PLAIN,13));
        tablaSimulacion.setBackground(new Color(48,54,64));
        tablaSimulacion.setForeground(Color.WHITE);
        
        tablaSimulacion.setTableHeader(null);//Oculta los encabezados
        
        DefaultTableCellRenderer renderSimulacion = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column){
                JLabel label =(JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if(row % 2 == 0){
                    label.setBackground(new Color(48,54,64));
                }else{
                    label.setBackground(new Color(40,45,54));
                }
                label.setForeground(Color.WHITE);
                
                if(column == 0){
                    label.setHorizontalAlignment(SwingConstants.RIGHT);
                    label.setFont(new Font("Century Gothic",Font.BOLD,13));
                }else if(column == 1){
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFont(new Font("Century Gothic",Font.BOLD,14));
                    label.setForeground(new Color(220,225,230));
                }else if(column ==2){
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    label.setFont(new Font("Century Gothic",Font.BOLD,13));
                }
                return label;
            }
        };
        
        for (int i = 0; i < tablaSimulacion.getColumnCount(); i++) {
            tablaSimulacion.getColumnModel().getColumn(i).setCellRenderer(renderSimulacion);
        }
        
        tablaSimulacion.getColumnModel().getColumn(0).setPreferredWidth(260);//Local
        tablaSimulacion.getColumnModel().getColumn(1).setPreferredWidth(100);//resultado
        tablaSimulacion.getColumnModel().getColumn(2).setPreferredWidth(260);//visitante
        
        JScrollPane scrollPane = new JScrollPane(tablaSimulacion);
        scrollPane.getViewport().setBackground(new Color(40,44,52));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60,66,78),1));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        

        // ---- accion del boton "simular partido a partido" ----
        btnPartido.addActionListener(e -> {
            // solicita al controlador el siguiente partido no jugado y lo simula
            Partido p = mundial.simularSiguientePartido();
            if (p != null) {
                agregarPartidoATabla(p);
                tablaSimulacion.scrollRectToVisible(tablaSimulacion.getCellRect(tablaSimulacion.getRowCount()-1,0,true));
                // actualiza las tablas de grupos con los nuevos datos
                actualizarTablasGrupos();
                // verifica si la fase de grupos esta completa
                if (mundial.faseGruposCompleta()) {
                    JOptionPane.showMessageDialog(this,"¡Fase de grupos completada!\n");
                    // habilita la pestaña de eliminatorias
                    tabbedPane.setEnabledAt(3, true);
                }
            } else {
                // si no hay mas partidos muestra el mensaje
                JOptionPane.showMessageDialog(this,"No hay más partidos.\n");
            }
        });

        // ---- accion del boton "simular fase completa" ----
        btnFase.addActionListener(e -> {
            // simula todos los partidos pendientes de la fase de grupos
            mundial.simularFaseCompleta();
            cargarTodosLosPartidos();
            JOptionPane.showMessageDialog(this,"Fase de grupos completada.\n");
            // actualiza las tablas de grupos
            actualizarTablasGrupos();
            // habilita la pestaña de eliminatorias
            tabbedPane.setEnabledAt(3, true);
        });

        return panel;
    }
    
    public void agregarPartidoATabla(Partido p){
        String local = p.getLocal().getNombrePais();
        String marcador = p.getGolesLocal()+" - "+p.getGolesVisitante();
        String visitante = p.getVisitante().getNombrePais();
        
        modeloTablaSimulacion.addRow(new Object[]{local,marcador,visitante});
    }
    public void cargarTodosLosPartidos(){
        modeloTablaSimulacion.setRowCount(0);
        if(mundial != null && mundial.getGrupos()!= null){
            for (Grupo g : mundial.getGrupos()) {
                if(g != null && g.getPartidos()!=null){
                    for (Partido p : g.getPartidos()) {
                        if (p != null && p.isJugado()){
                            agregarPartidoATabla(p);
                        }
                    }
                }
            }
        }
    }
    /**
     * crea el panel de eliminatorias contiene un boton para iniciar las
     * eliminatorias y el panelbracket para dibujar el arbol de llaves
     *
     * @return jpanel de eliminatorias
     */
    private JPanel crearPanelEliminatorias() {
        JPanel panel = new JPanel(new BorderLayout());

        // boton que inicia el proceso de clasificacion y simulacion de eliminatorias
        JButton btnIniciar = new JButton("Iniciar Eliminatorias");
        btnIniciar.setFont(new Font("Century Gothic",Font.BOLD,14));
        btnIniciar.setBackground(new Color(40,45,54));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        panel.add(btnIniciar, BorderLayout.NORTH);

        // panel personalizado que dibujara el bracket
        panelBracket = new PanelBracket();
        panel.add(panelBracket, BorderLayout.CENTER);

        // ---- accion del boton "iniciar eliminatorias" ----
        btnIniciar.addActionListener(e -> {
            // clasifica los equipos que avanzan (primeros, segundos y mejores terceros)
            Equipo[] clas = mundial.clasificarEliminatorias();
            // si no hay clasificados (porque la fase de grupos no esta completa)
            if (clas == null) {
                JOptionPane.showMessageDialog(this, "Fase de grupos incompleta.");
                return;
            }
            // genera las llaves con los equipos clasificados
            mundial.generarLlaves(clas);
            // simula todas las rondas de eliminatorias (octavos, cuartos, semis, final)
            mundial.simularEliminatorias();

            // pasa los arreglos de partidos de cada ronda al panelbracket para dibujar
            panelBracket.setPartidos(
                    mundial.getLlavesOctavos(),
                    mundial.getLlavesCuartos(),
                    mundial.getLlavesSemis(),
                    mundial.getLlavesFinal()
            );
            // solicita al panel que se redibuje con los nuevos datos
            panelBracket.repaint();

            // habilita la pestaña de estadisticas porque ya hay un campeon
            tabbedPane.setEnabledAt(4, true);
            // muestra un mensaje emergente con el nombre del campeon
            JOptionPane.showMessageDialog(this, "¡Campeón: " + mundial.getCampeon().getNombrePais() + "!");
        });

        return panel;
    }

    /**
     * crea el panel de estadisticas contiene un area de texto y un boton para
     * actualizar los datos muestra el resumen final del torneo
     *
     * @return jpanel de estadisticas
     */
    private JPanel crearPanelEstadisticas() {
        
        //return panel;
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Panel superior con botones
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton btnAct = new JButton("Actualizar");
        btnAct.setBackground(new Color(70, 130, 180));
        btnAct.setForeground(Color.WHITE);
        btnAct.setFont(new Font("Century Gothic", Font.BOLD, 12));
        btnAct.setFocusPainted(false);

        top.add(btnAct);
        panel.add(top, BorderLayout.NORTH);

        // Área de texto mejorada
        areaEstadisticas = new JTextArea(20, 60);
        areaEstadisticas.setEditable(false);
        areaEstadisticas.setFont(new Font("Century Gothic", Font.PLAIN, 13));
        areaEstadisticas.setBackground(new Color(255, 255, 240));
        panel.add(new JScrollPane(areaEstadisticas), BorderLayout.CENTER);

        btnAct.addActionListener(e -> actualizarEstadisticas());
       

        return panel;
    }

    /**
     * actualiza el area de estadisticas con los datos finales del torneo
     * muestra: - campeon y subcampeon - top 5 goleadores (bota de oro) -
     * reporte disciplinario (jugadores con rojas o muchas amarillas) - resumen
     * financiero (asistencia total e ingresos)
     */
    private void actualizarEstadisticas() {
        // stringbuilder para construir el texto de forma eficiente
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS FINALES ===\n\n");

        // si hay campeon (el torneo ha finalizado) lo muestra
        if (mundial.getCampeon() != null) {
            sb.append("CAMPEÓN: ").append(mundial.getCampeon().getNombrePais()).append("\n");
            sb.append("SUBCAMPEÓN: ").append(mundial.getSubcampeon().getNombrePais()).append("\n\n");
        }

        // seccion de goleadores
        sb.append("--- BOTA DE ORO (Top 5) ---\n");
        // obtiene los 5 mejores goleadores del controlador
        Jugador[] top = mundial.getTopGoleadores(5);
        for (int i = 0; i < top.length; i++) {
            // muestra posicion, nombre, pais y goles
            sb.append((i + 1) + ". " + top[i].getNombre() + " - " + obtenerPais(top[i]) + " (" + top[i].getGoles() + " goles)\n");
        }

        // seccion disciplinaria
        sb.append("\n--- REPORTE DISCIPLINARIO ---\n");
        // obtiene los jugadores infractores del controlador
        for (Jugador j : mundial.getReporteDisciplinario()) {
            // muestra nombre, pais, rojas y amarillas
            sb.append(j.getNombre() + " (" + obtenerPais(j) + ") - Rojas: " + j.getTarjetasRojas() + ", Amarillas: " + j.getTarjetasAmarillas() + "\n");
        }

        // seccion financiera
        sb.append("\n--- FINANZAS ---\n");
        sb.append("Asistencia total: " + mundial.getTotalAsistencia() + "\n");
        sb.append("Ingresos totales: $" + mundial.getTotalIngresos() + "\n");

        // coloca todo el texto en el area de estadisticas
        areaEstadisticas.setText(sb.toString());
    }

    /**
     * metodo auxiliar para obtener el pais de un jugador recorre todos los
     * equipos y sus plantillas buscando al jugador si lo encuentra devuelve el
     * nombre del pais si no, devuelve "desconocido"
     *
     * @param j jugador a buscar
     * @return nombre del pais o "desconocido"
     */
    private String obtenerPais(Jugador j) {
        // recorre todos los equipos
        for (Equipo e : mundial.getEquipos()) {
            if (e == null) {
                continue;
            }
            // recorre la plantilla de cada equipo
            for (Jugador jug : e.getPlantilla()) {
                // compara por referencia (==) porque es el mismo objeto
                if (jug == j) {
                    return e.getNombrePais();
                }
            }
        }
        // si no se encuentra, retorna desconocido
        return "Desconocido";
    }

   
     
 
    
 
 
    /**
     * clase interna que extiende jpanel para dibujar el arbol de llaves
     * (bracket) recibe los arreglos de partidos de cada ronda y los dibuja en
     * el panel utiliza graphics2d para dibujar rectangulos y texto
     */
    class PanelBracket extends JPanel {

        // arreglos de partidos de cada ronda
        private Partido[] octavos, cuartos, semis, finalP;
        
        public PanelBracket(){
            setBackground(new Color(90,100,115));//fondo gris
        }
        
        /**
         * asigna los partidos de cada ronda al panel este metodo se llama desde
         * el boton de iniciar eliminatorias
         *
         * @param o partidos de octavos
         * @param c partidos de cuartos
         * @param s partidos de semifinales
         * @param f partido de la final
         */
        public void setPartidos(Partido[] o, Partido[] c, Partido[] s, Partido[] f) {
            this.octavos = o;
            this.cuartos = c;
            this.semis = s;
            this.finalP = f;
            repaint();
        }

        /**
         * dibuja el bracket en el panel usa coordenadas y tamaños fijos para
         * colocar los rectangulos con los nombres de los equipos y los
         * resultados si se cambian las variables x, ancho, sep, etc, la
         * distribucion cambia
         *
         * @param g objeto graphics para dibujar
         */
        @Override
        protected void paintComponent(Graphics g) {
            // llama al metodo padre para limpiar el fondo y preparar el area
            super.paintComponent(g);

            // si no hay octavos (aun no se han generado) muestra un mensaje
            if (octavos == null) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Century Gothic", Font.BOLD,14));
                g.drawString("Sin llaves", 50, 50);
                return;
            }

            // convierte a graphics2d para mejor control de dibujo
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            // coordenadas y tamaños:
            // x: posicion horizontal inicial de la columna
            // y: posicion vertical inicial
            // ancho: ancho de cada rectangulo
            // alto: altura de cada rectangulo
            // sep: separacion entre rectangulos de una misma columna
            // si se cambia ancho a 80, los textos se cortan
            // si se cambia sep a 20, los rectangulos se superponen
            int ancho = 180, alto = 50, sep = 66;
            int gapColumna = 95; // espacio entre columnas
            int totalColumnas = 4;
            int anchoTotal=(totalColumnas*ancho)+((totalColumnas-1)*gapColumna);
            int altoTotal = (octavos.length*sep)-(sep-alto);
            
            int x = Math.max(30,(getWidth()-anchoTotal)/2);
            int yCalculado = (getHeight()-altoTotal)/2;
            int y= Math.max(85, Math.min(yCalculado,110));
            
            Font fuenteTitulos = new Font("Century Gothic", Font.BOLD,16);
            
            // ---- columna de octavos ----
            // dibuja el titulo
            
            g2.setFont(fuenteTitulos);
            g2.setColor(Color.WHITE);
            
            g2.drawString("Octavos", x, y - 15);
            
            int[]yOctavos = new int [octavos.length];
            
            // recorre cada partido de octavos
            for (int i = 0; i < octavos.length; i++) {
                yOctavos[i]=y + i * sep;
                DibujarTarjeta(g2, octavos[i], x, yOctavos[i], ancho, alto);
                
                Partido p = octavos[i];
                // construye el texto con los nombres de los equipos
                String txt = p.getLocal().getNombrePais() + " vs " + p.getVisitante().getNombrePais();
                // si el partido esta jugado, añade el resultado
                if (p.isJugado()) {
                    txt += " " + p.getGolesLocal() + "-" + p.getGolesVisitante();
                }
                
            }

            // ---- columna de cuartos ----
            // se desplaza a la derecha (ancho + 60)
            int x2 = x + ancho + gapColumna;
            g2.setFont(fuenteTitulos);
            g2.setColor(Color.WHITE);
            g2.drawString("Cuartos", x2, y - 15);
            int [] yCuartos = new int[cuartos.length];
            for (int i = 0; i < cuartos.length; i++) {
                yCuartos[i]=(yOctavos[i * 2] + yOctavos[i * 2 + 1])/2;
                DibujarTarjeta(g2, cuartos[i], x2, yCuartos[i], ancho, alto);
                
                Partido p = cuartos[i];
                String txt = p.getLocal().getNombrePais() + " vs " + p.getVisitante().getNombrePais();
                if (p.isJugado()) {
                    txt += " " + p.getGolesLocal() + "-" + p.getGolesVisitante();
                }
                
            }
            LineasFlujo(g2, x + ancho, yOctavos, x2, alto);

            // ---- columna de semifinales ----
            int x3 = x2 + ancho + gapColumna;
            g2.setFont(fuenteTitulos);
            g2.setColor(Color.WHITE);
            g2.drawString("Semis", x3, y - 15);
            int[] ySemis = new int[semis.length];
            for (int i = 0; i < semis.length; i++) {
                ySemis[i]=(yCuartos[i * 2]+ yCuartos[i * 2 + 1])/2;
                DibujarTarjeta(g2, semis[i], x3, ySemis[i], ancho, alto);
                
                Partido p = semis[i];
                String txt = p.getLocal().getNombrePais() + " vs " + p.getVisitante().getNombrePais();
                if (p.isJugado()) {
                    txt += " " + p.getGolesLocal() + "-" + p.getGolesVisitante();
                }
                
            }
            LineasFlujo(g2, x2 + ancho, yCuartos, x3, alto);

            // ---- columna de la final ----
            int x4 = x3 + ancho + gapColumna;
            g2.setFont(fuenteTitulos);
            g2.setColor(Color.WHITE);
            g2.drawString("Final", x4, y - 15);
            
            // solo hay un partido de final, si existe
            if (finalP != null && finalP.length > 0) {
                int yFinal =(ySemis[0] + ySemis[1])/2;
                DibujarTarjeta(g2, finalP[0], x4, yFinal, ancho, alto);
                
                
                Partido p = finalP[0];
                String txt = p.getLocal().getNombrePais() + " vs " + p.getVisitante().getNombrePais();
                if (p.isJugado()) {
                    txt += " " + p.getGolesLocal() + "-" + p.getGolesVisitante();
                }
                LineasFlujo(g2, x3 + ancho, ySemis, x4, alto);
                
            }
        }
    }

    /**
     * punto de entrada de la aplicacion invoca la creacion de la ventana en el
     * hilo de eventos de swing esto es necesario porque swing no es thread-safe
     * y todas las operaciones de la interfaz deben ejecutarse en el hilo de
     * eventos (edt)
     *
     * @param args argumentos de linea de comandos (no se usan)
     */
    
    private String RecortarTexto(String texto,int max){
        return (texto.length()>max) ? texto.substring(0, max -1)+ "..":texto;
    }
    
    private void DibujarTarjeta(Graphics2D g2, Partido p, int x, int y, int ancho, int alto){
        g2.setColor(new Color (185,195,205));
        g2.fillRoundRect(x, y, ancho, alto, 8, 8);
        
        g2.setColor(new Color(130,140,155));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(x, y, ancho, alto, 8, 8);
        
        int anchoGoles = 30;
        g2.setColor(new Color (40,45,55));
        g2.fillRoundRect(x + ancho - anchoGoles, y, anchoGoles, alto, 8, 8);
        g2.fillRect(x + ancho - anchoGoles, y, 6, alto);
        
        if (p != null){
            g2.setFont(new Font("Century Gothic", Font.BOLD, 12));
            g2.setColor(new Color(25,30,38));
            
            String local = RecortarTexto(p.getLocal().getNombrePais(),13);
            String vis =RecortarTexto(p.getVisitante().getNombrePais(), 13);
            
            g2.drawString(local, x + 6, y + 18);
            g2.drawString(vis, x + 6, y + 38);
            
            if(p.isJugado()){
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Century Gothic",Font.BOLD,12));
                g2.drawString(String.valueOf(p.getGolesLocal()), x + ancho - 20, y + 18);
                g2.drawString(String.valueOf(p.getGolesVisitante()), x + ancho - 20, y + 38);
            }
        }
        
        
        
    }
    
    private void LineasFlujo(Graphics2D g2, int xOrigen, int [] yOrigen, int xDestino, int altoTarjeta){
       g2.setColor(new Color(150,165,180));
       g2.setStroke(new BasicStroke(1.5f));
       int medioX = (xOrigen + xDestino)/2;
       
        for (int i = 0; i < yOrigen.length; i += 2) {
            if(i + 1 >= yOrigen.length)break;
            
            int y1 = yOrigen[i] + (altoTarjeta/2);
            int y2 = yOrigen[i+1] + (altoTarjeta/2);
            int yCentro = (y1+y2)/2;
            
            g2.drawLine(xOrigen, y1, medioX, y1);
            g2.drawLine(xOrigen, y2, medioX, y2);
            
            g2.drawLine(medioX, y1, medioX, y2);
            
            g2.drawLine(medioX, yCentro, xDestino, yCentro);
        }
    }
    
    
    
    public static void main(String[] args) {
        // invokeLater asegura que la creacion de la ventana se ejecute en el edt
        // ventanaprincipal::new es una referencia al constructor
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}
