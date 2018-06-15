package ifcClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import com.apstex.ifc4toolbox.*;
import com.apstex.ifc4toolbox.ifc4.IfcActor;
import com.apstex.ifc4toolbox.ifc4.IfcActorRole;
import com.apstex.ifc4toolbox.ifc4.IfcBuilding;
import com.apstex.ifc4toolbox.ifc4.IfcBuildingStorey;
import com.apstex.ifc4toolbox.ifc4.IfcGloballyUniqueId;
import com.apstex.ifc4toolbox.ifc4.IfcIdentifier;
import com.apstex.ifc4toolbox.ifc4.IfcLabel;
import com.apstex.ifc4toolbox.ifc4.IfcObject;
import com.apstex.ifc4toolbox.ifc4.IfcObjectDefinition;
import com.apstex.ifc4toolbox.ifc4.IfcOrganization;
import com.apstex.ifc4toolbox.ifc4.IfcOwnerHistory;
import com.apstex.ifc4toolbox.ifc4.IfcPerson;
import com.apstex.ifc4toolbox.ifc4.IfcPostalAddress;
import com.apstex.ifc4toolbox.ifc4.IfcProject;
import com.apstex.ifc4toolbox.ifc4.IfcProperty;
import com.apstex.ifc4toolbox.ifc4.IfcPropertySet;
import com.apstex.ifc4toolbox.ifc4.IfcPropertySetDefinition;
import com.apstex.ifc4toolbox.ifc4.IfcPropertySingleValue;
import com.apstex.ifc4toolbox.ifc4.IfcRelAggregates;
import com.apstex.ifc4toolbox.ifc4.IfcRelDefinesByProperties;
import com.apstex.ifc4toolbox.ifc4.IfcRoleEnum;
import com.apstex.ifc4toolbox.ifc4.IfcSpace;
import com.apstex.ifc4toolbox.ifc4.IfcText;
import com.apstex.ifc4toolbox.ifc4.IfcValue;
import com.apstex.ifc4toolbox.ifc4.IfcWall;
import com.apstex.ifc4toolbox.ifcmodel.IfcModel;
import com.apstex.step.core.LIST;
import com.apstex.step.core.SET;

import beansDB.Dezernatmitarbeiter;
import beansDB.Dienstleister;
import beansDB.Dienstleistung;
import beansDB.Gebaeude;
import beansDB.LnRaumWand;
import beansDB.Raum;
import beansDB.Stockwerk;
import beansDB.Wand;

public class ModelIfc {
	private IfcModel ifcModel;
	private List<Wand> waendeL;
	private List<Gebaeude> gebL;
	private List<Stockwerk> stwL;
	private List<Raum> raumL;
	private Map<Integer, IfcBuilding> mapGeb;
	private Map<Integer, IfcSpace> mapRaum;
	private Map<Integer, IfcWall> mapWand;
	private Map<IfcBuilding, List<IfcBuildingStorey>> mapGebStw;
	private Map<Integer, IfcBuildingStorey> mapStw;
	private Map<IfcBuildingStorey, List<IfcSpace>> mapStwRaum;
	private Map<IfcSpace, List<IfcWall>> mapRaumWand;
	private IfcActorRole dmaRole;
	private IfcActorRole dlrRole;

	public List<Wand> getWaendeL() {
		return waendeL;
	}

	public List<Gebaeude> getGebL() {
		return gebL;
	}

	public List<Stockwerk> getStwL() {
		return stwL;
	}

	public List<Raum> getRaumL() {
		return raumL;
	}

	public ModelIfc() {
		this.ifcModel = new IfcModel();
		IfcGloballyUniqueId gIproject = new IfcGloballyUniqueId(this.ifcModel.getNewGlobalUniqueId());
		// This can be change according to the user
		IfcOwnerHistory ownHistPro = new IfcOwnerHistory();
		IfcLabel nameprj = new IfcLabel("IIB2_UE2_Gruppe09");
		IfcText descrProj = new IfcText("");
		ifcModel.addObject(ownHistPro);
		IfcProject proj = new IfcProject(gIproject, ownHistPro, nameprj, descrProj, null, null, null, null, null);
		ifcModel.addObject(proj);
		mapGeb = new TreeMap<Integer, IfcBuilding>();
		mapGebStw = new TreeMap<IfcBuilding, List<IfcBuildingStorey>>();
		mapStw = new TreeMap<Integer, IfcBuildingStorey>();
		mapStwRaum = new TreeMap<IfcBuildingStorey, List<IfcSpace>>();
		mapRaum = new TreeMap<Integer, IfcSpace>();
		mapWand = new TreeMap<Integer, IfcWall>();
		mapRaumWand = new TreeMap<IfcSpace, List<IfcWall>>();
		dmaRole = new IfcActorRole(new IfcRoleEnum("DMA"), new IfcLabel("dma"), new IfcText("dezernatmitarbeiter"));
		dlrRole = new IfcActorRole(new IfcRoleEnum("DLR"), new IfcLabel("dlr"), new IfcText("dienstleister"));
		ifcModel.addObject(dmaRole);
		ifcModel.addObject(dlrRole);
	}

