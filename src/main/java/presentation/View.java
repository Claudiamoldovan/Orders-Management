package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private JPanel contentPane;
    private JButton client;
    private JButton produse;
    private JButton comenzi;

    public View() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 522, 438);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

       client = new JButton("Operatii client");
        client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientPres f=new ClientPres();
                f.show();
                dispose();
            }
        });
        client.setFont(new Font("Tahoma", Font.PLAIN, 18));
        client.setBounds(174, 125, 166, 40);
        contentPane.add(client);

        produse = new JButton("Operatii produse");
        produse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProdusePres f=new ProdusePres();
                f.show();
                dispose();
            }
        });
        produse.setFont(new Font("Tahoma", Font.PLAIN, 18));
        produse.setBounds(168, 185, 177, 40);
        contentPane.add(produse);

         comenzi = new JButton("Comenzi");
        comenzi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ComenziPres f=new ComenziPres();
                f.show();
                dispose();
            }
        });
        comenzi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        comenzi.setBounds(174, 248, 166, 40);
        contentPane.add(comenzi);

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        exit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        exit.setBounds(399, 351, 99, 40);
        contentPane.add(exit);

        JLabel lblNewLabel = new JLabel("Bine ati venit!");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblNewLabel.setBounds(159, 50, 186, 37);
        contentPane.add(lblNewLabel);
    }



}
