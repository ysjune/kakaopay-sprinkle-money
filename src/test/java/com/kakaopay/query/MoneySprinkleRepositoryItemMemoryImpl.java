package com.kakaopay.query;

import com.kakaopay.domain.MoneySprinkleItem;
import com.kakaopay.domain.MoneySprinkleItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoneySprinkleRepositoryItemMemoryImpl implements MoneySprinkleItemRepository {

    private List<MoneySprinkleItem> moneySprinkleItemList = new ArrayList<>();

    @Override
    public List<MoneySprinkleItem> findAll() {
        return moneySprinkleItemList;
    }

    @Override
    public List<MoneySprinkleItem> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<MoneySprinkleItem> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public <S extends MoneySprinkleItem> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends MoneySprinkleItem> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<MoneySprinkleItem> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MoneySprinkleItem getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends MoneySprinkleItem> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends MoneySprinkleItem> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<MoneySprinkleItem> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MoneySprinkleItem> S save(S s) {
        moneySprinkleItemList.add(s);
        return null;
    }

    @Override
    public Optional<MoneySprinkleItem> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(MoneySprinkleItem moneySprinkleItem) {

    }

    @Override
    public void deleteAll(Iterable<? extends MoneySprinkleItem> iterable) {

    }

    @Override
    public void deleteAll() {
        moneySprinkleItemList.clear();
    }

    @Override
    public <S extends MoneySprinkleItem> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MoneySprinkleItem> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MoneySprinkleItem> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MoneySprinkleItem> boolean exists(Example<S> example) {
        return false;
    }
}
