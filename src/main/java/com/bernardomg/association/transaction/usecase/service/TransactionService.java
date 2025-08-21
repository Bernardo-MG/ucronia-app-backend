
package com.bernardomg.association.transaction.usecase.service;

import java.util.Optional;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

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
    public Transaction create(final Transaction transaction);

    /**
     * Deletes the transaction with the received id.
     *
     * @param id
     *            id of the transaction to delete
     */
    public void delete(final long id);

    /**
     * Returns all the transactions matching the sample. If the sample fields are empty, then all the transactions are
     * returned.
     *
     * @param transaction
     *            sample for filtering
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the transactions matching the sample
     */
    public Page<Transaction> getAll(final TransactionQuery transaction, final Pagination pagination,
            final Sorting sorting);

    /**
     * Returns the transaction for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the transaction to acquire
     * @return an {@code Optional} with the transaction, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Transaction> getOne(final long id);

    /**
     * Updates the received transaction.
     *
     * @param transaction
     *            new data for the transaction
     * @return the updated transaction
     */
    public Transaction update(final Transaction transaction);

}
