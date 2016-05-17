package mochila;

import java.util.PriorityQueue;

public class Mochila {
	private double beneficio_mejor=0;
	private boolean[] sol_mejor;
	
	
	public double mochila_rp(Objeto[] objetos,double M){
		Nodo X,Y;
		Tupla tupla  ;
		int n = objetos.length; 
		this.sol_mejor = new boolean[n];
		for (int i=0; i<n; i++)
			this.sol_mejor[i]=false;
		X=new Nodo(n);
		Y=new Nodo(n);
		NodoComparator comparador = new NodoComparator();
		PriorityQueue<Nodo> queue = new PriorityQueue<Nodo>(comparador);
		Y.setK(-1);/*para empezar por el objeto 1*/
		Y.setPeso(0);
		Y.setBeneficio(0);
		tupla = calculo_estimaciones(objetos, M, Y.getK(), Y.getPeso(), Y.getBeneficio());
		Y.setBeneficio_opt(tupla.getX());
		this.beneficio_mejor = tupla.getY();
		queue.add(Y);
		while ( !queue.isEmpty()  && queue.peek().getBeneficio_opt() >= this.beneficio_mejor ){
			Y = queue.poll();
			X.setK(Y.getK()+1);
			X.setSol(Y.getSol());
			/*probamos a meter el objeto en la mochila*/
			if (Y.getPeso() + objetos[X.getK()].getPeso() < M) {
				/*es factible y, por tanto, las estimaciones coinciden con las de Y
				 * beneficio-opt(X)=beneficio-opt(Y) >= beneficio-mejor*/
				
				X.setSolK(X.getK(),true);	
				X.setPeso(Y.getPeso()+objetos[X.getK()].getPeso());
				X.setBeneficio(Y.getBeneficio()+objetos[X.getK()].getValor());
				X.setBeneficio_opt(Y.getBeneficio_opt());
				if ( X.getK() == n ){
					boolean[] s = X.getSol();
					for (int i = 0; i < n ; i++){
						this.sol_mejor[i] = s[i];
					}
					this.beneficio_mejor = X.getBeneficio();
					}
				else {
					queue.add(X);
					/*no se puede mejorar la solucion mejor*/
				}
			}
			/*probamos a no meter el objeto (siempre es factible)*/
			tupla = calculo_estimaciones(objetos, M, X.getK(), Y.getPeso(), Y.getBeneficio());
			X.setBeneficio_opt(tupla.getX());
			double pes = tupla.getY();
			if ( X.getBeneficio_opt() >= this.beneficio_mejor ){
				X.setSolK(X.getK(), false);
				X.setPeso(Y.getPeso());
				X.setBeneficio(Y.getBeneficio());
				if ( X.getK() == n ) {
					boolean[] s = X.getSol();
					for (int i = 0; i < n ; i++){
						this.sol_mejor[i] = s[i];
					}
					this.beneficio_mejor = X.getBeneficio();
				}
				else {
					queue.add(X);
					this.beneficio_mejor = max(this.beneficio_mejor, pes);
				}
			}
			
		}
		for (int i = 0; i<n;i++)
			System.out.println(this.sol_mejor[i]);
		return this.beneficio_mejor;
	}
	private double max(double beneficio_mejor2, double pes) {
		// TODO Auto-generated method stub
		if ( beneficio_mejor2 > pes ) return beneficio_mejor2;
		else return pes;
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
		
		while (j < n && objetos[j].getPeso() < hueco ){
			/*mientras podemos coger el objeto j entero */
			hueco -= objetos[j].getPeso();
			opt += objetos[j].getValor();
			pes += objetos[j].getValor();
			j=j+1;
			}
			if ( j < n ){
				/*Quedan objetos por probar
				 * fraccionamos el objeto j (solucion voraz)*/
				opt += (hueco/objetos[j].getPeso())*objetos[j].getValor();
				/*extendemos a una solución en la version 0/1 */
				j=j+1;
				while (j < n && hueco>0){
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
	
}
