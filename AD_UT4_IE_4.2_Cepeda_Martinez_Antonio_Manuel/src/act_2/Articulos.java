package act_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.Entry;

public class Articulos {

	private int codarti;
	private String denom;
	private int stock;
	private float pvp;
	private ArrayList<Ventas> Compras;

	public int getCodarti() {
		return codarti;
	}

	public void setCodarti(int codarti) {
		this.codarti = codarti;
	}

	public String getDenom() {
		return denom;
	}

	public void setDenom(String denom) {
		this.denom = denom;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public float getPvp() {
		return pvp;
	}

	public void setPvp(float pvp) {
		this.pvp = pvp;
	}

	public ArrayList<Ventas> getCompras() {
		return Compras;
	}

	public void setCompras(ArrayList<Ventas> compras) {
		Compras = compras;
	}

	public Articulos(int codarti, String denom, int stock, float pvp) {
		super();
		this.codarti = codarti;
		this.denom = denom;
		this.stock = stock;
		this.pvp = pvp;
	}

	public Articulos() {
		// TODO Auto-generated constructor stub
	}

	public int getNumCompras(HashMap<Integer, Ventas> ventas) {
		Compras = new ArrayList<Ventas>();
		ventas.forEach((i, venta) -> {
			if (venta.getCodarti().getCodarti() == this.getCodarti()) {
				Compras.add(venta);
			}
		});
		return Compras.size();
	}

	public HashMap<Integer, Articulos> getAllArticulos(ObjectContainer db) {
		HashMap<Integer, Articulos> articulos = new HashMap<Integer, Articulos>();
		Articulos articulo;

		ObjectSet res = db.queryByExample(new Articulos(0, null, 0, 0));
		while (res.hasNext()) {
			articulo = (Articulos) res.next();
			articulos.put(articulo.getCodarti(), articulo);
		}

		return articulos;
	}

	public int getComprasById(HashMap<Integer, Ventas> ventas, int id) {
		int numArticulos = 0;
		for (Map.Entry<Integer, Ventas> venta : ventas.entrySet()) {
			if (venta.getValue().getCodarti().getCodarti() == this.getCodarti() && venta.getValue().getCodventa() == id) {
				numArticulos = venta.getValue().getUniven();
			}
		}
		return numArticulos;
	}

	public int getNumVentasByID(HashMap<Integer, Ventas> ventas) {
		int numVentas = 0;
		for (Map.Entry<Integer, Ventas> venta : ventas.entrySet()) {
			if (venta.getValue().getCodarti().codarti == this.codarti) {
				numVentas += venta.getValue().getUniven();
			}
		}
		return numVentas;
	}

	/**
	 * Metodo que devuelve una lista pintada por consola con los datos y las ventas de cada articulo
	 * @param ventas
	 * @param articulos
	 */
	public void getVentasArticulos(HashMap<Integer, Ventas> ventas, HashMap<Integer, Articulos> articulos) {
		int totalSV = 0;
		float sumaImp = 0;

		int totalSUV = 0;
		System.out.println("|--------------------------------------------------------------------------------|");
		System.out.println("| CODARTI | DENOMINACION | STOCK | PVP | SUMA_UNIVEN | SUMA_IMPORTE | NUM_VENTAS |");
		System.out.println("|---------|--------------|-------|-----|-------------|--------------|------------|");
		for (Map.Entry<Integer, Articulos> a : articulos.entrySet()) {
			totalSUV += a.getValue().getNumVentasByID(ventas);
			totalSV += a.getValue().getNumCompras(ventas);
			sumaImp += a.getValue().getNumCompras(ventas) * a.getValue().getPvp();

			System.out.println("| " + a.getValue().toString() + " | " + a.getValue().getNumVentasByID(ventas)
					+ " | " + a.getValue().getNumVentasByID(ventas) * a.getValue().getPvp() + " | "
					+ a.getValue().getNumCompras(ventas) + " | ");
		}
		System.out.println("|--------------------------------------------------------------------------------|");
		System.out.println("|--------------------------|");
		System.out.println("|          TOTALES         |");
		System.out.println("|--------------------------|");
		System.out.println("|    SUMA_UNIVEN: " + totalSUV + "       |");
		System.out.println("|    SUMA_IMPORTE: " + sumaImp + "   |");
		System.out.println("|    NUM_VENTAS: " + totalSV + "         |");
		System.out.println("|--------------------------|");
	}

	public void getSumasArticulos(ObjectContainer db, HashMap<Integer, Articulos> articulos,
			HashMap<Integer, Ventas> ventas) {
		int totalSUV = 0; // total suma de las unidades vendidas
		int totalSV = 0; // total suma de ventas
		float sumaImporte = 0;
		System.out.println("|--------------------------------------------------------------------------------|");
		System.out.println("| CODARTI–|-DENOMINACION-|-STOCK–|-PVP–|-SUMA_UNIVEN-|-SUMA_IMPORTE–|-NUM_VENTAS |");
		System.out.println("|---------|--------------|-------|-----|-------------|--------------|------------|");
		for (Map.Entry<Integer, Articulos> art : articulos.entrySet()) {
			totalSUV += art.getValue().getNumVentasByID(ventas);
			totalSV += art.getValue().getNumCompras(ventas);
			sumaImporte += art.getValue().getNumCompras(ventas) * art.getValue().getPvp();

			System.out.println(" | "+art.getValue().toString() + " | " + art.getValue().getNumVentasByID(ventas) + " | "
					+ art.getValue().getNumVentasByID(ventas) * art.getValue().getPvp() + " | "
					+ art.getValue().getNumCompras(ventas)+" |");
		}
		System.out.println("|--------------------------------------------------------------------------------|");
		System.out.println("|--------------------------------------------------------------------------------|");
		System.out.println("|--------------------------|");
		System.out.println("|          TOTALES         |");
		System.out.println("|--------------------------|");
		System.out.println("|     SUMA_UNIVEN: " + totalSUV);
		System.out.println("|     SUMA_IMPORTE: " + sumaImporte);
		System.out.println("|     NUM_VENTAS: " + totalSV);
		System.out.println("|--------------------------|");
	}

	public void articuloBestSeller(HashMap<Integer, Ventas> ventas, HashMap<Integer, Articulos> articulos) {
		int bestSeller = 0;
		for (Map.Entry<Integer, Articulos> art : articulos.entrySet()) {
			if (art.getValue().getNumCompras(ventas) > bestSeller)
				bestSeller = art.getValue().getNumCompras(ventas);
		}
		System.out.println("|------------------------------------------------------------------------------------------------|");
		for (Map.Entry<Integer, Articulos> art : articulos.entrySet()) {
			if (art.getValue().getNumCompras(ventas) == bestSeller)
				System.out.println("| El articulo mas vendido es el articulo con ID: "+art.getValue().getCodarti() + " y tipo " + art.getValue().getDenom()
						+ " habiendo sido vendido : " + bestSeller+ " veces. |");
		}
		System.out.println("|------------------------------------------------------------------------------------------------|");
	}

	public void mediaPrecioArticulos(HashMap<Integer, Ventas> ventas, HashMap<Integer, Articulos> articulos) {
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("|----------------------------------------------|");
		System.out.println("|       MEDIA PRECIO POR TIPO DE ARTICULO      |");
		System.out.println("|----------------------------------------------|");
		
		for (Map.Entry<Integer, Articulos> artiulo : articulos.entrySet()) {
			
			if (artiulo.getValue().getNumCompras(ventas) > 0) {
				System.out.println("|------------------------------------------------------------------------------------------------|");
				System.out.println("| El tipo "+artiulo.getValue().getDenom() + " tiene una media de "
						+ (artiulo.getValue().getPvp() * artiulo.getValue().getNumVentasByID(ventas))
								/ artiulo.getValue().getNumCompras(ventas)
						+ " euros. |");
				System.out.println("|------------------------------------------------------------------------------------------------|");
			}
		}
	}

	@Override
	public String toString() {
		return "Articulo " + codarti + " | Denominacion: " + denom + " | Stock: " + stock + " | PVP: " + pvp + "€ .";
	}

}
