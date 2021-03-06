package services;

import Clases.Articulo;
import Clases.Comentario;
import Clases.Controladora;
import Clases.Etiqueta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterArticleServices {
    public void agregarComentariosYEtiquetasAlArticulo() {
        int idArticulo;
        Articulo art;
        int idComentario;
        int idEtiqueta;

        Connection con = null; //objeto conexion.
        Connection con2 = null; //objeto conexion.
        Connection con3 = null; //objeto conexion.
        try {
            //
            String query = "select * from articulos ";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                ArrayList<Comentario> coments = new ArrayList<>();
                ArrayList<Etiqueta> etiquetas = new ArrayList<>();
                idArticulo = rs.getInt("id");
                art = Controladora.getInstance().buscarArticulo(idArticulo);

                String query2 = "select * from ARTICULOSCOMENTARIOS where idarticulo = ?";
                con2 = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
                //
                PreparedStatement prepareStatement2 = con2.prepareStatement(query2);
                prepareStatement2.setLong(1, art.getId());
                ResultSet rs2 = prepareStatement2.executeQuery();
                while(rs2.next()){
                    idComentario = rs2.getInt("IDCOMENTARIO");
                    Comentario com = Controladora.getInstance().buscarComentario(idComentario);
                    com.setArticulo(art);
                    coments.add(com);
                }

                art.setListaComentarios(coments);

                String query3 = "select * from ARTICULOSETIQUETAS where idarticulo = ?";
                con3 = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
                //
                PreparedStatement prepareStatement3 = con3.prepareStatement(query3);
                prepareStatement3.setLong(1, art.getId());
                ResultSet rs3 = prepareStatement3.executeQuery();
                while(rs3.next()){
                    idEtiqueta = rs3.getInt("IDETIQUETA");
                    Etiqueta et = Controladora.getInstance().buscarEtiqueta(idEtiqueta);
                    etiquetas.add(et);

                    System.out.println("El id del articulo es: " + art.getId() + "El nombre de la etiqueta es: " + et.getEtiqueta());
                }
                art.setListaEtiquetas(etiquetas);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if(con != null){
                    con.close();
                }
                if(con2 != null){
                    con2.close();
                }
                if(con3 != null){
                    con3.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Retorna un estudiante dado su matricula
     * @param id
     * @return
     */
    public void agregarComentariosYEtiquetasAlArticuloEspecifico(int id) {
        int idArticulo;
        Articulo art;
        int idComentario;
        int idEtiqueta;
        ArrayList<Comentario> coments = new ArrayList<>();
        ArrayList<Etiqueta> etiquetas = new ArrayList<>();
        Connection con = null; //objeto conexion.
        Connection con2 = null; //objeto conexion.
        Connection con3 = null; //objeto conexion.
        try {
            //utilizando los comodines (?)...
            String query = "select * from articulos where id = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, id);
            //Ejecuto...
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                idArticulo = rs.getInt("id");
                art = Controladora.getInstance().buscarArticulo(idArticulo);

                String query2 = "select * from ARTICULOSCOMENTARIOS where idarticulo = ?";
                con2 = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
                //
                PreparedStatement prepareStatement2 = con2.prepareStatement(query2);
                prepareStatement2.setLong(1, art.getId());
                ResultSet rs2 = prepareStatement2.executeQuery();
                while(rs2.next()){
                    idComentario = rs2.getInt("IDCOMENTARIO");
                    Comentario com = Controladora.getInstance().buscarComentario(idComentario);
                    com.setArticulo(art);
                    coments.add(com);
                }

                art.setListaComentarios(coments);

                String query3 = "select * from ARTICULOSETIQUETAS where idarticulo = ?";
                con3 = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
                //
                PreparedStatement prepareStatement3 = con3.prepareStatement(query3);
                prepareStatement3.setLong(1, art.getId());
                ResultSet rs3 = prepareStatement3.executeQuery();
                while(rs3.next()){
                    idEtiqueta = rs3.getInt("IDETIQUETA");
                    Etiqueta et = Controladora.getInstance().buscarEtiqueta(idEtiqueta);
                    etiquetas.add(et);
                }

                art.setListaEtiquetas(etiquetas);

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
    }

    /**
     *
     * @param art
     * @param comentario
     * @return
     */
    public boolean nuevoComentarioAlArticulo(Articulo art, Comentario comentario){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into ARTICULOSCOMENTARIOS(id, idarticulo, idcomentario) values(?,?,?)";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setLong(1, Controladora.getInstance().getMisComentarios().size());
            prepareStatement.setLong(2, art.getId());
            prepareStatement.setLong(3, comentario.getId());
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

        int index = Controladora.getInstance().getMisArticulos().indexOf(art);
        Controladora.getInstance().getMisComentarios().add(comentario);
        Controladora.getInstance().getMisArticulos().get(index).getListaComentarios().add(comentario);
        return ok;
    }

    /**
     *
     * @param art
     * @param etq
     * @return
     */
    public boolean nuevaEtiquetaAlArticulo(Articulo art, Etiqueta etq){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into ARTICULOSETIQUETAS(id, idarticulo, idetiqueta) values(?,?,?)";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setLong(1, Controladora.getInstance().getMisEtiquetas().size());
            prepareStatement.setLong(2, art.getId());
            prepareStatement.setLong(3, etq.getId());
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

//    public boolean actualizarUsuario(Usuario usu){
//        boolean ok =false;
//
//        Connection con = null;
//        try {
//
//            String query = "update usuarios set username=?, nombre=?, password=?, administrador=?, autor=? where username = ?";
//            con = DataBaseServices.getInstancia().getConexion();
//            //
//            PreparedStatement prepareStatement = con.prepareStatement(query);
//            //Antes de ejecutar seteo los parametros.
//            prepareStatement.setString(1, usu.getUsername());
//            prepareStatement.setString(2, usu.getNombre());
//            prepareStatement.setString(3, usu.getPassword());
//            prepareStatement.setBoolean(4, usu.isAdministrador());
//            prepareStatement.setBoolean(5, usu.isAutor());
//            //Indica el where...
//            prepareStatement.setString(6, usu.getUsername());
//            //
//            int fila = prepareStatement.executeUpdate();
//            ok = fila > 0 ;
//
//        } catch (SQLException ex) {
//            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
//        } finally{
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return ok;
//    }

    public boolean borrarComentarioDeArticulo(Articulo art, Comentario comentario){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "delete from ARTICULOSCOMENTARIOS where idarticulo = ? and idcomentario = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setLong(1, art.getId());
            prepareStatement.setLong(2, comentario.getId());
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

    public boolean borrarTodoComentarioArticulo(Articulo art){
        boolean ok =false;

        Connection con = null;
        Connection con2 = null;
        try {

            String query = "delete from ARTICULOSCOMENTARIOS where idarticulo = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setLong(1, art.getId());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

            String query2 = "delete from COMENTARIOS where articulo = ?";
            con2 = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement2 = con2.prepareStatement(query2);

            //Indica el where...
            prepareStatement2.setLong(1, art.getId());
            //
            int fila2 = prepareStatement2.executeUpdate();

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

    public boolean borrarEtiquetaDeArticulo(Articulo art, Etiqueta etq){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "delete from ARTICULOSETIQUETAS where idarticulo = ? and idetiqueta = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setLong(1, art.getId());
            prepareStatement.setLong(2, etq.getId());
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

    public boolean borrarTodaEtiquetaDeArticulo(Articulo art){
        boolean ok =false;

        Connection con = null;
        Connection con2 = null;
        try {
            String query = "delete from ARTICULOSETIQUETAS where idarticulo = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setLong(1, art.getId());
            //
            int fila = prepareStatement.executeUpdate();

            ok = fila > 0;

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
