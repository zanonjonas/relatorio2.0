package br.com.paxtecnologia.pma.relatorio.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormataDataUtil {
	
	public static Date formataAnoInicio(String mesRelatorio) {
		Date anoInicio = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(mesRelatorio));
			c.add(Calendar.MONTH, -11);
			anoInicio = new Date(sdf.parse(sdf.format(c.getTime())).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return anoInicio;
	}
	
	public static Date formataDataInicio(String mesRelatorio) {
		Date dataInicio = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dataInicio = new Date((sdf.parse(mesRelatorio).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataInicio;
	}
	
	public static Date formataDataFim(String mesRelatorio) {
		Date dataFim = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(mesRelatorio));
			//c.add(Calendar.MONTH, 1);
			dataFim = new Date((sdf.parse(sdf.format(c.getTime())).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataFim;
	}
	
	public static Date formataDataFim_dia(String mesRelatorio) {
		Date dataFim = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(mesRelatorio));
			c.add(Calendar.MONTH, 1);
			dataFim = new Date((sdf.parse(sdf.format(c.getTime())).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataFim;
	}
	
	public static Integer diasNoMes(String mesRelatorio) {
		Integer dia = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(mesRelatorio));
			dia = c.getActualMaximum( Calendar.DAY_OF_MONTH );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dia;
		
		
	}	
	
	
	public static Integer getCampoMesRelatorio(String campo, String mesRelatorio){
		
		String dia;
        String mes;
        String ano;
        
        ano = mesRelatorio.split("-")[0];
        mes = mesRelatorio.split("-")[1];
        dia = mesRelatorio.split("-")[2];

        if (campo.equals("dia")){
       	return Integer.parseInt(dia); 
        } else if (campo.equals("mes")){
       	 return Integer.parseInt(mes);
        } else if (campo.equals("ano")){
       	 return Integer.parseInt(ano);
        } else {
       	 return null;
        }
		
        
	}
}
