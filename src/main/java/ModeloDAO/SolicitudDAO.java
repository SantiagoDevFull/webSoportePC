package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Isolicitud;
import Modelo.Cliente;
import Modelo.Solicitud;
import Modelo.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolicitudDAO implements Isolicitud {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public ArrayList<Solicitud> ListarSolicitud() {

        ArrayList<Solicitud> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT s.idSol,c.numCli,c.nomCli,t.idTipo,t.nomTipo,s.descripcionSol,s.respuestaSol,s.estadoSol FROM solicitud s \n"
                + "INNER JOIN cliente c ON (s.idCli=c.idCli)\n"
                + "INNER JOIN tipo t ON (s.idTipo=t.idTipo) order by 1";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {

                Cliente cli = new Cliente();
                cli.setNumCli(rs.getString(2));
                cli.setNomCli(rs.getString(3));

                Tipo tipo = new Tipo();
                tipo.setIdTipo(rs.getInt(4));
                tipo.setNomTipo(rs.getString(5));

                Solicitud obj = new Solicitud();
                obj.setIdSol(rs.getInt(1));
                obj.setDescripcionSol(rs.getString(6));
                obj.setRespuestaSol(rs.getString(7));
                obj.setEstadoSol(rs.getString(8));

                obj.setCliente(cli);
                obj.setTipo(tipo);

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
    public int ResponderSolicitud(int codigo, String mensaje) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE solicitud SET respuestaSol=?,estadoSol='Respondido' WHERE idSol=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, mensaje);
            st.setInt(2, codigo);
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
    public ArrayList<Solicitud> ListarSolicitudes(int id) {

        ArrayList<Solicitud> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT c.numCli,c.nomCli,t.nomTipo,s.descripcionSol,s.respuestaSol,s.estadoSol FROM solicitud s \n"
                + "INNER JOIN tipo t ON (s.idTipo=t.idTipo)\n"
                + "INNER JOIN cliente c ON (s.idCli=c.idCli) WHERE c.idCli=? ORDER BY s.idSol DESC";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, id);
            rs = st.executeQuery();

            while (rs.next()) {
                Solicitud obj = new Solicitud();

                Cliente cli = new Cliente();
                cli.setNumCli(rs.getString(1));
                cli.setNomCli(rs.getString(2));

                Tipo tipo = new Tipo();
                tipo.setNomTipo(rs.getString(3));

                obj.setDescripcionSol(rs.getString(4));
                obj.setRespuestaSol(rs.getString(5));
                obj.setEstadoSol(rs.getString(6));

                obj.setTipo(tipo);
                obj.setCliente(cli);

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
    public int EnviarSolicitud(Solicitud obj) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO solicitud(idCli,idTipo,descripcionSol,estadoSol) VALUES (?,?,?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setInt(1, obj.getCliente().getIdCli());
            st.setInt(2, obj.getTipo().getIdTipo());
            st.setString(3, obj.getDescripcionSol());
            st.setString(4, obj.getEstadoSol());
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
