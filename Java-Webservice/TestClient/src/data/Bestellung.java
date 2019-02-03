package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class Bestellung implements Serializable {
	
	
	private int id;
    private List<BestellPosition> bps = new ArrayList<BestellPosition>();
    

    public int getId() {
        return id;
    }
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }
    public List<BestellPosition> getBps() {
    	return bps;
    }
    public void setBps(List<BestellPosition> bps) {
    	this.bps = bps;
    }
    
    public Bestellung(int id) {
    	this.id = id;
    }   
    public Bestellung() {
    	
    }
    public void addBp(BestellPosition bp) {
    	bps.add(bp);
    }
}
