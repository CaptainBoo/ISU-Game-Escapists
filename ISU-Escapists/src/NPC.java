import java.util.ArrayList;

abstract public class NPC {
	private static String name;
	ArrayList<Item> Inventory = new ArrayList<>(5);
	
	public NPC () {
		System.out.println(NPC.name);
	}
}
