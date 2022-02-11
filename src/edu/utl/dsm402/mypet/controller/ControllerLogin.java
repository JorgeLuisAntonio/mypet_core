/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm402.mypet.controller;



import edu.utl.dsm402.mypet.db.ConexionMySQL;
import edu.utl.dsm402.mypet.model.Cliente;
import edu.utl.dsm402.mypet.model.Empleado;
import edu.utl.dsm402.mypet.model.Persona;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class ControllerLogin {
    /*
      public void saveToken(int idEmpleado,String us,String p) throws Exception{
          Usuario u=new Usuario();
          u.setUsuario(us);
          u.setContrasenia(p);
          u.setToken();
          String query ="UPDATE empleado SET token= "+u.setToken()+" WHERE idEmpleado="+idEmpleado;
          
            ConexionMySQL connMySQL = new ConexionMySQL();
    Connection conn=connMySQL.open();

    Statement stmt=conn.createStatement();
    stmt.executeUpdate(query);
    stmt.close();
    connMySQL.close();
    return;
          
    }

    */
    
    
    public Empleado logEmpleado(String u, String p) throws Exception{ //parametros usuario y password
        Empleado e=new Empleado();
String query="SELECT empleado.idEmpleado, persona.nombre, persona.apellidoPaterno, persona.apellidoMaterno"
+ " FROM persona INNER JOIN empleado ON persona.idPersona=empleado.idPersona"
+ " WHERE empleado.correo='"+u+"' && empleado.contrasenia='"+p+"';";
        
        
        
        ConexionMySQL objC=new ConexionMySQL();
                Connection con= objC.open();
                Statement stmt=con.createStatement();
                ResultSet rs= stmt.executeQuery(query);
                
                while(rs.next()){
                   Persona persona= new Persona();
                   persona.setNombre(rs.getString("nombre"));
                   persona.setApellidoPaterno(rs.getString("apellidoPaterno"));
                   persona.setApellidoMaterno(rs.getString("apellidoMaterno"));
                   e.setPersona(persona);
                   e.setIdEmpleado(rs.getInt("idEmpleado"));
                   
                }
                rs.close();
                stmt.close();
                con.close();
                objC.close();
                
                
        
         

        return e;
    }
    
    public Cliente logCliente(String u, String p) throws Exception{ //parametros usuario y password
        Cliente c=new Cliente();

        String query="SELECT cliente.idCliente, persona.nombre, persona.apellidoPaterno, persona.apellidoMaterno"
+ " FROM persona INNER JOIN cliente ON persona.idPersona=cliente.idPersona"
+ " WHERE cliente.correo='"+u+"' && cliente.contrasenia='"+p+"';";

        ConexionMySQL objC=new ConexionMySQL();
                Connection con= objC.open();
                Statement stmt=con.createStatement();
                ResultSet rs= stmt.executeQuery(query);
                
                while(rs.next()){
                   Persona persona= new Persona();
                   persona.setNombre(rs.getString("nombre"));
                   persona.setApellidoPaterno(rs.getString("apellidoPaterno"));
                   persona.setApellidoMaterno(rs.getString("apellidoMaterno"));
                   c.setPersona(persona);
                   c.setIdCliente(rs.getInt("idCliente"));
                }
                rs.close();
                stmt.close();
                con.close();
                objC.close();
        return c;
    } 
    
    //Metodos de login de empleado y cliente
}
