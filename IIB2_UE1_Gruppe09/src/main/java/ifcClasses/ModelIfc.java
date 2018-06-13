package ifcClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.apstex.ifc4toolbox.*;
import com.apstex.ifc4toolbox.ifc4.IfcBuilding;
import com.apstex.ifc4toolbox.ifc4.IfcGloballyUniqueId;
import com.apstex.ifc4toolbox.ifc4.IfcIdentifier;
import com.apstex.ifc4toolbox.ifc4.IfcLabel;
import com.apstex.ifc4toolbox.ifc4.IfcObjectDefinition;
import com.apstex.ifc4toolbox.ifc4.IfcOwnerHistory;
import com.apstex.ifc4toolbox.ifc4.IfcPostalAddress;
import com.apstex.ifc4toolbox.ifc4.IfcProperty;
import com.apstex.ifc4toolbox.ifc4.IfcPropertySet;
import com.apstex.ifc4toolbox.ifc4.IfcPropertySetDefinition;
import com.apstex.ifc4toolbox.ifc4.IfcPropertySingleValue;
import com.apstex.ifc4toolbox.ifc4.IfcRelDefinesByProperties;
import com.apstex.ifc4toolbox.ifc4.IfcText;
import com.apstex.ifc4toolbox.ifc4.IfcValue;
import com.apstex.ifc4toolbox.ifc4.IfcWall;
import com.apstex.ifc4toolbox.ifcmodel.IfcModel;
import com.apstex.step.core.LIST;
import com.apstex.step.core.SET;

import beansDB.Gebaeude;
import beansDB.Wand;

public class ModelIfc {
	IfcModel ifcModel;
	List<Wand> waendeL;

	public ModelIfc() {
		this.ifcModel = new IfcModel();
	}

	public ModelIfc(String filePath) {
		File ifcFile = new File("ifc.ifc");
		this.ifcModel = new IfcModel();

		try {
			ifcModel.readStepFile(ifcFile);
		} catch (Exception e) {

		}

		Collection<IfcWall> waende = ifcModel.getCollection(IfcWall.class);
		this.waendeL = createWandFromIfc(waende);

	}

	public void createGebaeude(List<Gebaeude> gebL) {
		IfcBuilding gebIfc;
		for (Gebaeude g : gebL) {
			gebIfc = new IfcBuilding();
			IfcGloballyUniqueId guid = new IfcGloballyUniqueId(g.getGuid());
			gebIfc.setGlobalId(guid);
			LIST<IfcLabel> addL =  new LIST<IfcLabel>();
			addL.add(0, new IfcLabel(g.getStrasse()));
			addL.add(1, new IfcLabel(g.getHausnummer()));
			IfcPostalAddress pA = new IfcPostalAddress(null, null, null, null, addL, null, new IfcLabel(g.getOrt()), null,
					new IfcLabel(String.valueOf(g.getPlz())), null);
			gebIfc.setBuildingAddress(pA);
			String pgDMAName = "dma_id";
			String pgDMADesc = "ID des ersteller DMA";
			String pgDMAValue = String.valueOf(g.getDma_id());
			
			IfcIdentifier namepgDMA = new IfcIdentifier(pgDMAName, false);
			IfcText descr = new IfcText(pgDMADesc, false);
			IfcValue valuepgDMA = new IfcText(pgDMAValue);
			IfcPropertySingleValue pgDMA = new IfcPropertySingleValue(namepgDMA, descr, valuepgDMA, null);
			
			IfcGloballyUniqueId propSetGuid = new IfcGloballyUniqueId(ifcModel.getNewGlobalUniqueId());
			IfcOwnerHistory ownerHistory = new IfcOwnerHistory();
			IfcLabel name = new IfcLabel( "geb_dma", false);
			IfcText description = new IfcText("Enthält Information der Ersteller des Gebäudes", false);
			SET<IfcProperty> s= new SET<IfcProperty>();
			IfcPropertySetDefinition pgDMASet = new IfcPropertySet(propSetGuid, ownerHistory, name, description, s);
			((IfcPropertySet) pgDMASet).addHasProperties(pgDMA);
			System.out.print(ifcModel.toString());
			ifcModel.addObject(pgDMA);
			ifcModel.addObject(pgDMASet);
			IfcRelDefinesByProperties ifcRelDefProp = new IfcRelDefinesByProperties();
			ifcRelDefProp.setRelatingPropertyDefinition(pgDMASet);
			SET<IfcObjectDefinition> tempSet = new SET<IfcObjectDefinition>();
			tempSet.add(gebIfc);
			ifcRelDefProp.addAllRelatedObjects(tempSet);
			ifcModel.addObject(ifcRelDefProp);
		}
	}

	private List<Gebaeude> createGeabeudeFromIfc(Collection<IfcBuilding> gebIfcList) {
		List<Gebaeude> gL = new ArrayList<Gebaeude>();
		Gebaeude geb;
		IfcPostalAddress ipA;
		for (IfcBuilding wIfc : gebIfcList) {
			geb = new Gebaeude();
			ipA = wIfc.getBuildingAddress();
			geb.setPlz(Integer.parseInt(ipA.getPostalCode().getValue()));
			geb.setOrt(ipA.getTown().getValue());
			LIST<IfcLabel> address = ipA.getAddressLines();
			for (IfcLabel l : address) {
			}
		}
		return gL;

	}

	private List<Wand> createWandFromIfc(Collection<IfcWall> waende) {
		List<Wand> wL;
		Wand wand;
		for (IfcWall wIfc : waende) {
			wand = new Wand();

		}
		return null;
	}

	public void getFile(String path) {
		URL url = getClass().getResource("export/out/test.ifc");
		String path1 = "export/out/test.ifc"; 
		try {
			this.ifcModel.writeStepFile(new File(url.getPath()));
			System.out.println("Hola");
		} catch (Exception e){
			System.out.println("Error with export "+ e.getMessage());
		}  
	}

}