	public ModelIfc(String filePath) {
		File ifcFile = new File(filePath);
		this.ifcModel = new IfcModel();

		try {
			ifcModel.readStepFile(ifcFile);
		} catch (Exception e) {

		}

		Collection<IfcRelAggregates> rels = ifcModel.getCollection(IfcRelAggregates.class);
		Collection<IfcBuilding> buildings = ifcModel.getCollection(IfcBuilding.class);
		Collection<IfcRelAggregates> relsB = getrelAggregatesForBuild(buildings, rels);
		this.gebL = createGeabeudeFromIfc(buildings);
		Collection<IfcBuildingStorey> strs = ifcModel.getCollection(IfcBuildingStorey.class);
		this.stwL = createStwFromIfc(strs);
		Collection<IfcWall> walls = ifcModel.getCollection(IfcWall.class);
		this.waendeL = createWandFromIfc(walls);
		Collection<IfcSpace> rooms = ifcModel.getCollection(IfcSpace.class);
		this.raumL = createRaumFromIfc(rooms);
		System.out.println("Lists created");

	}

	private Collection<IfcRelAggregates> getrelAggregatesForBuild(Collection<IfcBuilding> buildings,
			Collection<IfcRelAggregates> rels) {
		for (IfcBuilding bL : buildings) {
			bL.getIsDecomposedBy_Inverse();
		}
		return null;
	}

	private List<Raum> createRaumFromIfc(Collection<IfcSpace> rooms) {
		List<Raum> raumL = new ArrayList<Raum>();
		Raum r;
		for (IfcSpace sp : rooms) {
			r = new Raum();
			r.setGuid(sp.getGlobalId().getValue());
			r.setBezeichnung(sp.getName().getValue());
			r.setNr(" 1");
			raumL.add(r);
		}
		return raumL;
	}

	private List<Stockwerk> createStwFromIfc(Collection<IfcBuildingStorey> strs) {
		List<Stockwerk> stwL = new ArrayList<Stockwerk>();
		Stockwerk stw;
		for (IfcBuildingStorey wIfc : strs) {
			stw = new Stockwerk();
			stw.setGuid(wIfc.getGlobalId().getValue());
			stw.setBezeichnung(wIfc.getName().getValue());

			SET<IfcRelAggregates> rels = wIfc.getDecomposes_Inverse();
			for (IfcRelAggregates r : rels) {
				if (r.getRelatingObject() instanceof IfcBuilding
						&& r.getName().getValue().equals("BuildingContainer")) {
					System.out.println("Hier wird die Bezieung zu der Gebaeude gelesen!");
				}
			}
			stwL.add(stw);
		}
		return stwL;
	}

	private List<Gebaeude> createGeabeudeFromIfc(Collection<IfcBuilding> gebIfcList) {
		List<Gebaeude> gL = new ArrayList<Gebaeude>();

		Gebaeude geb;
		IfcPostalAddress ipA;
		for (IfcBuilding wIfc : gebIfcList) {
			geb = new Gebaeude();
			ipA = wIfc.getBuildingAddress();
			IfcRelAggregates iA = wIfc.getIsDecomposedBy_Inverse().get(0);
			iA.getRelatedObjects();
			if (!ipA.equals(null)) {
				geb.setPlz(Integer.parseInt(ipA.getPostalCode().getValue()));
				geb.setOrt(ipA.getTown().getValue());
				geb.setHausnummer("11");
				geb.setStrasse("Hauptstr.");
				// LIST<IfcLabel> address = ipA.getAddressLines();
				// for (IfcLabel l : address) {
				// }
				gL.add(geb);
			} else {
				geb.setPlz(00000);
				geb.setOrt("");
				geb.setHausnummer(" ");
				geb.setStrasse("");
				gL.add(geb);
			}
		}
		return gL;

	}

	private List<Wand> createWandFromIfc(Collection<IfcWall> waende) {
		List<Wand> wL = new ArrayList<Wand>();
		Wand wand;
		for (IfcWall wIfc : waende) {
			wand = new Wand();
			wand.setGuid(wIfc.getGlobalId().getValue());
			wL.add(wand);
		}
		return wL;
	}

