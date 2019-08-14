package org.tds.sgh.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Habitacion;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.Huesped;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.business.TipoHabitacion;


public class DTO
{
	// --------------------------------------------------------------------------------------------
	
	private static final DTO Instance = new DTO();
	
	// --------------------------------------------------------------------------------------------
	
	public static DTO getInstance()
	{
		return Instance;
	}
	
	// --------------------------------------------------------------------------------------------
	
	private DTO()
	{
	}
	
	// --------------------------------------------------------------------------------------------
	
	public ClienteDTO map(Cliente cliente)
	{
		return new ClienteDTO(
			cliente.getRut(),
			cliente.getNombre(),
			cliente.getDireccion(),
			cliente.getTelefono(),
			cliente.getMail());
	}
	
	public HotelDTO map(Hotel hotel)
	{
		return new HotelDTO(hotel.getNombre(), hotel.getPais());
	}
	
	public HabitacionDTO map(Hotel hotel, Habitacion habitacion)
	{
		return new HabitacionDTO(hotel.getNombre(), habitacion.getTipoHabitacion().getNombre(), habitacion.getNombre());
	}
	
	public TipoHabitacionDTO map(TipoHabitacion tipoHabitation)
	{
		return new TipoHabitacionDTO(tipoHabitation.getNombre());
	}
	
	public Set<ClienteDTO> mapClientes(Set<Cliente> clientes)
	{
		Set<ClienteDTO> clientesDTO = new HashSet<ClienteDTO>();
		
		for (Cliente cliente : clientes)
		{
			clientesDTO.add(this.map(cliente));
		}
		
		return clientesDTO;
	}
	
	public Set<HabitacionDTO> mapHabitaciones(Hotel hotel, Set<Habitacion> habitaciones)
	{
		Set<HabitacionDTO> habitacionesDTO = new HashSet<HabitacionDTO>();
		
		for (Habitacion habitacion : habitaciones)
		{
			habitacionesDTO.add(this.map(hotel, habitacion));
		}
		
		return habitacionesDTO;
	}
	
	public Set<HotelDTO> mapHoteles(Set<Hotel> hoteles)
	{
		Set<HotelDTO> hotelesDTO = new HashSet<HotelDTO>();
		
		for (Hotel hotel : hoteles)
		{
			hotelesDTO.add(this.map(hotel));
		}
		
		return hotelesDTO;
	}
	
	public Set<TipoHabitacionDTO> mapTiposHabitacion(Set<TipoHabitacion> tiposHabitacion)
	{
		Set<TipoHabitacionDTO> tiposHabitacionDTO = new HashSet<TipoHabitacionDTO>();
		
		for (TipoHabitacion tipoHabitacion : tiposHabitacion)
		{
			tiposHabitacionDTO.add(this.map(tipoHabitacion));
		}
		
		return tiposHabitacionDTO;
	}
	
	public Set<ReservaDTO> mapReservas(Set<Reserva> reservas){
		Set<ReservaDTO> reservaDTO = new HashSet<ReservaDTO>();
		
		for(Reserva res:reservas) {
			reservaDTO.add(mapReserva(res));
		}
		
		return reservaDTO;
	}
	
	public ReservaDTO map(Reserva reserva) {
	
		return new ReservaDTO(
				reserva.getCodigo(),
				reserva.getCliente().getRut(), 
				reserva.getHotel().getNombre(), 
				reserva.getTipoHabitacion().getNombre(),
				reserva.getFechaInicio(), reserva.getFechaFin(),
				reserva.isModificablePorHuesped(), 
				reserva.getEstado(), 
				reserva.getHabitacion().getNombre()
				);
	}

	public ReservaDTO mapReserva(Reserva reserva)
	{
		return new ReservaDTO(reserva.getCodigo(),
				reserva.getCliente().getRut(), 
				reserva.getHotel().getNombre(), 
				reserva.getTipoHabitacion().getNombre(),
				reserva.getFechaInicio(), reserva.getFechaFin(),
				reserva.isModificablePorHuesped(), 
				reserva.getEstado(), 
				reserva.getHabitacion().getNombre(),
				mapHuespedes(reserva.huespedesToArray())
				);
	}
	
	public HuespedDTO mapHuesped(Huesped huesped)
	{
		return new HuespedDTO(huesped.getNombre(),huesped.getDocumento());
	}
	
	public HuespedDTO[] mapHuespedes(Huesped[] huespedes)
	{
		HuespedDTO[]  huespedDTO = new HuespedDTO[huespedes.length];
		
		if(huespedes.length > 0)
		{
			int i = 0;
			
			for(Huesped h : huespedes) {
				huespedDTO[i] = this.mapHuesped(h);
				i++;
			}
		}
		
		return huespedDTO;
	}

}
