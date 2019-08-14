package org.tds.sgh.system;

import java.util.Set;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.Infrastructure;

public class CancelarReservaController implements ICancelarReservaController{

	private CadenaHotelera cadenaHotelera;
	private Cliente cliente;
	private Reserva reserva;
	
	public CancelarReservaController(CadenaHotelera c) {
		// TODO Auto-generated constructor stub
		this.cadenaHotelera = c;
	}
	@Override
	public Set<ClienteDTO> buscarCliente(String patronNombreCliente) {
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
	public Set<ReservaDTO> buscarReservasDelCliente() throws Exception {
		// TODO Auto-generated method stub
		Set<Reserva> reservasCliente = this.cadenaHotelera.buscarReservasDelCliente(this.cliente.getRut());
		return DTO.getInstance().mapReservas(reservasCliente);
	}

	@Override
	public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
		// TODO Auto-generated method stub
		if(cliente == null)
			reserva = this.cadenaHotelera.obtenerReservaPorCodigo(codigoReserva);
		else
			reserva = this.cadenaHotelera.seleccionarReserva(codigoReserva,this.cliente.getRut());
		return DTO.getInstance().mapReserva(reserva);
	}

	@Override
	public ReservaDTO cancelarReservaDelCliente() throws Exception {
		// TODO Auto-generated method stub
		Reserva res = this.cadenaHotelera.cancelarReservaDelCliente(this.cliente.getRut(),this.reserva.getCodigo());
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(res.getCliente().getMail(), "", "ok");
		return DTO.getInstance().mapReserva(res);
	}

}
