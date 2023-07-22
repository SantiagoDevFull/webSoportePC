package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Iproveedor;
import Modelo.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProveedorDAO implements Iproveedor {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public ArrayList<Proveedor> ListarProveedor() {

        ArrayList<Proveedor> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT p.idProv,p.rucProv,p.nomProv,p.direProv,p.telProv,p.estadoProv,\n"
                + "(SELECT count(*) FROM producto pro WHERE pro.idProv=p.idProv) numProductos\n"
                + "FROM proveedor p \n"
                + "order by 1;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Proveedor obj = new Proveedor();
                obj.setIdProv(rs.getInt(1));
                obj.setRucProv(rs.getString(2));
                obj.setNomProv(rs.getString(3));
                obj.setDireProv(rs.getString(4));
                obj.setTelProv(rs.getString(5));
                obj.setEstadoProv(rs.getString(6));
                obj.setNumProductos(rs.getInt(7));
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
    public int AgregarProveedor(Proveedor obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO proveedor(rucProv,nomProv,direProv,telProv,estadoProv) VALUES (?,?,?,?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, obj.getRucProv());
            st.setString(2, obj.getNomProv());
            st.setString(3, obj.getDireProv());
            st.setString(4, obj.getTelProv());
            st.setString(5, obj.getEstadoProv());
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
    public int ActualizarProveedor(Proveedor obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE proveedor SET rucProv=?,nomProv=?,direProv=?,telProv=?,estadoProv=? WHERE idProv=?";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, obj.getRucProv());
            st.setString(2, obj.getNomProv());
            st.setString(3, obj.getDireProv());
            st.setString(4, obj.getTelProv());
            st.setString(5, obj.getEstadoProv());
            st.setInt(6, obj.getIdProv());
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
    public int EliminarProveedor(int id) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "DELETE FROM proveedor WHERE idProv=?;";

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
