package mochila;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan;
		File file = new  File(args[0]);
		Objeto o;
		Objeto objetos[]; 
		double peso,valor;
		double M;
		try {
			scan = new Scanner(file);
			while( scan.hasNextInt() ){
				int n = scan.nextInt();
				M = scan.nextDouble();
				objetos =  new Objeto[n];
				for (int i = 0 ; i < n ; i++ ){
					peso =  scan.nextDouble();
					valor =  scan.nextDouble();
					o = new Objeto(valor,peso);
					objetos[i]=o;
				}
				System.out.println("***************Cotas ajustadas*********************************");
				
				Mochila m = new Mochila();
				Arrays.sort(objetos);
				Tupla sol = m.mochila_rp(objetos, M);				
				System.out.println("Solucion de siguiendo el orden (Objeto 0, .... Objeto N)");
				for (int i = 0; i < sol.getSol().length ; i++ )
					System.out.println("Objeto "+i+"-> | Peso= "+objetos[i].getPeso()+" | Valor= "+objetos[i].getValor()+" | Ratio= "+objetos[i].getRatio()+" | Seleccionado =  " + sol.getSol()[i]);
				System.out.println("Beneficio total = "+sol.getY());
				System.out.print("Solucion = ");
				if (sol.getSol()[0]) 
					System.out.print("(1");
				else
					System.out.print("(0");
				for (int i = 1; i < sol.getSol().length ; i++ ){
					if (sol.getSol()[i]) 
							System.out.print(",1");
					else
						System.out.print(",0");
				}
				System.out.println(")");
				
				System.out.println("*******************Cotas ingenuas*****************************");				
				Mochila m2 = new Mochila();
				Tupla sol2 = m2.mochila_rp2(objetos, M);
				System.out.println("Solucion de siguiendo el orden (Objeto 0, .... Objeto N)");
				for (int i = 0; i < sol2.getSol().length ; i++ )
					System.out.println("Objeto "+i+"-> | Peso= "+objetos[i].getPeso()+" | Valor= "+objetos[i].getValor()+" | Ratio= "+objetos[i].getRatio()+" | Seleccionado =  " + sol2.getSol()[i]);
				System.out.println("Beneficio total = "+sol2.getY());
				System.out.print("Solucion = ");
				if (sol2.getSol()[0]) 
					System.out.print("(1");
				else
					System.out.print("(0");
				for (int i = 1; i < sol2.getSol().length ; i++ ){
					if (sol2.getSol()[i]) 
							System.out.print(",1");
					else
						System.out.print(",0");
				}
				System.out.println(")");

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
