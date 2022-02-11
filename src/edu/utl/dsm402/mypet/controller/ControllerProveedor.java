/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm402.mypet.controller;

import edu.utl.dsm402.mypet.db.ConexionMySQL;
import edu.utl.dsm402.mypet.model.Proveedor;
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


public class ControllerProveedor {
    
    public int insert(Proveedor prov) throws Exception
    {
        //Generar la llamada al procedure
        String query ="{call insertProveedor(?,?,?,?,"+
                                            "?,?,?,?," +
                                            "?,?,?,?," +
                                             "?,?,?)}";    //Valores de retorno
        //2. Generar un objeto de la conexion y abrirla
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn=connMySQL.open();
        //3.Generar el callable statement que llevara la llamada procedure
        CallableStatement cstmt = conn.prepareCall(query);
        //4. Colocar los parametros que necesita el procedure
        cstmt.setString(1,prov.getIdRepresentante().getNombre());
        cstmt.setString(2,prov.getIdRepresentante().getApellidoPaterno());
        cstmt.setString(3,prov.getIdRepresentante().getApellidoMaterno());
        cstmt.setString(4, prov.getIdRepresentante().getCalle());
        cstmt.setInt(5, prov.getIdRepresentante().getNumero());
        cstmt.setString(6, prov.getIdRepresentante().getColonia());
        cstmt.setInt(7, prov.getIdRepresentante().getCp());
        cstmt.setString(8, prov.getIdRepresentante().getCiudad());
        cstmt.setString(9, prov.getIdRepresentante().getEstado());
        cstmt.setInt(10, prov.getIdRepresentante().getTel1());
        cstmt.setInt(11, prov.getIdRepresentante().getTel2());
        cstmt.setString(12, prov.getRfc());
        cstmt.setString(13, prov.getRazonSocial());
              
        //Se indican los parametros de salida
        cstmt.registerOutParameter(14,Types.INTEGER);
        cstmt.registerOutParameter(15,Types.INTEGER);
        //5. Ejecutar
        cstmt.execute();
        //6. Tomar los IDS que se generan
        prov.getIdRepresentante().setIdPersona(cstmt.getInt(14));
        prov.setIdProveedor(cstmt.getInt(15));
        //7. Cierre de los objetos
        cstmt.close();
        connMySQL.close();
        
        return prov.getIdProveedor();
    }
    
    public void update (Proveedor prov) throws Exception
    {
        String sql ="{call updateProveedor(?,?," +     
                                          "?,?,?,?,?,?,?,?,?,?,?,"+ 
                                          "?,?)}";      
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn=connMySQL.open();
        CallableStatement cstmt = conn.prepareCall(sql);
        
        cstmt.setInt(1,prov.getIdRepresentante().getIdPersona());
        cstmt.setInt(2,prov.getIdProveedor());                      
        cstmt.setString(3,prov.getIdRepresentante().getNombre());
        cstmt.setString(4,prov.getIdRepresentante().getApellidoPaterno());
        cstmt.setString(5,prov.getIdRepresentante().getApellidoMaterno());
        cstmt.setString(6,prov.getIdRepresentante().getCalle());
        cstmt.setInt(7,prov.getIdRepresentante().getNumero());
        cstmt.setString(8,prov.getIdRepresentante().getColonia());
        cstmt.setInt(9, prov.getIdRepresentante().getCp());
        cstmt.setString(10,prov.getIdRepresentante().getCiudad());
        cstmt.setString(11,prov.getIdRepresentante().getEstado());
        cstmt.setInt(12,prov.getIdRepresentante().getTel1());
        cstmt.setInt(13,prov.getIdRepresentante().getTel2());
        cstmt.setString(14,prov.getRfc());
        cstmt.setString(15,prov.getRazonSocial());  
        
        // Ejecutar
        cstmt.execute();
        
        // Cerrar los objetos de BD
        cstmt.close();
        connMySQL.close();
    }
    
    public void delete(int idPersona) throws  Exception
    {
        //1. Generar la sentencia SQL que se envia
        String query = "UPDATE persona SET estatus=0 WHERE idPersona=" + idPersona;
        //2. Generar un objeto de la conexion y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn=connMySQL.open();
        //3. Generar un statement que lleve las sentencias
        Statement stmt=conn.createStatement();
        //4. Se envia la sentencia sql (consulta)
        stmt.executeUpdate(query);
        //5. Se cierra el statement
        stmt.close();
        //6. Cierre de la conexion
        connMySQL.close();
        
        return;
    }
    
     public void reactivate (int idPersona) throws  Exception
    {
        //1. Generar la sentencia SQL que se envia
        String query = "UPDATE persona SET estatus=1 WHERE idPersona=" + idPersona;
        //2. Generar un objeto de la conexion y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn=connMySQL.open();
        //3. Generar un statement que lleve las sentencias
        Statement stmt=conn.createStatement();
        //4. Se envia la sentencia sql (consulta)
        stmt.executeUpdate(query);
        //5. Se cierra el statement
        stmt.close();
        //6. Cierre de la conexion
        connMySQL.close();
        
        return;
    }
    
    
    
