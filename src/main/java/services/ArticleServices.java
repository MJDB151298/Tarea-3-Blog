package services;

import Clases.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleServices {
    public List<Articulo> listaArticulos() {
        String userName;
        List<Articulo> lista = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select * from articulos ";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Articulo art = new Articulo();
                art.setId(rs.getInt("id"));
                art.setTitulo(rs.getString("titulo"));
                art.setCuerpo(rs.getString("cuerpo"));
                userName = rs.getString("autor");
                art.setAutor(Controladora.getInstance().buscarAutor(userName));
                art.setFecha(rs.getDate("fecha"));

                lista.add(art);
                Controladora.getInstance().getMisArticulos().add(art);
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
     * @param id
     * @return
     */
    public Articulo getAriculo(int id) {
        Articulo art = null;
        String userName;
        Connection con = null; //objeto conexion.
        try {
            //utilizando los comodines (?)...
            String query = "select * from usuarios where username = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, id);
            //Ejecuto...
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                art.setId(rs.getInt("id"));
                art.setTitulo(rs.getString("titulo"));
                art.setCuerpo(rs.getString("cuerpo"));
                userName = rs.getString("autor");
                art.setAutor(Controladora.getInstance().buscarAutor(userName));
                art.setFecha(rs.getDate("fecha"));

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

        return art;
    }

    /**
     *
     * @param art
     * @return
     */
    public boolean crearArticulo(Articulo art){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into articulos(titulo, cuerpo, autor, fecha) values(?,?,?,?)";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, art.getTitulo());
            prepareStatement.setString(2, art.getCuerpo());
            prepareStatement.setString(3, art.getAutor().getUsername());
            prepareStatement.setDate(4, (Date) art.getFecha());
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

    public boolean actualizarArticulo(Articulo art){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "update articulos set titulo=?, cuerpo=?, autor=?, fecha=? where id = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, art.getTitulo());
            prepareStatement.setString(2, art.getCuerpo());
            prepareStatement.setString(3, art.getAutor().getUsername());
            prepareStatement.setDate(4, (Date) art.getFecha());
            //Indica el where...
            prepareStatement.setLong(5, art.getId());
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

    public boolean borrarArticulo(int id){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "delete from articulos where id = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setInt(1, id);
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