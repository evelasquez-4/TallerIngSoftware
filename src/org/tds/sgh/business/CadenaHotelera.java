package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class CadenaHotelera
{
	// --------------------------------------------------------------------------------------------
	
	private Map<String, Cliente> clientes;
	
	private Map<String, Hotel> hoteles;
	
	private String nombre;
	
	private Map<String, TipoHabitacion> tiposHabitacion;
	
	// --------------------------------------------------------------------------------------------
	
	public CadenaHotelera(String nombre)
	{
		this.clientes = new HashMap<String, Cliente>();
		
		this.hoteles = new HashMap<String, Hotel>();
		
		this.nombre = nombre;
		
		this.tiposHabitacion = new HashMap<String, TipoHabitacion>();
	}
	
	// --------------------------------------------------------------------------------------------
	
	public Cliente agregarCliente(
		String rut,
		String nombre,
		String direccion,
		String telefono,
		String mail) throws Exception
	{
		if (this.clientes.containsKey(rut))
		{
			throw new Exception("Ya existe un cliente con el RUT indicado.");
		}
		
		Cliente cliente = new Cliente(rut, nombre, direccion, telefono, mail);
		
		this.clientes.put(cliente.getRut(), cliente);
		
		return cliente;
	}
	
	public Hotel agregarHotel(String nombre, String pais) throws Exception
	{
		if (this.hoteles.containsKey(nombre))
		{
			throw new Exception("Ya existe un hotel con el nombre indicado.");
		}
		
		Hotel hotel = new Hotel(nombre, pais);
		
		this.hoteles.put(hotel.getNombre(), hotel);
		
		return hotel;
	}
	
	public TipoHabitacion agregarTipoHabitacion(String nombre) throws Exception
	{
		if (this.tiposHabitacion.containsKey(nombre))
		{
			throw new Exception("Ya existe un tipo de habitación con el nombre indicado.");
		}
		
		TipoHabitacion tipoHabitacion = new TipoHabitacion(nombre);
		
		this.tiposHabitacion.put(tipoHabitacion.getNombre(), tipoHabitacion);
		
		return tipoHabitacion;
	}
	
	public Cliente buscarCliente(String rut) throws Exception
	{
		Cliente cliente = this.clientes.get(rut);
		
		if (cliente == null)
		{
			throw new Exception("No existe un cliente con el nombre indicado.");
		}
		
		return cliente;
	}
	
	public Set<Cliente> buscarClientes(String patronNombreCliente) 
	{
		Set<Cliente> clientesEncontrados = null;
		clientesEncontrados = new HashSet<Cliente>();
		
		if (patronNombreCliente.length() > 0) {

			for (Cliente cliente : this.clientes.values()) {
				if (cliente.coincideElNombre(patronNombreCliente)) {
					clientesEncontrados.add(cliente);
				}
			}
		}
		return clientesEncontrados;
		
	}
	
	public Hotel buscarHotel(String nombre) throws Exception
	{
		Hotel hotel = this.hoteles.get(nombre);
		
		if (hotel == null)
		{
			throw new Exception("No existe un hotel con el nombre indicado.");
		}
		
		return hotel;
	}
	
	public TipoHabitacion buscarTipoHabitacion(String nombre) throws Exception
	{
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombre);
		
		if (tipoHabitacion == null)
		{
			throw new Exception("No existe un tipo de habitación con el nombre indicado.");
		}
		
		return tipoHabitacion;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public Set<Cliente> listarClientes()
	{
		return new HashSet<Cliente>(this.clientes.values());
	}
	
	public Set<Hotel> listarHoteles()
	{
		return new HashSet<Hotel>(this.hoteles.values());
	}
	
	public Set<TipoHabitacion> listarTiposHabitacion()
	{
		return new HashSet<TipoHabitacion>(this.tiposHabitacion.values());
	}
	
	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception
	{		
		Hotel hotel = buscarHotel(nombreHotel);
		TipoHabitacion tipoHabitacion =  buscarTipoHabitacion(nombreTipoHabitacion);
		
		
		return hotel.confirmarDisponibilidad(tipoHabitacion, fechaInicio, fechaFin);
	}
	
	public Reserva registrarReserva(String idCliente,String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception
	{
		Cliente cli = buscarCliente(idCliente);
		Hotel ho = buscarHotel(nombreHotel);
		TipoHabitacion th = buscarTipoHabitacion(nombreTipoHabitacion);
		
		Reserva res = ho.registraReserva(cli, th , fechaInicio, fechaFin, modificablePorHuesped);
		
		cli.agregarReserva(res);
		
		return res;
	}
	
	public Set<Hotel> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,GregorianCalendar fechaFin) throws Exception
	{
		Set<Hotel> alternativas = new HashSet<Hotel>();
		TipoHabitacion th = buscarTipoHabitacion(nombreTipoHabitacion);
		
		for(Hotel h : hoteles.values()) {
			if(h.getPais().equals(pais)) {
				if( h.confirmarDisponibilidad(th, fechaInicio, fechaFin) )
				{
					alternativas.add(h);
				}
			}
		}
		
		return alternativas;
	}
	
	public Set<Reserva> seleccionarReserva(long codigoReserva){
		Set<Reserva> res = new HashSet<Reserva>();
		
		for(Hotel h:hoteles.values())
		{
			for(Reserva r : h.listarReservas()) {
				if(r.getCodigo() == codigoReserva)
					res.add(r);
			}
		}
		
		return res;
	}
	
	public Reserva obtenerReservaPorCodigo(long codigo)
	{
		Reserva res = null;
		for(Hotel h :hoteles.values()) {
			if(h.buscarReservaPorCodigo(codigo) != null)
				res = h.buscarReservaPorCodigo(codigo);
		}
		
		return res;
	}
	
	public Reserva registrarHuesped(long idReserva,String nombre, String documento) throws Exception {
		
		Reserva res = obtenerReservaPorCodigo(idReserva);
		Hotel hotel = buscarHotel(res.getHotel().getNombre());
		hotel.registraHuesped(res,nombre,documento);
		
		return res;
	}
	
	public Reserva tomarReserva(String nombreHotel,long idReserva) throws Exception {
		
		Hotel hotel = buscarHotel(nombreHotel);
		Reserva reserva= hotel.tomarReserva(idReserva);		
			
		return reserva;
	}
	
	public Set<Reserva> buscarReservasDelCliente(String rutCliente) throws Exception{
		Set<Reserva> reservas = new HashSet<Reserva>();
		Cliente c = buscarCliente(rutCliente);
		
		for(Hotel hotel: hoteles.values()) {
			
			for(Reserva res : hotel.buscarReservasDelCliente(c))
			{
				//if(res.getEstado() != String.valueOf(EstadoReserva.Cancelada))
					reservas.add(res);
			}
		}
		return reservas;
	}
	
	public Reserva seleccionarReserva(long codigoReserva, String idCLiente) throws Exception {
		Cliente cli = buscarCliente(idCLiente);
		Reserva res = null;
		
		for(Reserva r: buscarReservasDelCliente(cli.getRut()))
		{
			if(r.getCodigo() == codigoReserva)
				res = r;
		}
		
		return res;
		
	}
	
	public Set<Reserva> buscarReservasPendientes(String nombreHotel) throws Exception{
		Hotel h = buscarHotel(nombreHotel); 
		Set<Reserva> res = h.buscarReservasPorEstado(String.valueOf(EstadoReserva.Pendiente));
		return res;
	}
	
	public Reserva cancelarReservaDelCliente(String rutCliente,long codigoReserva) throws Exception {
		
		Reserva res = obtenerReservaPorCodigo(codigoReserva);
		res.setEstado(String.valueOf(EstadoReserva.Cancelada));
		
		for(Reserva r :buscarReservasDelCliente(rutCliente) )
		{
			if(r.getCodigo() == codigoReserva)
				r.setEstado(String.valueOf(EstadoReserva.Cancelada));
		}
		
		return res;
	}
	

	
	public Reserva modificarReserva(String hotelAnt,String rutCliente,long codigoReserva,String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		
		Hotel hotelNuevo = buscarHotel(nombreHotel);
		TipoHabitacion th = buscarTipoHabitacion(nombreTipoHabitacion);
		
		Hotel hotelAnterior = buscarHotel(hotelAnt);
		
		if(hotelAnterior.getNombre() == hotelNuevo.getNombre())
		{
				
			Reserva res = hotelAnterior.modificarReserva(codigoReserva, hotelNuevo, th,
					fechaInicio, fechaFin, modificablePorHuesped);
			// eliminarReserva(codigoReserva);
			// Reserva aux = hotelAnt.eliminarReserva(codigoReserva);
			return res;
		}else {
			
			Cliente cliente = buscarCliente(rutCliente);
			cliente.obtenerReservaPorCondigo(codigoReserva).setCodigo(codigoReserva+1);
			
			Reserva res = hotelNuevo.registraReserva(cliente, th, fechaInicio, fechaFin, modificablePorHuesped) ;
			hotelAnterior.eliminarReserva(codigoReserva);
			return res;
		}
		
		
	}
	
	public void modificarReservasCliente(Reserva reserva,String c) throws Exception {
		
		Cliente cli = buscarCliente(c);
		
		for(Reserva r: cli.getMisReservas()) {
			
			if(r.getHotel().getNombre() == reserva.getHotel().getNombre()
				&& r.getCodigo() == reserva.getCodigo()	 ) {
				//r.setHotel(re.getHotel());
				r.setEstado(String.valueOf(EstadoReserva.Cancelada));

			}
		}
	}
	
	public boolean confirmarDisponibilidadTomarReserva(String nombreCliente, String nombreHotel,String nombreTipoHabitacion,GregorianCalendar fi,GregorianCalendar ff) throws Exception
	{
		TipoHabitacion tipohab = buscarTipoHabitacion(nombreTipoHabitacion);
		Hotel hotel = buscarHotel(nombreHotel); 
		boolean res = hotel.confirmarDisponibilidadTomarReserva(nombreCliente,hotel, tipohab, fi, ff);
		
		return res;
	}
	
	
}
