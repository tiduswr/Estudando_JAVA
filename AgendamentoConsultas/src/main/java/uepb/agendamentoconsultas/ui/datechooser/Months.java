package uepb.agendamentoconsultas.ui.datechooser;

public final class Months extends javax.swing.JPanel {

    private Event event;
    private int m;

    public Months() {
        initComponents();
    }

    private void addEvent() {
        for (int i = 0; i < getComponentCount(); i++) {
            ((Button) getComponent(i)).setEvent(event);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmd1 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd2 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd3 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd4 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd5 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd6 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd7 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd8 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd9 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd10 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd11 = new uepb.agendamentoconsultas.ui.datechooser.Button();
        cmd12 = new uepb.agendamentoconsultas.ui.datechooser.Button();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridLayout(4, 4));

        cmd1.setForeground(new java.awt.Color(75, 75, 75));
        cmd1.setText("Janeiro");
        cmd1.setName("1"); // NOI18N
        cmd1.setOpaque(true);
        add(cmd1);

        cmd2.setForeground(new java.awt.Color(75, 75, 75));
        cmd2.setText("Fevereiro");
        cmd2.setName("2"); // NOI18N
        cmd2.setOpaque(true);
        add(cmd2);

        cmd3.setForeground(new java.awt.Color(75, 75, 75));
        cmd3.setText("Março");
        cmd3.setName("3"); // NOI18N
        cmd3.setOpaque(true);
        add(cmd3);

        cmd4.setForeground(new java.awt.Color(75, 75, 75));
        cmd4.setText("Abril");
        cmd4.setName("4"); // NOI18N
        cmd4.setOpaque(true);
        add(cmd4);

        cmd5.setForeground(new java.awt.Color(75, 75, 75));
        cmd5.setText("Maio");
        cmd5.setName("5"); // NOI18N
        cmd5.setOpaque(true);
        add(cmd5);

        cmd6.setForeground(new java.awt.Color(75, 75, 75));
        cmd6.setText("Junho");
        cmd6.setName("6"); // NOI18N
        cmd6.setOpaque(true);
        add(cmd6);

        cmd7.setForeground(new java.awt.Color(75, 75, 75));
        cmd7.setText("Julho");
        cmd7.setName("7"); // NOI18N
        cmd7.setOpaque(true);
        add(cmd7);

        cmd8.setForeground(new java.awt.Color(75, 75, 75));
        cmd8.setText("Agosto");
        cmd8.setName("8"); // NOI18N
        cmd8.setOpaque(true);
        add(cmd8);

        cmd9.setForeground(new java.awt.Color(75, 75, 75));
        cmd9.setText("Setembro");
        cmd9.setName("9"); // NOI18N
        cmd9.setOpaque(true);
        add(cmd9);

        cmd10.setForeground(new java.awt.Color(75, 75, 75));
        cmd10.setText("Outubro");
        cmd10.setName("10"); // NOI18N
        cmd10.setOpaque(true);
        add(cmd10);

        cmd11.setForeground(new java.awt.Color(75, 75, 75));
        cmd11.setText("Novembro");
        cmd11.setName("11"); // NOI18N
        cmd11.setOpaque(true);
        add(cmd11);

        cmd12.setForeground(new java.awt.Color(75, 75, 75));
        cmd12.setText("Dezembro");
        cmd12.setName("12"); // NOI18N
        cmd12.setOpaque(true);
        add(cmd12);
    }// </editor-fold>//GEN-END:initComponents

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
        addEvent();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd1;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd10;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd11;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd12;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd2;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd3;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd4;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd5;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd6;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd7;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd8;
    private uepb.agendamentoconsultas.ui.datechooser.Button cmd9;
    // End of variables declaration//GEN-END:variables

}
