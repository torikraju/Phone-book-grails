package com.hmtmcse.phonebook

import grails.web.servlet.mvc.GrailsParameterMap


class ContactService {

    GlobalConfigService globalConfigService
    MemberService memberService
    PhoneBookService phoneBookService

    def save(GrailsParameterMap params) {
        Contact contact = new Contact(params)
        contact.member = memberService.getCurrentMember()
        def response = AppUtil.saveResponse(false, contact)
        if (contact.validate()) {
            response.isSuccess = true
            contact.save(flush: true)
            if (params.number) {
                phoneBookService.saveContactNumber(contact, params.type, params.number)
            }
        }
        return response
    }

    def update(Contact contact, GrailsParameterMap params) {
        contact.properties = params
        def response = AppUtil.saveResponse(false, contact)
        if (contact.validate()) {
            response.isSuccess = true
            contact.save(flush: true)
            if (params.number) {
                phoneBookService.updateContactNumber(contact, params.type, params.number, params.numberId)
            }
        }
        return response
    }

    def get(Serializable id) {
        return Contact.get(id)
    }

    def list(GrailsParameterMap params) {
        params.max = params.max ?: globalConfigService.itemsPerPage()
        List<Contact> contactList = Contact.createCriteria().list(params) {
            if (params?.colName && params?.colValue) {
                like(params.colName, "%" + params.colValue + "%")
            }
            if (!params.sort) {
                order("id", "desc")
            }
            eq("member", memberService.getCurrentMember())
        }
        return [list: contactList, count: Contact.count()]
    }

    def delete(Contact contact) {
        try {
            contact.delete(flush: true)
        } catch (Exception e) {
            println(e.getMessage())
            return false
        }
        return true
    }

}