	public void createGebaeude(List<Gebaeude> gebL) {
		IfcBuilding gebIfc;
		for (Gebaeude g : gebL) {
			gebIfc = new IfcBuilding();
			IfcGloballyUniqueId guid = new IfcGloballyUniqueId(g.getGuid());
			gebIfc.setGlobalId(guid);
			LIST<IfcLabel> addL = new LIST<IfcLabel>();
			addL.add(0, new IfcLabel(g.getStrasse()));
			addL.add(1, new IfcLabel(g.getHausnummer()));
			IfcPostalAddress pA = new IfcPostalAddress(null, null, null, null, addL, null, new IfcLabel(g.getOrt()),
					null, new IfcLabel(String.valueOf(g.getPlz())), null);
			this.ifcModel.addObject(pA);
			gebIfc.setBuildingAddress(pA);
			this.ifcModel.addObject(gebIfc);
			String pgDMAName = "dma_id";
			String pgDMADesc = "ID des ersteller DMA";
			String pgDMAValue = String.valueOf(g.getDma_id());

			IfcIdentifier namepgDMA = new IfcIdentifier(pgDMAName, false);
			IfcText descr = new IfcText(pgDMADesc, false);
			IfcValue valuepgDMA = new IfcText(pgDMAValue);
			IfcPropertySingleValue pgDMA = new IfcPropertySingleValue(namepgDMA, descr, valuepgDMA, null);

			IfcGloballyUniqueId propSetGuid = new IfcGloballyUniqueId(ifcModel.getNewGlobalUniqueId());
			IfcOwnerHistory ownerHistory = new IfcOwnerHistory();
			IfcLabel name = new IfcLabel("geb_dma", false);
			IfcText description = new IfcText("Enthält Information der Ersteller des Gebäudes", false);
			SET<IfcProperty> s = new SET<IfcProperty>();
			IfcPropertySetDefinition pgDMASet = new IfcPropertySet(propSetGuid, ownerHistory, name, description, s);
			((IfcPropertySet) pgDMASet).addHasProperties(pgDMA);
			this.ifcModel.addObject(pgDMA);
			this.ifcModel.addObject(pgDMASet);
			IfcRelDefinesByProperties ifcRelDefProp = new IfcRelDefinesByProperties();
			ifcRelDefProp.setRelatingPropertyDefinition(pgDMASet);
			SET<IfcObjectDefinition> tempSet = new SET<IfcObjectDefinition>();
			tempSet.add(gebIfc);
			ifcRelDefProp.addAllRelatedObjects(tempSet);
			this.ifcModel.addObject(ifcRelDefProp);
			mapGeb.put(g.getId(), gebIfc);
		}
	}

	public void createStockwerk(List<Stockwerk> stwL) {
		IfcBuildingStorey stwIfc;
		for (Stockwerk s : stwL) {
			stwIfc = new IfcBuildingStorey();
			IfcGloballyUniqueId guid = new IfcGloballyUniqueId(s.getGuid());
			stwIfc.setGlobalId(guid);
			stwIfc.setName(new IfcLabel(s.getBezeichnung()));
			this.ifcModel.addObject(stwIfc);

			IfcBuilding b = mapGeb.get(s.getGeb_id());
			List<IfcBuildingStorey> l;
			if (!mapGebStw.containsKey(b)) {
				l = new ArrayList<IfcBuildingStorey>();

			} else {
				l = mapGebStw.get(b);
			}
			l.add(stwIfc);
			mapStw.put(s.getId(), stwIfc);
			mapGebStw.put(b, l);
		}
		createGebStwRel();

	}

	private void createGebStwRel() {
		for (Map.Entry<IfcBuilding, List<IfcBuildingStorey>> entry : mapGebStw.entrySet()) {
			SET<IfcObjectDefinition> tempSet = new SET<IfcObjectDefinition>();
			tempSet.addAll(entry.getValue());
			IfcRelAggregates r = new IfcRelAggregates(new IfcGloballyUniqueId(this.ifcModel.getNewGlobalUniqueId()),
					new IfcOwnerHistory(), new IfcLabel("geb_stw_id"), new IfcText("Building-BuildingStorey"),
					entry.getKey(), tempSet);
			ifcModel.addObject(r);
		}
	}

