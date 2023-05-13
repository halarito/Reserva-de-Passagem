/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import java.awt.Color;
import javax.swing.JOptionPane;
import Classes.Modulo;
import java.sql.*;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Arlindo Halar
 */
public class Main extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();

        conexao = Modulo.conector();

        listarFunc();
        listarBilhete();
        pintarCadeira();
    }

    void pintarCadeira() {

        String ver = "select nome, cadeira from bilhete where cadeira = ? and partida = ? and chegada = ? and data_partida = ?";

        try {

            pst = conexao.prepareStatement(ver);

            pst.setString(1, cadeira);
            pst.setString(2, jComboBox4.getSelectedItem().toString());
            pst.setString(3, jComboBox5.getSelectedItem().toString());
            pst.setString(4, jSpinner1.getValue().toString() + "-" + jSpinner2.getValue().toString() + "-" + jSpinner3.getValue().toString());

            rs = pst.executeQuery();

            if (rs.next()) {
                if (cadeira == rs.getString("cadeira")) {
                    JOptionPane.showMessageDialog(rootPane, "CADEIRA OCUPADA E INDISPONIVEL");
                } else {

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    void listarFunc() {

        String sql = "Select * from func";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    void listarBilhete() {

        String sql = "Select id,nome,contacto, cadeira, cobrador, matricula from bilhete where partida = ? and chegada = ? and data_partida = ?";

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, jComboBox4.getSelectedItem().toString());
            pst.setString(2, jComboBox5.getSelectedItem().toString());
            pst.setString(3, jSpinner1.getValue().toString() + "-" + jSpinner2.getValue().toString() + "-" + jSpinner3.getValue().toString());

            rs = pst.executeQuery();

            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    void cadastrarFunc() {

        String registar = "insert into func(nome, naturalidade, morada, contacto, bi, cargo, perfil, senha, usuario) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            pst = conexao.prepareStatement(registar);

            pst.setString(1, jTextField2.getText());
            pst.setString(2, String.valueOf(jComboBox1.getSelectedItem()));
            pst.setString(3, jTextField3.getText());
            pst.setString(4, jFormattedTextField1.getText());
            pst.setString(5, jFormattedTextField2.getText());
            pst.setString(6, String.valueOf(jComboBox2.getSelectedItem()));
            pst.setString(7, String.valueOf(jComboBox3.getSelectedItem()));
            pst.setString(8, jTextField4.getText());
            pst.setString(9, jTextField5.getText());

            if (jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty() || jFormattedTextField1.getText().equals("(+258)") || jFormattedTextField2.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "PREENCHA OS CAMPOS OBRIGATÓRIOS");

            } else {
                int cadastrar = JOptionPane.showConfirmDialog(rootPane, "CADASTRAR NOVO FUNCIONÁRIO?", "Funcionário", JOptionPane.YES_NO_OPTION);
                if (cadastrar == JOptionPane.YES_OPTION) {
                    int cadastro = pst.executeUpdate();
                    if (cadastro > 0) {
                        JOptionPane.showMessageDialog(null, "FUNCIONÁRIO CADASTRADO COM SUCESSO");

                        listarFunc();

                        jTextField1.setText(null);
                        jTextField2.setText(null);
                        jTextField3.setText(null);
                        jFormattedTextField1.setText("(+258)");
                        jComboBox1.setSelectedIndex(0);
                        jComboBox2.setSelectedIndex(0);
                        jComboBox3.setSelectedIndex(0);
                        jFormattedTextField2.setText(null);
                        jTextField4.setText(null);
                        jTextField5.setText(null);

                    } else {
                        JOptionPane.showMessageDialog(null, "FALHA AO CADASTRAR NOVO FUNCIONARIO");
                    }
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void alterarFunc() {

        String actualizar = "update func set nome = ?, naturalidade = ?, morada = ?, contacto = ?, bi = ?, cargo = ?, perfil = ?, senha = ?, usuario = ? where id = ?";
        try {

            pst = conexao.prepareStatement(actualizar);

            pst.setString(1, jTextField2.getText());
            pst.setString(2, String.valueOf(jComboBox1.getSelectedItem()));
            pst.setString(3, jTextField3.getText());
            pst.setString(4, jFormattedTextField1.getText());
            pst.setString(5, jFormattedTextField2.getText());
            pst.setString(6, String.valueOf(jComboBox2.getSelectedItem()));
            pst.setString(7, String.valueOf(jComboBox3.getSelectedItem()));
            pst.setString(8, jTextField4.getText());
            pst.setString(9, jTextField5.getText());

            if (jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty() || jFormattedTextField1.getText().equals("(+258)") || jFormattedTextField2.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "PREENCHA OS CAMPOS OBRIGATÓRIOS");

            } else {
                int confirmacao = JOptionPane.showConfirmDialog(rootPane, "ALTERAR DADOS?", "FUNCIONARIO", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    int alterar = pst.executeUpdate();

                    if (alterar > 0) {
                        JOptionPane.showMessageDialog(rootPane, "DADOS ALTERADOS COM SUCESSO!");

                        listarFunc();

                        jTextField1.setText(null);
                        jTextField2.setText(null);
                        jTextField3.setText(null);
                        jFormattedTextField1.setText("(+258)");
                        jComboBox1.setSelectedIndex(0);
                        jComboBox2.setSelectedIndex(0);
                        jComboBox3.setSelectedIndex(0);
                        jFormattedTextField2.setText(null);
                        jTextField4.setText(null);
                        jTextField5.setText(null);

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "FALHA AO ALTERAR DADOS!");

                    }
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    void limparFunc() {

        int limpar = JOptionPane.showConfirmDialog(rootPane, "LIMPAR CAMPOS?", "CAMPOS", JOptionPane.YES_OPTION);

        if (limpar == JOptionPane.YES_OPTION) {

            jTextField1.setText(null);
            jTextField2.setText(null);
            jTextField3.setText(null);
            jFormattedTextField1.setText("(+258)");
            jComboBox1.setSelectedIndex(0);
            jComboBox2.setSelectedIndex(0);
            jComboBox3.setSelectedIndex(0);
            jFormattedTextField2.setText(null);
            jTextField4.setText(null);
            jTextField5.setText(null);

        }

    }

    void eliminarFunc() {

        String eliminar = "delete from Func where id = ?";

        try {

            String id = jTextField1.getText();

            pst = conexao.prepareStatement(eliminar);

            pst.setString(1, id);

            int apagar = JOptionPane.showConfirmDialog(rootPane, "ELIMINAR?", "FUNCIONARIO", JOptionPane.YES_NO_OPTION);

            if (apagar == JOptionPane.YES_OPTION) {
                int apadago = pst.executeUpdate();

                if (apadago > 0) {
                    JOptionPane.showMessageDialog(rootPane, "DADOS ELIMINADOS COM SUCESSO!");

                    listarFunc();

                } else {
                    JOptionPane.showMessageDialog(rootPane, "FALHA NA ELIMINACAO!");

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);

        }
    }

    String[] cadeiras = new String[40];

    void teste() {

        String bt = "bt";

        for (int j = 0; j <= 4; j++) {

            //for(int i = 1; i < 6; i++){
            cadeiras[j] = bt + j;

            //}
            //System.out.println(bt+i);
        }

        //System.out.println(cadeiras[3]);
    }
    private static boolean verCadeira;
    private static String cadeira;

    void comprarBilhete() {

        String comprar = "insert into bilhete (nome, bi, contacto, contacto_alt, cadeira, cobrador, contacto_c, matricula, partida, chegada, data_partida) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String botao = "tb";
        int cont = 0;
        
        for (int i = 0; i <= 39; i++) {

            cadeiras[i] = botao + i;
            

        }
        

        if (tb1.isSelected() == false && tb2.isSelected() == false && tb3.isSelected() == false && tb4.isSelected() == false && tb5.isSelected() == false && tb6.isSelected() == false && tb7.isSelected() == false && tb8.isSelected() == false && tb9.isSelected() == false && tb10.isSelected() == false && tb11.isSelected() == false && tb12.isSelected() == false && tb13.isSelected() == false && tb14.isSelected() == false && tb15.isSelected() == false && tb16.isSelected() == false && tb17.isSelected() == false && tb18.isSelected() == false && tb19.isSelected() == false && tb20.isSelected() == false && tb21.isSelected() == false && tb22.isSelected() == false && tb23.isSelected() == false && tb24.isSelected() == false && tb25.isSelected() == false && tb26.isSelected() == false && tb27.isSelected() == false && tb28.isSelected() == false && tb29.isSelected() == false && tb30.isSelected() == false && tb31.isSelected() == false && tb32.isSelected() == false && tb33.isSelected() == false && tb34.isSelected() == false && tb35.isSelected() == false && tb36.isSelected() == false && tb37.isSelected() == false && tb38.isSelected() == false && tb39.isSelected() == false && tb40.isSelected() == false) {
            verCadeira = false;
        } else {
            verCadeira = true;
        }
        try {

            pst = conexao.prepareStatement(comprar);

            pst.setString(1, jTextField6.getText());
            pst.setString(2, jTextField7.getText());
            pst.setString(3, jTextField8.getText());
            pst.setString(4, jTextField9.getText());

            pst.setString(5, jTextField6.getText());

            if (tb1.isSelected()) {
                cadeira = "1";
            } else if (tb2.isSelected()) {
                cadeira = "2";
            } else if (tb3.isSelected()) {
                cadeira = "3";
            } else if (tb4.isSelected()) {
                cadeira = "4";
            } else if (tb5.isSelected()) {
                cadeira = "5";
            } else if (tb6.isSelected()) {
                cadeira = "6";
            } else if (tb7.isSelected()) {
                cadeira = "7";
            } else if (tb8.isSelected()) {
                cadeira = "8";
            } else if (tb9.isSelected()) {
                cadeira = "9";
            } else if (tb10.isSelected()) {
                cadeira = "10";
            } else if (tb11.isSelected()) {
                cadeira = "11";
            } else if (tb12.isSelected()) {
                cadeira = "12";
            } else if (tb13.isSelected()) {
                cadeira = "13";
            } else if (tb14.isSelected()) {
                cadeira = "14";
            } else if (tb15.isSelected()) {
                cadeira = "15";
            } else if (tb16.isSelected()) {
                cadeira = "16";
            } else if (tb17.isSelected()) {
                cadeira = "17";
            } else if (tb18.isSelected()) {
                cadeira = "18";
            } else if (tb19.isSelected()) {
                cadeira = "19";
            } else if (tb20.isSelected()) {
                cadeira = "20";
            } else if (tb21.isSelected()) {
                cadeira = "21";
            } else if (tb22.isSelected()) {
                cadeira = "22";
            } else if (tb23.isSelected()) {
                cadeira = "23";
            } else if (tb24.isSelected()) {
                cadeira = "26";
            } else if (tb25.isSelected()) {
                cadeira = "25";
            } else if (tb26.isSelected()) {
                cadeira = "26";
            } else if (tb27.isSelected()) {
                cadeira = "27";
            } else if (tb28.isSelected()) {
                cadeira = "28";
            } else if (tb29.isSelected()) {
                cadeira = "29";
            } else if (tb30.isSelected()) {
                cadeira = "30";
            } else if (tb31.isSelected()) {
                cadeira = "31";
            } else if (tb32.isSelected()) {
                cadeira = "32";
            } else if (tb33.isSelected()) {
                cadeira = "33";
            } else if (tb34.isSelected()) {
                cadeira = "34";
            } else if (tb35.isSelected()) {
                cadeira = "35";
            } else if (tb36.isSelected()) {
                cadeira = "36";
            } else if (tb37.isSelected()) {
                cadeira = "37";
            } else if (tb38.isSelected()) {
                cadeira = "38";
            } else if (tb39.isSelected()) {
                cadeira = "39";
            } else if (tb40.isSelected()) {
                cadeira = "40";
            }

            //System.out.println(cont);
            pst.setString(5, cadeira);
            pst.setString(6, jTextField10.getText());
            pst.setString(7, jTextField11.getText());
            pst.setString(8, jTextField12.getText());
            pst.setString(9, jComboBox4.getSelectedItem().toString());
            pst.setString(10, jComboBox5.getSelectedItem().toString());
            pst.setString(11, jSpinner1.getValue().toString() + "-" + jSpinner2.getValue().toString() + "-" + jSpinner3.getValue().toString());

            if (jTextField6.getText().isEmpty() || jTextField7.getText().isEmpty() || jTextField8.getText().isEmpty() || jTextField9.getText().isEmpty() || jTextField10.getText().isEmpty() || jTextField11.getText().isEmpty() || jTextField12.getText().isEmpty() || verCadeira == false) {

                JOptionPane.showMessageDialog(rootPane, "PREENCHA TODOS OS CAMPOS (e escolha o assunto), POR FAVOR");

            } else {
                int cmprar = JOptionPane.showConfirmDialog(rootPane, "COMPRAR BILHETE?", "BILHETE", JOptionPane.YES_OPTION);

                if (cmprar == JOptionPane.YES_OPTION) {

                    String ver = "select nome, cadeira from bilhete where cadeira = ? and partida = ? and chegada = ? and data_partida = ?";

                    try {

                        pst1 = conexao.prepareStatement(ver);

                        pst1.setString(1, cadeira);
                        pst1.setString(2, jComboBox4.getSelectedItem().toString());
                        pst1.setString(3, jComboBox5.getSelectedItem().toString());
                        pst1.setString(4, jSpinner1.getValue().toString() + "-" + jSpinner2.getValue().toString() + "-" + jSpinner3.getValue().toString());

                        rs = pst1.executeQuery();
                        
                        if (rs.next()) {
                            
                            JOptionPane.showMessageDialog(rootPane, "CADEIRA OCUPADA OU INDISPONIVEL");
                            
                        }else{
                            
                           
                                int comprado = pst.executeUpdate();

                                if (comprado > 0) {

                                    JOptionPane.showMessageDialog(rootPane, "BILHETE COMPRADO");
                                    listarBilhete();
                                    limparBilhete();

                                }
                            
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    void alterarBilhete(){
        
        String alterar = "update bilhete set nome = ?, bi = ?, contacto = ?, contacto_alt = ?, cadeira = ?, cobrador = ?, contacto_c = ?, matricula = ?, partida = ?, chegada = ?, data_partida = ? where id = ?";
        
        if (tb1.isSelected() == false && tb2.isSelected() == false && tb3.isSelected() == false && tb4.isSelected() == false && tb5.isSelected() == false && tb6.isSelected() == false && tb7.isSelected() == false && tb8.isSelected() == false && tb9.isSelected() == false && tb10.isSelected() == false && tb11.isSelected() == false && tb12.isSelected() == false && tb13.isSelected() == false && tb14.isSelected() == false && tb15.isSelected() == false && tb16.isSelected() == false && tb17.isSelected() == false && tb18.isSelected() == false && tb19.isSelected() == false && tb20.isSelected() == false && tb21.isSelected() == false && tb22.isSelected() == false && tb23.isSelected() == false && tb24.isSelected() == false && tb25.isSelected() == false && tb26.isSelected() == false && tb27.isSelected() == false && tb28.isSelected() == false && tb29.isSelected() == false && tb30.isSelected() == false && tb31.isSelected() == false && tb32.isSelected() == false && tb33.isSelected() == false && tb34.isSelected() == false && tb35.isSelected() == false && tb36.isSelected() == false && tb37.isSelected() == false && tb38.isSelected() == false && tb39.isSelected() == false && tb40.isSelected() == false) {
            verCadeira = false;
        } else {
            verCadeira = true;
        }
        
        try {
            
            pst = conexao.prepareStatement(alterar);
            
            pst.setString(1, jTextField6.getText());
            pst.setString(2, jTextField7.getText());
            pst.setString(3, jTextField8.getText());
            pst.setString(4, jTextField9.getText());

            pst.setString(5, jTextField6.getText());

            if (tb1.isSelected()) {
                cadeira = "1";
            } else if (tb2.isSelected()) {
                cadeira = "2";
            } else if (tb3.isSelected()) {
                cadeira = "3";
            } else if (tb4.isSelected()) {
                cadeira = "4";
            } else if (tb5.isSelected()) {
                cadeira = "5";
            } else if (tb6.isSelected()) {
                cadeira = "6";
            } else if (tb7.isSelected()) {
                cadeira = "7";
            } else if (tb8.isSelected()) {
                cadeira = "8";
            } else if (tb9.isSelected()) {
                cadeira = "9";
            } else if (tb10.isSelected()) {
                cadeira = "10";
            } else if (tb11.isSelected()) {
                cadeira = "11";
            } else if (tb12.isSelected()) {
                cadeira = "12";
            } else if (tb13.isSelected()) {
                cadeira = "13";
            } else if (tb14.isSelected()) {
                cadeira = "14";
            } else if (tb15.isSelected()) {
                cadeira = "15";
            } else if (tb16.isSelected()) {
                cadeira = "16";
            } else if (tb17.isSelected()) {
                cadeira = "17";
            } else if (tb18.isSelected()) {
                cadeira = "18";
            } else if (tb19.isSelected()) {
                cadeira = "19";
            } else if (tb20.isSelected()) {
                cadeira = "20";
            } else if (tb21.isSelected()) {
                cadeira = "21";
            } else if (tb22.isSelected()) {
                cadeira = "22";
            } else if (tb23.isSelected()) {
                cadeira = "23";
            } else if (tb24.isSelected()) {
                cadeira = "24";
            } else if (tb25.isSelected()) {
                cadeira = "25";
            } else if (tb26.isSelected()) {
                cadeira = "26";
            } else if (tb27.isSelected()) {
                cadeira = "27";
            } else if (tb28.isSelected()) {
                cadeira = "28";
            } else if (tb29.isSelected()) {
                cadeira = "29";
            } else if (tb30.isSelected()) {
                cadeira = "30";
            } else if (tb31.isSelected()) {
                cadeira = "31";
            } else if (tb32.isSelected()) {
                cadeira = "32";
            } else if (tb33.isSelected()) {
                cadeira = "33";
            } else if (tb34.isSelected()) {
                cadeira = "34";
            } else if (tb35.isSelected()) {
                cadeira = "35";
            } else if (tb36.isSelected()) {
                cadeira = "36";
            } else if (tb37.isSelected()) {
                cadeira = "37";
            } else if (tb38.isSelected()) {
                cadeira = "38";
            } else if (tb39.isSelected()) {
                cadeira = "39";
            } else if (tb40.isSelected()) {
                cadeira = "40";
            }

            //System.out.println(cont);
            pst.setString(5, cadeira);
            pst.setString(6, jTextField10.getText());
            pst.setString(7, jTextField11.getText());
            pst.setString(8, jTextField12.getText());
            pst.setString(9, jComboBox4.getSelectedItem().toString());
            pst.setString(10, jComboBox5.getSelectedItem().toString());
            pst.setString(11, jSpinner1.getValue().toString() + "-" + jSpinner2.getValue().toString() + "-" + jSpinner3.getValue().toString());
            pst.setString(12, jTextField13.getText());

            if (jTextField6.getText().isEmpty() || jTextField7.getText().isEmpty() || jTextField8.getText().isEmpty() || jTextField9.getText().isEmpty() || jTextField10.getText().isEmpty() || jTextField11.getText().isEmpty() || jTextField12.getText().isEmpty() || jTextField12.getText().isEmpty() || verCadeira == false) {

                JOptionPane.showMessageDialog(rootPane, "PREENCHA TODOS OS CAMPOS (e escolha o assunto), POR FAVOR");

            } else{
                int alter = JOptionPane.showConfirmDialog(rootPane, "ALTERAR BILHETE?", "BILHETE", JOptionPane.YES_OPTION);

                if (alter == JOptionPane.YES_OPTION){
                    
                    String ver = "select nome, cadeira from bilhete where cadeira = ? and partida = ? and chegada = ? and data_partida = ?";

                    try {

                        pst1 = conexao.prepareStatement(ver);

                        pst1.setString(1, cadeira);
                        pst1.setString(2, jComboBox4.getSelectedItem().toString());
                        pst1.setString(3, jComboBox5.getSelectedItem().toString());
                        pst1.setString(4, jSpinner1.getValue().toString() + "-" + jSpinner2.getValue().toString() + "-" + jSpinner3.getValue().toString());

                        rs = pst1.executeQuery();
                        
                        if (rs.next()) {
                            
                            JOptionPane.showMessageDialog(rootPane, "CADEIRA OCUPADA OU INDISPONIVEL");
                            
                        }else{
                            
                            //System.out.println(rs.getString("cadeira"));
                            
                            /*if (cadeira == rs.getString("cadeira")) {
                                JOptionPane.showMessageDialog(rootPane, "CADEIRA OCUPADA E INDISPONIVEL");
                            } *//*else {*/
                                int comprado = pst.executeUpdate();

                                if (comprado > 0) {

                                    JOptionPane.showMessageDialog(rootPane, "BILHETE ALTERADO");
                                    listarBilhete();
                                    limparBilhete();

                                }
                            //}
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }
                    
                }
            }
            
        } catch (Exception e) {
        
            JOptionPane.showMessageDialog(rootPane, e);
        
        }
        
        
    }
    
    void limparBilhete() {

        jTextField6.setText(null);
        jTextField7.setText(null);
        jTextField8.setText(null);
        jTextField9.setText(null);
        jTextField10.setText(null);
        jTextField11.setText(null);
        jTextField12.setText(null);
        jTextField13.setText(null);
        

        /*jSpinner1.setModel(null);
         jSpinner2.setModel(null);
         jSpinner3.setModel(null);*/
    }

    void eliminarBilhete() {

        String eliminar = "delete from bilhete where id = ?";

        try {

            String id = jTextField13.getText();

            pst = conexao.prepareStatement(eliminar);

            pst.setString(1, id);

            int apagar = JOptionPane.showConfirmDialog(rootPane, "ELIMINAR?", "BILHETE", JOptionPane.YES_NO_OPTION);

            if (apagar == JOptionPane.YES_OPTION) {
                int apadago = pst.executeUpdate();

                if (apadago > 0) {
                    JOptionPane.showMessageDialog(rootPane, "BILHETE ELIMINADO!");

                    listarBilhete();

                } else {
                    JOptionPane.showMessageDialog(rootPane, "FALHA NA ELIMINACAO!");

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);

        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox();
        jTextField5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        tb5 = new javax.swing.JToggleButton();
        tb6 = new javax.swing.JToggleButton();
        tb1 = new javax.swing.JToggleButton();
        tb2 = new javax.swing.JToggleButton();
        tb9 = new javax.swing.JToggleButton();
        tb10 = new javax.swing.JToggleButton();
        tb13 = new javax.swing.JToggleButton();
        tb14 = new javax.swing.JToggleButton();
        tb29 = new javax.swing.JToggleButton();
        tb30 = new javax.swing.JToggleButton();
        tb21 = new javax.swing.JToggleButton();
        tb22 = new javax.swing.JToggleButton();
        tb25 = new javax.swing.JToggleButton();
        tb18 = new javax.swing.JToggleButton();
        tb17 = new javax.swing.JToggleButton();
        tb26 = new javax.swing.JToggleButton();
        tb33 = new javax.swing.JToggleButton();
        tb34 = new javax.swing.JToggleButton();
        tb3 = new javax.swing.JToggleButton();
        tb4 = new javax.swing.JToggleButton();
        tb7 = new javax.swing.JToggleButton();
        tb8 = new javax.swing.JToggleButton();
        tb11 = new javax.swing.JToggleButton();
        tb12 = new javax.swing.JToggleButton();
        tb15 = new javax.swing.JToggleButton();
        tb16 = new javax.swing.JToggleButton();
        tb19 = new javax.swing.JToggleButton();
        tb20 = new javax.swing.JToggleButton();
        tb23 = new javax.swing.JToggleButton();
        tb24 = new javax.swing.JToggleButton();
        tb27 = new javax.swing.JToggleButton();
        tb28 = new javax.swing.JToggleButton();
        tb31 = new javax.swing.JToggleButton();
        tb32 = new javax.swing.JToggleButton();
        tb39 = new javax.swing.JToggleButton();
        tb40 = new javax.swing.JToggleButton();
        tb35 = new javax.swing.JToggleButton();
        tb36 = new javax.swing.JToggleButton();
        tb37 = new javax.swing.JToggleButton();
        tb38 = new javax.swing.JToggleButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jSpinner3 = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N

        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Dados do Funcionário");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CODIGO:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("NOME:");

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel13.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("BI NR:");

        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Controle de cargas", "Check-in Check-out", "Venda de Bilhetes" }));

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CARGO:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("PERFIL:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("SENHA:");

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Funcionario", "Administrador" }));

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("USUARIO:");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton1.setText("CADASTRAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton2.setText("ALTERAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton3.setText("ELIMINAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton4.setText("LIMPAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(199, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        try {
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(+258) ## ### ####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("MORADA:");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CONTACTO:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NATURALIDADE:");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Maputo", "Gaza", "Inhambane", "Sofala", "Manica", "Zambezia", "Nampula", "Cabo Delgado", "Niassa", " " }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel8))
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jFormattedTextField1)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(10, 10, 10)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel13, jLabel9});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addGap(8, 8, 8)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel12))))
                .addGap(79, 79, 79)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox1, jFormattedTextField1, jTextField1, jTextField2, jTextField3});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox2, jComboBox3, jFormattedTextField2, jLabel10, jLabel11, jLabel12, jLabel13, jLabel9, jTextField4, jTextField5});

        jTabbedPane2.addTab("OPÇÕES", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Lista dos Funcionário");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("ACESSAR LISTA", jPanel4);

        jTabbedPane1.addTab("FUNCIONARIOS", jTabbedPane2);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Dados do Bilhete");

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        jLabel58.setBackground(new java.awt.Color(255, 0, 0));
        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 0, 51));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("WC");
        jLabel58.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        tb5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb5.setText("5");
        tb5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb5StateChanged(evt);
            }
        });
        tb5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb5MouseClicked(evt);
            }
        });
        tb5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb5ActionPerformed(evt);
            }
        });

        tb6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb6.setText("6");
        tb6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb6StateChanged(evt);
            }
        });
        tb6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb6MouseClicked(evt);
            }
        });
        tb6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb6ActionPerformed(evt);
            }
        });

        tb1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb1.setText("1");
        tb1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb1StateChanged(evt);
            }
        });
        tb1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb1MouseClicked(evt);
            }
        });
        tb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb1ActionPerformed(evt);
            }
        });

        tb2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb2.setText("2");
        tb2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb2StateChanged(evt);
            }
        });
        tb2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb2MouseClicked(evt);
            }
        });
        tb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb2ActionPerformed(evt);
            }
        });

        tb9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb9.setText("9");
        tb9.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb9StateChanged(evt);
            }
        });
        tb9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb9MouseClicked(evt);
            }
        });
        tb9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb9ActionPerformed(evt);
            }
        });

        tb10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb10.setText("10");
        tb10.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb10StateChanged(evt);
            }
        });
        tb10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb10MouseClicked(evt);
            }
        });
        tb10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb10ActionPerformed(evt);
            }
        });

        tb13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb13.setText("13");
        tb13.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb13StateChanged(evt);
            }
        });
        tb13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb13MouseClicked(evt);
            }
        });
        tb13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb13ActionPerformed(evt);
            }
        });

        tb14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb14.setText("14");
        tb14.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb14StateChanged(evt);
            }
        });
        tb14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb14MouseClicked(evt);
            }
        });
        tb14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb14ActionPerformed(evt);
            }
        });

        tb29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb29.setText("29");
        tb29.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb29StateChanged(evt);
            }
        });
        tb29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb29MouseClicked(evt);
            }
        });
        tb29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb29ActionPerformed(evt);
            }
        });

        tb30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb30.setText("30");
        tb30.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb30StateChanged(evt);
            }
        });
        tb30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb30MouseClicked(evt);
            }
        });
        tb30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb30ActionPerformed(evt);
            }
        });

        tb21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb21.setText("21");
        tb21.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb21StateChanged(evt);
            }
        });
        tb21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb21MouseClicked(evt);
            }
        });
        tb21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb21ActionPerformed(evt);
            }
        });

        tb22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb22.setText("22");
        tb22.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb22StateChanged(evt);
            }
        });
        tb22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb22MouseClicked(evt);
            }
        });
        tb22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb22ActionPerformed(evt);
            }
        });

        tb25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb25.setText("25");
        tb25.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb25StateChanged(evt);
            }
        });
        tb25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb25MouseClicked(evt);
            }
        });
        tb25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb25ActionPerformed(evt);
            }
        });

        tb18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb18.setText("18");
        tb18.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb18StateChanged(evt);
            }
        });
        tb18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb18MouseClicked(evt);
            }
        });
        tb18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb18ActionPerformed(evt);
            }
        });

        tb17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb17.setText("17");
        tb17.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb17StateChanged(evt);
            }
        });
        tb17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb17MouseClicked(evt);
            }
        });
        tb17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb17ActionPerformed(evt);
            }
        });

        tb26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb26.setText("26");
        tb26.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb26StateChanged(evt);
            }
        });
        tb26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb26MouseClicked(evt);
            }
        });
        tb26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb26ActionPerformed(evt);
            }
        });

        tb33.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb33.setText("33");
        tb33.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb33StateChanged(evt);
            }
        });
        tb33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb33MouseClicked(evt);
            }
        });
        tb33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb33ActionPerformed(evt);
            }
        });

        tb34.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb34.setText("34");
        tb34.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb34StateChanged(evt);
            }
        });
        tb34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb34MouseClicked(evt);
            }
        });
        tb34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb34ActionPerformed(evt);
            }
        });

        tb3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb3.setText("3");
        tb3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb3StateChanged(evt);
            }
        });
        tb3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb3MouseClicked(evt);
            }
        });
        tb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb3ActionPerformed(evt);
            }
        });

        tb4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb4.setText("4");
        tb4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb4StateChanged(evt);
            }
        });
        tb4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb4MouseClicked(evt);
            }
        });
        tb4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb4ActionPerformed(evt);
            }
        });

        tb7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb7.setText("7");
        tb7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb7StateChanged(evt);
            }
        });
        tb7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb7MouseClicked(evt);
            }
        });
        tb7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb7ActionPerformed(evt);
            }
        });

        tb8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb8.setText("8");
        tb8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb8StateChanged(evt);
            }
        });
        tb8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb8MouseClicked(evt);
            }
        });
        tb8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb8ActionPerformed(evt);
            }
        });

        tb11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb11.setText("11");
        tb11.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb11StateChanged(evt);
            }
        });
        tb11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb11MouseClicked(evt);
            }
        });
        tb11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb11ActionPerformed(evt);
            }
        });

        tb12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb12.setText("12");
        tb12.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb12StateChanged(evt);
            }
        });
        tb12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb12MouseClicked(evt);
            }
        });
        tb12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb12ActionPerformed(evt);
            }
        });

        tb15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb15.setText("15");
        tb15.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb15StateChanged(evt);
            }
        });
        tb15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb15MouseClicked(evt);
            }
        });
        tb15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb15ActionPerformed(evt);
            }
        });

        tb16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb16.setText("16");
        tb16.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb16StateChanged(evt);
            }
        });
        tb16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb16MouseClicked(evt);
            }
        });
        tb16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb16ActionPerformed(evt);
            }
        });

        tb19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb19.setText("19");
        tb19.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb19StateChanged(evt);
            }
        });
        tb19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb19MouseClicked(evt);
            }
        });
        tb19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb19ActionPerformed(evt);
            }
        });

        tb20.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb20.setText("20");
        tb20.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb20StateChanged(evt);
            }
        });
        tb20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb20MouseClicked(evt);
            }
        });
        tb20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb20ActionPerformed(evt);
            }
        });

        tb23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb23.setText("23");
        tb23.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb23StateChanged(evt);
            }
        });
        tb23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb23MouseClicked(evt);
            }
        });
        tb23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb23ActionPerformed(evt);
            }
        });

        tb24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb24.setText("24");
        tb24.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb24StateChanged(evt);
            }
        });
        tb24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb24MouseClicked(evt);
            }
        });
        tb24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb24ActionPerformed(evt);
            }
        });

        tb27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb27.setText("27");
        tb27.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb27StateChanged(evt);
            }
        });
        tb27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb27MouseClicked(evt);
            }
        });
        tb27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb27ActionPerformed(evt);
            }
        });

        tb28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb28.setText("28");
        tb28.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb28StateChanged(evt);
            }
        });
        tb28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb28MouseClicked(evt);
            }
        });
        tb28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb28ActionPerformed(evt);
            }
        });

        tb31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb31.setText("31");
        tb31.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb31StateChanged(evt);
            }
        });
        tb31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb31MouseClicked(evt);
            }
        });
        tb31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb31ActionPerformed(evt);
            }
        });

        tb32.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb32.setText("32");
        tb32.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb32StateChanged(evt);
            }
        });
        tb32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb32MouseClicked(evt);
            }
        });
        tb32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb32ActionPerformed(evt);
            }
        });

        tb39.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb39.setText("39");
        tb39.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb39StateChanged(evt);
            }
        });
        tb39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb39MouseClicked(evt);
            }
        });
        tb39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb39ActionPerformed(evt);
            }
        });

        tb40.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb40.setText("40");
        tb40.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb40StateChanged(evt);
            }
        });
        tb40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb40MouseClicked(evt);
            }
        });
        tb40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb40ActionPerformed(evt);
            }
        });

        tb35.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb35.setText("35");
        tb35.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb35StateChanged(evt);
            }
        });
        tb35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb35MouseClicked(evt);
            }
        });
        tb35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb35ActionPerformed(evt);
            }
        });

        tb36.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb36.setText("36");
        tb36.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb36StateChanged(evt);
            }
        });
        tb36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb36MouseClicked(evt);
            }
        });
        tb36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb36ActionPerformed(evt);
            }
        });

        tb37.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb37.setText("37");
        tb37.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb37StateChanged(evt);
            }
        });
        tb37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb37MouseClicked(evt);
            }
        });
        tb37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb37ActionPerformed(evt);
            }
        });

        tb38.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tb38.setText("38");
        tb38.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb38StateChanged(evt);
            }
        });
        tb38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb38MouseClicked(evt);
            }
        });
        tb38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb38ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(tb5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb6))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(tb1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb2)))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(tb13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb14))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(tb9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tb10)))
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(tb21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb22))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(tb17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb18)))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(tb29)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb30))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(tb25)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb26)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tb33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tb34)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tb23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tb24))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(tb31)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb32))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(tb27)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb28)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tb39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tb40))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tb7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tb8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(tb15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb16))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(tb11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb12)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tb19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tb20))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tb3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tb4))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(tb37)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb38))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(tb35)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tb36)))))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tb1, tb10, tb2, tb5, tb6, tb9});

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tb11, tb3, tb4, tb7});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb1)
                    .addComponent(tb2))
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb5)
                    .addComponent(tb6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb9)
                    .addComponent(tb10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb13)
                    .addComponent(tb14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb17)
                    .addComponent(tb18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb21)
                    .addComponent(tb22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb25)
                    .addComponent(tb26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb29)
                    .addComponent(tb30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb33)
                    .addComponent(tb34)))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb3)
                    .addComponent(tb4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb7)
                    .addComponent(tb8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb11)
                    .addComponent(tb12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb15)
                    .addComponent(tb16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb19)
                    .addComponent(tb20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb23)
                    .addComponent(tb24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb27)
                    .addComponent(tb28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb31)
                    .addComponent(tb32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb36)
                    .addComponent(tb35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb37)
                    .addComponent(tb38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tb39)
                    .addComponent(tb40)))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {tb10, tb6});

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Mapa");

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jButton5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton5.setText("COMPRAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton7.setText("ELIMINAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton6.setText("LIMPAR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton8.setText("ALTERAR");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jButton5)
                .addGap(35, 35, 35)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addGap(59, 59, 59))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tete" }));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("DE:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("PARA:");

        jComboBox5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Niassa - 3600 Mzn", "Cabo Delgado - 3200 Mzn", "Nampula - 2900 Mzn", "Quelimane - 2500 Mzn", "Beira - 1600 Mzn", "Chimoio - 1000 Mzn", "Inhambane - 2800 Mzn", "Gaza - 2800 Mzn", "Maputo - 2800 Mzn" }));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("DATA:");

        jSpinner1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jSpinner2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));
        jSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner2StateChanged(evt);
            }
        });

        jSpinner3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSpinner3.setModel(new javax.swing.SpinnerNumberModel(2022, 2022, 2030, 1));
        jSpinner3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner3StateChanged(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("NOME:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("CONTACTO:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("CONTACTO ALT:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("BI/PASSAPORTE:");

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("COBRADOR:");

        jTextField10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("MATRICULA:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("CONTACTO:");

        jTextField11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("ID VIAGEM:");

        jTextField13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                                    .addComponent(jTextField7))))
                                        .addGap(29, 29, 29)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel24)
                                                .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField10)
                                            .addComponent(jTextField11)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 104, Short.MAX_VALUE))
                                            .addComponent(jTextField12)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSpinner2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(83, 83, 83)))
                .addContainerGap())
            .addComponent(jSeparator6)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel23)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel25)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22))
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField13)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("BILHETEIRA", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        eliminarFunc();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cadastrarFunc();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        alterarFunc();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        limparFunc();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void tb5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb5StateChanged
        // TODO add your handling code here:
        //jToggleButton1.setForeground(Color.red);
    }//GEN-LAST:event_tb5StateChanged

    private void tb5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb5MouseClicked
        // TODO add your handling code here:
        //jToggleButton1.setForeground(Color.green);
    }//GEN-LAST:event_tb5MouseClicked

    private void tb5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb5ActionPerformed
        // TODO add your handling code here:
        if (tb5.isSelected()) {
            tb5.setBackground(Color.green);
        } else {
            tb5.setBackground(Color.red);
        }
    }//GEN-LAST:event_tb5ActionPerformed

    private void tb6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb6StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb6StateChanged

    private void tb6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb6MouseClicked

    private void tb6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb6ActionPerformed

    private void tb1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb1StateChanged

    private void tb1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb1MouseClicked

    private void tb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb1ActionPerformed

    private void tb2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb2StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb2StateChanged

    private void tb2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb2MouseClicked

    private void tb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb2ActionPerformed

    private void tb9StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb9StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb9StateChanged

    private void tb9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb9MouseClicked

    private void tb9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb9ActionPerformed

    private void tb10StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb10StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb10StateChanged

    private void tb10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb10MouseClicked

    private void tb10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb10ActionPerformed

    private void tb13StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb13StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb13StateChanged

    private void tb13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb13MouseClicked

    private void tb13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb13ActionPerformed

    private void tb14StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb14StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb14StateChanged

    private void tb14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb14MouseClicked

    private void tb14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb14ActionPerformed

    private void tb29StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb29StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb29StateChanged

    private void tb29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb29MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb29MouseClicked

    private void tb29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb29ActionPerformed

    private void tb25StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb25StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb25StateChanged

    private void tb25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb25MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb25MouseClicked

    private void tb25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb25ActionPerformed

    private void tb21StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb21StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb21StateChanged

    private void tb21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb21MouseClicked

    private void tb21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb21ActionPerformed

    private void tb17StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb17StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb17StateChanged

    private void tb17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb17MouseClicked

    private void tb17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb17ActionPerformed

    private void tb18StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb18StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb18StateChanged

    private void tb18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb18MouseClicked

    private void tb18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb18ActionPerformed

    private void tb22StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb22StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb22StateChanged

    private void tb22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb22MouseClicked

    private void tb22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb22ActionPerformed

    private void tb26StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb26StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb26StateChanged

    private void tb26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb26MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb26MouseClicked

    private void tb26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb26ActionPerformed

    private void tb30StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb30StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb30StateChanged

    private void tb30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb30MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb30MouseClicked

    private void tb30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb30ActionPerformed

    private void tb33StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb33StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb33StateChanged

    private void tb33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb33MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb33MouseClicked

    private void tb33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb33ActionPerformed

    private void tb34StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb34StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb34StateChanged

    private void tb34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb34MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb34MouseClicked

    private void tb34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb34ActionPerformed

    private void tb3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb3StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb3StateChanged

    private void tb3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb3MouseClicked

    private void tb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb3ActionPerformed

    private void tb4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb4StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb4StateChanged

    private void tb4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb4MouseClicked

    private void tb4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb4ActionPerformed

    private void tb7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb7StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb7StateChanged

    private void tb7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb7MouseClicked

    private void tb7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb7ActionPerformed

    private void tb8StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb8StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb8StateChanged

    private void tb8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb8MouseClicked

    private void tb8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb8ActionPerformed

    private void tb11StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb11StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb11StateChanged

    private void tb11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb11MouseClicked

    private void tb11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb11ActionPerformed

    private void tb12StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb12StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb12StateChanged

    private void tb12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb12MouseClicked

    private void tb12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb12ActionPerformed

    private void tb15StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb15StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb15StateChanged

    private void tb15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb15MouseClicked

    private void tb15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb15ActionPerformed

    private void tb16StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb16StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb16StateChanged

    private void tb16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb16MouseClicked

    private void tb16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb16ActionPerformed

    private void tb19StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb19StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb19StateChanged

    private void tb19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb19MouseClicked

    private void tb19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb19ActionPerformed

    private void tb20StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb20StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb20StateChanged

    private void tb20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb20MouseClicked

    private void tb20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb20ActionPerformed

    private void tb23StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb23StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb23StateChanged

    private void tb23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb23MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb23MouseClicked

    private void tb23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb23ActionPerformed

    private void tb24StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb24StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb24StateChanged

    private void tb24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb24MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb24MouseClicked

    private void tb24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb24ActionPerformed

    private void tb27StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb27StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb27StateChanged

    private void tb27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb27MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb27MouseClicked

    private void tb27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb27ActionPerformed

    private void tb28StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb28StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb28StateChanged

    private void tb28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb28MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb28MouseClicked

    private void tb28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb28ActionPerformed

    private void tb31StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb31StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb31StateChanged

    private void tb31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb31MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb31MouseClicked

    private void tb31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb31ActionPerformed

    private void tb32StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb32StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb32StateChanged

    private void tb32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb32MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb32MouseClicked

    private void tb32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb32ActionPerformed

    private void tb39StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb39StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb39StateChanged

    private void tb39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb39MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb39MouseClicked

    private void tb39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb39ActionPerformed

    private void tb40StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb40StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb40StateChanged

    private void tb40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb40MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb40MouseClicked

    private void tb40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb40ActionPerformed

    private void tb35StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb35StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb35StateChanged

    private void tb35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb35MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb35MouseClicked

    private void tb35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb35ActionPerformed

    private void tb36StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb36StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb36StateChanged

    private void tb36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb36MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb36MouseClicked

    private void tb36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb36ActionPerformed

    private void tb37StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb37StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb37StateChanged

    private void tb37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb37MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb37MouseClicked

    private void tb37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb37ActionPerformed

    private void tb38StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb38StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tb38StateChanged

    private void tb38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb38MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb38MouseClicked

    private void tb38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tb38ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        comprarBilhete();
        //teste();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        limparBilhete();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        eliminarBilhete();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        // TODO add your handling code here:
        listarBilhete();
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        // TODO add your handling code here:
        listarBilhete();
    }//GEN-LAST:event_jSpinner1StateChanged

    private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner2StateChanged
        // TODO add your handling code here:
        listarBilhete();
    }//GEN-LAST:event_jSpinner2StateChanged

    private void jSpinner3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner3StateChanged
        // TODO add your handling code here:
        listarBilhete();
    }//GEN-LAST:event_jSpinner3StateChanged

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        alterarBilhete();
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToggleButton tb1;
    private javax.swing.JToggleButton tb10;
    private javax.swing.JToggleButton tb11;
    private javax.swing.JToggleButton tb12;
    private javax.swing.JToggleButton tb13;
    private javax.swing.JToggleButton tb14;
    private javax.swing.JToggleButton tb15;
    private javax.swing.JToggleButton tb16;
    private javax.swing.JToggleButton tb17;
    private javax.swing.JToggleButton tb18;
    private javax.swing.JToggleButton tb19;
    private javax.swing.JToggleButton tb2;
    private javax.swing.JToggleButton tb20;
    private javax.swing.JToggleButton tb21;
    private javax.swing.JToggleButton tb22;
    private javax.swing.JToggleButton tb23;
    private javax.swing.JToggleButton tb24;
    private javax.swing.JToggleButton tb25;
    private javax.swing.JToggleButton tb26;
    private javax.swing.JToggleButton tb27;
    private javax.swing.JToggleButton tb28;
    private javax.swing.JToggleButton tb29;
    private javax.swing.JToggleButton tb3;
    private javax.swing.JToggleButton tb30;
    private javax.swing.JToggleButton tb31;
    private javax.swing.JToggleButton tb32;
    private javax.swing.JToggleButton tb33;
    private javax.swing.JToggleButton tb34;
    private javax.swing.JToggleButton tb35;
    private javax.swing.JToggleButton tb36;
    private javax.swing.JToggleButton tb37;
    private javax.swing.JToggleButton tb38;
    private javax.swing.JToggleButton tb39;
    private javax.swing.JToggleButton tb4;
    private javax.swing.JToggleButton tb40;
    private javax.swing.JToggleButton tb5;
    private javax.swing.JToggleButton tb6;
    private javax.swing.JToggleButton tb7;
    private javax.swing.JToggleButton tb8;
    private javax.swing.JToggleButton tb9;
    // End of variables declaration//GEN-END:variables
}
