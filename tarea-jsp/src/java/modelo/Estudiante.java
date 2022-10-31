/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;

public class Estudiante extends Persona {
    private String carne;
    private int id_tipo_sangre;
    Conexion cn;
    
    public Estudiante(){}
    public Estudiante(String carne, int id_tipo_sangre) {
        this.carne = carne;
        this.id_tipo_sangre = id_tipo_sangre;
    }

    public Estudiante(String carne, int id_tipo_sangre, int id, String nombres, String apellidos, String direccion, String telefono, String correo_electronico, String fecha_nacimiento) {
        super(id, nombres, apellidos, direccion, telefono, correo_electronico, fecha_nacimiento);
        this.carne = carne;
        this.id_tipo_sangre = id_tipo_sangre;
    }

    
    
    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public int getId_tipo_sangre() {
        return id_tipo_sangre;
    }

    public void setId_tipo_sangre(int id_tipo_sangre) {
        this.id_tipo_sangre = id_tipo_sangre;
    }
    
    public DefaultTableModel leer(){
    DefaultTableModel tabla = new DefaultTableModel();
    try{
        cn = new Conexion();
        cn.abrir_conexion();
         String query = "select e.id_estudiante as id,e.carne,e.nombres,e.apellidos,e.direccion,e.telefono, e.correo_electronico, e.fecha_nacimiento, t.sangre, t.id_tipos_sangre from estudiantes as e inner join tipos_sangre as t on e.id_tipo_sangre = t.id_tipos_sangre;";
         ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
         String encabezado[] = {"id","carne","nombres","apellidos","direccion","telefono","correo_electronico","fecha_nacimiento","sangre","id_tipos_sangre"};
         tabla.setColumnIdentifiers(encabezado);
         String datos[] = new String[10];
         while (consulta.next()){
             datos[0] = consulta.getString("id");
             datos[1] = consulta.getString("carne");
             datos[2] = consulta.getString("nombres");
             datos[3] = consulta.getString("apellidos");
             datos[4] = consulta.getString("direccion");
             datos[5] = consulta.getString("telefono");
             datos[6] = consulta.getString("correo_electronico");
             datos[7] = consulta.getString("fecha_nacimiento");
             datos[8] = consulta.getString("sangre");
             datos[9] = consulta.getString("id_tipos_sangre");
             tabla.addRow(datos);

         }

        cn.cerrar_conexion();
    }catch(SQLException ex){
        System.out.println(ex.getMessage());
    }
    return tabla;
    }
    
    @Override
    public int agregar(){
    int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "INSERT INTO estudiantes(carne,nombres,apellidos,direccion,telefono,correo_electronico,fecha_nacimiento,id_tipo_sangre) values(?,?,?,?,?,?,?,?);";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1,getCarne());
            parametro.setString(2,getNombres());
            parametro.setString(3,getApellidos());
            parametro.setString(4,getDireccion());
            parametro.setString(5,getTelefono());
            parametro.setString(6,getCorreo_electronico());
            parametro.setString(7,getFecha_nacimiento());
            parametro.setInt(8, getId_tipo_sangre());
            retorno = parametro.executeUpdate();
            //parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            retorno =0;
        }
    return retorno;
    }
    @Override
    public int modificar(){
     int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();            
            String query = "update estudiantes set carne=?,nombres=?,apellidos=?,direccion=?,telefono=?,correo_electronico=?,fecha_nacimiento=?,id_tipo_sangre=? where id_estudiante = ?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1,getCarne());
            parametro.setString(2,getNombres());
            parametro.setString(3,getApellidos());
            parametro.setString(4,getDireccion());
            parametro.setString(5,getTelefono());
            parametro.setString(6,getCorreo_electronico());
            parametro.setString(7,getFecha_nacimiento());
            parametro.setInt(8, getId_tipo_sangre());
            parametro.setInt(9, getId());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return retorno;
    }
    @Override
    public int eliminar(){
    int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();            
            String query = "delete from estudiantes where id_estudiante = ?;";
            //String query = "delete from empleados  where id_empleado = ?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);            
            parametro.setInt(1, getId());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return retorno;
    }   
    
}
