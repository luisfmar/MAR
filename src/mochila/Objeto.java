package mochila;

public class Objeto implements Comparable<Objeto>{
	private double valor;
	private double peso;
	private double ratio;
	public Objeto(double valor, double peso){
		this.valor=valor;
		this.peso=peso;
		this.ratio=valor/peso;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public double getRatio() {
		return this.ratio;
	}
	@Override
	public int compareTo(Objeto o) {
		// TODO Auto-generated method stub
		if ( ratio < o.ratio ) {
			return 1;
		}
		if ( ratio > o.ratio ) {
			return -1;
		}
		return 0;
	}
}
