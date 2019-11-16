package model.epanet.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.epanet.element.networkcomponent.Emitter;
import model.epanet.element.networkcomponent.Junction;
import model.epanet.element.networkcomponent.Link;
import model.epanet.element.networkcomponent.Node;
import model.epanet.element.networkcomponent.Pipe;
import model.epanet.element.networkcomponent.Pump;
import model.epanet.element.networkcomponent.Reservoir;
import model.epanet.element.networkcomponent.Tank;
import model.epanet.element.networkcomponent.Valve;
import model.epanet.element.networkdesign.Backdrop;
import model.epanet.element.networkdesign.Label;
import model.epanet.element.networkdesign.Tag;
import model.epanet.element.optionsreport.Option;
import model.epanet.element.optionsreport.Report;
import model.epanet.element.optionsreport.Time;
import model.epanet.element.systemoperation.Control;
import model.epanet.element.systemoperation.Curve;
import model.epanet.element.systemoperation.Demand;
import model.epanet.element.systemoperation.Energy;
import model.epanet.element.systemoperation.Pattern;
import model.epanet.element.systemoperation.Rule;
import model.epanet.element.systemoperation.Status;
import model.epanet.element.waterquality.Mixing;
import model.epanet.element.waterquality.Quality;
import model.epanet.element.waterquality.Reaction;
import model.epanet.element.waterquality.Source;

public class Network {
	/*********************************************************
	 * Network Components
	 *********************************************************/
	private StringBuilder title;

	private Map<String, Link> linkMap;
	private Map<String, Node> nodesMap;
	// Nodes
	private Map<String, Junction> junctionsMap;
	private Map<String, Reservoir> reservoirsMap;
	private Map<String, Tank> tanksMap;
	// Links
	private Map<String, Pipe> pipesMap;
	private Map<String, Pump> pumpsMap;
	private Map<String, Valve> valvesMap;

	private List<Junction> junctionList;
	private List<Reservoir> reservoirList;
	private List<Tank> tankList;

	private List<Pipe> pipeList;
	private List<Pump> PumpList;
	private List<Valve> valveList;
	private List<Emitter> emitterList;

	/*********************************************************
	 * System Operation
	 *********************************************************/
	private Map<String, Curve> curveMap;
	private Map<String, Pattern> patternMap;

	private List<Control> controlList;
	private List<Curve> curveList;
	private List<Demand> demandList;
	private List<Energy> energyList;
	private List<Pattern> patternList;
	private List<Rule> ruleList;
	private List<Status> statusList;

	/*********************************************************
	 * Water Quality
	 *********************************************************/
	private List<Mixing> mixingList;
	private List<Quality> qualityList;
	private List<Source> sourceList;
	private Reaction reaction;

	/*********************************************************
	 * Options and Reports
	 *********************************************************/
	private Option option;
	private Time time;
	private Report report;
	/*********************************************************
	 * Network Design
	 *********************************************************/
	private Backdrop backdrop;
	private List<Label> labels;
	private List<Tag> tags;

	public Network() {
		this.title = new StringBuilder();
		this.linkMap = new HashMap<String, Link>();
		this.nodesMap = new HashMap<String, Node>();
		this.junctionsMap = new HashMap<String, Junction>();
		this.reservoirsMap = new HashMap<String, Reservoir>();
		this.tanksMap = new HashMap<String, Tank>();
		this.pipesMap = new HashMap<String, Pipe>();
		this.pumpsMap = new HashMap<String, Pump>();
		this.valvesMap = new HashMap<String, Valve>();
		this.junctionList = new ArrayList<>();
		this.reservoirList = new ArrayList<>();
		this.tankList = new ArrayList<>();
		this.pipeList = new ArrayList<>();
		this.PumpList = new ArrayList<>();
		this.valveList = new ArrayList<>();

	}

	/*********************************************************
	 * Network Components
	 *********************************************************/

	/**
	 * Set a title for this network
	 * 
	 * @param text The title for this network
	 */
	public void addLineToTitle(String text) {
		title.append(text);
	}

