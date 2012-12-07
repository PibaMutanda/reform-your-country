package reformyourcountry.search;

import java.util.Map;

public interface Searchable {
    
    
    public Map<String ,String>  getCriterias();
    
    public String getBoostedCriteriaName();
    
    public Long getId();
    
    
}
