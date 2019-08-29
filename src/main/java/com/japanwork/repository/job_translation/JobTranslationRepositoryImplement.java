package com.japanwork.repository.job_translation;

import com.japanwork.model.Company;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JobTranslationRepositoryImplement implements JobTranslationRepository {
    @Override
    public List<JobTranslation> findAll() {
        return null;
    }

    @Override
    public List<JobTranslation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<JobTranslation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<JobTranslation> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(JobTranslation entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends JobTranslation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends JobTranslation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends JobTranslation> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<JobTranslation> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends JobTranslation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<JobTranslation> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public JobTranslation getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends JobTranslation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends JobTranslation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends JobTranslation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends JobTranslation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends JobTranslation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends JobTranslation> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public JobTranslation findByJobAndLanguageAndDeletedAt(Job job, Language language, Timestamp deletedAt) {
        return findByJobAndLanguageAndDeletedAt(job, language, deletedAt);
    }

	@Override
	public Page<JobTranslation> findAllByJobCompanyAndLanguageAndDeletedAt(Pageable page, Company company,
			Language language, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByJobCompanyAndLanguageAndDeletedAt(page, company, language, deletedAt);
	}
}
