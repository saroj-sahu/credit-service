package com.cr.controller;

import com.cr.entity.Authority;
import com.cr.entity.Rank;
import com.cr.entity.User;
import com.cr.model.EmailDetails;
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
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Tag(description = "UserInfo api's that provides access to availabe user information",
        name = "UserInfo API")
@RequestMapping("/api")
@RestController
@CrossOrigin
public class UserInfoController {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    EmailService emailService;

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
    public ResponseEntity signUp(@RequestBody UserProfileRequest request){
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
            userDetailsRepository.saveAndFlush(value);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setSubject("CREDIT - Password Reset");
            emailDetails.setRecipient(request.getEmailId());
            emailDetails.setMsgBody(serverName+"/Passwordreset?userName="+request.getEmailId());
            emailService.sendSimpleMail(emailDetails);
        return ResponseEntity.ok("Success");
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
            user.get().setPassword(request.getPassword());
        }
        userDetailsRepository.saveAndFlush(user.get());
        return ResponseEntity.ok("Success");
    }

    @GetMapping(path="/forgotPassword")
    public ResponseEntity forgotPassword(@RequestParam(name="username") String userName){
        Optional<User> user = userDetailsRepository.findByUserName(userName);
        if(user.isPresent()) {
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setSubject("CREDIT - Password Reset");
            emailDetails.setRecipient(user.get().getUserName());
            emailDetails.setMsgBody(serverName+"/Passwordreset?userName="+user.get().getUserName());
            emailService.sendSimpleMail(emailDetails);
        }
        return ResponseEntity.ok("Success");
    }
}
