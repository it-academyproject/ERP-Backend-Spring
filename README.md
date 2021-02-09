# ITProject-ERP-Backend

For this project, whe have a given repo named ITProject-ERP-Frontend,
with a GitHub remote URL: https://github.com/it-academyproject/ITProject-ERP-Backend.git

This documentation will guide you through this Spring project installation.

NOTE: in this documentation...

    We will use <localProjectFolderName> as a generic alias for the local folder's name
    We will use <repoURL> as a generic alias for the remote repo's URL
    We will use <featureBranchName> as a generic alias for the branches generated from 'Dev'
    We will use <yourGitHubName> and <yourGitHubEmail> as alias for your user name and email registered in your GitHub account
    We will use <ScrumTask_ID> as alias for your tasks's id as stated in your SCRUM table.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

It's recommended to have an editor installed that supports Spring and has an application server incorporated. 
Otherwise, you can manually install the application server and follow the instructions for running the program locally. 

#### GIT and GITHUB
Skip this section's steps if you already have GIT  installed your local machine, and a valid GitHub account.
If not, follow next steps:

> 1. Download and install git CLI if you haven't yet: https://git-scm.com/
 
> 2. Create a GitHub account if you haven't yet: https://github.com/
>    Then check for its version, to make sure the installation went fine.
>    To do so, type this in your terminal or console:

	git--version

> 3.  Then, set up your name and email for local **git CLI**.  
>     Type the following commands in your terminal or console:

    git config –-global user.name <yourGitHubName>
    git config –-global user.email <yourGitHubEmail>

> 4.  Name and email must match those you registered in **GitHub** account.  
>     Then, check result. Type in:

    git config –-global user.name
    git config –-global user.email

#### Dependencies for Spring.

It is recommended to consult the pom.xml file of the DEV branch of the project. This file includes all the current requirements to run the application at this time.
Summarizing:
- Validations: groupId: javax.validation. Artifact: validation-api
- JPA.
- Spring Security
- Starter Web. (to run the application server included in the IDE)
- modelMapper. For the DAO-DTO mapping.
- Jsonwebtoken. To work with tokens in authentication.
- security-crypto and security core.
- commons.lang3 from apache.
- the DevTools.
- Mysql Connector.
- hibernate validations. 


### Installing

A step by step to Clone the repo from remote to local:

> 1.  Clone the repo from remote to local:

    git clone <repoURL>

> 2.  Yo can open projects from file system in your IDE and open from local clone repo directory. 


## WORKING WITH BRANCHES

This project has 2 branches (main and Dev), which should NOT be manipulated by any developer.
That role is restricted to project managers, who will accept or reject developer's pull requests.
Use the following semantics for naming your branches: <ScrumTask_ID> or optionally <ScrumTask_ID-featureBranchName>.
For example: B1 or B1.4_UserController

And don't forget to create your feature branches from Dev.
To do so:
>1. Pull actual Dev branch state:
	
	git pull origin Dev
	
>2. Create your feature branch:

	git checkout -b <featureBranchName>

>3. Work on your branch. Do your add-to-stage and commits

>4. Pull Dev branch state again:
	
	git pull origin Dev
	
>5. Push your branch to the repo:

	git push origin <featureBranchName>
	
>6. Finally, do a pull request in GitHub, from your branch to Dev

## CONFIGURE DATABASE

You must configure the database in the application.properties file.
First, create database in your Mysql Server.

Configuration for a database in MySQL.


>1. Connect to database

	spring.datasource.url=jdbc:mysql://localhost:3306/proyectoERP?useSSL=false
	spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver

>2. Database credentials

	spring.datasource.username=root
	spring.datasource.password=root
  
Configure JPA and Hibernate

>3. Hibernate dialect for Mysql, logs and ddl configuration.

	spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.show-sql = true
	logging.level.org.hibernate.SQL=debug


  