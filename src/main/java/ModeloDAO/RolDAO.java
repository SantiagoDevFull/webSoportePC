package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Irol;
import Modelo.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RolDAO implements Irol {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public ArrayList<Rol> ListarRol() {

        ArrayList<Rol> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT r.idRol,r.nomRol,r.estadoRol,\n"
                + "(SELECT count(*) FROM usuario u WHERE u.idRol=r.idRol) \n"
                + "FROM rol r\n"
                + "ORDER BY 1;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Rol obj = new Rol();
                obj.setIdRol(rs.getInt(1));
                obj.setNomRol(rs.getString(2));
                obj.setEstadoRol(rs.getString(3));
                obj.setNumUsuarios(rs.getInt(4));
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
    public int AgregarRol(Rol obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO rol(nomRol,estadoRol) VALUES(?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, obj.getNomRol());
            st.setString(2, obj.getEstadoRol());
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
    public int ActualizarRol(Rol obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE rol SET nomRol=?,estadoRol=? WHERE idRol=?";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, obj.getNomRol());
            st.setString(2, obj.getEstadoRol());
            st.setInt(3, obj.getIdRol());
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
    public int EliminarRol(int id) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "DELETE FROM rol WHERE idRol=?";

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
