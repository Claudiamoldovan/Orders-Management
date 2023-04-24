package presentation;

import bll.ProdusBLL;
import connection.ConnectionFactory;
import model.Produs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdusePres extends JFrame  {
    private JPanel contentPane;
    private JTextField id;
    private JTextField produs;
    private JTextField cantitate;
    private JTextField pret;
    private JTable tabel;
    Connection conectiune= null;
    private ProdusBLL produsBLL=new ProdusBLL();


    public ProdusePres() {
        conectiune= ConnectionFactory.getConnection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 834, 464);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel idProdus = new JLabel("Id ");
        idProdus.setFont(new Font("Tahoma", Font.PLAIN, 18));
        idProdus.setBounds(10, 50, 60, 26);
        contentPane.add(idProdus);

        JLabel numeProdus = new JLabel("Produs ");
        numeProdus.setFont(new Font("Tahoma", Font.PLAIN, 18));
        numeProdus.setBounds(10, 100, 99, 26);
        contentPane.add(numeProdus);

        JLabel cantitateProdus = new JLabel("cantitate");
        cantitateProdus.setFont(new Font("Tahoma", Font.PLAIN, 18));
        cantitateProdus.setBounds(10, 150, 99, 26);
        contentPane.add(cantitateProdus);

        JLabel pretProdus = new JLabel("pret/buc.");
        pretProdus.setFont(new Font("Tahoma", Font.PLAIN, 18));
        pretProdus.setBounds(10, 200, 99, 26);
        contentPane.add(pretProdus);

        id = new JTextField(String.valueOf(produsBLL.maxId()));
        id.setFont(new Font("Tahoma", Font.PLAIN, 18));
        id.setBounds(119, 50, 183, 26);
        contentPane.add(id);
        id.setColumns(10);

        produs = new JTextField();
        produs.setFont(new Font("Tahoma", Font.PLAIN, 18));
        produs.setBounds(119, 94, 183, 26);
        contentPane.add(produs);
        produs.setColumns(10);

        cantitate = new JTextField();
        cantitate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        cantitate.setColumns(10);
        cantitate.setBounds(119, 150, 183, 26);
        contentPane.add(cantitate);

        pret = new JTextField();
        pret.setFont(new Font("Tahoma", Font.PLAIN, 18));
        pret.setColumns(10);
        pret.setBounds(119, 198, 183, 26);
        contentPane.add(pret);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(426, 45, 370, 342);
        contentPane.add(scrollPane);

        tabel = new JTable();
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    int rand=tabel.getSelectedRow();
                    String tableClick=(tabel.getModel().getValueAt(rand,0)).toString();
                    String s="Select * from produs where id="+tableClick+";";
                    PreparedStatement ps=conectiune.prepareStatement(s);
                    ResultSet result=ps.executeQuery();
                    while(result.next()){
                        id.setText(result.getString("id"));
                        produs.setText(result.getString("nume"));
                        cantitate.setText(result.getString("cantitate"));
                        pret.setText(result.getString("pret"));
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        scrollPane.setViewportView(tabel);

        JButton adaugare = new JButton("Adaugare");
        adaugare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Produs c=new Produs(produs.getText(),Integer.parseInt(cantitate.getText()),Float.parseFloat(pret.getText()));
                produsBLL.adaugareProdus(c);
                id.setText(String.valueOf(produsBLL.maxId()));
                produs.setText("");
                cantitate.setText("");
                pret.setText("");
                tabel=produsBLL.afisareProduse(tabel);
            }
        });
        adaugare.setFont(new Font("Tahoma", Font.PLAIN, 18));
        adaugare.setBounds(34, 259, 122, 37);
        contentPane.add(adaugare);

        JButton stergere = new JButton("Stergere");
        stergere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Produs c=new Produs(produs.getText(),Integer.parseInt(cantitate.getText()),Float.parseFloat(pret.getText()));
                produsBLL.stergereProdus(c,Integer.parseInt(id.getText()));
                id.setText(String.valueOf(produsBLL.maxId()));
                produs.setText("");
                cantitate.setText("");
                pret.setText("");
                tabel=produsBLL.afisareProduse(tabel);
            }
        });
        stergere.setFont(new Font("Tahoma", Font.PLAIN, 18));
        stergere.setBounds(204, 259, 122, 37);
        contentPane.add(stergere);

        JButton editare = new JButton("Editare");
        editare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Produs c=new Produs(produs.getText(),Integer.parseInt(cantitate.getText()),Float.parseFloat(pret.getText()));
                produsBLL.updateProdus(Integer.parseInt(id.getText()),c);
                id.setText(String.valueOf(produsBLL.maxId()));
                produs.setText("");
                cantitate.setText("");
                pret.setText("");
                tabel=produsBLL.afisareProduse(tabel);
            }
        });
        editare.setFont(new Font("Tahoma", Font.PLAIN, 18));
        editare.setBounds(34, 323, 122, 37);
        contentPane.add(editare);

        JButton afisare = new JButton("Afisare");
        afisare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                tabel=produsBLL.afisareProduse(tabel);
            }
        });
        afisare.setFont(new Font("Tahoma", Font.PLAIN, 18));
        afisare.setBounds(204, 323, 122, 37);
        contentPane.add(afisare);

        JButton inapoi = new JButton("Inapoi");
        inapoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View f=new View();
                f.show();
                dispose();

            }
        });
        inapoi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        inapoi.setBounds(137, 386, 97, 31);
        contentPane.add(inapoi);
    }
}