    public List<Proveedor> getAll() throws Exception
    {
        //1. Generar la consulta
        String query="select * from v_proveedores where estatus = 1";
        //2. Generar un objeto de la conexion y abrirla
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn=connMySQL.open();
        //3. GEnerar un statement que lleve las sentencias
        PreparedStatement pstmt=conn.prepareStatement(query);
        //4. Ejecutar y almacenar el resultado del resultSet
        ResultSet rs=pstmt.executeQuery();
        //5. Generar  la estructura que contendra toda la informacion
        List<Proveedor> proveedores=new ArrayList<>();
        
        while (rs.next() != false)
        {
            
            // Generamos un objeto de tipo producto
            Persona per = new Persona();
            per.setIdPersona(rs.getInt("idPersona"));
            per.setNombre(rs.getString("nombre"));
            per.setApellidoPaterno(rs.getString("apellidoPaterno"));
            per.setApellidoMaterno(rs.getString("apellidoMaterno"));
            per.setCalle(rs.getString("calle"));
            per.setNumero(rs.getInt("numero"));
            per.setColonia(rs.getString("colonia"));
            per.setCp((rs.getInt("cp")));
            per.setCiudad(rs.getString("ciudad"));
            per.setEstado(rs.getString("estado"));
            per.setTel1(rs.getInt("tel1"));
            per.setTel2(rs.getInt("tel2"));
            per.setEstatus(rs.getInt("estatus"));     
            
            //Llenar los datos del objeto de tipo proveedor
            Proveedor pro = new Proveedor();
            pro.setIdProveedor(rs.getInt("idProveedor"));
            pro.setRfc(rs.getString("rfc"));
            pro.setRazonSocial(rs.getString("razonSocial"));           
            pro.setIdRepresentante(per);
            
            // Se coloca el objeto mercancia dentro de la estructura
            proveedores.add(pro);
        }
        
        //7. Cerrar objetos
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return proveedores;
    }
    
    public List<Proveedor> getAllInactive() throws Exception
    {
        //1. Generar la consulta
        String query="select * from v_proveedores where estatus = 0";
        //2. Generar un objeto de la conexion y abrirla
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn=connMySQL.open();
        //3. GEnerar un statement que lleve las sentencias
        PreparedStatement pstmt=conn.prepareStatement(query);
        //4. Ejecutar y almacenar el resultado del resultSet
        ResultSet rs=pstmt.executeQuery();
        //5. Generar  la estructura que contendra toda la informacion
        List<Proveedor> proveedores=new ArrayList<>();
        
        while (rs.next() != false)
        {
            
            // Generamos un objeto de tipo producto
            Persona per = new Persona();
            per.setIdPersona(rs.getInt("idPersona"));
            per.setNombre(rs.getString("nombre"));
            per.setApellidoPaterno(rs.getString("apellidoPaterno"));
            per.setApellidoMaterno(rs.getString("apellidoMaterno"));
            per.setCalle(rs.getString("calle"));
            per.setNumero(rs.getInt("numero"));
            per.setColonia(rs.getString("colonia"));
            per.setCp((rs.getInt("cp")));
            per.setCiudad(rs.getString("ciudad"));
            per.setEstado(rs.getString("estado"));
            per.setTel1(rs.getInt("tel1"));
            per.setTel2(rs.getInt("tel2"));
            per.setEstatus(rs.getInt("estatus"));     
            
            //Llenar los datos del objeto de tipo proveedor
            Proveedor pro = new Proveedor();
            pro.setIdProveedor(rs.getInt("idProveedor"));
            pro.setRfc(rs.getString("rfc"));
            pro.setRazonSocial(rs.getString("razonSocial"));           
            pro.setIdRepresentante(per);
            
            // Se coloca el objeto mercancia dentro de la estructura
            proveedores.add(pro);
        }
        
        //7. Cerrar objetos
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return proveedores;
    }
    
    public List<Proveedor> search(String s) throws Exception
    {
        //1. Generar la consulta
        String query="SELECT * FROM v_proveedores WHERE nombre like '%"+s+"%'";
        //2. Generar un objeto de la conexion y abrirla
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn=connMySQL.open();
        //3. GEnerar un statement que lleve las sentencias
        PreparedStatement pstmt=conn.prepareStatement(query);
        //4. Ejecutar y almacenar el resultado del resultSet
        ResultSet rs=  pstmt.executeQuery();
        //5. Generar  la estructura que contendra toda la informacion
        List<Proveedor> proveedores=new ArrayList<>();
        
        while (rs.next() != false)
        {
            // Generamos un objeto de tipo producto
            Persona per = new Persona();
            
            per.setIdPersona(rs.getInt("idPersona"));
            per.setNombre(rs.getString("nombre"));
            per.setApellidoPaterno(rs.getString("apellidoPaterno"));
            per.setApellidoMaterno(rs.getString("apellidoMaterno"));
            per.setCalle(rs.getString("calle"));
            per.setNumero(rs.getInt("numero"));  
            per.setColonia(rs.getString("colonia"));
            per.setCp((rs.getInt("cp")));
            per.setCiudad(rs.getString("ciudad"));
            per.setEstado(rs.getString("estado"));
            per.setTel1(rs.getInt("tel1"));
            per.setTel2(rs.getInt("tel2"));
            per.setEstatus(rs.getInt("estatus"));     
            
            //Llenar los datos del objeto de tipo proveedor
            Proveedor pro = new Proveedor();
            pro.setIdProveedor(rs.getInt("idProveedor"));
            pro.setRfc(rs.getString("rfc"));
            pro.setRazonSocial(rs.getString("razonSocial"));           
            pro.setIdRepresentante(per);
            
            proveedores.add(pro);          
        }
        
        //7. Cerrar objetos
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return proveedores;
    }
   
    
    public static void main (String[] a) throws Exception
    {
        ConexionMySQL connMySQL=new ConexionMySQL();
        try (Connection conn = connMySQL.open()){
            if (conn!=null) 
                System.out.println("OK");
            } catch (Exception ex){
                    System.out.println(ex.toString());
                    }
        
    }
    
}
