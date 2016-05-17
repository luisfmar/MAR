package mochila;

public class Nodo {
	private boolean[] sol ;
	private int k;
	private double peso,beneficio,beneficio_opt;
	public Nodo(int size){
		this.sol = new boolean[size];
		for (int i=0; i<size; i++)
			this.sol[i]=false;
	}
	public boolean[] getSol() {
		return sol;
	}
	public void setSol(boolean[] sol) {
		/*****cuidado con la copia********/
		for (int i = 0; i < sol.length ; i++){
			this.sol[i] = sol [i];
		}
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public double getBeneficio() {
		return beneficio;
	}
	public void setBeneficio(double beneficio) {
		this.beneficio = beneficio;
	}
	public double getBeneficio_opt() {
		return beneficio_opt;
	}
	public void setBeneficio_opt(double beneficio_opt) {
		this.beneficio_opt = beneficio_opt;
	}
	public void setSolK(int k,boolean b){
		this.sol[k]=b;
	}
}
