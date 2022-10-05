import java.util.*;
public class Class{
  String name;
  ArrayList<Student> roster;
  ArrayList<Assignment> assignments = new ArrayList<Assignment>();
  ArrayList<String> assignmentsandweights = new ArrayList<String>(); //type, weight
  ArrayList<String> assignmentTypes = new ArrayList<String>();
  ArrayList<Integer> assignmentWeights = new ArrayList<Integer>();

  public Class(String name, ArrayList<Student> roster){
    this.name = name;
    this.roster = roster;
  }
  public Class(String name, ArrayList<Student> roster, ArrayList<String> weightkey){
    this.name = name;
    this.roster = roster;
    this.assignmentsandweights = weightkey;
    for (int i = 0; i < weightkey.size(); i++){
      // System.out.println(weightkey.get(i));
      String type = weightkey.get(i).substring(0,weightkey.get(i).indexOf(","));
      // System.out.println(type);
      assignmentTypes.add(type);
      int weight = Integer.parseInt(weightkey.get(i).substring((weightkey.get(i).indexOf(",") + 2), weightkey.get(i).length()));
      // System.out.println(weight);
      assignmentWeights.add(weight);
    }
  }
  public Class(){}
  public String getName(){return this.name;}
  public void setName(String input){this.name = input;}
  public void addStudent(Student s){roster.add(s);}
  public void removeStudent(int i){roster.remove(i);}
  public ArrayList<Student> getRoster(){return this.roster;}
  public void changeStudentName(String oldname, String newname){
    for (Student s : roster){if (s.getName().equalsIgnoreCase(oldname)){s.setName(newname);}}
  }
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
  public String toString(){
    String str = this.name + ";";
    for (int s = 0; s < roster.size(); s++){str += (roster.get(s).getName() + ",");}
    str += ";";
    for (int a = 0; a < assignmentsandweights.size(); a++){str += (assignmentsandweights.get(a) + "-");}
    return str.substring(0, str.length() - 1);
  }
}

//set up categories/weights
//set up roster (student names)
//create assignments
  //choose a category (assume each assignment is out of 100%)
  //has a name
//edit grades
  //edit individual student grades
  //delete assignments
//work with files
  //if class exists, import data from file
  //else, create class
  //write data to file
    //saving mechanism
//exit program
