import java.util.Comparator;

public class SortItemByContraband implements Comparator <Item>{

	public int compare(Item i1, Item i2) {
		if (i1.isContraband() && !i2.isContraband()) {
			return -1;
		} else if (!i1.isContraband() && i2.isContraband()) {
			return 1;
		} else {
			return i1.getItemName().compareToIgnoreCase(i2.getItemName());
		}
		
	}

}
