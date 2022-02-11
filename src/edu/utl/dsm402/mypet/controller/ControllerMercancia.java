package edu.utl.dsm402.mypet.controller;
import edu.utl.dsm402.mypet.db.ConexionMySQL; 
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

*	@author Lolis */
public class ControllerMercancia {

public int insert(Mercancia m) throws Exception {
//1. Generar la llamada al prcedure
String query="{call insertProductoMercancia(?,?,?,?," + //Datos del producto
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
cstmt.setString(1,m.getProducto().getNombre());
cstmt.setInt(2,m.getProducto().getExistencias()); 
cstmt.setFloat(3,m.getProducto().getPrecioCompra());
cstmt.setString(4,m.getProducto().getFoto()); 
//Colocamos los datos de la mercancia 
cstmt.setString(5,m.getCodigoBarras()); 
cstmt.setString(6,m.getDescripcion());
cstmt.setString(7,m.getModelo()); 
cstmt.setString(8,m.getMarca());
//Se indican los parametros de salida

cstmt.registerOutParameter(9,Types.INTEGER);
cstmt.registerOutParameter(10,Types.INTEGER);
//5. Ejecutar
cstmt.execute();
//6. Tomar los IDs que se generaron
 
m.getProducto().setIdProducto(cstmt.getInt(9));
m.setIdMercancia(cstmt.getInt(10)); 
//7. Cierre de los objetos
cstmt.close();

connMySQL.close();
return m.getIdMercancia();
}

public void update(Mercancia m) throws Exception {

String sql = "{call updateProductoMercancia(?, ?, " + //ID de Producto y Mercancia
"?,?,?,"+
//Datos de Producto
"?, ?, ?, ?)}";
//Datos de Mercancia

ConexionMySQL connMySQL = new ConexionMySQL(); 
Connection conn = connMySQL.open();
CallableStatement cstmt = conn.prepareCall(sql);

//Llenamos los datos del Producto de acuerdo con los parÃ¡metros que pide
//el Stored Procedure:

cstmt.setInt(1, m.getProducto().getIdProducto());
cstmt.setInt(2, m.getIdMercancia());
cstmt.setString(3, m.getProducto().getNombre()); 
cstmt.setInt(4, m.getProducto().getExistencias()); 
cstmt.setDouble(5, m.getProducto().getPrecioCompra());
cstmt.setString(6, m.getCodigoBarras()); 
cstmt.setString(7, m.getDescripcion());
cstmt.setString(8, m.getModelo()); 
cstmt.setString(9, m.getMarca());

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



public List<Mercancia> getAll() throws Exception {
//1. Generar la consulta

String query="SELECT * FROM v_mercancias WHERE estatus=1"; 
//2.Generar la conexiÃ³n y abrirla

ConexionMySQL connMySQL=new ConexionMySQL();
Connection conn=connMySQL.open();

//3. Generar el statement que lleva la consulta 
PreparedStatement pstmt=conn.prepareStatement(query); 
//4. Ejecutar y alamacenar el resultado en un ResultSet 
ResultSet rs=pstmt.executeQuery();

//5. Generar la estructura que contendra toda la informaciÃ³n

List<Mercancia> mercancias=new ArrayList<>();
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
Mercancia m=new Mercancia();
m.setIdMercancia(rs.getInt("idMercancia"));
m.setCodigoBarras(rs.getString("codigoBarras"));
m.setDescripcion(rs.getString("descripcion"));
m.setMarca(rs.getString("marca"));
m.setModelo(rs.getString("modelo")); 
m.setProducto(p);

//Se coloca el objeto mercancia dentro de la estructura (lista de mercacias)
mercancias.add(m);
}
//7. Cerrar los objetos
rs.close();
pstmt.close();
connMySQL.close();
return mercancias;
}

public List<Mercancia> getAllInactive() throws Exception {
//1. Generar la consulta

String query="SELECT * FROM v_mercancias WHERE estatus=0"; 
//2.Generar la conexiÃ³n y abrirla

ConexionMySQL connMySQL=new ConexionMySQL();
Connection conn=connMySQL.open();

//3. Generar el statement que lleva la consulta 
PreparedStatement pstmt=conn.prepareStatement(query); 
//4. Ejecutar y alamacenar el resultado en un ResultSet 
ResultSet rs=pstmt.executeQuery();

//5. Generar la estructura que contendra toda la informaciÃ³n

List<Mercancia> mercancias=new ArrayList<>();
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
Mercancia m=new Mercancia();
m.setIdMercancia(rs.getInt("idMercancia"));
m.setCodigoBarras(rs.getString("codigoBarras"));
m.setDescripcion(rs.getString("descripcion"));
m.setMarca(rs.getString("marca"));
m.setModelo(rs.getString("modelo")); 
m.setProducto(p);

//Se coloca el objeto mercancia dentro de la estructura (lista de mercacias)
mercancias.add(m);
}
//7. Cerrar los objetos
rs.close();
pstmt.close();
connMySQL.close();
return mercancias;
}



public List<Mercancia> search(String s) throws Exception {
//1. Generar la consulta

String query="SELECT * FROM v_mercancias WHERE nombre like '%"+s+"%'";

//2.Generar la conexiÃ³n y abrirla 
ConexionMySQL connMySQL=new ConexionMySQL(); Connection conn=connMySQL.open();

//3. Generar el statement que lleva la consulta
PreparedStatement pstmt=conn.prepareStatement(query); 
//4. Ejecutar y alamacenar el resultado en un ResultSet 
ResultSet rs=pstmt.executeQuery();

//5. Generar la estructura que contendra toda la informaciÃ³n
List<Mercancia> mercancias=new ArrayList<>();
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
Mercancia m=new Mercancia(); 
m.setIdMercancia(rs.getInt("idMercancia"));
m.setCodigoBarras(rs.getString("codigoBarras")); 
m.setDescripcion(rs.getString("descripcion")); 
m.setMarca(rs.getString("marca"));
m.setModelo(rs.getString("modelo")); 
m.setProducto(p);
//Se coloca el objeto mercancia dentro de la estructura (lista de mercacias)
mercancias.add(m);
}
//7. Cerrar los objetos

rs.close();
pstmt.close();
connMySQL.close();
return mercancias;
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
