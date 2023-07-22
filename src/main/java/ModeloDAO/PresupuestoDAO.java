package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Ipresupuesto;
import Modelo.Cliente;
import Modelo.Fdetalle;
import Modelo.Pdetalle;
import Modelo.Presupuesto;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PresupuestoDAO implements Ipresupuesto {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public int ProcesarPresupuesto(Presupuesto obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO presupuesto(idCli,fecha,total) VALUES (?,?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, obj.getCliente().getIdCli());
            st.setString(2, obj.getFecha());
            st.setDouble(3, obj.getTotal());

            if (st.executeUpdate() > 0) {

                for (Pdetalle x : obj.getDetalle()) {
                    String consulta2 = "INSERT INTO pdetalle(idPre,idPro,cantidad,precio) VALUES (?,?,?,?);";
                    st = cn.prepareStatement(consulta2);
                    st.setInt(1, obj.getIdPre());
                    st.setInt(2, x.getProducto().getIdPro());
                    st.setInt(3, x.getCantidad());
                    st.setDouble(4, x.getPrecio());
                    res += st.executeUpdate();
                }

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
        return res;

    }

    @Override
    public ArrayList<Presupuesto> ListarPresupuesto(int id) {

        ArrayList<Presupuesto> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT p.idPre,c.numCli,c.nomCli,p.fecha,p.total FROM presupuesto p\n"
                + "INNER JOIN cliente c ON (p.idCli=c.idCli) WHERE p.idCli=? ORDER BY p.fecha DESC";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, id);
            rs = st.executeQuery();

            while (rs.next()) {
                Presupuesto obj = new Presupuesto();
                obj.setIdPre(rs.getInt(1));

                Cliente c = new Cliente();
                c.setNumCli(rs.getString(2));
                c.setNomCli(rs.getString(3));

                obj.setFecha(rs.getString(4));
                obj.setTotal(rs.getDouble(5));

                obj.setCliente(c);

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
    public int RetornarCodigoPresupuesto() {

        int res = -1;

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT AUTO_INCREMENT\n"
                + "FROM  INFORMATION_SCHEMA.TABLES\n"
                + "WHERE TABLE_SCHEMA = 'bdsoportepc'\n"
                + "AND   TABLE_NAME   = 'presupuesto';";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            if (rs.next()) {
                res = rs.getInt(1);
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

        return res;

    }

    @Override
    public ArrayList<Pdetalle> ListarDetallePresupuesto(int idPre) {

        ArrayList<Pdetalle> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT d.cantidad,p.nomPro,d.precio FROM pdetalle d \n"
                + "INNER JOIN producto p ON (d.idPro=p.idPro) WHERE d.idPre=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, idPre);
            rs = st.executeQuery();

            while (rs.next()) {

                Pdetalle obj = new Pdetalle();
                obj.setCantidad(rs.getInt(1));

                Producto p = new Producto();
                p.setNomPro(rs.getString(2));

                obj.setPrecio(rs.getDouble(3));

                obj.setProducto(p);

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

}
