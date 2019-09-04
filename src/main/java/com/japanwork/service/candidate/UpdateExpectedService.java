package com.japanwork.service.candidate;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.model.*;
import com.japanwork.payload.request.CandidateExpectedRequest;
import com.japanwork.repository.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateExpectedService {
    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate perform(
            CandidateExpectedRequest candidateExpectedRequest,
            Candidate candidate,
            City city,
            District district,
            Business business,
            Level level,
            Contract contract) {

        candidate.setExpectedWorkingCity(city);
        candidate.setExpectedWorkingDistrict(district);
        candidate.setExpectedWorkingAddress(candidateExpectedRequest.getExpectedWorkingAddress());
        candidate.setExpectedBusiness(business);
        candidate.setExpectedLevel(level);
        candidate.setExpectedContract(contract);
        candidate.setExpectedSalary(candidateExpectedRequest.getExpectedSalary());
        candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
        candidate.setStatusInfo(2);
        candidate.setUpdatedAt(CommonFunction.getCurrentDateTime());

        Candidate result = candidateRepository.save(candidate);
        return result;
    }
}
