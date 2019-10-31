package act_2;

import java.util.HashMap;
import java.util.Map;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

/**
 * 
 * @author DAM
 *
 */
public class Ventas {

	private int codventa;
	private Articulos codarti;
	private Clientes numcli;
	private int univen;
	private String fecha;

	public int getCodventa() {
		return codventa;
	}

	public void setCodventa(int codventa) {
		this.codventa = codventa;
	}

	public Articulos getCodarti() {
		return codarti;
	}

	public void setCodarti(Articulos codarti) {
		this.codarti = codarti;
	}

	public Clientes getNumcli() {
		return numcli;
	}

	public void setNumcli(Clientes numcli) {
		this.numcli = numcli;
	}

	public int getUniven() {
		return univen;
	}

	public void setUniven(int univen) {
		this.univen = univen;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Ventas(int codventa, Articulos codarti, Clientes numcli, int univen, String fecha) {
		super();
		this.codventa = codventa;
		this.codarti = codarti;
		this.numcli = numcli;
		this.univen = univen;
		this.fecha = fecha;
	}

	public Ventas() {
		// TODO Auto-generated constructor stub
	}

	public HashMap<Integer, Ventas> getAllVentas(ObjectContainer db) {
		HashMap<Integer, Ventas> ventas = new HashMap<Integer, Ventas>();
		Ventas venta;
		ObjectSet res = db.queryByExample(new Ventas(0, null, null, 0, null));
		while (res.hasNext()) {
			venta = (Ventas) res.next();
			ventas.put(venta.getCodventa(), venta);
		}

		return ventas;
	}
	
	public void getVentasData(HashMap<Integer,Ventas> ventas) {
		System.out.println("|--------------------------------------------------------------------------------|");
		System.out.println("| CODVENTA–|-CODARTI–|-DENOMINACION–|-NUMCLI–|-NOMBRE–|-FECHA–|-UNIVEN–|-IMPORTE |");
		System.out.println("|----------|---------|--------------|--------|--------|-------|--------|---------|");
		
		for (Map.Entry<Integer, Ventas> v : ventas.entrySet()) {
			System.out.println("| "+v.getValue().getCodventa()+" | "+
		v.getValue().getCodarti().getCodarti()+" | "+
		v.getValue().getCodarti().getDenom()+" | "+
		v.getValue().getNumcli().getNumcli()+" | "+
		v.getValue().getNumcli().getNombre()+" | "+
		v.getValue().getFecha()+" | "+
		v.getValue().getCodarti().getComprasById(ventas, v.getKey())+" | "+
		v.getValue().getCodarti().getComprasById(ventas, v.getKey())*v.getValue().getCodarti().getPvp()+" |");
			};
		System.out.println("|--------------------------------------------------------------------------------|");
	}

}
