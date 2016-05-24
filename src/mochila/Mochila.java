package mochila;

import java.util.PriorityQueue;

public class Mochila {
	private double beneficio_mejor;
	private boolean[] sol_mejor;
	private int nodos_explorados;
	
	/**
	 * 
	 * @param objetos Lista de objetos con su peso y valor
	 * @param M ->peso que soparta la mochila
	 * @param cotas -> tipo de cota
	 * @return la mejor solucion[1..n] = [True|False] y el mejor beneficio que se puede conseguir.
	 */
	public Tupla mochila_rp(Objeto[] objetos,double M,int cotas){
		this.nodos_explorados=0;
		Nodo Y;
		Tupla tupla =new Tupla(0,0) ;
		int n = objetos.length; 
		this.beneficio_mejor=0;
		this.sol_mejor = new boolean[n];
		for (int i=0; i<n; i++)
			this.sol_mejor[i]=false;
		
		Y=new Nodo(n,-1,0,0,0);
		NodoComparator comparador = new NodoComparator();
		PriorityQueue<Nodo> queue = new PriorityQueue<Nodo>(1 ,comparador);
		if (cotas == 0)
			tupla = calculo_estimaciones(objetos, M, Y.getK(), Y.getPeso(), Y.getBeneficio());
		else if (cotas == 1)
			tupla = calculo_estimacionesingenua(objetos, M, Y.getK(), Y.getPeso(), Y.getBeneficio());
		else if (cotas == 2) 
			tupla = calculo_estimacionesingenuaLuis(objetos, M, Y.getK(), Y.getPeso(), Y.getBeneficio());
		Y.setBeneficio_opt(tupla.getX());
		this.beneficio_mejor = tupla.getY();
		queue.add(Y);
		while ( !queue.isEmpty()  && queue.peek().getBeneficio_opt() >= this.beneficio_mejor ){
			Y = queue.poll();
			int k = Y.getK()+1;
			this.nodos_explorados++;
			double peso=Y.getPeso(),beneficio=Y.getBeneficio(),beneficio_opt=Y.getBeneficio_opt();
			boolean[] sol_copy=Y.copySol();
			Nodo X=new Nodo(n,k,peso,beneficio,beneficio_opt,sol_copy);
			Nodo X1=new Nodo(n,k,peso,beneficio,beneficio_opt,sol_copy);
			
			/*probamos a meter el objeto en la mochila*/
			double espacio_ocupado=peso + objetos[k].getPeso();
			if (espacio_ocupado <= M) {
				/*es factible y, por tanto, las estimaciones coinciden con las de Y
				 * beneficio-opt(X)=beneficio-opt(Y) >= beneficio-mejor*/
				
				X.setSolK(k,true);	
				
				X.setPeso(peso+objetos[k].getPeso());
				X.setBeneficio(beneficio+objetos[k].getValor());
				X.setBeneficio_opt(beneficio_opt);
				if ( k == n - 1 ){
					this.sol_mejor = X.copySol();
					this.beneficio_mejor = X.getBeneficio();
					}
				else {	
					queue.add(X);
					/*no se puede mejorar la solucion mejor*/
				}
			}
			/*probamos a no meter el objeto (siempre es factible)*/
			if (cotas == 0)
				tupla = calculo_estimaciones(objetos, M, k, peso, beneficio);
			else if (cotas == 1)
				tupla = calculo_estimacionesingenua(objetos, M, k, peso, beneficio);
			else if (cotas == 2) 
				tupla = calculo_estimacionesingenuaLuis(objetos, M, k, peso, beneficio);
			X1.setBeneficio_opt(tupla.getX());
			double pes = tupla.getY();
			if ( X1.getBeneficio_opt() >= this.beneficio_mejor ){
				X1.setSolK(k, false);
				X1.setPeso(peso);
				X1.setBeneficio(beneficio);
				if ( k == n - 1 ) {			
					this.sol_mejor = X1.copySol();
					this.beneficio_mejor = X1.getBeneficio();
				}
				else {
					queue.add(X1);
					this.beneficio_mejor = max(this.beneficio_mejor, pes);
				}
			}

			
		}
		Tupla solucion = new Tupla(this.sol_mejor,this.beneficio_mejor);
		System.out.println("Nodos explorados->"+this.nodos_explorados);
		return solucion;
	}
	