	/**
	 * Add a Link to network. It can be a Pipe, pump or valve.
	 * 
	 * @param id   the id link
	 * @param link link element
	 */
	public void addLink(String id, Link link) {
		this.linkMap.put(id, link);
		if (link instanceof Pipe) {
			this.pipesMap.put(id, (Pipe) link);
			this.pipeList.add((Pipe) link);
		} else if (link instanceof Pump) {
			this.pumpsMap.put(id, (Pump) link);
			this.PumpList.add((Pump) link);
		} else {
			this.valvesMap.put(id, (Valve) link);
			this.valveList.add((Valve) link);
		}
	}

	/**
	 * Add a node to network. It can be a Junction, Reservoir or Tank.
	 * 
	 * @param id   id node
	 * @param node node element
	 */
	public void addNode(String id, Node node) {
		this.nodesMap.put(id, node);
		if (node instanceof Junction) {
			this.junctionsMap.put(id, (Junction) node);
			this.junctionList.add((Junction) node);
		} else if (node instanceof Reservoir) {
			this.reservoirsMap.put(id, (Reservoir) node);
			this.reservoirList.add((Reservoir) node);
		} else {
			this.tanksMap.put(id, (Tank) node);
			this.tankList.add((Tank) node);
		}
	}

	/**
	 * Return a node by id
	 * 
	 * @param id id of the node
	 * @return The node
	 */
	public Node getNode(String id) {
		return this.nodesMap.get(id);
	}

	/**
	 * Return a link by id
	 * 
	 * @param id id of the link
	 * @return The link
	 */
	public Link getLink(String id) {
		return this.linkMap.get(id);
	}

	/**
	 * Get a junction by id.
	 * 
	 * @param id id of junction.
	 * @return Junction.
	 */
	public Junction getJuntion(String id) {
		return this.junctionsMap.get(id);
	}

	/**
	 * Get a reservoir by id.
	 * 
	 * @param id id of reservoir.
	 * @return Reservoir.
	 */
	public Reservoir getReservoir(String id) {
		return this.reservoirsMap.get(id);
	}

	/**
	 * Get a tank by id.
	 * 
	 * @param id id of tank.
	 * @return Tank.
	 */
	public Tank getTank(String id) {
		return this.tanksMap.get(id);
	}

	/**
	 * Get a pipe by id.
	 * 
	 * @param id id of pipe.
	 * @return Pipe.
	 */
	public Pipe getPipe(String id) {
		return this.pipesMap.get(id);
	}

	/**
	 * Get a pump by id.
	 * 
	 * @param id id of pump.
	 * @return Pump.
	 */
	public Pump getPump(String id) {
		return this.pumpsMap.get(id);
	}

	/**
	 * Get a valve by id.
	 * 
	 * @param id id of valve.
	 * @return Valve.
	 */
	public Valve getValve(String id) {
		return this.valvesMap.get(id);
	}

	/**
	 * Get all links no matter what they are. <br>
	 * The collection is unmodifiable.
	 * 
	 * @return the links
	 */
	public Collection<Link> getLinks() {
		return linkMap.values();
	}

	/**
	 * Get all nodes no matter what they are <br>
	 * The collection is unmodifiable.
	 * 
	 * @return the nodes
	 */
	public Collection<Node> getNodes() {
		return nodesMap.values();
	}