	public void createRaum(List<Raum> rL) {
		IfcSpace rIfc;
		for (Raum r : rL) {
			rIfc = new IfcSpace();
			IfcGloballyUniqueId guid = new IfcGloballyUniqueId(r.getGuid());
			rIfc.setGlobalId(guid);
			rIfc.setName(new IfcLabel(r.getBezeichnung()));
			rIfc.setDescription(new IfcText("Raum"));
			this.ifcModel.addObject(rIfc);

			IfcBuildingStorey s = mapStw.get(r.getStw_id());
			List<IfcSpace> l;
			if (!mapStwRaum.containsKey(s)) {
				l = new ArrayList<IfcSpace>();

			} else {
				l = mapStwRaum.get(s);
			}
			l.add(rIfc);
			mapRaum.put(r.getId(), rIfc);
			mapStwRaum.put(s, l);
		}
		createStwRaumRel();

	}

	private void createStwRaumRel() {
		for (Map.Entry<IfcBuildingStorey, List<IfcSpace>> entry : mapStwRaum.entrySet()) {
			SET<IfcObjectDefinition> tempSet = new SET<IfcObjectDefinition>();
			tempSet.addAll(entry.getValue());
			IfcRelAggregates r = new IfcRelAggregates(new IfcGloballyUniqueId(this.ifcModel.getNewGlobalUniqueId()),
					new IfcOwnerHistory(), new IfcLabel("stw_raum_id"), new IfcText("BuildingStorey-BuildingSpace"),
					entry.getKey(), tempSet);
			ifcModel.addObject(r);
		}
	}

	public void createWand(List<Wand> wL) {
		IfcWall wIfc;
		for (Wand w : wL) {
			wIfc = new IfcWall();
			IfcGloballyUniqueId guid = new IfcGloballyUniqueId(w.getGuid());
			wIfc.setGlobalId(guid);
			wIfc.setName(new IfcLabel("Wand"));
			this.ifcModel.addObject(wIfc);
			mapWand.put(w.getId(), wIfc);
		}
	}

	public void createRaumWandRel(List<LnRaumWand> rwL) {
		for (LnRaumWand lrw : rwL) {
			IfcSpace r = mapRaum.get(lrw.getRaum_id());
			List<IfcWall> l;
			if (!mapRaumWand.containsKey(r)) {
				l = new ArrayList<IfcWall>();
			} else {
				l = mapRaumWand.get(r);
			}
			l.add(mapWand.get(lrw.getWand_id()));
			mapRaumWand.put(r, l);
		}
		for (Map.Entry<IfcSpace, List<IfcWall>> entry : mapRaumWand.entrySet()) {
			SET<IfcObjectDefinition> tempSet = new SET<IfcObjectDefinition>();
			tempSet.addAll(entry.getValue());
			IfcRelAggregates r = new IfcRelAggregates(new IfcGloballyUniqueId(this.ifcModel.getNewGlobalUniqueId()),
					new IfcOwnerHistory(), new IfcLabel("raum_wand_id"), new IfcText("BuildingSpace-Wall"),
					entry.getKey(), tempSet);
			ifcModel.addObject(r);
		}
	}

	public void createActorsDMA(List<Dezernatmitarbeiter> dmaL) {
		IfcActor dmaIfc;
		IfcPerson person;
		for (Dezernatmitarbeiter dma : dmaL) {
			person = new IfcPerson();
			person.setGivenName(new IfcLabel(dma.getVorname()));
			person.setFamilyName(new IfcLabel(dma.getNachname()));
			// Id
			person.setIdentification(new IfcIdentifier(String.valueOf(dma.getId())));
			person.addRoles(dmaRole);
			ifcModel.addObject(person);
			// Name ist der username!
			dmaIfc = new IfcActor(null, null, new IfcLabel(dma.getUsername()), null, null, person);
			ifcModel.addObject(dmaIfc);

		}
	}

	public void createActorsDLR(List<Dienstleister> dlrL) {
		IfcActor dlrIfc;
		IfcOrganization org;
		for (Dienstleister dlr : dlrL) {
			org = new IfcOrganization();
			org.addRoles(dlrRole);
			org.setName(new IfcLabel(dlr.getFirmaname()));
			// Id
			org.setIdentification(new IfcIdentifier(String.valueOf(dlr.getId())));
			ifcModel.addObject(org);
			// Name ist der username!
			dlrIfc = new IfcActor(null, null, new IfcLabel(dlr.getUsername()), null, null, org);
			ifcModel.addObject(dlrIfc);
		}

	}
	public void createDienstleistung(List<Dienstleistung> dlnL) {
		IfcObject dlnIfc;
		for(Dienstleistung d: dlnL) {
			dlnIfc = new Object();
			IfcLabel dlnName = new IfcLabel(d.getName());
			
		}
	}

	public void getFile(String path) {
		try {
			this.ifcModel.writeStepFile(new File(path));
			System.out.println("Hola");
		} catch (Exception e) {
			System.out.println("Error with export " + e.getMessage());
		}
	}

}