	/**
	 * Funcion que devuelve el maximo comparando 2 valores	
	 * @param beneficio_mejor2 
	 * @param pes
	 * @return mayor de los dos 
	 */
	private double max(double beneficio_mejor2, double pes) {
		// TODO Auto-generated method stub
		if ( beneficio_mejor2 > pes ) return beneficio_mejor2;
		if ( beneficio_mejor2 <= pes ) return pes;
		return beneficio_mejor2;
	}
	/**
	 * Funcion que calcula las estimaciones optimista y pesismita considerando los objetos
	 * ordenados de la forma Vi/Pi >= Vi+1/Pi+1 
	 * @param objetos Array de la clase Objeto 
	 * @param M tamaño de la mochila
	 * @param k objeto actual
	 * @param peso peso actual 
	 * @param beneficio beneficio actual 
	 */
	private Tupla calculo_estimaciones(Objeto[] objetos,double M,int k,double peso,double beneficio){
		double hueco = M-peso;
		double pes = beneficio;
		double opt = beneficio;		
		int j = k + 1;
		int n = objetos.length; 
		
		while (j <= n - 1 && objetos[j].getPeso() <= hueco ){
			/*mientras podemos coger el objeto j entero */
			hueco -= objetos[j].getPeso();
			opt += objetos[j].getValor();
			pes += objetos[j].getValor();
			j=j+1;
		}
		if ( j <= n-1 ){
				/*Quedan objetos por probar
				 * fraccionamos el objeto j (solucion voraz)*/
				opt += (hueco/objetos[j].getPeso())*objetos[j].getValor();
				/*extendemos a una solución en la version 0/1 */
				j=j+1;
				while (j <= n - 1 && hueco > 0){
					if ( objetos[j].getPeso() <= hueco ) {
						hueco -= objetos[j].getPeso();
						pes += objetos[j].getValor();
					}
					j=j+1;
				}
		}

		Tupla t = new Tupla(opt,pes);
		return t;
	}
	
	/**
	 *Calculo de las cotas ingenuas
	 * @param objetos
	 * @param M
	 * @param k
	 * @param peso
	 * @param beneficio
	 * @return optimista-> beneficio actual + el objeto siguiente (o la fracción que quepa ); pesimista-> beneficio actual
	 */
	private Tupla calculo_estimacionesingenua(Objeto[] objetos,double M,int k,double peso,double beneficio){
		double hueco = M-peso;
		double pes = beneficio;
		double opt = beneficio;		
		int j = k + 1;
		int n = objetos.length; 
		if(j < n && hueco>=0){
				opt += (hueco/objetos[j].getPeso())*objetos[j].getValor();
		}
		Tupla t = new Tupla(opt,pes);
		return t;
	}

	/**
	 * Calculo de las cotas ingenuas
	 * @param objetos
	 * @param M
	 * @param k
	 * @param peso
	 * @param beneficio
	 * @return cota pesimista->beneficio actual; cota optimista->beneficio actual + el valor de todos los objetos que quedan por explorar(quepan o no).
	 */
	private Tupla calculo_estimacionesingenuaLuis(Objeto[] objetos,double M,int k,double peso,double beneficio){
		/* el peor beneficio que podemos obtener es el benficio actual(cota pesimista)
		 * el mejor benficio que vamos a obtener es la suma de todos los objetos (cota optimista)*/
		double pes = beneficio;
		/*double opt = beneficio;*/		
		double opt=beneficio;
		int n = objetos.length; 
		for (int i=k+1;i<n;i++)
				opt += objetos[i].getValor();
		Tupla t = new Tupla(opt,pes);
		return t;
	}
	
}
