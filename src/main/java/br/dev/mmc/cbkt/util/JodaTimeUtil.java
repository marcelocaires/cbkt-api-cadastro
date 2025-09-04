package br.dev.mmc.cbkt.util;


import java.time.LocalTime;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaTimeUtil {
	
	private static DateTimeFormatter DF_DAY_MONTH_BR=DateTimeFormat.forPattern("dd/MM");
	private static DateTimeFormatter DF_LONG_DATE_BR=DateTimeFormat.forPattern("dd 'de' MMMM 'de' yyyy");
	private static DateTimeFormatter DF_SHORT_DATE_BR=DateTimeFormat.forPattern("dd/MM/yy");
    private static DateTimeFormatter DF_DATE_BR=DateTimeFormat.forPattern("dd/MM/yyyy");
	private static DateTimeFormatter DF_MONTH_YEAR_BR=DateTimeFormat.forPattern("MM/yyyy");
	private static DateTimeFormatter DF_MONTH_YEAR=DateTimeFormat.forPattern("MMyyyy");
	private static DateTimeFormatter DF_YEAR=DateTimeFormat.forPattern("yyyy");
	private static DateTimeFormatter DF_TIME=DateTimeFormat.forPattern("HH:mm");
	//Conversões
	public static String convertDateToStringShortDateBR(Date dt){
		LocalDate ldt =convertDateToLocalDate(dt);
		return ldt.toString(DF_SHORT_DATE_BR);
	}

	public static String convertLocalDateToStringShortDateBR(LocalDate ldt){
		return ldt.toString(DF_SHORT_DATE_BR);
	}

	public static String convertDateToStringLongDateBR(Date dt){
		LocalDate ldt =convertDateToLocalDate(dt);
		return ldt.toString(DF_LONG_DATE_BR);
	}

	public static String convertDateToStringMonthYearBR(Date dt){
		LocalDate ldt =convertDateToLocalDate(dt);
		return ldt.toString(DF_MONTH_YEAR_BR);
	}

	public static String convertDateToStringDayMonthBR(Date dt){
		LocalDate ldt =convertDateToLocalDate(dt);
		return ldt.toString(DF_DAY_MONTH_BR);
	}

	public static String convertToStringTimeBR(Date dt){
		DateTime ldt =new DateTime(dt);
		return ldt.toString(DF_TIME);
	}

	public static String convertToStringTimeBR(LocalDate dt){
		DateTime ldt =new DateTime(dt);
		return ldt.toString(DF_TIME);
	}

	public static String convertLocalDateToStringDayMonthBR(LocalDate ldt){
		return ldt.toString(DF_DAY_MONTH_BR);
	}

	public static String convertLocalDateToStringMonthYearBR(LocalDate ldt){
		return ldt.toString(DF_MONTH_YEAR_BR);
	}

	public static String convertDateToStringMonthYear(Date dt){
		LocalDate ldt =convertDateToLocalDate(dt);
		return ldt.toString(DF_MONTH_YEAR);
	}

	public static String convertLocalDateToStringMonthYear(LocalDate ldt){
		return ldt.toString(DF_MONTH_YEAR);
	}
	
	public static LocalDate convertDateToLocalDate(Date dt){
        return new LocalDate(dt);
	}
	public static Date convertLocalDateToDate(LocalDate ldt){
		return ldt.toDate();
	}

	public static String convertLocalDateToStringDateBR(LocalDate ldt){
        return ldt.toString(DF_DATE_BR);
	}

	public static String convertDateToStringDateBR(Date dt){
		LocalDate ldt =convertDateToLocalDate(dt);
		return ldt.toString(DF_DATE_BR);
	}

	public static String convertMillisecondsToStringTime(long mils){
        Period period=new Period(mils);
        int horas=period.getHours();
        int minutos=period.getMinutes();
        return String.format("%02d:%02d",horas,minutos); 
    }
	
	//Parsers
	public static Date parseStringYearToDate(String year){
        return parseStringYearToLocalDate(year).toDate();
	}
	
	public static LocalDate parseStringYearToLocalDate(String year){
        return LocalDate.parse(year,DF_YEAR);
	}
	
	public static Interval parseStringYearToInterval(String year){
        Date dtIni = parseStringYearToLocalDate(year).dayOfYear().withMinimumValue().toDate();
        Date dtFim = parseStringYearToLocalDate(year).dayOfYear().withMaximumValue().toDate();
		return new Interval(dtIni.getTime(),dtFim.getTime());
	}

	public static DateTime parseStringTimeToDateTime(String sTime){
		return DateTime.parse(sTime, DF_TIME).withDate(2025,1 ,1);
	}

	
	public static long parseStringTimeToMilliseconds(String time) {
		return parseStringTimeToDateTime(time).getMillis();
	}
	
	public static Date parseStringDateBRtoDate(String dt){
        return LocalDate.parse(dt,DF_DATE_BR).toDate();
	}
	
	public static LocalDate parseStringDateBRtoLocalDate(String dt){
		return LocalDate.parse(dt,DF_DATE_BR);
	}

	public static Date parseStringMonthYearBRtoDate(String dt){
        return LocalDate.parse(dt,DF_MONTH_YEAR_BR).dayOfMonth().withMinimumValue().toDate();
	}
	
	public static LocalDate parseStringMonthYearBRtoLocalDate(String dt){
		return LocalDate.parse(dt,DF_MONTH_YEAR_BR).dayOfMonth().withMinimumValue();
	}
	
	//Comparações
	public static boolean checkLocalDateIgualOuAnterior(LocalDate dt, LocalDate dtRef) {
		return dt.isEqual(dtRef) || dt.isBefore(dtRef);
	}

	public static boolean checkLocalDateIgualOuPosterior(LocalDate dt, LocalDate dtRef) {
		return dt.isEqual(dtRef) || dt.isAfter(dtRef);
	}
		
	public static boolean checkDateBetweenPeriod(Date dt, Date dtInicial, Date dtFinal) {
		DateTime iDateTime = new DateTime(dtInicial);
		DateTime fDateTime = new DateTime(dtFinal);
		DateTime dDateTime = new DateTime(dt);
		Interval periodo = new Interval(iDateTime,fDateTime);
		return periodo.contains(dDateTime);
	}
	
	public static boolean checkLocalDateBetweenPeriod(LocalDate dt, LocalDate dtInicial, LocalDate dtFinal) {
		return checkDateBetweenPeriod(dt.toDate(), dtInicial.toDate(), dtFinal.toDate());
	}

	public static boolean checkStringDateBRBetweenPeriod(String stringDateBR, Date dtInicial, Date dtFinal) {
		Date dt = parseStringDateBRtoDate(stringDateBR);
		return checkDateBetweenPeriod(dt, dtInicial, dtFinal);
	}
	
	public static boolean checkStringDateBRBetweenStringPeriodBR(String stringDateBR, String dtInicial, String dtFinal) {
        Date dtIni = parseStringDateBRtoDate(dtInicial);
        Date dtFim = parseStringDateBRtoDate(dtFinal);
		Date dt = parseStringDateBRtoDate(stringDateBR);
		return checkDateBetweenPeriod(dt, dtIni, dtFim);
	}
	
	public static boolean checkDateBetweenStringPeriodBR(Date dt, String dtInicial, String dtFinal) {
        Date dtIni = parseStringDateBRtoDate(dtInicial);
        Date dtFim = parseStringDateBRtoDate(dtFinal);
		return checkDateBetweenPeriod(dt, dtIni, dtFim);
	}

	//DateTime comparar posterior
	public static boolean checkDateTimeLater(Date dt, Date dtRef) {
		DateTime dt1 = new DateTime(dt);
		DateTime dt2 = new DateTime(dtRef);
		return dt1.compareTo(dt2)>0;
	}

	//DateTime comparar igual ou anterior
	public static boolean checkDateTimeSameOrPrevious(Date dt, Date dtRef) {
		DateTime dt1 = new DateTime(dt);
		DateTime dt2 = new DateTime(dtRef);
		int r=dt1.compareTo(dt2);
		return r<=0;
	}
	
	//Igual ou anterior
	public static boolean checkLocalDateSameOrPrevious(LocalDate dt,LocalDate dtRef) {
		return dt.isEqual(dtRef) || dt.isBefore(dtRef);
	}
	
    //Igual ou posterior
	public static boolean checkLocalDateSameOrLater(LocalDate dt,LocalDate dtRef) {
		return dt.isEqual(dtRef) || dt.isAfter(dtRef);
	}
	
	//Igual ou anterior
	public static boolean checkDateSameOrPrevious(Date dt,Date dtRef) {
		LocalDate lDt=convertDateToLocalDate(dt);
		LocalDate dDtRef=convertDateToLocalDate(dtRef);
		return checkLocalDateSameOrPrevious(lDt,dDtRef);
	}
	
    //Igual ou posterior
	public static boolean checkDateSameOrLater(Date dt,Date dtRef) {
		LocalDate lDt=convertDateToLocalDate(dt);
		LocalDate dDtRef=convertDateToLocalDate(dtRef);
		return checkLocalDateSameOrLater(lDt,dDtRef);
	}
	
	//verifica se há inetercessão de um período em outro, a data final do período pode ser nula
	public static boolean checkPeriodIntersect(Date dtInicio,Date dtFim,Date dtInicioRef,Date dtFimRef) {
		if(dtInicio==null || dtInicioRef==null  || dtFimRef==null) return false;
		boolean check=false;
		if(dtFim==null) {
			check=checkDateBetweenPeriod(dtInicio, dtInicioRef, dtFimRef) || checkDateSameOrPrevious(dtInicio, dtInicioRef);
		}else{
			check=checkDateBetweenPeriod(dtInicio, dtInicioRef, dtFimRef) || checkDateBetweenPeriod(dtFim, dtInicioRef, dtFimRef) ||
				  (checkDateSameOrPrevious(dtInicio, dtInicioRef) && checkDateSameOrLater(dtFim, dtFimRef));
		}
		return check;
	}	

	public static DateTime convertLocalTimeToDateTime(LocalTime time){
		return new DateTime(2025,01,01,time.getHour(),time.getMinute());
	}

	//verifica se um hora está antes de uma referência
	public static boolean checkTimeBeforeStringTime(LocalTime time,String sTime) {
		if(time==null || sTime==null) return false;
		DateTime t = convertLocalTimeToDateTime(time);
		DateTime tRef = parseStringTimeToDateTime(sTime);
		return t.isBefore(tRef) || t.isEqual(tRef);
	}

	//verifica se um hora depois antes de uma referência
	public static boolean checkTimeAfterStringTime(LocalTime time,String sTime) {
		DateTime t = convertLocalTimeToDateTime(time);
		DateTime tRef = parseStringTimeToDateTime(sTime);
		return t.isAfter(tRef);
	}

	//verifica se um hora está antes de uma referência
	public static boolean checkTimeBeforeStringTime(Date time,String sTime) {
		DateTime t = new DateTime(time);
		DateTime tRef = DateTime.parse(sTime, DF_TIME);
		return t.isBefore(tRef) || t.isEqual(tRef);
	}

	//verifica se um hora depois antes de uma referência
	public static boolean checkTimeAfterStringTime(Date time,String sTime) {
		DateTime t = new DateTime(time);
		DateTime tRef = DateTime.parse(sTime, DF_TIME);
		return t.isAfter(tRef);
	}
	
	//Cálculos
	public static String calcStringTimesMinusToStringTime(String time1,String time2) {
		return convertMillisecondsToStringTime(calcStringTimesMinusToMils(time1,time2));
	}
	public static Long calcStringTimesMinusToMils(String time1,String time2) {
		long t1 = parseStringTimeToDateTime(time1).getMillis();
		long t2 = parseStringTimeToDateTime(time2).getMillis();
		if(t1>t2) {
			return t1-t2;
		}else {
			return t2-t1;
		}
	}

	public static String calcDateTimesMinusToStringTime(DateTime time1,DateTime time2) {
		return convertMillisecondsToStringTime(calcDateTimesMinusToMils(time1,time2));
	}
	public static Long calcDateTimesMinusToMils(DateTime time1,DateTime time2) {
		long t1 = time1.getMillis();
		long t2 = time2.getMillis();
		if(t1>t2) {
			return t1-t2;
		}else {
			return t2-t1;
		}
	}

	public static String calcLocalTimesDifferenceToStringTime(LocalTime time1,LocalTime time2) {
		DateTime t1 = new DateTime(2025,01,01,time1.getHour(),time1.getMinute());
		DateTime t2 = new DateTime(2025,01,01,time2.getHour(),time2.getMinute());
		return calcDateTimesMinusToStringTime(t1,t2);
	}


}
