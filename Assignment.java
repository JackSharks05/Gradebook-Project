/*
Jack de Haan
Mr. Cruté
AT1- E Block
October 7, 2022
Assignment.java

The Assignment Class holds information for Assignment: the assignment name, type, and the class the assignment is in.
*/

import java.util.*;
public class Assignment{
  String name;
  String type;
  Class myclass;
  //This constructor is used to create a temporary Assignment object: i.e., one that should soon be replaced. The attributes are initialized just to prevent a NullPointerException.
  public Assignment(){
    this.name = "";
    this.type = "";
  }
  public Assignment(String name, String type, Class myclass){this.name = name;this.type = type; this.myclass = myclass;}
  //The methods access the various attributes of the Assignment object. There are no setters, as Assignments in this program are immutable, and all of their attributes are initialized when the object is created.
  public String getType(){return this.type;}
  public String getName(){return this.name;}
  public int getWeight(){return myclass.getAssignmentWeight((myclass.findAssignment(this.name)));}
}
