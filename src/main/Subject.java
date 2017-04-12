package main;

public class Subject {
  
  private String _id;
  private double _ketamine;
  private double _telazol;
  private double _isoflurane;
  private double _normalizedTotalKetamineIsoflurane;
  
  public Subject(String id){
    _id = id;
    _ketamine = 0;
    _telazol = 0;
    _isoflurane = 0;
    _normalizedTotalKetamineIsoflurane = 0;
  }
  
  public void addKetamine(double amount){
    _ketamine += amount;
  }
  
  public void addTelazol(double amount){
    _telazol += amount;
  }
  
  public void addIsoflurane(double amount){
    _isoflurane += amount;
  }
  
  public void setNormalizedTotalKetamineIsoflurane(double amount){
    _normalizedTotalKetamineIsoflurane = amount;
  }
  
  public double getKetamine(){
    return _ketamine;
  }
  
  public double getTelazol(){
    return _telazol;
  }
  
  public double getIsoflurane(){
    return _isoflurane;
  }
  
  public double getNormalizedTotalKetamineIsoflurane(){
    return _normalizedTotalKetamineIsoflurane;
  }
  
  
  public String toString(){
    return _id + "_18months_up_DTI_manualBM_centered" + "," + _ketamine + "," + _telazol + "," + _isoflurane + "," + _normalizedTotalKetamineIsoflurane;
  }
}
