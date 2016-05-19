package mochila;

import java.util.Comparator;

public class NodoComparator implements Comparator<Nodo> {


	@Override
	public int compare(Nodo X, Nodo Y) {
		// TODO Auto-generated method stub
		if (X.getBeneficio_opt() < Y.getBeneficio_opt())
        {
            return 1;
        }
        if (X.getBeneficio_opt() > Y.getBeneficio_opt())
        {
            return -1;
        }
		/*tiene el mismo ratio por lo cual devuelvo el que menos peso tenga*/
        if (X.getPeso() < Y.getPeso()) 
        	return 1;
        if (X.getPeso() < Y.getPeso()) 
        	return -1;
        return 0;
        
	}



}
