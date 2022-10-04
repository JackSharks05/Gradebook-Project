import java.util.*;
public class Class{
  String name;
  ArrayList<Student> roster;
  ArrayList<Assignment> assignments = new ArrayList<Assignment>();
  ArrayList<String> assignmentsandweights = new ArrayList<String>(); //type, weight
  ArrayList<String> assignmentTypes = new ArrayList<String>();
  ArrayList<Integer> assignmentWeights = new ArrayList<Integer>();

  // public Class(String name, ArrayList<Student> roster, ArrayList<String> agn, ArrayList<String> key){
  //   this.name = name;
  //   this.roster = roster;
  //   for (int i = 0; i < agn.size(); i++){
  //     assignments.add(new Assignment(agn.get(i).substring(0,agn.get(i).indexOf(",")),agn.get(i).substring(agn.get(i).indexOf(",") + 1,agn.get(i).length())));
  //   }
  // }
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
  // public void addKey(ArrayList<String> weightkey){
  //   for (int i = 0; i < weightkey.size(); i++){
  //     assignmentTypes.add(weightkey.get(i).substring(0,weightkey.get(i).indexOf(",")));
  //     assignmentWeights.add(Integer.parseInt(weightkey.get(i).substring(weightkey.get(i).indexOf(",") + 1, weightkey.get(i).length())));
  //   }
  // }
  public Class(){}
  public void addAssignment(Assignment a){assignments.add(a);}
  public String getName(){return this.name;}
  public void setName(String input){this.name = input;}
  public void removeStudent(int i){roster.remove(i);}
  public ArrayList<Student> getRoster(){return this.roster;}
  public int getAssignmentWeight(Assignment a){
    for (int i = 0; i < assignmentTypes.size(); i++){if (assignmentTypes.get(i).equalsIgnoreCase(a.getType())){return assignmentWeights.get(i);}}
    return -1;
  }
  public ArrayList<String> getAssignmentTypes(){return this.assignmentTypes;}
  public ArrayList<Assignment> getAssignments(){return this.assignments;}
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
