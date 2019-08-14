
package org.tds.sgh.business;

public class Huesped {

	private String nombre;
	
	private String documento;
	
	public Huesped(String n, String d) {
		this.nombre = n;
		this.documento = d;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	
}