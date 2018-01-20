package com.hmtmcse.phonebook.controllers

import com.hmtmcse.phonebook.PhoneBookService
import grails.converters.JSON

class ContactNumberController {

    PhoneBookService phoneBookService

    def number() {
        [numbers: phoneBookService.getContactNumbersByContactId(params.id)]
    }

    def delete(Integer id){
        render(phoneBookService.deleteContactNumber(id) as JSON)
    }
}
