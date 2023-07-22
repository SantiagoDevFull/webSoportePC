package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Icliente;
import Modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO implements Icliente {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public Cliente LoginCliente(String correo, String pass) {

        Cliente obj = null;

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT idCli,docCli,numCli,nomCli,correoCli,passCli,estadoCli \n"
                + "FROM cliente\n"
                + "WHERE correoCli=? AND passCli=? AND estadoCli='Activo'";
        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, correo);
            st.setString(2, pass);
            rs = st.executeQuery();

            if (rs.next()) {
                obj = new Cliente();
                obj.setIdCli(rs.getInt(1));
                obj.setDocCli(rs.getString(2));
                obj.setNumCli(rs.getString(3));
                obj.setNomCli(rs.getString(4));
                obj.setCorreoCli(rs.getString(5));
                obj.setPassCli(rs.getString(6));
                obj.setEstadoCli(rs.getString(7));
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
        return obj;
    }

    @Override
    public ArrayList<Cliente> ListarCliente() {

        ArrayList<Cliente> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT c.idCli,c.docCli,c.numCli,c.nomCli,c.correoCli,c.passCli,\n"
                + "(SELECT count(*) FROM solicitud s WHERE s.idCli=c.idCli) as numSoli,\n"
                + "(SELECT count(*) FROM boleta b WHERE b.idCli=c.idCli) as numBol,\n"
                + "(SELECT count(*) FROM factura f WHERE f.idCli=c.idCli) as numFac,\n"
                + "c.estadoCli FROM cliente c \n"
                + "order by 1;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Cliente cli = new Cliente();
                cli.setIdCli(rs.getInt(1));
                cli.setDocCli(rs.getString(2));
                cli.setNumCli(rs.getString(3));
                cli.setNomCli(rs.getString(4));
                cli.setCorreoCli(rs.getString(5));
                cli.setPassCli(rs.getString(6));
                cli.setNumSoli(rs.getInt(7));
                cli.setNumBol(rs.getInt(8));
                cli.setNumFac(rs.getInt(9));
                cli.setEstadoCli(rs.getString(10));
                lista.add(cli);
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
    public int AgregarCliente(Cliente obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO cliente(docCli,numCli,nomCli,correoCli,passCli,estadoCli) VALUES (?,?,?,?,?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, obj.getDocCli());
            st.setString(2, obj.getNumCli());
            st.setString(3, obj.getNomCli());
            st.setString(4, obj.getCorreoCli());
            st.setString(5, obj.getPassCli());
            st.setString(6, obj.getEstadoCli());
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
    public int ActualizarCliente(Cliente obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE cliente SET docCli=?,numCli=?,nomCli=?,correoCli=?,passCli=?,estadoCli=? WHERE idCli=?";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, obj.getDocCli());
            st.setString(2, obj.getNumCli());
            st.setString(3, obj.getNomCli());
            st.setString(4, obj.getCorreoCli());
            st.setString(5, obj.getPassCli());
            st.setString(6, obj.getEstadoCli());
            st.setInt(7, obj.getIdCli());
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
    public int EliminarCliente(int id) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "DELETE FROM cliente WHERE idCli=?;";

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
    public String ExisteNumeroDocumento(String num) {

        String numero = "";

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT numCli FROM cliente where numCli=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, num);
            rs = st.executeQuery();

            if (rs.next()) {
                numero = rs.getString(1);
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
        return numero;

    }

    @Override
    public String ExisteCorreo(String correo) {

        String res = "";

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT correoCli FROM cliente where correoCli=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, correo);
            rs = st.executeQuery();

            if (rs.next()) {
                res = rs.getString(1);
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

}
