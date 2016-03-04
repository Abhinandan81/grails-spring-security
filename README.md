## Grail's Spring Security Core Plugin - Implementation  ##
**reference link :**  		

 - 	http://grails-plugins.github.io/grails-spring-security-core/v2/index.html
 - http://grails-plugins.github.io/grails-spring-security-core/v2/guide/single.html#tutorials
 
**Version specification :** Grail's  : 2.4.4 and Core Plugin :2.0.0

----------

**Step in implementation :**
----------------


----------


***Step 1 : configuration changes***

> **prerequisites :**
*Create a new grails application :*   grails create-app springSecurity
*create a new database in mysql as :*   create database springSecurity;
#### <i class="icon-pencil"></i> **Modifying grails application configuration** :

    
*dataSource configuration for mysql*
Configuring Datasource : DataSource.groovy

    dataSource {
       dbCreate = "create"
       url = "jdbc:mysql://localhost/springSecurity"
       driverClassName = "com.mysql.jdbc.Driver"
       username = "root"
       password = ""
    }    
*Add core plugin  i.e. "spring-security-core:2.0.0"  *  to BuildConfig.groovy
    
		plugins {
	------------other plugins------------------------
	   compile ":spring-security-core:2.0.0"
	------------other plugins------------------------
	}


----------


***Step 2: compiling grail's application***
Now compile the grail application using ***grails compile*** so that, it will download the plugin dependencies.


----------
***Step 3 : Domain class creation for spring security***
After the successful  addition of plugin run below command from application directory path(command line)

    grails s2-quickstart com.auth User Role
*here :  com.auth is a  package name for User and Role Domain classes*

**It will create three Domain classes for our application naming :**

 - Role
 - User
 - UserRole

It will also add below things at the end of  ***config.groovy*** file  :

    // Added by the Spring Security Core plugin:
    grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.auth.User'
    grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.auth.UserRole'
    grails.plugin.springsecurity.authority.className = 'com.auth.Role'
    grails.plugin.springsecurity.controllerAnnotations.staticRules = [
      '/':                ['permitAll'],
      '/index':           ['permitAll'],
      '/index.gsp':       ['permitAll'],
      '/assets/**':       ['permitAll'],
      '/**/js/**':        ['permitAll'],
      '/**/css/**':       ['permitAll'],
      '/**/images/**':    ['permitAll'],
      '/**/favicon.ico':  ['permitAll']
    ]
    


----------
***Step 4: Add user and role and association between them ***
*Before testing our applications security, we need to add user, role  and role to the user.*

Here we are going to add this entries at the time of application Bootstrapping.
 ***Edit Bootstrap class***

    // adding new Users
    User admin = new User(username:'admin', password:'secret', enabled:true).save(flush: true, failOnError: true)
    // adding new Roles
    Role adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
    // Assigning existing role to existing user
    UserRole userRole   =   new UserRole(secUser: admin, secRole: adminRole).save(flush: true, failOnError: true)


----------
***Step 5 : Adding Secured Annotation ***
*Reference:*
http://grails-plugins.github.io/grails-spring-security-core/v2/guide/requestMappings.html#securedAnnotations

*How to add annotations to controller action :*

Suppose we have a controller “***User***”, and it has a action  “**userRegistration**” then *adding annotations* to it, will looks like: 

    @Secured(['permitAll'])
    def userRegistration(){
    	-----------
    }

*@Secured(['permitAll'])* -  means anyone can access this action.No need for an Authentication

Suppose if we have other roles added as “ROLE_ADMIN” AND “ROLE_USER” ,then we can add annotations to a controller action for multiple roles as :

    @Secured([‘ROLE_ADMIN’,’ROLE_USER’])
    	def fetchExistingUserDetails(){
    	----------------
    	}

*@Secured([‘ROLE_ADMIN’,’ROLE_USER’]) -* means Only Users with role *“ROLE_ADMIN”* AND *“ROLE_USER”* can access this action.

***Now run the application as and try accessing  above actions:***
You can access userRegistration action without any difficulty even if you are not a user of the system  but  to access  fetchExistingUserDetails action you have to be user of the system and you should  have a role either   a“ROLE_ADMIN” or “ROLE_USER” . 

***What are the things this plugin will take care of :***

 - User Authentication
 -  User Authorization 
 - Preventing duplicate user   registration

*That’s it … we have achieved  security for our grail's application successfully.*

