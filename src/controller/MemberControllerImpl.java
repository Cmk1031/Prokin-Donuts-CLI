package controller;

import common.member.MemberText;
import common.util.InputUtil;
import common.util.MenuUtil;
import dto.memberDTO.MemberDTO;
import service.MemberService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MemberControllerImpl implements MemberController {
    Scanner scanner = new Scanner(System.in);

    MemberService memberService;
    public MemberControllerImpl(MemberService memberService) {
        this.memberService = memberService;
    }
    Map<Integer,Runnable> mainMenu = new HashMap<>();
    Map<Integer,Runnable> HQMenu = new HashMap<>();
    Map<Integer,Runnable> HQSearchMenu = new HashMap<>();
    Map<Integer,Runnable> HQAddMenu = new HashMap<>();
    Map<Integer,Runnable> WMMenu = new HashMap<>();
    Map<Integer,Runnable> WMSearchMenu = new HashMap<>();
    Map<Integer,Runnable> FMMenu = new HashMap<>();
    Map<Integer,Runnable> FMSearchMenu = new HashMap<>();


    public Map<Integer,Runnable>  setMainMenu(){
        mainMenu.put(1,()-> HQMenu());
        mainMenu.put(2,()-> WMMenu());
        mainMenu.put(3,()-> FMMenu());
        return mainMenu;
    }

    public Map<Integer,Runnable> setHQMenu(){
        HQAddMenu = setHQAddMenu();
        HQSearchMenu = setHQSearchMenu();
        HQMenu.put(1,()-> MenuUtil.handleMenuSelection(MemberText.HQ_MEMBER_ADD_MENU.getText(),HQAddMenu) );
        HQMenu.put(2,()-> updateMenu());
        HQMenu.put(3,()-> deleteMenu());
        HQMenu.put(4,()-> MenuUtil.handleMenuSelection(MemberText.HQ_MEMBER_SEARCH_MENU.getText(),HQSearchMenu) );
        return HQMenu;
    }
    public Map<Integer,Runnable> setHQAddMenu(){
        HQSearchMenu.put(1,()-> addMenu());
        HQSearchMenu.put(2,()-> approve());
        return HQSearchMenu;
    }

    public Map<Integer,Runnable>  setHQSearchMenu(){
        HQSearchMenu.put(1,()-> searchSimpleMenu());
        HQSearchMenu.put(2,()-> searchDitailMenu());
        HQSearchMenu.put(3,()-> searchAuthorityMenu());
        HQSearchMenu.put(4,()-> searchALLMenu());
        return HQSearchMenu;
    }

    public Map<Integer,Runnable> setWMMenu(){
        WMSearchMenu =setWMSearchMenu();
        WMSearchMenu = setWMSearchMenu();
        WMMenu.put(1,()-> MenuUtil.handleMenuSelection(MemberText.WM_MEMBER_SEARCH_MENU.getText(),WMSearchMenu));
        WMMenu.put(2,()-> updateMenu());
        return WMMenu;
    }

    public Map<Integer,Runnable> setWMSearchMenu(){
        WMSearchMenu.put(1,()->searchSimpleMenu());
        WMSearchMenu.put(2,()->searchDitailMenu());
        WMSearchMenu.put(3,()->searchAuthorityMenu());
        return WMSearchMenu;
    }

    public Map<Integer,Runnable> setFMMenu(){
        FMSearchMenu = setFMSearchMenu();
        FMMenu.put(1,()->MenuUtil.handleMenuSelection(MemberText.FM_MEMBER_ADD_MENU.getText(),FMSearchMenu));
        FMMenu.put(2,()->updateMenu());
        FMMenu.put(3,()->deleteMenu());
        return FMMenu;
    }

    public Map<Integer,Runnable> setFMSearchMenu(){
        FMMenu.put(1,()->searchSimpleMenu());
        FMMenu.put(2,()->searchDitailMenu());
        return FMMenu;
    }

    public void MainMune(int authorityId){
        MemberText.MENU_HEADER.getText();
        mainMenu = setMainMenu();
        Runnable action = mainMenu.get(authorityId);
        action.run();
    }


    public void HQMenu(){
        HQMenu =  setHQMenu();
        MenuUtil.handleMenuSelection(MemberText.HQ_MEMBER_MENU.getText(),HQMenu);
    }

    public void WMMenu(){
        WMMenu = setWMMenu();
        MenuUtil.handleMenuSelection(MemberText.WM_MEMBER_MENU.getText(),WMMenu);
    }

    public void FMMenu(){
        FMMenu = setFMMenu();
        MenuUtil.handleMenuSelection(MemberText.FM_MEMBER_MENU.getText(),FMMenu);
    }


    public void addMenu(){
        MemberText.INSERT_MEMBER_NEW_HEADER.getText();
        MemberDTO result = memberService.addMember(newMember());
        System.out.println(result.getId());
        System.out.println(MemberText.INSERT_MEMBER_SUCCESS);
    }

    public void approve(){
        MemberText.INSERT_MEMBER_APPROVE_HEADER.getText();
        memberService.searchRequestMember().forEach(System.out::println);
        String result = memberService.approvalMember(InputUtil.getInput
                (MemberText.INSERT_MEMBER.getText()+
                        MemberText.MEMBER_ID.getText()).get());
        System.out.println(result);
        System.out.println(MemberText.INSERT_MEMBER_SUCCESS);
    }



    public void deleteMenu(){
        MemberText.DELETE_MEMBER_HEADER.getText();
        String result = memberService.deleteMember(InputUtil.getInput(MemberText.DELETE_MEMBER.getText()+MemberText.MEMBER_ID.getText()).get());
        System.out.println(result);
        System.out.println(MemberText.DELETE_MEMBER_SUCCESS);
    }

    public void updateMenu(){
        MemberText.UPDATE_MEMBER_HEADER.getText();
        MemberDTO result = memberService.updateMember(updateMember());
        System.out.println(result.getId());
        System.out.println(MemberText.UPDATE_MEMBER_SUCCESS);
    }

    public void searchSimpleMenu(){
        MemberText.SEARCH_MEMBER_SIMPLE_HEADER.getText();
        MemberDTO result = memberService.searchSimple(InputUtil.getInput(MemberText.SEARCH_MEMBER_ID.getText()).get());
        System.out.println(result.getName()+" "+result.getId()+" "+result.getEmail());
    }
    public void searchDitailMenu(){
        MemberText.SEARCH_MEMBER_DETAIL_HEADER.getText();
        MemberDTO result = memberService.searchDitail(InputUtil.getInput(MemberText.SEARCH_MEMBER_ID.getText()).get());
        System.out.println(result);
    }
    public void searchAuthorityMenu(){
        MemberText.SEARCH_MEMBER_AUTHORITY_HEADER.getText();
        List<MemberDTO> result = memberService.searchAuthority(InputUtil.getInput(MemberText.SEARCH_MEMBER_AUTHORITY.getText()).get());
        result.forEach(System.out::println);
    }
    public void searchALLMenu(){
        MemberText.SEARCH_MEMBER_ALL_HEADER.getText();
        List<MemberDTO> result = memberService.searchAll();
        result.forEach(System.out::println);
    }


    public MemberDTO newMember(){
        MemberDTO newmember = new MemberDTO();
        newmember.setName(InputUtil.getInput(MemberText.INSERT_MEMBER.getText()+MemberText.MEMBER_NAME.getText()).get());
        newmember.setAuthorityId(InputUtil.getMenuSelection(MemberText.INSERT_MEMBER.getText()+MemberText.MEMBER_AUTHORITYID.getText()).get());
        newmember.setPhoneNumber(InputUtil.getInput(MemberText.INSERT_MEMBER.getText()+MemberText.MEMBER_PHONE.getText()).get());
        newmember.setEmail(InputUtil.getInput(MemberText.INSERT_MEMBER.getText()+MemberText.MEMBER_EMAIL.getText()).get());
        newmember.setAddress(InputUtil.getInput(MemberText.INSERT_MEMBER.getText()+MemberText.MEMBER_ADDRESS.getText()).get());
        newmember.setId(InputUtil.getInput(MemberText.INSERT_MEMBER.getText()+MemberText.MEMBER_ID.getText()).get());
        newmember.setPassword(InputUtil.getInput(MemberText.INSERT_MEMBER.getText()+MemberText.MEMBER_PASSWORD.getText()).get());
        return newmember;
    }

    public MemberDTO updateMember(){
        MemberDTO updateMember = new MemberDTO();
        updateMember.setName(InputUtil.getInput(MemberText.UPDATE_MEMBER.getText()+MemberText.MEMBER_NAME.getText()).get());
        updateMember.setAuthorityId(InputUtil.getMenuSelection(MemberText.UPDATE_MEMBER.getText()+MemberText.MEMBER_AUTHORITYID.getText()).get());
        updateMember.setPhoneNumber(InputUtil.getInput(MemberText.UPDATE_MEMBER.getText()+MemberText.MEMBER_PHONE.getText()).get());
        updateMember.setEmail(InputUtil.getInput(MemberText.UPDATE_MEMBER.getText()+MemberText.MEMBER_EMAIL.getText()).get());
        updateMember.setAddress(InputUtil.getInput(MemberText.UPDATE_MEMBER.getText()+MemberText.MEMBER_ADDRESS.getText()).get());
        updateMember.setId(InputUtil.getInput(MemberText.UPDATE_MEMBER.getText()+MemberText.MEMBER_ID.getText()).get());
        updateMember.setPassword(InputUtil.getInput(MemberText.UPDATE_MEMBER.getText()+MemberText.MEMBER_PASSWORD.getText()).get());
        return updateMember;
    }

}









