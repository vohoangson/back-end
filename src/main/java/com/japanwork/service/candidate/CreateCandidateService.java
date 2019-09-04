package com.japanwork.service.candidate;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.model.Candidate;
import com.japanwork.model.City;
import com.japanwork.model.District;
import com.japanwork.model.User;
import com.japanwork.payload.request.CandidatePersonalRequest;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateService {
    @Autowired
    private UserService userService;

    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate perform(
            CandidatePersonalRequest candidatePersonalRequest,
            User user,
            City city,
            District district) {
        Candidate candidate = new Candidate();
        candidate.setUser(user);
        candidate.setFullName(candidatePersonalRequest.getFullName());
        candidate.setDateOfBirth(candidatePersonalRequest.getDateOfBirth());
        candidate.setGender(candidatePersonalRequest.getGender());
        candidate.setMarital(candidatePersonalRequest.getMarital());
        candidate.setResidentalCity(city);
        candidate.setResidentalDistrict(district);
        candidate.setResidentalAddres(candidatePersonalRequest.getResidentalAddress());
        candidate.setAvatar(candidatePersonalRequest.getAvatar());
        candidate.setIntroduction(candidatePersonalRequest.getIntroduction());
        candidate.setJapaneseLevel(candidatePersonalRequest.getJapaneseLevel());
        candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
        candidate.setStatusInfo(1);
        candidate.setCreatedAt(CommonFunction.getCurrentDateTime());
        candidate.setUpdatedAt(CommonFunction.getCurrentDateTime());

        Candidate result = candidateRepository.save(candidate);
        userService.changePropertyId(user.getId(), result.getId());

        return result;
    }
}
