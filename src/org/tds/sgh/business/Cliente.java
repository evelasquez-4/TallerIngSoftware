package org.tds.sgh.business;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tds.sgh.infrastructure.ICalendario;
import org.tds.sgh.infrastructure.Infrastructure;

public class Cliente
{
	// --------------------------------------------------------------------------------------------
	
	private String direccion;
	
	private String mail;
	
	private String nombre;
	
	private String rut;
	
	private String telefono;
	
	private Set<Reserva> misReservas = new HashSet<Reserva>();
	
	// --------------------------------------------------------------------------------------------
	
	

	public Cliente(String rut, String nombre, String direccion, String telefono, String mail)
	{
		this.direccion = direccion;
		
		this.mail = mail;
		
		this.nombre = nombre;
		
		this.rut = rut;
		
		this.telefono = telefono;
		
		this.misReservas = new HashSet<Reserva>();
	}
	
	// --------------------------------------------------------------------------------------------
	public void eliminarReservaCliente(long codigo) {
		this.misReservas.remove(obtenerReservaPorCondigo(codigo));
	}
	
	public boolean coincideElNombre(String patronNombreCliente)
	{
		return this.nombre.matches(patronNombreCliente);
	}
	
	public String getDireccion()
	{
		return this.direccion;
	}
	
	public String getMail()
	{
		return this.mail;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getRut()
	{
		return this.rut;
	}
	
	public String getTelefono()
	{
		return this.telefono;
	}
	
	public Reserva agregarReserva(Reserva r) {
		
		//if( !existeCodigo(r) )
			misReservas.add(r);
		return r;
	}
	
	public Reserva obtenerReservaPorCondigo(long codigo)
	{
		Reserva res = null;
		
		for (Reserva r : misReservas) {
			
			if(r.getCodigo() == codigo)
				res = r;
		}
		return res;
		
	}
	public boolean existeCodigo(Reserva r) {
		boolean res = false;
		for(Reserva i:misReservas) {
			if(r.getCodigo() == i.getCodigo())
				res =true;
		}
		return res;
	}
	
	public Set<Reserva> getMisReservas() {
		return misReservas;
	}

	public void setMisReservas(Set<Reserva> misReservas) {
		this.misReservas = misReservas;
	}
	public void clearReservas() {
		misReservas.clear();
	}
	
	public Set<Reserva> buscarReservasCliente(String rutCliente){
		
		Set<Reserva> reservas = new HashSet<Reserva>();
		Cliente c = this;
		ICalendario cal = Infrastructure.getInstance().getCalendario();
		
		for(Reserva res : c.getMisReservas())
		{
			//if(res.getEstado() != String.valueOf(EstadoReserva.Cancelada))
			if(!(cal.esAnterior(res.getFechaFin(), cal.getHoy())) && 
					(res.getEstado() != String.valueOf(EstadoReserva.Tomada))  )
				reservas.add(res);
		}
		
		return reservas;
	}

}
