package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tds.sgh.infrastructure.ICalendario;
import org.tds.sgh.infrastructure.Infrastructure;


public class Hotel
{
	// --------------------------------------------------------------------------------------------
	
	private Map<String, Habitacion> habitaciones;
	
	private String nombre;
	
	private String pais;
	
	private Map<Long, Reserva> reservas = new HashMap<Long, Reserva>();
	
	// --------------------------------------------------------------------------------------------
	
	public Hotel(String nombre, String pais)
	{
		this.habitaciones = new HashMap<String, Habitacion>();
		
		this.nombre = nombre;
		
		this.pais = pais;
	}
	
	// --------------------------------------------------------------------------------------------
	
	public Habitacion agregarHabitacion(TipoHabitacion tipoHabitacion, String nombre) throws Exception
	{
		if (this.habitaciones.containsKey(nombre))
		{
			throw new Exception("El hotel ya tiene una habitación con el nombre indicado.");
		}
		
		Habitacion habitacion = new Habitacion(tipoHabitacion, nombre);
		
		this.habitaciones.put(habitacion.getNombre(), habitacion);
		
		return habitacion;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getPais()
	{
		return this.pais;
	}
	
	public Set<Habitacion> listarHabitaciones()
	{
		return new HashSet<Habitacion>(this.habitaciones.values());
	}
	
	public Set<Reserva> listarReservas(){
		return new HashSet<Reserva>(this.reservas.values());
	}
	
	public Reserva buscarReservaPorCodigo(long codigo) {
		Reserva res = null;
		for(Reserva r : reservas.values()) {
			if(r.getCodigo() == codigo)
				res = r;
		}
		return res;
	}
	//obtener habitaciones de un tipoHabitacion
	public Set<Habitacion> obtenerHabitacionesPorTipoHabitacion(TipoHabitacion tipo)
	{
		Set<Habitacion> res = new HashSet<Habitacion>();
		
		for(Habitacion h:habitaciones.values()) 
		{
			if(h.getTipoHabitacion().getNombre().equals(tipo.getNombre()))
				res.add(h);
		}
		return res;
	}
	
	
	public boolean estaEntreFechasCerrado(Reserva r,GregorianCalendar fi,GregorianCalendar ff)
	{
		ICalendario cal = Infrastructure.getInstance().getCalendario();
		//fi o ff de reserva esta entre  fechas
		boolean c1 = (cal.esPosterior(r.getFechaInicio(), fi) || cal.esMismoDia(r.getFechaInicio(), fi)) 
						&& ( cal.esAnterior(r.getFechaInicio(), ff) || cal.esMismoDia(r.getFechaInicio(), ff));
		
		//ff reserva esta entre las fechas
		boolean c2 = ( cal.esPosterior(r.getFechaFin(), fi) ||  cal.esMismoDia(r.getFechaFin(), fi) )&&
					( cal.esAnterior(r.getFechaFin(), ff) || cal.esMismoDia(r.getFechaFin(), ff) );
		
	
		
		return (c1 && c2);
	}
	
	//cantidad de reservas entre fechas
	public Set<Reserva> obtenerReservasEntreFechaCerrado(GregorianCalendar fi,GregorianCalendar ff)
	{
		Set<Reserva> res = new HashSet<Reserva>();
		
		for(Reserva r :this.reservas.values() ) {
			
			if( estaEntreFechasCerrado(r, fi, ff) )
				res.add(r);
		}
		
		return res;
	}
	
	public boolean estaEntreFechas(Reserva r,GregorianCalendar fi,GregorianCalendar ff)
	{
		ICalendario cal = Infrastructure.getInstance().getCalendario();
		//fi o ff de reserva esta entre  fechas
		boolean c1 = ( cal.esAnterior(r.getFechaInicio(), fi) || cal.esPosterior(r.getFechaInicio(), fi) || cal.esMismoDia(r.getFechaInicio(), fi) ) 
				&& ( cal.esAnterior(r.getFechaInicio(), ff) /*|| cal.esMismoDia(r.getFechaInicio(), ff) */);
		
		//ff reserva esta entre las fechas
		boolean c3 = //cal.esAnterior(r.getFechaInicio(), fi) &&
					(cal.esPosterior(r.getFechaFin(), fi) || cal.esPosterior(r.getFechaFin(), ff)	
				  || cal.esMismoDia(r.getFechaInicio(), fi) || cal.esMismoDia(r.getFechaFin(), ff) );
		
	
		
		return (c1 && c3);
//		return ( cal.esAnterior(fi, r.getFechaFin())  )&& ( cal.esAnterior(r.getFechaInicio(), ff) );
	}
	//cantidad de reservas entre fechas
	public Set<Reserva> obtenerReservasEntreFecha(GregorianCalendar fi,GregorianCalendar ff)
	{
		Set<Reserva> res = new HashSet<Reserva>();
		
		for(Reserva r :this.reservas.values() ) {
			
			if( estaEntreFechas(r, fi, ff) )
			{
					res.add(r);				
			}
		}
		
		return res;
	}
	
	public boolean confirmarDisponibilidad(TipoHabitacion tipoHabitacion,GregorianCalendar fechaInicio, GregorianCalendar fechaFin)
	{
		//ICalendario cal = Infrastructure.getInstance().getCalendario();

		Set<Habitacion> habitacionesTipo = obtenerHabitacionesPorTipoHabitacion(tipoHabitacion);
		Set<Reserva> reservasEntreFechas = obtenerReservasEntreFecha(fechaInicio, fechaFin);
//		Set<Reserva> reservasEntreFechas = obtenerReservasEntreFechaCerrado(fechaInicio, fechaFin);
		
		Set<Reserva> reservasTipo = new HashSet<Reserva>(); 
		
		for(Reserva r : reservasEntreFechas) {
			if(r.getTipoHabitacion().getNombre().equals(tipoHabitacion.getNombre())
					&& (r.getEstado() != String.valueOf(EstadoReserva.Cancelada) 
					 )  )
			{
					reservasTipo.add(r);
			}
		}
		
		return habitacionesTipo.size() > reservasTipo.size();
	}
	
	public Reserva registraReserva(Cliente cliente,TipoHabitacion tipoHab,GregorianCalendar fechaInicio,GregorianCalendar fechaFin,boolean modificablePorHuesped) {
		//TODO
		Reserva res = new Reserva(this, cliente, fechaInicio, fechaFin, modificablePorHuesped,tipoHab);
		reservas.put(res.getCodigo(), res);		
		
		return res;
	}
	
	public void registraHuesped(Reserva res, String nombre, String documento)
	{
		Reserva r = buscarReservaPorCodigo(res.getCodigo());
		r.registrarHuesped(documento, nombre);
		
	}
	
	public Habitacion buscarHabitacionPorTipoEntreFechas(TipoHabitacion th,GregorianCalendar fi, GregorianCalendar ff)
	{
		Habitacion opcion = new Habitacion(null, null);
		
		Set<Habitacion> habs = obtenerHabitacionesPorTipoHabitacion(th);
		Set<Reserva> reserv = obtenerReservasEntreFechaCerrado(fi,ff);
		
		for(Habitacion habitacion : habs)
		{
			for(Reserva reserva : reserv) {
				
				if(reserva.getTipoHabitacion().getNombre() == habitacion.getTipoHabitacion().getNombre()  
						&& reserva.getEstado() == String.valueOf( EstadoReserva.Pendiente ) )
				{
							opcion = habitacion;
				}
			}
		}
		
		return opcion;
	}
	
	
	public Reserva tomarReserva(long idReserva) {
		Reserva reserva = buscarReservaPorCodigo(idReserva);
		Habitacion habitacion = buscarHabitacionPorTipoEntreFechas(reserva.getTipoHabitacion(), reserva.getFechaInicio(), reserva.getFechaFin());
		reserva.setEstado(String.valueOf(EstadoReserva.Tomada));
		reserva.setHabitacion(habitacion);
		
		return reserva;
	}
	
	public Set<Reserva> buscarReservasDelCliente(Cliente cliente){
		Set<Reserva> res =new HashSet<Reserva>();
		
		for(Reserva r: reservas.values()) {
			if(r.getCliente().getRut().equals(cliente.getRut() ) )
				res.add(r);
		}
		
		return res;
	}
	
	public Set<Reserva> buscarReservasPorEstado(String estado){
		
		Set<Reserva> res = new HashSet<Reserva>();
		for(Reserva r : reservas.values()) {
			if( r.getEstado().equals(estado) )
				res.add(r);
		}
		
		return res;
	}

	
	public Reserva modificarReserva(long codigoReserva,Hotel hotelNuevo, TipoHabitacion tipo,
			GregorianCalendar fi,GregorianCalendar ff, boolean modificable)
	{
		Reserva res = buscarReservaPorCodigo(codigoReserva);
		//new Reserva(hotelNuevo, cli, fi, ff, modificable, tipo);
		
		if(res.isModificablePorHuesped())
		{
		
			res.setHotel(hotelNuevo);
//			res.setCodigo(codigoReserva);
//			res.setCliente(cli);
			res.setTipoHabitacion(tipo);
			res.setFechaInicio(fi);
			res.setFechaFin(ff);
			res.setModificablePorHuesped(modificable);
			//res.setHuespedes(new HashMap<String, Huesped>());
		}
		
		
		return res;
	}
	
	public Reserva eliminarReserva(long code)
	{
//		Reserva r = buscarReservaPorCodigo(code);
//		r.setEstado(String.valueOf(EstadoReserva.Cancelada));
		return this.reservas.remove(code);
	}
	
	public boolean confirmarDisponibilidadTomarReserva(String nombreCliente,Hotel hotel,TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin)
	{
		Set<Habitacion> habitacionesTipo = obtenerHabitacionesPorTipoHabitacion(tipoHabitacion);
		Set<Reserva> reservasEntreFechas = obtenerReservasEntreFecha(fechaInicio, fechaFin);

		
		Set<Reserva> reservasTipo = new HashSet<Reserva>(); 
		
		for(Reserva r : reservasEntreFechas) {
			if(r.getTipoHabitacion().getNombre().equals(tipoHabitacion.getNombre())
					  &&(r.getCliente().getNombre() != nombreCliente)  )
			{
					reservasTipo.add(r);
			}
		}
		return habitacionesTipo.size() > reservasTipo.size();
	}
}
