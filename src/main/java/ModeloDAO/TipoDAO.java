package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Itipo;
import Modelo.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TipoDAO implements Itipo {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public ArrayList<Tipo> ListarTipo() {

        ArrayList<Tipo> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT t.idTipo,t.nomTipo,t.estadoTipo,\n"
                + "(SELECT count(*) FROM solicitud s WHERE s.idTipo=t.idTipo) as numSoli\n"
                + "FROM tipo t \n"
                + "order by 1;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Tipo obj = new Tipo();
                obj.setIdTipo(rs.getInt(1));
                obj.setNomTipo(rs.getString(2));
                obj.setEstadoTipo(rs.getString(3));
                obj.setNumSolcitudes(rs.getInt(4));
                lista.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public int AgregarTipo(Tipo obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO tipo(nomTipo,estadoTipo) VALUES (?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, obj.getNomTipo());
            st.setString(2, obj.getEstadoTipo());
            res = st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public int ActualizarTipo(Tipo obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE tipo SET nomTipo=?,estadoTipo=? WHERE idTipo=?;";

        try {
            st = cn.prepareStatement(consulta);

            st.setString(1, obj.getNomTipo());
            st.setString(2, obj.getEstadoTipo());
            st.setInt(3, obj.getIdTipo());

            res = st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return res;

    }

    @Override
    public int EliminarTipo(int id) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "DELETE FROM tipo WHERE idTipo=?";

        try {
            st = cn.prepareStatement(consulta);

            st.setInt(1, id);

            res = st.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

}
