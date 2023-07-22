package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Ifactura;
import Modelo.Cliente;
import Modelo.Factura;
import Modelo.Fdetalle;
import Modelo.Producto;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacturaDAO implements Ifactura {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public int RetornarCodigoFactura() {

        int res = -1;

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT AUTO_INCREMENT\n"
                + "FROM  INFORMATION_SCHEMA.TABLES\n"
                + "WHERE TABLE_SCHEMA = 'bdsoportepc'\n"
                + "AND   TABLE_NAME   = 'factura';";

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
    public ArrayList<Cliente> ListarClientesActivos() {

        ArrayList<Cliente> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT idCli,numCli,nomCli,correoCli,estadoCli FROM cliente WHERE estadoCli='Activo' AND docCli='RUC';";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Cliente obj = new Cliente();
                obj.setIdCli(rs.getInt(1));
                obj.setNumCli(rs.getString(2));
                obj.setNomCli(rs.getString(3));
                obj.setCorreoCli(rs.getString(4));
                obj.setEstadoCli(rs.getString(5));
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
    public ArrayList<Producto> ListarProductosActivos() {

        ArrayList<Producto> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT idPro,nomPro,stockPro,precioPro,estadoPro FROM producto WHERE estadoPro='Activo' AND stockPro>0;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Producto obj = new Producto();
                obj.setIdPro(rs.getInt(1));
                obj.setNomPro(rs.getString(2));
                obj.setStockPro(rs.getInt(3));
                obj.setPrecioPro(rs.getDouble(4));
                obj.setEstadoPro(rs.getString(5));
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
    public void DisminuirStock(int codigo, int cantidad) {

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE producto SET stockPro=stockPro-? WHERE idPro=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, cantidad);
            st.setInt(2, codigo);
            st.execute();
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

    }

    @Override
    public void AumentarStock(int codigo, int cantidad) {
        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE producto SET stockPro=stockPro+? WHERE idPro=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, cantidad);
            st.setInt(2, codigo);
            st.execute();
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
    }

    @Override
    public int ProcesarFactura(Factura obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO factura(idUsu,idCli,fechaFact,totalFact,igvFact,netoFact) VALUES (?,?,?,?,?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, obj.getUsuario().getIdUsu());
            st.setInt(2, obj.getCliente().getIdCli());
            st.setString(3, obj.getFechaFact());
            st.setDouble(4, obj.getTotalFact());
            st.setDouble(5, obj.getIgvFact());
            st.setDouble(6, obj.getNetoFact());

            if (st.executeUpdate() > 0) {

                for (Fdetalle x : obj.getDetalle()) {
                    String consulta2 = "INSERT INTO fdetalle(idFac,idPro,cantidad,precio) VALUES (?,?,?,?);";
                    st = cn.prepareStatement(consulta2);
                    st.setInt(1, obj.getIdFact());
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
    public ArrayList<Factura> ListarFactura() {

        ArrayList<Factura> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT f.idFac,u.nomUsu,c.numCli,c.nomCli,f.fechaFact,f.totalFact,f.igvFact,f.netoFact FROM factura f \n"
                + "INNER JOIN usuario u ON (f.idUsu=u.idUsu) \n"
                + "INNER JOIN cliente c ON (f.idCli=c.idCli) \n"
                + "ORDER BY 1;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Factura f = new Factura();
                f.setIdFact(rs.getInt(1));

                Usuario u = new Usuario();
                u.setNomUsu(rs.getString(2));

                Cliente c = new Cliente();
                c.setNumCli(rs.getString(3));
                c.setNomCli(rs.getString(4));

                f.setFechaFact(rs.getString(5));
                f.setTotalFact(rs.getDouble(6));
                f.setIgvFact(rs.getDouble(7));
                f.setNetoFact(rs.getDouble(8));
                f.setUsuario(u);
                f.setCliente(c);

                lista.add(f);
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
    public ArrayList<Fdetalle> ListarDetalle(int id) {

        ArrayList<Fdetalle> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT f.cantidad,p.nomPro,f.precio FROM fdetalle f \n"
                + "INNER JOIN producto p ON (f.idPro=p.idPro) WHERE f.idFac=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, id);
            rs = st.executeQuery();

            while (rs.next()) {
                Fdetalle obj = new Fdetalle();
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
