package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Candidate;
import com.japanwork.payload.response.CandidateResponse;
import com.japanwork.repository.candidate.CandidateRepository;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AcademyService academyService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private LanguageCertificateService languageCertificateService;

    public Candidate isDel(UUID id, Timestamp deletedAt) throws ResourceNotFoundException, ServerError{
        try {
            Candidate candidate = candidateRepository.findById(id).get();
            candidate.setDeletedAt(deletedAt);
            candidateRepository.save(candidate);
            Candidate result = candidateRepository.findByIdAndDeletedAt(id, null);
            return result;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            if(deletedAt != null) {
                throw new ServerError(MessageConstant.CANDIDATE_DELETE_FAIL);
            } else {
                throw new ServerError(MessageConstant.CANDIDATE_UN_DELETE_FAIL);
            }
        }
    }

    public Page<Candidate> index(int page, int paging) throws ResourceNotFoundException{
        try {
            Page<Candidate> pages = candidateRepository.findAllByDeletedAt(PageRequest.of(page-1, paging), null);
            return pages;
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
        }
    }

    public Page<Candidate> candidatesByIds(Set<UUID> ids, int page, int paging) throws ResourceNotFoundException{
        try {
            Page<Candidate> pages = candidateRepository.findAllByIdInAndDeletedAt(PageRequest.of(page-1, paging), ids, null);
            return pages;

        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
        }
    }

    public CandidateResponse candiateFullResponse(Candidate candidate) {
        CandidateResponse candidateResponse = new CandidateResponse();

        candidateResponse.setId(candidate.getId());
        candidateResponse.setFullName(candidate.getFullName());
        candidateResponse.setDateOfBirth(candidate.getDateOfBirth());
        candidateResponse.setGender(candidate.getGender());
        candidateResponse.setMarital(candidate.getMarital());
        candidateResponse.setResidentalCityId(candidate.getResidentalCity().getId());
        candidateResponse.setResidentalDistrictId(candidate.getResidentalDistrict().getId());
        candidateResponse.setResidentalAddres(candidate.getResidentalAddres());
        candidateResponse.setAvatar(candidate.getAvatar());
        candidateResponse.setIntroduction(candidate.getIntroduction());
        candidateResponse.setJapaneseLevel(candidate.getJapaneseLevel());

        if(candidate.getExpectedWorkingCountry() != null) {
            candidateResponse.setExpectedWorkingCountryId(candidate.getExpectedWorkingCountry().getId());
        }
        
        if(candidate.getExpectedWorkingCity() != null) {
            candidateResponse.setExpectedWorkingCityId(candidate.getExpectedWorkingCity().getId());
        }

        if(candidate.getExpectedWorkingDistrict() != null) {
            candidateResponse.setExpectedWorkingDistrictId(candidate.getExpectedWorkingDistrict().getId());
        }

        if(candidate.getExpectedBusiness() != null) {
            candidateResponse.setExpectedBusinessId(candidate.getExpectedBusiness().getId());
        }

        if(candidate.getExpectedLevel() != null) {
            candidateResponse.setExpectedLevelId(candidate.getExpectedLevel().getId());
        }

        if(candidate.getExpectedContract() != null) {
            candidateResponse.setExpectedContractId(candidate.getExpectedContract().getId());
        }

        candidateResponse.setExpectedSalary(candidate.getExpectedSalary());

        candidateResponse.setAcademyResponses(academyService.listAcademyResponse(candidate.getAcademies()));
        candidateResponse.setExperienceResponses(experienceService.listExperienceResponse(candidate.getExperiences()));
        candidateResponse.setLanguageCertificates(languageCertificateService.listLanguageCertificateResponse(candidate.getLanguageCertificates()));

        return candidateResponse;
    }

    public CandidateResponse candiateShortResponse(Candidate candidate) {
        CandidateResponse candidateResponse = new CandidateResponse();

        candidateResponse.setId(candidate.getId());
        candidateResponse.setFullName(candidate.getFullName());
        candidateResponse.setAvatar(candidate.getAvatar());

        return candidateResponse;
    }
}
