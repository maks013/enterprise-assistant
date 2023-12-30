package com.enterpriseassistant.invoice.domain;

import com.enterpriseassistant.order.domain.Payment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryInvoiceRepository implements InvoiceRepository {

    Map<Integer, Invoice> inMemoryInvoices = new ConcurrentHashMap<>();

    Invoice invoice = new Invoice(1, "FV1/2023", Payment.CASH.toString(), LocalDateTime.now().plusDays(7),
            BigDecimal.valueOf(123), BigDecimal.valueOf(100), LocalDateTime.now(), 1);

    InMemoryInvoiceRepository() {
        inMemoryInvoices.put(1, invoice);
    }

    @Override
    public Invoice save(Invoice invoice) {
        final int newInvoiceId = 2;
        invoice.setId(newInvoiceId);
        inMemoryInvoices.put(newInvoiceId, invoice);
        return inMemoryInvoices.get(newInvoiceId);
    }

    @Override
    public boolean existsByOrderId(Integer id) {
        return inMemoryInvoices.values()
                .stream()
                .anyMatch(invoice -> invoice.getOrderId().equals(id));
    }

    @Override
    public List<Invoice> findAll() {
        return inMemoryInvoices.values().stream().toList();
    }

    @Override
    public List<Invoice> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Invoice> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Invoice entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Invoice> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Invoice> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Invoice> findById(Integer id) {
        return Optional.ofNullable(inMemoryInvoices.get(id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Invoice> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Invoice> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Invoice> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Invoice getOne(Integer integer) {
        return null;
    }

    @Override
    public Invoice getById(Integer integer) {
        return null;
    }

    @Override
    public Invoice getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Invoice> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Invoice> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Invoice> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Invoice> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Invoice> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Invoice> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Invoice, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
