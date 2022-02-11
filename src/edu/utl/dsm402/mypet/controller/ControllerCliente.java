
package edu.utl.dsm402.mypet.controller;

import edu.utl.dsm402.mypet.db.ConexionMySQL;
import edu.utl.dsm402.mypet.model.Cliente;
import java.sql.CallableStatement;
import java.sql.Connection;
import edu.utl.dsm402.mypet.model.Persona;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cecilia diaz
 */ 
 
 public class ControllerCliente 
{
    public int insert(Cliente c) throws Exception 
    {
    //1. Generar la llamada al prcedure
    String query="{call insertCliente(?,?,?,?,?,?,?,?,?,?,?,?," + //Datos del persona
                                                "?,?,"+
                                                 "?,?)}";

    //Valores de retorno
    //2. Generar un objeto de la conexion y abrirla 
    ConexionMySQL connMySQL=new ConexionMySQL();
    Connection conn=connMySQL.open();

    //3. Generar el callable statement que llevara la llamada al procedure

    CallableStatement cstmt=conn.prepareCall(query); 
    //4.Colocar los parametros que necesita el procedure
    //Colocamos los datos del producto 
    cstmt.setString(1,c.getPersona().getNombre());
    cstmt.setString(2,c.getPersona().getApellidoPaterno());
    cstmt.setString(3,c.getPersona().getApellidoMaterno()); 
    cstmt.setString(4,c.getPersona().getFechaNacimiento());
    cstmt.setString(5,c.getPersona().getCalle());
    cstmt.setInt(6,c.getPersona().getNumero()); 
    cstmt.setString(7,c.getPersona().getColonia()); 
    cstmt.setInt(8,c.getPersona().getCp()); 
    cstmt.setString(9,c.getPersona().getCiudad());
    cstmt.setString(10,c.getPersona().getEstado()); 
    cstmt.setInt(11,c.getPersona().getTel1()); 
    cstmt.setInt(12,c.getPersona().getTel2()); 
    
    //Colocamos los datos de la mercancia 
    cstmt.setString(13,c.getCorreo()); 
    cstmt.setString(14,c.getContrasenia());
    
    //Se indican los parametros de salida
    cstmt.registerOutParameter(15,Types.INTEGER);
    cstmt.registerOutParameter(16,Types.INTEGER);
    
    //5. Ejecutar
    cstmt.execute();
    
    //6. Tomar los IDs que se generaron
    c.getPersona().setIdPersona(cstmt.getInt(15));
    c.setIdCliente(cstmt.getInt(16)); 
    
    //7. Cierre de los objetos
    cstmt.close();

    connMySQL.close();
    return c.getIdCliente();
}

public void update( Cliente c) throws Exception 
{

    String sql = "{call updateCliente(?," + //ID de Cliente
                                      "?,?,?,?,?,?,?,?,?,?,?,?,"+//Datos de Persona
                                      "?, ?)}";//Datos de Cliente

    ConexionMySQL connMySQL = new ConexionMySQL(); 
    Connection conn = connMySQL.open();
    CallableStatement cstmt = conn.prepareCall(sql);

    cstmt.setInt(1, c.getIdCliente());
    cstmt.setString(2, c.getPersona().getNombre()); 
    cstmt.setString(3, c.getPersona().getApellidoPaterno()); 
    cstmt.setString(4, c.getPersona().getApellidoMaterno());
    cstmt.setString(5, c.getPersona().getFechaNacimiento()); 
    cstmt.setString(6, c.getPersona().getCalle());
    cstmt.setInt(7, c.getPersona().getNumero());
    cstmt.setString(8, c.getPersona().getColonia()); 
    cstmt.setInt(9, c.getPersona().getCp());
    cstmt.setString(10, c.getPersona().getCiudad()); 
    cstmt.setString(11, c.getPersona().getEstado()); 
    cstmt.setInt(12, c.getPersona().getTel1()); 
    cstmt.setInt(13, c.getPersona().getTel2()); 
    cstmt.setString(14, c.getCorreo()); 
    cstmt.setString(15, c.getContrasenia()); 

    //Ejecutamos la consulta:
    cstmt.execute();

    //Cerramos los objetos de BD:
    cstmt.close();
    connMySQL.close();
}

public void delete(int idPersona) throws Exception 
{

    //1. Generar la sentencia SQL que se envÃa 
    String query="UPDATE persona SET estatus=0 WHERE idPersona="+idPersona;

    //2. Generar un objeto de la conexion y abrirla
    ConexionMySQL connMySQL=new ConexionMySQL();
    Connection conn=connMySQL.open();
    //3. Generar un statement que lleve las sentencias

    Statement stmt=conn.createStatement(); 
    
    //4. Se envÃa la sentencia sql (consulta)
    stmt.executeUpdate(query); 
    
    //5. Se cierrar el statement
    stmt.close();
    //6. Cierro la conexion
    connMySQL.close();

    return;
}

public void reactivate(int idPersona) throws Exception 
{

    //1. Generar la sentencia SQL que se envÃa 
    String query="UPDATE persona SET estatus=1 WHERE idPersona="+idPersona;

    //2. Generar un objeto de la conexion y abrirla
    ConexionMySQL connMySQL =new ConexionMySQL();
    Connection conn =connMySQL.open();

    //3. Generar un statement que lleve las sentencias 
    Statement stmt=conn.createStatement();
    //4. Se envÃa la sentencia sql (consulta)

    stmt.executeUpdate(query);
    //5. Se cierrar el statement
    stmt.close();
    //6. Cierro la conexion
    connMySQL.close();

    return;
}

public List<Cliente> getAll() throws Exception 
{
    //1. Generar la consulta

    String query="SELECT * FROM v_cliente WHERE estatus=1"; 
    //2.Generar la conexiÃ³n y abrirla

    ConexionMySQL connMySQL=new ConexionMySQL();
    Connection conn=connMySQL.open();

    //3. Generar el statement que lleva la consulta 
    PreparedStatement pstmt=conn.prepareStatement(query); 
    //4. Ejecutar y alamacenar el resultado en un ResultSet 
    ResultSet rs=pstmt.executeQuery();

    //5. Generar la estructura que contendra toda la informaciÃ³n

    List<Cliente> clientes=new ArrayList<>();
    //6. Tomar todos los valores y colocarlos en la estructura
    while(rs.next() != false)
    {

    //Generamos y llenamos un objeto de tipo persona
        Persona per=new Persona();
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


        //Llenar los datos del objeto de tipo cliente
        Cliente c=new Cliente();
        c.setIdCliente(rs.getInt("idCliente"));
        c.setCorreo(rs.getString("correo"));
        c.setContrasenia(rs.getString("contrasenia")); 
        c.setPersona(per);

        //Se coloca el objeto clientes dentro de la estructura (lista de clientes)
        clientes.add(c);
    }
        //7. Cerrar los objetos
        rs.close();
        pstmt.close();
        connMySQL.close();
        return clientes;
}

public List<Cliente> getAllInactive() throws Exception 
{
    //1. Generar la consulta

    String query="SELECT * FROM v_cliente WHERE estatus=0"; 
    //2.Generar la conexiÃ³n y abrirla

    ConexionMySQL connMySQL=new ConexionMySQL();
    Connection conn=connMySQL.open();

    //3. Generar el statement que lleva la consulta 
    PreparedStatement pstmt=conn.prepareStatement(query); 
    //4. Ejecutar y alamacenar el resultado en un ResultSet 
    ResultSet rs=pstmt.executeQuery();

    //5. Generar la estructura que contendra toda la informaciÃ³n

    List<Cliente> clientes=new ArrayList<>();
    //6. Tomar todos los valores y colocarlos en la estructura
    while(rs.next() != false)
    {

    //Generamos y llenamos un objeto de tipo persona
        Persona per=new Persona();
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


        //Llenar los datos del objeto de tipo cliente
        Cliente c=new Cliente();
        c.setIdCliente(rs.getInt("idCliente"));
        c.setCorreo(rs.getString("correo"));
        c.setContrasenia(rs.getString("contrasenia")); 
        c.setPersona(per);

        //Se coloca el objeto mercancia dentro de la estructura (lista de mercacias)
        clientes.add(c);
    }
        //7. Cerrar los objetos
        rs.close();
        pstmt.close();
        connMySQL.close();
        return clientes;
}


public List<Cliente> search(String s) throws Exception 
{
    //1. Generar la consulta

    String query="SELECT * FROM v_cliente WHERE nombre like '%"+s+"%'";

    //2.Generar la conexiÃ³n y abrirla 
    ConexionMySQL connMySQL=new ConexionMySQL(); Connection conn=connMySQL.open();

    //3. Generar el statement que lleva la consulta
    PreparedStatement pstmt=conn.prepareStatement(query); 
    //4. Ejecutar y alamacenar el resultado en un ResultSet 
    ResultSet rs=pstmt.executeQuery();

    //5. Generar la estructura que contendra toda la informaciÃ³n
    List<Cliente> clientes=new ArrayList<>();
    //6. Tomar todos los valores y colocarlos en la estructura
    while(rs.next() != false)
    {
    //Generamos y llenamos un objeto de tipo persona
    Persona per=new Persona();
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
    

    //Llenar los datos del objeto de tipo cliente
    Cliente c=new Cliente();
    c.setIdCliente(rs.getInt("idCliente"));
    c.setCorreo(rs.getString("correo"));
    c.setContrasenia(rs.getString("contrasenia")); 
    c.setPersona(per);
    
    //Se coloca el objeto clientes dentro de la estructura (lista de Clientes)
    clientes.add(c);
}

   //7. Cerrar los objetos

    rs.close();
    pstmt.close();
    connMySQL.close();
    return clientes;
}


    public static void main(String[] a)
    {

    ConexionMySQL connMySQL=new ConexionMySQL(); try (Connection conn = connMySQL.open()) {
    if(conn!=null)
    System.out.println("OK");

    } catch (Exception ex) { System.out.println(ex.toString());

    }
    }

}
