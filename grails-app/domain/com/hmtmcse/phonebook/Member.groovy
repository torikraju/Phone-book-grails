package com.hmtmcse.phonebook

class Member {
    Integer id
    String firstName
    String lastName
    String email
    String password
    String identityHash

    Date dateCreated
    Date lastUpdated

    static constraints = {
        email(email: true, nullable: false, unique: true, blank: false)
        password(blank: false)
        firstName(blank: false)
        lastName(nullable: true, blank: true)
        identityHash(nullable: true, blank: true)
    }

    static mapping = {
        version(false)
    }

    static hasMany = [contact: Contact]


    def beforeInsert = {
        this.password = this.password.encodeAsMD5()
    }


    def beforeUpdate = {
        this.password = this.password.encodeAsMD5()
    }



}
