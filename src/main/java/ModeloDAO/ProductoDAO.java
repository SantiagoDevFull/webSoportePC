package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Iproducto;
import Modelo.Categoria;
import Modelo.Producto;
import Modelo.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO implements Iproducto {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public ArrayList<Producto> ListarProducto() {

        ArrayList<Producto> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT p.idPro,p.nomPro,p.stockPro,p.precioPro,p.estadoPro,c.idCat,c.nomCat,pr.idProv,pr.nomProv,p.fotoPro FROM producto p \n"
                + "INNER JOIN categoria c ON (p.idCat=c.idCat) \n"
                + "INNER JOIN proveedor pr ON (p.idProv=pr.idProv) \n"
                + "ORDER BY 1";
        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdPro(rs.getInt(1));
                p.setNomPro(rs.getString(2));
                p.setStockPro(rs.getInt(3));
                p.setPrecioPro(rs.getDouble(4));
                p.setEstadoPro(rs.getString(5));

                Categoria c = new Categoria();
                c.setIdCat(rs.getInt(6));
                c.setNomCat(rs.getString(7));

                Proveedor prov = new Proveedor();
                prov.setIdProv(rs.getInt(8));
                prov.setNomProv(rs.getString(9));

                p.setFotoPro(rs.getString(10));

                p.setCategoria(c);
                p.setProveedor(prov);

                lista.add(p);
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
    public int AgregarProducto(Producto obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO producto(nomPro,stockPro,precioPro,estadoPro,idCat,idProv,fotoPro) VALUES (?,?,?,?,?,?,?);";
        try {
            st = cn.prepareStatement(consulta);

            st.setString(1, obj.getNomPro());
            st.setInt(2, obj.getStockPro());
            st.setDouble(3, obj.getPrecioPro());
            st.setString(4, obj.getEstadoPro());
            st.setInt(5, obj.getCategoria().getIdCat());
            st.setInt(6, obj.getProveedor().getIdProv());
            st.setString(7, obj.getFotoPro());

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
    public int ActualizarProducto(Producto obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE producto SET nomPro=?,stockPro=?,precioPro=?,estadoPro=?,idCat=?,idProv=?,fotoPro=? WHERE idPro=?;";
        try {
            st = cn.prepareStatement(consulta);

            st.setString(1, obj.getNomPro());
            st.setInt(2, obj.getStockPro());
            st.setDouble(3, obj.getPrecioPro());
            st.setString(4, obj.getEstadoPro());
            st.setInt(5, obj.getCategoria().getIdCat());
            st.setInt(6, obj.getProveedor().getIdProv());
            st.setString(7, obj.getFotoPro());
            st.setInt(8, obj.getIdPro());

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
    public int EliminarProducto(int id) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "DELETE FROM producto WHERE idPro=?;";

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

    @Override
    public ArrayList<Producto> ListarHardware() {

        ArrayList<Producto> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT p.idPro,p.nomPro,p.stockPro,p.precioPro,p.estadoPro,c.idCat,c.nomCat,pr.idProv,pr.nomProv,p.fotoPro FROM producto p \n"
                + "INNER JOIN categoria c ON (p.idCat=c.idCat) \n"
                + "INNER JOIN proveedor pr ON (p.idProv=pr.idProv) \n"
                + "WHERE c.nomCat='Hardware' AND p.estadoPro='Activo'"
                + "ORDER BY 1";
        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdPro(rs.getInt(1));
                p.setNomPro(rs.getString(2));
                p.setStockPro(rs.getInt(3));
                p.setPrecioPro(rs.getDouble(4));
                p.setEstadoPro(rs.getString(5));

                Categoria c = new Categoria();
                c.setIdCat(rs.getInt(6));
                c.setNomCat(rs.getString(7));

                Proveedor prov = new Proveedor();
                prov.setIdProv(rs.getInt(8));
                prov.setNomProv(rs.getString(9));

                p.setFotoPro(rs.getString(10));

                p.setCategoria(c);
                p.setProveedor(prov);

                lista.add(p);
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
    public ArrayList<Producto> ListarSoftware() {

        ArrayList<Producto> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT p.idPro,p.nomPro,p.stockPro,p.precioPro,p.estadoPro,c.idCat,c.nomCat,pr.idProv,pr.nomProv,p.fotoPro FROM producto p \n"
                + "INNER JOIN categoria c ON (p.idCat=c.idCat) \n"
                + "INNER JOIN proveedor pr ON (p.idProv=pr.idProv) \n"
                + "WHERE c.nomCat='Software' AND p.estadoPro='Activo'"
                + "ORDER BY 1";
        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdPro(rs.getInt(1));
                p.setNomPro(rs.getString(2));
                p.setStockPro(rs.getInt(3));
                p.setPrecioPro(rs.getDouble(4));
                p.setEstadoPro(rs.getString(5));

                Categoria c = new Categoria();
                c.setIdCat(rs.getInt(6));
                c.setNomCat(rs.getString(7));

                Proveedor prov = new Proveedor();
                prov.setIdProv(rs.getInt(8));
                prov.setNomProv(rs.getString(9));

                p.setFotoPro(rs.getString(10));

                p.setCategoria(c);
                p.setProveedor(prov);

                lista.add(p);
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
