package com.mercadolibre.model;

/**
 * @author Guille Campos
 */
public class StatsResult {
	
	private String total;        
    private boolean _id;        
    
    public String getTotal() {
    	return total;
    }    
    public void setTotal(String total) {
    	this.total = total;
    }
	public boolean is_id() {
		return _id;
	}
	public void set_id(boolean _id) {
		this._id = _id;
	}            
}
