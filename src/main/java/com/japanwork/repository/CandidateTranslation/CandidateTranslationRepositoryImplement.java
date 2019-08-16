package com.japanwork.repository.CandidateTranslation;

import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.Translator;
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
public class CandidateTranslationRepositoryImplement implements CandidateTranslationRepository {
    @Override
    public List<CandidateTranslation> findAll() {
        return null;
    }

    @Override
    public List<CandidateTranslation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<CandidateTranslation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<CandidateTranslation> findAllById(Iterable<UUID> uuids) {
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
    public void delete(CandidateTranslation entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends CandidateTranslation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends CandidateTranslation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends CandidateTranslation> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<CandidateTranslation> findById(UUID uuid) {
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
    public <S extends CandidateTranslation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<CandidateTranslation> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public CandidateTranslation getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends CandidateTranslation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends CandidateTranslation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends CandidateTranslation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends CandidateTranslation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CandidateTranslation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends CandidateTranslation> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public CandidateTranslation findByCandidateAndLanguageAndDeletedAt(Candidate candidate, Language language, Timestamp deletedAt) {
        return findByCandidateAndLanguageAndDeletedAt(candidate, language, deletedAt);
    }
}
