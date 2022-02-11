/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm402.mypet.controller;


import edu.utl.dsm402.mypet.db.ConexionMySQL;
import edu.utl.dsm402.mypet.model.Empleado;
import edu.utl.dsm402.mypet.model.Mercancia;
import edu.utl.dsm402.mypet.model.Persona;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jorge
 */
public class ControllerEmpleado {
    
    public int insert(Empleado e) throws Exception{
        
        String query="{call insertEmpleado(?,?,?,?,?,?,?,?,?,?,?,?,"
                +"?,?,"
                +"?,?)}";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement cstmt = conn.prepareCall(query);
        
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoPaterno());
        cstmt.setString(3, e.getPersona().getApellidoMaterno());
        cstmt.setString(4, e.getPersona().getFechaNacimiento());
        cstmt.setString(5, e.getPersona().getCalle());
        cstmt.setInt(6, e.getPersona().getNumero());
        cstmt.setString(7, e.getPersona().getColonia());
        cstmt.setInt(8, e.getPersona().getCp());
        cstmt.setString(9, e.getPersona().getCiudad());
        cstmt.setString(10, e.getPersona().getEstado());
        cstmt.setInt(11, e.getPersona().getTel1());
        cstmt.setInt(12, e.getPersona().getTel2());
        
        cstmt.setString(13, e.getCorreo());
        cstmt.setString(14, e.getContrasenia());
        
        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.INTEGER);
 
        cstmt.execute();
        e.getPersona().setIdPersona(cstmt.getInt(15));
        e.setIdEmpleado(cstmt.getInt(16));
        cstmt.close();
        connMySQL.close();
        return e.getIdEmpleado();
    }
    
    
    public void update(Empleado e) throws Exception {

        String sql = "{call updateEmpleado(?," +
                                      "?,?,?,?,?,?,?,?,?,?,?,?,"+
                                      "?, ?)}";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement cstmt = conn.prepareCall(sql);

        cstmt.setInt(1, e.getIdEmpleado());
        cstmt.setString(2, e.getPersona().getNombre());
        cstmt.setString(3, e.getPersona().getApellidoPaterno());
        cstmt.setString(4, e.getPersona().getApellidoMaterno());
        cstmt.setString(5, e.getPersona().getFechaNacimiento());
        cstmt.setString(6, e.getPersona().getCalle());
        cstmt.setInt(7, e.getPersona().getNumero());
        cstmt.setString(8, e.getPersona().getColonia());
        cstmt.setInt(9, e.getPersona().getCp());
        cstmt.setString(10, e.getPersona().getCiudad());
        cstmt.setString(11, e.getPersona().getEstado());
        cstmt.setInt(12, e.getPersona().getTel1());
        cstmt.setInt(13, e.getPersona().getTel2());
        cstmt.setString(14, e.getCorreo());
        cstmt.setString(15, e.getContrasenia());
 
        cstmt.execute();
        cstmt.close();
        connMySQL.close();
    }
    
    
    public List<Empleado> getAll() throws Exception {

    String query = "SELECT * FROM v_empleados WHERE estatus=1";
    ConexionMySQL connMySQL = new ConexionMySQL();
    Connection conn = connMySQL.open();
 
    PreparedStatement pstmt = conn.prepareStatement(query);
    ResultSet rs = pstmt.executeQuery();

    List<Empleado> empleados = new ArrayList<>();
    while (rs.next() != false) {
        
            Persona per = new Persona();
            per.setIdPersona(rs.getInt("idPersona"));
            per.setNombre(rs.getString("nombre"));
            per.setApellidoPaterno(rs.getString("apellidoPaterno"));
            per.setApellidoMaterno(rs.getString("apellidoMaterno"));
            per.setFechaNacimiento(rs.getString("fechaNacimiento"));
            per.setCalle(rs.getString("calle"));
            per.setNumero(rs.getInt("numero"));
            per.setColonia(rs.getString("colonia"));
            per.setCp(rs.getInt("cp"));
            per.setCiudad(rs.getString("ciudad"));
            per.setEstado(rs.getString("estado"));
	    per.setTel1(rs.getInt("tel1"));
            per.setTel2(rs.getInt("tel2"));
            per.setEstatus(rs.getInt("estatus"));
            
            Empleado e = new Empleado();
            e.setIdEmpleado(rs.getInt("idEmpleado"));
            e.setCorreo(rs.getString("correo"));
            e.setContrasenia(rs.getString("contrasenia"));
            e.setPersona(per);

            empleados.add(e);
        }
        rs.close();
        pstmt.close();
        connMySQL.close();
        return empleados;
    }
    
     public List<Empleado> getAllInactive() throws Exception {

    String query = "SELECT * FROM v_empleados WHERE estatus=0";
    ConexionMySQL connMySQL = new ConexionMySQL();
    Connection conn = connMySQL.open();
 
    PreparedStatement pstmt = conn.prepareStatement(query);
    ResultSet rs = pstmt.executeQuery();

    List<Empleado> empleados = new ArrayList<>();
    while (rs.next() != false) {
        
            Persona per = new Persona();
            per.setIdPersona(rs.getInt("idPersona"));
            per.setNombre(rs.getString("nombre"));
            per.setApellidoPaterno(rs.getString("apellidoPaterno"));
            per.setApellidoMaterno(rs.getString("apellidoMaterno"));
            per.setFechaNacimiento(rs.getString("fechaNacimiento"));
            per.setCalle(rs.getString("calle"));
            per.setNumero(rs.getInt("numero"));
            per.setColonia(rs.getString("colonia"));
            per.setCp(rs.getInt("cp"));
            per.setCiudad(rs.getString("ciudad"));
            per.setEstado(rs.getString("estado"));
	    per.setTel1(rs.getInt("tel1"));
            per.setTel2(rs.getInt("tel2"));
            per.setEstatus(rs.getInt("estatus"));
            
            Empleado e = new Empleado();
            e.setIdEmpleado(rs.getInt("idEmpleado"));
            e.setCorreo(rs.getString("correo"));
            e.setContrasenia(rs.getString("contrasenia"));
            e.setPersona(per);

            empleados.add(e);
        }
        rs.close();
        pstmt.close();
        connMySQL.close();
        return empleados;
    }
    
    
    public void delete(int idPersona) throws Exception {

    String query = "UPDATE persona SET estatus=0 WHERE idPersona="+idPersona;

    ConexionMySQL connMySQL = new ConexionMySQL();
    Connection conn=connMySQL.open();

    Statement stmt=conn.createStatement();
    stmt.executeUpdate(query);
    stmt.close();
    connMySQL.close();
    return;
    }
    
    public void reactivate(int idPersona) throws Exception {
        
    String query = "UPDATE persona SET estatus=1 WHERE idPersona="+idPersona;

    ConexionMySQL connMySQL = new ConexionMySQL();
    Connection conn = connMySQL.open();
    Statement stmt = conn.createStatement();
    stmt.executeUpdate(query);
    stmt.close();
    connMySQL.close();
    return;
    }
     
     public List<Empleado> search(String s) throws Exception {
        
        String query = "SELECT * FROM v_empleados WHERE nombre like '%"+s+"%'";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        List<Empleado> empleados = new ArrayList<>();
    while (rs.next() != false) {
        
            Persona per = new Persona();
            per.setIdPersona(rs.getInt("idPersona"));
            per.setNombre(rs.getString("nombre"));
            per.setApellidoPaterno(rs.getString("apellidoPaterno"));
            per.setApellidoMaterno(rs.getString("apellidoMaterno"));
            per.setFechaNacimiento(rs.getString("fechaNacimiento"));
            per.setCalle(rs.getString("calle"));
            per.setNumero(rs.getInt("numero"));
            per.setColonia(rs.getString("colonia"));
            per.setCp(rs.getInt("cp"));
            per.setCiudad(rs.getString("ciudad"));
            per.setEstado(rs.getString("estado"));
	    per.setTel1(rs.getInt("tel1"));
            per.setTel2(rs.getInt("tel2"));
            per.setEstatus(rs.getInt("estatus"));
            
            Empleado e = new Empleado();
            e.setIdEmpleado(rs.getInt("idEmpleado"));
            e.setCorreo(rs.getString("correo"));
            e.setContrasenia(rs.getString("contrasenia"));
            e.setPersona(per);

            empleados.add(e);
        }
        rs.close();
        pstmt.close();
        connMySQL.close();
        return empleados;
    }
    
   
    
}
