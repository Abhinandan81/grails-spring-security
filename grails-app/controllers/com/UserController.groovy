package com

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class UserController {
    def userManagementService

    //Anyone can access this action as it's permitted for the all
    @Secured(['permitAll'])
    def userRegistration(){
        println "New Registration process started -- > "
        //for testing purpose I have added new User from here . You can accept user details using params as well.
        Boolean registrationStatus  =   userManagementService.registerUser("Dipak", "Dipak")

        render registrationStatus
    }

    //Only user with the role ROLE_ADMIN can access it
    @Secured(['ROLE_ADMIN'])
    def fetchExistingUserDetails(){
        println "fetching existing user details:"
        def userDetails =   userManagementService.fetchUserDetails()
        render userDetails as JSON
    }
}
