package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.Set;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.Infrastructure;

public class HacerReservaController implements IHacerReservaController {
	
	private CadenaHotelera cadenaHotelera;
	private Cliente cliente;
	
	public HacerReservaController(CadenaHotelera cadenaHotelera) {
		this.cadenaHotelera = cadenaHotelera;
	}

	@Override
	public Set<ClienteDTO> buscarCliente(String patronNombreCliente){
		// TODO Auto-generated method stub
		Set<Cliente> clientes = cadenaHotelera.buscarClientes(patronNombreCliente);
		return DTO.getInstance().mapClientes(clientes);
	}

	@Override
	public ClienteDTO seleccionarCliente(String rut) throws Exception {
		// TODO Auto-generated method stub
		Cliente seleccionado = cadenaHotelera.buscarCliente(rut);
		this.cliente = seleccionado;
		return DTO.getInstance().map(seleccionado);
	}

	@Override
	public ClienteDTO registrarCliente(String rut, String nombre, String direccion, String telefono, String mail)
			throws Exception {
		// TODO Auto-generated method stub
		Cliente cliente = cadenaHotelera.agregarCliente(rut, nombre, direccion, telefono, mail);
		this.cliente = cliente;
		return DTO.getInstance().map(cliente);
	}

	@Override
	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
		// TODO Auto-generated method stub
		return cadenaHotelera.confirmarDisponibilidad(nombreHotel, nombreTipoHabitacion, fechaInicio, fechaFin);
	}

	@Override
	public ReservaDTO registrarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		// TODO Auto-generated method stub
		Reserva res = cadenaHotelera.registrarReserva(cliente.getRut(),nombreHotel, nombreTipoHabitacion, fechaInicio, fechaFin, modificablePorHuesped);
					  		
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(res.getCliente().getMail(), "", "ok");
		
		return DTO.getInstance().map(res);
	}

	@Override
	public Set<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) throws Exception {
		// TODO Auto-generated method stub
		Set<Hotel> alternativas = cadenaHotelera.sugerirAlternativas(pais,nombreTipoHabitacion,fechaInicio,fechaFin);
		
		return DTO.getInstance().mapHoteles(alternativas);
	}

}
