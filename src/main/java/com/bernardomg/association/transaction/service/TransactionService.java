
package com.bernardomg.association.transaction.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionForm;
import com.bernardomg.association.transaction.model.TransactionRange;
import com.bernardomg.association.transaction.model.TransactionRequest;

/**
 * Transaction service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface TransactionService {

    /**
     * Persists the received transaction.
     *
     * @param transaction
     *            transaction to persist
     * @return the persisted transaction
     */
    public Transaction create(final TransactionForm transaction);

    /**
     * Deletes the transaction with the received id.
     *
     * @param id
     *            id of the transaction to delete
     * @return {@code true} if it managed to delete, {@code false} otherwise
     */
    public Boolean delete(final Long id);

    /**
     * Returns all the transactions matching the sample. If the sample fields are empty, then all the transactions are
     * returned.
     *
     * @param request
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the transactions matching the sample
     */
    public Iterable<Transaction> getAll(final TransactionRequest request, final Pageable pageable);

    /**
     * Returns the transaction for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the transaction to acquire
     * @return an {@code Optional} with the transaction, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Transaction> getOne(final Long id);

    public TransactionRange getRange();

    /**
     * Updates the transaction for the received id with the received data.
     *
     * @param id
     *            id of the transaction to update
     * @param transaction
     *            new data for the transaction
     * @return the updated transaction
     */
    public Transaction update(final Long id, final TransactionForm transaction);

}
