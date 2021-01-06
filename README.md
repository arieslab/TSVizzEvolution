<img align="left" width="120" height="100" src="https://github.com/AdrianaPriscilaSantos/TSVizzEvolution/blob/master/TSVizzEvolution/src/tsvizzevolution/logo_tsvizz.png">

# TSVizzEvolution: A Tool for Visualization the Evolution of the Test Smells 

## Description
TSVizzEvolution is a tool for visualizing the test smells and their evolution on software projects. 
It implements tree visualization techniques: (i) **Graph View**, draws the associations between the test smells and the software artifacts (e.g., projectand test classes) and authors; (ii) **Timeline View**, presents the test smells evolution over time; and (iii) **Treemap View**, instantly present the test smells of the project as a whole.
TSVizzEvolution integrates a conceptual framework under development by an inter-institutional researchers’ team, which consists of test smells prevention, identification, refactoring, and visualization to improve the test codequality. 


TSVizzEvolution has two types of analysis (Single Analysis and Evolution Analysis).
  
**Single Analysis.** The Graph View and Treemap View is the visualization technique applied to single analysis. First, we select the software version. There are no granularities for the Treemap View. For Graph View all types of granularity analysis. TSVizzEvolution supports views with different granularity: Project, All Test Classes, A Specific Test Class, A Specific Test Smells e Author. 
  - **Project.** It shows the relationship between the whole project and the types of test smells;
  - **All Test Classes.** It shows the relationship between test classes and types of test smells;
  - **A Specific Test Class.** It shows the relationship between one test classes and types of test smells;
  - **A Specific Test Smell.** It shows the relationship between one types of test smells and test classes;
  - **Authors.** It shows the relationship between authors and types of test smells, and the relationship between authors and test classes. It calculates the *authorship by guilty*, i.e. if a developer either inserts a new test smell or modifies a smelly method without fixing it. The developer “was aware” of the problem and assumed its co-authorship.
  - **Methods.** Displays the relationship between a specific test smells, a specific test class and the methods.

**Evolution Analysis.** Both Graph View and Timeline View are the visualization techniques applied to the evolution analysis. 
  - The Graph View technique supports all types of granularity to compare two version of the project. A suffix represents the order of the insertion of the versions: '_1' for the oldest version, and '_2' for the most recent one.
  - The Timeline View technique only has two granularity available (**Project**, **All Test Classes** and **Methods**). 
  
## Tool Execution

To run the TSVizzEvolution tool, simply download the available jar (TSVizzEvolution.jar) and does not run any command prompt: java -jar TSVizzEvolution.jar

Some files generated for the Commons IO system's JNose tool are available at files_cvs_jnose.

## Contact Email:
adrianapriscila06@gmail.com
