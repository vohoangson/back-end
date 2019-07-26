package com.japanwork.repository.language;

import com.japanwork.model.Language;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LanguageRepositoryImplement implements LanguageRepository {
    @Override
    public List<Language> findAll() {
        return null;
    }

    @Override
    public List<Language> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Language> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Language> findAllById(Iterable<UUID> uuids) {
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
    public void delete(Language entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Language> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Language> S save(S entity) {
        return save(entity);
    }

    @Override
    public <S extends Language> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Language> findById(UUID uuid) {
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
    public <S extends Language> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Language> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Language getOne(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Language> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Language> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Language> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Language> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Language> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Language> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Language findByIdAndDeletedAt(UUID id, Timestamp deletedAt) {
        return findByIdAndDeletedAt(id, deletedAt);
    }

    @Override
    public List<Language> findAllByDeletedAt(Timestamp deletedAt) {
        return findAllByDeletedAt(deletedAt);
    }
}
