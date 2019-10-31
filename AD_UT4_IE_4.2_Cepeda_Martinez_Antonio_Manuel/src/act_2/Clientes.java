package act_2;

import java.util.HashMap;
import java.util.Map;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Clientes {
	
	private int numcli;
	private String nombre;
	private String pobla;
	public int getNumcli() {
		return numcli;
	}
	public void setNumcli(int numcli) {
		this.numcli = numcli;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPobla() {
		return pobla;
	}
	public void setPobla(String pobla) {
		this.pobla = pobla;
	}
	public Clientes(int numcli, String nombre, String pobla) {
		super();
		this.numcli = numcli;
		this.nombre = nombre;
		this.pobla = pobla;
	}
	public Clientes() {
		// TODO Auto-generated constructor stub
	}
	
	public HashMap<Integer,Clientes> getAllClientes(ObjectContainer db){
		HashMap<Integer,Clientes> clientes=new HashMap<Integer,Clientes>();
		Clientes cliente;
		
			ObjectSet res=db.queryByExample(new Clientes(0, null, null));
			while(res.hasNext()){
				cliente=(Clientes)res.next();
				clientes.put(cliente.getNumcli(),cliente);
				}
			
		return clientes;
		
		
	}
	
	public float getTotalImporte(HashMap<Integer,Ventas> ventas) {
		float total=0;
		for (Map.Entry<Integer, Ventas> v : ventas.entrySet()) {
			if(v.getValue().getNumcli().getNumcli()==this.numcli) {
				total+=v.getValue().getUniven()*v.getValue().getCodarti().getPvp();
			}
		}
		return total;
	}
	
	public int getNumVentasDeClientes(HashMap<Integer,Ventas> ventas) {
		int total=0;
		for (Map.Entry<Integer, Ventas> v : ventas.entrySet()) {
			if(v.getValue().getNumcli().getNumcli()==this.numcli) {
				total++;
			}
		}
		return total;
	}
	
	public void clienteConMasCompras(HashMap<Integer,Clientes> clientes, HashMap<Integer,Ventas> ventas) {

		
		System.out.println("|--------------------------|");
		System.out.println("| CLIENTE CON MAS COMPRAS  |");
		System.out.println("| (El que mas ha comprado) |");
		System.out.println("|--------------------------|");
		int masAlto=0;
		for (Map.Entry<Integer, Clientes> cliente : clientes.entrySet()) {
			if(cliente.getValue().getNumVentasDeClientes(ventas)>masAlto) {
				masAlto=cliente.getValue().getNumVentasDeClientes(ventas);
			}
		}
		
		System.out.println("|---------------------------------------------------------------------------------------------------|");
		for (Map.Entry<Integer, Clientes> c : clientes.entrySet()) {
			if(c.getValue().getNumVentasDeClientes(ventas)==masAlto) {
				System.out.println("| El cliente que mas ha comprado es el cliente "+c.getValue().getNombre()+" con ID "+c.getValue().getNumcli()+" habiendo comprado "+c.getValue().getNumVentasDeClientes(ventas)+" productos. |");
			}
		}
		System.out.println("|---------------------------------------------------------------------------------------------------|");
		//En este caso han coincidido dos clientes con 2 producctos comprados
	}
	
	public void obtenerDatosClientes(HashMap<Integer,Clientes> clientes, HashMap<Integer,Ventas> ventas) {
		System.out.println("|----------------------------------------------------------|");
		System.out.println("| NUMCLI-|-NOMBRE-|-POBLACION-|-TOTAL_IMPORTE-|-NUM_VENTAS |");
		System.out.println("|--------|--------|-----------|---------------|------------|");
		for (Map.Entry<Integer, Clientes> c : clientes.entrySet()) {
			System.out.println("| "+c.getValue().getNumcli()+" | "+
		c.getValue().getNombre()+" | "+
		c.getValue().getPobla()+" | "+
		c.getValue().getTotalImporte(ventas)+" | "+
		c.getValue().getNumVentasDeClientes(ventas)+" |"
		);
			};
	}
	
	public void mejorCliente(HashMap<Integer,Clientes> clientes, HashMap<Integer,Ventas> ventas) {
		System.out.println("|-------------------------|");
		System.out.println("|      MEJOR CLIENTE      |");
		System.out.println("| (El que mas ha gastado) |");
		System.out.println("|-------------------------|");
		float masAlto=0;
		for (Map.Entry<Integer, Clientes> c : clientes.entrySet()) {
			if(c.getValue().getTotalImporte(ventas)>masAlto) {
				masAlto=c.getValue().getTotalImporte(ventas);
			}
		}
		
		for (Map.Entry<Integer, Clientes> c : clientes.entrySet()) {
			if(c.getValue().getTotalImporte(ventas)==masAlto) {
				System.out.println("| El cliente que mas ha gastado es el cliente "+c.getValue().getNombre()+" y la ID "+c.getValue().getNumcli()+" con un gasto de "+c.getValue().getTotalImporte(ventas)+" euros. |");
				System.out.println("|---------------------------------------------------------------------------------|");
			}
		}
	}

	
}
