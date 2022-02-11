
package edu.utl.dsm402.mypet.controller;
import edu.utl.dsm402.mypet.db.ConexionMySQL; 
import edu.utl.dsm402.mypet.model.Animal;
import edu.utl.dsm402.mypet.model.Mercancia;
import edu.utl.dsm402.mypet.model.Producto;
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
public class ControllerMascotas {
    
    
    public int insert(Animal a) throws Exception {
//1. Generar la llamada al prcedure
String query="{call insertProductoAnimal(?,?,?,?," + //Datos del producto
"?,?,?,?,"+
"?,?)}";

//Valores de retorno

//2. Generar un objeto de la conexion y abrirla 
ConexionMySQL connMySQL=new ConexionMySQL();
Connection conn=connMySQL.open();

//3. Generar el callable statement que llevara la llamada al procedure

CallableStatement cstmt=conn.prepareCall(query); 
//4.Colocar los parametros que necesita el procedure
//Colocamos los datos del producto 
cstmt.setString(1,a.getProducto().getNombre());
cstmt.setInt(2,a.getProducto().getExistencias()); 
cstmt.setFloat(3,a.getProducto().getPrecioCompra());
cstmt.setString(4,a.getProducto().getFoto()); 
//Colocamos los datos de la mercancia 
cstmt.setString(5,a.getRaza()); 
cstmt.setString(6,a.getEspecie());
cstmt.setString(7,a.getFechaNacimiento()); 
cstmt.setString(8,a.getFechaLlegada());
//Se indican los parametros de salida

cstmt.registerOutParameter(9,Types.INTEGER);
cstmt.registerOutParameter(10,Types.INTEGER);
//5. Ejecutar
cstmt.execute();
//6. Tomar los IDs que se generaron
 
a.getProducto().setIdProducto(cstmt.getInt(9));
a.setIdAnimal(cstmt.getInt(9)); 
//7. Cierre de los objetos
cstmt.close();

connMySQL.close();
return a.getIdAnimal();
}

    
public List<Animal> getAll() throws Exception {
//1. Generar la consulta

String query="SELECT * FROM v_animales WHERE estatus=1"; 
//2.Generar la conexiÃ³n y abrirla

ConexionMySQL connMySQL=new ConexionMySQL();
Connection conn=connMySQL.open();

//3. Generar el statement que lleva la consulta 
PreparedStatement pstmt=conn.prepareStatement(query); 
//4. Ejecutar y alamacenar el resultado en un ResultSet 
ResultSet rs=pstmt.executeQuery();

//5. Generar la estructura que contendra toda la informaciÃ³n

List<Animal> animales=new ArrayList<>();
//6. Tomar todos los valores y colocarlos en la estructura
while(rs.next() != false)
{

//Generamos y llenamos un objeto de tipo producto 
    Producto p=new Producto();
    p.setIdProducto(rs.getInt("idProducto"));
    p.setExistencias(rs.getInt("existencias"));
 
p.setFoto(rs.getString("foto"));
p.setNombre(rs.getString("nombre"));
p.setPrecioCompra(rs.getFloat("precioCompra")); 
p.setEstatus(rs.getInt("estatus"));

//Llenar los datos del objeto de tipo mercancia 
Animal a=new Animal();
a.setIdAnimal(rs.getInt("idAnimal"));
a.setRaza(rs.getString("raza"));
a.setEspecie(rs.getString("especie"));
a.setFechaNacimiento(rs.getString("fechaNacimiento"));
a.setFechaLlegada(rs.getString("fechaLlegada")); 
a.setProducto(p);

//Se coloca el objeto mercancia dentro de la estructura (lista de mercacias)
animales.add(a);
}
//7. Cerrar los objetos
rs.close();
pstmt.close();
connMySQL.close();
return animales;
}    

public List<Animal> getAllInactive() throws Exception {
//1. Generar la consulta

String query="SELECT * FROM v_animales WHERE estatus=0"; 
//2.Generar la conexiÃ³n y abrirla

ConexionMySQL connMySQL=new ConexionMySQL();
Connection conn=connMySQL.open();

//3. Generar el statement que lleva la consulta 
PreparedStatement pstmt=conn.prepareStatement(query); 
//4. Ejecutar y alamacenar el resultado en un ResultSet 
ResultSet rs=pstmt.executeQuery();

//5. Generar la estructura que contendra toda la informaciÃ³n

List<Animal> animales=new ArrayList<>();
//6. Tomar todos los valores y colocarlos en la estructura
while(rs.next() != false)
{

//Generamos y llenamos un objeto de tipo producto 
    Producto p=new Producto();
    p.setIdProducto(rs.getInt("idProducto"));
    p.setExistencias(rs.getInt("existencias"));
 
p.setFoto(rs.getString("foto"));
p.setNombre(rs.getString("nombre"));
p.setPrecioCompra(rs.getFloat("precioCompra")); 
p.setEstatus(rs.getInt("estatus"));

//Llenar los datos del objeto de tipo mercancia 
Animal a=new Animal();
a.setIdAnimal(rs.getInt("idAnimal"));
a.setRaza(rs.getString("raza"));
a.setEspecie(rs.getString("especie"));
a.setFechaNacimiento(rs.getString("fechaNacimiento"));
a.setFechaLlegada(rs.getString("fechaLlegada")); 
a.setProducto(p);

//Se coloca el objeto mercancia dentro de la estructura (lista de mercacias)
animales.add(a);
}
//7. Cerrar los objetos
rs.close();
pstmt.close();
connMySQL.close();
return animales;
}

    
public void update(Animal a) throws Exception {

String sql = "{call updateProductoAnimal(?, ?, " + //ID de Producto y Mercancia
"?,?,?,"+
//Datos de Producto
"?, ?, ?, ?)}";
//Datos de Mercancia

ConexionMySQL connMySQL = new ConexionMySQL(); 
Connection conn = connMySQL.open();
CallableStatement cstmt = conn.prepareCall(sql);

//Llenamos los datos del Producto de acuerdo con los parÃ¡metros que pide
//el Stored Procedure:

cstmt.setInt(1, a.getProducto().getIdProducto());
cstmt.setInt(2, a.getIdAnimal());
cstmt.setString(3, a.getProducto().getNombre()); 
cstmt.setInt(4, a.getProducto().getExistencias()); 
cstmt.setDouble(5, a.getProducto().getPrecioCompra());
cstmt.setString(6, a.getRaza()); 
cstmt.setString(7, a.getEspecie());
cstmt.setString(8, a.getFechaNacimiento()); 
cstmt.setString(9, a.getFechaLlegada());


//Ejecutamos la consulta:
cstmt.execute();

//Cerramos los objetos de BD:
cstmt.close();
connMySQL.close();
}


public void delete(int idProducto) throws Exception {

//1. Generar la sentencia SQL que se envÃa 
String query="UPDATE producto SET estatus=0 WHERE idProducto="+idProducto;

//2. Generar un objeto de la conexion y abrirla
ConexionMySQL connMySQL=new ConexionMySQL();
Connection conn=connMySQL.open();
//3. Generar un statement que lleve las sentencias
 
Statement stmt=conn.createStatement(); //4. Se envÃa la sentencia sql (consulta)
stmt.executeUpdate(query); 
//5. Se cierrar el statement

stmt.close();
//6. Cierro la conexion
connMySQL.close();

return;
}

public void reactivate(int idProducto) throws Exception {

//1. Generar la sentencia SQL que se envÃa 
String query="UPDATE producto SET estatus=1 WHERE idProducto="+idProducto;

//2. Generar un objeto de la conexion y abrirla
ConexionMySQL connMySQL=new ConexionMySQL();
Connection conn=connMySQL.open();
//3. Generar un statement que lleve las sentencias
 
Statement stmt=conn.createStatement(); //4. Se envÃa la sentencia sql (consulta)
stmt.executeUpdate(query); 
//5. Se cierrar el statement

stmt.close();
//6. Cierro la conexion
connMySQL.close();

return;
}

public List<Animal> search(String s) throws Exception {
//1. Generar la consulta

String query="SELECT * FROM v_animales WHERE nombre like '%"+s+"%'";

//2.Generar la conexiÃ³n y abrirla 
ConexionMySQL connMySQL=new ConexionMySQL(); Connection conn=connMySQL.open();

//3. Generar el statement que lleva la consulta
PreparedStatement pstmt=conn.prepareStatement(query); 
//4. Ejecutar y alamacenar el resultado en un ResultSet 
ResultSet rs=pstmt.executeQuery();

//5. Generar la estructura que contendra toda la informaciÃ³n
List<Animal> animales=new ArrayList<>();
//6. Tomar todos los valores y colocarlos en la estructura
while(rs.next() != false)
{

//Generamos y llenamos un objeto de tipo producto 
    Producto p=new Producto(); p.setIdProducto(rs.getInt("idProducto")); 
    p.setExistencias(rs.getInt("existencias"));
    p.setFoto(rs.getString("foto")); p.setNombre(rs.getString("nombre"));
    p.setPrecioCompra(rs.getFloat("precioCompra")); 
    p.setEstatus(rs.getInt("estatus"));
 
//Llenar los datos del objeto de tipo mercancia
Animal a=new Animal(); 
a.setIdAnimal(rs.getInt("IdAnimal"));
a.setRaza(rs.getString("raza")); 
a.setEspecie(rs.getString("especie")); 
a.setFechaNacimiento(rs.getString("fechaNacimiento"));
a.setFechaLlegada(rs.getString("fechaLlegada")); 
a.setProducto(p);
//Se coloca el objeto mercancia dentro de la estructura (lista de mercacias)
animales.add(a);
}
//7. Cerrar los objetos

rs.close();
pstmt.close();
connMySQL.close();
return animales;
}

}



