package Controlers;

import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlGraphique
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlGraphique() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> GetAllActions()
    {
        ArrayList<String> lesNomsDesActions = new ArrayList<>();
        return lesNomsDesActions;
    }

    public HashMap<String,Integer> GetDatasGrphique1()
    {
        HashMap<String, Integer> datas = new HashMap();
        try{
            ps = cnx.prepareStatement("select ageEmp, AVG(salaireEmp)"+
            "from employe"+
                    "group by employe.ageEmp");
            rs = ps.executeQuery();
            while (rs.next())
            {
                datas.put(String.valueOf(rs.getInt("ageEmp")), rs.getInt("salaireEmp"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, e);
        }
        return datas;
    }

    public HashMap<String, ArrayList<String>> GetDatasGraphique2()
    {
        HashMap<String, ArrayList<String>> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("select vente.nomSemestre, vente.nomMagasin, count(*) as nb\n"+
                    "from vente\n"+
                    "inner join montant on vente.idVente = montant.nomMagasin "+
                    "INNER JOIN vente on montant.nomSemestre = vente.idVente"+
                    "group by vente.nomMagasin, vente.nomSemestre");
            rs = ps.executeQuery();
            while (rs.next())
            {
                if(!datas.containsKey(rs.getString("nomSemestre")))
                {
                    ArrayList<String> lesMontants = new ArrayList<>();
                    lesMontants.add(rs.getString("nomMagasin"));
                    lesMontants.add(rs.getString("nb"));
                    datas.put(rs.getString("nomSemestre"), lesMontants);
                }
                else
                {
                    datas.get(rs.getString("nomSemestre")).add(rs.getString("nomMagasin"));
                    datas.get(rs.getString("nomSemestre")).add(rs.getString("nb"));

                }

            }
            ps.close();
            rs.close();

        } catch (SQLException e) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, e);
        }
        return datas;
    }


}
