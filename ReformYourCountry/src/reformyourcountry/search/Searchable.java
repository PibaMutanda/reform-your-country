package reformyourcountry.search;

import java.util.List;
import java.util.Map;

import reformyourcountry.security.Privilege;

public interface Searchable {
    
    
    public Map<String ,String>  getCriterias();
    
    public String getBoostedCriteriaName();
    
    public Long getId();
    
    /** Returns the fileds to be excluded is the current user does not have the param privilege */
   // public Map<String, String>  getProtectedCriterias(Privilege privilege);
    
    //public String getSearchDespcription();  // TODO REMOVE ME .
    
  //  public /*static*/ List<String> getCriteriaNamesToSearchOn();
    
}
