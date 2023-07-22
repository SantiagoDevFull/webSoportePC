package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Iboleta;
import Modelo.Bdetalle;
import Modelo.Boleta;
import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoletaDAO implements Iboleta {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public int RetornarCodigoBoleta() {

        int res = -1;

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT AUTO_INCREMENT\n"
                + "FROM  INFORMATION_SCHEMA.TABLES\n"
                + "WHERE TABLE_SCHEMA = 'bdsoportepc'\n"
                + "AND   TABLE_NAME   = 'boleta';";

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
        String consulta = "SELECT idCli,numCli,nomCli,correoCli,estadoCli FROM cliente WHERE estadoCli='Activo' AND docCli='DNI';";

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
    public int ProcesarBoleta(Boleta boleta) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO boleta(idUsu,idCli,fechaBol,totalBol) VALUES (?,?,?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, boleta.getUsuario().getIdUsu());
            st.setInt(2, boleta.getCliente().getIdCli());
            st.setString(3, boleta.getFechaBol());
            st.setDouble(4, boleta.getTotalBol());

            if (st.executeUpdate() > 0) {

                for (Bdetalle x : boleta.getDetalle()) {
                    String consulta2 = "INSERT INTO bdetalle(idBol,idPro,cantidad,precio) VALUES (?,?,?,?);";
                    st = cn.prepareStatement(consulta2);
                    st.setInt(1, boleta.getIdBol());
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
    public ArrayList<Boleta> ListarBoleta() {

        ArrayList<Boleta> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT b.idBol,u.nomUsu,c.numCli,c.nomCli,b.fechaBol,b.totalBol FROM boleta b \n"
                + "INNER JOIN usuario u ON (b.idUsu=u.idUsu) \n"
                + "INNER JOIN cliente c ON (b.idCli=c.idCli) \n"
                + "ORDER BY 1;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Boleta b = new Boleta();
                b.setIdBol(rs.getInt(1));

                Usuario u = new Usuario();
                u.setNomUsu(rs.getString(2));

                Cliente c = new Cliente();
                c.setNumCli(rs.getString(3));
                c.setNomCli(rs.getString(4));

                b.setFechaBol(rs.getString(5));
                b.setTotalBol(rs.getDouble(6));
                b.setUsuario(u);
                b.setCliente(c);

                lista.add(b);
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
    public ArrayList<Bdetalle> ListarDetalle(int id) {

        ArrayList<Bdetalle> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT d.cantidad,p.nomPro,d.precio FROM bdetalle d \n"
                + "INNER JOIN producto p ON (d.idPro=p.idPro) WHERE d.idBol=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1,id);
            rs=st.executeQuery();
            
            while(rs.next()){
                Bdetalle obj=new Bdetalle();
                obj.setCantidad(rs.getInt(1));
                
                Producto p=new Producto();
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
