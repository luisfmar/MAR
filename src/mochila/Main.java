package mochila;

import java.io.File;
import java.io.FileNotFoundException;
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
					System.out.println(objetos[i].getPeso());
					System.out.println(objetos[i].getValor());
				}
				Mochila m = new Mochila();
				System.out.println(m.mochila_rp(objetos, M));

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
