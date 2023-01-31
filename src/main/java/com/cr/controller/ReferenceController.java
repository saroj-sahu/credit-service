package com.cr.controller;

import com.cr.entity.Branch;
import com.cr.entity.City;
import com.cr.entity.Rank;
import com.cr.entity.State;
import com.cr.repository.BranchRepository;
import com.cr.repository.CityRepository;
import com.cr.repository.RankRepository;
import com.cr.repository.StateRepository;
import com.cr.response.BranchResponse;
import com.cr.response.CityResponse;
import com.cr.response.RankResponse;
import com.cr.response.StateResponse;
import com.cr.util.ExcelHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Tag(description = "Reference api's that provides access to availabe reference data",
        name = "Reference API")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ReferenceController {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private RankRepository rankRepository;

    @PostMapping("/uploadStates")
    public ResponseEntity uploadStateFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                try {
                    List<State> states = ExcelHelper.excelToStates(file.getInputStream());
                    stateRepository.saveAll(states);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store excel data: " + e.getMessage());
                }

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return (ResponseEntity) ResponseEntity.ok();
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return (ResponseEntity) ResponseEntity.ok();
            }
        }

        message = "Please upload an excel file!";
        return (ResponseEntity) ResponseEntity.badRequest();
    }

    @Operation(summary = "Get states",
            description = "Provides all available state list in US")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400",
                    description = "${api.response-codes.badRequest.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404",
                    description = "${api.response-codes.notFound.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }) })
    @GetMapping(path = "/states")
    public ResponseEntity<List<StateResponse>> getStates(){
        List<State> states = stateRepository.findAll();
        List<StateResponse> stateResponses = new ArrayList<>();
        states.stream().forEach(state -> {
            StateResponse response = new StateResponse();
            response.setStateId(state.getId());
            response.setStateName(state.getStateName());
            stateResponses.add(response);
        });
        return ResponseEntity.ok(stateResponses);
    }

    @PostMapping("/uploadCities")
    public ResponseEntity uploadCityFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                try {
                    List<City> cities = ExcelHelper.excelToCities(file.getInputStream());
                    cities.stream().forEach(city -> {
                        State state = stateRepository.findByStateName(city.getState().getStateName());
                        city.setState(state);
                    });
                    cityRepository.saveAll(cities);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store excel data: " + e.getMessage());
                }

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return (ResponseEntity) ResponseEntity.ok();
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return (ResponseEntity) ResponseEntity.ok();
            }
        }

        message = "Please upload an excel file!";
        return (ResponseEntity) ResponseEntity.badRequest();
    }


    @Operation(summary = "Get cities",
            description = "Provides all available cities list in given state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400",
                    description = "${api.response-codes.badRequest.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404",
                    description = "${api.response-codes.notFound.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }) })
    @GetMapping(path = "/cities")
    public ResponseEntity<List<CityResponse>> getCities(@RequestParam(name = "stateId") Long stateId){

        List<City> cityList = cityRepository.findAllByStateId(stateId);
        List<CityResponse> cityResponses = new ArrayList<>();
        cityList.stream().forEach(city -> {
            CityResponse response = new CityResponse();
            response.setCityId(city.getId());
            response.setCityName(city.getCityName());
            cityResponses.add(response);
        });
        return ResponseEntity.ok(cityResponses);
    }

    @PostMapping("/uploadBranch")
    public ResponseEntity uploadBranchFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                try {
                    List<Branch> branches = ExcelHelper.excelToBranch(file.getInputStream());
                    branchRepository.saveAll(branches);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store excel data: " + e.getMessage());
                }

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return (ResponseEntity) ResponseEntity.ok();
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return (ResponseEntity) ResponseEntity.ok();
            }
        }

        message = "Please upload an excel file!";
        return (ResponseEntity) ResponseEntity.badRequest();
    }


    @Operation(summary = "Get branch",
            description = "Provides all available branch list of US military")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400",
                    description = "${api.response-codes.badRequest.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404",
                    description = "${api.response-codes.notFound.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }) })
    @GetMapping(path = "/branch")
    public ResponseEntity<List<BranchResponse>> getBranch(){

        List<Branch> branchList = branchRepository.findAll();
        List<BranchResponse> branchResponses = new ArrayList<>();
        branchList.stream().forEach(branch -> {
            BranchResponse response = new BranchResponse();
            response.setBranchId(branch.getId());
            response.setBranchName(branch.getBranchName());
            branchResponses.add(response);
        });
        return ResponseEntity.ok(branchResponses);
    }

    @PostMapping("/uploadRanks")
    public ResponseEntity uploadRankFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                try {
                    List<Rank> ranks = ExcelHelper.excelToRank(file.getInputStream());
                    ranks.stream().forEach(rank -> {
                        Branch branch = branchRepository.findByBranchName(rank.getBranch().getBranchName());
                        rank.setBranch(branch);
                    });
                    rankRepository.saveAll(ranks);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store excel data: " + e.getMessage());
                }

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return (ResponseEntity) ResponseEntity.ok();
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return (ResponseEntity) ResponseEntity.ok();
            }
        }

        message = "Please upload an excel file!";
        return (ResponseEntity) ResponseEntity.badRequest();
    }


    @PostMapping("/uploadRankImage")
    public ResponseEntity uploadRankImageFile(@RequestParam("rankId") Long rankId, @RequestParam("file") MultipartFile file) throws IOException, SQLException {
        String message = "";
        Optional<Rank> rank = rankRepository.findById(rankId);
        byte []byteArray = file.getBytes();
        Blob blob = new SerialBlob(byteArray);
        rank.get().setRankImage(blob);
        rankRepository.save(rank.get());
        return (ResponseEntity) ResponseEntity.ok();
    }


    @Operation(summary = "Get rank",
            description = "Provides all available rank list in US Military")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400",
                    description = "${api.response-codes.badRequest.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404",
                    description = "${api.response-codes.notFound.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }) })
    @GetMapping(path = "/rank")
    public ResponseEntity<List<RankResponse>> getRank(@RequestParam (name = "branchId") Long branchId){

        List<Rank> rankList = rankRepository.findAllByBranchId(branchId);
        List<RankResponse> rankResponses = new ArrayList<>();
        rankList.stream().forEach(rank -> {
            RankResponse response = new RankResponse();
            response.setRankId(rank.getId());
            response.setRank(rank.getRankName());
            try {
                response.setRankImage(rank.getRankImage().getBytes(1l, (int)rank.getRankImage().length()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rankResponses.add(response);
        });
        return ResponseEntity.ok(rankResponses);
    }
}
