
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class JMenu extends javax.swing.JFrame {

    public JMenu() {
        initComponents();
        mostrarTabla();
    }
    private String DNI;
    private String nombre;
    private String apPat;
    private String apMat;
    private String celular;
    private String correo;
    private String tipo; //variables que se una en private void tblPersonaMouseClicked
    
    PreparedStatement ps;
    Connection con;
    ResultSet rs;
    ResultSetMetaData rsmd; //variables de conexión en bd se usan en varios métodos
        
    
    private void mostrarTabla(){
        
        DefaultTableModel modeloTabla = (DefaultTableModel) tblPersona.getModel();
        modeloTabla.setRowCount(0);
        
        int columnas;
        
        try{
            con = Conexion.getConnection();
            ps = con.prepareStatement("Select DNIPe, IDTipo, PNom, PApPat,PApMat,PCelular from TPersona");
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            columnas = rsmd.getColumnCount();
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for(int i=0;i<columnas;i++){
                    fila[i]=rs.getObject(i+1);
                }
                modeloTabla.addRow(fila);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPersona = new javax.swing.JTable();
        txtFbuscardni = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Tabla Persona");

        btnCrear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        tblPersona.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblPersona.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "DNI", "Tipo", "Nombre", "Apellido Pat", "Apellido Mat", "Celular"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblPersona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPersonaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPersona);

        txtFbuscardni.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("DNI Persona");

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(btnCrear)
                        .addGap(82, 82, 82)
                        .addComponent(btnModificar)
                        .addGap(62, 62, 62)
                        .addComponent(btnEliminar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLimpiar))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFbuscardni)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1)
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtFbuscardni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar)
                            .addComponent(btnLimpiar))))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // Limpia la tabla antes de mostrar los resultados
        DefaultTableModel modeloTabla = (DefaultTableModel) tblPersona.getModel();
        modeloTabla.setRowCount(0);

        // Captura el valor del DNI desde el TextField
        String dni = txtFbuscardni.getText().trim(); // Eliminamos espacios innecesarios

        // Validación del DNI
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un DNI válido.");
            return; // Sale del método si no hay DNI
        }

        String sql = "SELECT DNIPe, IDTipo, PNom, PApPat, PApMat, PCelular FROM TPersona WHERE DNIPe = ?";

        try (Connection con = Conexion.getConnection(); // Manejo automático de recursos
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Establece el valor del DNI en la consulta
            ps.setString(1, dni);

            // Ejecuta la consulta
            try (ResultSet rs = ps.executeQuery()) {
                rsmd = rs.getMetaData();
                int columnas = rsmd.getColumnCount();

                // Verifica si hay resultados
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "No se encontró un registro con ese DNI.");
                    return; // Sale del método si no hay registros
                }

                // Agrega los resultados a la tabla
                do {
                    Object[] fila = new Object[columnas];
                    for (int i = 0; i < columnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    modeloTabla.addRow(fila);
                } while (rs.next());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtFbuscardni.setText("");
        mostrarTabla();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblPersonaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPersonaMouseClicked
        try{
                        
            int fila = tblPersona.getSelectedRow(); //para saber qué fila se está seleccionando
            int dni = Integer.parseInt(tblPersona.getValueAt(fila,0).toString()); //fila defina la fila de donde se toman los datos y 0 la posicion de columna
           
            con = Conexion.getConnection();
            ps = con.prepareStatement("Select PNom, PApPat,PApMat,PCelular,PCorreo,IDTipo from TPersona where DNIPe = ?");
            ps.setInt(1,dni);
            rs=ps.executeQuery();
            
            while(rs.next()){
                DNI = String.valueOf(dni);
                nombre = rs.getString("PNom");
                apPat = rs.getString("PApPat");
                apMat = rs.getString("PApMat");
                celular = rs.getString("PCelular");
                tipo = rs.getString("IDTipo");
                correo = rs.getString("PCorreo");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }//GEN-LAST:event_tblPersonaMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // Crear la instancia de JModificar con las variables
        JModificar modificar = new JModificar(DNI, nombre, apPat, apMat, celular, correo, tipo);

        // Mostrar la ventana JModificar
        modificar.setVisible(true);

        // Opcionalmente, cerrar el JFrame actual (JMenu)
        this.dispose();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // Crear la instancia de JCrear con las variables
        JCrear crear = new JCrear();

        // Mostrar la ventana JCrear
        crear.setVisible(true);

        // Opcionalmente, cerrar el JFrame actual (JMenu)
        this.dispose();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblPersona.getSelectedRow(); // para saber qué fila se está seleccionando

        // Verificar si se ha seleccionado una fila
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un registro para eliminar.");
            return;
        }

        int dni = Integer.parseInt(tblPersona.getValueAt(fila, 0).toString());

        // Mostrar un diálogo de confirmación
        int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        // Si el usuario selecciona "Sí"
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                con = Conexion.getConnection();
                ps = con.prepareStatement("DELETE FROM TPersona WHERE DNIPe=?");
                ps.setInt(1, dni);

                ps.executeUpdate(); // para ejecutar la consulta

                JOptionPane.showMessageDialog(null, "Registro eliminado");
                mostrarTabla();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "El registro no ha sido eliminado.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    
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
            java.util.logging.Logger.getLogger(JMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblPersona;
    private javax.swing.JTextField txtFbuscardni;
    // End of variables declaration//GEN-END:variables
}
