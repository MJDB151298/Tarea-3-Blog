package services;

import Clases.Controladora;
import Clases.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServices {
    public ArrayList<Usuario> listaUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select * from usuarios ";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Usuario usu = new Usuario();
                usu.setId(rs.getString("id"));
                usu.setUsername(rs.getString("username"));
                usu.setNombre(rs.getString("nombre"));
                usu.setPassword(rs.getString("password"));
                usu.setAdministrador(rs.getBoolean("administrador"));
                usu.setAutor(rs.getBoolean("autor"));

                lista.add(usu);
                //Controladora.getInstance().getMisUsuarios().add(usu);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lista;
    }

    /**
     * Retorna un estudiante dado su matricula
     * @param username
     * @return
     */
    public Usuario getUsuario(String username) {
        Usuario usu = null;
        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select * from usuarios where username = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, username);
            //Ejecuto...
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                usu = new Usuario();
                usu.setId(rs.getString("id"));
                usu.setUsername(rs.getString("username"));
                usu.setNombre(rs.getString("nombre"));
                usu.setPassword(rs.getString("password"));
                usu.setAdministrador(rs.getBoolean("administrador"));
                usu.setAutor(rs.getBoolean("autor"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return usu;
    }

    /**
     *
     * @param usu
     * @return
     */
    public boolean crearUsuario(Usuario usu){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into usuarios(id,username, nombre, password, administrador, autor) values(?,?,?,?,?,?)";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, usu.getId());
            prepareStatement.setString(2, usu.getUsername());
            prepareStatement.setString(3, usu.getNombre());
            prepareStatement.setString(4, usu.getPassword());
            prepareStatement.setBoolean(5, usu.isAdministrador());
            prepareStatement.setBoolean(6, usu.isAutor());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Controladora.getInstance().getMisUsuarios().add(usu);
        return ok;
    }

    public boolean actualizarUsuario(Usuario usu){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "update usuarios set username=?, nombre=?, password=?, administrador=?, autor=? where username = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, usu.getUsername());
            prepareStatement.setString(2, usu.getNombre());
            prepareStatement.setString(3, usu.getPassword());
            prepareStatement.setBoolean(4, usu.isAdministrador());
            prepareStatement.setBoolean(5, usu.isAutor());
            //Indica el where...
            prepareStatement.setString(6, usu.getUsername());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public boolean borrarUsuario(String username){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "delete from usuarios where username = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setString(1, username);
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }
}
