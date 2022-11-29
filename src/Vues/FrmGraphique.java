package Vues;

import Controlers.CtrlGraphique;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class FrmGraphique extends JFrame{
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlGraph3;
    private JPanel pnlGraph4;
    private JPanel pnlRoot;
    CtrlGraphique ctrlGraphique;

    public FrmGraphique() throws SQLException, ClassNotFoundException {
        this.setTitle("Devoir graphique");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);

                //  ConnexionBDD cnx = new ConnexionBDD();
                ctrlGraphique = new CtrlGraphique();
                //graphique 1 moyenne des salaires par âge
                DefaultCategoryDataset donnees = new DefaultCategoryDataset();
                int salaire;
                String ageEmp;
                //remplissage du dataset
                for (String valeur : ctrlGraphique.GetDatasGrphique1().keySet()) {
                    salaire = ctrlGraphique.GetDatasGrphique1().get(valeur);
                    ageEmp = valeur;
                    donnees.setValue(salaire, "", ageEmp);
                }

                JFreeChart chart1 = ChartFactory.createLineChart(
                        "Moyenne des salaires par âge",
                        "Age",
                        "Salaire",
                        donnees,
                        PlotOrientation.VERTICAL, false, true, false);

                ChartFrame demograph = new ChartFrame("", chart1);

                pnlGraph1.add(demograph);

                pnlGraph1.validate();

                //graphique 2
                donnees = new DefaultCategoryDataset();

                for (String valeur : ctrlGraphique.GetDatasGraphique2().keySet())
                {
                    for (int i = 0;i< ctrlGraphique.GetDatasGraphique2().get(valeur).size();i+=2)
                    {
                        donnees.setValue
                                (
                                 Double.parseDouble(ctrlGraphique.GetDatasGraphique2().get(valeur).get(i+1)),
                                 valeur.toString(),
                                 ctrlGraphique.GetDatasGraphique2().get(valeur).get(i).toString()
                                );

                    }
                }

                chart1 = ChartFactory.createBarChart(
                        "Montant des ventes par magasin",
                        "Magasin",
                        "Montant des ventes ",
                        donnees,
                        PlotOrientation.VERTICAL,true,true,false);
                demograph = new ChartFrame("",chart1);

                pnlGraph2.add(demograph);
                pnlGraph2.validate();


            }
        });
    }}