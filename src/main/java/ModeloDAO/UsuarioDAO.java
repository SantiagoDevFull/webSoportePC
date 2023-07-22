package ModeloDAO;

import Config.MySQLConexion;
import Interfaces.Iusuario;
import Modelo.Rol;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements Iusuario {

    private Connection cn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    @Override
    public Usuario LoginUsuario(String correo, String pass) {

        Usuario obj = null;

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT u.idUsu,u.correoUsu,u.passUsu,u.nomUsu,u.estadoUsu,u.idRol,r.nomRol,r.estadoRol FROM usuario u \n"
                + "inner join rol r on (u.idRol=r.idRol) \n"
                + "WHERE u.estadoUsu='Activo' AND r.estadoRol='Activo' AND u.correoUsu=? AND u.passUsu=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, correo);
            st.setString(2, pass);
            rs = st.executeQuery();

            if (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt(6));
                rol.setNomRol(rs.getString(7));
                rol.setEstadoRol(rs.getString(8));

                obj = new Usuario();
                obj.setIdUsu(rs.getInt(1));
                obj.setCorreoUsu(rs.getString(2));
                obj.setPassUsu(rs.getString(3));
                obj.setNomUsu(rs.getString(4));
                obj.setEstadoUsu(rs.getString(5));
                obj.setRol(rol);
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
    public ArrayList<Usuario> ListarUsuario() {

        ArrayList<Usuario> lista = new ArrayList();

        cn = MySQLConexion.getConexion();
        String consulta = "SELECT u.idUsu,u.correoUsu,u.passUsu,u.nomUsu,u.estadoUsu,u.idRol,r.nomRol,r.estadoRol,\n"
                + "(SELECT count(*) FROM boleta b WHERE b.idUsu=u.idUsu) as numBol,\n"
                + "(SELECT count(*) FROM factura f WHERE f.idUsu=f.idUsu) as numFac\n"
                + "FROM usuario u \n"
                + "INNER JOIN rol r ON (u.idRol=r.idRol) order by 1;";

        try {
            st = cn.prepareStatement(consulta);
            rs = st.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsu(rs.getInt(1));
                u.setCorreoUsu(rs.getString(2));
                u.setPassUsu(rs.getString(3));
                u.setNomUsu(rs.getString(4));
                u.setEstadoUsu(rs.getString(5));
                u.setNumBol(rs.getInt(9));
                u.setNumFac(rs.getInt(10));

                Rol rol = new Rol();
                rol.setIdRol(rs.getInt(6));
                rol.setNomRol(rs.getString(7));
                rol.setEstadoRol(rs.getString(8));

                u.setRol(rol);
                lista.add(u);
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
    public int AgregarUsuario(Usuario u) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "INSERT INTO usuario(correoUsu,passUsu,nomUsu,estadoUsu,idRol) VALUES (?,?,?,?,?);";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, u.getCorreoUsu());
            st.setString(2, u.getPassUsu());
            st.setString(3, u.getNomUsu());
            st.setString(4, u.getEstadoUsu());
            st.setInt(5, u.getRol().getIdRol());
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
    public int ActualizarUsuario(Usuario u) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "UPDATE usuario SET correoUsu=?,passUsu=?,nomUsu=?,estadoUsu=?,idRol=? WHERE idUsu=?;";

        try {
            st = cn.prepareStatement(consulta);
            st.setString(1, u.getCorreoUsu());
            st.setString(2, u.getPassUsu());
            st.setString(3, u.getNomUsu());
            st.setString(4, u.getEstadoUsu());
            st.setInt(5, u.getRol().getIdRol());
            st.setInt(6, u.getIdUsu());
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
    public int EliminarUsuario(int id) {

        int res = 0;

        cn = MySQLConexion.getConexion();
        String consulta = "DELETE FROM usuario WHERE idUsu=?;";

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
