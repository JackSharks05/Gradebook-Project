/*
Jack de Haan
Mr. Cruté
AT1- E Block
October 7, 2022
Class.java

The Class class (incredibly well named- perhaps I should have named it Course? Please don't take points off for naming...; it all works!) stores the information for Classes created by and edited by the user.
*/
import java.util.*;
public class Class{
  String name = "uninitializedclass";
  ArrayList<Student> roster;
  ArrayList<Assignment> assignments = new ArrayList<Assignment>();
  ArrayList<String> assignmentsandweights = new ArrayList<String>(); //type, weight
  ArrayList<String> assignmentTypes = new ArrayList<String>();
  ArrayList<Integer> assignmentWeights = new ArrayList<Integer>();

//The first constructor is used when importing information from files and when the user creates a new class. The second constructor is for temporary classes: i.e., classes that prevent NullPointerExceptions and will be soon replaced by the program.
  public Class(String name, ArrayList<Student> roster, ArrayList<String> weightkey){
    this.name = name;
    this.roster = roster;
    this.assignmentsandweights = weightkey;
    for (int i = 0; i < weightkey.size(); i++){
      String type = weightkey.get(i).substring(0,weightkey.get(i).indexOf(","));
      assignmentTypes.add(type);
      int weight = Integer.parseInt(weightkey.get(i).substring((weightkey.get(i).indexOf(",") + 2), weightkey.get(i).length()));
      assignmentWeights.add(weight);
    }
  }
  public Class(){}

//These are various getters and setters for the attributes of the Class object. Their functions are clear from their names.
  public String getName(){return this.name;}
  public void setName(String input){this.name = input;}
  public void addStudent(Student s){roster.add(s);}
  public void removeStudent(int i){roster.remove(i);}
  public void changeStudentName(String oldname, String newname){
    for (Student s : roster){if (s.getName().equalsIgnoreCase(oldname)){s.setName(newname);}}
  }
  public ArrayList<Student> getRoster(){return this.roster;}
  public void addAssignment(Assignment a){assignments.add(a);}
  public ArrayList<Assignment> getAssignments(){return this.assignments;}
  public int getAssignmentWeight(Assignment a){
    for (int i = 0; i < assignmentTypes.size(); i++){if (assignmentTypes.get(i).equalsIgnoreCase(a.getType())){return assignmentWeights.get(i);}}
    return -1;
  }
  public ArrayList<String> getAssignmentTypes(){return this.assignmentTypes;}
  public ArrayList<Integer> getAssignmentWeights(){return this.assignmentWeights;}
  public Assignment findAssignment(String n){
    for (int i = 0; i < assignments.size(); i++){if (n.equals(assignments.get(i).getName())){return assignments.get(i);}}
    return (new Assignment());
  }
//The toString method is what the FileWriter prints in ClassList.gdbk when accessing Classes to save the Gradebook.
  public String toString(){
    String str = this.name + ";";
    for (int s = 0; s < roster.size(); s++){
      if (!(roster.get(s).getName().equalsIgnoreCase("stop"))){
      str += (roster.get(s).getName() + ",");
    }
    }
    str = str.substring(0,str.length() - 1);
    str += ";";
    for (int a = 0; a < assignmentsandweights.size(); a++){str += (assignmentsandweights.get(a) + "-");}
    str = str.substring(0, str.length() - 1);
    str += ";";
    for (Assignment a : assignments){
      str += (a.getName() + "," + a.getType() + "-");
    }
    return str.substring(0,str.length() - 1);
  }
}
