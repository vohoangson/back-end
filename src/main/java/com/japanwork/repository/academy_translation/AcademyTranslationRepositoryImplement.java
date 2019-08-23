package com.japanwork.repository.academy_translation;

import com.japanwork.model.AcademyTranslation;
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
public class AcademyTranslationRepositoryImplement implements AcademyTranslationRepository {
    @Override
    public List<AcademyTranslation> findAll() {
        return null;
    }

    @Override
    public List<AcademyTranslation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AcademyTranslation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<AcademyTranslation> findAllById(Iterable<UUID> uuids) {
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
    public void delete(AcademyTranslation entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends AcademyTranslation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends AcademyTranslation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends AcademyTranslation> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<AcademyTranslation> findById(UUID uuid) {
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
    public <S extends AcademyTranslation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<AcademyTranslation> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AcademyTranslation getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends AcademyTranslation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AcademyTranslation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AcademyTranslation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AcademyTranslation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AcademyTranslation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AcademyTranslation> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public AcademyTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt) {
        return findByIdAndDeletedAt(id, deletedAt);
    }
}
