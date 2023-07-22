package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Icategoria;
import Modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriaDAO implements Icategoria {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public ArrayList<Categoria> ListarCategoria() {

        ArrayList<Categoria> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT c.idCat,c.nomCat,c.estadoCat,\n"
                + "(SELECT count(*) FROM producto p WHERE p.idCat=c.idCat) as numPro \n"
                + "FROM categoria c \n"
                + "order by 1;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCat(rs.getInt(1));
                c.setNomCat(rs.getString(2));
                c.setEstadoCat(rs.getString(3));
                c.setNumProductos(rs.getInt(4));
                lista.add(c);
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
    public int AgregarCategoria(Categoria c) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO categoria(nomCat,estadoCat) VALUES (?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, c.getNomCat());
            st.setString(2, c.getEstadoCat());
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
    public int ActualizarCategoria(Categoria c) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE categoria SET nomCat=?,estadoCat=? WHERE idCat=?";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, c.getNomCat());
            st.setString(2, c.getEstadoCat());
            st.setInt(3, c.getIdCat());
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
    public int EliminarCategoria(int id) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "DELETE FROM categoria WHERE idCat=?;";

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
