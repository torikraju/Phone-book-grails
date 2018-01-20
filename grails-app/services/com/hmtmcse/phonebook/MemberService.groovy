package com.hmtmcse.phonebook

import grails.web.servlet.mvc.GrailsParameterMap

class MemberService {

    private static final String AUTHORIZED = "AUTHORIZED"

    def registerMember(GrailsParameterMap params) {
        Member member = new Member(params)
        def response = AppUtil.saveResponse(false, member)
        if (member.validate()) {
            response.isSuccess = true
            member.save()
        }
        return response
    }

    def setMemberAuthorization(Member member) {
        def authorization = [isLoggedIn: true, member: member]
        AppUtil.getAppSession()[AUTHORIZED] = authorization
    }

    def doLogin(String email, String password){
        password = password.encodeAsMD5()
        Member member = Member.findByEmailAndPassword(email, password)
        if (member){
            setMemberAuthorization(member)
            return true
        }
        return false
    }

    boolean isAuthenticated(){
        def authorization = AppUtil.getAppSession()[AUTHORIZED]
        if (authorization && authorization.isLoggedIn){
            return true
        }
        return false
    }

    def getMember(){
        def authorization = AppUtil.getAppSession()[AUTHORIZED]
        return authorization?.member
    }

    def getMemberName(){
        def member = getMember()
        return "${member.firstName} ${member.lastName}"
    }

    def getCurrentMember(){
        return getMember()
    }

    def changePassword(String oldPassword, String newPassword, String retrievePassword){
        Member member = getCurrentMember()
        if (!newPassword || !retrievePassword || !newPassword.equals(retrievePassword)) {
            return AppUtil.infoMessage("Your Entered Password Not Matched.", false)
        } else if (member && !member.password.equals(oldPassword.encodeAsMD5())) {
            return AppUtil.infoMessage("Incorrect Old Password.", false)
        } else {
            member = Member.get(member.id)
            member.password = newPassword
            member.save(flush: true)
            setMemberAuthorization(member)
        }
        return AppUtil.infoMessage("Password Changed")
    }

}
