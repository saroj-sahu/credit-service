package com.cr.controller;

import com.cr.entity.Authority;
import com.cr.entity.Rank;
import com.cr.entity.User;
import com.cr.model.EmailDetails;
import com.cr.repository.AuthorityRepository;
import com.cr.repository.RankRepository;
import com.cr.repository.UserDetailsRepository;
import com.cr.request.PasswordResetRequest;
import com.cr.request.UserProfileRequest;
import com.cr.response.UserProfileResponse;
import com.cr.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Tag(description = "UserInfo api's that provides access to availabe user information",
        name = "UserInfo API")
@RequestMapping("/api")
@RestController
@CrossOrigin
public class UserInfoController {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${service.host}")
    String serverName;

    ClassPathResource imageFile = new ClassPathResource("img/img2.png");

    @PostMapping(path = "/update")
    public ResponseEntity update(@RequestBody UserProfileRequest request){
        Optional<User> user = userDetailsRepository.findByUserName(request.getId());
        if(user.isPresent()){
            User value = user.get();
            value.setFirstName(request.getFirstName());
            value.setLastName(request.getLastName());
            value.setEmail(request.getEmailId());
            value.setPhoneNumber(request.getPhoneNumber());
            value.setAddress1(request.getAddress1());
            value.setAddress2(request.getAddress2());
            value.setCity(request.getCity());
            value.setState(request.getState());
            value.setZipCode(request.getZipCode());
            value.setMilitaryRank(request.getRank());
            value.setMos(request.getMos());
            value.setYearsOfService(request.getYearsOfService());
            value.setBranch(request.getBranch());
            userDetailsRepository.saveAndFlush(value);
        }else{
            User value = new User();
            value.setFirstName(request.getFirstName());
            value.setLastName(request.getLastName());
            value.setEmail(request.getEmailId());
            value.setPhoneNumber(request.getPhoneNumber());
            value.setAddress1(request.getAddress1());
            value.setAddress2(request.getAddress2());
            value.setCity(request.getCity());
            value.setState(request.getState());
            value.setZipCode(request.getZipCode());
            value.setMilitaryRank(request.getRank());
            value.setMos(request.getMos());
            value.setYearsOfService(request.getYearsOfService());
            value.setBranch(request.getBranch());
            Authority authority = new Authority();
            authority.setRoleCode("USER");
            authority.setRoleDescription("USER");
            value.setAuthorities(Arrays.asList(authority));
            userDetailsRepository.saveAndFlush(value);
        }
        return ResponseEntity.ok("Updated");
    }

    @PostMapping(path = "/signup")
    public ResponseEntity signUp(@RequestBody UserProfileRequest request) throws IOException {
            User value = new User();
            value.setFirstName(request.getFirstName());
            value.setLastName(request.getLastName());
            value.setEmail(request.getEmailId());
            value.setUserName(request.getEmailId());
            value.setPhoneNumber(request.getPhoneNumber());
            value.setAddress1(request.getAddress1());
            value.setAddress2(request.getAddress2());
            value.setCity(request.getCity());
            value.setState(request.getState());
            value.setZipCode(request.getZipCode());
            value.setMilitaryRank(request.getRank());
            value.setMos(request.getMos());
            value.setYearsOfService(request.getYearsOfService());
            value.setBranch(request.getBranch());
            List<Authority> authorities = authorityRepository.findAllByRoleCode("USER");
            value.setAuthorities(authorities);
            userDetailsRepository.saveAndFlush(value);
            sendPasswordResetMail(value);
        return ResponseEntity.ok("Success");
    }

    private void sendPasswordResetMail(User user){
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("CREDIT - Password Reset");
        emailDetails.setRecipient(user.getUserName());
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getFirstName());
        String link = serverName+"/Passwordreset?userName="+user.getUserName();
        properties.put("resetLink", link);
        properties.put("sign", "Credit Team");
        emailDetails.setProperties(properties);
        Context context = new Context();
        context.setVariables(emailDetails.getProperties());
        String html = templateEngine.process("password_reset", context);
        emailDetails.setSubject("CREDIT - Password Reset");
        emailDetails.setAttachment("img/logo.png");
        emailDetails.setMsgBody(html);
        emailService.sendMailWithAttachment(emailDetails);
    }

    @GetMapping(path="/userInfo")
    public ResponseEntity<UserProfileResponse> getUserProfile(@RequestParam (name = "userName") String userName) throws IOException {
        Optional<User> user = userDetailsRepository.findByUserName(userName);

        UserProfileResponse response = new UserProfileResponse();
        if(user.isPresent()){

            response.setFirstName(user.get().getFirstName());
            response.setLastName(user.get().getLastName());
            response.setEmailId(user.get().getEmail());
            response.setPhoneNumber(user.get().getPhoneNumber());
            response.setAddress1(user.get().getAddress1());
            response.setAddress2(user.get().getAddress2());
            response.setBranch(user.get().getBranch());
            response.setCity(user.get().getCity());
            response.setState(user.get().getState());
            response.setMos(user.get().getMos());
            response.setRank(user.get().getMilitaryRank());
            /*if(user.get().getMilitaryRank()!= null && !user.get().getMilitaryRank().isEmpty()){
                Rank rank = rankRepository.findByRankName(user.get().getMilitaryRank());
                response.setRankIcon();
            }*/
            byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
            response.setRankIcon(imageBytes);

        }
        return ResponseEntity.ok(response);
    }


    @PostMapping(path="/passwordReset")
    public ResponseEntity resetPassword(@RequestBody PasswordResetRequest request){
        Optional<User> user = userDetailsRepository.findByUserName(request.getUserName());
        if(user.isPresent()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(request.getCurrentPassword()!=null){
                if(!encoder.matches(user.get().getPassword(), encoder.encode(request.getCurrentPassword()))){
                    return new ResponseEntity("Current Password did not match", HttpStatus.PRECONDITION_FAILED);
                }
            }
            String encodedPass = encoder.encode(request.getPassword());
            user.get().setPassword(encodedPass);
        }else{
            return new ResponseEntity("User Not Found", HttpStatus.PRECONDITION_FAILED);
        }
        userDetailsRepository.saveAndFlush(user.get());
        return ResponseEntity.ok("Success");
    }

    @GetMapping(path="/forgotPassword")
    public ResponseEntity forgotPassword(@RequestParam(name="username") String userName) throws IOException {
        Optional<User> user = userDetailsRepository.findByUserName(userName);
        if(user.isPresent()) {
            sendPasswordResetMail(user.get());
        }else{
            return new ResponseEntity("User Not Found", HttpStatus.PRECONDITION_FAILED);
        }
        return ResponseEntity.ok("Success");
    }

    public static void main(String args[]){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode("admin");
        System.out.println(pass);
    }
}
