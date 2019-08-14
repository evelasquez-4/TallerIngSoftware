package org.tds.sgh.business;


import java.util.GregorianCalendar;
import java.util.HashMap;

import java.util.Map;

public class Reserva {

	private long codigo;
	private GregorianCalendar fechaFin;	
	private GregorianCalendar fechaInicio;
	
	private boolean modificablePorHuesped;
	
	private String estado;
	
	private Map<String, Huesped> huespedes;
	
	private Hotel hotel;
	private Cliente cliente;
	private TipoHabitacion tipoHabitacion;
	private Habitacion habitacion;
	
	
	public Reserva(Hotel hotel,Cliente cliente,GregorianCalendar fechaInicio,GregorianCalendar fechaFin,
			boolean modificablePorHuesped, TipoHabitacion tipoHabitacion)
	{
		this.hotel = hotel;
		this.cliente=cliente;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tipoHabitacion=tipoHabitacion;
		this.modificablePorHuesped = modificablePorHuesped;
		
		this.huespedes = new HashMap<String, Huesped>();
		
		this.estado = String.valueOf(EstadoReserva.Pendiente);
		this.habitacion=new Habitacion(null,null);
		
	}
	
	public Huesped[] huespedesToArray(){
		Huesped[] res = new Huesped[huespedes.size()];
		
		int i= 0;
		
		if(huespedes.size() > 0) {
			for(Huesped h : huespedes.values()) {
				res[i] = h;
				i++;
			}
		}
		
		return res;
		 
	} 

	public long getCodigo() {
		return codigo;
	}


	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}


	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}


	public GregorianCalendar getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public boolean isModificablePorHuesped() {
		return modificablePorHuesped;
	}


	public void setModificablePorHuesped(boolean modificablePorHuesped) {
		this.modificablePorHuesped = modificablePorHuesped;
	}

	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String ne) {
		this.estado = ne;
	}

//	public Estado getEstado() {
//		return estado;
//	}
//
//
//	public void setEstado(Estado estado) {
//		this.estado = estado;
//	}


	public Map<String, Huesped> getHuespedes() {
		return huespedes;
	}


	public void setHuespedes(Map<String, Huesped> huespedes) {
		this.huespedes = huespedes;
	}


	public Hotel getHotel() {
		return hotel;
	}


	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}


	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}


	public Habitacion getHabitacion() {
		return habitacion;
	}


	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	
	public void registrarHuesped(String documento,String nombre) {
		Huesped h = new Huesped(nombre, documento);
		
		this.huespedes.put(documento, h);
		
	}
	
	
	
	
}

enum EstadoReserva  {
	Pendiente,
	Tomada,
	Finalizada,
	Cancelada,
	NoTomada
}
