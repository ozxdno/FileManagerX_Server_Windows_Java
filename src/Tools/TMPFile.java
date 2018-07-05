package Tools;

public class TMPFile {

	public static final boolean clear() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		
		return dm.deleteContent(Tools.Pathes.getFolder_TMP());
	}
	
}
