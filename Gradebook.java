/*
Gradebook.java
This is the main class which keeps the global variables (global roster of students, list of classes, and list of grades).
It includes all the GUI methods (the methods that interact with the user to gain information which will eventually be stored in the global variables).
It also has the important startup and save methods which read and write to the files which store the information in the global variables.
*/
import java.io.*;
import java.util.*;
public class Gradebook{
  //These are the global variables which multiple methods in this class are constantly accessing.
  public static ArrayList<Student> globalroster;
  public static ArrayList<Class> classes;
  public static ArrayList<Grade> gradelist;
  //The main method, which initializes the global variables and calls the mainGUI method.
  public static void main(String[] args){globalroster = new ArrayList<Student>();classes = new ArrayList<Class>();gradelist = new ArrayList<Grade>();mainGUI(false);}
//The isYes method checks an input fed externally from a scanner and determines if the user inputted yes (true) or no (false). The default is false.
public static boolean isYes(String input){
  String[] yes = {"Y","sure","ok"};
  if ((input.toUpperCase()).startsWith("N") || input.equalsIgnoreCase("stop")){return false;}
  else {
    for (String y : yes){if (input.equalsIgnoreCase(y)||input.toUpperCase().startsWith((y.charAt(0)+"").toUpperCase())){return true;}}
  }
  return false;
}
//The mainGUI method is where the hub that points the user to other GUIs. When the method starts up for the first time (boolean repeating = false), it gives the user the starting up animation, but its main function is the switch statement to call the other GUIs.
public static void mainGUI(boolean repeating){
  if (!repeating){System.out.print("\033[H\033[2J");startup();}
  boolean valid = false;
  Scanner guiScanner = new Scanner(System.in);
  int switchint = 0;
  String input = "";
  System.out.println("\033[33m1: \033[0mMake a new class");
  System.out.println("\033[33m2: \033[0mMake a new student");
  System.out.println("\033[33m3: \033[0mMake a new assignment");
  System.out.println("\033[33m4: \033[0mEdit a class");
  System.out.println("\033[33m5: \033[0mEdit a student");
  System.out.println("\033[33m0: \033[0mQuit GradebookOS");
  while (!valid){
  System.out.println("\033[31mPlease enter an integer from 0-5.\033[33m");
  input = guiScanner.nextLine();
  try {
    switchint = Integer.parseInt(input);
    if (switchint >= 0 && switchint <= 5){valid = true;}
  } catch(NumberFormatException e) {
  }
}
  switch (switchint){
    case 0:
      quitGUI(guiScanner);break;
    case 1:
      classCreateGUI("");break;
    case 2:
      studentCreateGUI("");break;
    case 3:
      assignmentGUI();break;
    case 4:
      classEditGUI();break;
    case 5:
      studentEditGUI(new Class());break;
    default:
      System.out.println("error lol");
    }
    System.out.print("Anything else? ");
  if (isYes(guiScanner.nextLine())){mainGUI(true);} else {quitGUI(guiScanner);}
}
//The classCreateGUI does what it sounds like it does: it creates a class. Through many inputs from the user, the method gleans information about a class, such as its name, the assignment types and corresponding weights, and which students it has in its class. At the end, it adds the newly created class to the global classlist (classes). Some methods, such as the classEditGUI and assignmentGUI, may reference this GUI, which is why it has an input string for the class's name (default is empty).
public static void classCreateGUI(String name){
  System.out.print("\033[H\033[2J\033[0m");
  FileWriter fWriter = null;
  PrintWriter output = null;
  Scanner classScanner = new Scanner(System.in);
  String input = "";
  boolean done = false;
  while (!done){
  String localname = name;
  boolean stop = false;
  ArrayList<Student> localroster = new ArrayList<Student>();
  ArrayList<String> localassignmentsandweights = new ArrayList<String>();
  if (localname.equals("")){
  System.out.print("Class name: ");
  while (!stop){
    input = classScanner.nextLine();
    if (-1 == findClass(input)){localname = input;stop = true;}
    else{System.out.print("\033[31mClass already exists.\033[0m Please use a different name: ");}
  }
  }
  System.out.println("List students' names: (\033[31m\"stop\"\033[0m to stop, \033[31m\"ls\"\033[0m to list current students)");
  stop = false;
  while (!stop){
    input = classScanner.nextLine();
    if (input.equalsIgnoreCase("stop")){stop = true;}
    else if (input.equalsIgnoreCase("ls")){if(globalroster.size()==0){System.out.println("\033[31mNo students found.\033[0m");}else{for (Student s : globalroster){if(!s.getName().equals("stop")){System.out.println("\033[33m" + s.getName() + "\033[0m");}}}}
    else if (findStudent(globalroster,input) != -1){
      localroster.add(globalroster.get(findStudent(globalroster,input)));
      }
    else if (findStudent(localroster,input) == -1){Student newStudent = new Student(input);localroster.add(newStudent);globalroster.add(newStudent);}
    else {System.out.print("\033[31mStudent already exists in class.\033[0m Please find a different student: ");}
  }
  System.out.println("List assignment types (type, weight):");
  int counter = 0;
  stop = false;
  while (!stop){
    System.out.println("The rest of the class's assignments should add up to \033[35m" + (100 - counter) + "%\033[0m of the grade.");
    input = classScanner.nextLine();
    int weight = 0;
    try {
        weight = Integer.parseInt((input.substring(input.indexOf(",") + 1,input.length())).replaceAll(" ", ""));
    } catch(NumberFormatException e) {
        System.out.print("\033[31mError with your input: ");
    }
    if (input.indexOf(",") == -1){System.out.print("Your input should be formatted as (type, weight).\033[0m Please try again: ");}
    else if (!(weight > 0)){System.out.print("The weight should be a positive integer:\033[0m ");}
    else if (weight + counter > 100){System.out.print("Weight exceeds limit.\033[0m Please try again: ");}
    else if (doesTypeExist(localassignmentsandweights,input)){System.out.print("Type already exists.\033[0m Please try again: ");}
    else {localassignmentsandweights.add(input);counter += weight;}
    if (counter == 100){stop = true;}
  }
  classes.add(new Class(localname,localroster,localassignmentsandweights));
  for (Student s : localroster){globalroster.get(findStudent(globalroster,s.getName())).addClass(classes.get(findClass(localname)));}
  System.out.print("\033[32mClass " + localname + " is made.\033[0m Would you like to make another class? ");
  if(!isYes(classScanner.nextLine())){done = true;}
  }
}
//The studentCreateGUI also thoroughly interacts with the user to gain information about a new student it will create, such as the student's name and any classes the student is in.
public static void studentCreateGUI(String externalinput){
System.out.print("\033[H\033[2J\033[0m");
  Scanner studentScanner = new Scanner(System.in);
  boolean done = false;
    while (!done){
    String name = externalinput;
    String input = "";
    boolean stop = false;
    ArrayList<Class> classList = new ArrayList<Class>();
    if (externalinput.equals("")){
    System.out.print("Student name: ");
    while (!stop){
      input = studentScanner.nextLine();
      if (findStudent(globalroster,input) == -1){name = input;stop = true;}
      else {System.out.println("\033[31mStudent already exists.\033[0m Please use a different name: ");}
    }
  }
    System.out.println("List the id('s) of the class(es) the student is in (\033[31m\"stop\"\033[0m to stop, \033[31m\"ls\"\033[0m to list all classes):");
    stop = false;
    while (!stop){
      input = studentScanner.nextLine();
      if (input.equalsIgnoreCase("stop")){stop = true;}
      else if (input.equalsIgnoreCase("ls")){for (Class cla : classes){System.out.println("\033[33m" + cla.getName() + "\033[0m");}System.out.print("Which class(es) is the student in? ");}
      else if (findClass(input) == -1){System.out.println("\033[31mCould not find class.\033[0m Please try again: ");}
      else {classList.add(classes.get(findClass(input)));}
    }
    Student newStudent = new Student(name, classList);
    globalroster.add(newStudent);
    for (Class c : classList){c.addStudent(newStudent);}
    System.out.print("\033[32mStudent " + name + " is made.\033[0m Would you like to make another student? ");
    if(!isYes(studentScanner.nextLine())){done = true;}
  }
}
//The assignmentGUI creates a new assignment with its name, weight, and class it's in.
public static void assignmentGUI(){
System.out.print("\033[H\033[2J\033[0m");
    Scanner assignmentScanner = new Scanner(System.in);
    if (classes.size() == 0){
      System.out.print("\033[31mYou cannot create an assignment without having created a class first. Would you like to make a new class? \033[0m");
      if (isYes(assignmentScanner.nextLine())){classCreateGUI("");}
    } else {
    boolean done = false;
      while (!done){
      String name = "";
      String type = "";
      String input = "";
      String inclass = "";
      int weight = 0;
      Class localclass = new Class();
      System.out.println("This assignment is for which class: (\033[31m\"ls\"\033[0m to list all classes)");
      boolean stop = false;
      while (!stop){
        inclass = assignmentScanner.nextLine();
        if (inclass.equalsIgnoreCase("ls")){for (Class c : classes){System.out.println("\033[33m"+c.getName()+"\033[0m");}System.out.print("This assignment is for which class? ");}
        else if (findClass(inclass) == -1){System.out.println("\033[31mCould not find class.\033[0m Please try again: ");}
        else {localclass = classes.get(findClass(inclass));stop = true;}
      }
      System.out.print("Assignment name: ");
      stop = false;
      while (!stop){
        input = assignmentScanner.nextLine();
        if (-1 == findAssignment(localclass,input)){name = input;stop = true;}
        else{System.out.println("\033[31mAssignment already exists.\033[0m Please use a different name: ");}
      }
      System.out.print("Assignment type: ");
      stop = false;
      while (!stop){
        input = assignmentScanner.nextLine();
        if (-1 == findAssignmentType(localclass,input)){System.out.print("\033[31mAssignment type doesn't exist.\033[0m Please try again: ");}
        else{type = input;stop = true;}
      }
      (classes.get(findClass(inclass))).addAssignment(new Assignment(name,type,(classes.get(findClass(inclass)))));
      System.out.print("\033[32mAssignment " + name + " is made.\033[0m Would you like to make another assignment? ");
      if(!isYes(assignmentScanner.nextLine())){done = true;}
  }
}
}
//The classEditGUI allows the user to edit the name of the class and to add or remove any students.
public static void classEditGUI(){
System.out.print("\033[H\033[2J\033[0m");
  Scanner classScanner = new Scanner(System.in);
  Class localclass = new Class();
  System.out.print("Which class would you like to edit? (\033[31m\"ls\"\033[0m to list all classes) ");
  boolean stop = false;
  boolean creatingClass = false;
  String input = "";
  while (!stop){
    input = classScanner.nextLine();
    if (input.equalsIgnoreCase("ls")){for (Class c : classes){System.out.println("\033[33m"+c.getName()+"\033[0m");}System.out.print("Which class would you like to edit? ");}
    else if (-1 == findClass(input)){
      System.out.print("\033[31mCannot find class.\033[0m Would you like to create a new class of this name?: ");
      creatingClass = isYes(classScanner.nextLine());
      if (creatingClass){stop = true;}else{System.out.print("Which class would you like to edit? ");}
    }
    else{localclass = classes.get(findClass(input));stop = true;}
  }
  if (creatingClass){classCreateGUI(input);} else {
    System.out.print("Would you like to change the name of the class? ");
    if (isYes(classScanner.nextLine())){
      System.out.print("Current name: " + localclass.getName() +". New name: ");
      input = classScanner.nextLine();
      localclass.setName(input);
    }
    System.out.print("Would you like to add any students? ");
    if (isYes(classScanner.nextLine())){
      ArrayList<Student> adding = new ArrayList<Student>();
      for (Student s : globalroster){
        if (isStudentinClass(localclass.getName(),s.getClassList())){
          System.out.println(s.getName() + " is already in " + localclass.getName() + "!");
        } else {
        System.out.print("\033[32mAdd " + /*s.getSurname() + ", " + s.getFirstName()*/ s.getName() + "?\033[0m ");
        if (isYes(classScanner.nextLine())){
          adding.add(s);
        }
      }
      }
      for (Student a : adding){
        classes.get(findClass(localclass.getName())).addStudent(a);
        globalroster.get(findStudent(globalroster,a.getName())).removeClass(classes.get(findClass(localclass.getName())).getName());
      }
    }
    System.out.print("Would you like to remove any students? ");
    if (isYes(classScanner.nextLine())){
      ArrayList<Student> removing = new ArrayList<Student>();
      for (Student s : localclass.getRoster()){
        System.out.print("\033[31mRemove " + /*s.getSurname() + ", " + s.getFirstName()*/ s.getName() + "?\033[0m ");
        if (isYes(classScanner.nextLine())){
          removing.add(s);
        }
      }
      for (Student r : removing){
        classes.get(findClass(localclass.getName())).removeStudent(findStudent(localclass.getRoster(),r.getName()));
        globalroster.get(findStudent(globalroster,r.getName())).removeClass(classes.get(findClass(localclass.getName())).getName());
      }
    }



  }

}
//The studentEditGUI allows the user to edit any student's name, but most importantly allows them to access the class and specific assignment grades of any student given a class and assignment.
public static void studentEditGUI(Class c){
System.out.print("\033[H\033[2J\033[0m");
  Scanner studentScanner = new Scanner(System.in);
  if (globalroster.size() == 0){
    System.out.println("\033[31mYou cannot edit a student without having created a student first. Would you like to make a new student?\033[0m ");
    if (isYes(studentScanner.nextLine())){studentCreateGUI("");}
  } else {
  Student localstudent = new Student();
  Class localclass = c;
  Assignment localassignment = new Assignment();
  Double localgrade = -1.0;
  String newname = "";
  Double newgrade = -1.0;
  System.out.print("Which student would you like to edit? (\033[31m\"ls\"\033[0m to list all students) ");
  boolean stop = false;
  boolean creatingStudent = false;
  boolean creatingAssignment = false;
  String input = "";
  while (!stop){
    input = studentScanner.nextLine();
    if (input.equalsIgnoreCase("ls")){for (Student s : globalroster){if(!(s.getName().equalsIgnoreCase("stop"))){System.out.println("\033[33m" + s.getName() + "\033[0m");}}System.out.print("Which student would you like to edit? ");}
    else if (-1 == findStudent(globalroster,input)){
      System.out.print("\033[31mCannot find student.\033[0m Would you like to create a new student of this name?: ");
      creatingStudent = isYes(studentScanner.nextLine());
      if (creatingStudent){stop = true;}else{System.out.print("Which student would you like to edit? ");}
    }
    else{localstudent = globalroster.get(findStudent(globalroster,input));newname=localstudent.getName();stop = true;}
  }
  if (creatingStudent){studentCreateGUI(input);} else {
    System.out.print("Would you like to change the name of the student? ");
    if (isYes(studentScanner.nextLine())){
      stop = false;
      System.out.print("Current name: " + localstudent.getName() +". New name: ");
      while (!stop){
        input = studentScanner.nextLine();
        if (findStudent(globalroster,input) == -1){newname = input;stop = true;}
        else {System.out.println("\033[31mStudent with this name already exists.\033[0m Please use a different name: ");}
      }
      System.out.println("\033[32mName was successfully changed.\033[0m");
    }
    System.out.print("Would you like to edit the student's grade? ");
    if (isYes(studentScanner.nextLine())){
      if (localstudent.getClassList().size() == 0){
        System.out.println("You cannot edit the student's grade if the student isn't in any classes.");
      } else {
      if (!(localclass.equals(new Class()))){
      System.out.print("For which class is the student's grade changing? (\033[31m\"ls\"\033[0m to list all classes)");
      stop = false;
      while (!stop){
        input = studentScanner.nextLine();
        if (input.equalsIgnoreCase("ls")){for (Class cl : localstudent.getClassList()){System.out.println("\033[33m" + cl.getName() + "\033[0m");}System.out.print("Which class? ");}
        else if (-1 == findClass(input)){
          System.out.print("\033[31mCannot find class.\033[0m Please try again: ");
        } else if (!(isStudentinClass(input,localstudent.getClassList()))){
          System.out.print("\033[31mStudent is not in class.\033[0m Would you like to add " + newname + " to this class? ");
          if (isYes(studentScanner.nextLine())){classes.get(findClass(input)).addStudent(localstudent);stop = true;localclass = classes.get(findClass(input));}else{System.out.print("Please try again: ");}
        } else if (classes.get(findClass(localclass.getName())).getAssignments().size() == 0){
          System.out.print("\033[31m" + classes.get(findClass(input)).getName() +" doesn't have any assignments.\033[0m Would you like to make a new assignment? ");
          if (isYes(studentScanner.nextLine())){assignmentGUI();creatingAssignment = true; stop = true;} else {System.out.print("Which class? ");}
        }
        else{localclass = classes.get(findClass(input));stop = true;}
      }
    }
    if (!creatingAssignment){
      System.out.println(newname + "'s current grade for the class is \033[35m" + getClassGrade(localstudent,localclass.getName()) + "(" + localstudent.getLetterGrade(getClassGrade(localstudent,localclass.getName())) + ")\033[0m.");
      System.out.print("In which assignment is the student's grade changing? (\033[31m\"ls\"\033[0m to list all assignments)");
      stop = false;
      while (!stop){
        input = studentScanner.nextLine();
        if (input.equalsIgnoreCase("ls")){for (Assignment a : localclass.getAssignments()){if (localclass.getAssignments().size() == 0){System.out.println("\033[31mNo assignments found.\033[0m");} else {System.out.println("\033[33m" + a.getName() + "\033[0m");}}System.out.print("Which assignment would you like to edit? ");}
        else if (-1 == findAssignment(localclass,input)){
          System.out.print("\033[31mCannot find assignment.\033[0m Please try again: ");
        }
        else{localassignment = localclass.getAssignments().get(findAssignment(localclass,input));stop = true;}
      }
      localgrade = localstudent.getGrade(localclass, localassignment, localstudent, gradelist);
      if (localgrade == -1.0){System.out.print("\033[31m" + newname + " does not have a grade for this assignment.\033[0m New grade: ");}
      else {
      System.out.print(newname + "'s current grade in the " + localassignment.getName() + " assignment of " + localclass.getName() + " class is " + localgrade + " (" + localstudent.getLetterGrade(localgrade) + "). New grade for this assignment: ");}
      stop = false;
      input = studentScanner.nextLine();
      if (isDouble(input)){newgrade=Double.parseDouble(input);stop = true;}
      while (!stop){
          System.out.print("\033[31mPlease enter a positive integer including or under 100:\033[0m ");
          input = studentScanner.nextLine();
          if (isDouble(input)){newgrade=Double.parseDouble(input);stop = true;}
        }
        if (localgrade == -1.0){
          System.out.println("\033[32mAssignment grade was successfully set to " + newgrade + ".\033[0m");
        }else{
      System.out.println("\033[32mAssignment grade was successfully changed from "+ localgrade + " to " + newgrade + ".\033[0m");
    }
  }
    }
  }
  globalroster.get(findStudent(globalroster,localstudent.getName())).setName(newname);
  localstudent.setGrade(localclass,localassignment,localstudent,newgrade);
  if (!(newname.equals(localstudent.getName()))){
  classes.get(findClass(localclass.getName())).removeStudent(findStudent(localclass.getRoster(),localstudent.getName()));
  classes.get(findClass(localclass.getName())).addStudent(globalroster.get(findStudent(globalroster,newname)));
}
}
}
}
//The quitGUI is the final GUI called by the program, and it's just to ask the user if the gradebook should be saved... and it also has some fun animations.
public static void quitGUI(Scanner s){
  System.out.print("\033[0mWould you like to save all changes made? ");
  if (isYes(s.nextLine())){save();}else{System.out.println("\033[31mRecent changes were not saved.\033[0m");}
  randomizedLoading("\033[36mShutting down GradebookOS");
  System.out.println("\033[H\033[2J\033[0m\n");
  System.exit(1);
}
//The isDouble menthod is used for checking if an input for a grade (which should be a double) is valid (true = input is valid, false = input is not valid).
public static boolean isDouble(String str) {
  try {
      double d = Double.parseDouble(str);
      if (d >= 0.0 && d <= 100.0){
      return true;
      }
      return false;
  } catch(NumberFormatException e) {
      return false;
  }
}
//The findClass method finds the index of the class within the global classes array given the class's name
public static int findClass(String name){
  if (classes.size() == 0){return -1;}
  for (int i = 0; i < classes.size(); i++){if (name.equalsIgnoreCase((classes.get(i)).getName())){return i;}}
  return -1;
}
//The findAssignment method finds the index of an assignment within a class's assignment array given the assignment's name
public static int findAssignment(Class c,String name){
  if (c.getAssignments().size() == 0){return -1;}
  for (int i = 0; i < c.getAssignments().size(); i++){if (name.equals(c.getAssignments().get(i).getName())){return i;}}
  return -1;
}
//The findStudent method finds the index of a student within a given roster given the student's name
public static int findStudent(ArrayList<Student> roster,String name){
  for (int i = 0; i < roster.size(); i++){if (name.equalsIgnoreCase(roster.get(i).getName())){return i;}}
  return -1;
}
//The findAssignmentType method finds the index of the assignment type within a class's assignmenttypes array
public static int findAssignmentType(Class c,String type){
  if (c.getAssignmentTypes().size() == 0){return -1;}
  for (int i = 0; i < c.getAssignmentTypes().size(); i++){if (type.equalsIgnoreCase(c.getAssignmentTypes().get(i))){return i;}}
  return -1;
}
//The isStudentinClass method determines whether a student is in a class's roster given the class's name and the student's classList
public static boolean isStudentinClass(String classname, ArrayList<Class> classList){
  for (Class c : classList){
    if (classname.equalsIgnoreCase(c.getName())){return true;}
  }
  return false;
}
//The doesTypeExist method determines whether an assignment type exists given the type's name and an class's assignment weightkey
public static boolean doesTypeExist(ArrayList<String> weightkey, String type){
  for (String s : weightkey){
    if (type.substring(0,s.indexOf(",")).equalsIgnoreCase(s.substring(0,s.indexOf(",")))){return true;}
  }
  return false;
}
//The getClassGrade method searches inside the global gradelist
public static double getClassGrade(Student s, String classname){
  ArrayList<Double> weightedGradeAverages = new ArrayList<Double>();
  double gradesum = 0.0;
  int assignmentcount = 0;
  int weightkeycounter = 0;
  Class c = classes.get(findClass(classname));
  for (String type : c.getAssignmentTypes()){
    gradesum = 0.0;
    assignmentcount = 0;
    for (Grade g : s.getGradeList()){
      if (g.getClassName().equals(classname)){
        gradesum += g.getGrade();
        assignmentcount++;
      }
    }
  weightedGradeAverages.add(c.getAssignmentWeights().get(weightkeycounter) * 0.01 * (gradesum / assignmentcount));
  weightkeycounter++;
}
  double total = 0.0;
  for (Double d : weightedGradeAverages){
    total += d;
  }
  return total;
}
//The startup function calls the startupAnimation method but more importantly reads the files (if they exist) and insert the data into the global variables.
public static void startup(){
  FileReader classReader = null;
  Scanner classInput = null;
  FileReader studentReader = null;
  Scanner studentInput = null;
  FileReader assignmentReader = null;
  Scanner assignmentInput = null;
  FileReader gradeReader = null;
  Scanner gradeInput = null;
  boolean fileFound = false;
  //ClassList
  try {
     classReader = new FileReader("ClassList.gdbk");
     classInput = new Scanner(classReader);
     studentReader = new FileReader("GlobalRoster.gdbk");
     studentInput = new Scanner(studentReader);
     assignmentReader = new FileReader("AssignmentsList.gdbk");
     assignmentInput = new Scanner(assignmentReader);
     gradeReader = new FileReader("Gradebook.gdbk");
     gradeInput = new Scanner(gradeReader);
     fileFound = true;
  } catch (IOException e) {
  }
  if (fileFound){
    while (classInput.hasNextLine()){
    String line = classInput.nextLine();
    String[] words = line.split(";");
    String[] studentNames = words[1].split(",");
    String[] weightkey = words[2].split("-");
    ArrayList<Student> localroster = new ArrayList<Student>();
    for (String s : studentNames){localroster.add(new Student(s));}
    ArrayList<String> localassignmentsandweights = new ArrayList<String>();
    for (String w : weightkey){localassignmentsandweights.add(w);}
    classes.add(new Class(words[0], localroster, localassignmentsandweights));
  }

  //Student list / getRoster
  while (studentInput.hasNextLine()){
  String line = studentInput.nextLine();
  String[] words = line.split(";");
  String[] classnamesList = words[1].split(",");
  ArrayList<Class> localclasslist = new ArrayList<Class>();
  for (String a : classnamesList){localclasslist.add(classes.get(findClass(a)));}
  globalroster.add(new Student(words[0], localclasslist));
}
while (assignmentInput.hasNextLine()){
  String line = assignmentInput.nextLine();
  String[] words = line.split(";");
  classes.get(findClass(words[2])).addAssignment(new Assignment(words[0],words[1],classes.get(findClass(words[2]))));
}
while (gradeInput.hasNextLine()){
  String line = gradeInput.nextLine();
  String[] words = line.split(";");
  gradelist.add(new Grade(
  classes.get(findClass(words[0])),
  classes.get(findClass(words[0])).getAssignments().get(findAssignment(classes.get(findClass(words[0])),words[1])),
  globalroster.get(findStudent(globalroster,words[2])),
  Double.parseDouble(words[3])
  ));
}
for (Grade g : gradelist){
  if (findStudent(globalroster,g.getStudentName()) != -1){
    globalroster.get(findStudent(globalroster,g.getStudentName())).addGrade(g);
  }
}
try {
    classReader.close();
    studentReader.close();
    assignmentReader.close();
    gradeReader.close();
} catch (IOException e){
    System.out.println("\033[31mError closing file: " + e);
    System.exit(1);
}
//startupAnimation(true);
} else {
  startupAnimation(false);
}

}
//The save function takes all of the global variables and exports the data appropriately in files for it to be read next startup.
public static void save(){
  FileWriter classWriter = null;
  FileWriter studentWriter = null;
  FileWriter assignmentWriter = null;
  FileWriter gradeWriter = null;
  PrintWriter classPrinter = null;
  PrintWriter studentPrinter = null;
  PrintWriter assignmentPrinter = null;
  PrintWriter gradePrinter = null;
  try {
      classWriter = new FileWriter("ClassList.gdbk", false);
      classPrinter = new PrintWriter(classWriter);
  } catch (IOException e){
      System.out.println("\033[31mError opening Class List file: " + e);
      System.out.println("Could not save.\033[0m");
  }
  for (Class c : classes){classPrinter.println(c);}
  try {
      studentWriter = new FileWriter("GlobalRoster.gdbk", false);
      studentPrinter = new PrintWriter(studentWriter);
  } catch (IOException e){
      System.out.println("\033[31mError opening Global Roster file: " + e);
      System.out.println("Could not save.\033[0m");
  }
  // gradelist = new ArrayList<Grade>();
  classes = new ArrayList<Class>();
  for (Student s : globalroster){studentPrinter.println(s);for (Grade g : s.getGradeList()){gradelist.add(g);}for (Class c : s.getClassList()){c.addStudent(s);}}
  try {
      assignmentWriter = new FileWriter("AssignmentsList.gdbk", false);
      assignmentPrinter = new PrintWriter(assignmentWriter);
  } catch (IOException e){
      System.out.println("\033[31mError opening Assignments List file: " + e);
      System.out.println("Could not save.\033[0m");
  }
  for (Class c : classes){
    for (int i = 0; i < c.getAssignments().size(); i++){

      assignmentPrinter.println(c.getAssignments().get(i));
      System.out.println("Assignment " + i + ": " + c.getAssignments().get(i));
    }
  }
  try {
      gradeWriter = new FileWriter("Gradebook.gdbk", false);
      gradePrinter = new PrintWriter(gradeWriter);
  } catch (IOException e){
      System.out.println("\033[31mError opening gradebook: " + e);
      System.out.println("Could not save.\033[0m");
  }
  for (Grade g : gradelist){gradePrinter.println(g);}

  try {
    classWriter.close();
    studentWriter.close();
    assignmentWriter.close();
    gradeWriter.close();
  } catch (IOException e){
      System.out.println("Error closing file: " + e);
      System.exit(1);
  }
  // System.out.print("Saving");
  randomizedLoading("Saving");
  System.out.println("\r\033[32mGradebook saved successfully.\033[0m");
}
//The startupAnimation method enhances the user experience with a realistic (though not actually real) loading up animation. The message changes whether the input files were found (boolean found).
public static void startupAnimation(boolean found){
  randomizedLoading("\033[36mStarting GradebookOS");
  System.out.println();
  System.out.println();
  try{Thread.sleep(500);} catch (Exception e) {}
  randomizedLoading("\033[0mSearching for input files");
  if (found){System.out.println("\r\033[32mInput files detected.                  ");} else {System.out.println("\r\033[31mNo gradebook files detected.                 ");}
  try{Thread.sleep(2000);} catch (Exception e) {}
    System.out.println();
  randomizedLoading("\033[0mLoading Gradebook");
  if(found){System.out.println("\r\033[32mGradebook successfully loaded. ");}else{System.out.println("\r\033[32mNew gradebook successfully created.");}
  try{Thread.sleep(1000);} catch (Exception e) {}
  System.out.print("\033[H\033[2J");
  System.out.println("\033[36m*** Welcome to Gradebook! ***\033[0m");


}
//This method takes a message and appends a loading bar animation whose speed is randomized for realisticness.
public static void randomizedLoading(String message){
  String str = "";
  int c = 0;
  for (int i = 0; i < 11; i++){
    try{Thread.sleep((int)(Math.random() * 1000));} catch (Exception e) {}
      str = "\r" + message + ": [";
      for (int a = 0; a < i; a++){str+="=";c=a;}
    for (int b = 0; b < (9-c); b++){str+="-";}
    str += "]";
   System.out.print(str);
  }
  try{Thread.sleep(1000);} catch (Exception e) {}
}
}
