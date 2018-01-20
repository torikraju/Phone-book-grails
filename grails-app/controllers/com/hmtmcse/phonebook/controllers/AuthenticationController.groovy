package com.hmtmcse.phonebook.controllers

import com.hmtmcse.phonebook.AppUtil
import com.hmtmcse.phonebook.MemberService

class AuthenticationController {

    MemberService memberService

    def index() {
        redirect(controller: "dashboard", action: "index")
    }


    def login() {
        if (memberService.isAuthenticated()) {
            redirect(controller: "dashboard", action: "index")
        }
    }


    def doChangePassword() {
        if (memberService.isAuthenticated()) {
            def response = memberService.changePassword(params.password, params.newPassword, params.renewPassword)
            flash.message = response
            if (response.success){
                redirect(controller: "dashboard", action: "index")
            }else{
                redirect(controller: "authentication", action: "changePassword")
            }
        } else {
            redirect(controller: "authentication", action: "login")
        }
    }

    def changePassword() {
        if (!memberService.isAuthenticated()) {
            redirect(controller: "dashboard", action: "index")
        }
    }


    def forgotPassword() {
        if (memberService.isAuthenticated()) {
            redirect(controller: "dashboard", action: "index")
        }
    }


    def logout() {
        session.invalidate()
        redirect(controller: "authentication", action: "login")
    }

    def registration() {
        [member: flash.redirectParams]
    }


    def doLogin() {
        if (memberService.doLogin(params.email, params.password)) {
            redirect(controller: "dashboard", action: "index")
        } else {
            redirect(controller: "authentication", action: "login")
        }
    }

    def doRegistration() {
        def response = memberService.registerMember(params)
        if (response.isSuccess) {
            memberService.setMemberAuthorization(response.model)
            redirect(controller: "dashboard", action: "index")
        } else {
            flash.redirectParams = response.model
            redirect(controller: "authentication", action: "registration")
        }
    }
}
