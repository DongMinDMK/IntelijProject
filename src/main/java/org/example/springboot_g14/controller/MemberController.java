package org.example.springboot_g14.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.springboot_g14.dto.KakaoProfile;
import org.example.springboot_g14.dto.MemberVO;
import org.example.springboot_g14.dto.OAuthToken;
import org.example.springboot_g14.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "member/login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("memberDTO") @Valid MemberVO memberVO, BindingResult bindingResult, Model model, HttpServletRequest request
    ){
        String url="member/login";

        if(bindingResult.getFieldError("userid") != null){
            model.addAttribute("message", bindingResult.getFieldError("userid").getDefaultMessage());
        }else if(bindingResult.getFieldError("pwd") != null){
            model.addAttribute("message", bindingResult.getFieldError("pwd").getDefaultMessage());
        }else{
            MemberVO memberVO1 = memberService.getMember(memberVO.getUserid());

            if(memberVO1 == null){
                model.addAttribute("message", "ID/비밀번호를 확인하세요.");
            }else if(!memberVO1.getPwd().equals(memberVO.getPwd())){
                model.addAttribute("message", "ID/비밀번호를 확인하세요.");
            }else if(memberVO1.getPwd().equals(memberVO.getPwd())){
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", memberVO1);
                url="redirect:/";
            }else{
                model.addAttribute("message", "알수 없는 이유로 로그인을 할 수 없습니다. 관리자에게 문의하세요.");
            }
        }

        return url;
    }

    @GetMapping("/kakaoStart")
    public @ResponseBody String kakaoStart(){
        String a = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize?"
                + "client_id=fc83420a029ccfe52280517bb7c264d0"
                + "&redirect_uri=http://localhost:8070/kakaoLogin"
                + "&response_type=code'"
                + "</script>";
        return a;
    }

    @GetMapping("/kakaoLogin")
    public String login(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code"); // 문 열고 들어왔는데 다음 문으로 들어갈려면 이 토큰 코드가 하나 필요할거야

        System.out.println(code); //토큰 출력

        // 실제 User info 를 요청할 url 과 전달인수 설정

        String endpoint="https://kauth.kakao.com/oauth/token";

        URL url = new URL(endpoint);
        String bodyData="grant_type=authorization_code";
        bodyData += "&client_id=fc83420a029ccfe52280517bb7c264d0";
        bodyData += "&redirect_uri=http://localhost:8070/kakaoLogin";
        bodyData += "&code="+code;

        //Stream 연결
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // import - java.net
        //http header 값 넣기
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setDoOutput(true);

        // 인증절차를 완료하고 User info 요청을 위한 정보를 요청 및 수신합니다.
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bw.write(bodyData);
        bw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String input="";
        StringBuilder sb=new StringBuilder(); // 조각난 String 을 조립하기 위한 객체
        while((input=br.readLine())!=null){
            sb.append(input);
            System.out.println(input);
        }

        // 여기까지가 2번째 관문을 열었을 때 토큰을 줌.
        // 사용자가 실제로 동의한 항목들만 요청하고 받을 수 있도록 권한을 갖고 있는 새로운 토큰으로 구성
        // {"access_token":"4k0uiNirz0nPZFKAoldYkWKN6hv9lqeHAAAAAQo9c5sAAAGQLmcjFirXsvB0zxAC","token_type":"bearer","refresh_token":"PxlcFLK78Jt2fWpKRFy58jukjiwxNPcAAAAAAgo9c5sAAAGQLmcjEyrXsvB0zxAC","expires_in":21599,"scope":"profile_nickname","refresh_token_expires_in":5183999}

        // 수신내용을 GSon 으로 변경(파싱)
        Gson gson = new Gson();

        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
        // OAuthToken <- sb{access_token":"4k0uiNi...}
        // sb 내용을 항목에 맞춰서 OAuthToken 객체에 옮겨 담습니다.


        // 실제 정보 요청 및 수신
        String endpoint2 = "https://kapi.kakao.com/v2/user/me";
        URL url2 = new URL(endpoint2);
        // import java.net.HttpURLConnection
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();

        //header 값 넣기
        conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());

        conn2.setDoOutput(true);

        // UserInfo 수신
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));

        String input2="";

        StringBuilder sb2=new StringBuilder();

        while((input2=br2.readLine())!=null){
            sb2.append(input2);
            System.out.println(input2);
        }

        // 수신내용
        // {"id":3586438558,"connected_at":"2024-06-19T02:50:29Z","properties":{"nickname":"김동민"},"kakao_account":{"profile_nickname_needs_agreement":false,"profile":{"nickname":"김동민","is_default_nickname":false}}}

        Gson gson2 = new Gson();
        KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);
        System.out.println(kakaoProfile.getId());
        KakaoProfile.KakaoAccount ac = kakaoProfile.getAccount();
        System.out.println(ac.getEmail());
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        System.out.println(pf.getNickname());

        MemberVO memberVO = memberService.getMember(kakaoProfile.getId());

        if(memberVO == null){
            memberVO = new MemberVO();
            memberVO.setUserid(kakaoProfile.getId());
            memberVO.setEmail("kakao@gmail.com");
            // memberDTO.setEmail(ac.getEmail());
            memberVO.setName(pf.getNickname());
            memberVO.setProvider("kakao");
            memberVO.setPwd("kakao");
            memberVO.setPhone("");
            memberService.insertMember(memberVO);
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", memberVO);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();

        session.removeAttribute("loginUser");

        return "redirect:/loginForm";
    }

    @GetMapping("/contract")
    public String contract(){
        return "member/contract";
    }

    @PostMapping("/joinForm")
    public String joinForm(){
        return "member/joinForm";
    }

    @GetMapping("/idcheckForm")
    public ModelAndView idcheckForm(@RequestParam("userid") String userid){
        ModelAndView mav = new ModelAndView();

        MemberVO memberVO = memberService.getMember(userid);

        if(memberVO == null){
            mav.addObject("result", -1); //사용가능
        }else{
            mav.addObject("result", 1); //사용불가능
        }
        mav.addObject("userid", userid);
        mav.setViewName("member/idcheck");

        return mav;
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("memberDTO") @Valid MemberVO memberVO, BindingResult bindingResult, @RequestParam("reid") String reid, @RequestParam("pwdCheck") String pwdCheck, Model model){
        String url = "member/joinForm";

        if(bindingResult.getFieldError("userid") != null){
            model.addAttribute("message", bindingResult.getFieldError("userid").getDefaultMessage());
        }else if(bindingResult.getFieldError("pwd") != null){
            model.addAttribute("message", bindingResult.getFieldError("pwd").getDefaultMessage());
        }else if(bindingResult.getFieldError("name") != null){
            model.addAttribute("message", bindingResult.getFieldError("name").getDefaultMessage());
        }else if(bindingResult.getFieldError("phone") != null){
            model.addAttribute("message", bindingResult.getFieldError("phone").getDefaultMessage());
        }else if(bindingResult.getFieldError("email") != null) {
            model.addAttribute("message", bindingResult.getFieldError("email").getDefaultMessage());
        }else if(reid == null || !reid.equals(memberVO.getUserid())){
            model.addAttribute("message", "아이디 중복체크를 확인하세요.");
        }else if(pwdCheck == null || !pwdCheck.equals(memberVO.getPwd())){
            model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다.");
        }else{
            url = "member/login";
            model.addAttribute("message", "회원가입이 정상적으로 완료되었습니다. 로그인하세요.");
            memberService.insertMember(memberVO);
        }

        return url;
    }
}
