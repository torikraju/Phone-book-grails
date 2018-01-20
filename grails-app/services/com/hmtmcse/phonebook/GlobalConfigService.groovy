package com.hmtmcse.phonebook


class GlobalConfigService {

    MemberService memberService
    ContactGroupService contactGroupService

    public static boolean isBuilding = false;
    public static boolean isGeneratingSchema = false;

    def itemsPerPage() {
        return 5
    }

    public static String getUID(){
        return UUID.randomUUID().toString().toUpperCase();
    }

}
