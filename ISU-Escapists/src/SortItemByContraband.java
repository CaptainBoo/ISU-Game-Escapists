import java.util.Comparator;

public class SortItemByContraband implements Comparator <Item>{

	//This overridden method sorts for controband so that controband items go to the front
	//And non contraband go to the back.
	//Parameters: (Item i1, Itemi2)
	//Return: int
	public int compare(Item i1, Item i2) {
	    if (i1.isContraband() && !i2.isContraband()) {
	        return -1;
	    } else if (!i1.isContraband() && i2.isContraband()) {
	        return 1;
	    } else {
	        return 0;
	    }
	}

}
