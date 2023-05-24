/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mib.grupp.pkg15;

/**
 *
 * @author Linda
 */
//    Fälten för AdminVisaUtrustning.
public class AdminVisaUtrustning extends javax.swing.JFrame {

    private String användarnamn;

    /**
     * Konstruktorn för AdminVisaUtrustning.
     *
     * @param användarnamn
     */
    public AdminVisaUtrustning(String användarnamn) {
        initComponents();
        this.användarnamn = användarnamn;
        FyllText.inloggadSom(lblInloggadSom, användarnamn);
        ComboBoxar.fyllCBAgentNamn(cbVäljAgent);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBacka = new javax.swing.JButton();
        btnLoggaut = new javax.swing.JButton();
        btnAvsluta = new javax.swing.JButton();
        lblInloggadSom = new javax.swing.JLabel();
        cbVäljAgent = new javax.swing.JComboBox<>();
        lblLäggaTill = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtareaVisaUtrustning = new javax.swing.JTextArea();
        btnVisaUtrustning = new javax.swing.JButton();
        btnVisaFordon = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnBacka.setText("Föregående sida");
        btnBacka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackaActionPerformed(evt);
            }
        });

        btnLoggaut.setText("Logga ut");
        btnLoggaut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoggautActionPerformed(evt);
            }
        });

        btnAvsluta.setText("Avsluta program");
        btnAvsluta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvslutaActionPerformed(evt);
            }
        });

        lblInloggadSom.setText("Inloggad som");

        lblLäggaTill.setText("Välj Agent");

        txtareaVisaUtrustning.setEditable(false);
        txtareaVisaUtrustning.setColumns(20);
        txtareaVisaUtrustning.setRows(5);
        jScrollPane1.setViewportView(txtareaVisaUtrustning);

        btnVisaUtrustning.setText("Visa vald Agents utrustning");
        btnVisaUtrustning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisaUtrustningActionPerformed(evt);
            }
        });

        btnVisaFordon.setText("Visa vald Agents fordon");
        btnVisaFordon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisaFordonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblInloggadSom)
                .addGap(152, 152, 152))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblLäggaTill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbVäljAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoggaut))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnBacka)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAvsluta))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnVisaUtrustning)
                                        .addGap(37, 37, 37)
                                        .addComponent(btnVisaFordon))
                                    .addComponent(jScrollPane1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblInloggadSom)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnLoggaut)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLäggaTill)
                            .addComponent(cbVäljAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVisaUtrustning)
                    .addComponent(btnVisaFordon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAvsluta)
                    .addComponent(btnBacka))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Knappen för att starta metoden Logga ut.
    private void btnLoggautActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoggautActionPerformed
        Navigera.openStartSkärm();
        dispose();
    }//GEN-LAST:event_btnLoggautActionPerformed
    // Knappen för att starta metoden Avsluta program.
    private void btnAvslutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvslutaActionPerformed
        Navigera.avslutaProgram();
    }//GEN-LAST:event_btnAvslutaActionPerformed
    //    Knappen för att starta metoden Backa.
    private void btnBackaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackaActionPerformed
        new AdminUtrustning(användarnamn).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBackaActionPerformed
    // Knappen för att visa vilken utrustning en agent har på sig.
    private void btnVisaUtrustningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisaUtrustningActionPerformed
        MetoderUnikaAdmin.listaAgentsUtrustning(cbVäljAgent, txtareaVisaUtrustning);
    }//GEN-LAST:event_btnVisaUtrustningActionPerformed
    // Knappen för att visa vilka fordon en agent har.
    private void btnVisaFordonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisaFordonActionPerformed
        MetoderUnikaAdmin.listaAgentsFordon(cbVäljAgent, txtareaVisaUtrustning);
    }//GEN-LAST:event_btnVisaFordonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvsluta;
    private javax.swing.JButton btnBacka;
    private javax.swing.JButton btnLoggaut;
    private javax.swing.JButton btnVisaFordon;
    private javax.swing.JButton btnVisaUtrustning;
    private javax.swing.JComboBox<String> cbVäljAgent;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInloggadSom;
    private javax.swing.JLabel lblLäggaTill;
    private javax.swing.JTextArea txtareaVisaUtrustning;
    // End of variables declaration//GEN-END:variables
}