package org.tds.sgh.system;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.infrastructure.NotImplementedException;


public class ControllerFactory implements IControllerFactory
{
	// --------------------------------------------------------------------------------------------
	
	private CadenaHotelera cadenaHotelera;
	
	// --------------------------------------------------------------------------------------------
	
	public ControllerFactory(CadenaHotelera cadenaHotelera)
	{
		this.cadenaHotelera = cadenaHotelera;
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public ICadenaController createCadenaController()
	{
		return new CadenaController(this.cadenaHotelera);
	}
	
	@Override
	public ICancelarReservaController createCancelarReservaController()
	{
		// TODO
		return new CancelarReservaController(this.cadenaHotelera); 
	}
	
	@Override
	public IHacerReservaController createHacerReservaController()
	{
		// TODO
		return new HacerReservaController(this.cadenaHotelera);
	}
	
	@Override
	public IModificarReservaController createModificarReservaController()
	{
		// TODO
		return new ModificarReservaController(this.cadenaHotelera);
	}
	
	@Override
	public ITomarReservaController createTomarReservaController()
	{
		// TODO
		return new TomarReservaController(this.cadenaHotelera); 
	}
}
