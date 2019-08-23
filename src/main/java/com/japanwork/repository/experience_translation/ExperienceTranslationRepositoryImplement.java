package com.japanwork.repository.experience_translation;

import com.japanwork.model.ExperienceTranslation;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ExperienceTranslationRepositoryImplement implements ExperienceTranslationRepository {

    @Override
    public ExperienceTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt) {
        return findByIdAndDeletedAt(id, deletedAt);
    }

    @Override
    public List<ExperienceTranslation> findAll() {
        return null;
    }

    @Override
    public List<ExperienceTranslation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ExperienceTranslation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<ExperienceTranslation> findAllById(Iterable<UUID> uuids) {
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
    public void delete(ExperienceTranslation entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends ExperienceTranslation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends ExperienceTranslation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ExperienceTranslation> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ExperienceTranslation> findById(UUID uuid) {
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
    public <S extends ExperienceTranslation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<ExperienceTranslation> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ExperienceTranslation getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends ExperienceTranslation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ExperienceTranslation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ExperienceTranslation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ExperienceTranslation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ExperienceTranslation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ExperienceTranslation> boolean exists(Example<S> example) {
        return false;
    }
}
