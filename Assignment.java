import java.util.*;
public class Assignment{
  String name;
  String type;
  Class myclass;
  public Assignment(){
    this.name = "error";
    this.type = "error";
  }
  public Assignment(String name, String type, Class myclass){this.name = name;this.type = type; this.myclass = myclass;}
  public String getType(){return this.type;}
  public String getName(){return this.name;}
  public int getWeight(){return myclass.getAssignmentWeight((myclass.findAssignment(this.name)));}
  public String toString(){return this.name + ";" + this.type + ";" + this.myclass.getName();}
}
