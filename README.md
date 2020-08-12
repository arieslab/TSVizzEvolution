# TSVizzEvolution: </br> A Tool for Visualization the Evolution of the Test Smells ![TZVizz](https://logo_tsvizz.png "TSVizz")

## Description
TSVizzEvolution is a tool for visualizing the test smells and their evolution on software projects. 
It implements two visualization techniques: (i) **Graph View**, draws the associations between the test smells and the software artifacts (e.g., projectand test classes) and authors; and (ii) **Timeline View**, presents the test smells evolution over time.
TSVizzEvolution integrates a conceptual framework under development by an inter-institutional researchers’ team, which consists of test smells prevention, identification, refactoring, and visualization to improve the test codequality. 


TSVizzEvolution has two types of analysis (Single Analysis and Evolution Analysis).
  
**Single Analysis.** The Graph View is the visualization technique applied to single analysis. Therefore, we select version of the software
project and all types of granularity analysis. TSVizzEvolution supports views with different granularity: Project, All Test Classes, A Specific Test Class, A Specific Test Smells e Author. 
  - **Project.** It shows the relationship between the whole project and the types of test smells;
  - **All Test Classes.** It shows the relationship between test classes and types of test smells;
  - **A Specific Test Class.** It shows the relationship between test classes and types of test smells;
  - **A Specific Test Smell.** It shows the relationship between test classes and types of test smells;
  - **Authorship.** It shows the relationship between authors and types of test smells, and the relationship between authors and test classes. It calculates the *authorship by guilty*, i.e. if a developer either inserts a new test smell or modifies a smelly method without fixing it. The developer “was aware” of the problem and assumed its co-authorship.

**Evolution Analysis.** Both Graph View and Timeline View are the visualization techniques applied to the evolution analysis. 
  - The Graph View technique supports all types of granularity to compare two version of the project. A suffix represents the order of the insertion of the versions: '_1' for the oldest version, and '_2' for the most recent one.
  - The Timeline View technique only has two granularity available (Project and All Test Classes). 
  
## Tool Execution

To run the TSVizzEvolution tool, simply download the available jar: TSVizzEvolution.jar

Some files generated for the Commons IO system's JNose tool are available at files_cvs_jnose.

## Contact Email:
adrianapriscila06@gmail.com
