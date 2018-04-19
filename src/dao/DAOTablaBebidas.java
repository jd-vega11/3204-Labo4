package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Bebida;

public class DAOTablaBebidas {

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOTablaProductos
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaBebidas() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexion que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	public ArrayList<Bebida> darBebidas() throws SQLException, Exception {
		ArrayList<Bebida> listaBebidas = new ArrayList<Bebida>();

		String sql = "SELECT * FROM BEBIDAS";

		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			double precio=rs.getDouble("PRECIO");
			int cantidad=rs.getInt("CANTIDAD");
			Bebida b=new Bebida();
			b.setNombre(name);
			b.setCantidad(cantidad);
			b.setPrecio(precio);
			listaBebidas.add(b);
		}
		return listaBebidas;
		
	}
	
	public void reabastecerCerveza() throws SQLException, Exception {

		String sql = "UPDATE BEBIDAS SET CANTIDAD=15 WHERE NOMBRE='AguilAlpes'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();		
	}

	


}

