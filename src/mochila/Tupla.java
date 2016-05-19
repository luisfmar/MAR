package mochila;

public class Tupla {
	private double x , y;
	private boolean[] sol ;
	public Tupla(double A, double B){
		this.x = A;
		this.y = B;
	}
	public Tupla(boolean[] solucion, double beneficio){
		int n = solucion.length;
		this.sol = new boolean[n];
		for (int i = 0; i < n ; i++ )
				this.sol[i] = solucion[i];
		this.y = beneficio;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public boolean[] getSol() {
		return sol;
	}
	public void setSol(boolean[] sol) {
		this.sol = sol;
	}
}
