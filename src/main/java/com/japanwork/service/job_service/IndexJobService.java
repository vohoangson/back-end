package com.japanwork.service.job_service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.JobFilterRequest;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.support.CommonSupport;

@Service
public class IndexJobService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommonSupport commonSupport;
    
    public ResponseDataAPI perform(
            JobFilterRequest jobFilterRequest,
            int page,
            int paging,
            Language language
    ) throws ResourceNotFoundException
    {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT jt ");
            sql.append("    FROM JobTranslation jt ");
            sql.append("	INNER JOIN  jt.language la ");
            sql.append("		ON la.id = '" + language.getId() + "'" );
            sql.append("	INNER JOIN  jt.job j ");
            sql.append("	INNER JOIN  j.company c ");
            sql.append("	INNER JOIN  CompanyTranslation ct ");
            sql.append("		ON  c.id = ct.company.id ");
            sql.append("		AND ct.language.id = '" + language.getId() + "'");
            sql.append("	INNER JOIN j.businesses b ");
            sql.append("	INNER JOIN j.contract con ");
            sql.append("	INNER JOIN j.level lev ");
            sql.append("	INNER JOIN j.city city ");
            sql.append("	WHERE ");
            sql.append("	j.deletedAt is null ");
            if(jobFilterRequest != null) {
                if(!jobFilterRequest.getJobName().isEmpty()) {
                    sql.append(" AND ");
                    sql.append(" jt.name LIKE '%" + jobFilterRequest.getJobName() + "%' ");
                }
                if(!jobFilterRequest.getCompanyName().isEmpty()) {
                    sql.append(" AND ");
                    sql.append("ct.name LIKE '%" + jobFilterRequest.getCompanyName() + "%' ");
                }
                if(jobFilterRequest.getBusinessIds() != null) {
                    sql.append(" AND ");
                    if(jobFilterRequest.getBusinessIds().size() == 1) {
                        sql.append("b.id = '" + jobFilterRequest.getBusinessIds().get(0) + "' ");
                    }

                    if(jobFilterRequest.getBusinessIds().size() > 1) {
                        sql.append("( b.id = '" + jobFilterRequest.getBusinessIds().get(0) + "' ");
                        for(int i = 1; i< jobFilterRequest.getBusinessIds().size(); i++) {
                            sql.append(" OR b.id = '" + jobFilterRequest.getBusinessIds().get(i) + "' ");
                        }
                        sql.append(" )");
                    }
                }

                if(jobFilterRequest.getContractIds() != null) {
                    sql.append(" AND ");
                    if(jobFilterRequest.getContractIds().size() == 1) {
                        sql.append("con.id = '" + jobFilterRequest.getContractIds().get(0) + "' ");
                    }

                    if(jobFilterRequest.getContractIds().size() > 1) {
                        sql.append("( con.id = '" + jobFilterRequest.getContractIds().get(0) + "' ");
                        for(int i = 1; i< jobFilterRequest.getContractIds().size(); i++) {
                            sql.append(" OR con.id = '" + jobFilterRequest.getContractIds().get(i) + "' ");
                        }
                        sql.append(" ) ");
                    }
                }
                if(jobFilterRequest.getLevelIds() != null) {
                    sql.append(" AND ");
                    if(jobFilterRequest.getLevelIds().size() == 1) {
                        sql.append("lev.id = '" + jobFilterRequest.getLevelIds().get(0) + "' ");
                    }

                    if(jobFilterRequest.getLevelIds().size() > 1) {
                        sql.append("( lev.id = '" + jobFilterRequest.getLevelIds().get(0) + "' ");
                        for(int i = 1; i< jobFilterRequest.getLevelIds().size(); i++) {
                            sql.append(" OR lev.id = '" + jobFilterRequest.getLevelIds().get(i) + "' ");
                        }
                        sql.append(" ) ");
                    }
                }
                if(jobFilterRequest.getCityIds() != null) {
                    sql.append(" AND ");
                    if(jobFilterRequest.getCityIds().size() == 1) {
                        sql.append("city.id = '" + jobFilterRequest.getCityIds().get(0) + "' ");
                    }

                    if(jobFilterRequest.getCityIds().size() > 1) {
                        sql.append("( city.id = '" + jobFilterRequest.getCityIds().get(0) + "' ");
                        for(int i = 1; i< jobFilterRequest.getCityIds().size(); i++) {
                            sql.append(" OR city.id = '" + jobFilterRequest.getCityIds().get(i) + "' ");
                        }
                        sql.append(" ) ");
                    }
                }

                if(jobFilterRequest.getMinSalary() != 0) {
                    sql.append(" AND ");
                    sql.append(" (j.minSalary > " + jobFilterRequest.getMinSalary());
                    sql.append(" OR j.maxSalary > " + jobFilterRequest.getMinSalary() + ")");
                }

                if(!jobFilterRequest.getPostTime().isEmpty()) {
                    sql.append(" AND ");
                    try {
                        sql.append(" jt.createdAt >= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jobFilterRequest.getPostTime()) + "'");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                sql.append(" ORDER BY jt.createdAt ASC ");
            }

            List<JobTranslation> pages = (List<JobTranslation>)entityManager.createQuery(sql.toString(), JobTranslation.class).setFirstResult((page-1)*paging)
                    .setMaxResults(paging).getResultList();

            long totalElements = ((List<JobTranslation>)entityManager.createQuery(sql.toString(), JobTranslation.class).getResultList()).size();

            int totalPage = (int)totalElements / paging;
            if((totalElements % paging) > 0) {
                totalPage ++;
            }
            if(totalPage == 0) {
                totalPage = 1;
            }

            PageInfo pageInfo = new PageInfo(page, totalPage, totalElements);

            List<JobResponse> list = new ArrayList<JobResponse>();

            JobResponse jobResponse = new JobResponse();
            if(pages.size() > 0) {
                for (JobTranslation jobTranslation : pages) {
                	Company company = jobTranslation.getJob().getCompany();
                	CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(company, language);
                    list.add(jobResponse.jobFullSerializer(
                    		jobTranslation.getJob(),
                    		jobTranslation,
                            new CompanyResponse().companyMainSerializer(company, companyTranslation)
                    ));
                }
            }

            return new ResponseDataAPI(
                    CommonConstant.ResponseDataAPIStatus.SUCCESS,
                    list,
                    pageInfo
            );
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
        }
    }
}
