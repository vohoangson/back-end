package com.japanwork.service.candidate;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Candidate;
import com.japanwork.model.Language;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.response.CandidateResponse;
import com.japanwork.repository.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexCandidateService extends CandidateBaseService {
    @Autowired
    private CandidateRepository candidateRepository;

    public ResponseDataAPI perform(int page, int paging, Language language) throws ResourceNotFoundException {
        try {
            Page<Candidate> pages = candidateRepository.findAllByDeletedAt(PageRequest.of(page-1, paging), null);
            PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
            List<CandidateResponse> list = new ArrayList<CandidateResponse>();

            if (pages.getContent().size() > 0) {
                for (Candidate candidate : pages.getContent()) {
                    list.add(getCandidateResponse(candidate, language));
                }
            }

            return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, pageInfo);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
        }
    }
}
