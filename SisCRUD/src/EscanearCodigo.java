import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;

public class EscanearCodigo extends javax.swing.JFrame {
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    String codigoEscaneado;
    ResultSetMetaData rsmd; 
    ConexionSerial conexion = new ConexionSerial();

    public EscanearCodigo() {
        initComponents(); 
        conexion.conectar("COM7");
        
        // Listener para capturar el código escaneado desde tfDNI
        tfDNI.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoEscaneado = tfDNI.getText();
                System.out.println("Código escaneado: " + codigoEscaneado);
                // Aquí puedes realizar otras acciones con el código escaneado
                DeterminarAcceso();
                    }
                });
        
        // Agregar listener para desconectar el puerto al cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                conexion.desconectar();
                System.exit(0);
            }
        });
    }
    
    private void DeterminarAcceso() {
        try {
            con = Conexion.getConnection(); // Conexión a la base de datos
            String dni = tfDNI.getText();

            // Preparar la consulta para filtrar por el DNI
            ps = con.prepareStatement("SELECT DNIPe FROM TPersona WHERE DNIPe = ?");
            ps.setString(1, dni);
            rs = ps.executeQuery();
            
            // Verificar que el DNI solo contenga dígitos
            if (!dni.matches("\\d+")) {
                System.out.println("El DNI debe contener solo números.");
                lbAcceso.setForeground(Color.yellow);
                lbAcceso.setText("CÓDIGO INVÁLIDO");
                conexion.enviarDato("2");
            }else{
                
            long DNI2 = Long.parseLong(dni);
                if(DNI2<2147483646){
                    // Lógica para el acceso
                    if (rs.next()) { // Si se encuentra un registro
                        lbAcceso.setForeground(Color.green);
                        lbAcceso.setText("ACCESO PERMITIDO");
                        GuardarOcurrencia();
                        conexion.enviarDato("1");
                    } else {
                        lbAcceso.setForeground(Color.red);
                        lbAcceso.setText("ACCESO DENEGADO");
                        AccesoDenegado();
                        conexion.enviarDato("2");
                    }
                }else{
                    lbAcceso.setForeground(Color.yellow);
                    lbAcceso.setText("CÓDIGO INVÁLIDO");
                    conexion.enviarDato("2");
                } 
            }

            // Usar javax.swing.Timer para esperar 2 segundos antes de reiniciar
            Timer timer = new Timer(1500, e -> {
                tfDNI.setText("");
                lbAcceso.setText("");
            });
            timer.setRepeats(false); // Solo ejecutarlo una vez
            timer.start(); // Iniciar el temporizador

        } catch (SQLException ex) {
            Logger.getLogger(JLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerrar recursos en el bloque finally
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                Logger.getLogger(JLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void GuardarOcurrencia() {
        String tipo = ""; // Inicializa la variable para almacenar el IDTipo
        String sql = "SELECT IDTipo FROM TPersona WHERE DNIPe = ?"; // Consulta SQL para seleccionar IDTipo

        try (Connection con = Conexion.getConnection(); // Manejo automático de recursos
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Establece el valor del DNI en la consulta
            ps.setString(1, codigoEscaneado);

            // Ejecuta la consulta
            try (ResultSet rs = ps.executeQuery()) {
                // Verifica si hay resultados
                if (rs.next()) {
                    // Recupera el IDTipo del conjunto de resultados
                    tipo = rs.getString("IDTipo"); // Obtiene el valor de IDTipo
                } else {
                    // Maneja el caso donde no se encuentra un registro coincidente
                    System.out.println("No se encontraron registros para el DNI proporcionado.");
                    return; // Salir si no se encuentra el DNI
                }
            }
        } catch (SQLException e) {
            // Maneja excepciones SQL
            System.out.println("Error al acceder a la base de datos: " + e.getMessage());
            return; // Salir en caso de error
        }

        // Obtener fecha y hora actuales
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Formato de fecha
        String hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Formato de hora

        int dni = Integer.parseInt(codigoEscaneado.trim());

        // Inserción en la tabla TOcurrencia
        String insertSql = "INSERT INTO TOcurrencia (OcHora, OcFecha, IDTipo, DNIPe) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection(); // Nueva conexión para la inserción
             PreparedStatement ps = con.prepareStatement(insertSql)) {

            ps.setString(1, hora);
            ps.setString(2, fecha);
            ps.setString(3, tipo);
            ps.setInt(4, dni);

            int filasAfectadas = ps.executeUpdate(); // Ejecuta la consulta y obtiene el número de filas afectadas

            if (filasAfectadas > 0) {
                System.out.println("Ocurrencia guardada con éxito.");
            } else {
                System.out.println("No se pudo guardar la ocurrencia.");
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar la ocurrencia: " + e.getMessage());
        }
    }

    private void AccesoDenegado() {
        // Obtener fecha y hora actuales
        LocalDateTime ahora = LocalDateTime.now();
        String hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Formato de hora
        String fecha = ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Formato de fecha
        String tipo = "N"; // Inicializa la variable para almacenar el IDTipo
        int dni = Integer.parseInt(codigoEscaneado.trim());

        // Inserción en la tabla TOcurrencia
        String insertSql = "INSERT INTO TAccesoDenegado (ADHora, ADFecha, IDTipo, DNI) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection(); // Nueva conexión para la inserción
             PreparedStatement ps = con.prepareStatement(insertSql)) {

            ps.setString(1, hora);
            ps.setString(2, fecha);
            ps.setString(3, tipo);
            ps.setInt(4, dni);

            int filasAfectadas = ps.executeUpdate(); // Ejecuta la consulta y obtiene el número de filas afectadas

            if (filasAfectadas > 0) {
                System.out.println("Acceso Denegado guardado con éxito.");
            } else {
                System.out.println("No se pudo guardar el intento de acceso.");
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar el acceso denegado: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        tfDNI = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbAcceso = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        tfDNI.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tfDNI.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(tfDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfDNI, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DNI");

        jPanel3.setBackground(new java.awt.Color(204, 51, 255));

        lbAcceso.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbAcceso.setForeground(new java.awt.Color(0, 153, 102));
        lbAcceso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbAcceso.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(lbAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbAcceso)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Bienvenido a la");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo2.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("UNIVERSIDAD CONTINENTAL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addGap(43, 43, 43)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EscanearCodigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EscanearCodigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EscanearCodigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EscanearCodigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EscanearCodigo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbAcceso;
    private javax.swing.JTextField tfDNI;
    // End of variables declaration//GEN-END:variables
}
