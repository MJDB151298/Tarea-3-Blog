package services;

import Clases.Comentario;
import Clases.Controladora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComentServices {
    public ArrayList<Comentario> listaComentarios() {
        String autor;
        int idArticulo;
        ArrayList<Comentario> lista = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select * from comentarios ";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Comentario com = new Comentario();
                com.setId(rs.getInt("id"));
                com.setComentario(rs.getString("comentario"));
                autor = rs.getString("autor");
                com.setAutor(Controladora.getInstance().buscarAutor(autor));
                idArticulo = rs.getInt("articulo");
                com.setArticulo(Controladora.getInstance().buscarArticulo(idArticulo));

                lista.add(com);
                //Controladora.getInstance().getMisComentarios().add(com);
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
    public Comentario getComentario(int id) {
        String autor;
        int idArticulo;
        Comentario comentario = null;
        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select * from comentarios where id = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, id);
            //Ejecuto...
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                comentario = new Comentario();
                comentario.setId(rs.getInt("id"));
                comentario.setComentario(rs.getString("comentario"));
                autor = rs.getString("autor");
                comentario.setAutor(Controladora.getInstance().buscarAutor(autor));
                idArticulo = rs.getInt("articulo");
                comentario.setArticulo(Controladora.getInstance().buscarArticulo(idArticulo));
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

        return comentario;
    }

    /**
     *
     * @param comentario
     * @return
     */
    public boolean crearComentario(Comentario comentario){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into comentarios(id, comentario, autor, articulo) values(?, ?,?,?)";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setLong(1, comentario.getId());
            prepareStatement.setString(2, comentario.getComentario());
            prepareStatement.setString(3, comentario.getAutor().getUsername());
            prepareStatement.setLong(4, comentario.getArticulo().getId());
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
        Controladora.getInstance().getMisComentarios().add(comentario);
        return ok;
    }

    public boolean actualizarComentario(Comentario comentario){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "update comentarios set comentario=?, autor=?, articulo=? where id = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, comentario.getComentario());
            prepareStatement.setString(2, comentario.getAutor().getUsername());
            prepareStatement.setLong(3, comentario.getArticulo().getId());
            //Indica el where...
            prepareStatement.setLong(4, comentario.getId());
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

    public boolean borrarComentario(int id){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "delete from comentarios where id = ?";
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
