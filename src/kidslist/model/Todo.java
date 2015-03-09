package kidslist.model;

public class Todo {
	private long id;
    private String category;
    private boolean status;
    private String summary;
    private String description;
    private String points;
    private String location;
    //String created_at;
 
    // constructors
    public Todo() {
        this.summary=null;
        this.status=false;
    }
 
    public Todo(String summary, boolean status) {
    	super();
        this.summary = summary;
        this.status = status;
    }

 
    // setters
    public void setId(long l) {
        this.id = l;
    }
 
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public void setPoints(String points) {
    	this.points = points;
    }
    
    public void setLocation(String location){
    	this.location = location;
    }
    /*public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }*/
 
    // getters
    public long getId() {
        return this.id;
    }
 
    public String getCategory() {
        return this.category;
    }
    
    public String getSummary() {
        return this.summary;
    }
    
    public String getDescription() {
        return this.description;
    }
 
    public boolean getStatus() {
        return this.status;
    }
    
    public String getPoints() {
    	return this.points;
    }
    
    public String getLocation() {
    	return this.location;
    }
}
