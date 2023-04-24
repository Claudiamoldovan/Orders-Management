package presentation;

import bll.ClientBLL;
import bll.ComandaBLL;
import bll.ComandaProdusBLL;
import bll.ProdusBLL;
import connection.ConnectionFactory;
import model.Client;
import model.Comanda;
import model.ComandaProdus;
import model.Produs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComenziPres extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private JTable tabel;
    private Connection conectiune = null;
    private float total=0;
    private String Afisarebon="";
    private Afisare istoric=new Afisare("Istoric bonuri.txt");
    private ComandaBLL comandaBLL=new ComandaBLL();
    private ComandaProdusBLL comandaProdusBLL=new ComandaProdusBLL();

    public ComenziPres() {
        conectiune = ConnectionFactory.getConnection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 414, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        DefaultComboBoxModel listClient = new DefaultComboBoxModel();
        JComboBox clienti = new JComboBox(listClient);
        String string = "SELECT * FROM client order by id";
        PreparedStatement ps = null;
        try {
            ps= conectiune.prepareStatement(string);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                String c = result.getString("id") + " - " + result.getString("nume") + ", " + result.getString("adresa") + ", " + result.getString("mail");
                listClient.addElement(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        clienti.setFont(new Font("Tahoma", Font.PLAIN, 18));
        clienti.setBounds(64, 90, 275, 42);
        contentPane.add(clienti);

        DefaultComboBoxModel listProd = new DefaultComboBoxModel();
        JComboBox produse = new JComboBox(listProd);
        string = "SELECT * FROM produs order by id";
        ps = null;
        try {
            ps = conectiune.prepareStatement(string);
            ResultSet result =ps.executeQuery();
            while (result.next()) {
                String c = result.getString("id") + " - " + result.getString("nume") + ", " + result.getString("pret") + " lei/bucata ";
                listProd.addElement(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        produse.setFont(new Font("Tahoma", Font.PLAIN, 18));
        produse.setBounds(64, 163, 275, 42);
        contentPane.add(produse);

        JLabel cantitate = new JLabel("Cantitate");
        cantitate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        cantitate.setBounds(69, 247, 117, 32);
        contentPane.add(cantitate);

        textField = new JTextField();
        textField.setBounds(209, 252, 130, 29);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton adauga = new JButton("Adauga produs");
        adauga.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                        int cantitate = Integer.parseInt(textField.getText()); //cantitatea
                        ProdusBLL produsBLL=new ProdusBLL();
                        int idC = comandaBLL.findMaxId(); //idComanda
                        String s1 = (String) produse.getSelectedItem();
                        String prod = "";
                        for (char c : s1.toCharArray()) {
                            if (c != ' ')
                                prod += c;
                            else
                                break;
                        }
                        int idP = Integer.parseInt(prod);
                        Produs p = produsBLL.gasireID(idP);
                        if (p.getCantitate() - cantitate >= 0) {
                            Afisarebon+= p.getNume() + " " + cantitate + " " + cantitate * p.getPret() + " lei;\n";
                            ComandaProdus nou = new ComandaProdus(idC, idP, cantitate);
                            comandaProdusBLL.adaugareComanda(nou);
                            p.setCantitate(p.getCantitate() - cantitate);
                            produsBLL.updateProdus(idP, p);
                            tabel=comandaProdusBLL.afisareIdComanda(tabel,idC);
                            total += cantitate * p.getPret();
                        } else
                            JOptionPane.showMessageDialog(null, "Cantitatea produsului ales: " + p.getCantitate());

            }
        });
        adauga.setFont(new Font("Tahoma", Font.PLAIN, 18));
        adauga.setBounds(129, 308, 164, 42);
        contentPane.add(adauga);

        JLabel comanda = new JLabel("Comanda!");
        comanda.setFont(new Font("Tahoma", Font.PLAIN, 30));
        comanda.setBounds(129, 27, 158, 48);
        contentPane.add(comanda);

        JButton finalizare = new JButton("Finalizare");
        finalizare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s1= (String) clienti.getSelectedItem();
                String client="";
                for(char c:s1.toCharArray()){
                    if(c!=' ')
                        client+=c;
                    else
                        break;
                }
                Client c=ClientBLL.gasireID(Integer.parseInt(client));
                Afisarebon+="Total de plata: "+total+" lei\n";
                Afisare bon=new Afisare("Bon comanda "+comandaBLL.findMaxId()+" "+c.getNume()+".txt");
                bon.writeToFile(Afisarebon);
                Comanda comandaNoua=new Comanda(Integer.parseInt(client),c.getNume(),total);
                comandaBLL.adaugareComanda(comandaNoua);
                istoric.writeToFile(afisareIstoric());
                Afisarebon="";
                total=0;
                tabel.setModel(new DefaultTableModel());
            }
        });
        finalizare.setFont(new Font("Tahoma", Font.PLAIN, 18));
        finalizare.setBounds(129, 469, 164, 42);
        contentPane.add(finalizare);

        JButton inapoi = new JButton("Inapoi");
        inapoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View f = new View();
                f.show();
                dispose();
            }
        });
        inapoi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        inapoi.setBounds(129, 532, 164, 42);
        contentPane.add(inapoi);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(31, 358, 359, 99);
        contentPane.add(scrollPane);
        tabel = new JTable();
        scrollPane.setViewportView(tabel);
    }

    public String afisareIstoric(){
        String istoric="";
        try{
           String s="Select * from comanda";
            PreparedStatement ps=conectiune.prepareStatement(s);
            ResultSet result=ps.executeQuery();
            while(result.next()){
                istoric+=result.getString("id")+" "+result.getString("numeClient")+" "+result.getString("suma")+"\n";
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return istoric;
    }
}