	/**
	 * Get a unmodifiable list only with junction.
	 * 
	 * @return the junctions
	 */
	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(junctionList);
	}

	/**
	 * Get a unmodifiable list only with reservoir.
	 * 
	 * @return the reservoirs
	 */
	public List<Reservoir> getReservoirs() {
		return Collections.unmodifiableList(reservoirList);
	}

	/**
	 * Get a unmodifiable list only with tanks.
	 * 
	 * @return the tanks
	 */
	public List<Tank> getTanks() {
		return Collections.unmodifiableList(tankList);
	}

	/**
	 * Get a unmodifiable list only with pipes.
	 * 
	 * @return the pipes
	 */
	public List<Pipe> getPipes() {
		return Collections.unmodifiableList(pipeList);
	}

	/**
	 * Get a unmodifiable list only with pump.
	 * 
	 * @return the pumps
	 */
	public List<Pump> getPumps() {
		return Collections.unmodifiableList(PumpList);
	}

	/**
	 * Get a unmodifiable list only with valve.
	 * 
	 * @return the valves
	 */
	public List<Valve> getValves() {
		return Collections.unmodifiableList(valveList);
	}

	/**
	 * Get a unmodifiable list only with emitter.
	 * 
	 * @return the emitterList
	 */
	public List<Emitter> getEmitterList() {
		return Collections.unmodifiableList(emitterList);
	}

	/**
	 * Add a emitter to network
	 * 
	 * @param id   id node
	 * @param node node element
	 */
	public void addEmiter(Emitter emitter) {
		if (this.emitterList == null) {
			this.emitterList = new ArrayList<Emitter>();
		}
		this.emitterList.add(emitter);
	}

	/*********************************************************
	 * System Operation
	 *********************************************************/

	/**
	 * Get a curve by id
	 * @param id the curve id
	 * @return the curve
	 */
	public Curve getCurve(String id) {
		return this.curveMap.get(id);
	}

	/**
	 * Add a curve to the network.
	 * @param id the curve id
	 * @param curve the curve to add.
	 */
	public void addCurve(String id, Curve curve) {
		if (this.curveMap == null) {
			this.curveMap = new HashMap<String, Curve>();
		}
		if (this.curveList == null) {
			this.curveList = new ArrayList<Curve>();
		}
		this.curveMap.put(id, curve);
		this.curveList.add(curve);
	}

	/**
	 * Get the pattern by id.
	 * @param the pattern id
	 * @return the pattern
	 */
	public Pattern getPattern(String id) {
		return this.patternMap.get(id);
	}

	/**
	 * Add a pattern to the network.
	 * @param id the id of pattern
	 * @param pattern the pattern to add
	 */
	public void addPattern(String id, Pattern pattern) {
		if (this.patternMap == null) {
			this.patternMap = new HashMap<String, Pattern>();
		}
		if (this.patternList == null) {
			this.patternList = new ArrayList<Pattern>();
		}
		this.patternMap.put(id, pattern);
		this.patternList.add(pattern);
	}

	/**
	 * Get a unmodifiable list with the controls
	 * @return the controlList
	 */
	public List<Control> getControlList() {
		return Collections.unmodifiableList(controlList);
	}

	/**
	 * Add a control to the network.
	 * @param control the control to add
	 */
	public void addControl(Control control) {
		if(this.controlList == null) {
			this.controlList = new ArrayList<>();
		}
		this.controlList.add(control);
	}

	/**
	 * Get a unmodifiable list with curves.
	 * @return the curveList
	 */
	public List<Curve> getCurveList() {
		return Collections.unmodifiableList(curveList);
	}

	/**
	 * Get a unmodifiable list with the demands.
	 * @return the demandList
	 */
	public List<Demand> getDemandList() {
		return Collections.unmodifiableList(demandList);
	}

	/**
	 * Add a demand to the network.
	 * @param demand the demand to add
	 */
	public void addDemand(Demand demand) {
		if(this.demandList == null) {
			this.demandList = new ArrayList<>();
		}
		this.demandList.add(demand);
	}

	/**
	 * Get a unmodifiable list with the energy
	 * @return the energyList
	 */
	public List<Energy> getEnergyList() {
		return Collections.unmodifiableList(energyList);
	}

	/**
	 * Add a energy to the network.
	 * @param energy the energy to add
	 */
	public void addEnergy(Energy energy) {
		if(this.energyList == null) {
			this.energyList = new ArrayList<>();
		}
		this.energyList.add(energy);
	}

	/**
	 * Get a unmodifiable list with patterns
	 * @return the patternList
	 */
	public List<Pattern> getPatternList() {
		return Collections.unmodifiableList(patternList);
	}

	/**
	 * Get a unmodifiable list with rules.
	 * @return the ruleList
	 */
	public List<Rule> getRuleList() {
		return Collections.unmodifiableList(ruleList);
	}

	/**
	 * Add a rule to the network.
	 * @param rule the rule to add
	 */
	public void addRule(Rule rule) {
		if(this.ruleList == null) {
			this.ruleList = new ArrayList<>();
		}
		this.ruleList.add(rule);
	}

	/**
	 * Get a unmodifiable list with status.
	 * @return the statusList
	 */
	public List<Status> getStatusList() {
		return Collections.unmodifiableList(statusList);
	}

	/**
	 * Add a status to the network.
	 * @param status the status to add
	 */
	public void addStatus(Status status) {
		if(this.statusList == null) {
			this.statusList = new ArrayList<>();
		}
		this.statusList.add(status);
	}

	/*********************************************************
	 * Water Quality
	 *********************************************************/

	/**
	 * Get a unmodifiable list with mixing configurations.
	 * @return the mixingList
	 */
	public List<Mixing> getMixingList() {
		return Collections.unmodifiableList(mixingList);
	}

	/**
	 * Add a mixing configuration.
	 * @param mixing the mixing to add
	 */
	public void addMixing(Mixing mixing) {
		if (this.mixingList == null) {
			this.mixingList = new ArrayList<>();
		}
		this.mixingList.add(mixing);
	}

	/**
	 * Get a unmodifiable list with quality configurations. 
	 * @return the qualityList
	 */
	public List<Quality> getQualityList() {
		return Collections.unmodifiableList(qualityList);
	}

	/**
	 * Add a quality configuration.
	 * @param quality the quality to add
	 */
	public void addQuality(Quality quality) {
		if (this.qualityList == null) {
			this.qualityList = new ArrayList<>();			
		}
		this.qualityList.add(quality);
	}

	/**
	 * Get a unmodifiable list with sources configuration.
	 * @return the sourceList
	 */
	public List<Source> getSourceList() {
		return Collections.unmodifiableList(sourceList);
	}

	/**
	 * Add a source configuration.
	 * @param source the source to add
	 */
	public void addSource(Source source) {
		if (this.sourceList == null) {
			this.sourceList = new ArrayList<>();			
		}
		this.sourceList.add(source);
	}

	/**
	 * Get the reaction configurations.
	 * @return the reaction
	 */
	public Reaction getReaction() {
		return reaction;
	}

	/**
	 * Set the reaction configurations.
	 * @param reaction the reaction to set
	 */
	public void setReaction(Reaction reaction) {
		this.reaction = reaction;
	}

	/*********************************************************
	 * Options and Reports
	 *********************************************************/

	/**
	 * Get the option configuration
	 * @return the option
	 */
	public Option getOption() {
		return option;
	}

	/**
	 * Set the option configuration
	 * @param option the option to set
	 */
	public void setOption(Option option) {
		this.option = option;
	}

	/**
	 * Get the time configuration
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * Set the time configuration
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}

	/**
	 * Get the report configuration
	 * @return the report
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * Set report configuration
	 * @param report the report to set
	 */
	public void setReport(Report report) {
		this.report = report;
	}

	/*********************************************************
	 * Network Design
	 *********************************************************/

	/**
	 * Get the backdrop.
	 * @return the backdrop
	 */
	public Backdrop getBackdrop() {
		return backdrop;
	}

	/**
	 * Set the backdrop.
	 * @param backdrop the backdrop to set
	 */
	public void setBackdrop(Backdrop backdrop) {
		this.backdrop = backdrop;
	}

	/**
	 * Get a unmodifiable list with labels
	 * @return the labels
	 */
	public List<Label> getLabels() {
		return Collections.unmodifiableList(labels);
	}

	/**
	 * Add a label to network
	 * @param label the label to add
	 */
	public void addLabel(Label label) {
		if (this.labels == null) {
			this.labels = new ArrayList<>();			
		}
		this.labels.add(label);
	}

	/**
	 * Get a unmodifiable list with tags
	 * @return the tags
	 */
	public List<Tag> getTags() {
		return Collections.unmodifiableList(tags);
	}

	/**
	 * Add a tag to network
	 * @param tag the tag to add
	 */
	public void addTag(Tag tag) {
		if (this.tags == null) {
			this.tags = new ArrayList<>();			
		}
		this.tags.add(tag);
	}

	@Override
	public String toString() {
		String text;
		text = "Network:\n";
		text += "Title: " + this.title + "\n";
		text += "Node: \n";
		for (Node node : this.getNodes()) {
			text += node.toString();
		}
		text += "Link: \n";
		for (Link link : this.getLinks()) {
			text += link.toString();
		}
		return text;
	}

}
