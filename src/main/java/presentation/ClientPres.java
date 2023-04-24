package presentation;

import bll.ClientBLL;
import connection.ConnectionFactory;
import model.Client;

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

public class ClientPres extends JFrame {

    private JPanel contentPane;
    private JTextField idC;
    private JTextField numeC;
    private JTextField adresaC;
    private JTextField mailC;
    private JTable table;
    private Connection connection = null;
    private ClientBLL clientBLL=new ClientBLL();

    public ClientPres() {
        connection = ConnectionFactory.getConnection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 834, 464);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel idClient = new JLabel("Id ");
        idClient.setFont(new Font("Tahoma", Font.PLAIN, 18));
        idClient.setBounds(10, 50, 60, 26);
        contentPane.add(idClient);

        JLabel numeClient = new JLabel("Nume ");
        numeClient.setFont(new Font("Tahoma", Font.PLAIN, 18));
        numeClient.setBounds(10, 100, 60, 26);
        contentPane.add(numeClient);

        JLabel adresaClient = new JLabel("adresa");
        adresaClient.setFont(new Font("Tahoma", Font.PLAIN, 18));
        adresaClient.setBounds(10, 150, 60, 26);
        contentPane.add(adresaClient);

        JLabel mailClient = new JLabel("mail");
        mailClient.setFont(new Font("Tahoma", Font.PLAIN, 18));
        mailClient.setBounds(10, 200, 60, 26);
        contentPane.add(mailClient);

        idC = new JTextField(String.valueOf(ClientBLL.maxId()));
        idC.setFont(new Font("Tahoma", Font.PLAIN, 18));
        idC.setBounds(119, 50, 183, 26);
        contentPane.add(idC);
        idC.setColumns(10);

        numeC = new JTextField("");
        numeC.setFont(new Font("Tahoma", Font.PLAIN, 18));
        numeC.setBounds(119, 100, 183, 26);
        contentPane.add(numeC);
        numeC.setColumns(10);

        adresaC = new JTextField("");
        adresaC.setFont(new Font("Tahoma", Font.PLAIN, 18));
        adresaC.setColumns(10);
        adresaC.setBounds(119, 150, 183, 26);
        contentPane.add(adresaC);

        mailC = new JTextField("");
        mailC.setFont(new Font("Tahoma", Font.PLAIN, 18));
        mailC.setColumns(10);
        mailC.setBounds(119, 200, 183, 26);
        contentPane.add(mailC);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(426, 45, 370, 342);
        contentPane.add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int rand = table.getSelectedRow();
                    String tableClick = (table.getModel().getValueAt(rand, 0)).toString();
                    String s = "Select * from client where id=" + tableClick + ";";
                    PreparedStatement str = connection.prepareStatement(s);
                    ResultSet rs = str.executeQuery();
                    while (rs.next()) {
                        idC.setText(rs.getString("id"));
                        numeC.setText(rs.getString("nume"));
                        adresaC.setText(rs.getString("adresa"));
                        mailC.setText(rs.getString("mail"));
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        scrollPane.setViewportView(table);

        JButton adaugare = new JButton("Adaugare");
        adaugare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client c = new Client(numeC.getText(), adresaC.getText(), mailC.getText());
                clientBLL.adaugareClient(c);
                idC.setText(String.valueOf(clientBLL.maxId()));
                numeC.setText("");
                adresaC.setText("");
                mailC.setText("");
                table = clientBLL.afisareClient(table);
            }
        });
        adaugare.setFont(new Font("Tahoma", Font.PLAIN, 18));
        adaugare.setBounds(34, 259, 122, 37);
        contentPane.add(adaugare);

        JButton stergere = new JButton("Stergere");
        stergere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client c = new Client(numeC.getText(), adresaC.getText(), mailC.getText());
                clientBLL.stergereClient(c, Integer.parseInt(idC.getText()));
                idC.setText(String.valueOf(clientBLL.maxId()));
                numeC.setText("");
                adresaC.setText("");
                mailC.setText("");
                table = clientBLL.afisareClient(table);
            }
        });
        stergere.setFont(new Font("Tahoma", Font.PLAIN, 18));
        stergere.setBounds(204, 259, 122, 37);
        contentPane.add(stergere);

        JButton editare = new JButton("Editare");
        editare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idC.getText());
                Client c = new Client(numeC.getText(), adresaC.getText(), mailC.getText());
                clientBLL.updateClient(id, c);
                idC.setText(String.valueOf(clientBLL.maxId()));
                numeC.setText("");
                adresaC.setText("");
                mailC.setText("");
                table = clientBLL.afisareClient(table);
            }
        });
        editare.setFont(new Font("Tahoma", Font.PLAIN, 18));
        editare.setBounds(34, 323, 122, 37);
        contentPane.add(editare);

        JButton afisare = new JButton("Afisare");
        afisare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table = clientBLL.afisareClient(table);
            }
        });
        afisare.setFont(new Font("Tahoma", Font.PLAIN, 18));
        afisare.setBounds(204, 323, 122, 37);
        contentPane.add(afisare);

        JButton inapoi = new JButton("Inapoi");
        inapoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View f = new View();
                f.show();
                dispose();

            }
        });
        inapoi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        inapoi.setBounds(137, 386, 97, 31);
        contentPane.add(inapoi);
    }

}



