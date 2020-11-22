package com.kakaopay.query;

import com.kakaopay.domain.MoneySprinkle;
import com.kakaopay.domain.MoneySprinkleRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoneySprinkleRepositoryMemoryImpl implements MoneySprinkleRepository {

    private List<MoneySprinkle> moneySprinkleList = new ArrayList<>();

    @Override
    public MoneySprinkle findByRoomIdAndToken(String roomId, String token) {
        return moneySprinkleList.stream().filter(a-> {
            if(a.getRoomId().equals(roomId) && a.getToken().equals(token)) {
                return true;
            }
            return false;
        }).findAny().orElseGet(() -> null);
    }

    @Override
    public List<MoneySprinkle> findAll() {
        return moneySprinkleList;
    }

    @Override
    public List<MoneySprinkle> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<MoneySprinkle> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public <S extends MoneySprinkle> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends MoneySprinkle> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<MoneySprinkle> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MoneySprinkle getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends MoneySprinkle> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends MoneySprinkle> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<MoneySprinkle> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<MoneySprinkle> findById(Long aLong) {
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
    public void delete(MoneySprinkle moneySprinkle) {

    }

    @Override
    public void deleteAll(Iterable<? extends MoneySprinkle> iterable) {
    }

    @Override
    public void deleteAll() {
        moneySprinkleList.clear();
    }

    @Override
    public <S extends MoneySprinkle> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MoneySprinkle> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MoneySprinkle> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MoneySprinkle> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends MoneySprinkle> S save(S s) {
        moneySprinkleList.add(s);
        return null;
    }
}
