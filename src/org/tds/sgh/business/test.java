package org.tds.sgh.business;

import java.util.GregorianCalendar;

import org.tds.sgh.infrastructure.Infrastructure;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Infrastructure infrastructure  = Infrastructure.getInstance();

		
		GregorianCalendar fecha1 = new GregorianCalendar();
		GregorianCalendar fecha2 = new GregorianCalendar();
		
		TipoHabitacion a = new TipoHabitacion("simple");
		TipoHabitacion b = new TipoHabitacion("simple");
		Hotel h =new Hotel("hotel1", "pais1");
		Cliente c = new Cliente("1", "Carlos", "Perez", "telf", "mail");
		
		Reserva r1 = new Reserva(h, c, fecha1, fecha2, true, a);
		Reserva r2 = new Reserva(h, c, fecha1, fecha2, true, b);
		
//		boolean ba = Infrastructure.getInstance().getCalendario().esAnterior(r1.getFechaInicio(), r2.getFechaFin());
		
		System.out.println( );
		
//		System.out.println(infrastructure.getCalendario().esHoy(fecha));
	}

}
